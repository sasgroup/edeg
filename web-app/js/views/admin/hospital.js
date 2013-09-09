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
		//this.model.on('change', this.render, this);
		App.isModified = false;
	},
	
	events : {
		'click #submit-btn' 			 							: 'submHospital',	
		'click #submit-close-btn' 		 							: 'submCloseHospital',
		'click button#cancel' 			 							: 'returnOnMain', 
		'click #btnApplyHospitalOptions' 							: 'applyHospitalOptions',
		'click a[data-toggle="tab"]'	 							: 'changeTab',
		'change #notes, #email,  #txtEHRs' 	                        : 'changeVal',
		'click #btnExternalEHRs' 		 							: 'showExternalEHRs',
		'change #slcPopulationMethod'								: 'changeSlcVal'
	},

	render : function() {		
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
		if (window.console) console.log(ehr.code);
		var temp = _.template($('#product-option').html());
		this.$el.find('#slcEHRs').append(temp({id:ehr.get('id'),code:ehr.get('name')}));		
	},
	
			
	setPrimaryEhr : function(){		
		var ehr_id = this.model.get('ehr').id;		
		$("#slcEHRs").multiselect("widget").find('input[value='+ehr_id+']').click();
		
		$('#slcEHRs').change(function(e){			
			var new_e_id = $( "#slcEHRs").multiselect('getChecked').val();
			
			if (new_e_id!=ehr_id) {				
				alert("The EHR version has been updated. Make sure to reset locations for data elements.");
				App.isModified = true;
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
	
	},

	changeTab: function (e){		
		var product_id = $(e.target).attr('href').replace('#t','');				
		var slcTab = '#myTabContent div#t' + product_id;	
		
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
			"aaSorting": [[0, 'asc'], [1, 'asc']],
			"aoColumns": [
				  			{ "sSortDataType": "dom-checkbox" },
				  			null,
				  			null,
				  			{ "sSortDataType": "dom-checkbox" },
				  			{ "sSortDataType": "dom-checkbox" },
				  			{ "sSortDataType": "dom-checkbox" },
				  			{ "sSortDataType": "dom-checkbox" }
				  		]			
		 });	
	
	    //new FixedColumns( oTable, {"sHeightMatch": "none"} );		
		$(slcTab + ' .dataTables_scrollHeadInner').css('width', '934px');
		$(slcTab + ' .hospitalMeasureTable.dataTable').css('width', '934px');
		$(slcTab + ' .hospitalMeasureTable.dataTable td:eq(0), ' + slcTab + ' .hospitalMeasureTable.dataTable th:eq(0)').css('width', '30px');
		$(slcTab + ' .hospitalMeasureTable.dataTable td:eq(1), ' + slcTab + ' .hospitalMeasureTable.dataTable th:eq(1)').css('width', '73px');
		$(slcTab + ' .hospitalMeasureTable.dataTable td:eq(2), ' + slcTab + ' .hospitalMeasureTable.dataTable th:eq(2)').css('width', '336px');
		$(slcTab + ' .hospitalMeasureTable.dataTable td:eq(3), ' + slcTab + ' .hospitalMeasureTable.dataTable th:eq(3)').css('width', '83px');
		$(slcTab + ' .hospitalMeasureTable.dataTable td:eq(4), ' + slcTab + ' .hospitalMeasureTable.dataTable th:eq(4)').css('width', '72px');
		$(slcTab + ' .hospitalMeasureTable.dataTable td:eq(5), ' + slcTab + ' .hospitalMeasureTable.dataTable th:eq(5)').css('width', '90px');
		$(slcTab + ' .hospitalMeasureTable.dataTable td:eq(6), ' + slcTab + ' .hospitalMeasureTable.dataTable th:eq(6)').css('width', '63px');	    
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
																		"product_id" : product.id
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

	changeVal : function(e) {
		//HOSPITAL IS MODIFIED
		App.isModified = true;
		if (window.console) console.log(e.target.name);
		this.model.attributes[e.target.name] = $(e.target).val();
		if (window.console) console.log(this.model.attributes);
	},
	
	changeSlcVal : function(e) {
		//HOSPITAL IS MODIFIED
		App.isModified = true;
		//console.log(this.model);
		if (window.console) console.log(e.target.name);
		this.model.attributes[e.target.name] =  $(e.target).multiselect('getChecked').val();
		if (window.console) console.log(this.model.attributes);
	},
	
	submHospital : function(e) {
		e.preventDefault();		
		if (window.console) console.log('submHospital');
                                
        this.model.set("products" , App.hospital_products);
        
        this.model.save(this.attributes,{
	        success: function (model, response) {
	           if (window.console) console.log(response);
	           $('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
               //Backbone.history.navigate("hospital", true);	           
	        },
	        error: function (model, response) {
	        	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
	            //Backbone.history.navigate("hospital", true);	        	
	        }
	    });
	},
	
	submCloseHospital : function(e) {
		e.preventDefault();
		                                
        this.model.set("products" , App.hospital_products);
        
        this.model.save(this.attributes,{
	        success: function (model, response) {
	           if (window.console) console.log(response);
	          // if (response.resp=="ok") {	       
	        	   $('div#message-box').text("").append("response.message").removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2000).fadeOut(50);  
	        	   Backbone.history.navigate("hospital", true);
	          /* } else if (response.resp=="error") {
					var btn = '<button type="button" class="close">&times;</button>';
			    	$('div#message-box').text("").append(btn).append("response.message").removeClass().addClass('alert').addClass('alert-error').show();
		        	Backbone.history.navigate("hospital", true);	        	   
	           } 	     */             
	        },
	        error: function (model, response) {
	        	var btn = '<button type="button" class="close">&times;</button>';
	        	$('div#message-box').text("").append(btn).append("response.message").removeClass().addClass('alert').addClass('alert-error').show();
	            Backbone.history.navigate("hospital", true);	        	
	        }
	    });
	},
	
	returnOnMain: function (e) {
		e.preventDefault();
		this_hospital = this;
		if (App.isModified) {
			$('#app').append('<div id="dialog-confirm" title="Confirm the changes">'+
				'<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>Save the changes?</p>'+	
				'</div>')
		
			$( "#dialog-confirm" ).dialog({
		      resizable: false,
		      height:180,		     
		      modal: true,
		      buttons: {
		        "YES": function() {
		          $( this ).dialog( "close" );
		          this_hospital.submCloseHospital(e);
		        },
		        "NO": function() {
		          $( this ).dialog( "close" );
		          Backbone.history.navigate("/hospital", true);
		        }
		      }
			});		
		}
		else {
			Backbone.history.navigate("/hospital", true);

		}		

		
	},
	
	showExternalEHRs : function(){
		$('#divExternalEHRs').modal('show');

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
											 verified:ch_verified
											}));
				//this.$el.html(this.template(this.model.toJSON()));
				
				this.$el.attr("data-product", this.model.get('p_index'));
				this.$el.attr("data-measure", this.model.get('m_index'));
				this.$el.attr("id", "m"+this.model.get('id'));
				
				return this;
			},
			
						
			changeVal: function(e) {		
				//HOSPITAL IS MODIFIED
				App.isModified = true;
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
			 App.ho.save(this.attributes,{
			        success: function (model, response) {
			           if (window.console) console.log(response);			           	           
			        },
			        error: function (model, response) {			 
			           if (window.console) console.log(response);
			        }
			});
			},
									
			goToDataElements : function(e) {				
				e.preventDefault();							
				Backbone.history.navigate("hospital/"+ App.ho.get('id') +"/product/" + this.model.get('product_id')+ "/measure/" + this.model.get('id'), true);
			}			
		});


