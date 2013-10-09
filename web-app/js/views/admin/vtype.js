// List of ValuesTypes
App.Views.ValuesTypes = Backbone.View.extend({
	template : _.template($('#vtypes-list-template').html()),
	
	initialize : function() {		
		//this.collection.on('add', this.appendValuesType, this);
		this.collection.on('add', this.render, this);
	},
	
	render : function() {		
		this.$el.html(this.template());
		
		this.collection.each(this.appendValuesType, this);
		return this;
	},
	
	appendValuesType : function(vtype) {
		var view = new App.Views.SingleValuesType({
			model : vtype
		});
		this.$el.find('#table_items tbody').append(view.render().el);
	}
});

//New/Edit ValuesType
App.Views.ValuesType = Backbone.View.extend({
	template : _.template($('#vtype-template').html()),

	events : {
		'submit' : 'saveValuesType'		
	},
	
	render : function() {			
		this.$el.html(this.template());				
		return this;
	},
	
	saveValuesType : function(e) {		
		e.preventDefault();
		
		this.model.set({name:this.$el.find('#name').val()});
		this.model.set({description:this.$el.find('#description').val()});
		
				
		this.model.save(this.attributes,{
	      //this.model.collection.create(this.attributes,{ 
	        success: function (model, response) {
	        	if (response.resp=="ok") {	        	   
		        	   $('div#message-box').text("").append(response.message).removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2000).fadeOut(50);
		        	   //Backbone.history.navigate("types", true);
		        	  
		        	   
		           } else if (response.resp=="error") {
						var btn = '<button type="button" class="close">&times;</button>';
				    	$('div#message-box').text("").append(btn).append(response.message).removeClass().addClass('alert').addClass('alert-error').show();
			        	
		           }              
	        },
	        error: function (model, response) {
	        	var btn = '<button type="button" class="close">&times;</button>';
		    	$('div#message-box').text("").append(btn).append(response.message).removeClass().addClass('alert').addClass('alert-error').show();
	            
	        }
	    });
	}	
});

//Single ValuesType
App.Views.SingleValuesType = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-vtype').html()),		
			events : {
				'click #edit'    : 'goToEdit',
				'click #destroy' : 'destroy'
			},

			render : function() {
				this.$el.html(this.template(this.model.toJSON()));
				return this;
			},
			
			goToEdit : function() {
				/*if (window.console) console.log(this.model);
				if (window.console) console.log("goToEdit",this.model.get('id'));							
				Backbone.history.navigate("product/"+this.model.get('id')+'/edit', true);*/
				$('#name').val(this.model.get('name'));
				$('#description').val(this.model.get('description'));
			},
			
			destroy : function(e){				
				e.preventDefault();
				var el = this.$el;
				var thisVType = this.model;
				
				bootbox.confirm("Are you sure you want to delete this Values Type?", function(result) {					
					if (result) {
						thisVType.destroy({
								wait: true,
							    success: function(model, response){							    	
							    	$('div#message-box').text("").append(response.message).removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2000).fadeOut(50);    
						    		el.remove();							    	
							     },
							     error: function (model, response) {
							    	 if (window.console) console.log(response);							    	 
							    	 var btn = '<button type="button" class="close">&times;</button>';
							    	 $('div#message-box').text("").append(btn).append(response.responseText).removeClass().addClass('alert').addClass('alert-error').show();							    	 
							     }
						 });
						 
					}
				});							
			}
});

