// List of Hospitals
App.Views.Hospitals = Backbone.View.extend({
	template : _.template($('#hospital-list-template').html()),

	render : function() {		
		this.$el.html(this.template({
			hospitals : this.collection
		}));
		this.collection.each(this.appendHospital, this);
		return this;
	},

	appendHospital : function(hospital) {
		var view = new App.Views.SingleHospital({
			model : hospital
		});

		this.$el.find('#table_items tbody').append(view.render().el);
	}	
});


//New/Edit Hospital
App.Views.Hospital = Backbone.View.extend({
	template : _.template($('#hospital-template').html()),

	initialize : function() {		
		this.model.on('change', this.render, this);
	},
	
	events : {
		'click #submit-btn' 			 : 'submHospital',	
		'click button#cancel' 			 : 'returnOnMain', 
		'click #btnApplyHospitalOptions' : 'applyHospitalOptions',
		'click a[data-toggle="tab"]'	 : 'changeTab',
		'change #notes' 				 : 'changeVal'		
	},

	render : function() {	
		if (!this.model.toJSON().id) {
			this.model.set("state" , "New");
		};
		
		// get embedded collection
		App.hospital_products = App.ho.get('products');
		
		this.$el.html(this.template(this.model.toJSON()));
		
		App.products.forEach(this.appendProductOption,this);
		App.ehrs.forEach(this.appendEhrOption,this);	
				
		return this;
	},
		
	appendProductOption: function(product) {		
		var temp = _.template($('#product-option').html());
		this.$el.find('#slcProducts').append(temp({id:product.get('id'),code:product.get('code')+"-"+product.get('name')}));		
	},
	
	appendEhrOption: function(ehr) {	
		console.log(ehr.code);
		var temp = _.template($('#product-option').html());
		this.$el.find('#slcEHRs').append(temp({id:ehr.get('id'),code:ehr.get('name')}));		
	},
	
			
	setPrimaryEhr : function(){		
		var ehr_id = this.model.get('ehr').id;		
		$("#slcEHRs").multiselect("widget").find('input[value='+ehr_id+']').click();		
	},
	
	createTabs : function(){
		// set first option false
		$("#slcProducts").multiselect("widget").find(":checkbox").eq(0).click();		

		//get assigned products
		var products = App.ho.get('products');
		$.each( products, function( i, product ) { 	
			// set checkboxes for assigned products
			$("#slcProducts").multiselect("widget").find('input[value='+ product.id  +']').click();			
		});
					
		//create tabs for all checked products
		$( "#slcProducts").multiselect('getChecked').each(function( index ) {  					
			var tabName = 't'+$(this).val();
			var tabNameText = $(this).closest('label').text();
						
			tabNameText = tabNameText.substring(0,tabNameText.indexOf("-"));

			$('div#myTabContent').append('<div id="'+tabName+'" class="tab-pane fade"></div>');			
			$('ul#myTab').append('<li class=""><a data-toggle="tab" href="#' + tabName + '">'+ tabNameText + '</a></li>');			
		});
		
		// set active tab
		$('div#myTabContent div').first().addClass('active in');
		$('ul#myTab li').first().addClass('active');
				
		// create and fill in HospitalMeasures for all checked products 
		this.appendHospitalMeasureTable();	
		$('#loading').hide();
	
	},

	changeTab: function (e){		
		var product_id = $(e.target).attr('href').replace('#t','');				
		var slcTab = '#myTabContent div#t' + product_id;	
			
		
		var oTable = $('.hospitalMeasureTable').dataTable({
			//"bRetrieve": true, 
			"bDestroy": true, 
			"bPaginate": false,
			"bFilter": false,
			"sScrollY": "272px",			
			"bSort": true,
			"bInfo": false,
			"aaSorting": [[0, 'asc']],
			"aoColumnDefs": [{'bSortable': false, 'aTargets': [ 4,5,6,7 ] }]
			/*"fnDrawCallback": function( oSettings ) {
			      //alert( 'DataTables has redrawn the table' );
				this.refeshTableHeader();
			 }*/	 
		
		 });	
	
	    new FixedColumns( oTable, {"sHeightMatch": "none"} );        	    
	    setTimeout(this.refeshTableHeader, 300);    
		
	},
	
	refeshTableHeader:  function() {
		if ($('th.sorting_asc').length >0) {
    		$('th.sorting_asc').click();	
    	} else {
    		$('th.sorting_desc').click();
    	}	
	},

	
	// append HospitalMeasureTable to Tab
	appendHospitalMeasureTable : function(){		
	    var table_template = _.template($('#hospital-measure_table').html());						
		var products = App.ho.get('products');	
				
		// for all products
		$.each( products , function( p_index, product ) {					
			var slcTab = '#myTabContent div#t' + product.id;
			$(slcTab).append(table_template());			
						
			// get assigned measures
			var measures = product.measures;
			
			$.each( measures, function( m_index, measure ) {
				console.log(JSON.stringify(measure));		
				var hospitalMeasure	 =  new App.Models.HospitalMeasure({"id":measure.id,
																		"code":measure.code,
																		"name":measure.name,
																		"accepted" :measure.accepted,
																		"completed":measure.completed,
																		"confirmed":measure.confirmed,
																		"included" :measure.included,
																		"verified" :measure.verified,
																		"p_index"  :p_index,
																		"m_index"  :m_index
																		});				
				
				var view = new App.Views.SingleHospitalMeasure({ model : hospitalMeasure });				
				$(slcTab + ' .hospitalMeasureTable tbody').append(view.render().el);				
			});		
		
		});	  
				
		/*_.each (hospitalMeasures.models, function(model) {
			model.set({verified:true})
			model.save();
		});*/				
	},
			
	
	// apply
	applyHospitalOptions : function(){		
    	//parameters
		var h_id = this.model.get('id');		
		var pr_ids = new Array();
		var e_id = $( "#slcEHRs").multiselect('getChecked').val();					
		
		$( "#slcProducts").multiselect('getChecked').each(function( index ) {  					
			pr_ids.push($(this).val());
		});	
		
		this.model.set({apply: true});		
		this.model.attributes.ehr_id = e_id;
		this.model.attributes.product_ids = pr_ids;
		this.model.attributes.id = h_id;
		
		var view = this;								
		this.model.save(this.attributes,{
	        success: function (model, response) {	               	
	        	Backbone.history.navigate("reload/"+h_id, true);	           
	        },
	        
	        error: function (model,xhr) {	        	
	        	$('#app').html("apply error");
	        	//Backbone.history.navigate("reload/"+h_id, true);	
	        }
	    });
	},

	changeVal : function(e) {
		console.log(e.target.name);
		this.model.attributes[e.target.name] = $(e.target).val();
		console.log(this.model.attributes);
	},
	
	submHospital : function(e) {
		e.preventDefault();		
        console.log('submHospital');
                                
        this.model.set("products" , App.hospital_products);
        
        this.model.save(this.attributes,{
	        success: function (model, response) {
	           console.log(response);
	           $('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
               Backbone.history.navigate("hospital", true);
	        },
	        error: function (model, response) {
	        	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
	            Backbone.history.navigate("hospital", true);
	        }
	    });
	},
	
	returnOnMain: function () {		
		Backbone.history.navigate("/hospital", true);				
	}

});

//Single Hospital
App.Views.SingleHospital = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-hospital').html()),			
			events : {
				'click #edit' : 'goToEdit',
				'click #destroy' : 'destroy'
			},

			render : function() {
				this.$el.html(this.template(this.model.toJSON()));
				return this;
			},

			goToEdit : function() {
				Backbone.history.navigate("hospital/"+this.model.get('id')+'/edit', true);
			},

			destroy : function(){
				console.log("destroy");
			}
		});

