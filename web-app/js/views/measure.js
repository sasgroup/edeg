// List of Measures
App.Views.Measures = Backbone.View.extend({
	template : _.template($('#measure-list-template').html()),

	events : {
		'click #create_measure' : 'createMeasure'
	},

	render : function() {		
		this.$el.html(this.template({
			measures : this.collection
		}));
		this.collection.each(this.appendMeasure, this);
		return this;
	},

	appendMeasure : function(measure) {
		var view = new App.Views.SingleMeasure({
			model : measure
		});
		this.$el.find('#table_items tbody').append(view.render().el);
	},

	createMeasure : function() {
		console.log("createMeasure");
		Backbone.history.navigate("measure/new", true)
	}
});

// New Measure
App.Views.NewMeasure = Backbone.View.extend({
	template : _.template($('#measure-new-template').html()),

	events : {		
		'submit' : 'addMeasure'/*,
		'change #code, #name, #notes' : 'changeVal',
		'change .checkbox' : 'changeCh'*/
	},

	render : function() {
		console.log(App.measureCategories);
		this.$el.html(this.template());
		App.products.forEach(this.appendProduct,this);		
		App.dataElements.forEach(this.appendDataElement,this);		
		
		App.measureCategories.forEach(this.appendMeasureCategory,this);	
		App.cqmDomains.forEach(this.appendCqmDomain,this);
		return this;
	},
	
	appendProduct : function(ehr_hospital){
		var temp = _.template($('#single-measure-product').html());		
		var chd = '';		
		this.$el.find('div#products').append(temp({name:ehr_hospital.get('name'),id:ehr_hospital.get('id'),ch:chd}));		
	},
	
	appendDataElement : function(ehr_element){
		var temp = _.template($('#single-measure-element').html());
		var chd = '';		
		this.$el.find('div#elements').append(temp({name:ehr_element.get('name'),id:ehr_element.get('id'),ch:chd}));
	},
	
	appendMeasureCategory : function(measure_category){
		var temp = _.template($('#single-measure-category').html());
		console.log("measure_category: "+ measure_category.get('name'));
		this.$el.find('#category').append(temp({name:measure_category.get('name')}));
	},
	
	appendCqmDomain : function(cqm_domain){
		var temp = _.template($('#single-measure-domain').html());
		console.log("cqm_domain: "+ cqm_domain.get('name'));
		this.$el.find('#cqm_domain').append(temp({name:cqm_domain.get('name')}));
	},
	
	addMeasure : function(e) {
		e.preventDefault();
		console.log("Measure added");

		this.model.save({
			code : this.$('#code').val(),
			name : this.$('#name').val(),
			notes : this.$('#notes').val()
		},
		
		{
		    success: function(model, response) {
		    	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
		        Backbone.history.navigate("measure", true);
		    }
		
		});				
	}
});

// Edit Measure
App.Views.EditMeasure = Backbone.View.extend({
	template : _.template($('#measure-edit-template').html()),

	events : {
		'submit' : 'editMeasure'
		/*'change #code, #name, #notes' : 'changeVal',
		'change .checkbox' : 'changeCh'*/
	},
	
	render : function() {				
		console.log(this.model.toJSON());	
		this.$el.html(this.template(this.model.toJSON()));
		App.products.forEach(this.appendProduct,this);		
		App.dataElements.forEach(this.appendDataElement,this);		
		
		App.measureCategories.forEach(this.appendMeasureCategory,this);	
		App.cqmDomains.forEach(this.appendCqmDomain,this);
		
		return this;
	},

	appendProduct : function(measure_product){
		var temp = _.template($('#single-measure-product').html());		
		var chd = '';
		/*this.model.get('products').forEach(function( product ) {
			if (product.mid == measure_product.get('id')) {chd = 'checked';}
		});*/
		this.$el.find('div#products').append(temp({name:measure_product.get('name'),id:measure_product.get('id'),ch:chd}));		
	},
	
	appendDataElement : function(measure_element){
		var temp = _.template($('#single-measure-element').html());
		var chd = '';
		/*this.model.get('elements').forEach(function( element ) {
			if (element.hid == measure_element.get('id')) {chd = 'checked';}
		});*/
		this.$el.find('div#elements').append(temp({name:measure_element.get('name'),id:measure_element.get('id'),ch:chd}));
	},
	
	appendMeasureCategory : function(measure_category){
		var temp = _.template($('#single-measure-category').html());
		console.log("measure_category: "+ measure_category.get('name'));
		this.$el.find('#category').append(temp({name:measure_category.get('name')}));
	},
	
	appendCqmDomain : function(cqm_domain){
		var temp = _.template($('#single-measure-domain').html());
		console.log("cqm_domain: "+ cqm_domain.get('name'));
		this.$el.find('#cqm_domain').append(temp({name:cqm_domain.get('name')}));
	},

	editMeasure : function(e) {
		e.preventDefault();		

		this.model.save(this.attributes,{
	        success: function (model, response) {
	           console.log(response);
 	           App.mesageDialog = new App.Models.MesageDialog({resp:"ok"});			
	           console.log(App.mesageDialog.resp);
               Backbone.history.navigate("product", true);
	        },
	        error: function (model, response) {
	            console.log("error");
	            Backbone.history.navigate("product", true);
	        }
	    });
	}
});	

// Single Measure
App.Views.SingleMeasure = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-measure').html()),			
			events : {
				'click #edit' : 'goToEdit',
				'click #destroy' : 'destroy'
			},

			render : function() {
				this.$el.html(this.template(this.model.toJSON()));
				return this;
			},

			goToEdit : function() {				
				console.log(this.model);
				console.log("goToEdit",this.model.get('id'));							
				Backbone.history.navigate("measure/"+this.model.get('id')+'/edit', true);
			},
			
			destroy : function(){
				console.log("destroy");
			}
		});
