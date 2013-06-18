App.Router = Backbone.Router.extend({
	routes : {
		''                : 'products',
		'product'         : 'products',
		'measure'         : 'measures',
		'measure/:new'    : 'newMeasure',
		'product/:new'    : 'newProduct',
		'product/:id/edit': 'editProduct'	
	},

	
	// list of products
	products : function() {		
		console.log("run products");
		App.products = new App.Collections.Products();			
		App.products.fetch().then(function(){
			App.viewProducts = new App.Views.Products({collection:App.products});
			console.log(App.viewProducts.render().el);
			$('#app').html(App.viewProducts.render().el);
		});
		
		
		//var view = new App.Views.ProductsList();
		//$('#app').html(view.render().el);
	},
	
	// list of measures
	measures : function() {		
		//var view = new App.Views.Measures();
		//$('#app').html(view.render().el);
		App.measures = new App.Collections.Measures();			
		App.measures.fetch().then(function(){
			App.viewMeasures = new App.Views.Measures({collection:App.measures});
			console.log(App.viewMeasures.render().el);
			$('#app').html(App.viewMeasures.render().el);
		});
	},	
	
    // new product
	newProduct : function() {		
		console.log(App.products);
		var view = new App.Views.NewProduct({collection:App.products});
		$('#app').html(view.render().el);  
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
		 
		//var view = new App.Views.EditProduct({collection:App.products});
		console.log(view.render().el);
		$('#app').html(view.render().el);  
	},

});

