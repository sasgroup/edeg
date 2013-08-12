App.Routers.User = Backbone.Router.extend({
	routes : {
		""     	                        : 'home',		
		'product/:p_id/measure/:m_id'   : 'elements',
		'hospital/:h_id/product/:p_id'  : 'productn'			

	},

	initialize: function(options){
		App.hospitals = new App.Collections.Hospitals();		
	},
		
	home : function(){			
		$('ul#hospital-list-dropdown').empty();
		App.hospitals.fetch().then(function(){			
			App.hospitals.forEach(function(hospital){				
				$('ul#hospital-list-dropdown').append('<li data-id='+ hospital.get('id')+'><a href="#">' + hospital.get('name') + '</a></li>');
			});
		});
	},
					

	productn : function(h_id,p_id) {				
		
		App.ho.fetch({data:{id: h_id}}).then(function(){			
		
		//hospital_measure_table	
		//App.hospital_products =  App.ho.get('products');
		$.each(App.ho.get('products'), function( i, product ) { 	
			if (product.id==p_id) {
				
				//breadcrumb
				var temp = _.template($('#user-hospital-breadcrumb').html());		
				$('#breadcrumb-box').html(temp({product_code:product.code}));
				
																		//product index	
				var view = new App.Views.HospitalProduct({model: product, p_index: i});		
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
			

	elements : function(p_id, m_id){			
		var hm_id = '';
		// get hospital_measure_id
		$.each( App.ho.get('products'), function( i, product ) { 	
			if (product.id==p_id) {				
				$.each(product.measures, function( i, measure ){
					if (measure.id==m_id) {
						hm_id = measure.id;				
						//breadcrumb
						var temp = _.template($('#user-measure-breadcrumb').html());			
						$('#breadcrumb-box').html(temp({product_code:product.code, product_id:p_id, measure_code:measure.code}));

					}				
				});				
			}						       
		});	
					
		App.hospitalElements = new App.Collections.HospitalElements();
		
		App.hospitalElements.fetch({data:{id: hm_id}}).then(function(){			                          //pass product_id
			App.viewHospitalElements = new App.Views.HospitalElements ({collection:App.hospitalElements, product_id: p_id});
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
		
		
	}	
});
