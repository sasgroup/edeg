App.Routers.User = Backbone.Router.extend({
	routes : {
		""     	                                : "home",
		':product_code/:measure_code/elements'  : 'elements',
		':product_code'                         : 'productn'			
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
					
	productn : function(product_code) {		
		//breadcrumb
		var temp = _.template($('#user-hospital-breadcrumb').html());		
		$('#breadcrumb-box').html(temp({product_code:product_code}));
		
		//hospital_measure_table		
		$.each( App.ho.get('products'), function( i, product ) { 	
			if (product.code==product_code) {				
				var view = new App.Views.HospitalProduct({model: product});		
				$('#app').html(view.render().el);	
				return;
			}						       
		});	
				
		var oTable = $('.hospitalMeasureTable').dataTable({		
			"bDestroy": true, 
			"bPaginate": false,
			"bFilter": false,
			"sScrollY": "548px",			
			"bSort": true,
			"bInfo": false,
			"aaSorting": [[0, 'asc']],
			"aoColumnDefs": [{'bSortable': false, 'aTargets': [ 2,3,4,5 ] }]			 
		});				
		
		new FixedColumns( oTable, {"sHeightMatch": "none"} );			
	},
			
	elements : function(product_code,measure_code){			
		//breadcrumb
		var temp = _.template($('#user-measure-breadcrumb').html());			
		$('#breadcrumb-box').html(temp({product_code:product_code, measure_code:measure_code}));
		
		var hm_id = '';
		// get hospital_measure_id
		$.each( App.ho.get('products'), function( i, product ) { 	
			if (product.code==product_code) {				
				$.each(product.measures, function( i, measure ){
					if (measure.code==measure_code) {
						hm_id = measure.id;						
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
