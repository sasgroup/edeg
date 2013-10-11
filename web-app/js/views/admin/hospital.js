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

//Edit Hospital
App.Views.Hospital = Backbone.View.extend({
	template : _.template($('#hospital-template').html()),

	initialize : function() {		
		this.isModified = false;
		this.xEhrsShown = false;
	},
	
	events : {
		'click #submit-btn' 			 							: 'submHospital',	
		'click #submit-close-btn' 		 							: 'submCloseHospital',
		'click button#cancel' 			 							: 'returnOnMain', 
		'click #btnApplyHospitalOptions' 							: 'applyHospitalOptions',
		'click #btnCloneHospital' 									: 'cloneHospital',
		'click a[data-toggle="tab"]'	 							: 'changeTab',
		'change #notes, #email,  #txtEHRs' 	                        : 'changeVal',
		'click #btnExternalEHRs' 		 							: 'showExternalEHRs',
		'change #slcPopulationMethod'								: 'changeSlcVal',
		'click .admin-edit-notes'       							: 'adminEditNotes'
	},

	render : function() {		
		this.$el.html(this.template(this.model.toJSON()));
				
		App.products.forEach(this.appendProductOption,this);
		App.ehrs.forEach(this.appendEhrOption,this);		
		App.hospitals.forEach(this.appendHospitalOption,this);	
		
		$('body')
		.unbind('mousedown')
		.mousedown(function(){
			$('.show')
			.removeClass('show')
			.popover('destroy');	
		});
						
		return this;
	},
	
	adminEditNotes : function(evt){
		// mark as read
		$('.admin-edit-notes').removeClass('btn-info');			
				
	    var this_hosp = this;
	    
		if ($('#myTab li').length>0) {
				
			var p_id = $('#myTab li.active a').attr("href").replace('#t','');	
			var product = $('#myTab li.active a').text();
			
			var h_id = this.model.get('id');
			
			App.cur_product = new App.Models.HospitalProduct();
			
			App.cur_product.fetch({data:{p_id:p_id, h_id:h_id}}).then(function(){
				// mark as read
				App.cur_product.set({"notifyAdmin":false});   
				App.cur_product.save();
				
				var qa_view = new App.Views.QA({ model : App.cur_product});  
				var _my_content =  qa_view.render().el; 
						
				var _code = product;
				var _show = $('.admin-edit-notes').hasClass('show');
	
				$('.show').removeClass('show').popover('destroy');
				
				if (!_show){
					$('.admin-edit-notes').addClass('show')
					.popover({html:true,placement:'right',title:'Notes for [' + _code + ']',content:_my_content||"No notes were supplied..."}).popover('show');
					$('#breadcrumb-box .popover').css('top','0px');
					this_hosp.adjustPopover();
				}
				 
			});	
			
			evt.stopPropagation();		
	   }	
	},
	
	adjustPopover:function(){
		$('.popover')
		.unbind('mousedown')
		.mousedown(function(e){
			e.stopPropagation();
		})
	},
		
	appendProductOption: function(product) {		
		var temp = _.template($('#product-option').html());
		this.$el.find('#slcProducts').append(temp({id:product.get('id'),code:product.get('code')+"-"+product.get('name')}));		
	},
	
	appendEhrOption: function(ehr) {		
		var temp = _.template($('#product-option').html());
		this.$el.find('#slcEHRs').append(temp({id:ehr.get('id'),code:ehr.get('name')}));		
	},
	
	appendHospitalOption: function(h) {	
		var temp = _.template($('#product-option').html());
		
		if (h.get('id')!=this.model.get('id')) {		
			this.$el.find('#slcHospitals').append(temp({id:h.get('id'),code:h.get('name')}));
		}
	},
	
			
	setPrimaryEhr : function(){		
		var ehr_id = this.model.get('ehr').id;		
		$("#slcEHRs").multiselect("widget").find('input[value='+ehr_id+']').click();
		
		this_hospital = this;
		
		$('#slcEHRs').change(function(e){			
			var new_e_id = $( "#slcEHRs").multiselect('getChecked').val();
			
			if (new_e_id!=ehr_id) {				
				//alert("The EHR version has been updated. Make sure to reset locations for data elements.");
				bootbox.alert("You are going to update the primary EHR. Please click on Apply if you want to update the default locations.", function() {
				});
				this_hospital.isModified = true;
			}	
		});	
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
		
		//new
		//active p_id
		if ($('ul#myTab li.active a').attr('href') != undefined) {
			var p_id = $('ul#myTab li.active a').attr('href').replace('#t','');
			var h_id = this.model.get('id');
			
			App.cur_hosp_product = new App.Models.HospitalProduct();
			App.cur_hosp_product.fetch({data:{p_id:p_id, h_id:h_id}}).then(function(){
				  var notifyAdmin = App.cur_hosp_product.get('notifyAdmin'); 
				  if (notifyAdmin){
					  $('.admin-edit-notes').addClass('btn-info');
				  }	  
			});	  
		}	
		//
	},

	changeTab: function (e){		
		var product_id = $(e.target).attr('href').replace('#t','');				
		var slcTab = '#myTabContent div#t' + product_id;	
		
		$.cookie("active_tab", product_id);
				
		$.fn.dataTableExt.afnSortData['dom-checkbox'] = function  ( oSettings, iColumn )
		{
		    var aData = [];
		    $( 'td:eq('+iColumn+') input', oSettings.oApi._fnGetTrNodes(oSettings) ).each( function () {
		        aData.push( this.checked==true ? "0" : "1" );
		    } );				   
		    return aData;		    
		}
					
		var oTable = $(slcTab + ' .hospitalMeasureTable').dataTable({ 
			"bDestroy": true, 
			"bPaginate": false,
			"bSortClasses": false,
			"bFilter": false,
			"sScrollY": "272px",			
			"bSort": true,
			"bInfo": false,
			"aaSorting": [[0, 'asc'], [1, 'asc'], [3, 'asc']],			
			"bAutoWidth": false,
			"aoColumns": [
				  			{ "sSortDataType": "dom-checkbox" },
				  			null,
				  			{"sWidth": "73px" },
				  			{"sWidth": "336px" },
				  			{ "sSortDataType": "dom-checkbox", "sWidth": "60px" },
				  			{ "sSortDataType": "dom-checkbox", "sWidth": "60px" },
				  			{ "sSortDataType": "dom-checkbox", "sWidth": "50px" },
				  			{ "sSortDataType": "dom-checkbox", "sWidth": "50px" }
				  		]			
		 });	
				
	    new FixedColumns( oTable, {"sHeightMatch": "none"} );		
		$(slcTab + ' .dataTables_scrollHeadInner').css('width', '934px');
		$(slcTab + ' .hospitalMeasureTable.dataTable').css('width', '934px');
				
		$(slcTab + ' .hospitalMeasureTable.dataTable td:eq(2), ' + slcTab + ' .hospitalMeasureTable.dataTable th:eq(2)').css('width', '73px');
		$(slcTab + ' .hospitalMeasureTable.dataTable td:eq(3), ' + slcTab + ' .hospitalMeasureTable.dataTable th:eq(3)').css('width', '336px');
		$(slcTab + ' .hospitalMeasureTable.dataTable td:eq(4), ' + slcTab + ' .hospitalMeasureTable.dataTable th:eq(4)').css('width', '60px');
		$(slcTab + ' .hospitalMeasureTable.dataTable td:eq(5), ' + slcTab + ' .hospitalMeasureTable.dataTable th:eq(5)').css('width', '60px');
		$(slcTab + ' .hospitalMeasureTable.dataTable td:eq(6), ' + slcTab + ' .hospitalMeasureTable.dataTable th:eq(6)').css('width', '50px');
		$(slcTab + ' .hospitalMeasureTable.dataTable td:eq(7), ' + slcTab + ' .hospitalMeasureTable.dataTable th:eq(7)').css('width', '50px');
		
		var p_id = product_id;
		var h_id = this.model.get('id');
		
		$('.admin-edit-notes').removeClass('btn-info');
		
		App.cur_hosp_product = new App.Models.HospitalProduct();
		App.cur_hosp_product.fetch({data:{p_id:p_id, h_id:h_id}}).then(function(){
			  var notifyAdmin = App.cur_hosp_product.get('notifyAdmin'); 
			  if (notifyAdmin){
				  $('.admin-edit-notes').addClass('btn-info');
			  }	  
		});	  		
		
	},
		
	// append HospitalMeasureTable to Tab
	appendHospitalMeasureTable : function(){		
	    var table_template = _.template($('#hospital-measure_table').html());			
		App.hospital_products = App.ho.get('products');	
				
		// for all products
		$.each( App.hospital_products , function( p_index, product ) {					
			var slcTab = '#myTabContent div#t' + product.id;
			$(slcTab).append(table_template());			
						
			$.each( product.measures, function( m_index, measure ) {
				var hospitalMeasure	 =  new App.Models.HospitalMeasure({"id":measure.id,
																		"code":measure.code,
																		"name":measure.name,
																		"accepted" :measure.accepted,
																		"completed":measure.completed,
																		"confirmed":measure.confirmed,
																		"included" :measure.included,
																		"verified" :measure.verified,
																		"p_index"  :p_index,
																		"m_index"  :m_index,
																		"product_id" : product.id,
																		"measureCategory":measure.measureCategory
																		});				
				
				var view = new App.Views.SingleHospitalMeasure({ model : hospitalMeasure });				
				$(slcTab + ' .hospitalMeasureTable tbody').append(view.render().el);				
			});	
		
		});					
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
		$('#loading').show();
		
		this.model.save(null, {
	        success: function (model, response) {
	        	$('#loading').hide();
	        	Backbone.history.navigate("reload/"+h_id, true);	           
	        },
	        
	        error: function (model,xhr) {
	        	$('#loading').hide();
	        	$('#app').html("apply error");
	        	//Backbone.history.navigate("reload/"+h_id, true);	
	        }
	    }, {wait:true});
	    
	    return false;
	},
	
	// cloneHospital
	cloneHospital : function(){		
				
		//parameters
		var h_id = this.model.get('id');		
		var hosp_id = $( "#slcHospitals").multiselect('getChecked').val();	
		var hosp_name = $( "#slcHospitals option:selected" ).text().trim();
		
		var this_model = this.model;
				
		if (hosp_id!='') {			
			bootbox.confirm("You are going to clone all settings from  [" + hosp_name + "] to current hospital. Continue?", function(result) {
				if (result) {
					///*
					this_model.set({clone: true});	
					
					this_model.attributes.id = h_id;
					this_model.attributes.src_id = hosp_id;
							
					var view = this;
					$('#loading').show();
					
					this_model.save(null, {
				        success: function (model, response) {
				        	$('#loading').hide();
				        	Backbone.history.navigate("reload/"+h_id, true);	           
				        },
				        
				        error: function (model,xhr) {
				        	$('#loading').hide();
				        	$('#app').html("clone error");
				        }
				    }, {wait:true});
					//*/
				} 
			}); 
		}	
					    
	    return false;
	},
	

	changeVal : function(e) {
		//HOSPITAL IS MODIFIED
		this.isModified = true;
		if (window.console) console.log(e.target.name);
		this.model.attributes[e.target.name] = $(e.target).val();
		if (window.console) console.log(this.model.attributes);
	},
	
	changeSlcVal : function(e) {
		//HOSPITAL IS MODIFIED
		this.isModified = true;
		//console.log(this.model);
		if (window.console) console.log(e.target.name);
		this.model.attributes[e.target.name] =  $(e.target).multiselect('getChecked').val();
		if (window.console) console.log(this.model.attributes);
	},
	
	submHospital : function(e) {
		e.preventDefault();		

		this.model.attributes.externalEHRs = $('#txtEHRs').val();
		
        this.model.set("products" , App.hospital_products);
        this.model.set({submit: true});
        
        this.model.save(this.attributes,{
	        success: function (model, response) {
	           if (window.console) console.log(response);
	           $('div#message-box').text("").append(response.message).removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2000).fadeOut(50);              
	        },
	        error: function (model, response) {
	        	var btn = '<button type="button" class="close">&times;</button>';
		    	$('div#message-box').text("").append(btn).append(response.message).removeClass().addClass('alert').addClass('alert-error').show();	           
	        }
	    });
        
        App.viewHospital.isModified = false;
	},
	
	submCloseHospital : function(e) {
		e.preventDefault();		
		this.model.set("products" , App.hospital_products);
		this.model.set({submit: true});
        
        this.model.save(this.attributes,{
	        success: function (model, response) {
	           if (window.console) console.log(response);
	           if (response.resp=="ok") {	       
	        	   $('div#message-box').text("").append(response.message).removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2000).fadeOut(50);    	  
	        	   Backbone.history.navigate("hospital", true);
	           } else if (response.resp=="error") {
					var btn = '<button type="button" class="close">&times;</button>';
			    	$('div#message-box').text("").append(btn).append(response.message).removeClass().addClass('alert').addClass('alert-error').show();
		        	Backbone.history.navigate("hospital", true);	        	   
	           } 	                  
	        },
	        error: function (model, response) {
	        	var btn = '<button type="button" class="close">&times;</button>';
	        	$('div#message-box').text("").append(btn).append(response.message).removeClass().addClass('alert').addClass('alert-error').show();
	            Backbone.history.navigate("hospital", true);	        	
	        }
	    });        
        
        App.viewHospital.isModified = false;
	},
		
	returnOnMain: function (e) {
		e.preventDefault();
		//this.showConfirm();
		this_hospital = this;
		if (this.isModified) {							
			bootbox.confirm("Save the changes?", function(result) {
					if (result) {
						 this_hospital.submCloseHospital(e);						
					} else {
						 Backbone.history.navigate("/hospital", true);
					}
			}); 	
		}
		else {
			Backbone.history.navigate("/hospital", true);
		}			
	},
	
	// for FILTER
	saveAndClose : function() {
		this.model.set("products", App.hospital_products);
		this.model.set({submit: true});
		
        this.model.save(this.attributes,{
	        success: function (model, response) {
	           if (window.console) console.log(response);
	           if (response.resp=="ok") {	       
	        	   $('div#message-box').text("").append(response.message).removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2500).fadeOut(50);	        	   
	           } else if (response.resp=="error") {
					var btn = '<button type="button" class="close">&times;</button>';
			    	$('div#message-box').text("").append(btn).append(response.message).removeClass().addClass('alert').addClass('alert-error').show();		        		        	   
	           } 	                  
	        },
	        error: function (model, response) {
	        	var btn = '<button type="button" class="close">&times;</button>';
	        	$('div#message-box').text("").append(btn).append(response.message).removeClass().addClass('alert').addClass('alert-error').show();	         
	        }
	    });
	},
	
	showConfirm: function() {
		this_hospital = this;
		if (this.isModified) {							
			bootbox.confirm("Save the changes?", function(result) {
					if (result) {
						 this_hospital.saveAndClose();						 
					} 
					App.viewHospital.isModified = false;
			}); 	
		}		
	},
	
	showExternalEHRs : function(){
		var len = $('.helpAreaHospital').length;
		for (var i=1; i<len; i++){
			$('.helpAreaHospital:eq(1)').remove();
		}
		
		if (!this.xEhrsShown){
			$('#divExternalEHRs')
			//.appendTo($("body"))
			.modal('show');
			this.xEhrsShown = true;
		}
		else{
			$('#divExternalEHRs').modal('show');
		}
	}

});

