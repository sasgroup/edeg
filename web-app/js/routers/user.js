App.Routers.User = Backbone.Router.extend({
	routes : {
		''											   : 'index',	
		'home/:h_id'     	                           : 'home',		
		'hospital/:h_id/product/:p_id/measure/:m_id'   : 'elements',
		'hospital/:h_id/product/:p_id'                 : 'productn'
	},

	
	initialize: function(options){
		App.route = this;
		App.hospitals = new App.Collections.Hospitals();		
		App.valuesTypes = new App.Collections.ValuesTypes();
		App.valuesType  = new App.Models.ValuesType();
		App.security = new App.Models.Security();
		App.viewHospitalElements = new App.Views.HospitalElements({isModified:false});
	},
	
	before: {	   		 
		 'hospital/' : function() {			 
			 if (App.viewHospitalElements.isModified) App.viewHospitalElements.showConfirm();
			 App.viewHospitalElements.isModified = false;
		 }			 
	},
	
	//get list of available Hospitals
	getListOfHospitals : function(){
		App.availableHospitals = App.security.get('availableHospitals');			
		var output = new Array();

		$.each(App.availableHospitals, function(key, value) {
			output.push({id: key, name: value});
		});
		output.sort(function(a,b) {

		    if(a.name > b.name) return 1;
		    if(a.name < b.name) return -1;
		    return 0;
		});

		$('ul#hospital-list-dropdown').empty();
		
		if (_.size(output) > 1) {
			for(var index in output) {
				$('ul#hospital-list-dropdown').append('<li data-id='+ output[index].id +' class="hospital" id='+ output[index].id+'><a href="#home/' + output[index].id + '">' + output[index].name+ '</a></li>');	
				$("a.btn.dropdown-toggle").removeAttr('disabled');
			}
		} else {
			//show hospital-name		
			if (_.size(output) == 1) { 
				$('h3.hospital-name').text(output[0].name);
			}	
			
			$("a.btn.dropdown-toggle").attr('disabled','disabled');
			$("#hospital-list-dropdown").hide();
		}			
	},
	
	// initial route, redirect to the current hospital 
	index : function(){
		App.security = new App.Models.Security();		
		App.security.fetch().then(function(){			
			var hospital_id = App.security.get('curHospitalId');
			var availableHospitals = App.security.get('availableHospitals');			
						
			if ((hospital_id==-1) &&(_.size(availableHospitals)>=1) ) {
				var hospitals = new Array();
				$.each(availableHospitals, function(key, value) {
					hospitals.push({id: key, name: value});
				});				
				hospital_id = hospitals[0].id;				
			}
						
			if ( hospital_id!=-1) {
				Backbone.history.navigate('/home/' + hospital_id , true);
			}
		});   
	},
		
	// render home page
	home : function(h_id){		
		 App.security.fetch().then(function(){						 
			App.route.getListOfHospitals();	 				
			$('#breadcrumb-box').empty();
			$('#app').empty();
					
			var ho = new App.Models.Hospital();		
			ho.fetch({data:{id: h_id}}).then(function(){
				var viewHome = new App.Views.Home({model:ho});
				$('#app').html(viewHome.render().el);
				
				// set hospital name
				$('h3.hospital-name').text(ho.get('name'));	
				
				// set tabs
				var products = ho.get('products');
				$('nav#products-nav').empty();
				$.each( products, function( i, product ) {		           
					$('nav#products-nav').append('<a href="#hospital/' + h_id + '/product/'+ product.id+ '">' + product.code + '</a>');					
				});		
			});			
		});	
	},
		
	// render nav-tabs for products
	tabs: function(h_id){		
		App.hosp_products = new App.Models.HospitalProduct();
		App.hosp_products.fetch({data:{h_id:h_id}}).then(function(){		
			var products = App.hosp_products.get('products');
			//generate tabs					
			$('nav#products-nav').empty();		
			$.each( products, function( i, product ) {		           
				$('nav#products-nav').append('<a href="#hospital/' + h_id + '/product/'+ product.id+ '">' + product.code + '</a>');
			});					
		});
	},

	// render form for hospitalMeasures
	productn : function(h_id,p_id) {		
		App.ho = new App.Models.Hospital();	
		App.cur_hosp_product = new App.Models.HospitalProduct();
		
		App.security.fetch().then(function(){						 
			App.route.getListOfHospitals();	 
			App.ho.fetch({data:{id: h_id}}).then(function(){
				App.cur_hosp_product.fetch({data:{p_id:p_id, h_id:h_id}}).then(function(){	
			  
				  var notifyUser = App.cur_hosp_product.get('notifyUser');		
				  App.hospital_products =  App.ho.get('products');	
				  App.route.tabs(h_id);	
				
				  $.each(App.ho.get('products'), function( i, product ) { 	
					if (product.id==p_id) {				
						//breadcrumb							
						var viewProductBreadcrumb = new App.Views.HospitalProductBreadcrumb({model: product, h_id:h_id, notifyUser:notifyUser});		
						$('#breadcrumb-box').html(viewProductBreadcrumb.render().el);
		                
						//content 																			
						var view = new App.Views.HospitalProduct({model: product, h_id:h_id});		
						$('#app').html(view.render().el);	
						return;
					}						       
				  });	
				
				 $.fn.dataTableExt.afnSortData['dom-checkbox'] = function  ( oSettings, iColumn )
				 {
				    var aData = [];
				    $( 'td:eq('+iColumn+') input', oSettings.oApi._fnGetTrNodes(oSettings) ).each( function () {
				        aData.push( this.checked==true ? "0" : "1" );
				    } );		    
				    
				    return aData;		    
				 }
						
				 var oTable = $('.hospitalMeasureTable').dataTable({		
					"bDestroy": true, 
					"bPaginate": false,
					"bSortClasses": false,
					"bFilter": true,
					"sScrollY": "528px",			
					"bSort": true,
					"bInfo": false,							
					"aoColumns": [
					  			{ "sSortDataType": "dom-checkbox" },
					  			null,
					  			null,
					  			null,
					  			{ "sSortDataType": "dom-checkbox" },
					  			{ "sSortDataType": "dom-checkbox" },
					  			{ "sSortDataType": "dom-checkbox" },
					  			{ "sSortDataType": "dom-checkbox" },
					  			{ "bSortable": false }
					  		]
				});				
				
				new FixedColumns( oTable, {"sHeightMatch": "none"} );
				
				$('.dataTables_scrollHeadInner').css('width', '899px');
				$('.hospitalMeasureTable.dataTable').css('width', '899px');
						
				$(".btn show_info").tooltip({
		             'selector': '',
		             'placement': 'left'
		           });				 
			});	
		});	
	});	
	},
			
	// render form for hospitalElements
	elements : function(h_id, p_id, m_id){        
        App.hpm = new App.Models.HospitalProductMeasure();        
        App.cur_measure = new App.Models.HospitalMeasure();
        App.h_id = h_id;
                             
        App.security.fetch().then(function(){						 
			App.route.getListOfHospitals();	 
			App.hpm.fetch({data:{h_id:h_id, p_id:p_id, m_id:m_id}}).then(function(){
				App.cur_measure.fetch({data:{id: m_id,hm: true}}).then(function(){                        
					App.valuesTypes.fetch().then(function(){              
               
                       var notifyUser = App.cur_measure.get('notifyUser');                                               
                       App.route.tabs(h_id);                
                       
                       var external_ehrs = [];
                       var primary_ehr="";
                       var measure_completed=false;
                                            
                       var product = App.hpm.get('products')[0];
                       var measure = product.measures[0];
                       measure_completed = measure.completed;                        
                       external_ehrs = App.hpm.get('externalEHRs').split('\n');
                       primary_ehr = App.hpm.get('ehr').code;                      
                                               
                       App.hospitalElements = new App.Collections.HospitalElements();
                       
                       App.hospitalElements.fetch({data:{id: m_id, h_id:h_id,p_id:p_id}}).then(function(){    
                    	      
                    	   	   var accessAllowed = App.hospitalElements.at(0).get('id');
                    	   	   if (typeof accessAllowed != 'undefined') {                    	        
                               
                               App.viewHospitalElements = new App.Views.HospitalElements ({collection:App.hospitalElements, m_id: m_id,product_id: p_id, external_ehrs:external_ehrs, primary_ehr:primary_ehr, measure_completed:measure_completed});                            
                               
                               $('#app').html(App.viewHospitalElements.render().el);
                              
                               var viewMeasureBreadcrumb = new App.Views.HospitalMeasureBreadcrumb({model: measure, product_code:product.code, product_id:p_id, hospital_id:h_id, notifyUser:notifyUser});                
                               $('#breadcrumb-box').html(viewMeasureBreadcrumb.render().el);
                              
                               
                               $('#deatails *').attr('disabled','disabled');
                               
                               var oTable = $('#hospital-elements').dataTable({                
                                       "bDestroy": true,
                                       "bPaginate": false,
                                       "bFilter": false,                                             
                                       "sScrollY": "220px",
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
                               
                       }      else {                     	   		
                    	   		Backbone.history.navigate('hospital/' + h_id + '/product/'+ p_id, true);				
                       }
                       });        
               
					});
				});
			});                
        });         
       }
});
