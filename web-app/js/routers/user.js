App.Routers.User = Backbone.Router.extend({
	routes : {
		"home"     : "home",
		"product1" : "product1",
		"product2" : "product2"	
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
	
	product1 : function() {		
		$('#app').html("Here should be view for product1");	
		
		var temp = '<ul class="breadcrumb">' +
		  '<li><a href="#">Home</a><span class="divider">></span></li>' +
		  '<li><a href="#">Product1</a><span class="divider">></span></li>' +
		  '<li class="active">Measures</li> ' +
		'</ul>';		
		$('#breadcrumb-box').html(temp);
				
	},
	
	product2 : function() {		
		$('#app').html("Here should be view for product2");	
		
		var temp = '<ul class="breadcrumb">' +
		  '<li><a href="#">Home</a><span class="divider">></span></li>' +
		  '<li><a href="#">Product2</a><span class="divider">></span></li>' +
		  '<li class="active">Measures</li> ' +
		'</ul>';
		$('#breadcrumb-box').html(temp);				
	},
	
	
});
