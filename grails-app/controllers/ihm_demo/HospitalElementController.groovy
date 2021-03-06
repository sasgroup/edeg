package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class HospitalElementController {
        def sendMailService
        
        private HospitalElement saveInstance (HospitalElement instance, def param) {
                
                def ehrCode = instance.hospital.ehr.code
                
                // TODO: check here for possible changes to be reported via Email Notification
                def modificationDetected = false
                
                if (isNULL(instance.location, "") != param.location) 
                        modificationDetected = true 
                instance.location = param.location
                
                if (instance.sourceEHR != (ehrCode == param.source))
                        modificationDetected = true
                instance.sourceEHR = (ehrCode == param.source)
                
                if (isNULL(instance.source, "") != param.source)
                        modificationDetected = true
                //instance.source = param.sourceEHR ? instance.hospital.ehr.code : param.source                
                instance.source = param.source
                
                if (isNULL(instance.valueSet, "") != param.valueSet)
                        modificationDetected = true
                instance.valueSet = param.valueSet

                if (isNULL(instance.valueSetFile, "") != param.valueSetFile)
                        modificationDetected = true
                instance.valueSetFile = param.valueSetFile
                // TODO append notes info
        

                if (isNULL(instance.notes, "") != param.notes)
                        modificationDetected = true
                instance.notes = param.notes

                //TODO correct ValuesType
                if (instance.valuesType != ValuesType.get(param?.valuesTypeId))
                        modificationDetected = true
                instance.valuesType = ValuesType.get(param?.valuesTypeId)
                
                                
                instance.save(flush :true)
                
                //check if new ValueSet detected
                if (!(param.hospitalValueSet.empty && HospitalValueSet.findAllByHospitalElement(instance).empty)) {
                        if (param.hospitalValueSet.size() != HospitalValueSet.findAllByHospitalElement(instance).size()) {
                                modificationDetected = true
                        } else {
                                HospitalValueSet [] hospitalElements = HospitalValueSet.findAllByHospitalElement(instance)
                                for (inst in hospitalElements){
                                        boolean isHospitalValueSet = false
                                                for (hvs in param.hospitalValueSet){
                                                        if (inst.code == hvs.code && inst.mnemonic == hvs.mnemonic) {isHospitalValueSet = true; break;}
                                                }
                                        if (!isHospitalValueSet)         {modificationDetected = true; break}
                                }
                        }        
                }
                
                //check if new ExtraLocation detected
                if (!(param.elementExtraLocation.empty && ElementExtraLocation.findAllByHospitalElement(instance).empty)) {
                        if (param.elementExtraLocation.size() != ElementExtraLocation.findAllByHospitalElement(instance).size()) {
                                modificationDetected = true
                        } else {
                                ElementExtraLocation [] elementExtraLocation = ElementExtraLocation.findAllByHospitalElement(instance)
                                for (inst in elementExtraLocation){
                                        boolean isElementExtraLocation = false
                                                for (eel in param.elementExtraLocation){
                                                        if ((isNULL(inst.location,"") == eel.location) && isNULL(inst.source,"") == eel.source && inst.valuesType.id == eel.valuesTypeId) {isElementExtraLocation = true; break;}
                                                }
                                        if (!isElementExtraLocation)         {modificationDetected = true; break}
                                }
                        }
                }

                
                if (modificationDetected)
                        sendMailService.updateDataElement(instance?.hospital.email, instance?.hospital.name, instance?.dataElement.name, HospitalMeasure.get(param?.m_id)?.measure.name, new Date(), session?.user.login)
        
        
                
                // TODO remove all hospital value sets
                HospitalValueSet.executeUpdate("delete HospitalValueSet hvs where hvs.hospitalElement=?", [instance])
                ElementExtraLocation.executeUpdate("delete ElementExtraLocation eel where eel.hospitalElement=?", [instance])
                
                if (param.hospitalValueSet)                                                                                                                                
                        for (hvs in param.hospitalValueSet){
                                new HospitalValueSet(code:hvs.code, mnemonic:hvs.mnemonic, hospitalElement:instance).save(flush:true)
                        }
                        
                
                if (param.elementExtraLocation)
                        for (e in param.elementExtraLocation){
                                //TODO correct ValuesType
                                if (e.valuesTypeId){
                                        ElementExtraLocation xl = new ElementExtraLocation(        location:e.location, 
                                                                                                                                                source:e.source, 
                                                                                                                                                sourceEHR:(ehrCode==e.source), 
                                                                                                                                                hospitalElement:instance, 
                                                                                                                                                valuesType : ValuesType.get(e.valuesTypeId))
                                        xl.save(flush:true)
                                }
                        }
				//different notification for admin and internal notes		
                if (session.user.role == "admin") { 
                        if (isNULL(instance.internalNotes, "") != param.internalNotes  ) {
                                instance.internalNotes = param.internalNotes
                                if (!modificationDetected)
                                        sendMailService.updateDataElement("", instance?.hospital.name, instance?.dataElement.name, HospitalMeasure.get(param?.m_id)?.measure.name, new Date(), session?.user.login)
                        }        
                }        

                return instance.save(flush :true)        
        }

        def save() {
                def hospitalElementInstance  = saveInstance(new HospitalElement(), params)
                render(contentType: "text/json") {
                        resp = "ok"
                        message = "HospitalElement ${hospitalElementInstance.dataElement.name} successfully created"
                }
        }
        
        def show() {
                if (params.id && HospitalMeasure.exists(params.id)) {
                        
                        if (params.defaults){//view defaults hospitalmeasure
                                def hMeasure = HospitalMeasure.get(params.id)
                                //reset to default settings only required HospitalElement"
                                if (params.he_id){
                                        def hElement = HospitalElement.get(params.he_id)
                                        if (hElement && hElement.sourceEHR){
                                                def defSettings = DataElementDefaults.findByDataElementAndEhr(hElement.dataElement, hElement.hospital.ehr)
                                                if (defSettings && hElement.sourceEHR) {
                                                        hElement.location = defSettings.location
                                                }
                                                hElement.save(flush:true)
                                                sendMailService.updateDataElement(hElement?.hospital.email, hElement?.hospital.name, hElement?.dataElement.name, HospitalMeasure.get(params?.id)?.measure.name, new Date(), session?.user.login)
                                        }
                                        
                                        def hospitalElements =  hMeasure.hospitalMeasureElements
                                        render(contentType: "text/json") {
                                                        hospitalElements = array {
                                                                for (hme in hospitalElements) {
                                                                        if (hme.hospitalElement.id == hElement.id){
                                                                                hospitalElement         id : hme.hospitalElement.id,
                                                                                                                        version : hme.hospitalElement.version,
																														//display only for admin
                                                                                                                        internalNotes : isNULL(availableForUser(hme.hospitalElement.internalNotes), ""),
                                                                                                                        location : isNULL(hme.hospitalElement.location,""),
                                                                                                                        notes : isNULL(hme.hospitalElement.notes,""),
                                                                                                                        source : isNULL(hme.hospitalElement.source,""),
                                                                                                                        sourceEHR : hme.hospitalElement.sourceEHR,
                                                                                                                        valueSet : isNULL(hme.hospitalElement.valueSet,""),
                                                                                                                        valueSetFile : isNULL(hme.hospitalElement.valueSetFile,""),
                                                                                                                        valuesTypeId : hme.hospitalElement.valuesType.id,
                                                                                                                        dataElement : hme.hospitalElement.dataElement.code,
                                                                                                                        element_notes:hme.hospitalElement.dataElement.notes,
                                                                                                                        help:isNULL(hme.hospitalElement.dataElement.help,""),
                                                                                                                        ids : deriveIds(hme.hospitalElement),
                                                                                                        
                                                                                                                        hospitalValueSet : array {
                                                                                                                                for (hvs in HospitalValueSet.findAllByHospitalElement(hme.hospitalElement)){
                                                                                                                                        hvset code : hvs.code,
                                                                                                                                        mnemonic : hvs.mnemonic
                                                                                                                                }
                                                                                                                        },
                                                                                                
                                                                                                                        elementExtraLocation : array {
                                                                                                                                for (e in ElementExtraLocation.findAllByHospitalElement(hme.hospitalElement)){
                                                                                                                                        elem location  : e.location,
                                                                                                                                                 source    : e.source,
                                                                                                                                                 sourceEHR : e.sourceEHR,
                                                                                                                                                 valuesTypeId : e.valuesType.id
                                                                                                                                }
                                                                                                                        }
                                                                        }
                                                                }
                                                        }
                                                }
                                }
                                
                                else{//reset all hospital element to default settings
                                        if (hMeasure){
                                                def hElementIds =  hMeasure.hospitalMeasureElements.collect{it.hospitalElement.id}
                                                for (hElementId in hElementIds){
                                                        def hElement = HospitalElement.get(hElementId)
                                                        if (hElement && hElement.sourceEHR){
                                                                def defSettings = DataElementDefaults.findByDataElementAndEhr(hElement.dataElement, hElement.hospital.ehr)
                                                                if (defSettings) {
                                                                        hElement.location = defSettings.location
                                                                }
                                                                hElement.save(flush:true)
                                                                sendMailService.updateDataElement(hElement?.hospital.email, hElement?.hospital.name, hElement?.dataElement.name, hMeasure?.measure.name, new Date(), session?.user.login)
                                                        }
                                                }
                                                
                                                def hospitalElements =  hMeasure.hospitalMeasureElements
                                                render(contentType: "text/json") {
                                                                hospitalElements = array {
                                                                        for (hme in hospitalElements) {
                                                                                hospitalElement         id : hme.hospitalElement.id,
                                                                                                                        version : hme.hospitalElement.version,
                                                                                                                        internalNotes : isNULL(availableForUser(hme.hospitalElement.internalNotes), ""),
                                                                                                                        location : isNULL(hme.hospitalElement.location,""),
                                                                                                                        notes : isNULL(hme.hospitalElement.notes,""),
                                                                                                                        source : isNULL(hme.hospitalElement.source,""),
                                                                                                                        sourceEHR : hme.hospitalElement.sourceEHR,
                                                                                                                        valueSet : isNULL(hme.hospitalElement.valueSet,""),
                                                                                                                        valueSetFile : isNULL(hme.hospitalElement.valueSetFile,""),
                                                                                                                        valuesTypeId : hme.hospitalElement.valuesType.id,
                                                                                                                        dataElement : hme.hospitalElement.dataElement.code,
                                                                                                                        element_notes:hme.hospitalElement.dataElement.notes,
                                                                                                                        help:isNULL(hme.hospitalElement.dataElement.help,""),
                                                                                                                        ids : deriveIds(hme.hospitalElement),
                                                                                                        
                                                                                                                        hospitalValueSet : array {
                                                                                                                                for (hvs in HospitalValueSet.findAllByHospitalElement(hme.hospitalElement)){
                                                                                                                                        hvset code : hvs.code,
                                                                                                                                        mnemonic : hvs.mnemonic
                                                                                                                                }
                                                                                                                        },
                                                                                                
                                                                                                                        elementExtraLocation : array {
                                                                                                                                for (e in ElementExtraLocation.findAllByHospitalElement(hme.hospitalElement)){
                                                                                                                                        elem location  : e.location,
                                                                                                                                                 source    : e.source,
                                                                                                                                                 sourceEHR : e.sourceEHR,
                                                                                                                                                 valuesTypeId : e.valuesType.id
                                                                                                                                }
                                                                                                                        }
                                                                        }
                                                                }
                                                        }
                                        }
                                }
                                
                                
                        }
                        else{//view not default HospitalMeasure
                                def  result = HospitalMeasure.get(params.id)
                                HospitalProduct hospitalProduct = HospitalProduct.findByHospitalAndProduct(Hospital.get(params.h_id), Product.get(params.p_id))
                                HospitalProductMeasure hospitalProductMeasure         = HospitalProductMeasure.findByHospitalProductAndHospitalMeasure(hospitalProduct, result)
                                def hospitalElements = new ArrayList()

                                //TODO verify permission to view HospitalMeasure
                                if ((session.user.role.equals("user") && hospitalProductMeasure?.included)||session.user.role.equals("admin")) {
                                        hospitalElements =  result.hospitalMeasureElements
                                        render(contentType: "text/json") {
                                                hospitalElements = array {
                                                        for (hme in hospitalElements) {
                                                                hospitalElement         id : hme.hospitalElement.id,
                                                                                                        version : hme.hospitalElement.version,
                                                                                                        internalNotes : isNULL(availableForUser(hme.hospitalElement.internalNotes), ""),
                                                                                                        location : isNULL(hme.hospitalElement.location,""),
                                                                                                        notes : isNULL(hme.hospitalElement.notes,""),
                                                                                                        source : isNULL(hme.hospitalElement.source,""),
                                                                                                        sourceEHR : hme.hospitalElement.sourceEHR,
                                                                                                        valueSet : isNULL(hme.hospitalElement.valueSet,""),
                                                                                                        valueSetFile : isNULL(hme.hospitalElement.valueSetFile,""),
                                                                                                        valuesTypeId : hme.hospitalElement.valuesType.id,
                                                                                                        dataElement : hme.hospitalElement.dataElement.code,
                                                                                                        element_notes:hme.hospitalElement.dataElement.notes,
                                                                                                        help:isNULL(hme.hospitalElement.dataElement.help,""),
                                                                                                        ids : deriveIds(hme.hospitalElement),
                                                                                        
                                                                                                        hospitalValueSet : array {
                                                                                                                for (hvs in HospitalValueSet.findAllByHospitalElement(hme.hospitalElement)){
                                                                                                                        hvset code : hvs.code,
                                                                                                                        mnemonic : hvs.mnemonic
                                                                                                                }
                                                                                                        },
                                                                                
                                                                                                        elementExtraLocation : array {
                                                                                                                for (e in ElementExtraLocation.findAllByHospitalElement(hme.hospitalElement)){
                                                                                                                        elem location  : e.location,
                                                                                                                                 source    : e.source,
                                                                                                                                 sourceEHR : e.sourceEHR,
                                                                                                                                 valuesTypeId : e.valuesType.id
                                                                                                                }
                                                                                                        }
                                                        }
                                                }
                                        }
                                } 
                                else {
                                        render(contentType: "text/json") {
                                                resp = "false"
                                                message = "Some porblems with permissions"
                                        }
                                }
                        }
                }
        }

        def update(Long id, Long version) {
                def hospitalElementInstance = HospitalElement.get(id)

                if  (!hospitalElementInstance) {
                        render(contentType: "text/json") {
                                resp = "error"
                                message = "Id exceptions"
                        }
                }

                if (params.version != null) {
                        if (hospitalElementInstance.version > params.version) {
                                return render(contentType: "text/json") {
                                        resp = "error"
                                        message = "Another user edited this record and saved the changes before you attempted to save your changes. Re-edit the record [${hospitalElementInstance.dataElement.name}]."
                                }
                        }
                }

                hospitalElementInstance  = saveInstance(hospitalElementInstance, params)
                render(contentType: "text/json") {
                        resp = "ok"
                        message = "The Hospital Element is updated successfully"
                }
        }

        def delete(Long id) {
                println "Delete"
        }
        
        
        private String deriveIds(HospitalElement hElement){
                def defSettings = DataElementDefaults.findByDataElementAndEhr(hElement.dataElement, hElement.hospital.ehr)
                if (defSettings) {
                        return defSettings.ids
                }
                return ""
        }
        
        private String isNULL(String str, String dfl){
                return (null!=str)?str:dfl
        }
        
        private String availableForUser (String value) {
                if (session.user.role.equals("user"))
                        return null
                return value
        }
}
