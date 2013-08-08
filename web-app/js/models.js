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
	    "dataElementDefaults" : [],
	    "measures" : []//,
	    //"hospitals" :[]
	  },
	  parse: function (response) {
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


App.Models.HospitalMeasure = Backbone.Model.extend({
	urlRoot: '/ihm/api/hospital_measure'	
});


App.Models.HospitalElement = Backbone.Model.extend({
	urlRoot: '/ihm/api/hospital_element'	
});

//Modal
App.Models.ModalExtraDataElement = Backbone.Model.extend({
});


App.Models.ModalHospitalSpecific = Backbone.Model.extend({
});


//ExtraDataElement
App.Models.ExtraDataElement = Backbone.Model.extend({
});

//HospitalSpecific
App.Models.HospitalSpecific = Backbone.Model.extend({
});
