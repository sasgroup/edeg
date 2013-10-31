// List of ValuesTypes
App.Views.ValuesTypes = Backbone.View.extend({
	//template for the view
	template : _.template($('#vtypes-list-template').html()),
	
	//listen for collections events
	initialize : function() {		
		this.collection.on('add', this.render, this);
		this.collection.on('change', this.render, this);
		this.collection.on('destroy', this.render, this);
	},
	
	//render view for ValuesTypes
	render : function() {		
		this.$el.html(this.template());
		
		this.collection.each(this.appendValuesType, this);
		
		var oTable = $('#table_items').dataTable( 
				{	"bDestroy": true,
					"bPaginate": false,
					"bFilter": false,
					"sScrollY": "528px",
					"bSort": true,
		 			"bInfo": false,
		 			"bAutoWidth": false,
		 			"aoColumnDefs": [
		 							{ 'bSortable': false, 'aTargets': [ 2,3 ] }
		 						 ],
					"bScrollCollapse": true,
					"bPaginate": false
				} );
		return this;
	},
	
	//render single ValuesType in the table of ValuesTypes
	appendValuesType : function(vtype) {
		var view = new App.Views.SingleValuesType({model : vtype});
		this.$el.find('#table_items tbody').append(view.render().el);
	}
});

//New/Edit ValuesType
App.Views.ValuesType = Backbone.View.extend({
	//template for the view
	template : _.template($('#vtype-edit').html()),
	
	//listen for events
	events : {
		'submit' 					 : 'saveValuesType',
		'change #name, #description' : 'changeVal'	
	},
	
	//render view for ValuesType:New(Edit) form
	render : function() {		
		var state = (this.model.isNew())? "Add New ":"Edit "; 
		this.$el.html(this.template(this.model.toJSON()));			
		return this;
	},
	
	//set model attributes 
	changeVal : function(e) {		
		this.model.attributes[e.target.name] = $(e.target).val();		
	},
		
	//save ValuesType
	saveValuesType : function(e) {		
		e.preventDefault();			
		//update view
		App.valuesType  = new App.Models.ValuesType();								
		var viewValuesTypeView = new App.Views.ValuesType({model:App.valuesType});
		$('#input_form').html(viewValuesTypeView.render().el);
		App.route.validateValuesTypeForm();		
				
		if (this.model.isNew()) {
	      App.valuesTypes.create(this.model.attributes,{ 
	        success: function (model, response) {
	        	if (response.resp=="ok") {	        	   
		        	   $('div#message-box').text("").append(response.message).removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2000).fadeOut(50);      	   
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
		} else {			
			this.model.save(this.model.attributes, {
				success: function (model, response) {					
		        	if (response.resp=="ok") {	        	   
			        	   $('div#message-box').text("").append(response.message).removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2000).fadeOut(50);        	   
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
	}	
});

//render a single row in the table of ValuesTypes  
App.Views.SingleValuesType = Backbone.View.extend({
			tagName : 'tr',
			template: _.template($('#single-vtype').html()),
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
							
			//render form for editing 
			goToEdit : function() {				
				App.valuesType  = this.model;	
				
				if (this.model.get('name')== 'NotApplicable') {
					bootbox.alert("You can not modify reserved Values Type.", function() {
					});
					return;
				} 
								
				var viewValuesType = new App.Views.ValuesType({model:App.valuesType});
								
				$('#input_form').html(viewValuesType.render().el);
				App.route.validateValuesTypeForm();		
			},
			
			//delete a ValuesType from the table
			destroy : function(e){				
				e.preventDefault();
				var el = this.$el;
				var thisVType = this.model;
				
				if (this.model.get('name')== 'NotApplicable') {
					bootbox.alert("You can not delete reserved Values Type.", function() {
					});
					return;
				} 
				
				bootbox.confirm("Are you sure you want to delete this Values Type?", function(result) {					
					if (result) {						
						thisVType.destroy({
								wait: true,
							    success: function(model, response){	
							    	if (response.resp=="success") {	
							    	$('div#message-box').text("").append(response.message).removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2000).fadeOut(50);
							    	} else {
							    		var btn = '<button type="button" class="close">&times;</button>';
								    	 $('div#message-box').text("").append(btn).append(response.responseText).removeClass().addClass('alert').addClass('alert-error').show();		
							    	}
							     },
							     error: function (model, response) {							    	 							    	 
							    	 var btn = '<button type="button" class="close">&times;</button>';
							    	 $('div#message-box').text("").append(btn).append(response.responseText).removeClass().addClass('alert').addClass('alert-error').show();							    	 
							     }
						 });
						 
					}
				});							
			}
});

