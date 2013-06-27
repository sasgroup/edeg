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
		Backbone.history.navigate("product/new", true);
	}
});


// New Product
App.Views.NewProduct = Backbone.View.extend({
	template : _.template($('#product-new-template').html()),

	events : {		
		'submit' : 'addProduct',
		'change #code, #name, #notes' : 'changeVal',
		'change .checkbox' : 'changeCh'
	},

	render : function() {
		console.log(App);
		this.$el.html(this.template());
		App.measures.forEach(this.appendProductMeasure,this);		
		App.hospitals.forEach(this.appendProductHospital,this);		
		return this;
	},
	
	appendProductMeasure : function(product_measure){
		var temp = _.template($('#single-product-measure').html());		
		var chd = '';
		this.model.get('measures').forEach(function( hospital ) {
			if (hospital.mid == product_measure.get('id')) {chd = 'checked';}
		});
		this.$el.find('div#measures').append(temp({name:product_measure.get('name'),id:product_measure.get('id'),ch:chd}));		
	},
	appendProductHospital : function(product_hospital){
		var temp = _.template($('#single-product-hospital').html());
		var chd = '';
		this.model.get('hospitals').forEach(function( hospital ) {
			if (hospital.hid == product_hospital.get('id')) {chd = 'checked';}
		});
		this.$el.find('div#hospitals').append(temp({name:product_hospital.get('name'),id:product_hospital.get('id'),ch:chd}));
	},
	
	changeCh : function(e) {
		console.log(e.target.value + ' ' + e.target.id + ' ' + e.target.checked+ ' '+e.target.name);
		if (e.target.name == 'hospital' ) {
			if ( e.target.checked ) {
				console.log("Push hospitals");
				var hospitals = this.model.get("hospitals");
				hospitals.push({"hid" : e.target.id, "hname" : e.target.value});
				this.model.set("hospitals" , hospitals);
			} else {
				console.log("Remove hospitals");
				var hospitals = this.model.get("hospitals");
				var removeIndex; 
				for (var i = 0; i < hospitals.length; i++) {
					if (hospitals[i].hid = e.target.id) {
						removeIndex = i;
					}
				}
				hospitals.splice(removeIndex,1);
			}
		};
		if (e.target.name == 'measure' ) {
			if ( e.target.checked ) {
				console.log("Push measure");
				var measures = this.model.get("measures");
				measures.push({"mid" : e.target.id, "mname" : e.target.value});
				this.model.set("measures" , measures);
			} else {
				console.log("Remove measures");
				var measures = this.model.get("measures");
				var removeIndex; 
				for (var i = 0; i < measures.length; i++) {
					if (measures[i].hid = e.target.id) {
						removeIndex = i;
					}
				}
				measures.splice(removeIndex,1);
			};
		};	
	},
	
	changeVal : function(e) {
		console.log(e.target.name);
		this.model.attributes[e.target.name] = $(e.target).val();
		console.log(this.model.attributes);
	},
	addProduct : function(e) {
		e.preventDefault();
		console.log("Product added");

		this.model.save({
			code : this.$('#code').val(),
			name : this.$('#name').val(),
			notes : this.$('#notes').val()
		},
		
		{
		    success: function(model, response) {
		    	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
		        Backbone.history.navigate("product", true);
		    }
		
		});			
		
	}

});


// Edit Product
App.Views.EditProduct = Backbone.View.extend({
	template : _.template($('#product-edit-template').html()),

	events : {
		'submit' : 'editProduct',
		'change #code, #name, #notes' : 'changeVal',
		'change .checkbox' : 'changeCh'
	},
	
	render : function() {				
		console.log(this.model.toJSON());	
		this.$el.html(this.template(this.model.toJSON()));
		App.measures.forEach(this.appendProductMeasure,this);		//new
		App.hospitals.forEach(this.appendProductHospital,this);		//new	
		return this;
	},

	appendProductMeasure : function(product_measure){
		var temp = _.template($('#single-product-measure').html());		
		var chd = '';
		this.model.get('measures').forEach(function( hospital ) {
			if (hospital.mid == product_measure.get('id')) {chd = 'checked';}
		});
		this.$el.find('div#measures').append(temp({name:product_measure.get('name'),id:product_measure.get('id'),ch:chd}));		
	},
	
	appendProductHospital : function(product_hospital){
		var temp = _.template($('#single-product-hospital').html());
		var chd = '';
		this.model.get('hospitals').forEach(function( hospital ) {
			if (hospital.hid == product_hospital.get('id')) {chd = 'checked';}
		});
		this.$el.find('div#hospitals').append(temp({name:product_hospital.get('name'),id:product_hospital.get('id'),ch:chd}));
	},
	
	changeCh : function(e) {
		console.log(e.target.value + ' ' + e.target.id + ' ' + e.target.checked+ ' '+e.target.name);
		if (e.target.name == 'hospital' ) {
			if ( e.target.checked ) {
				console.log("Push hospitals");
				var hospitals = this.model.get("hospitals");
				hospitals.push({"hid" : e.target.id, "hname" : e.target.value});
				this.model.set("hospitals" , hospitals);
			} else {
				console.log("Remove hospitals");
				var hospitals = this.model.get("hospitals");
				var removeIndex; 
				for (var i = 0; i < hospitals.length; i++) {
					if (hospitals[i].hid = e.target.id) {
						removeIndex = i;
					}
				}
				hospitals.splice(removeIndex,1);
			}
		};
		if (e.target.name == 'measure' ) {
			if ( e.target.checked ) {
				console.log("Push measure");
				var measures = this.model.get("measures");
				measures.push({"mid" : e.target.id, "mname" : e.target.value});
				this.model.set("measures" , measures);
			} else {
				console.log("Remove measures");
				var measures = this.model.get("measures");
				var removeIndex; 
				for (var i = 0; i < measures.length; i++) {
					if (measures[i].hid = e.target.id) {
						removeIndex = i;
					}
				}
				measures.splice(removeIndex,1);
			};
		};	
	},
	
	changeVal : function(e) {
		console.log(e.target.name);
		this.model.attributes[e.target.name] = $(e.target).val();
		console.log(this.model.attributes);
	},
	
	editProduct : function(e) {
		e.preventDefault();		

		this.model.save(this.attributes,{
	        success: function (model, response) {
	           console.log(response);
	           $('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
               Backbone.history.navigate("product", true);
	        },
	        error: function (model, response) {
	        	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
	            Backbone.history.navigate("product", true);
	        }
	    });
	},
	
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
				//e.preventDefault();
				
				var el = this.$el;
				
				this.model.destroy({
					wait: true,
				    success: function(model, response){
				    	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
			    		el.remove();
				    	Backbone.history.navigate("product", true);
				     },
				     error: function (model, response) {
				    	 console.log(response);
				    	 $('div#message-box').text("").append(response.responseText).fadeIn(500).delay(1500).fadeOut(500);
				            Backbone.history.navigate("product", true);
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

