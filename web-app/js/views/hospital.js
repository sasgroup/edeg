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
		'click #submit-btn' 					 : 'submHospital',		
		'click #btnApplyHospitalOptions' : 'applyHospitalOptions',
		'click a[data-toggle="tab"]'	 : 'changeTab',
		'change #notes' 				 : 'changeVal'
	},

	render : function() {	
		if (!this.model.toJSON().id) {
			this.model.set("state" , "New");
		};
		
		console.log(this.model.toJSON());
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
	
	},

	changeTab: function (e){		
		var product_id = $(e.target).attr('href').replace('#t','');				
		var slcTab = '#myTabContent div#t' + product_id;	
	},
	
	// append HospitalMeasureTable to Tab
	appendHospitalMeasureTable : function(){		
	    var table_template = _.template($('#hospital-measure_table').html());						
		var products = App.ho.get('products');	
				
		// for all products
		$.each( products , function( i, product ) {					
			var slcTab = '#myTabContent div#t' + product.id;
			$(slcTab).append(table_template());	
			
			// get assigned measures
			var measures = product.measures;
			$.each( measures, function( i, measure ) {
				console.log(JSON.stringify(measure));		
				var hospitalMeasure	 =  new App.Models.HospitalMeasure({"id":measure.id,
																		"code":measure.code,
																		"name":measure.name,
																		"accepted" :measure.accepted,
																		"completed":measure.completed,
																		"confirmed":measure.confirmed,
																		"included" :measure.included,
																		"verified" :measure.verified});				
				
				var view = new App.Views.SingleHospitalMeasure({ model : hospitalMeasure });				
				$(slcTab + ' .hospitalMeasureTable tbody').append(view.render().el);				
			});		
			
			//$('.hospitalMeasureTable').dataTable();
			
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
								
		this.model.save(this.attributes,{
	        success: function (model, response) {
	           	           	           
	           //$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
               // Backbone.history.navigate("hospital", true);
	           App.ho.fetch({data:{id: h_id}}).then(function(){	   			
	   			App.route.hospital(App.ho);
	   		   });
	        },
	        error: function (model, response) {
	         //	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
	          //  Backbone.history.navigate("hospital", true);
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
				console.log("changeVal " + e.target);
				console.log(e.target.name);
				this.model.attributes[e.target.name] = $(e.target).val();
				console.log(this.model.attributes);
						
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
				var dataElementDefault = {code:"code", location:"location", source_ehr: "source_ehr", source: "source"};
				
				var ehrtbody = $('#modalDataElements tbody');
				//remove child elements
				$(ehrtbody).empty();		

				var hospitalElements = new App.Collections.HospitalElements();
				var hm_id = this.model.get('id');
								
				hospitalElements.fetch({data:{id: hm_id}}).then(function(){
					console.log(hospitalElements);
					
					_.each (hospitalElements.models, function(hospitalElement) {
						console.log(hospitalElement);
						var view = new App.Views.ModalDataElement({ model : hospitalElement});		
						var modalDataElementRow = view.render().el;
						$(ehrtbody).append(modalDataElementRow);						
						$(modalDataElementRow).find(".slcCodeType").val(hospitalElement.get('codeType').name);
						$(modalDataElementRow).find(".slcValueType").val(hospitalElement.get('valueType').name);
				    });
					
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
