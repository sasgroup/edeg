// List of Products
App.Views.Products = Backbone.View.extend({
	template : _.template($('#product-list-template').html()),

	events : {
		'click #create_product' : 'createProduct'
	},

	initialize : function() {		
		this.collection.on('add', this.appendProduct, this);
		this.collection.on('change', this.render, this);
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
		//'click #assignMeasure' : 'assignMeasure',		
		'submit' : 'addProduct'
	},

	render : function() {
		this.$el.html(this.template());
		App.measures.forEach(this.appendProductMeasure,this);		//new
		App.hospitals.forEach(this.appendProductHospital,this);		//new
		return this;
	},

	appendProductMeasure : function(product_measure){
		var temp = _.template($('#single-product-measure').html());		
		console.log(product_measure.get('name'));
		this.$el.find('div#measures').append(temp({name:product_measure.get('name')}));		
	},
	
	appendProductHospital : function(product_hospital){
		var temp = _.template($('#single-product-hospital').html());		
		console.log(product_hospital.get('name'));
		this.$el.find('div#hospitals').append(temp({name:product_hospital.get('name')}));		
	},
	
	
	/*assignMeasure : function() {
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
	},*/

	addProduct : function(e) {
		e.preventDefault();
		console.log("Product added");

		this.collection.create({
			code : this.$('#code').val(),
			name : this.$('#name').val(),
			notes : this.$('#notes').val()
		},
		
		{
		    success: function() {
		        console.log("repsonse from Collection create");
		        Backbone.history.navigate("product", true);
		    }
		
		});			
		
	}

});


// Edit Product
App.Views.EditProduct = Backbone.View.extend({
	template : _.template($('#product-edit').html()),

	events : {
		'submit' : 'editProduct',
		'click #assignMeasure' : 'assignMeasure',
	},
	
	render : function() {				
		//console.log(this.model.toJSON());		
		this.$el.html(this.template(this.model.toJSON()));
		App.measures.forEach(this.appendProductMeasure,this);		//new
		App.hospitals.forEach(this.appendProductHospital,this);		//new	
		this.model.get('measures').forEach(this.setProductMeasure,this);		
		return this;
	},

	setProductMeasure : function(product_measure){
		console.log("product_measure.mname:" + product_measure.mname);
		//$('.myCheckbox').prop('checked', true);
		
		//var temp = _.template($('#single-product-measure').html());		
		//this.$el.find('.checkboxlist').append(temp({name:product_measure.mname}));		
	},
	
	appendProductMeasure : function(product_measure){
		var temp = _.template($('#single-product-measure').html());		
		console.log(product_measure.get('name'));
		this.$el.find('div#measures').append(temp({name:product_measure.get('name')}));		
	},
	
	appendProductHospital : function(product_hospital){
		var temp = _.template($('#single-product-hospital').html());		
		console.log(product_hospital.get('name'));
		this.$el.find('div#hospitals').append(temp({name:product_hospital.get('name')}));		
	},
	
	editProduct : function(e) {
		e.preventDefault();		
		console.log("Product edited");
		this.model.save({
			code  : this.$('#code').val(),
			name  : this.$('#name').val(),
			notes : this.$('#notes').val()
		},
		
		{
	        success: function (model, response) {
	            console.log("success");
	            Backbone.history.navigate("product", true);
	        },
	        error: function (model, response) {
	            console.log("error");
	            Backbone.history.navigate("product", true);
	        }
	    });
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
			
			destroy : function(e){
				console.log("destroy");
				e.preventDefault();
				
				var el = this.$el;
				
				this.model.destroy({
				      success: function(model, response){
				      
				    	if (response.resp=="error")  				    	
				    	  alert(response.message)
				    	else el.remove();				    					        
				        
				      }
				});
				
				
				//this.model.destroy();				  
				//this.$el.remove();  
			}
		});


//Single ProductMeasure (assigned Measures)
App.Views.ProductMeasure = Backbone.View
		.extend({			
			tagName : 'label',
			className: 'checkbox',
			template: _.template($('#single-product-measure').html()),		
			
			render : function() {
				console.log("name:" + this.name);
				this.$el.html(this.template());
				return this;
			}			
			
		});

