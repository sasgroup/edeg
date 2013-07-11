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

	events : {
		'submit' 						 : 'submHospital',		
		'click #btnApplyHospitalOptions' : 'applyHospitalOptions',
		'click a[data-toggle="tab"]'	 : 'changeTab'			
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
		this.$el.find('#slcProducts').append(temp({id:'t'+product.get('id'),code:product.get('code')+"-"+product.get('name')}));		
	},
	
	appendEhrOption: function(ehr) {	
		console.log(ehr.code);
		var temp = _.template($('#product-option').html());
		this.$el.find('#slcEHRs').append(temp({id:'e'+ehr.get('id'),code:ehr.get('name')}));		
	},
		
	createTabs : function (){
		// set first option false
		$("#slcProducts").multiselect("widget").find(":checkbox").eq(0).click();				   

		//get assigned products
		var products = App.ho.get('products');
		$.each( products, function( i, product ) { 	
			// set checkboxes for assigned products
			$("#slcProducts").multiselect("widget").find('input[value=t'+ product.id  +']').click();			
		});
					
		//create tabs for all checked products
		$( "#slcProducts").multiselect('getChecked').each(function( index ) {  					
			var tabName = $(this).val();
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
				
		//this.appendHospitalMeasureTable(slcTab,product_id);		
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
				var view = new App.Views.SingleHospitalMeasure({ model : measure });				
				$(slcTab + ' .hospitalMeasureTable tbody').append(view.render().el);				
			});		
		});	  	   
	},

	
	// apply
	applyHospitalOptions : function(){		
    	//parameters
		var h_id = this.model.get('id');		
		var pr_ids = new Array();
		var e_id;
				
		$( "#slcProducts").multiselect('getChecked').each(function( index ) {  					
			var product_id = $(this).val();
			product_id = product_id.replace('t','');			
			pr_ids[index] = product_id;			
		});	
		
		$( "#slcEHRs").multiselect('getChecked').each(function( index ) {  					
			e_id = $(this).val();		
			e_id = e_id.replace('e','');			
		});	
				
		
		$.ajax({
			url: "api/hospital",
			type: 'POST',
			
			data: {ehr_id: e_id, product_ids : pr_ids, id: h_id},
			error: function(request, error) {
				console.log(request);
			},
			success: function(_data){
				console.log(_data);
				Backbone.history.loadUrl(Backbone.history.fragment);
			},
			traditional: true,
			dataType: 'JSON'
		});
		console.log("end");

	},

	submHospital : function(e) {
		e.preventDefault();		

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
				'click .edit-btn'   : 'goToEdit',
				'click .save-btn'   : 'goToSave',
				'click .cancel-btn' : 'goToCancel'
			},

			render : function() {						
				var ch_included  = (this.model.included)  ? "checked" : "";
				var ch_completed = (this.model.completed) ? "checked" : "";
				var ch_confirmed = (this.model.confirmed) ? "checked" : "";				
				var ch_accepted  = (this.model.accepted)  ? "checked" : "";
				var ch_verified  = (this.model.verified)  ? "checked" : "";
								
				this.$el.html(this.template({id:this.model.id,
											 code:this.model.code,
											 name:this.model.name,
											 included:ch_included,
											 completed:ch_completed,
											 confirmed:ch_confirmed,
											 accepted:ch_accepted,
											 verified:ch_verified
											}));			
				
				return this;
			},

			goToEdit : function(e) {
				console.log("goToEdit " + e.target);				
				$(e.target).closest('tr').find('td span.view').hide();
				$(e.target).closest('tr').find('td span.edit').show();				
			},
			
			goToSave : function (e){
				console.log("goToSave ");
				
				this.saveCheckboxState(e,'included');
				this.saveCheckboxState(e,'completed');
				this.saveCheckboxState(e,'confirmed');
				this.saveCheckboxState(e,'accepted');
				this.saveCheckboxState(e,'verified');
								
				
				$(e.target).closest('tr').find('td span.edit').hide();
				$(e.target).closest('tr').find('td span.view').show();				
			},
			
			saveCheckboxState : function(e, checkbox_name) {
				var chb = $(e.target).closest('tr').find('input[name='+ checkbox_name + ']');
				var checked = $(chb).attr('checked') == 'checked'? 'true' : 'false';				
				$(chb).closest('span').prev().html(checked);				
			},
			
			goToCancel : function (e){
				console.log("goToCancel " + e.target);				
				$(e.target).closest('tr').find('td span.edit').hide();
				$(e.target).closest('tr').find('td span.view').show();			
			},

			destroy : function(){
				console.log("destroy");
			}
		});
