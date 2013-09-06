App.Views.Home = Backbone.View.extend({
	template : _.template($('#home-page').html()),

	render : function() {		
		this.$el.html(this.template({hospital:"Community Hospital", pr_ehr:"MEDITECH Version 6.1", add_ehrs:"Picis, ProMed", products: "MU1, MU2", pop_methode: "OBS Services"}));		
		return this;
	}
});
