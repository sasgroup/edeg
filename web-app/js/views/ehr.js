// List of Ehrs
App.Views.Ehrs = Backbone.View.extend({
	template : _.template($('#ehr-list-template').html()),

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
		var view = new App.Views.SingleEhr({
			model : dataElement
		});
		this.$el.find('#table_items tbody').append(view.render().el);
	},

	createDataElement : function() {
		console.log("create EHR");
		Backbone.history.navigate("element/new", true)
	}
});

//Single Ehr
App.Views.SingleEhr = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-ehr').html()),			
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