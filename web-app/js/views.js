// View for List of Products
App.Views.Products = Backbone.View.extend({
	template : _.template($('#product-list-template').html()),

	events : {
		'click #create_product' : 'createProduct'
	},

	initialize : function() {
		//console.log(this.collection.toJSON());
		//this.collection.on('reset', this.render(), this);
		//this.collection.on('add', this.appendProduct, this);
		//this.collection.on('destroy', this.render(), this);
		//this.collection.on('change', this.render(), this);
	},

	render : function() {
		//this.$el.empty();
		this.$el.html(this.template({
			products : this.collection
		}));
		this.collection.each(this.appendProduct, this);
		return this;
	},

	appendProduct : function(product) {
		var view = new App.Views.SingleProduct({
			model : product
		});
		this.$el.find('#table_items tbody').append(view.render().el);
	},

	createProduct : function() {
		console.log("createProduct");
		Backbone.history.navigate("product/new", true)
	}
});

//View for List of Measures
App.Views.Measures = Backbone.View.extend({
	template : _.template($('#measure-list-template').html()),

	events : {
		'click #create_measure' : 'createMeasure'
	},

	render : function() {
		//this.$el.html(this.template());
		//return this;
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

//View for a new Product
App.Views.NewProduct = Backbone.View.extend({
	template : _.template($('#product-template').html()),

	events : {
		'click #assignMeasure' : 'assignMeasure',		
		'submit' : 'addProduct'
	},

	render : function() {
		this.$el.html(this.template());
		return this;
	},

	assignMeasure : function() {
		console.log('assignMeasure');
		this.$el.find("#modalMeasures input[type='checkbox']:checked").each(
				function(index) {
					$('.checkboxlist').append($(this).closest('label'));
					$(".checkboxlist input[type='checkbox']").bind(
							'click',
							function() {
								var checked = $(this).prop("checked");

								if (!checked) {
									$('.checkboxlistModal').append(
											$(this).closest('label'));
								}
							});
				});
	},

	addProduct : function(e) {
		e.preventDefault();
		console.log("Product added");

		this.collection.create({
			code : this.$('#code').val(),
			name : this.$('#name').val(),
			notes : this.$('#notes').val()
		}, {
			wait : true
		});

		console.log(this.collection.toJSON());

	}

});


//View for Product editing
App.Views.EditProduct = Backbone.View.extend({
	template : _.template($('#product-edit').html()),

	events : {
		'submit' : 'editProduct'
	},
	
	render : function() {				
		this.$el.html(this.template(this.model.toJSON()));
		//this.$el.html(this.template({name: "test"});
		return this;
	},

	editProduct : function(e) {
		e.preventDefault();		
		console.log("Product edited");
		this.model.save({
			code  : this.$('#code').val(),
			name  : this.$('#name').val(),
			notes : this.$('#notes').val()
		},{error: function(){ console.log("error by editing") }});
	}
});

//View for a new Measure
App.Views.Measure = Backbone.View.extend({
	template : _.template($('#measure-template').html()),

	events : {
		'click #assignProduct' : 'assignProduct',
		'click #assignElement' : 'assignElement'
	},

	render : function() {
		this.$el.html(this.template());
		return this;
	},

	assignProduct : function() {
		this.$el.find("#modalProducts input[type='checkbox']:checked").each(
				function(index) {
					$('.checkboxlist').append($(this).closest('label'));
					$(".checkboxlist input[type='checkbox']").bind(
							'click',
							function() {
								var checked = $(this).prop("checked");

								if (!checked) {
									$('.checkboxlistModal').append(
											$(this).closest('label'));
								}
							});
				});
	},

	assignElement : function() {
		this.$el.find("#modalElements input[type='checkbox']:checked").each(
				function(index) {
					console.log($(this).closest('label'));
					$('.checkboxlist-elements')
							.append($(this).closest('label'));

					$(".checkboxlist-elements input[type='checkbox']").bind(
							'click',
							function() {
								var checked = $(this).prop("checked");
								console.log($(this).closest('div'));

								if (!checked) {
									$('.checkboxlistModalElement').append(
											$(this).closest('label'));
								}
							});
				});
	}

});

//Single Product
App.Views.SingleProduct = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-product').html()),		
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
				Backbone.history.navigate("product/"+this.model.get('id')+'/edit', true);
			},
			
			destroy : function(){
				console.log("destroy");
			}
		});

//Single Measure
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
				console.log("goToEdit");
			},
			
			destroy : function(){
				console.log("destroy");
			}
		});

