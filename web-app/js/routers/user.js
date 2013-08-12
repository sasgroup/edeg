App.Routers.User = Backbone.Router.extend({
	routes : {
		""     	                                       : 'home',		
		'hospital/:h_id/product/:p_id/measure/:m_id'   : 'elements',
		'hospital/:h_id/product/:p_id'                 : 'productn'			

	},

	initialize: function(options){
		App.hospitals = new App.Collections.Hospitals();		
		App.route = this;
	},
		
	home : function(){			
		$('ul#hospital-list-dropdown').empty();
		App.hospitals.fetch().then(function(){			
			App.hospitals.forEach(function(hospital){				
				$('ul#hospital-list-dropdown').append('<li data-id='+ hospital.get('id')+'><a href="#">' + hospital.get('name') + '</a></li>');
			});
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
		App.route.home();
		App.ho = new App.Models.Hospital();		
		App.ho.fetch({data:{id: h_id}}).then(function(){
		
		  App.hospital_products =  App.ho.get('products');	
		  App.route.tabs(h_id);	
		
		  $.each(App.ho.get('products'), function( i, product ) { 	
			if (product.id==p_id) {				
				//breadcrumb
				var temp = _.template($('#user-hospital-breadcrumb').html());		
				$('#breadcrumb-box').html(temp({product_code:product.code}));				
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
			"bFilter": true,
			"sScrollY": "548px",			
			"bSort": true,
			"bInfo": false,
			"aaSorting": [[0, 'asc']],
			//"aoColumnDefs": [{'bSortable': false, 'aTargets': [ 1,3,4,5,6,7 ] }]
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
		
		 $(".btn show_info").tooltip({
             'selector': '',
             'placement': 'left'
           });
		 
		});	 
	},
			

	elements : function(h_id, p_id, m_id){		
		App.route.home();
		App.ho = new App.Models.Hospital();		
		App.ho.fetch({data:{id: h_id}}).then(function(){
		
		App.hospital_products =  App.ho.get('products');	
		App.route.tabs(h_id);		
		
		$.each( App.ho.get('products'), function( i, product ) { 	
			if (product.id==p_id) {				
				$.each(product.measures, function( i, measure ){
					if (measure.id==m_id) {										
						//breadcrumb
						var temp = _.template($('#user-measure-breadcrumb').html());			
						$('#breadcrumb-box').html(temp({product_code:product.code, product_id:p_id, measure_code:measure.code, hospital_id:h_id}));

					}				
				});				
			}						       
		});	
						
		App.hospitalElements = new App.Collections.HospitalElements();
		
		App.hospitalElements.fetch({data:{id: m_id}}).then(function(){			                          
			
			App.viewHospitalElements = new App.Views.HospitalElements ({collection:App.hospitalElements});
			$('#app').html(App.viewHospitalElements.render().el);		
			
			var oTable = $('#hospital-elements').dataTable({		
				"bDestroy": true, 
				"bPaginate": false,
				"bFilter": false,
				"sScrollY": "262px",			
				"bSort": true,
				"bInfo": false,
				"aaSorting": [[0, 'asc']],
				"aoColumnDefs": [{'bSortable': false, 'aTargets': [ 1,2,3,4,5,6 ] }]			 
			});				
			
			new FixedColumns( oTable, {"sHeightMatch": "none"} );				
	    });		
		
		});
	}	
});
