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

// New/Edit Product
App.Views.Product = Backbone.View.extend({
	template : _.template($('#product-template').html()),

	events : {
		'submit' : 'submProduct',
		'click button#cancel' : 'returnOnMain',       
		'change #code, #name, #notes' : 'changeVal',
		'change .checkbox' : 'changeCh',
		'focusout input[name=code]': 'uniqueCodeCheck'

	},
	
	render : function() {	
		if (!this.model.toJSON().id) {
			this.model.set("state" , "New");
		};
		this.$el.html(this.template(this.model.toJSON()));
		App.measures.forEach(this.appendProductMeasure,this);	
		this.model.get('hospitals').forEach(this.appendHospital,this);	
		//App.hospitals.forEach(this.appendProductHospital,this);			
		return this;
	},

	uniqueCodeCheck: function () {
		var cur_code = '';
		var new_code = $('input[name=code]').val();
		var codes = [];
		 		
		if (this.model.toJSON().id) {
			cur_code = this.model.get('code');
		};
		
		$('input[name=code]').next('label.error').remove();		
		
		App.products.forEach(function(product){			
			codes.push(product.get('code'));
		});
				
		var index = codes.indexOf(cur_code);
		if (index!=-1) {
			codes.splice(index, 1);
		}	
				
		if (codes.indexOf(new_code)!=-1) {
			$('input[name=code]').after('<label class="error">Should be unique</label>');
		}				
	},
	
	appendProductMeasure : function(product_measure){
		var temp = _.template($('#single-product-measure').html());		
		var chd = '';
		this.model.get('measures').forEach(function( hospital ) {
			if (hospital.mid == product_measure.get('id')) {chd = 'checked';}
		});
		this.$el.find('div#measures').append(temp({name:product_measure.get('name'),id:product_measure.get('id'),ch:chd}));		
	},
	
	/*appendProductHospital : function(product_hospital){
		var temp = _.template($('#single-product-hospital').html());
		var chd = '';
		this.model.get('hospitals').forEach(function( hospital ) {
			if (hospital.hid == product_hospital.get('id')) {chd = 'checked';}
		});
		this.$el.find('div#hospitals').append(temp({name:product_hospital.get('name'),id:product_hospital.get('id'),ch:chd}));
	},*/
	
	appendHospital : function(product_hospital){
		var temp = _.template($('#single-product-hospital').html());
		this.$el.find('div#hospitals').append(temp({name:product_hospital.hname}));		
	},
	
	changeCh : function(e) {
		//console.log(e.target.value + ' ' + e.target.id + ' ' + e.target.checked+ ' '+e.target.name);
		if (e.target.name == 'hospital' ) {
			if ( e.target.checked ) {
				//console.log("Push hospitals");
				var hospitals = this.model.get("hospitals");
				hospitals.push({"hid" : e.target.id, "hname" : e.target.value});
				this.model.set("hospitals" , hospitals);
			} else {
				//console.log("Remove hospitals");
				var hospitals = this.model.get("hospitals");
				var removeIndex; 
				for (var i = 0; i < hospitals.length; i++) {
					if (hospitals[i].hid == e.target.id) {
						removeIndex = i;
					}
				}
				hospitals.splice(removeIndex,1);
				this.model.set("hospitals" , hospitals);
			}
		};
		if (e.target.name == 'measure' ) {
			if ( e.target.checked ) {
				//console.log("Push measure");
				var measures = this.model.get("measures");
				measures.push({"mid" : e.target.id, "mname" : e.target.value});
				this.model.set("measures" , measures);
			} else {
				//console.log("Remove measures");
				var measures = this.model.get("measures");
				var removeIndex; 
				for (var i = 0; i < measures.length; i++) {
					if (measures[i].mid == e.target.id) {
						removeIndex = i;
					}
				}
				measures.splice(removeIndex, 1);
				this.model.set("measures" , measures);
			};
		};	
	},
	
	changeVal : function(e) {
		//console.log(e.target.name);
		this.model.attributes[e.target.name] = $(e.target).val();
		//console.log(this.model.attributes);
	},
	
	submProduct : function(e) {
		e.preventDefault();				
		
		this.uniqueCodeCheck();
		
		this.model.save(this.attributes,{
	        success: function (model, response) {
	           //console.log(response);
	           $('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
               Backbone.history.navigate("product", true);
	        },
	        error: function (model, response) {
	        	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
	            //Backbone.history.navigate("product", true);
	        }
	    });
	},
	
	returnOnMain: function () {		
		Backbone.history.navigate("/product", true);				
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
				//console.log(this.model);
				//console.log("goToEdit",this.model.get('id'));							
				Backbone.history.navigate("product/"+this.model.get('id')+'/edit', true);
			},
			
			destroy : function(e){
				//console.log("destroy");
				e.preventDefault();
				
				var el = this.$el;
				
				if (confirm('Are you sure you want to delete this Product?')) {
				this.model.destroy({
					wait: true,
				    success: function(model, response){
				    	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
			    		el.remove();
				    	Backbone.history.navigate("product", true);
				     },
				     error: function (model, response) {
				    	 //console.log(response);
				    	 $('div#message-box').text("").append(response.responseText).fadeIn(500).delay(1500).fadeOut(500);
				            Backbone.history.navigate("product", true);
				     }
				});
				
				}
			}
		});


//Single ProductMeasure (assigned Measures)
App.Views.ProductMeasure = Backbone.View
		.extend({			
			tagName : 'label',
			className: 'checkbox',
			template: _.template($('#single-product-measure').html()),		
			
			render : function() {
				//console.log("name:" + this.name);
				this.$el.html(this.template());
				return this;
			}			
			
		});

