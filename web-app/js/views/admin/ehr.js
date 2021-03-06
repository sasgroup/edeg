// List of Ehrs
App.Views.Ehrs = Backbone.View.extend({
	//template for the view
	template : _.template($('#ehr-list-template').html()),
	//listen for events
	events : {
		'click #create_ehr' : 'createEhr'
	},
	
	//render view for List of EHRs
	render : function() {		
		this.$el.html(this.template({ehrs : this.collection}));
		this.collection.each(this.appendEhr, this);
		return this;
	},

	//render single EHR in the table of EHRs
	appendEhr : function(ehr) {
		var view = new App.Views.SingleEhr({model : ehr});
		this.$el.find('#table_items tbody').append(view.render().el);
	},

	//redirect to EHR:New page
	createEhr : function() {		
		Backbone.history.navigate("ehr/new", true)
	}
});


// Edit/New Ehr
App.Views.Ehr = Backbone.View.extend({
	//template for the view
	template : _.template($('#ehr-template').html()),
	//listen for events
	events : {
		'submit' 				: 'editEhr',
		'click button#cancel'   : 'returnOnMain', 
		'change #name, #notes'  : 'changeVal'		
	},
	
	//render view for EHR:(New/Edit) form
	render : function() {	
		var state = (this.model.isNew())? "Add New EHR":"Edit EHR"; 
		this.model.set("state", state);
		
		this.model.timeId = -1;
		this.$el.html(this.template(this.model.toJSON()));
		
		//append Hospitals
		var this_ehr = this;		
		$.each(this.model.get('hospitals'), function(i, ehr_hospital){
			this_ehr.appendHospital(ehr_hospital);
		});
		
		//append DataElementsDefault
		this.appendDataElementsDefault();	
		
		return this;
	},
		
	//show existing DataElements
	elementOptions: function() {
		var temp = _.template($('#default-element-option').html());
		var html= '';		
		App.dataElements.each(function(element) {
			html = html + temp({id:'e'+element.get('id'), code:element.get('code')});
		});			
		return html;
	},
	
	//show existing valueTypes
	vtypeOptions: function() {
		var temp = _.template($('#default-element-option').html());
		var html= '';		
		App.valuesTypes.each(function(vtype) {
			html = html + temp({id:vtype.get('id'), code:vtype.get('name')});
		});			
		return html;
	},
	
	//render tab for Default Locations
	appendDataElementsDefault: function(){
		var table_template = _.template($('#data-elements-default-table').html());		
		this.$el.find('div#elements').append(table_template({ehr_element:"Data Element"}));			
		
		var dataElementDefaults = this.model.get('dataElementDefaults');
		var ehrtbody = this.$el.find('div#elements .ehrTable tbody');
		var optionsList = this.elementOptions();
		var vtypesList = this.vtypeOptions(); //new
				
		// dataElementDefaults defined
		if (dataElementDefaults !== undefined) {
		  $.each( dataElementDefaults, function( i, dataElementDefault ) {
			dataElementDefault.parent = "ehr";
			var view = new App.Views.DataElementsDefault({ model : dataElementDefault, default_element: "element", parent:"ehr"});		
			var dataElementDefaultRow = view.render().el;
			$(ehrtbody).append(dataElementDefaultRow);			
									
			$(dataElementDefaultRow).find('.slcParent').append(optionsList);			
			$(dataElementDefaultRow).find(".slcParent").val("e"+dataElementDefault.linkId);	
			
			$(dataElementDefaultRow).find('.slcValuesType').append(vtypesList); //new
			
			var de_ids = dataElementDefault.ids;
			var ids=de_ids.split(";");			
			for (var i = 0; i < ids.length; i++) {	
				$(dataElementDefaultRow).find('.slcValuesType option[value='+ids[i]+']').attr("selected","selected") ;
			}		
			
		  });	
		}
				
		// dataElementDefaults not defined
		if ((dataElementDefaults == undefined)||(dataElementDefaults.length == 0)) { 
			var linkId = App.dataElements.at(0).get('id');
			
			var emptyDataElementDefault = {"id":"-1","linkId":linkId,"location":"","sourceEHR":"","valueType":{"enumType":"","name":"NotApplicable"}};		
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
			
			$(dataElementDefaultRow).find('.slcValuesType').append(vtypesList); //new
		}		
	},
	
	//set model attributes 
	changeVal : function(e) {
		this.model.attributes[e.target.name] = $(e.target).val();
	},
	
	//render Hospitals, which use this EHR 
	appendHospital : function(ehr_hospital){
		var temp = _.template($('#single-ehr-hospital').html());
		this.$el.find('div#hospitals').append(temp({name:ehr_hospital.hname}));		
	},
		
	// save EHR
	editEhr : function(e) {
		e.preventDefault();	
			
		var emptyValuesType = _.indexOf(_.pluck(this.model.get('dataElementDefaults'),"ids"), '');		
		if ((emptyValuesType!=-1)&&(this.model.get('dataElementDefaults')[emptyValuesType].location!='')){
			bootbox.alert("Please specify Values Type for [" + this.model.get('dataElementDefaults')[emptyValuesType].location + "] location.", function() {
			});			
			return;			
		}	
				
		this.model.set({code:this.$el.find('#code').val()});
		this.model.save(this.attributes,{
	        success: function (model, response) {
	        	if (response.resp=="ok") {	        	   
		        	   $('div#message-box').text("").append(response.message).removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2000).fadeOut(50);              	           
		        	   Backbone.history.navigate("ehr", true);
		           } else if (response.resp=="error") {
						var btn = '<button type="button" class="close">&times;</button>';
				    	$('div#message-box').text("").append(btn).append(response.message).removeClass().addClass('alert').addClass('alert-error').show();
			        	Backbone.history.navigate("ehr", true);	        	   
		           }              
	        },
	        error: function (model, response) {
	        	var btn = '<button type="button" class="close">&times;</button>';
		    	$('div#message-box').text("").append(btn).append(response.message).removeClass().addClass('alert').addClass('alert-error').show();
	            Backbone.history.navigate("ehr", true);
	        }
	    });
	},
	
	//redirect to the list of EHRs
	returnOnMain: function () {		
		Backbone.history.navigate("/ehr", true);				
	}
	
});


