App.Routers.Administrator = Backbone.Router.extend({
	routes : {		
		
		''		      	  : 'index',
		'product'		  : 'products',
		'measure'         : 'measures',
		'element'         : 'dataElements',
		'hospital'        : 'hospitals',
		'ehr'        	  : 'ehrs',
		'reports'         : 'reports',
		'types'           : 'vtypes',
		
		'product/:new'    : 'newProduct',
		'measure/:new'    : 'newMeasure',
		'element/:new'    : 'newDataElement',
		'ehr/:new'        : 'newEhr',		
		
		'hospital/:id/edit':'edithospital',
		'product/:id/edit': 'editProduct',
		'measure/:id/edit': 'editMeasure',
		'element/:id/edit': 'editDataElement',
		'ehr/:id/edit'    : 'editEhr',
		'reload/:id'      :	'reopenHospital',
		
		'hospital/:h_id/product/:p_id/measure/:m_id':  'elements'
		
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
		
		
		App.productMeasures = new App.Collections.ProductMeasures();	
		
		App.hospitalMeasures = new App.Collections.HospitalMeasures();	
		
		App.viewHospital = new App.Views.Hospital({isModified:false});
		
		App.valuesTypes = new App.Collections.ValuesTypes();
		App.valuesType  = new App.Models.ValuesType();
	},
	
	before: {	    
	    'product$' : function() {	    		    		    	
	    	if (App.viewHospital.isModified) App.viewHospital.showConfirm();	    	
	     },
	     
	     'measure$' : function() {	    	
	    	 if (App.viewHospital.isModified) App.viewHospital.showConfirm();
		 },
		 
		 'element$' : function() {			 
			 if (App.viewHospital.isModified) App.viewHospital.showConfirm();
		 },
		 
		 'hospital$' : function() {			 	
			 if (App.viewHospital.isModified) App.viewHospital.showConfirm();
		 },
		 
		 'ehr$' : function() {			 
			 if (App.viewHospital.isModified) App.viewHospital.showConfirm();
		 },
		 
		 'reports$' : function() {			 
			 if (App.viewHospital.isModified) App.viewHospital.showConfirm();
		 },
		 
		 'types' : function() {			
			 if (App.viewHospital.isModified) App.viewHospital.showConfirm();
		 }
	},

		
	reopenHospital : function(id) {		
		Backbone.history.navigate("hospital/"+id+'/edit', true);		
	},
	
	index : function(){
		Backbone.history.navigate("/product", true);		
	},
	
	elements : function(h_id,p_id, m_id){        
	        App.hpm = new App.Models.HospitalProductMeasure();        
	        App.cur_measure = new App.Models.HospitalMeasure();
	                                
	        App.hpm.fetch({data:{h_id:h_id, p_id:p_id, m_id:m_id}}).then(function(){
	         App.cur_measure.fetch({data:{id: m_id,hm: true}}).then(function(){                        
	         App.valuesTypes.fetch().then(function(){                 
	        
	                var measure_code='';
	                var external_ehrs = [];
	                var primary_ehr="";
	                var measure_completed=false;
	                                        
	                var product = App.hpm.get('products')[0];
	         var measure = product.measures[0];
	         measure_completed = measure.completed;
	         measure_code = measure.code;
	                external_ehrs = App.hpm.get('externalEHRs').split('\n');
	                primary_ehr = App.hpm.get('ehr').code;
	                
	                App.hospitalElements = new App.Collections.HospitalElements();
	        
	                App.hospitalElements.fetch({data:{id: m_id}}).then(function(){                        
	                        App.viewHospitalElements = new App.Views.HospitalElements ({collection:App.hospitalElements, m_id: m_id, product_id: p_id, measure_code: measure_code, external_ehrs:external_ehrs, primary_ehr:primary_ehr, measure_completed:measure_completed});
	                        $('#app').html(App.viewHospitalElements.render().el);        
	
	                        $('#deatails *').attr('disabled','disabled');
	                
	                        var oTable = $('#hospital-elements').dataTable({                
	                                "bDestroy": true,
	                                "bPaginate": false,
	                                "bFilter": false,
	                                "sScrollY": "246px",                        
	                                "bSort": true,
	                                "bInfo": false,
	                                "aaSorting": [[0, 'asc']],
	                                "aoColumnDefs": [{'bSortable': false, 'aTargets': [ 1,2,3,4,5 ] }],                                        
	                                "bAutoWidth": false,
	                                "aoColumns" : [
	                                 {"sWidth": "20%"},
	                                 {"sWidth": "25%"},                                
	                                 {"sWidth": "20%"},        
	                                 {"sWidth": "25%"},
	                                 {"sWidth": "5%"},
	                                 {"sWidth": "5%"}]                                        
	                        });                                
	                                                
	                        new FixedColumns( oTable, {"sHeightMatch": "none"} );
	                                                                                        
	                        var notifyAdmin = App.cur_measure.get('notifyAdmin');
	                        if (notifyAdmin){
	                                         $('.admin-edit-notes').addClass('btn-info');
	                        }                                                
	                        
	         });                
	         });                
	        });        
	});
	},
	
	elementsOLD : function(h_id,p_id, m_id){        
	        App.ho = new App.Models.Hospital();                
	        App.cur_measure = new App.Models.HospitalMeasure();
	        App.ho.fetch({data:{id: h_id}}).then(function(){
	         App.cur_measure.fetch({data:{id: m_id,hm: true}}).then(function(){
	         App.valuesTypes.fetch().then(function(){        
	        
	                var measure_code='';
	                var external_ehrs = [];
	                var primary_ehr="";
	                var measure_completed=false;
	                
	                //get measure_code
	                $.each( App.ho.get('products'), function( i, product ) {         
	                        if (product.id==p_id) {
	                                $.each(product.measures, function( i, measure ){
	                                        if (measure.id==m_id) {
	                                                 measure_code = measure.code;
	                                                 measure_completed = measure.completed;
	                                                 external_ehrs = App.ho.get('externalEHRs').split('\n');
	                                                 primary_ehr = App.ho.get('ehr').code;
	                                        }
	                                });
	                        }
	                });
	                
	                App.hospitalElements = new App.Collections.HospitalElements();
	        
	                App.hospitalElements.fetch({data:{id: m_id}}).then(function(){                        
	                        App.viewHospitalElements = new App.Views.HospitalElements ({collection:App.hospitalElements, m_id: m_id, product_id: p_id, measure_code: measure_code, external_ehrs:external_ehrs, primary_ehr:primary_ehr, measure_completed:measure_completed});
	                        $('#app').html(App.viewHospitalElements.render().el);        
	
	                        $('#deatails *').attr('disabled','disabled');
	                
	                        var oTable = $('#hospital-elements').dataTable({                
	                                "bDestroy": true,
	                                "bPaginate": false,
	                                "bFilter": false,
	                                "sScrollY": "246px",                        
	                                "bSort": true,
	                                "bInfo": false,
	                                "aaSorting": [[0, 'asc']],
	                                "aoColumnDefs": [{'bSortable': false, 'aTargets': [ 1,2,3,4,5 ] }],                                        
	                                "bAutoWidth": false,
	                                "aoColumns" : [
	                                 {"sWidth": "20%"},
	                                 {"sWidth": "25%"},                                
	                                 {"sWidth": "20%"},        
	                                 {"sWidth": "25%"},
	                                 {"sWidth": "5%"},
	                                 {"sWidth": "5%"}]                                        
	                        });                                
	                                                
	                        new FixedColumns( oTable, {"sHeightMatch": "none"} );
	                                                                                        
	                        var notifyAdmin = App.cur_measure.get('notifyAdmin');
	                        if (notifyAdmin){
	                                         $('.admin-edit-notes').addClass('btn-info');
	                        }                                                
	                        
	         });                
	         });                
	        });        
	});
	},
	
	// ------- LIST ------------
	// list of products
	products : function() {
		App.products.fetch().then(function(){
			App.viewProducts = new App.Views.Products({collection:App.products});
			$('#app').html(App.viewProducts.render().el);		
			
			var oTable = $('#table_items').dataTable( 
					{
						"bPaginate": false,
						"bFilter": true,
						"sScrollY": "528px",
						"bSort": true,
			 			"bInfo": false,
			 			"bAutoWidth": false,
			 			"aoColumnDefs": [
			 							{ 'bSortable': false, 'aTargets': [ 2,3,4 ] }
			 						 ],
						"bScrollCollapse": true,
						"bPaginate": false
					} );
		   new FixedColumns( oTable,
					{ "sHeightMatch": "none"} );						
		});		
	},
	
	// list of measures
	measures : function() {		
		App.measures.fetch().then(function(){
			App.viewMeasures = new App.Views.Measures({collection:App.measures});
			$('#app').html(App.viewMeasures.render().el);
			
			var oTable = $('#table_items').dataTable( 
					{
						"bPaginate": false,
						"bFilter": true,
						"sScrollY": "528px",
						"bSort": true,
			 			"bInfo": false,
			 			"bAutoWidth": false,
			 			"aoColumnDefs": [
			 							{'bSortable': false, 'aTargets': [ 3, 4,5 ] }
			 						 ],
			 			"aaSorting": [[0, 'asc']],			 
						"bScrollCollapse": true						
					});
		   new FixedColumns( oTable,
					{ "sHeightMatch": "none"} );	
			
		});
	},	
	
	// list of elements
	dataElements : function() {		
		App.dataElements.fetch().then(function(){
			App.viewDataElements = new App.Views.DataElements({collection:App.dataElements});			
			$('#app').html(App.viewDataElements.render().el);
			
			var oTable = $('#table_items').dataTable( 
					{
						"bPaginate": false,
						"bFilter": true,
						"sScrollY": "528px",
						"bSort": true,
			 			"bInfo": false,
			 			"bAutoWidth": false,
			 			"aoColumnDefs": [
			 							{ 'bSortable': false, 'aTargets': [ 2,3,4 ] }
			 						 ],
						"bScrollCollapse": true,
						"bPaginate": false
					} );
		   new FixedColumns( oTable,
					{ "sHeightMatch": "none"} );						
		});
	},	
	
	// list of hospitals
	hospitals : function() {		
		App.hospitals.fetch().then(function(){
			App.viewHospitals = new App.Views.Hospitals({collection:App.hospitals});			
			$('#app').html(App.viewHospitals.render().el);
			
			var oTable = $('#table_items').dataTable( 
					{
						"bPaginate": false,
						"bFilter": true,
						"sScrollY": "528px",
						"bSort": true,
			 			"bInfo": false,
			 			"bAutoWidth": false,
			 			"aoColumnDefs": [
			 							{ 'bSortable': false, 'aTargets': [ -1 ] }
			 						 ],
						"bScrollCollapse": true,
						"bPaginate": false
					} );
		    new FixedColumns( oTable,
					{ "sHeightMatch": "none"} );		
		});
	},	
	
	// list of ehrs
	ehrs : function() {		
		App.ehrs.fetch().then(function(){			
			App.viewEhrs = new App.Views.Ehrs({collection:App.ehrs});			
			$('#app').html(App.viewEhrs.render().el);
			
			var oTable = $('#table_items').dataTable( 
					{
						"bPaginate": false,
						"bFilter": true,
						"sScrollY": "528px",
						"bSort": true,
			 			"bInfo": false,
			 			"bAutoWidth": false,
			 			"aoColumnDefs": [
			 							{ 'bSortable': false, 'aTargets': [ 2,3,4 ] }
			 						 ],
						"bScrollCollapse": true,
						"bPaginate": false
					} );
		   new FixedColumns( oTable,
					{ "sHeightMatch": "none"} );		
		});		
	},	
	
	// reports
	reports : function() {	
		App.dataElements.fetch().then(function(){
			App.measures.fetch().then(function(){
				App.products.fetch().then(function(){
					App.hospitals.fetch().then(function(){
						var view = new App.Views.Reports({model:{H:App.hospitals,P:App.products,M:App.measures,E:App.dataElements} });
						$('#app').html(view.render().el);	
					});
				});
			});
		});
	},	
	
	// vtypes
	vtypes : function() {
		var temp = _.template($('#vtype-template').html());
		$('#app').html(temp);
		
		App.valuesType  = new App.Models.ValuesType();
		App.valuesTypes = new App.Collections.ValuesTypes();
				
		App.viewValuesType = new App.Views.ValuesType({model:App.valuesType});
		$('#input_form').html(App.viewValuesType.render().el);
		
		App.route.validateValuesTypeForm();		
		
		App.valuesTypes.fetch().then(function(){
						
			App.viewValuesTypes = new App.Views.ValuesTypes({collection:App.valuesTypes});
			$('#list_form').html(App.viewValuesTypes.render().el);		
			
			var oTable = $('#table_items').dataTable( 
					{	"bDestroy": true,
						"bPaginate": false,
						"bFilter": false,
						"sScrollY": "528px",
						"bSort": true,
			 			"bInfo": false,
			 			"bAutoWidth": false,
			 			"aoColumnDefs": [
			 							{ 'bSortable': false, 'aTargets': [ 2,3 ] }
			 						 ],
						"bScrollCollapse": true,
						"bPaginate": false
					} );
		    new FixedColumns( oTable,
					{ "sHeightMatch": "none"} );						
		});	
		
	},	
	
	// ----- display Edit/New 
    product : function (productModel) {
		App.measures.fetch().then(function(){			
			App.hospitals.fetch().then(function(){
				var view = new App.Views.Product({model:productModel});
				$('#app').html(view.render().el);					
				
				jQuery.validator.addMethod("unique", (function(value, element) {										
					return App.route.checkCode(productModel, App.products, value );					
					}), "This ID already exists in the system."
				);
				
				$('form#product-edit').validate({
				     rules: {
				   	     code: { required: true,
				   	    	     unique  : true
				   	    	   },
				         name: { required: true }	               
				     },
				     messages: {
				       	 code: {required: "ID is required.",
				       	    	unique  : "This ID already exists in the system."			       	    	
				       	       },
				         name: {required: "Name is required."}
				     }
				});		
				
			});			
		});		
    },
    
    ehr : function (ehrModel) {
		App.hospitals.fetch().then(function(){	
			App.dataElements.fetch().then(function(){
			  App.valuesTypes.fetch().then(function(){	
				var view = new App.Views.Ehr({model:ehrModel});
				$('#app').html(view.render().el);				
				
				jQuery.validator.addMethod("unique", (function(value, element) {										
					return App.route.checkCode(ehrModel, App.ehrs, value );					
					}), "This ID already exists in the system."
				);
				
				$('form#ehr-edit').validate({
				     rules: {
				   	     code: { required: true,
				   	    	     unique  : true
				   	    	   },
				         name: { required: true }	               
				     },
				     messages: {
				       	 code: {required: "ID is required.",
				       	        unique  : "This ID already exists in the system."			       	    	
				       	       },
				         name: {required: "Name and version are required."}
				     }
				});		
				
				$('.ehrTable').dataTable({
					"bPaginate": false,
					"bFilter": false,					
					"bSort": false,
					"bInfo": false,
					"bAutoWidth": false,
					"aoColumns": [
					              { "sWidth": "30%" },
					              { "sWidth": "30%" },
					              { "sWidth": "30%" },
					              { "sWidth": "5%" },
					              { "sWidth": "5%" }
					          ]
				});		
				
				$( ".slcValuesType").multiselect({
			        multiple : true,
			        header : true,
			        noneSelectedText : "Select",
			        selectedList : 1,
			        height: "auto"			        
			    });	
			 });
		  });	
		});		
    },
    measure : function (measureModel) {
		App.products.fetch().then(function(){			
			App.dataElements.fetch().then(function(){
				var view = new App.Views.Measure({model: measureModel});		
				$('#app').html(view.render().el); 
				
				jQuery.validator.addMethod("unique", (function(value, element) {										
					return App.route.checkCode(measureModel, App.measures, value );					
					}), "This ID already exists in the system."
				);
				
				$('form#measure-edit').validate({
				     rules: {
				   	     code: { required: true,
				   	    	     unique  : true
				   	    	   },
				         name: { required: true }	               
				     },
				     messages: {
				       	 code: {required: "ID is required.",
				       	    	unique  : "This ID already exists in the system."			       	    	
				       	       },
				         name: {required: "Name is required."}
				     }
				});						
				
			});			
			  
		});		
    },
          
    dataElement  : function (dataElement) {
		App.measures.fetch().then(function(){	
			App.ehrs.fetch().then(function(){
				App.valuesTypes.fetch().then(function(){	
				var view = new App.Views.DataElement({model: dataElement});		
				$('#app').html(view.render().el);
				
				jQuery.validator.addMethod("unique", (function(value, element) {										
					return App.route.checkCode(dataElement, App.dataElements, value );					
					}), "This ID already exists in the system."
				);
				
				$('form#element-edit').validate({
				     rules: {
				   	     code: { required: true,
				   	    	     unique  : true
				   	    	   },
				         name: { required: true }	               
				     },
				     messages: {
				       	 code: {required: "ID is required.",
				       	    	unique  : "This ID already exists in the system."			       	    	
				       	       },
				         name: {required: "Name is required."}
				     }
				});
				
				$('.ehrTable').dataTable({
					"bPaginate": false,
					"bFilter": false,					
					"bSort": false,
					"bInfo": false,
					"bAutoWidth": false,
					"aoColumns": [
					              { "sWidth": "30%" },
					              { "sWidth": "30%" },
					              { "sWidth": "30%" },
					              { "sWidth": "5%" },
					              { "sWidth": "5%" }
					          ]
				});	
										
				
				$( ".slcValuesType").multiselect({
			        multiple : true,
			        header : true,
			        noneSelectedText : "Select",
			        selectedList : 1,
			        height: "auto"			        
			    });				
				
			  });	
			});
		});	
    },
    
    hospital  : function (hospital) {
		App.products.fetch().then(function(){	
		 App.ehrs.fetch().then(function(){			 
		  App.hospitals.fetch().then(function(){			 
			 
			App.viewHospital = new App.Views.Hospital({model: hospital});		
			$('#app').html(App.viewHospital.render().el);			
				
				jQuery.validator.addMethod("emaillist", function(value, element) {
				        //return  /^(\s*;?\s*[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\s*;?\s*){1,}$/im.test(value);
				        
				        //return this.optional(element) || /^(\s*;?\s*[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\s*;?\s*){1,}$/im.test(value);
				        return this.optional(element) || /^(\s*,?\s*[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\s*,?\s*){1,}$/im.test(value);
				        
				    }, "Please specify at least one email address. <br> Separate multiple recipients by commas."
				);
				
				$('form#hospital-edit').validate({
				     rules: {				   	    
				         email: {emaillist: true}	               
				     },
				     messages: {
				    	 email: { emaillist  : "Please specify at least one email address. <br> Separate multiple recipients by commas."}				        
				     }
				});		
										
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
				
				$( "#slcHospitals").multiselect({
			        multiple : false,
			        header : false,
			        noneSelectedText : "Select",
			        selectedList : 1,
			        height: "370px",
			        minWidth: "300px"
			    });	
				
				$( "#slcPopulationMethod").multiselect({
					 multiple : false,
				     header : false,
				     noneSelectedText : "Select",
				     selectedList : 1,
				     height: "auto",
				     minWidth: "150px"
			    });
																				
				App.viewHospital.createTabs();
				App.viewHospital.setPrimaryEhr();
				
				$.fn.dataTableExt.afnSortData['dom-checkbox'] = function  ( oSettings, iColumn )
				{
				    var aData = [];
				    $( 'td:eq('+iColumn+') input', oSettings.oApi._fnGetTrNodes(oSettings) ).each( function () {
				        aData.push( this.checked==true ? "0" : "1" );
				    } );				   
				    return aData;		    
				}
				
				if ($.cookie("active_tab")) {		
					var href= '#t'+ $.cookie("active_tab");
					$('ul#myTab a[href=' + href +']').click();
				}
				else {
								
				var oTable = $(".hospitalMeasureTable:first").dataTable({
						"bDestroy": true, 
						"bPaginate": false,
						"bSortClasses": false,
						"bFilter": false,
						"sScrollY": "292px",			
						"bSort": true,
						"bInfo": false,
						"aaSorting": [[0, 'asc'], [1, 'asc'], [3, 'asc']],
						"bAutoWidth": false,
						"aoColumns": [
							  			{ "sSortDataType": "dom-checkbox" },
							  			null,
							  			{"sWidth": "73px" },
							  			{"sWidth": "336px" },
							  			{ "sSortDataType": "dom-checkbox", "sWidth": "60px" },
							  			{ "sSortDataType": "dom-checkbox", "sWidth": "60px" },
							  			{ "sSortDataType": "dom-checkbox", "sWidth": "50px" },
							  			{ "sSortDataType": "dom-checkbox", "sWidth": "50px" }
							  		]						 
					 });				
								
					new FixedColumns( oTable, {"sHeightMatch": "none"} );				
				}	
		 });		
		});	
	  });	
		
    },
	// ------- NEW ------------
    // new product
	newProduct : function() {			
		this.product(new App.Models.Product({measures:[], hospitals:[]}));
	},
	
	// new measure
	newMeasure : function() {		
		this.measure(new App.Models.Measure({products:[], dataElements:[]}));	
	},

	// new dataElement
	newDataElement : function() {	
		App.element = new App.Models.DataElement({measures:[], dataElementDefaults:[]});
		this.dataElement(App.element);		
	},

	// new EHR
	newEhr : function() {	
		App.ehr = new App.Models.Ehr({hospitals:[], dataElementDefaults:[]});
		this.ehr(App.ehr);
	},	
		
	
	// ------- EDIT ------------
	// edit hospital
	edithospital : function(id) {
		App.ho = new App.Models.Hospital();		
		App.ho.fetch({data:{id: id}}).then(function(){			
			App.route.hospital(App.ho);
		})
	},
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
		App.element = new App.Models.DataElement();
		App.element.fetch({data:{id: id}}).then(function(){
			App.route.dataElement(App.element);
		})			
	},

	// edit ehr
	editEhr : function(id) {
		App.ehr = new App.Models.Ehr();
		App.ehr.fetch({data:{id: id}}).then(function(){
			App.route.ehr(App.ehr);
		})
	},
	
	// check code uniqueness
	checkCode : function(model_to_check, collection_to_check, value) {
	    	var cur_code = '';
			var new_code = value.toUpperCase();
			var codes = [];
			 		
			if (model_to_check.toJSON().id) {
				cur_code = model_to_check.get('code');
				cur_code = cur_code.toUpperCase();
			};
			
			collection_to_check.forEach(function(model){			
				var c = model.get('code');
				c = c.toUpperCase();
				codes.push(c);
			});
			
			var index = _.indexOf(codes, cur_code)
			
			if (index!=-1) {
				codes.splice(index, 1);
			}
						
			if (_.indexOf(codes, new_code)!=-1) {
				return false;
			}		
			return true;		
	 },
	 
	// check name uniqueness
	checkName : function(model_to_check, collection_to_check, value) {
		    	var cur_name = '';
				var new_name = value.toUpperCase();
				var names = [];
				 		
				if (model_to_check.toJSON().id) {
					cur_name = model_to_check.get('name');
					cur_name = cur_name.toUpperCase();
				};
				
				collection_to_check.forEach(function(model){			
					var c = model.get('name');
					c = c.toUpperCase();
					names.push(c);
				});
				
				var index = _.indexOf(names, cur_name)
				
				if (index!=-1) {
					names.splice(index, 1);
				}
							
				if (_.indexOf(names, new_name)!=-1) {
					return false;
				}		
				return true;		
	 },
	 
	 validateValuesTypeForm : function() {
			jQuery.validator.addMethod("unique", (function(value, element) {										
				return App.route.checkName(App.valuesType, App.valuesTypes, value );					
				}), "This Name already exists in the system."
			);
			
			$('form#form-vtype-edit').validate({
			     rules: {		   	    
			         name: { required: true,
			        	     unique  : true }	               
			     },
			     messages: {		       	 
			         name: {required: "Name is required.",
			        	    unique  : "This Name already exists in the system."}
			     }
			});		
	 }
});
