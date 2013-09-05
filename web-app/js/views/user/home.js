App.Views.Home = Backbone.View.extend({
	template : _.template($('#home-page').html()),

	render : function() {		
		this.$el.html(this.template());		
		return this;
	}
});
