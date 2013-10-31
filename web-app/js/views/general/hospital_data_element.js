//List of HospitalElements
App.Views.HospitalElements = Backbone.View.extend({
	//template for the view
	template : _.template($('#hospital_data_element-form').html()),
	
	initialize : function() {		
		this.isModified = false;
	},

	//listen for events
	events : {		
		'click button#cancel' 			: 'returnToProduct' ,
		'click #save-btn'     			: 'saveHospitalElements',
		'click a[data-toggle="tab"]'	: 'changeTab',
		'click .admin-edit-notes'       : 'adminEditNotes'
	},

	//render List of HospitalElements
	render : function() {		
		this.$el.html(this.template({ hospitals : this.collection, measure_completed: this.options.measure_completed, role: App.userRole}));
		this.collection.each(this.appendHospitalElement, this);

		if (App.userRole == 'admin') {
			this.$el.find("h3").html(this.options.measure_code+ ": Data Elements");			
		} else {
			// hide Q&A tab
			this.$el.find('div#qa3').hide();
			this.$el.find('li.qa3').hide();
		}

		$('body')
		.unbind('mousedown')
		.mousedown(function(){
			$('.show')
			.removeClass('show')
			.popover('destroy');	
		});
				
		return this;
	},
	
	// open popup for Client Notes
	adminEditNotes : function(evt){
		// mark as read
		$('.admin-edit-notes').removeClass('btn-info');	
		var thisHospMeasure = this;
		var m_id = this.options.m_id;
		
		App.cur_measure = new App.Models.HospitalMeasure();
		App.cur_measure.fetch({data:{id: m_id,hm: true}}).then(function(){				
			// mark as read
			App.cur_measure.set({"notifyAdmin":false});   
			App.cur_measure.save();
		
			var qa_view = new App.Views.QA({ model : App.cur_measure});  
			var _my_content =  qa_view.render().el; 
					
			var _code = thisHospMeasure.options.measure_code;
			var _show = $('.admin-edit-notes').hasClass('show');
		
			$('.show').removeClass('show').popover('destroy');
			
			if (!_show){
				$('.admin-edit-notes').addClass('show')
				.popover({html:true,placement:'right',title:'Notes for [' + _code + ']',content:_my_content||"No notes were supplied..."}).popover('show');
				
				if (App.userRole == 'admin') {
					$('.popover').css('top','570px');
				}else {
					$('#breadcrumb-box .popover').css('top','0px');
				}
				thisHospMeasure.adjustPopover();
			}
		
		});
		
		evt.stopPropagation();		
	}, 
	
	adjustPopover:function(){
		$('.popover')
		.unbind('mousedown')
		.mousedown(function(e){
			e.stopPropagation();
		})
	},
	
	changeTab: function (e){		
		 $('div#tab-qa2 .txt-qa').scrollTop($('div#tab-qa2 .txt-qa')[0].scrollHeight);
		 $('div#tab-qa3 .txt-qa').scrollTop($('div#tab-qa3 .txt-qa')[0].scrollHeight);
	},	

	// render single hospitalElement in the table of hospitalElements
	appendHospitalElement : function(hospitalElement) {
		var m_id 	= this.options.m_id;
		var p_id    = this.options.product_id;
		var e_ehrs 	= this.options.external_ehrs;
		var p_ehr 	= this.options.primary_ehr;
		var view 	= new App.Views.SingleHospitalElement({ model : hospitalElement, m_id: m_id,p_id:p_id, external_ehrs: e_ehrs, primary_ehr:p_ehr});
			
		this.$el.find('#hospital-elements tbody').append(view.render().el);			
	},

	// redirect to hospitalProduct page
	returnToProduct : function(){		
		App.viewHospitalElements.isModified = false;
		
		if (window.history.length<=1) {
			window.close();
		} else {
			window.history.back();		
		}	
	},

	// save HospitalElementDetails
	saveHospitalElementDetails: function() {
		if ($('tr').hasClass("row_selected")){
			var he_id_to_save = $('tr.row_selected td:first').prop("id");			
			var hospital_element_to_save = this.collection.get(he_id_to_save);

			hospitalValueSet = [];				
			$('table#hospital-specific-table tr').not(':first').each(function( ) {
				var _code = $(this).find('input#code').val();
				var _mnemonic = $(this).find('input#mnemonic').val();

				if ((_code!="")||(_mnemonic!="")) {
					var hvs = {"code":_code, "mnemonic":_mnemonic};					
					hospitalValueSet.push(hvs);				
				}	
			});

			var valueSet = $('#txtValueSet').val();

			elementExtraLocation = [];
			$('table#extra-table tr').not(':first').each(function( ) {
				var _location = $(this).find('input#location').val();			
				var _sourceEHR = hospital_element_to_save.get("sourceEHR");
				
				var isAdditionEHR = $(this).find('option:selected').hasClass('opt2');
				(isAdditionEHR) ? _sourceEHR = false : _sourceEHR = true;
				
				var _source = $(this).find('select#source').val();				
				var _valuesTypeId = $(this).find('select.slcValuesType').val();
				
				if (_location!="") {
					var extraloc = {"location":_location, "source":_source, "sourceEHR":_sourceEHR, "valueType":{name:''}, "valuesTypeId": _valuesTypeId};					
					elementExtraLocation.push(extraloc);				
				}				
			});
		
			hospital_element_to_save.set({"elementExtraLocation":elementExtraLocation});									
			hospital_element_to_save.set({"hospitalValueSet":hospitalValueSet});		
			hospital_element_to_save.set({"valueSet":valueSet});
		}		
	},

	//saveHospitalElements
	saveHospitalElements : function() {
		this.saveHospitalElementsOnly();

		App.viewHospitalElements.isModified = false;
				
		if (window.history.length<=1) {
			window.close();
		} else {
			window.history.back();		
		}		
	},

	//saveHospitalElementsOnly
	saveHospitalElementsOnly : function() {
		var errorMessage="";
		var successRecordCounter=this.collection.length;
		var markAsComplete = $('#markAsComplete').is(":checked");
		
		App.cur_measure.set({completed: markAsComplete, p_id:this.options.product_id});
		App.cur_measure.save();
		
		this.saveHospitalElementDetails();		

		var m_id = this.options.m_id;
		var product_id = this.options.product_id;	
						
		_.each(this.collection.models, function(model) {			 	 
			  model.set({m_id: m_id});
			  model.set({p_id: product_id});

			  model.save(null,{
			    wait: true,
			    success: function (m, response) {			       
			           if (response.resp=="ok") {
			        	   successRecordCounter = successRecordCounter - 1;
			        	   if (successRecordCounter==0) { 
			        		   $('div#message-box').text("").append('Hospital Elements have been successfully updated.').removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2000).fadeOut(50);
			        	   }	   
			           } else if (response.resp=="error") {			        	  
			        	   if (errorMessage=="") {
			        		   var btn = '<button type="button" class="close">&times;</button>';
			        		   $('div#message-box').text("").append(btn).append(response.message).removeClass().addClass('alert').addClass('alert-error').show();
			        		   errorMessage = response.message;
			        		   errorMessage = errorMessage.replace("[","");
			        		   errorMessage = errorMessage.replace("]","");			        		   
			        	   }else {
			        		   var btn = '<button type="button" class="close">&times;</button>';
			        		   var message = response.message;
			        		   errorMessage = errorMessage.substring(0, errorMessage.length - 1);
			        		   errorMessage = errorMessage + ', ' + message.substr(message.indexOf('[')+1,message.indexOf(']')-message.indexOf('[')-1)
			        		   $('div#message-box').text("").append(btn).append(errorMessage);
			        	   }

			           } 
			    }	      
			  });
		});	
			
	},

	// confirm dialog
	showConfirm: function() {
		this_he = this;
		if (App.viewHospitalElements.isModified) {							
			bootbox.confirm("Save the changes?", function(result) {
					if (result) {
						App.viewHospitalElements.saveHospitalElementsOnly();						 
					} 
					App.viewHospitalElements.isModified = false;
			}); 	
		}		
	}

});

