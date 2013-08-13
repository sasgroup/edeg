//List of HospitalElements
App.Views.HospitalElements = Backbone.View.extend({
	template : _.template($('#hospital_data_element-form').html()),
	
	events : {
		'click #resetAll'     : 'resetAllToDefault',
		'click button#cancel' : 'returnToProduct' ,
		'click #save-btn'     : 'saveHospitalElements'
	},

	render : function() {		
		console.log(this.options.measure_code);
		this.$el.html(this.template({ hospitals : this.collection}));
		this.collection.each(this.appendHospitalElement, this);
				
		if ($('#role').val() == 'admin') {
			this.$el.find("h3").html(this.options.measure_code+ ":DataElements");
		}
		
		return this;
	},
	
	resetAllToDefault : function() {		
		this.collection.each(this.restoreHospitalElement, this);
	},

	appendHospitalElement : function(hospitalElement) {
		var view = new App.Views.SingleHospitalElement({ model : hospitalElement});		
		this.$el.find('#hospital-elements tbody').append(view.render().el);		
	},
	
	restoreHospitalElement : function(hospitalElement) {
		//only for checked sourceEHR		
		var cur_row = $('#hospital-elements td#'+hospitalElement.get('id')).closest('tr');
		var ch = $(cur_row).find('.sourceEhr').is(':checked');
			
		if (ch) {
			var view = new App.Views.SingleHospitalElement({ model : hospitalElement});
			$(cur_row).replaceWith(view.render().el);
		}		
	},
	
	returnToProduct : function(){
		//Backbone.history.navigate("product/" + this.options.product_id, true);
		window.history.back();

	},
	
	saveHospitalElements : function() {		
		_.each(this.collection.models, function(model) {
			  return model.save({
			    wait: true,
			    error: function (collection, response) {
		        			if (window.console) console.log("error");	          
		        		}    
			  });
		});	
		
	}
});

//Single Hospital Element
App.Views.SingleHospitalElement = Backbone.View
.extend({
	tagName : 'tr',
	template: _.template($('#hospital_data_element').html()),	
	events : {
		'click .slc_row'  : 'selectRow',
		'click #reset'    : 'resetToDefault',
		'change  #source' : 'changeVal'		
	},
					
	render : function() {			
		var ch  = (this.model.get('sourceEHR'))  ? "checked" : "";		
		this.model.set({chd:ch});
		if (window.console) console.log(this.model.toJSON());
		this.$el.html(this.template(this.model.toJSON()));				
		
		this.$el.find(".slcCodeType").val(this.model.get('codeType').name);
		this.$el.find(".slcValueType").val(this.model.get('valueType').name);
		
		return this;
	},
	
	changeVal : function(e) {
		this.model.attributes[e.target.name] = $(e.target).val();
	},
	
	selectRow: function(event) {		
        // remove selection		
		$('.row_selected').css( "background-color", "#FFFFFF" );
		$('tr.row_selected td:first').css( "background-color", "#FFFFFF" );
		$('.row_selected').removeClass('row_selected');
		
		// add selection
		$(event.target.parentNode).addClass('row_selected');
		$(event.target.parentNode).css( "background-color", "rgb(0, 136, 204, 0.5)" );		
		$('tr.row_selected td:first').css( "background-color", "rgb(0, 136, 204, 0)" );
		
		var he_id = $('tr.row_selected td:first').prop("id");
		// get hospital element by id 
		var slc_hospital_element = App.hospitalElements.get(he_id);

		this.showQA(slc_hospital_element);
		this.showExtraLocation(slc_hospital_element);
		this.showHospitalSpecific(slc_hospital_element);
	},
	
	resetToDefault : function(event) {			
		//var he_id = $(event.target).closest('tr').find('td:first').prop('id');	
		this.$el.html(this.template(this.model.toJSON()));	
		this.$el.find(".slcCodeType").val(this.model.get('codeType').name);
		this.$el.find(".slcValueType").val(this.model.get('valueType').name);
	},
	
	showQA: function(slc_hospital_element){		
		//g&a level1
		var notes = slc_hospital_element.get("notes");
		//g&a level2
		var internalNotes = slc_hospital_element.get("internalNotes");
		//hospital specific
		var hospitalValueSet = slc_hospital_element.get("hospitalValueSet");
		//extra locations
		var elementExtraLocation = slc_hospital_element.get("elementExtraLocation");
		
		//load relevant values
		$("#txt-qa2").val("some notes for " + slc_hospital_element.get("dataElement") );
		$("#txt-qa3").val("some internalNotes for " + slc_hospital_element.get("dataElement") );		
	},
	
	showExtraLocation: function(slc_hospital_element){	
		var extra_tbody = $('#extra-table tbody');
		$(extra_tbody).empty();						
		var extra_view = new App.Views.ExtraDataElement({ model : slc_hospital_element});		
		if (window.console) console.log(slc_hospital_element);
		var extra_row = extra_view.render().el;
		$(extra_tbody).append(extra_row);		
		$(extra_row).find(".slcCodeType").val(slc_hospital_element.get('codeType').name);
		$(extra_row).find(".slcValueType").val(slc_hospital_element.get('valueType').name);
	},
	
	showHospitalSpecific: function(slc_hospital_element){		
		var hospital_specific_tbody = $('#hospital-specific-table tbody');
		$(hospital_specific_tbody).empty();		
								
		var hospitalSpecific =	{
				  code:      '',
				  codeType:  slc_hospital_element.get('codeType'),
				  mnemonic:  ''
		};
		
		var hospital_specific_model = new App.Models.HospitalSpecific(hospitalSpecific);						
		var hospital_specific_view = new App.Views.HospitalSpecific({ model : hospital_specific_model});
		var hospital_specific_row = hospital_specific_view.render().el;
		$(hospital_specific_tbody).append(hospital_specific_row);	
		$(hospital_specific_row).find(".slcCodeType").val(slc_hospital_element.get('codeType').name);	
		
	}
});	

