App.Router = Backbone.Router.extend({
	routes : {
		''                : 'products',
		'product'		  : 'products',
		'measure'         : 'measures',
		'element'         : 'dataElements',
		'hospital'        : 'hospitals',
		'ehr'        	  : 'ehrs',
		
		'product/:new'    : 'newProduct',
		'measure/:new'    : 'newMeasure',
		'element/:new'    : 'newDataElement',
		'ehr/:new'        : 'newEhr',
		'hospital/:new'   : 'newHospital',
		
		'product/:id/edit': 'editProduct',
		'measure/:id/edit': 'editMeasure',
		'element/:id/edit': 'editDataElement',
		'ehr/:id/edit'    : 'editEhr'
	},

	initialize: function(options){
		App.route = this;
		App.products = new App.Collections.Products();			
		App.measures = new App.Collections.Measures();
		App.dataElements = new App.Collections.DataElements();
		App.hospitals = new App.Collections.Hospitals();
		App.ehrs = new App.Collections.Ehrs();	
		
		App.measureCategories  = new App.Collections.MeasureCategories();
		App.measureCategories.fetch();
		
		App.cqmDomains         = new App.Collections.CqmDomains();
		App.cqmDomains.fetch();
	},
	jqGrid : function(){
		// jqGrid                
		App.dataElementsTable = jQuery("#dataElementsTable").jqGrid({ 
		    datatype: 'local',		   
		    width:'100%',
		    colNames:['isIMO', 'location', 'queryMnemonic', 'valueSet', 'valueSetRequired', 'locationtype'], 
		    colModel:[  {name:'isIMO', index:'isIMO'},
		                {name:'location', index:'location'},
		                {name:'queryMnemonic', index:'queryMnemonic'},
		                {name:'valueSet', index:'valueSet'},
		                {name:'valueSetRequired',index:'valueSetRequired'},
		                {name:'locationtype', index:'locationtype'}],				                
		                
		    rowNum:10, 
		    rowList:[10,20,30], 
		    pager: '#pager5', 
		    sortname: 'location', 
		    viewrecords: true, 
		    sortorder: "desc", 
		    				             
		    loadComplete : function(data) {
		        //alert('grid loading completed ' + data);
		    },
		    loadError : function(xhr, status, error) {
		        alert('grid loading error' + error);
		    }
		});
	   // jqGrid		
	},
	// ------- LIST ------------
	// list of products
	products : function() {
		App.products.fetch().then(function(){
			App.viewProducts = new App.Views.Products({collection:App.products});
			$('#app').html(App.viewProducts.render().el);
		});		
	},
	
	// list of measures
	measures : function() {		
		App.measures.fetch().then(function(){
			App.viewMeasures = new App.Views.Measures({collection:App.measures});
			$('#app').html(App.viewMeasures.render().el);
		});
	},	
	
	// list of elements
	dataElements : function() {		
		App.dataElements.fetch().then(function(){
			App.viewDataElements = new App.Views.DataElements({collection:App.dataElements});			
			$('#app').html(App.viewDataElements.render().el);
		});
	},	
	
	// list of hospitals
	hospitals : function() {		
		App.hospitals.fetch().then(function(){
			App.viewHospitals = new App.Views.Hospitals({collection:App.hospitals});			
			$('#app').html(App.viewHospitals.render().el);
		});
	},	
	
	// list of ehrs
	ehrs : function() {		
		App.ehrs.fetch().then(function(){
			console.log ();
			App.viewEhrs = new App.Views.Ehrs({collection:App.ehrs});			
			$('#app').html(App.viewEhrs.render().el);
		});		
	},	
	
	// ----- display Edit/New 
    product : function (productModel) {
		App.measures.fetch().then(function(){			
			App.hospitals.fetch().then(function(){
				var view = new App.Views.Product({model:productModel});
				$('#app').html(view.render().el);
			});			
		});		
    },
    
    ehr : function (ehrModel) {
		App.hospitals.fetch().then(function(){			
			var view = new App.Views.Ehr({model:ehrModel});
			$('#app').html(view.render().el);
			App.route.jqGrid();					
			view.appendDataElements();
		});		
    },
    measure : function (measureModel) {
		App.products.fetch().then(function(){			
			App.dataElements.fetch().then(function(){
				var view = new App.Views.Measure({model: measureModel});		
				$('#app').html(view.render().el); 
			});			
			  
		});		
    },
    dataElement  : function (dataElement) {
		App.measures.fetch().then(function(){	
			var view = new App.Views.DataElement({model: dataElement});		
			$('#app').html(view.render().el);
			App.route.jqGrid();					
			view.appendDataElements();
		});	
    },
	// ------- NEW ------------
    // new product
	newProduct : function() {			
		this.product(new App.Models.Product());
	},
	
	// new measure
	newMeasure : function() {		
		this.measure(new App.Models.Measure());	
	},

	// new dataElement
	newDataElement : function() {				
		this.dataElement(new App.Models.DataElement());		
	},

	// new EHR
	newEhr : function() {	
		this.ehr(new App.Models.Ehr());
	},	
	
	// new hospital
	newHospital : function() {		
		App.hospital = new App.Models.Hospital();
		var view = new App.Views.Hospital({model:App.hospital});
		$('#app').html(view.render().el);		
		$(document).ready(function(){
			$( "#slcEHRs").multiselect({
		        multiple : false,
		        header : false,
		        noneSelectedText : "Select",
		        selectedList : 1,
		        height: "auto",
		        minWidth: "300px"
		    });	

			$( "#slcProducts").multiselect({
		        multiple : true,
		        header : true,
		        noneSelectedText : "Select",
		        selectedList : 1,
		        height: "auto",
		        minWidth: "300px"
		    });	  

		    $("#btnApplyHospitalOptions").click(function(){	    	

		    	// remove tabs
		    	$("div#myTabContent").empty();
		    	$("ul#myTab").empty();

		    	$( "#slcProducts").multiselect('getChecked').each(function( index ) {  					
  					var tabName = $(this).val();

  					$('div#myTabContent').append('<div id="'+tabName+'" class="tab-pane fade"><table id="dataElementsTable"></table></div>');
					$('ul#myTab').append('<li class=""><a data-toggle="tab" href="#' + tabName + '">'+ tabName + '</a></li>'); 

					var slcTab = '#myTabContent div#' + tabName + ' #dataElementsTable';
					console.log(slcTab);

					App.dataElementsTable = $(slcTab).jqGrid({ 
					    datatype: 'local',		   
					    width:'100%',		
					    height: 250,			    
					    colNames:['ID','CODE', 'TITLE', 'NOTES','Use','Completed','Confirmed','Accepted','Verified'],
					    colModel:[
	                        {name:'id',index:'id', width:60, sorttype:"int"},
	                        {name:'code',index:'code', width:100, sorttype:"date"},
	                        {name:'name',index:'name', width:200},
	                        {name:'note',index:'note', width:150},
	                        {name:'included',index:'included', width:70, edittype:"checkbox",editoptions: {value:"Yes:No"}},
	                        {name:'completed',index:'completed', width:70, edittype:"checkbox",editoptions: {value:"Yes:No"}},
	                        {name:'confirmed',index:'confirmed', width:70, edittype:"checkbox",editoptions: {value:"Yes:No"}},
	                        {name:'accepted',index:'accepted', width:70, edittype:"checkbox",editoptions: {value:"Yes:No"}},
	                        {name:'verified',index:'verified', width:70, edittype:"checkbox",editoptions: {value:"Yes:No"}}
                    	],		
                    	            
					                
					    rowNum:10, 
					    rowList:[10,20,30], 					   
					    sortname: 'location', 
					    viewrecords: true, 
					    sortorder: "desc", 
					    				             
					    loadComplete : function(data) {
					        //alert('grid loading completed ' + data);
					    },
					    loadError : function(xhr, status, error) {
					        alert('grid loading error' + error);
					    },

					    onSelectRow: function(id){
                			//$( "#dialog" ).dialog();
                			console.log('Select row');
           				},
            			
            			multiselect: false            			
					});


				});	

		    	// set active tab
				$('div#myTabContent div').first().addClass('active in');
				$('ul#myTab li').first().addClass('active');

				//load values
				var path = '/ihm/api/measure';

		
			    $.getJSON(path, function(data){
			    	$.each( data.measures, function( i, measure ) {
			    		console.log(measure);
			    		//MU1
			    		//$('#myTabContent div# #MU1 #dataElementsTable').jqGrid('addRowData',i+1,mydata2[i]);
			    		var json_data = JSON.stringify(measure);
						console.log(json_data);
						$('#myTabContent div#MU1 #dataElementsTable').jqGrid('addRowData', (i + 1), {id:measure.id, 
							   code:measure.code,
							   name:measure.name});
						
						});	

	    	
			    });		

				//});
				
               
            	}
        	);
			   
		});

		/*var mydata = [
            {id:"1",code:"CPOE-Meds",name:"CPOE - Medications",note:"notes about CPOE - Meds",included:"Yes",completed:"Yes",confirmed:"Yes",accepted:"No",verified:"No"},
            {id:"2",code:"CPOE-Labs",name:"CPOE - Laboratory",note:"notes about CPOE - Labs",included:"Yes",completed:"Yes",confirmed:"Yes",accepted:"No",verified:"No"},
            {id:"3",code:"CPOE-Rad",name:"CPOE - Radiology",note:"notes about CPOE - Rad",included:"Yes",completed:"Yes",confirmed:"Yes",accepted:"No",verified:"No"},
            {id:"4",code:"VTE-4",name:"Venous ThromboEmbolism - 4",note:"notes about VTE-4",included:"Yes",completed:"Yes",confirmed:"Yes",accepted:"No",verified:"No"},
            {id:"5",code:"VTE-6",name:"Venous ThromboEmbolism - 6",note:"notes about VTE-6",included:"Yes",completed:"Yes",confirmed:"Yes",accepted:"No",verified:"No"},
            {id:"6",code:"STK-5",name:"Stroke - 5",note:"notes about Stroke 5 ",included:"Yes",completed:"Yes",confirmed:"Yes",accepted:"No",verified:"No"},
            {id:"7",code:"STK-6",name:"Stroke - 6",note:"notes about Stroke 6",included:"Yes",completed:"Yes",confirmed:"Yes",accepted:"No",verified:"No"},
            {id:"8",code:"PN-6",name:"Pneumonia",note:"notes about Pneumonia",included:"Yes",completed:"Yes",confirmed:"Yes",accepted:"No",verified:"No"},
            {id:"9",code:"Smoking",name:"Smoking",note:"notes about Smoking",included:"Yes",completed:"Yes",confirmed:"Yes",accepted:"No",verified:"No"},
            {id:"9",code:"Smoking",name:"Smoking",note:"notes about Smoking",included:"Yes",completed:"Yes",confirmed:"Yes",accepted:"No",verified:"No"},
            {id:"9",code:"Smoking",name:"Smoking",note:"notes about Smoking",included:"Yes",completed:"Yes",confirmed:"Yes",accepted:"No",verified:"No"},
            {id:"9",code:"Smoking",name:"Smoking",note:"notes about Smoking",included:"Yes",completed:"Yes",confirmed:"Yes",accepted:"No",verified:"No"},
            {id:"9",code:"Smoking",name:"Smoking",note:"notes about Smoking",included:"Yes",completed:"Yes",confirmed:"Yes",accepted:"No",verified:"No"}
        ];


        var mydata2 = [
            {id:"1",code:"Admission Date",type:"Internal",locationSystem:"Meditech",location:"ADM.PAT.admit.date",valueType:"Standard",codeType:"SNOMED"},
            {id:"2",code:"Admission Time",type:"Internal",locationSystem:"Meditech",location:"ADM.PAT.admit.time",valueType:"Standard",codeType:"LOINC"},
            {id:"3",code:"Discharge Date",type:"Internal",locationSystem:"Meditech",location:"ADM.PAT.discharge.date",valueType:"Standard",codeType:"ICD-9"},
            {id:"4",code:"Discharge Time",type:"Internal",locationSystem:"Meditech",location:"ADM.PAT.discharge.time",valueType:"Standard",codeType:"RXNORM"},
            {id:"5",code:"Diagnosis Code",type:"Internal",locationSystem:"Meditech",location:"ABS.PAT.dx",valueType:"Standard",codeType:"SNOMED"},
            {id:"6",code:"Patient Encounter Number",type:"Internal",locationSystem:"Meditech",location:"ABS.PAT.account.number",valueType:"Standard",codeType:"SNOMED"},
            {id:"7",code:"Patient Medical Record number",type:"Internal",locationSystem:"Meditech",location:"ABS.PAT.unit.number",valueType:"Standard",codeType:"SNOMED"},

        ];

        $("#list5").jqGrid({
            datatype: "local",
            height: 250,
            colNames:['ID','CODE', 'TYPE', 'LOCATION SYSTEM','LOCATION','VALUE TYPE','CODE TYPE'],
            colModel:[
                {name:'id',index:'id', width:60, sorttype:"int"},
                {name:'code',index:'code', width:90, sorttype:"date"},
                {name:'type',index:'type', width:90},
                {name:'locationSystem',index:'locationSystem', width:100},
                {name:'location',index:'location', width:180, edittype:"checkbox",editoptions: {value:"Yes:No"}},
                {name:'valueType',index:'valueType', width:180, edittype:"checkbox",editoptions: {value:"Yes:No"}},
                {name:'codeType',index:'codeType', width:180, edittype:"checkbox",editoptions: {value:"Yes:No"}}
            ],
            onSelectRow: function(id){
                $( "#dialog" ).dialog();
            },
            multiselect: false //, caption: "Data Element associated with Measure"
        });

        for(var i=0;i<=mydata2.length;i++)
            $("#list5").jqGrid('addRowData',i+1,mydata2[i]);

        $("#list1,#list2,#list3").each(function(j,e){
                var p = "";
                switch (j){
                    case 0: p = "MU1";break;
                    case 1: p = "MU2";break;
                    case 2: p = "MU3";break;
                }
                $(e).jqGrid({
                    datatype: "local",
                    height: 250,
                    colNames:['ID','CODE', 'TITLE', 'NOTES','Use','Completed','Confirmed','Accepted','Verified'],
                    colModel:[
                        {name:'id',index:'id', width:60, sorttype:"int"},
                        {name:'code',index:'code', width:90, sorttype:"date"},
                        {name:'name',index:'name', width:300},
                        {name:'note',index:'note', width:300},
                        {name:'included',index:'included', width:80, edittype:"checkbox",editoptions: {value:"Yes:No"}},
                        {name:'completed',index:'completed', width:80, edittype:"checkbox",editoptions: {value:"Yes:No"}},
                        {name:'confirmed',index:'confirmed', width:80, edittype:"checkbox",editoptions: {value:"Yes:No"}},
                        {name:'accepted',index:'accepted', width:80, edittype:"checkbox",editoptions: {value:"Yes:No"}},
                        {name:'verified',index:'verified', width:80, edittype:"checkbox",editoptions: {value:"Yes:No"}}
                    ],
                    onSelectRow: function(id){
                        $( "#dialog" ).dialog({width: 1000});
                        $( "#divDataElementsOptions").show();
                    },
                    multiselect: false//, caption: "Measures associated with " + p
                });

                for(var i=j;i<=mydata.length;i++)
                    $(e).jqGrid('addRowData',i+1,mydata[i]);
            }
        )*/
	},
	
	
	// ------- EDIT ------------
	// edit product
	editProduct : function(id) {
		App.pr = new App.Models.Product();
		App.pr.fetch({data:{id: id}}).then(function(){
			App.route.product(App.pr);
		})
	},

	 // edit measure
	editMeasure : function(id) {
		App.me = new App.Models.Measure();
		App.me.fetch({data:{id: id}}).then(function(){
			App.route.measure(App.me);
		})
	},

	// edit dataElement
	editDataElement : function(id) {
		App.de = new App.Models.DataElement();
		App.de.fetch({data:{id: id}}).then(function(){
			App.route.dataElement(App.de);
		})			
	},

	// edit ehr
	editEhr : function(id) {
		App.ehr = new App.Models.Ehr();
		App.ehr.fetch({data:{id: id}}).then(function(){
			App.route.ehr(App.ehr);
		})
	}

});