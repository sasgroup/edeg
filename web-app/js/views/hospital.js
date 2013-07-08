// List of Hospitals
App.Views.Hospitals = Backbone.View.extend({
	template : _.template($('#hospital-list-template').html()),

	events : {
		'click #create_hospital' : 'createHospital'
	},

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
	},

	createHospital : function() {
		console.log("createHospital");
		Backbone.history.navigate("hospital/new", true)
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
		this.$el.html(this.template(this.model.toJSON()));

		console.log(App.products);
		App.products.forEach(this.appendProductOption,this);		

		return this;
	},
		

	appendProductOption: function(product) {
		console.log("product " + product.toJSON());
		var temp = _.template($('#product-option').html());
		this.$el.find('#slcProducts').append(temp({id:'t'+product.get('id'),code:product.get('code')}));		
	},

	changeTab: function (e){		
		var product_id = $(e.target).attr('href').replace('#t','');				
		var slcTab = '#myTabContent div#t' + product_id;
				
		this.appendHospitalMeasureTable(slcTab,product_id);		
	},


	// append HospitalMeasureTable to Tab
	appendHospitalMeasureTable : function(slcTab, product_id){	
		
	  var table_template = _.template($('#hospital-measure_table').html());       	
		
	  if ($(slcTab).has("table").length==0) {
		  
		$(slcTab).append(table_template());  		
		
		var path = '/ihm/api/product_measure/'+product_id;

		   $.getJSON(path, function(data){
		    	$.each( data, function( i, measure ) {   	
		    		    		
		    		var view = new App.Views.SingleHospitalMeasure({
		    			model : measure
		    		});
		    		$(slcTab + ' .hospitalMeasureTable tbody').append(view.render().el);
				});	
		    	
		    	 $(slcTab + ' .hospitalMeasureTable').dataTable({
		    		 "bPaginate": false,
		    	        "bLengthChange": false,
		    	        "bFilter": false,
		    	        "bSort": false,
		    	        "bInfo": false,
		    	        "bAutoWidth": false
				    });	
		    });		   
	  }	   
	},

	
	// apply
	applyHospitalOptions : function(){	
		// remove tabs
    	$("div#myTabContent").empty();
    	$("ul#myTab").empty();

    	//create tabs with checked products
    	$( "#slcProducts").multiselect('getChecked').each(function( index ) {  					
			var tabName = $(this).val();
			var tabNameText = $(this).closest('label').text();			

			$('div#myTabContent').append('<div id="'+tabName+'" class="tab-pane fade"></div>');			
			$('ul#myTab').append('<li class=""><a data-toggle="tab" href="#' + tabName + '">'+ tabNameText + '</a></li>');			
		});	

    	// set active tab
		$('div#myTabContent div').first().addClass('active in');
		$('ul#myTab li').first().addClass('active');

		var tabName = $('div#myTabContent div').first().prop('id');		

		var slcTab = '#myTabContent div#' + tabName;

		var product_id = $('#myTab li:first a').attr('href').replace('#t','');

		this.appendHospitalMeasureTable(slcTab,product_id);			
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
				console.log("goToEdit");
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
				var chd = '';
				
				this.$el.html(this.template({id:this.model.id,
											 code:this.model.code,
											 name:this.model.name,
											 included:this.model.included,
											 completed:this.model.completed,
											 confirmed:this.model.confirmed,
											 accepted:this.model.accepted,
											 verified:this.model.verified,
											 ch: chd
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
