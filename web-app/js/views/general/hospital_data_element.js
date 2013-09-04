//List of HospitalElements
App.Views.HospitalElements = Backbone.View.extend({
	template : _.template($('#hospital_data_element-form').html()),
	
	events : {		
		'click button#cancel' : 'returnToProduct' ,
		'click #save-btn'     : 'saveHospitalElements'
	},
	
	render : function() {		
		this.$el.html(this.template({ hospitals : this.collection}));
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
			.popover('hide');	
		});
					
		return this;
	},
		
	clearTabsContent: function(){		
		var temp =  _.template($('#qa').html());
			
		$('div#qa2').html(temp({notes:''}));  
		   
		$('div#qa3').html(temp({notes:''}));  
		
		
		$('div#hs-table').empty();
		$('div#extra-location').empty();
	},

	appendHospitalElement : function(hospitalElement) {
		var m_id = this.options.m_id;
		var e_ehrs = this.options.external_ehrs;
		var p_ehr = this.options.primary_ehr;
		var view = new App.Views.SingleHospitalElement({ model : hospitalElement, m_id: m_id, external_ehrs: e_ehrs, primary_ehr:p_ehr});		
		this.$el.find('#hospital-elements tbody').append(view.render().el);		
	},
	
	
	returnToProduct : function(){
		//Backbone.history.navigate("product/" + this.options.product_id, true);
		window.history.back();
	},
	
	saveHospitalElementDetails: function() {
		if ($('tr').hasClass("row_selected")){
			var he_id_to_save = $('tr.row_selected td:first').prop("id");
			var hospital_element_to_save = App.hospitalElements.get(he_id_to_save);
			
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
				var _sourceEHR = $(this).find('input#sourceEHR').is(':checked');
				var _source = $(this).find('select#source').val();				
				var _valueType = $(this).find('select.slcValueType').val();
						
				if ((_location!="")||(_source!="")||(_valueType!="")) {
					var extraloc = {"location":_location, "source":_source, "sourceEHR":_sourceEHR, "valueType":{name:_valueType}};					
					elementExtraLocation.push(extraloc);				
				}				
			});
								
			hospital_element_to_save.set({"elementExtraLocation":elementExtraLocation});									
			hospital_element_to_save.set({"hospitalValueSet":hospitalValueSet});		
			hospital_element_to_save.set({"valueSet":valueSet});
		}		
	},
	
	saveHospitalElements : function() {
		var markAsComplete = $('#markAsComplete').is(":checked");
		
		this.saveHospitalElementDetails();		
		
		var m_id = this.options.m_id;
		_.each(this.collection.models, function(model) {
			  model.set({markAsComplete: markAsComplete});	 
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
		'change .slcValueType' 		   				   : 'changeSlc',
		'click .show_info'                			   : 'showInfo'				
	},
					
	render : function() {		
		this.$el.html(this.template(this.model.toJSON()));				
		this.$el.attr('id',this.model.get('id'));
				
		this.$el.find('#source').append("<option class='opt1' value='"+this.options.primary_ehr+"' >"+this.options.primary_ehr+"</option>");	
		//this.$el.find('#source').append("<optgroup class='opt2'></optgroup>");
		var _el = this.$el; 
		$(this.options.external_ehrs).each(function(i,ex){
			if (ex)
				_el.find('#source').append("<option class='opt2' value='"+ex+"' >"+ex+"</option>")	
		});
		
		if (this.$el.find('#sourceEHR').is(':checked')) {
			this.$el.find('#source').val(this.options.primary_ehr);
		}
		else{
			this.$el.find('#source').val(this.model.get('source'));
		}
		
		return this;
	},
	
	changeVal : function(e) {
		this.model.attributes[e.target.name] = $(e.target).val();		
	},
	
	changeCh : function(e) {
		this.model.attributes[e.target.name] = $(e.target).is(':checked');	
		if (this.$el.find('#sourceEHR').is(':checked')) {
			this.restoreSourceValue(e);
			this.$el.find('#source').attr('disabled','disabled');			
		}	else {						
			this.$el.find('#source').val('').removeAttr('disabled');
			this.model.attributes["source"]="";
		}
	},
	
	changeSlc : function(e) {
		this.model.attributes[e.target.name].name = e.target.value;
	},
	
	selectRow: function(event) {
				
		//activate details tabs
		$('#deatails *').removeAttr('disabled');
        	
		//save selected hospital specific and remove selection	
		if ($('tr').hasClass("row_selected")){			
						
			var he_id_to_save = $('tr.row_selected td:first').prop("id");
			var hospital_element_to_save = App.hospitalElements.get(he_id_to_save);
			
			// save Hospital Specific
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
			
			//save elementExtraLocation
			elementExtraLocation = [];
			$('table#extra-table tr').not(':first').each(function( ) {
				var _location = $(this).find('input#location').val();
				var _sourceEHR = $(this).find('input#sourceEHR').is(':checked');
				var _source = $(this).find('select#source').val();				
				var _valueType = $(this).find('select.slcValueType').val();
						
				if ((_location!="")||(_source!="")||(_valueType!="")) {
					var extraloc = {"location":_location, "source":_source, "sourceEHR":_sourceEHR, "valueType":{name:_valueType}};					
					elementExtraLocation.push(extraloc);				
				}				
			});
								
			//Hospital Specific
			hospital_element_to_save.set({"valueSet":valueSet});
			
			//$('#output2').text()
			//hospital_element_to_save.set({"valueSetFile":valueSet});
			hospital_element_to_save.set({"hospitalValueSet":hospitalValueSet});
			
			//ExtraLocation
			hospital_element_to_save.set({"elementExtraLocation":elementExtraLocation});
			
			$('.row_selected').css( "background-color", "#FFFFFF" );
			$('tr.row_selected td:first').css( "background-color", "#FFFFFF" );
			$('.row_selected').removeClass('row_selected');
		}
						
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
		this.restoreValues(event);		
		//this.clearTabsContent();
	},
	
	restoreValues : function(event) {
		var m_id = this.options.m_id;
		var e_ehrs = this.options.external_ehrs;
		var p_ehr = this.options.primary_ehr;
		var id = $(event.target).closest('tr').find('td:first').prop('id');		
		
		App.hospitalElements = new App.Collections.HospitalElements();		
		App.hospitalElements.fetch({data:{id: m_id, defaults: true, he_id: id}}).then(function(){			
			var hospitalElement = App.hospitalElements.get(id);
			var cur_row = $('#hospital-elements td#'+hospitalElement.id).closest('tr');
			var view = new App.Views.SingleHospitalElement({ model : hospitalElement, m_id: m_id, external_ehrs: e_ehrs, primary_ehr:p_ehr});			
			$(cur_row).replaceWith(view.render().el);	
			$('#hospital-elements td#'+hospitalElement.id).closest('tr').find('.slc_row').click();			
		});				
	},
	
	restoreSourceValue : function(event) {
		var m_id = this.options.m_id;
		var id = $(event.target).closest('tr').find('td:first').prop('id');		
		
		App.hospitalElements = new App.Collections.HospitalElements();		
		App.hospitalElements.fetch({data:{id: m_id, defaults: true, he_id: id}}).then(function(){			
			var hospitalElement = App.hospitalElements.get(id);
			var cur_row = $('#hospital-elements td#'+hospitalElement.id).closest('tr');
			$(cur_row).find('input#source').val(hospitalElement.get('source'));
		});				
	},
		
	
	showQA: function(slc_hospital_element){		
		 var qa_view2 = new App.Views.QADataElement({ model : slc_hospital_element, tab: "tab-qa2"});  
		 $('div#qa2').html(qa_view2.render().el);  
		   
		 var qa_view3 = new App.Views.QADataElement({ model : slc_hospital_element, tab: "tab-qa3"});
		 $('div#qa3').html(qa_view3.render().el); 
	},
			
	showHospitalSpecific: function(slc_hospital_element){		
		$('div#hs-table').empty();
		
		var view = new App.Views.HospitalSpesificTable({ model : slc_hospital_element});  
		$('div#hs-table').append(view.render().el);
		
		var view_file = new App.Views.HospitalFileUpload({ model : slc_hospital_element});  
		$('div#hs-table').append(view_file.render().el);  
		
		//set hospitalElementId to AjaxForm
		$("#currentHospitalElement").val(this.model.id);
		
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
	
	showExtraLocation: function(slc_hospital_element){	
		var elementExtraLocation = slc_hospital_element.get('elementExtraLocation');
		
		var external_ehrs = this.options.external_ehrs;
		var primary_ehr = this.options.primary_ehr;
		
		var view = new App.Views.ExtraTable({collection : elementExtraLocation, external_ehrs:external_ehrs, primary_ehr:primary_ehr});			
		$('div#extra-location').html(view.render().el);  
				
		var elementExtraLocation = slc_hospital_element.get('elementExtraLocation');
		
		
		if (elementExtraLocation.length==0){	
		  //add empty row	
		  var extraDataElement =	{
				  location: '',
				  sourceEHR: '',
				  source:    '',				 
				  valueType: '-Select-'
		  };
		  
		  var extra_model = new App.Models.ExtraDataElement(extraDataElement);
		  var extra_view = new App.Views.ExtraDataElement({ model : extra_model, external_ehrs:external_ehrs, primary_ehr:primary_ehr});					  
		  var extra_row = extra_view.render().el;
		  $('#extra-table tbody').append(extra_row);
		} else {			
		  $.each( elementExtraLocation, function( i, extraDataElement ) {			
			var extra_model = new App.Models.ExtraDataElement(extraDataElement);
			var extra_view = new App.Views.ExtraDataElement({ model : extra_model, external_ehrs:external_ehrs, primary_ehr:primary_ehr});	
			var extra_row = extra_view.render().el;
			$('#extra-table tbody').append(extra_row);
		  });				
		}			
	},
	
	showInfo: function(evt) {		
		var _did = $(evt.target).closest('tr').prop("id");
		var _help = this.model.get('help');
		var _code = this.model.get('dataElement');
		var _show = $('.show_info[did='+_did+']').hasClass('show');

		$('.show').removeClass('show').popover('hide');
		
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
App.Views.HospitalSpesificTable = Backbone.View
.extend({ 
className: "span7",	
template: _.template($('#hosp-spec-table').html()),      
      
render : function() {  
this.$el.html(this.template());
return this;
}  

});

//File Upload
App.Views.HospitalFileUpload = Backbone.View
.extend({ 
className: "span5",	
template: _.template($('#file-upload-temp').html()),   

events : {
	'click #upload' 	  : 'upload',
	'click #del' 		  : 'deleteFile',
	'change #fileToUpload': 'changeFile'
},
        
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

deleteFile : function() {	
	var he = this.model;
	var id = this.model.get('id');
	$.ajax({
		  type: "DELETE",
		  //url: "/ihm/api/file?currentHospitalElement="+$("#currentHospitalElement").val()
		  url: "/ihm/api/file?currentHospitalElement="+id
		}).done(function( msg ) {
		  //reload model
			$('form#uploadForm a').remove();
			$('input#fileToUpload').removeClass('hide');
			$('input#upload').addClass('hide');
			$('input#del').addClass('hide');
			he.set({"valueSetFile":''});
		});		
},

changeFile : function() {
	$('input#fileToUpload').addClass('hide');
	$('input#upload').removeClass('hide');
	
	var str = $('input[type=file]').val();
	str = str.substr(str.lastIndexOf('\\')+1);
	$('form#uploadForm').append('<span>'+str+'</span>');	
},

upload : function(){
	var he = this.model;
	$('#uploadForm').ajaxSubmit({
        target: '#output2',
        success:  function afterSuccess(url){
        				$('#uploadForm').resetForm();  // reset form	  
        				var str = url;
        				var name = str.substr(str.lastIndexOf('/')+1);
        				he.set({"valueSetFile":name});
        				
        				var name = name.substr(name.indexOf('_')+1);
        				        					        				
        				$('input#fileToUpload').addClass('hide');
        				$('input#upload').addClass('hide');
        				$('input#del').removeClass('hide');	        				    				
        					        				
        				//hardcode
        				var he_id = $('tr.row_selected td:first').prop("id");	        				
        				var path = "/ihm/api/file?currentHospitalElement=" + he_id;	        				
        				$('form#uploadForm span').replaceWith('<a href= "' + path + '">' + name +'</a>');
        				
        				
        				
        				
				 }
    }); 		
}



});

//Extra Table 
App.Views.ExtraTable = Backbone.View
.extend({ 
template: _.template($('#extra-table-temp').html()),      
        
render : function() {		
	this.$el.html(this.template());	
	// TODO extra Element source
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
		this.$el.html(this.template(this.model.toJSON()));	
		// TODO extra Element source
		var external_ehrs = this.options.external_ehrs;
		var primary_ehr = this.options.primary_ehr;
		
		this.$el.find('#source').append("<option class='opt1' value='"+this.options.primary_ehr+"' >"+this.options.primary_ehr+"</option>");	
		//this.$el.find('#source').append("<optgroup class='opt2'></optgroup>");
		var _el = this.$el; 
		$(this.options.external_ehrs).each(function(i,ex){
			if (ex)
				_el.find('#source').append("<option class='opt2' value='"+ex+"' >"+ex+"</option>")	
		});
		
		if (this.$el.find('#sourceEHR').is(':checked')) {
			this.$el.find('#source').val(this.options.primary_ehr);
		}
		else{
			this.$el.find('#source').val(this.model.get('source'));
		}
		
		return this;
	},
	
	addRow : function (event){		
		if (window.console) console.log("add extra row");
		
		var extraDataElement =	{
				  location:  this.model.get('location'),
				  sourceEHR: this.model.get("sourceEHR"),
				  source:    this.model.get("source"),				 
				  valueType: this.model.get("valueType")
		};
		
		var external_ehrs = this.options.external_ehrs;
		var primary_ehr = this.options.primary_ehr;
		
		var extra_model = new App.Models.ExtraDataElement(extraDataElement);
		var extra_view = new App.Views.ExtraDataElement({ model : extra_model, external_ehrs:external_ehrs, primary_ehr:primary_ehr});		
		
		var extra_tbody = $('#extra-table tbody');
		var extra_row = extra_view.render().el;
		$(extra_tbody).append(extra_row);				
		
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
				  mnemonic:  ''
		};
		
		var hospital_specific_model = new App.Models.HospitalSpecific(hospitalSpecific);						
		var hospital_specific_view = new App.Views.HospitalSpecific({ model : hospital_specific_model});
		var hospital_specific_row = hospital_specific_view.render().el;
		$(hospital_specific_tbody).append(hospital_specific_row);	
	},
	
	removeRow : function (event){
		if (window.console) console.log("remove extra row");
		$(event.target).closest('tr').remove();
	}
});


//QA
App.Views.QADataElement = Backbone.View
.extend({ 
  template: _.template($('#qa').html()),      
  
  events : {
    'click .send-btn' : 'appendQuestion',
    'keypress .txt-message': 'sendOnEnter'
  },
          
  render : function() {
    //console.log(this.model.toJSON());        
        
    if (this.options.tab=="tab-qa2") {
      this.$el.html(this.template({notes:this.model.get('notes')}));    
    } else 
    if (this.options.tab=="tab-qa3") {
      this.$el.html(this.template({notes:this.model.get('internalNotes')}));    
    }
    
    this.$el.attr('id',this.options.tab);
    
    return this;
  },
  
  sendOnEnter: function(e) {
      if (e.keyCode == 13) {
    	  this.appendQuestion();
      }
   },
  
  appendQuestion : function() {
    var message = this.$el.find(".txt-message").val();
    var txt =  this.$el.find(".txt-qa").html();
    
    var date = new Date();        
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var ampm = hours >= 12 ? 'pm' : 'am';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    minutes = minutes < 10 ? '0'+minutes : minutes;
    var strTime = hours + ':' + minutes + ' ' + ampm;
    
    var messageTimeStamp = (date.getMonth() + 1) + "/" + date.getDate() + "/"  + date.getFullYear().toString() + " " +strTime;
    
    var user = App.userRole;
    
    message = user + ", " + messageTimeStamp + ": " + message;
    txt = txt + "<p>" + message + "</p>";
        
    this.$el.find(".txt-qa").html(txt);
    this.$el.find(".txt-message").val('');    
        
    if (this.options.tab=="tab-qa2") {
      this.model.set({"notes":txt});
    } else 
    if (this.options.tab=="tab-qa3") {
      this.model.set({"internalNotes":txt});
    }
  }
});

