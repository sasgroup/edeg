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
		if (!this.model.toJSON().id) {
			this.model.set("state" , "New");
		};
		this.$el.html(this.template(this.model.toJSON()));
				
		//append Measures				
		this.appendProductMeasures();
						
		//append Hospitals
		var this_product = this;		
		$.each(this.model.get('hospitals'), function(i, product_hospital){
			this_product.appendHospital(product_hospital);
		});
				
		$('.helpArea').wysihtml5();		
		return this;
	},


	appendProductMeasures : function(){
		var temp = _.template($('#single-product-measure').html());		
		var checked = [];
		var unchecked = [];
				
		var mids = _.pluck(this.model.get('measures'), 'mid');					
		var measure_ids = App.measures.pluck('id');
				
		App.measures.forEach(function(m){			
			if (window.console) console.log(m);		
			if (_.contains(mids, m.get('id'))) {				
				checked.push({name:m.get('name'),id:m.get('id')});
			} else {
				unchecked.push({name:m.get('name'),id:m.get('id')});
			}
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
		if (window.console) console.log(e.target.value + ' ' + e.target.id + ' ' + e.target.checked+ ' '+e.target.name);		
		if (e.target.name == 'measure' ) {
			if ( e.target.checked ) {
				if (window.console) console.log("Push measure");
				var measures = this.model.get("measures");
				measures.push({"mid" : e.target.id, "mname" : e.target.value});
				this.model.set("measures" , measures);
			} else {
				if (window.console) console.log("Remove measures");
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
		if (window.console) console.log(e.target.name);
		this.model.attributes[e.target.name] = $(e.target).val();
		if (window.console) console.log(this.model.attributes);
	},
	
	submProduct : function(e) {
		e.preventDefault();
		this.model.attributes.help = $('.helpArea').val();			
		this.model.set({code:this.$el.find('#code').val()});		
		this.model.save(this.attributes,{
	        success: function (model, response) {
	        	if (window.console) console.log(response);
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
	},
	
	showHelpDialog : function(){
		if (! $('.helpArea').data("wysihtml5") )
			$('.helpArea').wysihtml5();
		$('#myHelp').modal('show');
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
				if (window.console) console.log(this.model);
				if (window.console) console.log("goToEdit",this.model.get('id'));							
				Backbone.history.navigate("product/"+this.model.get('id')+'/edit', true);
			},
			
			destroy : function(e){
				if (window.console) console.log("destroy");
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
				    	 if (window.console) console.log(response);
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
				if (window.console) console.log("name:" + this.name);
				this.$el.html(this.template());
				return this;
			}			
			
		});




