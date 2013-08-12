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
		if (window.console) console.log("create EHR");
		Backbone.history.navigate("ehr/new", true)
	}
});


// Edit/New Ehr
App.Views.Ehr = Backbone.View.extend({
	template : _.template($('#ehr-template').html()),
	events : {
		'submit' : 'editEhr',
		'click button#cancel' : 'returnOnMain', 
		'change #code, #name, #notes' : 'changeVal'		
	},
	
	
	render : function() {	
		if (!this.model.toJSON().id) {
			this.model.set("state" , "New");
		};
		this.model.timeId = -1;
		this.$el.html(this.template(this.model.toJSON()));
		this.model.get('hospitals').forEach(this.appendHospital,this);		
		
		this.appendDataElementsDefault();	
		
		return this;
	},
		
	elementOptions: function() {
		var temp = _.template($('#default-element-option').html());
		var html= '';		
		App.dataElements.each(function(element) {
			html = html + temp({id:'e'+element.get('id'), code:element.get('code')});
		});			
		return html;
	},
	
	appendDataElementsDefault: function(){
		var table_template = _.template($('#data-elements-default-table').html());		
		this.$el.find('div#elements').append(table_template({ehr_element:"DataElement"}));			
		
		var dataElementDefaults = this.model.get('dataElementDefaults');
		var ehrtbody = this.$el.find('div#elements .ehrTable tbody');
		var optionsList = this.elementOptions();
				
		if (dataElementDefaults !== undefined) {
		  $.each( dataElementDefaults, function( i, dataElementDefault ) {
			dataElementDefault.parent = "ehr";
			var view = new App.Views.DataElementsDefault({ model : dataElementDefault, default_element: "element", parent:"ehr"});		
			var dataElementDefaultRow = view.render().el;
			$(ehrtbody).append(dataElementDefaultRow);	
			$(dataElementDefaultRow).find(".slcCodeType").val(dataElementDefault.codeType.name);
			$(dataElementDefaultRow).find(".slcValueType").val(dataElementDefault.valueType.name);
						
			$(dataElementDefaultRow).find('.slcParent').append(optionsList);			
			$(dataElementDefaultRow).find(".slcParent").val("e"+dataElementDefault.linkId);			
		  });	
		}
				
		if ((dataElementDefaults == undefined)||(dataElementDefaults.length == 0)) { 	
			var emptyDataElementDefault = {"id":"-1","linkId":"1","location":"","sourceEHR":"","valueType":{"enumType":"","name":""},"codeType":{"enumType":"","name":""}};		
			emptyDataElementDefault.parent = "ehr";
			this.model.timeId = -2;
			var dataElementDefaults = this.model.get('dataElementDefaults');
			dataElementDefaults.push(emptyDataElementDefault);
			this.model.set("dataElementDefaults" , dataElementDefaults);
			
			var view = new App.Views.DataElementsDefault({ model : emptyDataElementDefault, default_element: "element", parent:"ehr"});	
			var dataElementDefaultRow = view.render().el;
			
			$(ehrtbody).append(dataElementDefaultRow);
			$(dataElementDefaultRow).find('.slcParent').append(optionsList);			
			$(dataElementDefaultRow).find(".slcParent").val("e"+emptyDataElementDefault.linkId);		
		}		
	},
	
	
	changeVal : function(e) {
		this.model.attributes[e.target.name] = $(e.target).val();
	},
	
	appendHospital : function(ehr_hospital){
		var temp = _.template($('#single-ehr-hospital').html());
		this.$el.find('div#hospitals').append(temp({name:ehr_hospital.hname}));		
	},
	
		
	/*appendDataElement : function(dem_element){
		var temp = _.template($('#single-data-elements-def-element').html());		
		if (window.console) console.log(dem_element);
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
	        	if (window.console) console.log(response);
	           $('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
               Backbone.history.navigate("ehr", true);
	        },
	        error: function (model, response) {
	        	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
	            //Backbone.history.navigate("ehr", true);
	        }
	    });
	},
	
	returnOnMain: function () {		
		Backbone.history.navigate("/ehr", true);				
	}
	
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
				if (window.console) console.log(this.model);
				if (window.console) console.log("goToEdit",this.model.get('id'));							
				Backbone.history.navigate("ehr/"+this.model.get('id')+'/edit', true);
			},
			
			destroy : function(e){
				if (window.console) console.log("destroy");
				e.preventDefault();
				
				if (confirm('Are you sure you want to delete this EHR?')) {
				
				var el = this.$el;
				
				this.model.destroy({
					wait: true,
				    success: function(model, response){
				    	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
			    		el.remove();
				    	Backbone.history.navigate("ehr", true);
				     },
				     error: function (model, response) {
				    	 if (window.console) console.log(response);
				    	 $('div#message-box').text("").append(response.responseText).fadeIn(500).delay(1500).fadeOut(500);
				            Backbone.history.navigate("ehr", true);
				     }
				});
				
				}
				  
			}
		});