//Single Hospital Element
App.Views.SingleHospitalElement = Backbone.View.extend({
	tagName : 'tr',	
	template: _.template($('#hospital_data_element').html()),
	//listen for events
	events : {
		'click .slc_row'                   			   : 'selectRow',
		'click  #reset'                     		   : 'resetToDefault',
		'change .location'       			   		   : 'changeVal',
		'change .source'       			   			   : 'changeSource',		
		'click .show_info'                			   : 'showInfo',
		'change .slcValuesType'                        : 'changeValuesType'
	},

	//render Single Hospital Element in the table of HospitalElements
	render : function() {		
		this.$el.html(this.template(this.model.toJSON()));				
		this.$el.attr('id',this.model.get('id'));
		
		var valuesTypeId = this.model.get('valuesTypeId');		
		this.$el.find('#source').append("<option class='opt1' value='"+this.options.primary_ehr+"' >"+this.options.primary_ehr+"</option>");
		
		var _el = this.$el; 
		$(this.options.external_ehrs).each(function(i,ex){
			if (ex)
				_el.find('#source').append("<option class='opt2' value='"+ex+"' >"+ex+"</option>")	
		});
		
		if (this.model.get('sourceEHR')) {
			this.$el.find('#source').val(this.options.primary_ehr);
			//appendList of available ValuesType
			if (this.model.get('ids')=='') {
				this.$el.find('.slcValuesType').find('option').remove().end().append(this.vtypeAllOptions());	
			} else {
				this.$el.find('.slcValuesType').find('option').remove().end().append(this.vtypeOptions());	
			}						
		}
		else{
			//additional EHRs
			this.$el.find('#source').val(this.model.get('source'));
			//appendList of all available ValuesType
			this.$el.find('.slcValuesType').find('option').remove().end().append(this.vtypeAllOptions());						
		}
		
		//set selected ValuesType
		this.$el.find('.slcValuesType option[value=' + valuesTypeId+ ']').attr("selected","selected") ;

		return this;
	},
		
	// filter ValuesTypes
	vtypeOptions: function() {  
		var temp = _.template($('#multiple-default-element-option').html());
		var html= '';
		
		var he_ids = this.model.get('ids');
		var ids=he_ids.split(";");		
			
		App.valuesTypes.forEach(function(vtype){	
			if (_.contains(ids, vtype.get('id').toString())) {				
				html = html + temp({id:vtype.get('id'), code:vtype.get('name')}); 
			}	
		});		
					
		return html;
	},
	
	//all existing ValuesTypes
	vtypeAllOptions: function() {  
		var temp = _.template($('#multiple-default-element-option').html());
		var html= '';
						
		App.valuesTypes.forEach(function(vtype){			
				html = html + temp({id:vtype.get('id'), code:vtype.get('name')});				
		});							
		return html;
	},

	//set model attribute: location
	changeVal : function(e) {
		var _targetName = e.target.name
		var _val = $(e.target).val();
		var _id = this.model.attributes.id;

		this.model.attributes[e.target.name] = _val;

		_.each(App.hospitalElements.models, function (m){
			if (m.attributes.id == _id)
				m.attributes[_targetName] = _val;
		});

		App.viewHospitalElements.isModified = true;
	},
	
	//set model attribute: source
	changeSource : function(e) {
		var _targetName = e.target.name
		var _val = $(e.target).val();
		var _id = this.model.get('id');
		var _ids = this.model.get('ids');
		
		var isAdditionEHR = $(e.target).find('option:selected').hasClass('opt2');
		
		if (isAdditionEHR) {
			this.$el.find('.slcValuesType').find('option').remove().end().append(this.vtypeAllOptions());			
			var ids_array = _.pluck(App.valuesTypes.models, 'id');	
			var _ids = ids_array.join(";");
			
		} else {
			if (this.model.get('ids')=='') {
				this.$el.find('.slcValuesType').find('option').remove().end().append(this.vtypeAllOptions());	
			} else {
				this.$el.find('.slcValuesType').find('option').remove().end().append(this.vtypeOptions());	
			}			
		}

		this.model.attributes[e.target.name] = _val;
		//this.model.attributes.ids = _ids;

		_.each(App.hospitalElements.models, function (m){
			if (m.attributes.id == _id)
				m.attributes[_targetName] = _val;
			    //m.attributes.ids = _ids;
		});
		
		App.viewHospitalElements.isModified = true;
	},
		
	//set model attribute: valuesTypeId
	changeValuesType : function(e){	
		var _targetName = e.target.name;
		var _id = this.model.attributes.id;
				
		var _val = $(e.target).val();
		_.each(App.hospitalElements.models, function (m){
			if (m.attributes.id == _id)
				m.attributes.valuesTypeId = _val;
		});		
	},

	// show details for selected Hospital Data Elements
	selectRow: function(event) {				
		//activate details tabs
		$('#deatails *').removeAttr('disabled');
        	
		//save selected hospital specific and remove selection	
		if ($('tr').hasClass("row_selected")){			

			var he_id_to_save = $('tr.row_selected td:first').prop("id");
			var hospital_element_to_save = this.model.collection.get(he_id_to_save);

			//save Hospital Specific Value List
			hospitalValueSet = [];	
			$('table#hospital-specific-table tr').not(':first').each(function( ) {
				var _code = $(this).find('input#code').val();
				var _mnemonic = $(this).find('input#mnemonic').val();

				if ((_code!="")||(_mnemonic!="")) {
					var hvs = {"code":_code, "mnemonic":_mnemonic};					
					hospitalValueSet.push(hvs);				
				}				
			});

			var valueSet = $('#txtValueSet').val();

			//save Additional Locations
			elementExtraLocation = [];
			$('table#extra-table tr').not(':first').each(function( ) {
				var _location = $(this).find('input#location').val();				
				var _sourceEHR = hospital_element_to_save.get("sourceEHR");
				
				var isAdditionEHR = $(this).find('option:selected').hasClass('opt2');
				(isAdditionEHR) ? _sourceEHR = false : _sourceEHR = true;
				
				var _source = $(this).find('select#source').val();				
				var _valuesTypeId = $(this).find('select.slcValuesType').val();

				if (_location!="") {
					var extraloc = {"location":_location, "source":_source, "sourceEHR":_sourceEHR, "valueType":{name:''}, "valuesTypeId": _valuesTypeId};					
					elementExtraLocation.push(extraloc);				
				}				
			});

			//Hospital Specific Value List
			hospital_element_to_save.set({"valueSet":valueSet});						
			hospital_element_to_save.set({"hospitalValueSet":hospitalValueSet});

			//Additional Locations
			hospital_element_to_save.set({"elementExtraLocation":elementExtraLocation});

			$('.row_selected').css( "background-color", "#FFFFFF" );
			$('tr.row_selected td:first').css( "background-color", "#FFFFFF" );
			$('.row_selected').removeClass('row_selected');
		}

		// add selection
		$(event.target.parentNode).addClass('row_selected');
		$(event.target.parentNode).css( "background-color", "rgb(0, 136, 204, 0.5)" );		
		$('tr.row_selected td:first').css( "background-color", "rgb(0, 136, 204, 0)" );

		var he_id = $('tr.row_selected').prop("id");
		// get hospital element by id 
		var slc_hospital_element = this.model.collection.get(he_id);

		this.showQA(slc_hospital_element);
		this.showExtraLocation(slc_hospital_element);
		this.showHospitalSpecific(slc_hospital_element);

		 $('div#tab-qa2 .txt-qa').scrollTop($('div#tab-qa2 .txt-qa')[0].scrollHeight);
		 $('div#tab-qa3 .txt-qa').scrollTop($('div#tab-qa3 .txt-qa')[0].scrollHeight);
	},

	// reset selected HospitalElement to default Values
	resetToDefault : function(event) {		
		var _this = this;
		bootbox.confirm("This action cannot be reverted. Continue?", function(result) {
			if (result) {
				_this.restoreValues(event);	
			}		 
		}); 				
	},

	// restore values for selected HospitalElement
	restoreValues : function(event) {
		//parameters
		var m_id = this.options.m_id;
		var e_ehrs = this.options.external_ehrs;
		var p_ehr = this.options.primary_ehr;
		var id = $(event.target).closest('tr').prop('id');

		var curhospitalElement =this.model.collection.get(id);

		App.hospitalElements = new App.Collections.HospitalElements();		
		App.hospitalElements.fetch({data:{id: m_id, defaults: true, he_id: id}}).then(function(){			
			var hospitalElement = App.hospitalElements.get(id);

			$.each(["version","location","source","sourceEHR","valueType"], function(index,ael){ 
				curhospitalElement.attributes[ael] = hospitalElement.attributes[ael]; 
			});			

			var cur_row = $('#hospital-elements tr#'+hospitalElement.id);
			var view = new App.Views.SingleHospitalElement({ model : curhospitalElement, m_id: m_id, external_ehrs: e_ehrs, primary_ehr:p_ehr});			
			
			$(cur_row).replaceWith(view.render().el);	
			$('#hospital-elements tr#'+hospitalElement.id).find('.slc_row').click();			
		});				
	},

	//show content of [Questions and Comments/Internal IHM Comments] Tab 
	showQA: function(slc_hospital_element){		
		 var qa_view2 = new App.Views.QADataElement({ model : slc_hospital_element, tab: "tab-qa2"});  
		 $('div#qa2').html(qa_view2.render().el);  

		 var qa_view3 = new App.Views.QADataElement({ model : slc_hospital_element, tab: "tab-qa3"});
		 $('div#qa3').html(qa_view3.render().el); 		
	},

	//show content of [Hospital Specific Value List] Tab
	showHospitalSpecific: function(slc_hospital_element){		
		$('div#hs-table').empty();

		var view = new App.Views.HospitalSpesificTable({ model : slc_hospital_element});  
		$('div#hs-table').append(view.render().el);

		var view_file = new App.Views.HospitalFileUpload({ model : slc_hospital_element});  
		$('div#hs-table').append(view_file.render().el);  
		$("#currentHospitalElement").val(slc_hospital_element.get('id'));
		$("#currentMeasureId").val(this.options.m_id);

		var hospitalValueSet = slc_hospital_element.get('hospitalValueSet');

		if (hospitalValueSet.length==0){			
			//add empty row
			var hospitalSpecific =	{
					  code:      '',					 
					  mnemonic:  ''
			};					
			var hospital_specific_model = new App.Models.HospitalSpecific(hospitalSpecific);
			var hospital_specific_view = new App.Views.HospitalSpecific({ model : hospital_specific_model});
			var hospital_specific_row = hospital_specific_view.render().el;
			$('#hospital-specific-table tbody').append(hospital_specific_row);	
		}			
		else {
			//append existing rows 
			$.each( hospitalValueSet, function( i, hvs ) {				
				var hospital_specific_model = new App.Models.HospitalSpecific(hvs);
				var hospital_specific_view = new App.Views.HospitalSpecific({ model : hospital_specific_model}); 
				var hospital_specific_row = hospital_specific_view.render().el;
				$('#hospital-specific-table tbody').append(hospital_specific_row);				
			  });				
		}
	},

	//show content of [Additional Locations] Tab
	showExtraLocation: function(slc_hospital_element){	
		var elementExtraLocation = slc_hospital_element.get('elementExtraLocation');
		
		//get ids and valuesTypeId form parent dataElement
		var ids = slc_hospital_element.get('ids');
		var valuesTypeId = slc_hospital_element.get('valuesTypeId');
		var sourceEHR = slc_hospital_element.get('sourceEHR');
		var source = slc_hospital_element.get('source');

		var external_ehrs = this.options.external_ehrs;
		var primary_ehr = this.options.primary_ehr;

		var view = new App.Views.ExtraTable({collection : elementExtraLocation, external_ehrs:external_ehrs, primary_ehr:primary_ehr});			
		$('div#extra-location').html(view.render().el);  

		if (elementExtraLocation.length==0){	
		  //add empty row	
		  var extraDataElement =	{
				  location: '',
				  sourceEHR: sourceEHR,
				  source:    source,				 
				  valueType: 'NotApplicable',
				  valuesTypeId : valuesTypeId
		  };

		  var extra_model = new App.Models.ExtraDataElement(extraDataElement);
		  var extra_view = new App.Views.ExtraDataElement({ model : extra_model, external_ehrs:external_ehrs, primary_ehr:primary_ehr, ids:ids});					  
		  var extra_row = extra_view.render().el;
		  $('#extra-table tbody').append(extra_row);
		} else {			
		  $.each( elementExtraLocation, function( i, extraDataElement ) {			
			var extra_model = new App.Models.ExtraDataElement(extraDataElement);
			var extra_view = new App.Views.ExtraDataElement({ model : extra_model, external_ehrs:external_ehrs, primary_ehr:primary_ehr, ids:ids});	
			var extra_row = extra_view.render().el;
			$('#extra-table tbody').append(extra_row);
		  });				
		}			
	},

	//show help instructions 
	showInfo: function(evt) {		
		var _did = $(evt.target).closest('tr').prop("id");
		var _help = this.model.get('help');
		var _code = this.model.get('dataElement');
		var _show = $('.show_info[did='+_did+']').hasClass('show');

		$('.show').removeClass('show').popover('destroy');

		if (!_show){
			$('.show_info[did='+_did+']').addClass('show')
			.popover({html:true,placement:'left',title:'Instructions for ['+_code+']',content:_help||"No Instructions were supplied..."}).popover('show');
			this.adjustPopover();
		}
		evt.stopPropagation();		
	},

	adjustPopover:function(){
		$('.popover')
		.unbind('mousedown')
		.mousedown(function(e){
			e.stopPropagation();
		})
	}
});	


