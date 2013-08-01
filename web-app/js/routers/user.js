App.Routers.User = Backbone.Router.extend({
	routes : {
		""     	   : "home",
		':id'      : 'productn'
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
		var temp = _.template($('#user-hospital-breadcrumb').html());		
		$('#breadcrumb-box').html(temp({product_code:product_code}));			
		
		var measures;
		
		var table_template = _.template($('#user-hospital-measure_table').html());
		$('#app').html(table_template());		
		
		$.each( App.ho.get('products'), function( i, product ) { 	
			if (product.code==product_code) {
				measures = product.measures;
				
			}			
			       
		});	
		
		$.each( measures, function( m_index, measure ) {
			//console.log(JSON.stringify(measure));		
			var hospitalMeasure	 =  new App.Models.HospitalMeasure({"id":measure.id,
																"code":measure.code,
																"name":measure.name,
																"accepted" :measure.accepted,
																"completed":measure.completed,
																"confirmed":measure.confirmed,
																"included" :measure.included,
																"verified" :measure.verified,																
																"m_index"  :m_index
																});	
			console.log(hospitalMeasure);
		
			var view = new App.Views.SingleUserHospitalMeasure({ model : hospitalMeasure });				
			$('.hospitalMeasureTable tbody').append(view.render().el);				
		});
		
		var oTable = $('.hospitalMeasureTable').dataTable({		
			"bDestroy": true, 
			"bPaginate": false,
			"bFilter": false,
			"sScrollY": "600px",			
			"bSort": true,
			"bInfo": false,
			"aaSorting": [[0, 'asc']],
			"aoColumnDefs": [{'bSortable': false, 'aTargets': [ 3,4,5,6 ] }]					
			 
		 });				
	
		new FixedColumns( oTable, {"sHeightMatch": "none"} );			
	}	
	
});
