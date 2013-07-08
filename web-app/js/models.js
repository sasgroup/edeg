App.Models.Product = Backbone.Model.extend({
	urlRoot: '/ihm/api/product'	,
	defaults: {
		"state" : "Edit",
	    "version":  1,
	    "code":     "",
	    "name":    "",
	    "notes": "",
	    "measures" : [],
	    "hospitals" :[]
	  }
});

App.Models.Measure = Backbone.Model.extend({
	urlRoot: '/ihm/api/measure',
	defaults: {
		"state" : "Edit",
		"version":  1,
	    "code":     "",
	    "name":    "",
	    "notes": "",
	    "measureCategory" : "",
	    "cqmDomain" : "",
	    "products" : [],
	    "dataElements" :[]
	  },
	  parse: function (response) {
	      return this.model = response;
	   }
});


App.Models.DataElement = Backbone.Model.extend({
	urlRoot: '/ihm/api/element',
	defaults: {
		"state" : "Edit",
	    "version":  1,
	    "code":     "",
	    "name":    "",
	    "notes": "",
	    "measures" : []//,
	    //"hospitals" :[]
	  },
	  parse: function (response) {
		  console.log(response);
	      return this.model = response;
	   }
});

App.Models.Hospital = Backbone.Model.extend({
	urlRoot: '/ihm/api/hospital'	
});

App.Models.Ehr = Backbone.Model.extend({
	urlRoot: '/ihm/api/ehr'	,
	defaults: {
		"state" : "Edit",
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


App.Models.ProductMeasure = Backbone.Model.extend({
	urlRoot: '/ihm/api/product_measure'	
});