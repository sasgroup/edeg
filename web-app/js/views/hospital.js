// List of Hospitals
App.Views.Hospitals = Backbone.View.extend({
	template : _.template($('#hospital-list-template').html()),

	events : {
		'click #create_hospital' : 'createHospital'
	},

	render : function() {		
		this.$el.html(this.template({
			hospitals : this.collection
		}));
		this.collection.each(this.appendHospital, this);
		return this;
	},

	appendHospital : function(hospital) {
		var view = new App.Views.SingleHospital({
			model : hospital
		});
		this.$el.find('#table_items tbody').append(view.render().el);
	},

	createHospital : function() {
		console.log("createHospital");
		Backbone.history.navigate("hospital/new", true)
	}
});


//New/Edit Hospital
App.Views.Hospital = Backbone.View.extend({
	template : _.template($('#hospital-template').html()),

	events : {
		'submit' : 'submHospital'//,
		/*'change #code, #name, #notes' : 'changeVal',
		'change .checkbox' : 'changeCh'*/
		//'click input[name=multiselect_example]' : 'chooseProduct'	
			
	},
	
	render : function() {	
		if (!this.model.toJSON().id) {
			this.model.set("state" , "New");
		};
		this.$el.html(this.template(this.model.toJSON()));			
		return this;
	},

	chooseProduct : function (ch) {
		var isSelected = $(ch).prop('aria-selected');
		console.log("You clicked on " + ch.value + " " + isSelected);
		this.$el.find('div#myTabContent').append('<div id="product111" class="tab-pane fade"></div>');
		this.$el.find('ul#myTab').append('<li class=""><a data-toggle="tab" href="#product111">Product1</a></li>');         
	},
	
	submHospital : function(e) {
		e.preventDefault();		

		this.model.save(this.attributes,{
	        success: function (model, response) {
	           console.log(response);
	           $('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
               Backbone.history.navigate("hospital", true);
	        },
	        error: function (model, response) {
	        	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
	            Backbone.history.navigate("hospital", true);
	        }
	    });
	},
	
});

//Single Hospital
App.Views.SingleHospital = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-hospital').html()),			
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