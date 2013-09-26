App.Routers.User = Backbone.Router.extend({
	routes : {
		"home/:h_id"     	                           : 'home',		
		'hospital/:h_id/product/:p_id/measure/:m_id'   : 'elements',
		'hospital/:h_id/product/:p_id'                 : 'productn'
	},

	initialize: function(options){
		App.hospitals = new App.Collections.Hospitals();		
		App.route = this;
		App.viewHospitalElements = new App.Views.HospitalElements({isModified:false});
	},
	
	before: {	   		 
		 'hospital/' : function() {
			 if (window.console) console.log("before go to hospital/:id/product/:id/");	
			 console.log(App.viewHospitalElements.isModified);
			 if (App.viewHospitalElements.isModified) App.viewHospitalElements.showConfirm();
			 App.viewHospitalElements.isModified = false;
		 }			 
	},
		
	home : function(h_id){
		$('#breadcrumb-box').empty();
		$('#app').empty();
				
		var ho = new App.Models.Hospital();		
		ho.fetch({data:{id: h_id}}).then(function(){
			var viewHome = new App.Views.Home({model:ho});
			$('#app').html(viewHome.render().el);			
		});		
	},
		
	tabs: function(h_id){		
		//generate tabs					
		$('nav#products-nav').empty();		
		$.each( App.hospital_products, function( i, product ) {		           
			$('nav#products-nav').append('<a href="#hospital/' + h_id + '/product/'+ product.id+ '">' + product.code + '</a>');
		});			
	},

	productn : function(h_id,p_id) {		
		App.ho = new App.Models.Hospital();		
		App.ho.fetch({data:{id: h_id}}).then(function(){
		
		  App.hospital_products =  App.ho.get('products');	
		  App.route.tabs(h_id);	
		
		  $.each(App.ho.get('products'), function( i, product ) { 	
			if (product.id==p_id) {				
				//breadcrumb							
				var viewProductBreadcrumb = new App.Views.HospitalProductBreadcrumb({model: product, h_id:h_id});		
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
			"sScrollY": "548px",			
			"bSort": true,
			"bInfo": false,
			"aaSorting": [[0, 'asc'], [2, 'asc']],			
			"aoColumns": [
			  			{ "sSortDataType": "dom-checkbox" },
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
		$('.hospitalMeasureTable.dataTable td:eq(0), .hospitalMeasureTable.dataTable th:eq(0)').css('width', '30px');
		$('.hospitalMeasureTable.dataTable td:eq(1), .hospitalMeasureTable.dataTable th:eq(1)').css('width', '50px');
		$('.hospitalMeasureTable.dataTable td:eq(2), .hospitalMeasureTable.dataTable th:eq(2)').css('width', '260px');
		$('.hospitalMeasureTable.dataTable td:eq(3), .hospitalMeasureTable.dataTable th:eq(3)').css('width', '70px');
		$('.hospitalMeasureTable.dataTable td:eq(4), .hospitalMeasureTable.dataTable th:eq(4)').css('width', '70px');
		$('.hospitalMeasureTable.dataTable td:eq(5), .hospitalMeasureTable.dataTable th:eq(5)').css('width', '95px');
		$('.hospitalMeasureTable.dataTable td:eq(6), .hospitalMeasureTable.dataTable th:eq(6)').css('width', '60px');	  
		
		
		 $(".btn show_info").tooltip({
             'selector': '',
             'placement': 'left'
           });
		 
		});	 
	},
			

	elements : function(h_id, p_id, m_id){		
		App.ho = new App.Models.Hospital();		
		App.ho.fetch({data:{id: h_id}}).then(function(){
		
		App.hospital_products =  App.ho.get('products');	
		App.route.tabs(h_id);		
		
		var external_ehrs = [];
		var primary_ehr="";
		var measure_completed=false;
		
		$.each( App.ho.get('products'), function( i, product ) { 	
			if (product.id==p_id) {				
				$.each(product.measures, function( i, measure ){
					if (measure.id==m_id) {
						measure_completed = measure.completed;
						external_ehrs = App.ho.get('externalEHRs').split('\n');
						primary_ehr = App.ho.get('ehr').code;
						//breadcrumb
						var viewMeasureBreadcrumb = new App.Views.HospitalMeasureBreadcrumb({model: measure, product_code:product.code, product_id:p_id, hospital_id:h_id});		
						$('#breadcrumb-box').html(viewMeasureBreadcrumb.render().el);
					}				
				});				
			}						       
		});	
						
		App.hospitalElements = new App.Collections.HospitalElements();
		
		App.hospitalElements.fetch({data:{id: m_id}}).then(function(){			                          
			
			App.viewHospitalElements = new App.Views.HospitalElements ({collection:App.hospitalElements, m_id: m_id,product_id: p_id, external_ehrs:external_ehrs, primary_ehr:primary_ehr, measure_completed:measure_completed});
			$('#app').html(App.viewHospitalElements.render().el);	
			
			$('#deatails *').attr('disabled','disabled');
			
			var oTable = $('#hospital-elements').dataTable({		
				"bDestroy": true, 
				"bPaginate": false,
				"bFilter": false,
				//"sScrollY": "262px",			
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
	}	
});
