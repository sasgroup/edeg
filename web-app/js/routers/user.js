App.Routers.User = Backbone.Router.extend({
	routes : {
		"product1" : "product1",
		"product2" : "product2"	
	},

	initialize: function(options){
				
	},
		
	product1 : function() {
		
		$('#app').html("Here should be view for product1");	
				
	},
	
	product2 : function() {
		
		$('#app').html("Here should be view for product2");	
				
	},
	
	
});
