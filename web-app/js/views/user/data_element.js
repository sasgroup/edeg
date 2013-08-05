//USER 
App.Views.UserDataElement = Backbone.View
.extend({
	tagName : 'tr',
	template: _.template($('#user-data-elements').html()),			
					
	render : function() {			
		var ch  = (this.model.get('sourceEHR'))  ? "checked" : "";		
		this.model.set({chd:ch});
		console.log(this.model.toJSON());
		this.$el.html(this.template(this.model.toJSON()));				
		return this;
	}
});	
