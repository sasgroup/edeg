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


// Edit/New Measure
App.Views.Measure = Backbone.View.extend({
	template : _.template($('#measure-template').html()),

	events : {
		'submit' : 'editMeasure',
		'click button#cancel' : 'returnOnMain', 
		'change #code, #name, #notes' : 'changeVal',
		'change #measureCategory, #cqmDomain' : 'changeDr',
		'change .checkbox' : 'changeCh',
		'focusout input[name=code]': 'uniqueCodeCheck'
	},
	
	render : function() {		
		if (!this.model.toJSON().id) {
			this.model.set("state" , "New");
		};
		this.$el.html(this.template(this.model.toJSON()));
		App.products.forEach(this.appendProduct,this);		
		App.dataElements.forEach(this.appendDataElement,this);		
		App.measureCategories.forEach(this.appendMeasureCategory,this);	
		App.cqmDomains.forEach(this.appendCqmDomain,this);
		
		return this;
	},
	
	uniqueCodeCheck: function () {
		var cur_code = ''; 
		// get current code (for Edit-form)
		if (this.model.toJSON().id) {
			cur_code = this.model.get('code');
		};
		
		// remove previous error label if exists
		$('input[name=code]').next('label.error').remove();		
		
		// get a new code
		var new_code = $('input[name=code]').val();
		var codes = [];
				
		// create array code
		App.measures.forEach(function(measure){			
			codes.push(measure.get('code'));
		});
		
		// remove from codes current code
		var index = codes.indexOf(cur_code);
		if (index!=-1) {
			codes.splice(index, 1);
		}	
				
		if (codes.indexOf(new_code)!=-1) {
			$('input[name=code]').after('<label class="error">Should be unique</label>');
		}				
	},
	
	changeDr : function(e) {
		this.model.attributes[e.target.id] = {id : e.target.value};
	},
	changeVal : function(e) {
		this.model.attributes[e.target.name] = $(e.target).val();
	},
	appendProduct : function(measure_product){
		var temp = _.template($('#single-measure-product').html());		
		var chd = '';
		this.model.get('products').forEach(function( product ) {
			if (product.pid == measure_product.get('id')) {chd = 'checked';}
		});
		this.$el.find('div#products').append(temp({name:measure_product.get('name'),id:measure_product.get('id'),ch:chd}));		
	},
	changeCh : function(e) {
		console.log(e.target.value + ' ' + e.target.id + ' ' + e.target.checked+ ' '+e.target.name);
		if (e.target.name == 'product' ) {
			if ( e.target.checked ) {
				console.log("Push products");
				var products = this.model.get("products");
				products.push({"pid" : e.target.id, "pname" : e.target.value});
				this.model.set("products" , products);
			} else {
				console.log("Remove products");
				var products = this.model.get("products");
				var removeIndex; 
				for (var i = 0; i < products.length; i++) {
					if (products[i].pid == e.target.id) {
						removeIndex = i;
					}
				}
				products.splice(removeIndex,1);
			}
		};
		if (e.target.name == 'element' ) {
			if ( e.target.checked ) {
				console.log("Push dataElements");
				var dataElements = this.model.get("dataElements");
				dataElements.push({"did" : e.target.id, "dname" : e.target.value});
				this.model.set("dataElements" , dataElements);
			} else {
				console.log("Remove dataElements");
				var dataElements = this.model.get("dataElements");
				var removeIndex; 
				for (var i = 0; i < dataElements.length; i++) {
					if (dataElements[i].did == e.target.id) {
						removeIndex = i;
					}
				}
				dataElements.splice(removeIndex,1);
			};
		};	
		console.log(this.model);
	},
	appendDataElement : function(measure_element){
		var temp = _.template($('#single-measure-element').html());
		var chd = '';
		this.model.get('dataElements').forEach(function( element ) {
			if (element.did == measure_element.get('id')) {chd = 'checked';}
		});
		this.$el.find('div#elements').append(temp({name:measure_element.get('name'),id:measure_element.get('id'),ch:chd}));
	},
	
	appendMeasureCategory : function(measure_category){
		var temp = _.template($('#single-measure-category').html());
		var sel = "";
		if (this.model.get('measureCategory')){
			if (this.model.get('measureCategory').id == measure_category.get("id"))
				sel="selected";
		}
		this.$el.find('#measureCategory').append(temp({selected:sel, id:measure_category.get("id"),name:measure_category.get('name')}));
	},
	
	appendCqmDomain : function(cqmComain){
		var temp = _.template($('#single-measure-domain').html());
		var sel = "";
		if (this.model.get('cqmDomain')){
			if (this.model.get('cqmDomain').id == cqmComain.get("id"))
				sel="selected";
		}
		this.$el.find('#cqmDomain').append(temp({selected:sel, id:cqmComain.get("id"),name:cqmComain.get('name')}));
	},

	editMeasure : function(e) {
		e.preventDefault();
		
		this.uniqueCodeCheck();
		
		this.model.save(this.attributes,{
	        success: function (model, response) {
	           console.log(response);
	           $('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
               Backbone.history.navigate("measure", true);
	        },
	        error: function (model, response) {
	        	$('div#message-box').text("").append(response.responseText).fadeIn(500).delay(1500).fadeOut(500);
	            Backbone.history.navigate("measure", true);
	        }
	    });
	},
	
	returnOnMain: function () {		
		Backbone.history.navigate("/measure", true);				
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
				Backbone.history.navigate("measure/"+this.model.get('id')+'/edit', true);
			},
			
			destroy : function(e){
				console.log("destroy");
				e.preventDefault();
				
				if (confirm('Are you sure you want to delete this Measure?')) {
				var el = this.$el;
				
				this.model.destroy({
					wait: true,
				    success: function(model, response){
				    	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
			    		el.remove();
				    	Backbone.history.navigate("measure", true);
				     },
				     error: function (model, response) {
				    	 console.log(response);
				    	 $('div#message-box').text("").append(response.responseText).fadeIn(500).delay(1500).fadeOut(500);
				            Backbone.history.navigate("measure", true);
				     }
				});
				}
			}
		});
