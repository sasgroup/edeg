App.Router = Backbone.Router.extend({
	routes : {
		''                : 'products',
		'product'		  : 'products',
		'measure'         : 'measures',
		'element'         : 'dataElements',
		'hospital'        : 'hospitals',
		'ehr'        	  : 'ehrs',
		'reports'         : 'reports',
		
		'product/:new'    : 'newProduct',
		'measure/:new'    : 'newMeasure',
		'element/:new'    : 'newDataElement',
		'ehr/:new'        : 'newEhr',		
		
		'hospital/:id/edit': 'edithospital',
		'product/:id/edit': 'editProduct',
		'measure/:id/edit': 'editMeasure',
		'element/:id/edit': 'editDataElement',
		'ehr/:id/edit'    : 'editEhr',
		'reload/:id'      :	'reopenHospital' 		
	},

	initialize: function(options){
		App.route = this;
		App.products = new App.Collections.Products();			
		App.measures = new App.Collections.Measures();
		App.dataElements = new App.Collections.DataElements();
		App.hospitals = new App.Collections.Hospitals();
		App.ehrs = new App.Collections.Ehrs();	
		
		App.measureCategories  = new App.Collections.MeasureCategories();
		App.measureCategories.fetch();
		
		App.cqmDomains         = new App.Collections.CqmDomains();
		App.cqmDomains.fetch();
		
		
		App.productMeasures = new App.Collections.ProductMeasures();	
		
		App.hospitalMeasures = new App.Collections.HospitalMeasures();	

		
	},
	
	reopenHospital : function(id) {		
		Backbone.history.navigate("hospital/"+id+'/edit', true);		
	},
	
	// ------- LIST ------------
	// list of products
	products : function() {
		App.products.fetch().then(function(){
			App.viewProducts = new App.Views.Products({collection:App.products});
			$('#app').html(App.viewProducts.render().el);		
			
			var oTable = $('#table_items').dataTable( 
					{
						"bPaginate": false,
						"bFilter": true,
						"sScrollY": "470px",
						"bSort": true,
			 			"bInfo": false,
			 			"bAutoWidth": false,
			 			"aoColumnDefs": [
			 							{ 'bSortable': false, 'aTargets': [ 2,3,4 ] }
			 						 ],
						"bScrollCollapse": true,
						"bPaginate": false
					} );
		   new FixedColumns( oTable,
					{ "sHeightMatch": "none"} );						
		});		
	},
	
	// list of measures
	measures : function() {		
		App.measures.fetch().then(function(){
			App.viewMeasures = new App.Views.Measures({collection:App.measures});
			$('#app').html(App.viewMeasures.render().el);
			
			var oTable = $('#table_items').dataTable( 
					{
						"bPaginate": false,
						"bFilter": true,
						"sScrollY": "470px",
						"bSort": true,
			 			"bInfo": false,
			 			"bAutoWidth": false,
			 			"aoColumnDefs": [
			 							{'bSortable': false, 'aTargets': [ 2,3,4 ] }
			 						 ],
			 			"aaSorting": [[0, 'asc']],			 
						"bScrollCollapse": true						
					});
		   new FixedColumns( oTable,
					{ "sHeightMatch": "none"} );	
			
		});
	},	
	
	// list of elements
	dataElements : function() {		
		App.dataElements.fetch().then(function(){
			App.viewDataElements = new App.Views.DataElements({collection:App.dataElements});			
			$('#app').html(App.viewDataElements.render().el);
			
			var oTable = $('#table_items').dataTable( 
					{
						"bPaginate": false,
						"bFilter": true,
						"sScrollY": "470px",
						"bSort": true,
			 			"bInfo": false,
			 			"bAutoWidth": false,
			 			"aoColumnDefs": [
			 							{ 'bSortable': false, 'aTargets': [ 2,3,4 ] }
			 						 ],
						"bScrollCollapse": true,
						"bPaginate": false
					} );
		   new FixedColumns( oTable,
					{ "sHeightMatch": "none"} );						
		});
	},	
	
	// list of hospitals
	hospitals : function() {		
		App.hospitals.fetch().then(function(){
			App.viewHospitals = new App.Views.Hospitals({collection:App.hospitals});			
			$('#app').html(App.viewHospitals.render().el);
			
			var oTable = $('#table_items').dataTable( 
					{
						"bPaginate": false,
						"bFilter": true,
						"sScrollY": "470px",
						"bSort": true,
			 			"bInfo": false,
			 			"bAutoWidth": false,
			 			"aoColumnDefs": [
			 							{ 'bSortable': false, 'aTargets': [ -1 ] }
			 						 ],
						"bScrollCollapse": true,
						"bPaginate": false
					} );
		    new FixedColumns( oTable,
					{ "sHeightMatch": "none"} );		
		});
	},	
	
	// list of ehrs
	ehrs : function() {		
		App.ehrs.fetch().then(function(){
			console.log ();
			App.viewEhrs = new App.Views.Ehrs({collection:App.ehrs});			
			$('#app').html(App.viewEhrs.render().el);
			
			var oTable = $('#table_items').dataTable( 
					{
						"bPaginate": false,
						"bFilter": true,
						"sScrollY": "470px",
						"bSort": true,
			 			"bInfo": false,
			 			"bAutoWidth": false,
			 			"aoColumnDefs": [
			 							{ 'bSortable': false, 'aTargets': [ 2,3,4 ] }
			 						 ],
						"bScrollCollapse": true,
						"bPaginate": false
					} );
		   new FixedColumns( oTable,
					{ "sHeightMatch": "none"} );		
		});		
	},	
	
	// reports
	reports : function() {		
		$('#app').html("Here should be reports");				
	},	
	
	// ----- display Edit/New 
    product : function (productModel) {
		App.measures.fetch().then(function(){			
			App.hospitals.fetch().then(function(){
				var view = new App.Views.Product({model:productModel});
				$('#app').html(view.render().el);
				
				$('form#product-edit').validate({
				     rules: {
				   	 code: { required: true },
				         	name: { required: true }	               
				           },
				    messages: {
				       	code: "Code cannot be blank", 
				            name: "Name cannot be blank"
				         }
				});
			});			
		});		
    },
    
    ehr : function (ehrModel) {
		App.hospitals.fetch().then(function(){	
			App.dataElements.fetch().then(function(){
				var view = new App.Views.Ehr({model:ehrModel});
				$('#app').html(view.render().el);
				
				$('form#ehr-edit').validate({
				     rules: {
				   	 code: { required: true },
				         name: { required: true }	               
				         },
				    messages: {
				       	code: "Code cannot be blank", 
				            name: "Name cannot be blank"
				         }
				});
				
				$('.ehrTable').dataTable({
					"bPaginate": false,
					"bFilter": false,					
					"bSort": false,
					"bInfo": false,
					"bAutoWidth": false
				});		
			});	
		});		
    },
    measure : function (measureModel) {
		App.products.fetch().then(function(){			
			App.dataElements.fetch().then(function(){
				var view = new App.Views.Measure({model: measureModel});		
				$('#app').html(view.render().el); 
				
				$('form#measure-edit').validate({
				     rules: {
				   	 code: { required: true },
				         name: { required: true }	               
				         },
				    messages: {
				       	code: "Code cannot be blank", 
				            name: "Name cannot be blank"
				         }
				});
			});			
			  
		});		
    },
    
    dataElement  : function (dataElement) {
		App.measures.fetch().then(function(){	
			App.ehrs.fetch().then(function(){
				var view = new App.Views.DataElement({model: dataElement});		
				$('#app').html(view.render().el);
				
				$('form#element-edit').validate({
				     rules: {
				   	 code: { required: true },
				         name: { required: true }	               
				         },
				    messages: {
				       	code: "Code cannot be blank", 
				            name: "Name cannot be blank"
				         }
				});
				
				$('.ehrTable').dataTable({
					"bPaginate": false,
					"bFilter": false,					
					"bSort": false,
					"bInfo": false,
					"bAutoWidth": false
				 });			
				
			});
		});	
    },
    
    hospital  : function (hospital) {
		App.products.fetch().then(function(){	
		 App.ehrs.fetch().then(function(){		 
			var view = new App.Views.Hospital({model: hospital});		
			$('#app').html(view.render().el);	
		
				$( "#slcEHRs").multiselect({
			        multiple : false,
			        header : false,
			        noneSelectedText : "Select",
			        selectedList : 1,
			        height: "auto",
			        minWidth: "300px"
			    });	
	
				$( "#slcProducts").multiselect({
			        multiple : true,
			        header : true,
			        noneSelectedText : "Select",
			        selectedList : 1,
			        height: "auto",
			        minWidth: "300px"
			    });	
																				
				view.createTabs();
				view.setPrimaryEhr();
				
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
						 
					 });				
				
				new FixedColumns( oTable, {"sHeightMatch": "none"} );	
				
		});	
	  });	 
    },
	// ------- NEW ------------
    // new product
	newProduct : function() {			
		this.product(new App.Models.Product());
	},
	
	// new measure
	newMeasure : function() {		
		this.measure(new App.Models.Measure());	
	},

	// new dataElement
	newDataElement : function() {	
		App.element = new App.Models.DataElement();
		this.dataElement(App.element);		
	},

	// new EHR
	newEhr : function() {	
		App.ehr = new App.Models.Ehr();
		this.ehr(App.ehr);
	},	
		
	
	// ------- EDIT ------------
	// edit hospital
	edithospital : function(id) {
		App.ho = new App.Models.Hospital();		
		App.ho.fetch({data:{id: id}}).then(function(){
			//console.log(App.ho);
			App.route.hospital(App.ho);
		})
	},
	// edit product
	editProduct : function(id) {
		App.pr = new App.Models.Product();
		
		App.pr.fetch({data:{id: id}}).then(function(){
			console.log(this);
			App.route.product(App.pr);
		})
	},

	 // edit measure
	editMeasure : function(id) {
		App.me = new App.Models.Measure();
		App.me.fetch({data:{id: id}}).then(function(){
			
			App.route.measure(App.me);
		})
	},

	// edit dataElement
	editDataElement : function(id) {
		App.element = new App.Models.DataElement();
		App.element.fetch({data:{id: id}}).then(function(){
			App.route.dataElement(App.element);
		})			
	},

	// edit ehr
	editEhr : function(id) {
		App.ehr = new App.Models.Ehr();
		App.ehr.fetch({data:{id: id}}).then(function(){
			App.route.ehr(App.ehr);
		})
	}

});
