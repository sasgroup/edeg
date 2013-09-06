App.Views.Home = Backbone.View.extend({
	template : _.template($('#home-page').html()),

	render : function() {				
		var hospital = this.model.get('name');
		var pr_ehr = this.model.get('ehr').code;
		var add_ehrs = this.model.get('externalEHRs');
		var codes = _.pluck(this.model.get('products'), 'code');				
		products = codes.join(',');		
		var pop_methode = this.model.get('populationMethod');
		
		this.$el.html(this.template({hospital:hospital, pr_ehr:pr_ehr, add_ehrs:add_ehrs, products:products, pop_methode: pop_methode}));		
		return this;
	}
});
