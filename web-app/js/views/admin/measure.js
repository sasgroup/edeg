// List of Measures
App.Views.Measures = Backbone.View.extend({
	//template for the view
	template : _.template($('#measure-list-template').html()),

	//listen for events
	events : {
		'click #create_measure' : 'createMeasure'
	},

	//render view for List of Measures
	render : function() {		
		this.$el.html(this.template({measures : this.collection}));
		this.collection.each(this.appendMeasure, this);
				
		$('.helpAreaMeasure').wysihtml5();
		return this;
	},

	//render single measure in the table of measures
	appendMeasure : function(measure) {
		var view = new App.Views.SingleMeasure({model : measure});
		this.$el.find('#table_items tbody').append(view.render().el);
	},

	//redirect to Measure:New page
	createMeasure : function() {		
		Backbone.history.navigate("measure/new", true)
	}
});


// Edit/New Measure
App.Views.Measure = Backbone.View.extend({
	//template for the view
	template : _.template($('#measure-template').html()),

	//listen for events
	events : {
		'submit' 			  				  : 'editMeasure',
		'click button#cancel' 				  : 'returnOnMain', 
		'change #name, #notes' 				  : 'changeVal',
		'change #measureCategory, #cqmDomain' : 'changeDr',
		'change .checkbox' 					  : 'changeCh',
		'click #btnHelp' 					  : 'showHelpDialog'
	},
	
	//render view for Measure:New(Edit) form
	render : function() {		
		var state = (this.model.isNew())? "Add New Measure":"Edit Measure"; 
		this.model.set("state", state);
		
		this.$el.html(this.template(this.model.toJSON()));
				
		//append Products	
		this.appendProducts();	
		
		//append DataElements
		this.appendDataElements();			
			
		//append MeasureCategories	
		var this_measure = this;		
		App.measureCategories.each(function( measure_category){
			this_measure.appendMeasureCategory(measure_category);			
		});
						
		//append CqmDomains
		App.cqmDomains.each(function(cqm_domain){
			this_measure.appendCqmDomain(cqm_domain);			
		});	
		
		return this;
	},
			
	// render Products referred to the measure
	appendProducts : function(){
		var temp = _.template($('#single-measure-product').html());		
		var checked = [];
		var unchecked = [];
						
		var pids = _.pluck(this.model.get('products'), 'pid');					
								
		App.products.forEach(function(p){	
			if (_.contains(pids, p.get('id'))) {				
				checked.push({name:p.get('name'),id:p.get('id')});
			} else {
				unchecked.push({name:p.get('name'),id:p.get('id')});
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
		
		for(var measure_product in checked) {
			var product = checked[measure_product];			
			this.$el.find('div#products').append(temp({name:product.name,id:product.id,ch:'checked'}));					
		}	
		
		for(var measure_product in unchecked) {
			var product = unchecked[measure_product];			
			this.$el.find('div#products').append(temp({name:product.name,id:product.id,ch:''}));
		}	
	},
	
	//render DataElements assigned to the Measure
	appendDataElements : function(){
		var temp = _.template($('#single-measure-element').html());
		var checked = [];
		var unchecked = [];
						
		var dids = _.pluck(this.model.get('dataElements'), 'did');					
								
		App.dataElements.forEach(function(e){	
			if (_.contains(dids, e.get('id'))) {				
				checked.push({name:e.get('name'),id:e.get('id')});
			} else {
				unchecked.push({name:e.get('name'),id:e.get('id')});
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

		
		for(var measure_element in checked) {
			var element = checked[measure_element];			
			this.$el.find('div#elements').append(temp({name:element.name,id:element.id,ch:'checked'}));
		}	
		
		for(var measure_element in unchecked) {
			var element = unchecked[measure_element];			
			this.$el.find('div#elements').append(temp({name:element.name,id:element.id,ch:''}));
		}	
	},
	
	// select MeasureCategory 
	appendMeasureCategory : function(measure_category){
		var temp = _.template($('#single-measure-category').html());
		var sel = "";
		if (this.model.get('measureCategory')){
			if (this.model.get('measureCategory').id == measure_category.get("id"))
				sel="selected";
		}
		this.$el.find('#measureCategory').append(temp({selected:sel, id:measure_category.get("id"),name:measure_category.get('name')}));
	},
	
	// select CqmDomain 
	appendCqmDomain : function(cqmComain){
		var temp = _.template($('#single-measure-domain').html());
		var sel = "";
		if (this.model.get('cqmDomain')){
			if (this.model.get('cqmDomain').id == cqmComain.get("id"))
				sel="selected";
		}
		this.$el.find('#cqmDomain').append(temp({selected:sel, id:cqmComain.get("id"),name:cqmComain.get('name')}));		
	},
		
	//set model attributes 
	changeDr : function(e) {
		this.model.attributes[e.target.id] = {id : e.target.value};
	},
	
	//set model attributes 
	changeVal : function(e) {
		this.model.attributes[e.target.name] = $(e.target).val();
	},
	
	//save a checkbox state
	changeCh : function(e) {		
		if (e.target.name == 'product' ) {
			if ( e.target.checked ) {				
				var products = this.model.get("products");
				products.push({"pid" : e.target.id, "pname" : e.target.value});
				this.model.set("products" , products);
			} else {				
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
				var dataElements = this.model.get("dataElements");
				dataElements.push({"did" : e.target.id, "dname" : e.target.value});
				this.model.set("dataElements" , dataElements);
			} else {				
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
	},
	
	// save Measure
	editMeasure : function(e) {
		e.preventDefault();
		this.model.attributes.help = $('.helpAreaMeasure').val();	
		this.model.set({code:this.$el.find('#code').val()});
		this.model.set({"measureCategory":{"id": this.$el.find('#measureCategory').val()} });
		
		this.model.save(this.attributes,{
	        success: function (model, response) {	         
	        	if (response.resp=="ok") {	        	   
		        	   $('div#message-box').text("").append(response.message).removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2000).fadeOut(50);              	           
		        	   Backbone.history.navigate("measure", true);
		           } else if (response.resp=="error") {
						var btn = '<button type="button" class="close">&times;</button>';
				    	$('div#message-box').text("").append(btn).append(response.message).removeClass().addClass('alert').addClass('alert-error').show();
			        	Backbone.history.navigate("measure", true);	        	   
		           } 	
	        },
	        error: function (model, response) {
	        	var btn = '<button type="button" class="close">&times;</button>';
		    	$('div#message-box').text("").append(btn).append(response.message).removeClass().addClass('alert').addClass('alert-error').show();
	            Backbone.history.navigate("measure", true);
	        }
	    });
	},
	
	//redirect to the list of measures
	returnOnMain: function () {		
		Backbone.history.navigate("/measure", true);				
	},
	
	//show help
	showHelpDialog : function(){
		if (! $('.helpAreaMeasure').data("wysihtml5") )
			$('.helpAreaMeasure').wysihtml5();
		$('#myHelp')
		//.appendTo($("body"))
		.modal('show');
	}
});	

// render Single Measure in the table of Measures
App.Views.SingleMeasure = Backbone.View.extend({
			tagName : 'tr',
			template: _.template($('#single-measure').html()),	
			//listen for events
			events : {
				'click #edit'    : 'goToEdit',
				'click #destroy' : 'destroy'
			},
			
			//render a single row
			render : function() {
				this.$el.html(this.template(this.model.toJSON()));
				return this;
			},

			//redirect to the Measure:Edit page
			goToEdit : function() {				
				Backbone.history.navigate("measure/"+this.model.get('id')+'/edit', true);
			},
			
			//delete a Measure
			destroy : function(e){				
				e.preventDefault();
				
				var el = this.$el;
				var thisMeasure = this.model;
								
				bootbox.confirm("Are you sure you want to delete this Measure?", function(result) {					
					if (result) {
						thisMeasure.destroy({
							wait: true,
						    success: function(model, response){							    	
						    	$('div#message-box').text("").append(response.message).removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2000).fadeOut(50);    
					    		el.remove();
						    	Backbone.history.navigate("measure", true);
						     },
						     error: function (model, response) {						    								    	 
						    	 var btn = '<button type="button" class="close">&times;</button>';
						    	 $('div#message-box').text("").append(btn).append(response.responseText).removeClass().addClass('alert').addClass('alert-error').show();
						    	 Backbone.history.navigate("measure", true);
						     }
					 });	
							
					}
				});		
			}
});
