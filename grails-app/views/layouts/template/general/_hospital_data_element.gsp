<!-- hospital_data_element-form -->
<script type="text/template" class="template" id="hospital_data_element-form">
<div id="data-element-page">    
    <h3></h3>
 	<div id="main_table">
	 <table class="table table-condensed" id="hospital-elements">
	  <thead><tr>
 				<th class="data-element-column">Data Element</th>    
		    	<th>Location</th>           
				<th title="SourceEHR">EHR</th>
				<th>Source EHR</th>
				<th>Code Type</th>
		    	<th>Values Type</th>
				<th>Reset</th>
				<th>Help</th>	    		    
			 </tr>
      </thead>				
    <tbody></tbody>
    </table>
	</div>  	
		<div id="deatails">	
		<ul id="detailsTab" class="nav nav-tabs">
          <li class="active">
            <a data-toggle="tab" href="#qa2">Questions and Comments</a>
          </li>
          <li class="qa3">
            <a data-toggle="tab" href="#qa3">Internal IHM Comments</a>
          </li>
		  <li class="">
            <a data-toggle="tab" href="#hospital-specific-tab">Hospital Specific Value List</a>
          </li>	
		   <li class="">
            <a data-toggle="tab" href="#extra-location">Additional Locations</a>
          </li>
        </ul>
         
    	<div id="detailsTabContent" class="tab-content">
          <div id="qa2" class="tab-pane fade active in">
				<div id="tab-qa2">
					<div rows="3" class="txt-qa input-xlarge" placeholder="Click on the Data Element to view its details">Click on the Data Element to view its details</div>					
					<textarea rows="2" class="input-xlarge"></textarea><a class= "btn send-btn" title="Add"><i class="icon-share"></i></a>		
				</div>
          </div>
          <div id="qa3" class="tab-pane fade">
		  		<div id="tab-qa3">
					<div rows="3" class="txt-qa input-xlarge" placeholder="Click on the Data Element to view its details"></div>					
					<textarea rows="2" class="input-xlarge"></textarea><a class= "btn send-btn" title="Add"><i class="icon-share"></i></a>
				</div>
          </div>		  
		  <div id="hospital-specific-tab" class="tab-pane fade">             
		    <div class="row-fluid" id="hs-table">						
				<!--<div class="span7" >             
				<table id="hospital-specific-table" class="table table-bordered table-condensed">				
				<thead>
        			<tr>
            			<th>
                			Code
            			</th>
            			<th>
                			CodeType
            			</th>
            			<th>
							Mnemonic
						</th>
						<th></th>
						<th></th>			
        			</tr>					
    			</thead>
    			<tbody>					
    			</tbody>
				</table>
				</div>-->
				<div class="span5">						
						<div class="row-fluid">
                          <label for="txtValueSet" class="span4">Value Set Name</label>   
					      <input id="txtValueSet" type="text" class="span8">
					    </div>					  
					    <div class="row-fluid">	
							<form id="uploadForm"  method="post" enctype="multipart/form-data" action="/ihm/api/file">
								<label class="span4">File Upload</label>  
								<input type="file"   id="fileToUpload" name="fileToUpload"/>								
								<input type="button" id="upload" class= "hide"   value="Upload"/>
								<input type="button" id="del"    class= "hide"   value="Delete"/>
								<input type="hidden" id="currentHospitalElement" name="currentHospitalElement"/>
							</form>							
							<div id="output2" class='hide'></div>
					   </div>					   	 				
				</div>
			</div>					
		  </div>
		  <div id="extra-location" class="tab-pane fade">
			 <!--<table id="extra-table" class="table table-condensed table-bordered">				
				<thead>
        			<tr>
            			<th>Location</th>           
						<th title="SourceEHR">EHR</th>
						<th>Source EHR</th>
						<th>Code Type</th>
		    			<th>Values Type</th>		
						<th></th>
						<th></th>			
        			</tr>					
    			</thead>
    			<tbody>					
    			</tbody>
				</table>-->		   
          </div>
       </div>
	</div>
 
<div class="form-actions">                   
<button type="reset" id="cancel" class="btn btn-info pull-right">Cancel</button>
<button id="save-mark-btn" class="btn btn-info pull-right">Save&Mark</button>
<button id="save-btn" class="btn btn-info pull-right">Save</button>			
</div>
</div>
</script>

