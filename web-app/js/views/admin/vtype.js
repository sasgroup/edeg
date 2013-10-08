// List of Products
App.Views.ValueTypes = Backbone.View.extend({
	template : _.template($('#vtypes-list-template').html()),
	
	events : {
	//	'click #create_vtype' : 'createValueType'
	},

	render : function() {
		//this.$el.empty();
		this.$el.html(this.template());
		//this.collection.each(this.appendProduct, this);
		return this;
	},
	
	createValueType : function() {
		alert("createValueType");
	}

});

