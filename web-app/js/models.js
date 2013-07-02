App.Models.Product = Backbone.Model.extend({
	urlRoot: '/ihm/api/product'	,
	defaults: {
	    "version":  1,
	    "code":     "",
	    "name":    "",
	    "notes": "",
	    "measures" : [],
	    "hospitals" :[]
	  }
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
	urlRoot: '/ihm/api/ehr'	,
	defaults: {
	    "version":  1,
	    "code":     "",
	    "name":    "",
	    "notes": "",
	    "dataElementDefaults" : [],
	    "hospitals" :[]
	  }
});

App.Models.MeasureCategory = Backbone.Model.extend({
	urlRoot: '/ihm/api/measure_category'	
});

App.Models.CqmDomain = Backbone.Model.extend({
	urlRoot: '/ihm/api/cqm_domain'	
});