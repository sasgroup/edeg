App.Router = Backbone.Router.extend({
	routes : {
		''                : 'products',
		'product'		  : 'products',
		'measure'         : 'measures',
		'element'         : 'dataElements',
		'hospital'        : 'hospitals',
		'ehr'        	  : 'ehrs',
		
		'product/:new'    : 'newProduct',
		'measure/:new'    : 'newMeasure',
		'element/:new'    : 'newDataElement',
		'ehr/:new'        : 'newEhr',
		'hospital/:new'   : 'newHospital',
		
		'hospital/:id/edit': 'edithospital',
		'product/:id/edit': 'editProduct',
		'measure/:id/edit': 'editMeasure',
		'element/:id/edit': 'editDataElement',
		'ehr/:id/edit'    : 'editEhr'
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
		
	},
	
	/*jqGrid : function(){
		// jqGrid                
		App.dataElementsTable = jQuery("#dataElementsTable").jqGrid({ 
		    datatype: 'local',		   
		    width:'100%',
		    colNames:['location', 'source', 'sourceEHR', 'valueType', 'codeType'], 
		    colModel:[  {name:'location', index:'location'},
		                {name:'source', index:'source'},
		                {name:'sourceEHR', index:'sourceEHR'},
		                {name:'valueType',index:'valueType'},
		                {name:'codeType', index:'codeType'}],				                
		                
		    rowNum:10, 
		    rowList:[10,20,30], 
		    pager: '#pager5', 
		    sortname: 'location', 
		    viewrecords: true, 
		    sortorder: "desc", 
		    				             
		    loadComplete : function(data) {
		        //alert('grid loading completed ' + data);
		    },
		    loadError : function(xhr, status, error) {
		        alert('grid loading error' + error);
		    }
		});
	   // jqGrid		
	},*/
	// ------- LIST ------------
	// list of products
	products : function() {
		App.products.fetch().then(function(){
			App.viewProducts = new App.Views.Products({collection:App.products});
			$('#app').html(App.viewProducts.render().el);
		});		
	},
	
	// list of measures
	measures : function() {		
		App.measures.fetch().then(function(){
			App.viewMeasures = new App.Views.Measures({collection:App.measures});
			$('#app').html(App.viewMeasures.render().el);
		});
	},	
	
	// list of elements
	dataElements : function() {		
		App.dataElements.fetch().then(function(){
			App.viewDataElements = new App.Views.DataElements({collection:App.dataElements});			
			$('#app').html(App.viewDataElements.render().el);
		});
	},	
	
	// list of hospitals
	hospitals : function() {		
		App.hospitals.fetch().then(function(){
			App.viewHospitals = new App.Views.Hospitals({collection:App.hospitals});			
			$('#app').html(App.viewHospitals.render().el);
		});
	},	
	
	// list of ehrs
	ehrs : function() {		
		App.ehrs.fetch().then(function(){
			console.log ();
			App.viewEhrs = new App.Views.Ehrs({collection:App.ehrs});			
			$('#app').html(App.viewEhrs.render().el);
		});		
	},	
	
	// ----- display Edit/New 
    product : function (productModel) {
		App.measures.fetch().then(function(){			
			App.hospitals.fetch().then(function(){
				var view = new App.Views.Product({model:productModel});
				$('#app').html(view.render().el);
			});			
		});		
    },
    
    ehr : function (ehrModel) {
		App.hospitals.fetch().then(function(){	
			App.dataElements.fetch().then(function(){
				var view = new App.Views.Ehr({model:ehrModel});
				$('#app').html(view.render().el);
				//App.route.jqGrid();					
				//view.appendDataElements();
			});	
		});		
    },
    measure : function (measureModel) {
		App.products.fetch().then(function(){			
			App.dataElements.fetch().then(function(){
				var view = new App.Views.Measure({model: measureModel});		
				$('#app').html(view.render().el); 
			});			
			  
		});		
    },
    
    dataElement  : function (dataElement) {
		App.measures.fetch().then(function(){	
			App.ehrs.fetch().then(function(){
				var view = new App.Views.DataElement({model: dataElement});		
				$('#app').html(view.render().el);
				//App.route.jqGrid();					
				//view.appendDataElements();
			});
		});	
    },
    
    hospital  : function (hospital) {
		App.products.fetch().then(function(){	
		 App.ehrs.fetch().then(function(){		
			var view = new App.Views.Hospital({model: hospital});		
			$('#app').html(view.render().el);	
			
			$(document).ready(function(){
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
								
			});		
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
		this.dataElement(new App.Models.DataElement());		
	},

	// new EHR
	newEhr : function() {	
		this.ehr(new App.Models.Ehr());
	},	
	
	// new hospital
	newHospital : function() {		
		App.hospital = new App.Models.Hospital();

		//get list of products
		App.products.fetch().then(function(){			
		
			var view = new App.Views.Hospital({model:App.hospital});
			
			$('#app').html(view.render().el);		
		
			$(document).ready(function(){
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
			});		
		});
	},
	
	
	// ------- EDIT ------------
	// edit hospital
	edithospital : function(id) {
		App.ho = new App.Models.Hospital();
		
		App.ho.fetch({data:{id: id}}).then(function(){
			console.log(App.ho);
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
		App.de = new App.Models.DataElement();
		App.de.fetch({data:{id: id}}).then(function(){
			App.route.dataElement(App.de);
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
