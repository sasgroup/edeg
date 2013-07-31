// List of User's Hospitals
App.Views.UserHospitals = Backbone.View.extend({
	render : function() {		
		this.$el.html(this.template({
			hospitals : this.collection
		}));
		this.collection.each(this.appendHospital, this);
		return this;
	},

	appendHospital : function(hospital) {
	/*	var view = new App.Views.SingleHospital({
			model : hospital
		});

		this.$el.find('#table_items tbody').append(view.render().el);
		$('ul#hospital-list-dropdown').append('<li><a href="#">' + hospital.get('name') + '</a></li>');*/
	}	
});