// List of Ehrs
App.Views.Ehrs = Backbone.View.extend({
	template : _.template($('#ehr-list-template').html()),

	events : {
		'click #create_ehr' : 'createEhr'
	},

	render : function() {		
		this.$el.html(this.template({
			ehrs : this.collection
		}));
		this.collection.each(this.appendEhr, this);
		return this;
	},

	appendEhr : function(ehr) {
		var view = new App.Views.SingleEhr({
			model : ehr
		});
		this.$el.find('#table_items tbody').append(view.render().el);
	},

	createEhr : function() {
		console.log("create EHR");
		Backbone.history.navigate("ehr/new", true)
	}
});

// New Ehr
App.Views.NewEhr = Backbone.View.extend({
	template : _.template($('#ehr-new-template').html()),

	events : {		
		'submit' : 'addEhr'/*,
		'change #code, #name, #notes' : 'changeVal',
		'change .checkbox' : 'changeCh'*/
	},

	render : function() {
		console.log(App);
		this.$el.html(this.template());
		//App.hospitals.forEach(this.appendHospital,this);		
		//App.dataElements.forEach(this.appendDataElement,this);		
		return this;
	},
	
	appendHospital : function(ehr_hospital){
		var temp = _.template($('#single-ehr-hospital').html());		
		var chd = '';
		this.model.get('hospitals').forEach(function( hospital ) {
			if (hospital.mid == ehr_hospital.get('id')) {chd = 'checked';}
		});
		this.$el.find('div#hospitals').append(temp({name:ehr_hospital.get('name'),id:ehr_hospital.get('id'),ch:chd}));		
	},
	
	appendDataElement : function(ehr_element){
		var temp = _.template($('#single-ehr-element').html());
		var chd = '';
		this.model.get('elemets').forEach(function( elemet ) {
			if (elemet.hid == ehr_element.get('id')) {chd = 'checked';}
		});
		this.$el.find('div#elements').append(temp({name:ehr_element.get('name'),id:ehr_element.get('id'),ch:chd}));
	},
	
	addEhr : function(e) {
		e.preventDefault();
		console.log("EHR added");

		this.model.save({
			code : this.$('#code').val(),
			name : this.$('#name').val(),
			notes : this.$('#notes').val()
		},
		
		{
		    success: function(model, response) {
		    	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
		        Backbone.history.navigate("ehr", true);
		    }
		
		});			
		
	}

});


// Edit Ehr
App.Views.EditEhr = Backbone.View.extend({
	template : _.template($('#ehr-edit-template').html()),

	events : {
		'submit' : 'editEhr'/*,
		'change #code, #name, #notes' : 'changeVal',
		'change .checkbox' : 'changeCh'*/
	},
	
	render : function() {				
		console.log(this.model.toJSON());	
		this.$el.html(this.template(this.model.toJSON()));
		//App.hospitals.forEach(this.appendHospital,this);		
		//App.dataElements.forEach(this.appendEhr,this);		
		return this;
	},

	appendHospital : function(ehr_hospital){
		var temp = _.template($('#single-ehr-hospital').html());		
		var chd = '';
		this.model.get('hospitals').forEach(function( hospital ) {
			if (hospital.mid == ehr_hospital.get('id')) {chd = 'checked';}
		});
		this.$el.find('div#hospitals').append(temp({name:ehr_hospital.get('name'),id:ehr_hospital.get('id'),ch:chd}));		
	},
	
	appendDataElement : function(ehr_element){
		var temp = _.template($('#single-ehr-element').html());
		var chd = '';
		this.model.get('elemets').forEach(function( elemet ) {
			if (elemet.hid == ehr_element.get('id')) {chd = 'checked';}
		});
		this.$el.find('div#elements').append(temp({name:ehr_element.get('name'),id:ehr_element.get('id'),ch:chd}));
	},
		
	editEhr : function(e) {
		e.preventDefault();		

		this.model.save(this.attributes,{
	        success: function (model, response) {
	           console.log(response);
	           $('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
               Backbone.history.navigate("ehr", true);
	        },
	        error: function (model, response) {
	        	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
	            Backbone.history.navigate("ehr", true);
	        }
	    });
	},
	
});


//Single Ehr
App.Views.SingleEhr = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-ehr').html()),			
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
				Backbone.history.navigate("ehr/"+this.model.get('id')+'/edit', true);
			},
			
			destroy : function(){
				console.log("destroy");
			}
		});