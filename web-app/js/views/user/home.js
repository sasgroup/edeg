App.Views.Home = Backbone.View.extend({
	template : _.template($('#home-page').html()),

	render : function() {		
		
		var hospital = this.model.get('name');
		var pr_ehr = this.model.get('ehr').code;
		var add_ehrs = this.model.get('externalEHRs');
		add_ehrs = add_ehrs.replace(/\n/g, ", ");
		
		add_ehrs = add_ehrs.replace(/^\s+|\s+$/g,'');
		
		if (add_ehrs.substr(-1, 1) == ',')	{
			add_ehrs = add_ehrs.substr(0, add_ehrs.length-1);
		}
		
		var codes = _.pluck(this.model.get('products'), 'code');				
		products = codes.join(', ');		
		var pop_methode = this.model.get('populationMethod');
		
		this.$el.html(this.template({hospital:hospital, pr_ehr:pr_ehr, add_ehrs:add_ehrs, products:products, pop_methode: pop_methode}));		
		return this;
	}
});
