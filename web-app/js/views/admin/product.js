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
		'change #name, #notes' : 'changeVal',
		'change .checkbox' : 'changeCh',
		'click #btnHelp' : 'showHelpDialog'
	},
	
	render : function() {
		var state = (this.model.isNew())? "Add New Product":"Edit Product"; 
		this.model.set("state", state);
				
		this.$el.html(this.template(this.model.toJSON()));
				
		//append Measures				
		this.appendProductMeasures();
						
		//append Hospitals
		var this_product = this;		
		$.each(this.model.get('hospitals'), function(i, product_hospital){
			this_product.appendHospital(product_hospital);
		});
				
		$('.helpAreaProduct').wysihtml5();		
		return this;
	},


	appendProductMeasures : function(){
		var temp = _.template($('#single-product-measure').html());		
		var checked = [];
		var unchecked = [];
				
		var mids = _.pluck(this.model.get('measures'), 'mid');					
		var measure_ids = App.measures.pluck('id');
				
		App.measures.forEach(function(m){					
			if (_.contains(mids, m.get('id'))) {				
				checked.push({name:m.get('name'),id:m.get('id')});
			} else {
				unchecked.push({name:m.get('name'),id:m.get('id')});
			}
		});		
		
		checked.sort(function(a, b){
		    if(a.name < b.name) return -1;
		    if(a.name > b.name) return 1;
		    return 0;
		});
		
		unchecked.sort(function(a, b){
		    if(a.name < b.name) return -1;
		    if(a.name > b.name) return 1;
		    return 0;
		});
		
		for(var product_measure in checked) {
			var measure = checked[product_measure];
			this.$el.find('div#measures').append(temp({name:measure.name,id:measure.id,ch:'checked'}));	 
		}	
		
		for(var product_measure in unchecked) {
			var measure = unchecked[product_measure];
			this.$el.find('div#measures').append(temp({name:measure.name,id:measure.id,ch:''}));	 
		}	
	},
		
	appendHospital : function(product_hospital){
		var temp = _.template($('#single-product-hospital').html());
		this.$el.find('div#hospitals').append(temp({name:product_hospital.hname}));		
	},
	
	changeCh : function(e) {				
		if (e.target.name == 'measure' ) {
			if ( e.target.checked ) {				
				var measures = this.model.get("measures");
				measures.push({"mid" : e.target.id, "mname" : e.target.value});
				this.model.set("measures" , measures);
			} else {				
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
		this.model.attributes[e.target.name] = $(e.target).val();		
	},
	
	submProduct : function(e) {
		e.preventDefault();
		this.model.attributes.help = $('.helpAreaProduct').val();			
		this.model.set({code:this.$el.find('#code').val()});		
		this.model.save(this.attributes,{
	        success: function (model, response) {	        
	           if (response.resp=="ok") {	        	   
	        	   $('div#message-box').text("").append(response.message).removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2000).fadeOut(50);              	           
	        	   Backbone.history.navigate("product", true);
	           } else if (response.resp=="error") {
					var btn = '<button type="button" class="close">&times;</button>';
			    	$('div#message-box').text("").append(btn).append(response.message).removeClass().addClass('alert').addClass('alert-error').show();
		        	Backbone.history.navigate("product", true);	        	   
	           } 
	        },
	        error: function (model, response) {	        	
	        	var btn = '<button type="button" class="close">&times;</button>';
		    	$('div#message-box').text("").append(btn).append(response.message).removeClass().addClass('alert').addClass('alert-error').show();
	        	Backbone.history.navigate("product", true);
	        }
	    });
	},
	
	returnOnMain: function () {		
		Backbone.history.navigate("/product", true);				
	},
	
	showHelpDialog : function(){
		if (! $('.helpAreaProduct').data("wysihtml5") )
			$('.helpAreaProduct').wysihtml5();
		$('#myHelp')
		//.appendTo($("body"))
		.modal('show');
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
				Backbone.history.navigate("product/"+this.model.get('id')+'/edit', true);
			},
			
			destroy : function(e){				
				e.preventDefault();
				
				var el = this.$el;
				var thisProduct = this.model;
				
				bootbox.confirm("Are you sure you want to delete this Product?", function(result) {					
					if (result) {
						thisProduct.destroy({
								wait: true,
							    success: function(model, response){							    	
							    	$('div#message-box').text("").append(response.message).removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2000).fadeOut(50);    
						    		el.remove();
							    	Backbone.history.navigate("product", true);
							     },
							     error: function (model, response) {							    	 							    	 
							    	 var btn = '<button type="button" class="close">&times;</button>';
							    	 $('div#message-box').text("").append(btn).append(response.responseText).removeClass().addClass('alert').addClass('alert-error').show();
							    	 Backbone.history.navigate("product", true);
							     }
						 });
						 
					 }
				});							
			}
		});


//Single ProductMeasure (assigned Measures)
App.Views.ProductMeasure = Backbone.View
		.extend({			
			tagName : 'label',
			className: 'checkbox',
			template: _.template($('#single-product-measure').html()),		
			
			render : function() {				
				this.$el.html(this.template());
				return this;
			}			
			
		});




