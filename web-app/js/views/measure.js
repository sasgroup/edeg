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

// Edit Measure
App.Views.EditMeasure = Backbone.View.extend({
	template : _.template($('#measure-edit').html()),

	events : {
		'submit' : 'editMeasure'
		/*'change #code, #name, #notes' : 'changeVal',
		'change .checkbox' : 'changeCh'*/
	},
	
	render : function() {				
		console.log(this.model.toJSON());	
		this.$el.html(this.template(this.model.toJSON()));
		//App.products.forEach(this.appendMeasureProduct,this);		
		//App.dataElements.forEach(this.appendMeasureElement,this);			
		return this;
	},

	appendMeasureProduct : function(measure_product){
		var temp = _.template($('#single-measure-product').html());		
		var chd = '';
		this.model.get('products').forEach(function( product ) {
			if (product.mid == measure_product.get('id')) {chd = 'checked';}
		});
		this.$el.find('div#product').append(temp({name:measure_product.get('name'),id:measure_product.get('id'),ch:chd}));		
	},
	
	appendMeasureElement : function(measure_element){
		var temp = _.template($('#single-measure-element').html());
		var chd = '';
		this.model.get('elements').forEach(function( element ) {
			if (element.hid == measure_element.get('id')) {chd = 'checked';}
		});
		this.$el.find('div#data-element').append(temp({name:measure_element.get('name'),id:measure_element.get('id'),ch:chd}));
	},

	editMeasure : function(e) {
		e.preventDefault();		

		this.model.save(this.attributes,{
	        success: function (model, response) {
	           console.log(response);
 	           App.mesageDialog = new App.Models.MesageDialog({resp:"ok"});			
	           console.log(App.mesageDialog.resp);
               Backbone.history.navigate("product", true);
	        },
	        error: function (model, response) {
	            console.log("error");
	            Backbone.history.navigate("product", true);
	        }
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
				console.log(this.model);
				console.log("goToEdit",this.model.get('id'));							
				Backbone.history.navigate("measure/"+this.model.get('id')+'/edit', true);
			},
			
			destroy : function(){
				console.log("destroy");
			}
		});