//Single Hospital_Measure
App.Views.SingleHospitalMeasure = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-hospital_measure').html()),			
			events : {
				'click .edit-btn'             : 'goToEdit',
				'click .save-btn'             : 'goToSave',
				'click .cancel-btn'           : 'goToCancel',
				'click a#customLink'       	  : 'goToDataElements',
				'change input[name="included"], input[name="completed"], input[name="confirmed"], input[name="accepted"], input[name="verified"]'  : 'changeVal'				
			},

			render : function() {						
				var ch_included  = (this.model.get('included'))  ? "checked" : "";
				var ch_completed = (this.model.get('completed')) ? "checked" : "";
				var ch_confirmed = (this.model.get('confirmed')) ? "checked" : "";				
				var ch_accepted  = (this.model.get('accepted'))  ? "checked" : "";
				var ch_verified  = (this.model.get('verified'))  ? "checked" : "";
																
				this.$el.html(this.template({id:this.model.get('id'),
											 code:this.model.get('code'),
											 name:this.model.get('name'),
											 included:ch_included,
											 completed:ch_completed,
											 confirmed:ch_confirmed,
											 accepted:ch_accepted,
											 verified:ch_verified
											}));
				//this.$el.html(this.template(this.model.toJSON()));
				
				return this;
			},
			
			changeVal: function(e) {				
				console.log("checkbox "+e.target.name+ ":", $(e.target).is(':checked'));	
							    
			    p_index=this.model.get('p_index');
			    m_index=this.model.get('m_index');			    
			    
			    switch (e.target.name) {
			    case "included":
			    	App.hospital_products[p_index].measures[m_index].included = $(e.target).is(':checked');	
			        break;
			    case "completed":			        
			    	App.hospital_products[p_index].measures[m_index].completed = $(e.target).is(':checked');	
			    	break;
			    case "confirmed":			        
			    	App.hospital_products[p_index].measures[m_index].confirmed = $(e.target).is(':checked');
			    	break;
			    case "accepted":
			    	App.hospital_products[p_index].measures[m_index].accepted = $(e.target).is(':checked');
			        break;    
			    case "verified":
			    	App.hospital_products[p_index].measures[m_index].verified = $(e.target).is(':checked');
			        break;        
			    }
			   
			    console.log( App.hospital_products);						
			},

			goToEdit : function(e) {
				console.log("goToEdit " + e.target);				
						
			},
			
			goToSave : function (e){
				console.log("goToSave ");
								
				this.model.save(this.attributes,{
			        success: function (model, response) {
			           console.log(response);
			           $('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
		               //Backbone.history.navigate("product", true);
			        },
			        error: function (model, response) {
			        	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
			            //Backbone.history.navigate("product", true);
			        }
			    });				
			},
			
						
			goToDataElements : function(e) {
				e.preventDefault();
				console.log("goToDataElements");				
				
				$('#main_table').empty();
				var table_template = _.template($('#hospital-elements_table').html());	
				//append TableHeader
				$('#main_table').append(table_template());	
				
				var dataElementDefault = {code:"code", location:"location", source_ehr: "source_ehr", source: "source"};
				
				var ehrtbody = $('#hospital-elements tbody');				

				var hospitalElements = new App.Collections.HospitalElements();
				var hm_id = this.model.get('id');
								
				hospitalElements.fetch({data:{id: hm_id}}).then(function(){
					console.log(hospitalElements);
					
					hospitalElements.forEach(function(hospitalElement){			
						console.log(hospitalElement);
						var view = new App.Views.ModalDataElement({ model : hospitalElement});		
						var modalDataElementRow = view.render().el;
						$(ehrtbody).append(modalDataElementRow);						
						$(modalDataElementRow).find(".slcCodeType").val(hospitalElement.get('codeType').name);
						$(modalDataElementRow).find(".slcValueType").val(hospitalElement.get('valueType').name);
					});	
										
					var oTable = $('#hospital-elements').dataTable( 
							{	"bDestroy": true, 
								"bPaginate": false,
								"bFilter": false,
								"sScrollY": "225px",
								"bSort": true,
					 			"bInfo": false,
					 			"bAutoWidth": false,
					 			"aoColumnDefs": [
					 							{ 'bSortable': false, 'aTargets': [ 1,2,3,4,5 ] }
					 						 ],
								"bScrollCollapse": true,
								"bPaginate": false
							} );
					
					$("#hospital-elements").click(function(event) {
						$(oTable.fnSettings().aoData).each(function (){
							$('.row_selected').css( "background-color", "#FFFFFF" );							
							$('tr.row_selected td:first').css( "background-color", "#FFFFFF" );
							$(this.nTr).removeClass('row_selected');							
						});
						$(event.target.parentNode).addClass('row_selected');
						$(event.target.parentNode).css( "background-color", "rgb(0, 136, 204, 0.5)" );
						$('tr.row_selected td:first').css( "background-color", "rgb(0, 136, 204, 0)" );
						// show relevant information			
						var he_id = $('tr.row_selected td:first').prop("id");
						// get hospital element by id 
						var slc_hospital_element = hospitalElements.get(he_id);
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
						
						//extra location
						var extra_tbody = $('#modal-extra-table tbody');
						$(extra_tbody).empty();						
						var extra_view = new App.Views.ModalExtraDataElement({ model : slc_hospital_element});		
						console.log(slc_hospital_element);
						var extra_row = extra_view.render().el;
						$(extra_tbody).append(extra_row);		
						$(extra_row).find(".slcCodeType").val(slc_hospital_element.get('codeType').name);
						$(extra_row).find(".slcValueType").val(slc_hospital_element.get('valueType').name);
						
						//hospital specific
						var hospital_specific_tbody = $('#modal-hospital-specific-table tbody');
						$(hospital_specific_tbody).empty();		
												
						var hospitalSpecific =	{
								  code:      '',
								  codeType:  slc_hospital_element.get('codeType'),
								  mnemonic:  ''
						};
						
						var hospital_specific_model = new App.Models.ModalHospitalSpecific(hospitalSpecific);						
						var hospital_specific_view = new App.Views.ModalHospitalSpecific({ model : hospital_specific_model});
						var hospital_specific_row = hospital_specific_view.render().el;
						$(hospital_specific_tbody).append(hospital_specific_row);	
						$(hospital_specific_row).find(".slcCodeType").val(slc_hospital_element.get('codeType').name);
						
						
						console.log(event.target.parentNode);
					});
					
				   new FixedColumns( oTable,
							{ "sHeightMatch": "none"} );
				   
				   setTimeout(this.refeshTableHeader, 300);    
				});
				
											
				
				//circle
				/*if (dataElementDefaults !== undefined) {
					  $.each( dataElementDefaults, function( i, dataElementDefault ) {												
						var view = new App.Views.ModalDataElement({ model : dataElementDefault});				
						var dataElementDefaultRow = view.render().el;
						$(ehrtbody).append(dataElementDefaultRow);	
						$(dataElementDefaultRow).find(".slcCodeType").val(dataElementDefault.codeType.name);
						$(dataElementDefaultRow).find(".slcValueType").val(dataElementDefault.valueType.name);
						
						$(dataElementDefaultRow).find('.slcParent').append(optionsList);			
						$(dataElementDefaultRow).find(".slcParent").val("e"+dataElementDefault.linkId);			
					  });	
			    }*/				
				
				/*var view = new App.Views.ModalDataElement({ model : dataElementDefault});		
				var modalDataElementRow = view.render().el;
				
						
				$(ehrtbody).append(modalDataElementRow);	*/			
			},
			
			
			goToCancel : function (e){
				console.log("goToCancel " + e.target);			
			},

			destroy : function(){
				console.log("destroy");
			}
		});

