// List of Measures
App.Views.Measures = Backbone.View.extend({
	template : _.template($('#measure-list-template').html()),

	events : {
		'click #create_measure' : 'createMeasure'
	},

	render : function() {		
		this.$el.html(this.template({
			measures : this.collection
		}));
		this.collection.each(this.appendMeasure, this);
		return this;
	},

	appendMeasure : function(measure) {
		var view = new App.Views.SingleMeasure({
			model : measure
		});
		this.$el.find('#table_items tbody').append(view.render().el);
	},

	createMeasure : function() {
		console.log("createMeasure");
		Backbone.history.navigate("measure/new", true)
	}
});

// New Measure
App.Views.Measure = Backbone.View.extend({
	template : _.template($('#measure-template').html()),

	events : {
		'click #assignProduct' : 'assignProduct',
		'click #assignElement' : 'assignElement'
	},

	render : function() {
		this.$el.html(this.template());
		return this;
	},

	assignProduct : function() {
		this.$el.find("#modalProducts input[type='checkbox']:checked").each(
				function(index) {
					$('.checkboxlist').append($(this).closest('label'));
					$(".checkboxlist input[type='checkbox']").bind(
							'click',
							function() {
								var checked = $(this).prop("checked");

								if (!checked) {
									$('.checkboxlistModal').append(
											$(this).closest('label'));
								}
							});
				});
	},

	assignElement : function() {
		this.$el.find("#modalElements input[type='checkbox']:checked").each(
				function(index) {
					console.log($(this).closest('label'));
					$('.checkboxlist-elements')
							.append($(this).closest('label'));

					$(".checkboxlist-elements input[type='checkbox']").bind(
							'click',
							function() {
								var checked = $(this).prop("checked");
								console.log($(this).closest('div'));

								if (!checked) {
									$('.checkboxlistModalElement').append(
											$(this).closest('label'));
								}
							});
				});
	}

});

// Single Measure
App.Views.SingleMeasure = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-measure').html()),			
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