<!-- hospital_data_element -->
<script type="text/template" class="template" id="hospital_data_element">
 <td id="{{id}}" class="slc_row" title="{{element_notes}}">{{dataElement}}</td>
 <td><input type="text" class="location" name="location" id="location" value="{{location}}"></td>
 <td><input type="checkbox" class="sourceEHR" name="sourceEHR" id="sourceEHR" value={{sourceEHR}} {{sourceEHR ? "checked": ""}}></td>
 <td><input type="text" class="source" id="source" name= "source" value="{{source}}"></td>
 <td>
    <select class="slcCodeType" name="codeType">                
				 <option value="">-Select-</option>
				 <option value="NotApplicable" {{codeType.name=="NotApplicable"? "selected": ""}}>NotApplicable</option>
				 <option value="AdministrativeSex" {{codeType.name=="AdministrativeSex"? "selected": ""}}>AdministrativeSex</option>                 
				 <option value="CDCREC" {{codeType.name=="CDCREC"? "selected": ""}}>CDCREC</option>
				 <option value="CDT" {{codeType.name=="CDT"? "selected": ""}}>CDT</option>
				 <option value="CPT" {{codeType.name=="CPT"? "selected": ""}}>CPT</option>
				 <option value="CVX" {{codeType.name=="CVX"? "selected": ""}}>CVX</option>
				 <option value="DischargeDisposition" {{codeType.name=="DischargeDisposition"? "selected": ""}}>DischargeDisposition</option>
				 <option value="HCPCS" {{codeType.name=="HCPCS"? "selected": ""}}>HCPCS</option>
				 <option value="HSLOC" {{codeType.name=="HSLOC"? "selected": ""}}>HSLOC</option>
				 <option value="ICD10CM" {{codeType.name=="ICD10CM"? "selected": ""}}>ICD10CM</option>
				 <option value="ICD10PCS" {{codeType.name=="ICD10PCS"? "selected": ""}}>ICD10PCS</option>
				 <option value="ICD9CM" {{codeType.name=="ICD9CM"? "selected": ""}}>ICD9CM</option>
				 <option value="LOINC" {{codeType.name=="LOINC"? "selected": ""}}>LOINC</option>
				 <option value="RXNORM" {{codeType.name=="RXNORM"? "selected": ""}}>RXNORM</option>
				 <option value="SNOMEDCT" {{codeType.name=="SNOMEDCT"? "selected": ""}}>SNOMEDCT</option>
				 <option value="SOP" {{codeType.name=="SOP"? "selected": ""}}>SOP</option>
    </select>
 </td>
 <td>
    <select class="slcValueType" name="valueType">			
				<option value="">-Select-</option>
				<option value="NotApplicable" {{valueType.name=="NotApplicable"? "selected": ""}}>NotApplicable</option>	     
                <option value="IMO_Code" {{valueType.name=="IMO_Code"? "selected": ""}}>IMO_Code</option>                
				<option value="Query_Mnemonic" {{valueType.name=="Query_Mnemonic"? "selected": ""}}>Query/Mnemonic</option>
				<option value="HospitalSpecific" {{valueType.name=="HospitalSpecific"? "selected": ""}}>HospitalSpecific</option>
				<option value="StandardCode" {{valueType.name=="StandardCode"? "selected": ""}}>StandardCode</option>
				<option value="ValueSet" {{valueType.name=="ValueSet"? "selected": ""}}>ValueSet</option>
     </select>
 </td>
<td class="f-btn"><div id="reset" class= "btn" title="Reset the data element location to default in accordance with the current EHR version"><i class="icon-refresh"></i></div></td>
<td><a class="btn show_info" rel="tooltip" did="{{id}}" title=""><i class="icon-info-sign" did="{{id}}"></i></a></td>
</script>


<!--extra-elements -->
<script type="text/template" class="template" id="extra-elements">
 <td><input type="text" class="location" id="location" value="{{location}}" placeholder="Enter Location"></td>
 <td><input type="checkbox" class="sourceEHR" id="sourceEHR" value="{{sourceEHR}}" {{sourceEHR ? "checked": ""}}></td>
 <td><input type="text" class="source" id="source" value="{{source}}" placeholder="Enter Source"></td>
 <td><select class="slcCodeType">                
				 <option value="">-Select-</option>
				 <option value="NotApplicable" {{codeType.name=="NotApplicable"? "selected": ""}}>NotApplicable</option>
				 <option value="AdministrativeSex" {{codeType.name=="AdministrativeSex"? "selected": ""}}>AdministrativeSex</option>                 
				 <option value="CDCREC" {{codeType.name=="CDCREC"? "selected": ""}}>CDCREC</option>
				 <option value="CDT" {{codeType.name=="CDT"? "selected": ""}}>CDT</option>
				 <option value="CPT" {{codeType.name=="CPT"? "selected": ""}}>CPT</option>
				 <option value="CVX" {{codeType.name=="CVX"? "selected": ""}}>CVX</option>
				 <option value="DischargeDisposition" {{codeType.name=="DischargeDisposition"? "selected": ""}}>DischargeDisposition</option>
				 <option value="HCPCS" {{codeType.name=="HCPCS"? "selected": ""}}>HCPCS</option>
				 <option value="HSLOC" {{codeType.name=="HSLOC"? "selected": ""}}>HSLOC</option>
				 <option value="ICD10CM" {{codeType.name=="ICD10CM"? "selected": ""}}>ICD10CM</option>
				 <option value="ICD10PCS" {{codeType.name=="ICD10PCS"? "selected": ""}}>ICD10PCS</option>
				 <option value="ICD9CM" {{codeType.name=="ICD9CM"? "selected": ""}}>ICD9CM</option>
				 <option value="LOINC" {{codeType.name=="LOINC"? "selected": ""}}>LOINC</option>
				 <option value="RXNORM" {{codeType.name=="RXNORM"? "selected": ""}}>RXNORM</option>
				 <option value="SNOMEDCT" {{codeType.name=="SNOMEDCT"? "selected": ""}}>SNOMEDCT</option>
				 <option value="SOP" {{codeType.name=="SOP"? "selected": ""}}>SOP</option>
    </select>
 </td>
 <td><select class="slcValueType">			
				<option value="">-Select-</option>
				<option value="NotApplicable" {{valueType.name=="NotApplicable"? "selected": ""}}>NotApplicable</option>	     
                <option value="IMO_Code" {{valueType.name=="IMO_Code"? "selected": ""}}>IMO_Code</option>                
				<option value="Query_Mnemonic" {{valueType.name=="Query_Mnemonic"? "selected": ""}}>Query/Mnemonic</option>
				<option value="HospitalSpecific" {{valueType.name=="HospitalSpecific"? "selected": ""}}>HospitalSpecific</option>
				<option value="StandardCode" {{valueType.name=="StandardCode"? "selected": ""}}>StandardCode</option>
				<option value="ValueSet" {{valueType.name=="ValueSet"? "selected": ""}}>ValueSet</option>
     </select>
 </td>
 <td><div id="plus-btn" class="btn btn-mini"><i class="icon-plus"></i></div></td>
 <td><div id="minus-btn" class="btn btn-mini"><i class="icon-minus"></i></div></td>