//Hospital Spesific Table 
App.Views.HospitalSpesificTable = Backbone.View.extend({ 
	className: "span7",	
	template: _.template($('#hosp-spec-table').html()),      
      
	render : function() {  
		this.$el.html(this.template());
		return this;
	}
});

//File Upload
App.Views.HospitalFileUpload = Backbone.View.extend({ 
	className: "span5",	
	template: _.template($('#file-upload-temp').html()),   
	//listen for events
	events : {
		'click #upload' 	  : 'upload',
		'click #del' 		  : 'deleteFile',
		'change #fileToUpload': 'changeFile'
	},
	 
	//render view for File Upload
	render : function() {
	  var name = this.model.get('valueSetFile');
	  var name = name.substr(name.indexOf('_')+1);  
	  var he_id = this.model.get('id');
	  var linkToFile = "";
	  
	  //browse/upload/delete
	  var status = "browse"; // if name=="";
	  //var status = "delete"; // if name!="";
	  //var status = "upload"; // if clicked browse
	  
	  if (name.length>0) {  
		  var path = "/ihm/api/file?currentHospitalElement=" + he_id;  				
		  linkToFile = '<a href= "' + path + '">' + name +'</a>';
		  status = "delete";
	  }
	   
	  this.$el.html(this.template({valueSet:this.model.get('valueSet'), linkToFile:linkToFile, status:status}));
	  return this;
	},
	
	//delete attached File
	deleteFile : function() {	
		var he = this.model;
		var id = this.model.get('id');
		$.ajax({
			  type: "DELETE",		 
			  url: "/ihm/api/file?currentHospitalElement="+id
			}).done(function( data ) {
				var arg = data.split(';')
				resp    = arg[0];
				version = arg[2];
				
				if (resp=="ok") { 
					//reload model
					$('form#uploadForm a').remove();
					$('input#fileToUpload').removeClass('hide');
					$('input#upload').addClass('hide');
					$('input#del').addClass('hide');
					he.set({"valueSetFile":''});
					he.set({"version": version});
				}
			});		
	},
	
	changeFile : function() {
		$('input#fileToUpload').addClass('hide');
		$('input#upload').removeClass('hide');
	
		var str = $('input[type=file]').val();
		str = str.substr(str.lastIndexOf('\\')+1);
		$('form#uploadForm').append('<span>'+str+'</span>');	
	},
	
	//upload File
	upload : function(){
		var he = this.model;
		$('#uploadForm').ajaxSubmit({
	        target: '#output2',         
	        success:  function afterSuccess(data){
				$('#uploadForm').resetForm();  // reset form
				
				var arg = data.split(';')
				resp    = arg[0];
				message = arg[1];
				version = arg[2];
				
				if (resp=="ok") {        				
					var name = message.substr(message.lastIndexOf('/')+1);
					he.set({"valueSetFile":name});
					he.set({"version": version});
		
					var name = name.substr(name.indexOf('_')+1);
		
					$('input#fileToUpload').addClass('hide');
					$('input#upload').addClass('hide');
					$('input#del').removeClass('hide');	        				    				
		
					//hardcode
					var he_id = $('tr.row_selected td:first').prop("id");	        				
					var path = "/ihm/api/file?currentHospitalElement=" + he_id;	        				
					$('form#uploadForm span').replaceWith('<a href= "' + path + '">' + name +'</a>');     
				}
	 		}      
	    }); 		
	}
});

