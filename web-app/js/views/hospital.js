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
		//e.preventDefault();
		$('.hospitalMeasureTable').jqGrid('GridUnload');
		$('.hospitalMeasureTable').remove();		
						
		var product_id = $(e.target).attr('href').replace('#t','');				
		var slcTab = '#myTabContent div#t' + product_id;
		
		this.appendHospitalMeasureTable(slcTab);
		this.loadValueToHospitalMeasureTable(product_id);		
	},
	
	
	// append HospitalMeasureTable to Tab
	appendHospitalMeasureTable : function(slcTab){			
		$(slcTab).append('<table class="hospitalMeasureTable"></table>');		
		var lastsel2;
		$('.hospitalMeasureTable').jqGrid({ 
		    datatype: 'local',		   
		    //width:'100%',		
		    height: 250,			    
		    colNames:['ID','CODE', 'TITLE', 'NOTES','Use','Completed','Confirmed','Accepted','Verified'],
		    colModel:[
                {name:'id',index:'id', width:60, sorttype:"int"},
                {name:'code',index:'code', width:100, sorttype:"date"},
                {name:'name',index:'name', width:200},
                {name:'note',index:'note', width:150},
                {name:'included',index:'included', width:70, editable: true, edittype:"checkbox",editoptions: {value:"Yes:No"}},
                {name:'completed',index:'completed', width:70, editable: true, edittype:"checkbox",editoptions: {value:"Yes:No"}},
                {name:'confirmed',index:'confirmed', width:70, editable: true, edittype:"checkbox",editoptions: {value:"Yes:No"}},
                {name:'accepted',index:'accepted', width:70, editable: true, edittype:"checkbox",editoptions: {value:"Yes:No"}},
                {name:'verified',index:'verified', width:70, editable: true, edittype:"checkbox",editoptions: {value:"Yes:No"}}
        	],	            	           
		                
		    rowNum:10, 
		    rowList:[10,20,30], 					   
		    sortname: 'location', 
		    viewrecords: true, 
		    sortorder: "desc", 
		    				             
		    loadComplete : function(data) {
		        //alert('grid loading completed ' + data);
		    },
		    loadError : function(xhr, status, error) {
		        alert('grid loading error' + error);
		    },
		    onSelectRow: function(id){
    			//$( "#dialog" ).dialog();
    			console.log('Selected row');
    			if(id && id!==lastsel2){
    				jQuery('.hospitalMeasureTable').jqGrid('restoreRow',lastsel2);
    				jQuery('.hospitalMeasureTable').jqGrid('editRow',id,true);
    				lastsel2=id;
    			}
			},   			
			multiselect: false            			
		});		
	},
	
	// load values
	loadValueToHospitalMeasureTable: function(product_id){
		var path = '/ihm/api/product/'+product_id;

	    $.getJSON(path, function(data){
	    	$.each( data.measures, function( i, measure ) {	    		
				$('#myTabContent div#t' + product_id+ ' .hospitalMeasureTable').jqGrid('addRowData', (i + 1), {id:measure.mid, 
					   code:measure.mcode,
					   name:measure.mname,
					   included:"No",
					   completed:"No",
					   confirmed:"No",
					   accepted:"No",
					   verified:"Yes"				
				});				
			});	
	    });	
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
		
		this.appendHospitalMeasureTable(slcTab);	
		this.loadValueToHospitalMeasureTable(product_id);
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