App.Views.ModalDataElement = Backbone.View
.extend({
	tagName : 'tr',
	template: _.template($('#modal-data-elements').html()),			
					
	render : function() {			
		var ch  = (this.model.get('sourceEHR'))  ? "checked" : "";		
		this.model.set({chd:ch});
		console.log(this.model.toJSON());
		this.$el.html(this.template(this.model.toJSON()));				
		return this;
	}
});	

// EXTRA
App.Views.ModalExtraDataElement = Backbone.View
.extend({
	tagName : 'tr',
	template: _.template($('#modal-extra-elements').html()),		
	
	events : {
		'click #plus-btn' : 'addRow',
		'click #minus-btn': 'removeRow'		
	},
					
	render : function() {			
		var ch  = (this.model.get('sourceEHR'))  ? "checked" : "";		
		this.model.set({chd:ch});
		console.log(this.model.toJSON());
		this.$el.html(this.template(this.model.toJSON()));				
		return this;
	},
	
	addRow : function (event){		
		console.log("add extra row");
		
		var extraDataElement =	{
				  location:  this.model.get('location'),
				  sourceEHR: this.model.get("sourceEHR"),
				  source:    this.model.get("source"),
				  codeType:  this.model.get("codeType"),
				  valueType: this.model.get("valueType")
		};
		
		var extra_model = new App.Models.ModalExtraDataElement(extraDataElement);
		var extra_view = new App.Views.ModalExtraDataElement({ model : extra_model});		
		
		var extra_tbody = $('#modal-extra-table tbody');
		var extra_row = extra_view.render().el;
		$(extra_tbody).append(extra_row);
		$(extra_row).find(".slcCodeType").val(extra_model.get('codeType').name);
		$(extra_row).find(".slcValueType").val(extra_model.get('valueType').name);		
		
	},
	
	removeRow : function (event){
		console.log("remove extra row");
		$(event.target).closest('tr').remove();
	}
});

//Hospital Specific 
App.Views.ModalHospitalSpecific =  Backbone.View
.extend({
	tagName : 'tr',
	template: _.template($('#modal-hospital-specific').html()),		
	
	events : {
		'click #plus-btn' : 'addRow',
		'click #minus-btn': 'removeRow'		
	},
					
	render : function() {	
		this.$el.html(this.template(this.model.toJSON()));				
		return this;
	},
	
	addRow : function (event){		
		console.log("add extra row");
		
		var hospital_specific_tbody = $('#modal-hospital-specific-table tbody');
										
		var hospitalSpecific =	{
				  code:      '',
				  codeType:  this.model.get("codeType"),
				  mnemonic:  ''
		};
		
		var hospital_specific_model = new App.Models.ModalHospitalSpecific(hospitalSpecific);						
		var hospital_specific_view = new App.Views.ModalHospitalSpecific({ model : hospital_specific_model});
		var hospital_specific_row = hospital_specific_view.render().el;
		$(hospital_specific_tbody).append(hospital_specific_row);
		$(hospital_specific_row).find(".slcCodeType").val(hospital_specific_model.get('codeType').name);
		
	},
	
	removeRow : function (event){
		console.log("remove extra row");
		$(event.target).closest('tr').remove();
	}
});