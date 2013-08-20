//List of HospitalElements
App.Views.HospitalElements = Backbone.View.extend({
	template : _.template($('#hospital_data_element-form').html()),
	
	events : {
		'click #resetAll'     : 'resetAllToDefault',
		'click button#cancel' : 'returnToProduct' ,
		'click #save-btn'     : 'saveHospitalElements',
		'click #save-mark-btn': 'saveAndMarkHospitalElements'
		//'change #txt-qa2'     : 'changeQA2'
	},
	
	
	render : function() {		
		this.$el.html(this.template({ hospitals : this.collection}));
		this.collection.each(this.appendHospitalElement, this);
				
		if ($('#role').val() == 'admin') {
			this.$el.find("h3").html(this.options.measure_code+ ":DataElements");			
		} else {
			// hide Q&A tab
			this.$el.find('div#qa3').hide();
			this.$el.find('li.qa3').hide();
		}
		
		$('body')
		.unbind('mousedown')
		.mousedown(function(){
			$('.show_info.shown')
			.removeClass('shown')
			.popover('hide');	
		});
		
		
		
		return this;
	},
	
	resetAllToDefault : function(e) {		
		var m_id = this.options.m_id;
        App.hospitalElements = new App.Collections.HospitalElements();		
		App.hospitalElements.fetch({data:{id: m_id, defaults: true}}).then(function(){			
			App.hospitalElements.forEach(function(hospitalElement) {
			   var cur_row = $('#hospital-elements td#'+hospitalElement.id).closest('tr');
			   var ch = $(cur_row).find('.sourceEHR').is(':checked');				
			   if (ch) {
				var view = new App.Views.SingleHospitalElement({ model : hospitalElement, m_id: m_id});			
				$(cur_row).replaceWith(view.render().el);
			   }	
			});	
		});		
	},

	appendHospitalElement : function(hospitalElement) {
		var m_id = this.options.m_id;
		var view = new App.Views.SingleHospitalElement({ model : hospitalElement, m_id: m_id});		
		this.$el.find('#hospital-elements tbody').append(view.render().el);		
	},
			
	/*changeQA2 : function(e){
		 model.set({"notes": $(e.target).val()});
		 //Click on the Data Element to view its details.
	},*/
	
	returnToProduct : function(){
		//Backbone.history.navigate("product/" + this.options.product_id, true);
		window.history.back();

	},
	
	saveHospitalElements : function() {		
		var m_id = this.options.m_id;
		_.each(this.collection.models, function(model) {
			  model.set({markAsComplete: false});	 
			  model.set({m_id: m_id});			 
			  return model.save({
			    wait: true,
			    error: function (collection, response) {
		        			if (window.console) console.log("error");	          
		        		}    
			  });
		});	
		window.history.back();
	},
	
	saveAndMarkHospitalElements : function() {	
		var m_id = this.options.m_id;
		_.each(this.collection.models, function(model) {
			  model.set({markAsComplete: true});
			  model.set({m_id: m_id});	
			  return model.save({
			    wait: true,
			    error: function (collection, response) {
		        			if (window.console) console.log("error");	          
		        		}    
			  });
		});
		window.history.back();
	}
});

//Single Hospital Element
App.Views.SingleHospitalElement = Backbone.View
.extend({
	tagName : 'tr',	
	template: _.template($('#hospital_data_element').html()),	
	events : {
		'click .slc_row'                   			   : 'selectRow',
		'click  #reset'                     		   : 'resetToDefault',
		'change .source, .location'       			   : 'changeVal',
		'change .sourceEHR'                            : 'changeCh',
		'change .slcCodeType, .slcValueType' 		   : 'changeSlc',
		'click .show_info'                			   : 'showInfo'	
			
	},
					
	render : function() {		
		this.$el.html(this.template(this.model.toJSON()));				
		
		this.$el.find(".slcCodeType").val(this.model.get('codeType').name);
		this.$el.find(".slcValueType").val(this.model.get('valueType').name);
		this.$el.find('.sourceEHR').attr('checked', this.model.get('sourceEHR'));
		
		this.$el.attr('id',this.model.get('id'));
				
		return this;
	},
	
	changeVal : function(e) {
		this.model.attributes[e.target.name] = $(e.target).val();		
	},
	
	changeCh : function(e) {
		this.model.attributes[e.target.name] = $(e.target).is(':checked');	
	},
	
	changeSlc : function(e) {
		this.model.attributes[e.target.name].name = e.target.value;
	},
	
	selectRow: function(event) {
		//activate details tabs
		$('#deatails *').removeAttr('disabled');
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
		var m_id = this.options.m_id;
		var id = $(event.target).closest('tr').find('td:first').prop('id');		
		
		App.hospitalElements = new App.Collections.HospitalElements();		
		App.hospitalElements.fetch({data:{id: m_id, defaults: true, he_id: id}}).then(function(){			
			var hospitalElement = App.hospitalElements.get(id);
			var cur_row = $('#hospital-elements td#'+hospitalElement.id).closest('tr');
			var view = new App.Views.SingleHospitalElement({ model : hospitalElement, m_id: m_id});			
			$(cur_row).replaceWith(view.render().el);				
		});		
		
		this.clearTabsContent();
	},
	
	clearTabsContent: function(){
		$('#txt-qa2').val('');
		$('#txt-qa3').val('');
		$('#extra-table tbody').empty();
		$('#hospital-specific-table tbody').empty();		
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
		/*$("#txt-qa2").val("some notes for " + slc_hospital_element.get("dataElement") + ":" + notes);
		$("#txt-qa3").val("some internalNotes for " + slc_hospital_element.get("dataElement") + ":" + internalNotes );*/
		
		var qa_view = new App.Views.QADataElement({ model : slc_hospital_element});	
		$('div#tab-qa2').append(qa_view);	
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
		
	},
	
	showInfo: function(evt) {		
		var _did = $(evt.target).closest('tr').prop("id");
		var _help = this.model.get('help');
		var _code = this.model.get('dataElement');
		var _show = $('.show_info[did='+_did+']').hasClass('show');

		$('.show_info.show')
		.removeClass('show')
		.popover('hide');
		
		if (!_show){
			$('.show_info[did='+_did+']')
			.addClass('show')
			.popover({html:true,placement:'left',title:'Instructions for ['+_code+']',content:_help||"No Instructions were supplied..."})
			.popover('show');
			
			this.adjustPopover();
		}
		
		evt.preventDefault();
		evt.stopPropagation();		
	},
	
	adjustPopover:function(){
		$('.popover')
		.unbind('mousedown')
		.mousedown(function(e){
			e.preventDefault();
			e.stopPropagation();
		})
	},
});	

//QA
App.Views.QAElement = Backbone.View
.extend({	
	template: _.template($('#qa').html()),			
	
	events : {
		/*'click #plus-btn' : 'addRow',
		'click #minus-btn': 'removeRow'		*/
	},
					
	render : function() {		
		this.$el.html(this.template(this.model.toJSON()));				
		return this;
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