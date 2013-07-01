App.Router = Backbone.Router.extend({
	routes : {
		''                : 'products',
		'product'		  : 'products',
		'measure'         : 'measures',
		'element'         : 'dataElements',
		'hospital'        : 'hospitals',
		'ehr'        	  : 'ehrs',
		
		'product/:new'    : 'newProduct',
		'measure/:new'    : 'newMeasure',
		'element/:new'    : 'newDataElement',
		'ehr/:new'        : 'newEhr',
		
		'product/:id/edit': 'editProduct',
		'measure/:id/edit': 'editMeasure',
		'element/:id/edit': 'editDataElement',
		'ehr/:id/edit'    : 'editEhr'
	},

	
	// ------- LIST ------------
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
	
	
    
	// ------- NEW ------------
    // new product
	newProduct : function() {			
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
		App.products = new App.Collections.Products();			
		App.products.fetch().then(function(){			
			App.dataElements = new App.Collections.DataElements();			
			App.dataElements.fetch().then(function(){
				App.measure = new App.Models.Measure();
				view = new App.Views.NewMeasure({model: App.measure});		
				$('#app').html(view.render().el); 
			});			
			  
		});		
	},

	// new dataElement
	newDataElement : function() {				
		App.measures = new App.Collections.Measures();			
		App.measures.fetch().then(function(){			
			App.ehrs = new App.Collections.Ehrs();			
			App.ehrs.fetch().then(function(){
				App.dataElement = new App.Models.DataElement();
				var view = new App.Views.NewDataElement({model:App.dataElement});
				$('#app').html(view.render().el);
			});			
		});		
	},


	// new EHR
	newEhr : function() {				
		App.hospitals = new App.Collections.Hospitals();			
		App.hospitals.fetch().then(function(){			
			App.dataElements = new App.Collections.DataElements();			
			App.dataElements.fetch().then(function(){
				App.ehr = new App.Models.Ehr();
				var view = new App.Views.NewEhr({model:App.ehr});
				$('#app').html(view.render().el);
			});			
		});		
	},	

	
	// ------- EDIT ------------
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
	},

	// edit dataElement
	editDataElement : function(id) {
		console.log('dataElementEdit id:'+id)
		App.measures = new App.Collections.Measures();			
		App.measures.fetch().then(function(){			
			App.ehrs = new App.Collections.Ehrs();			
			App.ehrs.fetch().then(function(){
				var dataElement = App.dataElements.get(id);	
				var view = new App.Views.EditDataElement({model:dataElement});
				$('#app').html(view.render().el);
			});			
		});				
	},

	// edit ehr
	editEhr : function(id) {
		console.log('EHR Edit id:'+id)
		App.hospitals = new App.Collections.Hospitals();			
		App.hospitals.fetch().then(function(){			
			App.dataElements = new App.Collections.DataElements();			
			App.dataElements.fetch().then(function(){
				var ehr = App.ehrs.get(id);	
				var view = new App.Views.EditEhr({model:ehr});
				$('#app').html(view.render().el);
			});			
		});				
	}

});