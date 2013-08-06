//List of HospitalElements
App.Views.HospitalElements = Backbone.View.extend({
	template : _.template($('#user-data-element').html()),

	render : function() {		
		this.$el.html(this.template({ hospitals : this.collection }));
		this.collection.each(this.appendHospitalElement, this);
		return this;
	},

	appendHospitalElement : function(hospitalElement) {
		var view = new App.Views.SingleHospitalElement({ model : hospitalElement});		
		this.$el.find('#hospital-elements tbody').append(view.render().el);		
	}	
});

//Single HospitalElement
App.Views.SingleHospitalElement = Backbone.View
.extend({
	tagName : 'tr',
	template: _.template($('#user-data-elements').html()),			
					
	render : function() {			
		var ch  = (this.model.get('sourceEHR'))  ? "checked" : "";		
		this.model.set({chd:ch});
		console.log(this.model.toJSON());
		this.$el.html(this.template(this.model.toJSON()));				
		
		this.$el.find(".slcCodeType").val(this.model.get('codeType').name);
		this.$el.find(".slcValueType").val(this.model.get('valueType').name);
		
		return this;
	}
});	