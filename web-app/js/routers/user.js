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
		$('#app').html("Here should be view for " + product_code);	
		
		var temp = '<ul class="breadcrumb">' +
		  '<li><a href="#">Home</a><span class="divider">></span></li>' +
		  '<li><a href="#">'+product_code+ '</a><span class="divider">></span></li>' +
		  '<li class="active">Measures</li> ' +
		'</ul>';
		$('#breadcrumb-box').html(temp);				
	}
	
	
});
