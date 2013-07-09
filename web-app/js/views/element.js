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
		Backbone.history.navigate("element/new", true)
	}
});


// Edit/New DataElement
App.Views.DataElement = Backbone.View.extend({
	template : _.template($('#element-template').html()),

	events : {
		'submit' : 'editDataElement',
		'change #code, #name, #notes' : 'changeVal',
		'change .checkbox' : 'changeCh'
	},
	
	render : function() {	
		if (!this.model.toJSON().id) {
			this.model.set("state" , "New");
		};
		console.log(this.model.toJSON());	
		this.$el.html(this.template(this.model.toJSON()));
		App.measures.forEach(this.appendMeasure,this);	
		this.appendDataElements();
		
		console.log("App.ehrs " + App.ehrs);
		App.ehrs.forEach(this.appendEhrOption,this);		
		
		return this;
	},
	
	appendEhrOption: function(ehr) {
		console.log("ehr " + JSON.stringify(ehr));
		var temp = _.template($('#ehr-option').html());
		this.$el.find('#slcEHR').append(temp({id:'e'+ehr.get('id'), name:ehr.get('name')}));		
	},
	
	
	appendDataElements: function(){
		var table_template = _.template($('#data-elements-default-table').html());		
		this.$el.find('div#ehrs').append(table_template());			
		
		var dataElementDefaults = this.model.get('dataElementDefaults');
		var ehrtbody = this.$el.find('div#ehrs .ehrTable tbody');
				
		$.each( dataElementDefaults, function( i, dataElementDefault ) {				
			console.log(JSON.stringify(dataElementDefault));
			var view = new App.Views.DataElementsDefault({ model : dataElementDefault});			
			$(ehrtbody).append(view.render().el);	
		});	
				
		if (dataElementDefaults.length == 0) { 	
			var emptyDataElementDefault = {"location":"","sourceEHR":"","valueType":{"enumType":"","name":""},"codeType":{"enumType":"","name":""}};		
			var view = new App.Views.DataElementsDefault({ model : emptyDataElementDefault});		
			$(ehrtbody).append(view.render().el);
		}		
	},
	
	changeVal : function(e) {
		this.model.attributes[e.target.name] = $(e.target).val();
	},
	changeCh : function(e) {
		console.log(e.target.value + ' ' + e.target.id + ' ' + e.target.checked+ ' '+e.target.name);
		if (e.target.name == 'measure' ) {
			if ( e.target.checked ) {
				console.log("Push measure");
				var measures = this.model.get("measures");
				measures.push({"mid" : e.target.id, "mname" : e.target.value});
				this.model.set("measures" , measures);
			} else {
				console.log("Remove measures");
				var measures = this.model.get("measures");
				var removeIndex; 
				for (var i = 0; i < measures.length; i++) {
					if (measures[i].hid = e.target.id) {
						removeIndex = i;
					}
				}
				measures.splice(removeIndex,1);
			};
		};	
	},
	appendMeasure : function(element_measure){
		var temp = _.template($('#single-element-measure').html());		
		var chd = '';
		this.model.get('measures').forEach(function( measure ) {
			if (measure.mid == element_measure.get('id')) {chd = 'checked';}
		});
		this.$el.find('div#measures').append(temp({name:element_measure.get('name'),id:element_measure.get('id'),ch:chd}));		
	},
	
		
	editDataElement : function(e) {
		e.preventDefault();		

		this.model.save(this.attributes,{
	        success: function (model, response) {
	           console.log(response);
	           $('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
               Backbone.history.navigate("element", true);
	        },
	        error: function (model, response) {
	        	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
	            Backbone.history.navigate("element", true);
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
			
			destroy : function(e){
				console.log("destroy");
				e.preventDefault();
				var el = this.$el;
				
				this.model.destroy({
					wait: true,
				    success: function(model, response){
				    	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
			    		el.remove();
				    	Backbone.history.navigate("element", true);
				     },
				     error: function (model, response) {
				    	 console.log(response);
				    	 $('div#message-box').text("").append(response.responseText).fadeIn(500).delay(1500).fadeOut(500);
				            Backbone.history.navigate("element", true);
				     }
				});
			}
		});

//Single DataElementsDefault
App.Views.DataElementsDefault = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-data-elements-default').html()),			
			
			events : {
				'click #plus-btn' : 'addRow'				
			},
						
			render : function() {								
				this.$el.html(this.template({loc:this.model.location, code_type: this.model.codeType.name, value_type: this.model.valueType.name, ehr: this.model.sourceEHR}));				
				return this;
			},
			
			addRow : function (){
				var emptyDataElementDefault = {"location":"","sourceEHR":"","valueType":{"enumType":"","name":""},"codeType":{"enumType":"","name":""}}; 
				var view = new App.Views.DataElementsDefault({ model : emptyDataElementDefault});
				var ehrtbody = this.$el.closest('tbody');
				$(ehrtbody).append(view.render().el);				
			}
			
		});
