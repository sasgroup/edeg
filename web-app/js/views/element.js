// List of DataElements
App.Views.DataElements = Backbone.View.extend({
	template : _.template($('#element-list-template').html()),

	events : {
		'click #create_dataElement' : 'createDataElement'
	},

	initialize : function() {		
		this.collection.on('add', this.appendDataElement, this);
		this.collection.on('change', this.render, this);
	},

	render : function() {		
		this.$el.html(this.template({
			dataElements : this.collection
		}));
		this.collection.each(this.appendDataElement, this);
		return this;
	},

	appendDataElement : function(dataElement) {
		var view = new App.Views.SingleDataElement({
			model : dataElement
		});
		this.$el.find('#table_items tbody').append(view.render().el);
	},

	createDataElement : function() {
		console.log("createDataElement");
		Backbone.history.navigate("element/new", true)
	}
});


// New DataElement
App.Views.NewDataElement = Backbone.View.extend({
	template : _.template($('#element-new-template').html()),

	events : {		
		'submit' : 'addDataElement'/*,
		'change #code, #name, #notes' : 'changeVal',
		'change .checkbox' : 'changeCh'*/
	},

	render : function() {
		console.log(App);
		this.$el.html(this.template());
		App.measures.forEach(this.appendMeasure,this);		
		App.ehrs.forEach(this.appendEhr,this);		
		return this;
	},
	
	appendMeasure : function(element_measure){
		var temp = _.template($('#single-element-measure').html());		
		var chd = '';
		/*this.model.get('measures').forEach(function( measure ) {
			if (measure.mid == element_measure.get('id')) {chd = 'checked';}
		});*/
		this.$el.find('div#measures').append(temp({name:element_measure.get('name'),id:element_measure.get('id'),ch:chd}));		
	},
	
	appendEhr : function(element_ehr){
		var temp = _.template($('#single-element-ehr').html());
		var chd = '';
		/*this.model.get('ehrs').forEach(function( ehr ) {
			if (ehr.hid == element_ehr.get('id')) {chd = 'checked';}
		});*/
		this.$el.find('div#ehrs').append(temp({name:element_ehr.get('name'),id:element_ehr.get('id'),ch:chd}));
	},
	
	addDataElement : function(e) {
		e.preventDefault();
		console.log("DataElement added");

		this.model.save({
			code : this.$('#code').val(),
			name : this.$('#name').val(),
			notes : this.$('#notes').val()
		},
		
		{
		    success: function(model, response) {
		    	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
		        Backbone.history.navigate("element", true);
		    }
		
		});			
		
	}

});

// Edit DataElement
App.Views.EditDataElement = Backbone.View.extend({
	template : _.template($('#element-edit-template').html()),

	events : {
		'submit' : 'editDataElement'/*,
		'change #code, #name, #notes' : 'changeVal',
		'change .checkbox' : 'changeCh'*/
	},
	
	render : function() {				
		console.log(this.model.toJSON());	
		this.$el.html(this.template(this.model.toJSON()));
		App.measures.forEach(this.appendMeasure,this);		
		App.ehrs.forEach(this.appendEhr,this);		
		return this;
	},

	appendMeasure : function(element_measure){
		var temp = _.template($('#single-element-measure').html());		
		var chd = '';
		/*this.model.get('measures').forEach(function( measure ) {
			if (measure.mid == element_measure.get('id')) {chd = 'checked';}
		});*/
		this.$el.find('div#measures').append(temp({name:element_measure.get('name'),id:element_measure.get('id'),ch:chd}));		
	},
	
	appendEhr : function(element_ehr){
		var temp = _.template($('#single-element-ehr').html());
		var chd = '';
		/*this.model.get('ehrs').forEach(function( ehr ) {
			if (ehr.hid == element_ehr.get('id')) {chd = 'checked';}
		});*/
		this.$el.find('div#ehrs').append(temp({name:element_ehr.get('name'),id:element_ehr.get('id'),ch:chd}));
	},	
		
	editDataElement : function(e) {
		e.preventDefault();		

		this.model.save(this.attributes,{
	        success: function (model, response) {
	           console.log(response);
	           $('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
               Backbone.history.navigate("product", true);
	        },
	        error: function (model, response) {
	        	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
	            Backbone.history.navigate("product", true);
	        }
	    });
	},
	
});


//Single DataElement
App.Views.SingleDataElement = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-element').html()),			
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
				Backbone.history.navigate("element/"+this.model.get('id')+'/edit', true);
			},
			
			destroy : function(){
				console.log("destroy");
			}
		});