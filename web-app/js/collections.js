App.Collections.Products = Backbone.Collection.extend({
	model:App.Models.Product,
	
	url: '/ihm/api/product',
	
	 parse: function(resp) {
		   return resp["products"];
	 }	
	
});

App.Collections.Measures = Backbone.Collection.extend({
	model:App.Models.Measure,
	
	url: '/ihm/api/measure',
	
	 parse: function(resp) {
		   return resp["measures"];
	 }	
	
});