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


// Edit/New Ehr
App.Views.Ehr = Backbone.View.extend({
	template : _.template($('#ehr-template').html()),

	events : {
		'submit' : 'editEhr',
		'change #code, #name, #notes' : 'changeVal'
	},
	
	
	render : function() {				
		if (!this.model.toJSON().id) {
			this.model.set("state" , "New");
		};
		this.$el.html(this.template(this.model.toJSON()));
		this.model.get('hospitals').forEach(this.appendHospital,this);		
				
		this.appendDataElementsDefault();		
		this.$el.find('.slcEHR').append(this.elementOptions());	
		
		return this;
	},
	
	elementOptions: function() {
		var temp = _.template($('#default-element-option').html());
		var html= '';		
		App.dataElements.each(function(element) {
			html = html + temp({id:'e'+element.get('id'), name:element.get('name')});
		});			
		return html;
	},
	
	appendDataElementsDefault: function(){
		var table_template = _.template($('#data-elements-default-table').html());		
		this.$el.find('div#elements').append(table_template({ehr_element:"DataElement"}));			
		
		var dataElementDefaults = this.model.get('dataElementDefaults');
		var ehrtbody = this.$el.find('div#elements .ehrTable tbody');
		console.log("dataElementDefaults " + dataElementDefaults);
				
		if (dataElementDefaults !== undefined) {
		  $.each( dataElementDefaults, function( i, dataElementDefault ) {				
			console.log(JSON.stringify(dataElementDefault));
			var view = new App.Views.DataElementsDefault({ model : dataElementDefault, default_element: "element"});		
			var dataElementDefaultRow = view.render().el;
			$(ehrtbody).append(dataElementDefaultRow);	
			$(dataElementDefaultRow).find(".slcCodeType").val(dataElementDefault.codeType.name);
			$(dataElementDefaultRow).find(".slcValueType").val(dataElementDefault.valueType.name);
		  });	
		}
				
		if ((dataElementDefaults == undefined)||(dataElementDefaults.length == 0)) { 	
			var emptyDataElementDefault = {"location":"","sourceEHR":"","valueType":{"enumType":"","name":""},"codeType":{"enumType":"","name":""}};		
			var view = new App.Views.DataElementsDefault({ model : emptyDataElementDefault, default_element: "element"});	
			var dataElementDefaultRow = view.render().el;
			$(ehrtbody).append(dataElementDefaultRow);			
		}		
	},
	
	
	changeVal : function(e) {
		console.log(e.target.name);
		this.model.attributes[e.target.name] = $(e.target).val();
		console.log(this.model.attributes);
	},
	
	appendHospital : function(ehr_hospital){
		var temp = _.template($('#single-ehr-hospital').html());
		this.$el.find('div#hospitals').append(temp({name:ehr_hospital.hname}));		
	},
	
		
	/*appendDataElement : function(dem_element){
		var temp = _.template($('#single-data-elements-def-element').html());		
		console.log(dem_element);
		this.$el.find('#dataElementsTable tbody').append(temp({is_imo:dem_element.isIMO, 
															   loc:dem_element.location,
															   query_mnemonic:dem_element.queryMnemonic,
															   value_set:dem_element.valueSet,
															   value_set_req:dem_element.valueSetRequired,
															   location_type:dem_element.locationtype.name}));
		
	},*/
	
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
			
			destroy : function(e){
				console.log("destroy");
				e.preventDefault();
				
				var el = this.$el;
				
				this.model.destroy({
					wait: true,
				    success: function(model, response){
				    	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
			    		el.remove();
				    	Backbone.history.navigate("ehr", true);
				     },
				     error: function (model, response) {
				    	 console.log(response);
				    	 $('div#message-box').text("").append(response.responseText).fadeIn(500).delay(1500).fadeOut(500);
				            Backbone.history.navigate("ehr", true);
				     }
				});
				
				
				//this.model.destroy();				  
				//this.$el.remove();  
			}
		});