//Single Hospital
App.Views.SingleHospital = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-hospital').html()),			
			events : {
				'click #edit' : 'goToEdit'
			},

			render : function() {
				this.$el.html(this.template(this.model.toJSON()));
				return this;
			},

			goToEdit : function() {
				$.removeCookie('active_tab');
				Backbone.history.navigate("hospital/"+this.model.get('id')+'/edit', true);				
			}			
		});

//Single Hospital_Measure
App.Views.SingleHospitalMeasure = Backbone.View
		.extend({
			tagName : 'tr',			
			template: _.template($('#single-hospital_measure').html()),			
			events : {				
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
											 verified:ch_verified,
											 measureCategory:this.model.get("measureCategory")
											}));
				//this.$el.html(this.template(this.model.toJSON()));
				
				this.$el.attr("data-product", this.model.get('p_index'));
				this.$el.attr("data-measure", this.model.get('m_index'));
				this.$el.attr("id", "m"+this.model.get('id'));
				
				return this;
			},
			
						
			changeVal: function(e) {		
				//HOSPITAL IS MODIFIED
				App.viewHospital.isModified = true;
				if (window.console) console.log("checkbox "+e.target.name+ ":", $(e.target).is(':checked'));
				
				if (e.target.name!="included") {
					var ch_slc = 'input[name="' + e.target.name + '"]';
					var tr_slc = 'tr#' + $(e.target).closest('tr').attr('id');
					var sl = tr_slc + ' ' + ch_slc;
					var sl_val = $(e.target).is(':checked');					
					$(sl).attr('checked', sl_val);

							
					$(tr_slc).each(function( index ) {
						//console.log( index + ": " + $(this).text() );
						$(this).attr('checked', sl_val);
						var p_index = $(this).data("product");
						var m_index = $(this).data("measure");
						
						switch (e.target.name) {
					    case "included":
					    	App.hospital_products[p_index].measures[m_index].included = sl_val;	
					        break;
					    case "completed":			        
					    	App.hospital_products[p_index].measures[m_index].completed = sl_val;	
					    	break;
					    case "confirmed":			        
					    	App.hospital_products[p_index].measures[m_index].confirmed = sl_val;
					    	break;
					    case "accepted":
					    	App.hospital_products[p_index].measures[m_index].accepted = sl_val;
					        break;    
					    case "verified":
					    	App.hospital_products[p_index].measures[m_index].verified = sl_val;
					        break;        
					    }
					});
				
				} else {
											    
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
				}
				
			 App.ho.set('products', App.hospital_products);		
			 /*App.ho.save(this.attributes,{
			        success: function (model, response) {
			           if (window.console) console.log(response);			           	           
			        },
			        error: function (model, response) {			 
			           if (window.console) console.log(response);
			        }
			});*/
			},
									
			goToDataElements : function(e) {				
				e.preventDefault();	
				
				App.ho.set({submit: true});
				
				App.ho.save(this.attributes,{
				        success: function (model, response) {
				           if (window.console) console.log(response);			           	           
				        },
				        error: function (model, response) {			 
				           if (window.console) console.log(response);
				        }
				});
				Backbone.history.navigate("hospital/"+ App.ho.get('id') +"/product/" + this.model.get('product_id')+ "/measure/" + this.model.get('id'), true);
			}			
		});


