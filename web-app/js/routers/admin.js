App.Routers.Administrator = Backbone.Router.extend({
	routes : {		
		'product'		  : 'products',
		'measure'         : 'measures',
		'element'         : 'dataElements',
		'hospital'        : 'hospitals',
		'ehr'        	  : 'ehrs',
		'reports'         : 'reports',
		
		'product/:new'    : 'newProduct',
		'measure/:new'    : 'newMeasure',
		'element/:new'    : 'newDataElement',
		'ehr/:new'        : 'newEhr',		
		
		'hospital/:id/edit': 'edithospital',
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
	},
	
	reopenHospital : function(id) {		
		Backbone.history.navigate("hospital/"+id+'/edit', true);		
	},
	
	elements : function(h_id,p_id, m_id){	
		App.ho = new App.Models.Hospital();		
		App.ho.fetch({data:{id: h_id}}).then(function(){
		
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
					"sScrollY": "220px",			
					"bSort": true,
					"bInfo": false,
					"aaSorting": [[0, 'asc']],
					"aoColumnDefs": [{'bSortable': false, 'aTargets': [ 1,2,3,4,5,6 ] }],					
					"bAutoWidth": false,
					"aoColumns" : [
					    null,
					    null,
					    null,                    
					    null,
					    null,
					    {"sWidth": "20px"},
					    null]					
				});				
				
				
			
				new FixedColumns( oTable, {"sHeightMatch": "none"} );			
				
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
						"sScrollY": "470px",
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
						"sScrollY": "520px",
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
						"sScrollY": "520px",
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
						"sScrollY": "520px",
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
						"sScrollY": "520px",
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
	
	// ----- display Edit/New 
    product : function (productModel) {
		App.measures.fetch().then(function(){			
			App.hospitals.fetch().then(function(){
				var view = new App.Views.Product({model:productModel});
				$('#app').html(view.render().el);					
				
				jQuery.validator.addMethod("unique", (function(value, element) {										
					return App.route.checkCode(productModel, App.products, value );					
					}), "This code name already exists in the system."
				);
				
				$('form#product-edit').validate({
				     rules: {
				   	     code: { required: true,
				   	    	     unique  : true
				   	    	   },
				         name: { required: true }	               
				     },
				     messages: {
				       	 code: {required: "ID is required",
				       	    	unique  : "This code name already exists in the system."			       	    	
				       	       },
				         name: {required: "Name is required"}
				     }
				});		
				
			});			
		});		
    },
    
    ehr : function (ehrModel) {
		App.hospitals.fetch().then(function(){	
			App.dataElements.fetch().then(function(){
				var view = new App.Views.Ehr({model:ehrModel});
				$('#app').html(view.render().el);				
				
				jQuery.validator.addMethod("unique", (function(value, element) {										
					return App.route.checkCode(ehrModel, App.ehrs, value );					
					}), "This code name already exists in the system."
				);
				
				$('form#ehr-edit').validate({
				     rules: {
				   	     code: { required: true,
				   	    	     unique  : true
				   	    	   },
				         name: { required: true }	               
				     },
				     messages: {
				       	 code: {required: "ID is required",
				       	        unique  : "This code name already exists in the system."			       	    	
				       	       },
				         name: {required: "Name and version are required"}
				     }
				});		
				
				$('.ehrTable').dataTable({
					"bPaginate": false,
					"bFilter": false,					
					"bSort": false,
					"bInfo": false,
					"bAutoWidth": false
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
					}), "This code name already exists in the system."
				);
				
				$('form#measure-edit').validate({
				     rules: {
				   	     code: { required: true,
				   	    	     unique  : true
				   	    	   },
				         name: { required: true }	               
				     },
				     messages: {
				       	 code: {required: "ID is required",
				       	    	unique  : "This code name already exists in the system."			       	    	
				       	       },
				         name: {required: "Name is required"}
				     }
				});						
				
			});			
			  
		});		
    },
          
    dataElement  : function (dataElement) {
		App.measures.fetch().then(function(){	
			App.ehrs.fetch().then(function(){
				var view = new App.Views.DataElement({model: dataElement});		
				$('#app').html(view.render().el);
				
				jQuery.validator.addMethod("unique", (function(value, element) {										
					return App.route.checkCode(dataElement, App.dataElements, value );					
					}), "This code name already exists in the system."
				);
				
				$('form#element-edit').validate({
				     rules: {
				   	     code: { required: true,
				   	    	     unique  : true
				   	    	   },
				         name: { required: true }	               
				     },
				     messages: {
				       	 code: {required: "ID is required",
				       	    	unique  : "This code name already exists in the system."			       	    	
				       	       },
				         name: {required: "Name is required"}
				     }
				});
				
				$('.ehrTable').dataTable({
					"bPaginate": false,
					"bFilter": false,					
					"bSort": false,
					"bInfo": false,
					"bAutoWidth": false
				 });			
				
			});
		});	
    },
    
    hospital  : function (hospital) {
		App.products.fetch().then(function(){	
		 App.ehrs.fetch().then(function(){		 
			var view = new App.Views.Hospital({model: hospital});		
			$('#app').html(view.render().el);			
				
				jQuery.validator.addMethod("emaillist", function(value, element) {
				        return  /^(\s*;?\s*[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\s*;?\s*){1,}$/im.test(value);
				    }, "Please specify at least one email address. <br> Separate multiple addresses with a semicolon."
				);
				
				$('form#hospital-edit').validate({
				     rules: {				   	    
				         email: { required: true,
				        	 	  emaillist: true
				        	 	}	               
				     },
				     messages: {
				    	 email: {required   : "Email is required.",
				    		 	 emaillist  : "Please specify at least one email address. <br> Separate multiple addresses with a semicolon"			       	    	
				       	       }				        
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
				
				$( "#slcPopulationMethod").multiselect({
					 multiple : false,
				     header : false,
				     noneSelectedText : "Select",
				     selectedList : 1,
				     height: "auto",
				     minWidth: "150px"
			    });
																				
				view.createTabs();
				view.setPrimaryEhr();
				
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
						"sScrollY": "272px",			
						"bSort": true,
						"bInfo": false,
						"aaSorting": [[0, 'asc'], [1, 'asc']],
						"aoColumns": [
							  			{ "sSortDataType": "dom-checkbox" },
							  			null,
							  			null,
							  			{ "sSortDataType": "dom-checkbox" },
							  			{ "sSortDataType": "dom-checkbox" },
							  			{ "sSortDataType": "dom-checkbox" },
							  			{ "sSortDataType": "dom-checkbox" }
							  		]						 
					 });				
								
					new FixedColumns( oTable, {"sHeightMatch": "none"} );
				}	
				
		});	
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
		App.element = new App.Models.DataElement();
		this.dataElement(App.element);		
	},

	// new EHR
	newEhr : function() {	
		App.ehr = new App.Models.Ehr();
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
			var new_code = value;
			var codes = [];
			 		
			if (model_to_check.toJSON().id) {
				cur_code = model_to_check.get('code');
			};
			
			collection_to_check.forEach(function(model){			
				codes.push(model.get('code'));
			});
			
			//var index = codes.indexOf(cur_code);			
			var index = _.indexOf(codes, cur_code)
			
			if (index!=-1) {
				codes.splice(index, 1);
			}
						
			//if (codes.indexOf(new_code)!=-1) {
			if (_.indexOf(codes, new_code)!=-1) {
				return false;
			}		
			return true;		
	 }

});