//EXTRA
App.Views.ExtraDataElement = Backbone.View
.extend({
	tagName : 'tr',
	template: _.template($('#extra-elements').html()),		
	
	events : {
		'click #plus-btn' : 'addRow',
		'click #minus-btn': 'removeRow'		
	},
					
	render : function() {			
		var ch  = (this.model.get('sourceEHR'))  ? "checked" : "";		
		this.model.set({chd:ch});
		if (window.console) console.log(this.model.toJSON());
		this.$el.html(this.template(this.model.toJSON()));				
		return this;
	},
	
	addRow : function (event){		
		if (window.console) console.log("add extra row");
		
		var extraDataElement =	{
				  location:  this.model.get('location'),
				  sourceEHR: this.model.get("sourceEHR"),
				  source:    this.model.get("source"),
				  codeType:  this.model.get("codeType"),
				  valueType: this.model.get("valueType")
		};
		
		var extra_model = new App.Models.ExtraDataElement(extraDataElement);
		var extra_view = new App.Views.ExtraDataElement({ model : extra_model});		
		
		var extra_tbody = $('#extra-table tbody');
		var extra_row = extra_view.render().el;
		$(extra_tbody).append(extra_row);
		$(extra_row).find(".slcCodeType").val(extra_model.get('codeType').name);
		$(extra_row).find(".slcValueType").val(extra_model.get('valueType').name);		
		
	},
	
	removeRow : function (event){
		if (window.console) console.log("remove extra row");
		$(event.target).closest('tr').remove();
	}
});

//Hospital Specific 
App.Views.HospitalSpecific =  Backbone.View
.extend({
	tagName : 'tr',
	template: _.template($('#hospital-specific').html()),		
	
	events : {
		'click #plus-btn' : 'addRow',
		'click #minus-btn': 'removeRow'		
	},
					
	render : function() {	
		this.$el.html(this.template(this.model.toJSON()));				
		return this;
	},
	
	addRow : function (event){		
		if (window.console) console.log("add extra row");
		
		var hospital_specific_tbody = $('#hospital-specific-table tbody');
										
		var hospitalSpecific =	{
				  code:      '',
				  codeType:  this.model.get("codeType"),
				  mnemonic:  ''
		};
		
		var hospital_specific_model = new App.Models.HospitalSpecific(hospitalSpecific);						
		var hospital_specific_view = new App.Views.HospitalSpecific({ model : hospital_specific_model});
		var hospital_specific_row = hospital_specific_view.render().el;
		$(hospital_specific_tbody).append(hospital_specific_row);
		$(hospital_specific_row).find(".slcCodeType").val(hospital_specific_model.get('codeType').name);
		
	},
	
	removeRow : function (event){
		if (window.console) console.log("remove extra row");
		$(event.target).closest('tr').remove();
	}
});