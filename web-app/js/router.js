App.Router = Backbone.Router.extend({
	routes : {
		''                : 'products',
		'product'         : 'products',
		'measure'         : 'measures',
		'element'         : 'dataElements',
		'hospital'        : 'hospitals',
		'ehr'        	  : 'ehrs',
		'measure/:new'    : 'newMeasure',
		'product/:new'    : 'newProduct',
		'product/:id/edit': 'editProduct'	
	},

	
	// list of products
	products : function() {	
		App.products = new App.Collections.Products();			
		App.products.fetch().then(function(){
			App.viewProducts = new App.Views.Products({collection:App.products});
			console.log(App.viewProducts.render().el);
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
				
		App.measures = new App.Collections.Measures();			
		App.measures.fetch().then(function(){			
			console.log(App.measures);
			
			App.hospitals = new App.Collections.Hospitals();			
			App.hospitals.fetch().then(function(){
				console.log(App.hospitals);
				var view = new App.Views.NewProduct({collection:App.products});
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
		console.log("router editProduct");
		var product = App.products.get(id);		 
		view = new App.Views.EditProduct({model: product});		
		console.log(view.render().el);
		$('#app').html(view.render().el);  
	},

});

