// List of DataElements
App.Views.DataElements = Backbone.View.extend({
	template : _.template($('#element-list-template').html()),

	events : {
		'click #create_dataElement' : 'createDataElement'
	},

	render : function() {		
		this.$el.html(this.template({
			measures : this.collection
		}));
		this.collection.each(this.appendDataElement, this);
		return this;
	},

	appendDataElement : function(dataElement) {
		var view = new App.Views.SingleDataElement({
			model : dataElement
		});
		this.$el.find('#table_items tbody').append(view.render().el);
	},

	createDataElement : function() {
		console.log("createDataElement");
		Backbone.history.navigate("element/new", true)
	}
});

//Single DataElement
App.Views.SingleDataElement = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-element').html()),			
			events : {
				'click #edit' : 'goToEdit',
				'click #destroy' : 'destroy'
			},

			render : function() {
				this.$el.html(this.template(this.model.toJSON()));
				return this;
			},

			goToEdit : function() {
				console.log("goToEdit");
			},
			
			destroy : function(){
				console.log("destroy");
			}
		});