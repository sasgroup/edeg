App.Models.Product = Backbone.Model.extend({
	urlRoot: '/ihm/api/product'	
});

App.Models.Measure = Backbone.Model.extend({
	urlRoot: '/ihm/api/measure'	
});


App.Models.DataElement = Backbone.Model.extend({
	urlRoot: '/ihm/api/element'	
});

App.Models.Hospital = Backbone.Model.extend({
	urlRoot: '/ihm/api/hospital'	
});

App.Models.Ehr = Backbone.Model.extend({
	urlRoot: '/ihm/api/ehr'	
});