</script>

<!-- hospital-specific -->
<script type="text/template" class="template" id="hospital-specific">
 <td><input type="text" class="code" id="code" value="{{code}}" placeholder="Enter Code"></td> 
 <td><select class="slcCodeType">                
				 <option value="">-Select-</option>
				 <option value="NotApplicable" {{codeType.name=="NotApplicable"? "selected": ""}}>NotApplicable</option>
				 <option value="AdministrativeSex" {{codeType.name=="AdministrativeSex"? "selected": ""}}>AdministrativeSex</option>                 
				 <option value="CDCREC" {{codeType.name=="CDCREC"? "selected": ""}}>CDCREC</option>
				 <option value="CDT" {{codeType.name=="CDT"? "selected": ""}}>CDT</option>
				 <option value="CPT" {{codeType.name=="CPT"? "selected": ""}}>CPT</option>
				 <option value="CVX" {{codeType.name=="CVX"? "selected": ""}}>CVX</option>
				 <option value="DischargeDisposition" {{codeType.name=="DischargeDisposition"? "selected": ""}}>DischargeDisposition</option>
				 <option value="HCPCS" {{codeType.name=="HCPCS"? "selected": ""}}>HCPCS</option>
				 <option value="HSLOC" {{codeType.name=="HSLOC"? "selected": ""}}>HSLOC</option>
				 <option value="ICD10CM" {{codeType.name=="ICD10CM"? "selected": ""}}>ICD10CM</option>
				 <option value="ICD10PCS" {{codeType.name=="ICD10PCS"? "selected": ""}}>ICD10PCS</option>
				 <option value="ICD9CM" {{codeType.name=="ICD9CM"? "selected": ""}}>ICD9CM</option>
				 <option value="LOINC" {{codeType.name=="LOINC"? "selected": ""}}>LOINC</option>
				 <option value="RXNORM" {{codeType.name=="RXNORM"? "selected": ""}}>RXNORM</option>
				 <option value="SNOMEDCT" {{codeType.name=="SNOMEDCT"? "selected": ""}}>SNOMEDCT</option>
				 <option value="SOP" {{codeType.name=="SOP"? "selected": ""}}>SOP</option>
    </select>
 </td>
 <td><input type="text" class="mnemonic" id="mnemonic" value="{{mnemonic}}" placeholder="Enter Mnemonic"></td>
 <td><div id="plus-btn" class="btn btn-mini"><i class="icon-plus"></i></div></td>
 <td><div id="minus-btn" class="btn btn-mini"><i class="icon-minus"></i></div></td>
</script>

<!-- qa -->
<script type="text/template" class="template" id="qa">
  <div rows="3" class="txt-qa input-xlarge" placeholder="Click on the Data Element to view its details"><p>{{notes}}</p></div>					
  <textarea rows="2" class="txt-message input-xlarge"></textarea><td class="f-btn"><a class= "btn send-btn" title="Add"><i class="icon-share"></i></a> 
</script>

<script type="text/template" class="template" id="hosp-spec-table">
<table id="hospital-specific-table" class="table table-bordered table-condensed">				
<thead>
	<tr>
		<th>
			Code
		</th>
		<th>
			Code Type
		</th>
		<th>
			Description
		</th>
		<th></th>
		<th></th>			
	</tr>					
</thead>
<tbody>					
</tbody>
</table>
</script>

<script type="text/template" class="template" id="extra-table-temp">
<table id="extra-table" class="table table-condensed table-bordered">				
				<thead>
        			<tr>
            			<th>Location</th>           
						<th title="SourceEHR">EHR</th>
						<th>Source EHR</th>
						<th>Code Type</th>
		    			<th>Values Type</th>		
						<th></th>
						<th></th>			
        			</tr>					
    			</thead>
    			<tbody>					
    			</tbody>
</table>
</script>
