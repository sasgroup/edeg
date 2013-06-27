App.Router = Backbone.Router.extend({
	routes : {
		''                : 'products',
		'product'		  : 'products',
		'measure'         : 'measures',
		'element'         : 'dataElements',
		'hospital'        : 'hospitals',
		'ehr'        	  : 'ehrs',
		
		'measure/:new'    : 'newMeasure',
		'product/:new'    : 'newProduct',
		
		'product/:id/edit': 'editProduct',
		'measure/:id/edit': 'editMeasure'
	},

	// list of products
	products : function() {
		App.products = new App.Collections.Products();			
		App.products.fetch().then(function(){
			App.viewProducts = new App.Views.Products({collection:App.products});
			$('#app').html(App.viewProducts.render().el);
		});		
	},
	
	// list of measures
	measures : function() {		
		App.measures = new App.Collections.Measures();			
		App.measures.fetch().then(function(){
			App.viewMeasures = new App.Views.Measures({collection:App.measures});
			console.log(App.viewMeasures.render().el);
			$('#app').html(App.viewMeasures.render().el);
		});
	},	
	
	// list of elements
	dataElements : function() {		
		App.dataElements = new App.Collections.DataElements();			
		App.dataElements.fetch().then(function(){
			App.viewDataElements = new App.Views.DataElements({collection:App.dataElements});			
			$('#app').html(App.viewDataElements.render().el);
		});
	},	
	
	// list of hospitals
	hospitals : function() {		
		App.hospitals = new App.Collections.Hospitals();			
		App.hospitals.fetch().then(function(){
			App.viewHospitals = new App.Views.Hospitals({collection:App.hospitals});			
			$('#app').html(App.viewHospitals.render().el);
		});
	},	
	
	// list of ehrs
	ehrs : function() {		
		App.ehrs = new App.Collections.Ehrs();			
		App.ehrs.fetch().then(function(){
			App.viewEhrs = new App.Views.Ehrs({collection:App.ehrs});			
			$('#app').html(App.viewEhrs.render().el);
		});
	},	
	
	
    // new product
	newProduct : function() {	
		console.log("Product create");
		App.measures = new App.Collections.Measures();			
		App.measures.fetch().then(function(){			
			App.hospitals = new App.Collections.Hospitals();			
			App.hospitals.fetch().then(function(){
				App.product = new App.Models.Product();
				var view = new App.Views.NewProduct({model:App.product});
				$('#app').html(view.render().el);
			});			
		});		
	},
	
	// new measure
	newMeasure : function() {		
		var view = new App.Views.Measure();
		$('#app').html(view.render().el);  
	},
	
	 // edit product
	editProduct : function(id) {
		console.log('productEdit id:'+id)
		App.measures = new App.Collections.Measures();			
		App.measures.fetch().then(function(){			
			App.hospitals = new App.Collections.Hospitals();			
			App.hospitals.fetch().then(function(){
				var product = App.products.get(id);	
				view = new App.Views.EditProduct({model: product});		
				$('#app').html(view.render().el); 
			});			
			  
		});		
	},

	 // edit measure
	editMeasure : function(id) {
		console.log('measuretEdit id:'+id)
		App.products = new App.Collections.Products();			
		App.products.fetch().then(function(){			
			App.dataElements = new App.Collections.DataElements();			
			App.dataElements.fetch().then(function(){
				var measure = App.measures.get(id);	
				view = new App.Views.EditMeasure({model: measure});		
				$('#app').html(view.render().el); 
			});			
			  
		});		
	}

});