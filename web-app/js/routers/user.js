App.Routers.User = Backbone.Router.extend({
	routes : {
		""     	                        : 'home',		
		'product/:p_id/measure/:m_id'   : 'elements',
		'product/:p_id'                 : 'productn'			

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
					

	productn : function(p_id) {		
		
		//hospital_measure_table		
		$.each( App.ho.get('products'), function( i, product ) { 	
			if (product.id==p_id) {
				
				//breadcrumb
				var temp = _.template($('#user-hospital-breadcrumb').html());		
				$('#breadcrumb-box').html(temp({product_code:product.code}));
				

				var view = new App.Views.HospitalProduct({model: product});		
				$('#app').html(view.render().el);	
				return;
			}						       
		});	
				
		var oTable = $('.hospitalMeasureTable').dataTable({		
			"bDestroy": true, 
			"bPaginate": false,
			"bFilter": true,
			"sScrollY": "548px",			
			"bSort": true,
			"bInfo": false,
			"aaSorting": [[0, 'asc']],
			"aoColumnDefs": [{'bSortable': false, 'aTargets': [ 0,3,4,5,6,7 ] }]			 
		});				
		
		new FixedColumns( oTable, {"sHeightMatch": "none"} );	
		
		 $(".btn show_info").tooltip({
             'selector': '',
             'placement': 'left'
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
		
		App.hospitalElements.fetch({data:{id: hm_id}}).then(function(){			
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
		
		
	}	
});
