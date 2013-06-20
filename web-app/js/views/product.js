// List of Products
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


// New Product
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


// Edit Product
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

//Single Product
App.Views.SingleProduct = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-product').html()),		
			events : {
				'click #edit'    : 'goToEdit',
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
				this.model.destroy();				  
				this.$el.remove();  
			}
		});