//render Single EHR in the table of EHRs
App.Views.SingleEhr = Backbone.View.extend({
			tagName : 'tr',
			template: _.template($('#single-ehr').html()),	
			//listen for events
			events : {
				'click #edit'    : 'goToEdit',
				'click #destroy' : 'destroy'
			},

			//render a single row
			render : function() {
				this.$el.html(this.template(this.model.toJSON()));
				return this;
			},

			//redirect to the EHR:Edit page
			goToEdit : function() {											
				Backbone.history.navigate("ehr/"+this.model.get('id')+'/edit', true);
			},
			
			//delete an EHR
			destroy : function(e){				
				e.preventDefault();
					
				var el = this.$el;
				var thisEHR = this.model;
				
				bootbox.confirm("Are you sure you want to delete this EHR?", function(result) {					
					if (result) {
						thisEHR.destroy({
								wait: true,
							    success: function(model, response){							    	
							    	$('div#message-box').text("").append(response.message).removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2000).fadeOut(50);    
						    		el.remove();
							    	Backbone.history.navigate("ehr", true);
							     },
							     error: function (model, response) {							    	 							    	 
							    	 var btn = '<button type="button" class="close">&times;</button>';
							    	 $('div#message-box').text("").append(btn).append(response.responseText).removeClass().addClass('alert').addClass('alert-error').show();
							    	 Backbone.history.navigate("ehr", true);
							     }
						 });
						 
					 }
				});		
				  
			}
});