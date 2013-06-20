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


App.Collections.DataElements = Backbone.Collection.extend({
	model:App.Models.DataElement,
	
	url: '/ihm/api/element',
	
	parse: function(resp) {
		   return resp["elements"];
	}	
});


App.Collections.Hospitals = Backbone.Collection.extend({
	model:App.Models.Hospital,
	
	url: '/ihm/api/hospital',
	
	parse: function(resp) {
		   return resp["hospitals"];
	}	
});


App.Collections.Ehrs = Backbone.Collection.extend({
	model:App.Models.Ehr,
	
	url: '/ihm/api/ehr',
	
	parse: function(resp) {
		   return resp["ehrs"];
	}	
});
