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
	},
	
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
    	App.measures = new App.Collections.Measures();			
		App.measures.fetch().then(function(){			
			App.hospitals = new App.Collections.Hospitals();			
			App.hospitals.fetch().then(function(){
				var view = new App.Views.Product({model:productModel});
				$('#app').html(view.render().el);
			});			
		});		
    },
    
    ehr : function (ehrModel) {
    	App.hospitals = new App.Collections.Hospitals();			
		App.hospitals.fetch().then(function(){			
			App.dataElements = new App.Collections.DataElements();			
			App.dataElements.fetch().then(function(){
				var view = new App.Views.Ehr({model:ehrModel});
				$('#app').html(view.render().el);
				
				// jqGrid                
				App.dataElementsTable = jQuery("#dataElementsTable").jqGrid({ 
				    datatype: 'local',		   
				    width:'100%',
				    colNames:['isIMO', 'location', 'queryMnemonic', 'valueSet', 'valueSetRequired', 'locationtype'], 
				    colModel:[  {name:'isIMO', index:'isIMO'},
				                {name:'location', index:'location'},
				                {name:'queryMnemonic', index:'queryMnemonic'},
				                {name:'valueSet', index:'valueSet'},
				                {name:'valueSetRequired',index:'valueSetRequired'},
				                {name:'locationtype', index:'locationtype'}],				                
				                
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
			  view.appendDataElements();
				
				
			});			
		});		
    },
    measure : function (measureModel) {
    	App.products = new App.Collections.Products();			
		App.products.fetch().then(function(){			
			App.dataElements = new App.Collections.DataElements();			
			App.dataElements.fetch().then(function(){
				var view = new App.Views.Measure({model: measureModel});		
				$('#app').html(view.render().el); 
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
		App.measures = new App.Collections.Measures();			
		App.measures.fetch().then(function(){			
			App.ehrs = new App.Collections.Ehrs();			
			App.ehrs.fetch().then(function(){
				App.dataElement = new App.Models.DataElement();
				var view = new App.Views.NewDataElement({model:App.dataElement});
				$('#app').html(view.render().el);
			});			
		});		
	},

	// new EHR
	newEhr : function() {	
		this.ehr(new App.Models.Ehr());
	},	
	
	// new hospital
	newHospital : function() {		
		App.hospital = new App.Models.Hospital();
		var view = new App.Views.Hospital({model:App.hospital});
		$('#app').html(view.render().el);		
		$(document).ready(function(){
			   $("#example").multiselect({				
				   selectedList: 3			   
			   });
			
			   $('input[name=multiselect_example]').bind('click', function() {
				view.chooseProduct(this);				
			   });
			   
		});
	},
	
	
	// ------- EDIT ------------
	// edit product
	editProduct : function(id) {
		App.pr = new App.Models.Product();
		App.pr.fetch({data:{id: id}}).then(function(){
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
		console.log('dataElementEdit id:'+id)
		App.measures = new App.Collections.Measures();			
		App.measures.fetch().then(function(){			
			App.ehrs = new App.Collections.Ehrs();			
			App.ehrs.fetch().then(function(){
				var dataElement = App.dataElements.get(id);	
				var view = new App.Views.EditDataElement({model:dataElement});
				$('#app').html(view.render().el);
			});			
		});				
	},

	// edit ehr
	editEhr : function(id) {
		App.ehr = new App.Models.Ehr();
		App.ehr.fetch({data:{id: id}}).then(function(){
			App.route.ehr(App.ehr);
		})
	}

});