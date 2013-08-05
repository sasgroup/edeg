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
				var view = new App.Views.UserHospitalProduct({model: product});		
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
		
		var temp_content = _.template($('#user-data-element').html());
		
		$('#app').html(temp_content());		
		
		var hm_id = '';
		// get hospital_measure_id
		$.each( App.ho.get('products'), function( i, product ) { 	
			if (product.code==product_code) {				
				//console.log(product.measures);
				$.each(product.measures, function( i, measure ){
					if (measure.code==measure_code) {
						hm_id = measure.id;						
					}				
				});				
			}						       
		});	
					
		var hospitalElements = new App.Collections.HospitalElements();
		
		hospitalElements.fetch({data:{id: hm_id}}).then(function(){
			console.log(hospitalElements);
			
			hospitalElements.forEach(function(hospitalElement){			
			var view = new App.Views.UserDataElement({ model : hospitalElement});		
			var modalDataElementRow = view.render().el;
			$('table#hospital-elements tbody').append(modalDataElementRow);						
			$(modalDataElementRow).find(".slcCodeType").val(hospitalElement.get('codeType').name);
			$(modalDataElementRow).find(".slcValueType").val(hospitalElement.get('valueType').name);
			});
	    });
		
		//$('#app').html(temp_content());		
		//table_row = _.template($('#user-data-elements').html());		
		//console.log(table_row);		
		//$('table#hospital-elements tbody').append(table_row);		
	}
	
});
