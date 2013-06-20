// List of Hospitals
App.Views.Hospitals = Backbone.View.extend({
	template : _.template($('#hospital-list-template').html()),

	events : {
		'click #create_hospital' : 'createHospital'
	},

	render : function() {		
		this.$el.html(this.template({
			hospitals : this.collection
		}));
		this.collection.each(this.appendHospital, this);
		return this;
	},

	appendHospital : function(hospital) {
		var view = new App.Views.SingleHospital({
			model : hospital
		});
		this.$el.find('#table_items tbody').append(view.render().el);
	},

	createHospital : function() {
		console.log("createHospital");
		Backbone.history.navigate("hospital/new", true)
	}
});

//Single Hospital
App.Views.SingleHospital = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-hospital').html()),			
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