//Extra Table 
App.Views.ExtraTable = Backbone.View.extend({ 
	template: _.template($('#extra-table-temp').html()),      
	        
	render : function() {		
		this.$el.html(this.template());	
		return this;
	}
});


//Additional Locations
App.Views.ExtraDataElement = Backbone.View.extend({
	tagName : 'tr',
	template: _.template($('#extra-elements').html()),		
	//listen for events
	events : {
		'click #plus-btn' : 'addRow',
		'click #minus-btn': 'removeRow',
		'change .source'  : 'changeSource'
	},

	//render a single row 
	render : function() {						
		this.$el.html(this.template(this.model.toJSON()));		
		var external_ehrs = this.options.external_ehrs;
		var primary_ehr = this.options.primary_ehr;
		var valuesTypeId = this.model.get('valuesTypeId');

		this.$el.find('#source').append("<option class='opt1' value='"+this.options.primary_ehr+"' >"+this.options.primary_ehr+"</option>");	
		
		var _el = this.$el; 
		$(this.options.external_ehrs).each(function(i,ex){
			if (ex)
				_el.find('#source').append("<option class='opt2' value='"+ex+"' >"+ex+"</option>")	
		});
				
		if (this.model.get('sourceEHR')) {
			this.$el.find('#source').val(this.options.primary_ehr);
			//appendList of available ValuesType			
			if (this.options.ids=='') {
				this.$el.find('.slcValuesType').find('option').remove().end().append(this.vtypeAllOptions());	
			} else {
				this.$el.find('.slcValuesType').find('option').remove().end().append(this.vtypeOptions());	
			}	
		}
		else{
			//additional EHRs
			this.$el.find('#source').val(this.model.get('source'));
			//appendList of all available ValuesType
			this.$el.find('.slcValuesType').find('option').remove().end().append(this.vtypeAllOptions());						
		}
		
		//set selected ValuesType
		this.$el.find('.slcValuesType option[value=' + valuesTypeId+ ']').attr("selected","selected") ;
				
		return this;
	},
	
	// filtered ValuesTypes
	vtypeOptions: function() {  
		var temp = _.template($('#multiple-default-element-option').html());
		var html= '';
		
		var he_ids = this.options.ids; //options
				
		var ids=he_ids.split(";");		
			
		App.valuesTypes.forEach(function(vtype){	
			if (_.contains(ids, vtype.get('id').toString())) {				
				html = html + temp({id:vtype.get('id'), code:vtype.get('name')}); 
			}	
		});		
					
		return html;
	},
	
	// all ValuesTypes
	vtypeAllOptions: function() {  
		var temp = _.template($('#multiple-default-element-option').html());
		var html= '';
						
		App.valuesTypes.forEach(function(vtype){			
				html = html + temp({id:vtype.get('id'), code:vtype.get('name')});				
		});		
					
		return html;
	},

	// add new row to the table
	addRow : function (event){		
		var valuesTypeId = this.model.get('valuesTypeId');
		var sourceEHR = this.model.get("sourceEHR");
		var source = this.model.get('source');

		var extraDataElement =	{
				  location:  '',
				  sourceEHR: sourceEHR,
				  source:    source,				 
				  valueType: 'NotApplicable',
				  valuesTypeId : valuesTypeId
		};

		var external_ehrs = this.options.external_ehrs;
		var primary_ehr = this.options.primary_ehr;
		var ids = this.options.ids;

		var extra_model = new App.Models.ExtraDataElement(extraDataElement);
		var extra_view = new App.Views.ExtraDataElement({ model : extra_model, external_ehrs:external_ehrs, primary_ehr:primary_ehr, ids:ids});		

		var extra_tbody = $('#extra-table tbody');
		var extra_row = extra_view.render().el;
		$(extra_tbody).append(extra_row);	
	},

	// remove row from the table
	removeRow : function (event){		
		$(event.target).closest('tr').remove();
	},
	
	// handle SourceEHR changes
	changeSource : function(e) {
		var _targetName = e.target.name
		var _val = $(e.target).val();
		var isAdditionEHR = $(e.target).find('option:selected').hasClass('opt2');
		
		if (isAdditionEHR) {
			this.$el.find('.slcValuesType').find('option').remove().end().append(this.vtypeAllOptions());			
			var ids_array = _.pluck(App.valuesTypes.models, 'id');	
			var _ids = ids_array.join(";");
			this.model.attributes.sourceEHR = false;
			
		} else {
			this.$el.find('.slcValuesType').find('option').remove().end().append(this.vtypeOptions());
			this.model.attributes.sourceEHR = true;
		}				
		this.model.attributes[e.target.name] = _val;				
	}
});


//Hospital Specific Value List
App.Views.HospitalSpecific =  Backbone.View.extend({
	tagName : 'tr',
	template: _.template($('#hospital-specific').html()),		
	//listen for events
	events : {
		'click #plus-btn' : 'addRow',
		'click #minus-btn': 'removeRow'		
	},

	//render single row
	render : function() {	
		this.$el.html(this.template(this.model.toJSON()));				
		return this;
	},

	//add row to the table
	addRow : function (event){		
		var hospital_specific_tbody = $('#hospital-specific-table tbody');
		
		var hospitalSpecific =	{
				  code:      '',				 
				  mnemonic:  ''
		};

		var hospital_specific_model = new App.Models.HospitalSpecific(hospitalSpecific);						
		var hospital_specific_view = new App.Views.HospitalSpecific({ model : hospital_specific_model});
		var hospital_specific_row = hospital_specific_view.render().el;
		$(hospital_specific_tbody).append(hospital_specific_row);	
	},

	//remove row from the table
	removeRow : function (event){		
		$(event.target).closest('tr').remove();
	}
});