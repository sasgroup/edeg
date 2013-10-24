// List of DataElements
App.Views.DataElements = Backbone.View.extend({
	template : _.template($('#element-list-template').html()),

	events : {
		'click #create_dataElement' : 'createDataElement'
	},

	initialize : function() {		
		this.collection.on('add', this.appendDataElement, this);
		this.collection.on('change', this.render, this);
		$('.helpAreaElement').wysihtml5();
	},

	render : function() {		
		this.$el.html(this.template({
			dataElements : this.collection
		}));
		this.collection.each(this.appendDataElement, this);
		return this;
	},

	appendDataElement : function(dataElement) {
		var view = new App.Views.SingleDataElement({
			model : dataElement
		});
		this.$el.find('#table_items tbody').append(view.render().el);
	},

	createDataElement : function() {
		Backbone.history.navigate("element/new", true)
	}
});


// Edit/New DataElement
App.Views.DataElement = Backbone.View.extend({
	template : _.template($('#element-template').html()),
	
	events : {
		'submit' : 'editDataElement',
		'click button#cancel' : 'returnOnMain', 
		'change  #name, #notes' : 'changeVal',		
		'change .checkbox' : 'changeCh',
		'click #btnHelp' : 'showHelpDialog'		
	},
	
	render : function() {	
		var state = (this.model.toJSON().id)? "Edit Data Element" : "Add New Data Element"; 
		this.model.set("state", state);
		
		this.$el.html(this.template(this.model.toJSON()));		
		this.model.timeId = -1;
						
		//append Measures
		this.appendMeasures();
		
		//append DataElementsDefault
		this.appendDataElementsDefault();		
		this.$el.find('.slcEHR').append(this.ehrOptions());		
				
		return this;
	},
			
	ehrOptions: function() {
		var temp = _.template($('#default-element-option').html());
		var html= '';		
		App.ehrs.each(function(ehr) {
			html = html + temp({id:'e'+ehr.get('id'), code:ehr.get('code')});
		});			
		return html;
	},
	
	vtypeOptions: function() {
		var temp = _.template($('#multiple-default-element-option').html());
		var html= '';		
		App.valuesTypes.each(function(vtype) {
			html = html + temp({id:vtype.get('id'), code:vtype.get('name')});
		});			
		return html;
	},
		
	appendMeasures : function(){
		var temp = _.template($('#single-element-measure').html());	
		checked = [];
		unchecked = [];
				
		var mids = _.pluck(this.model.get('measures'), 'mid');					
		var measure_ids = App.measures.pluck('id');
				
		App.measures.forEach(function(m){					
			if (_.contains(mids, m.get('id'))) {				
				checked.push({name:m.get('name'),id:m.get('id')});
			} else {
				unchecked.push({name:m.get('name'),id:m.get('id')});
			}
		});		
		
		checked.sort(function(a, b){
		    if(a.name < b.name) return -1;
		    if(a.name > b.name) return 1;
		    return 0;
		});
		
		unchecked.sort(function(a, b){
		    if(a.name < b.name) return -1;
		    if(a.name > b.name) return 1;
		    return 0;
		});
		
		for(var de_measure in checked) {
			var measure = checked[de_measure];
			this.$el.find('div#measures').append(temp({name:measure.name,id:measure.id,ch:'checked'}));	 
		}	
		
		for(var de_measure in unchecked) {
			var measure = unchecked[de_measure];
			this.$el.find('div#measures').append(temp({name:measure.name,id:measure.id,ch:''}));					
		}		
	},	
	
	appendDataElementsDefault: function(){
		var table_template = _.template($('#data-elements-default-table').html());		
		this.$el.find('div#ehrs').append(table_template({ehr_element:"EHR"}));			
		
		var dataElementDefaults = this.model.get('dataElementDefaults');
		var ehrtbody = this.$el.find('div#ehrs .ehrTable tbody');
				
		var optionsList = this.ehrOptions();
		
		var vtypesList = this.vtypeOptions(); //new
				
		if (dataElementDefaults !== undefined) {
		  $.each( dataElementDefaults, function( i, dataElementDefault ) {				
			  dataElementDefault.parent = "element";
			
			var view = new App.Views.DataElementsDefault({ model : dataElementDefault, default_element: "ehr", parent:"element"});		
			var dataElementDefaultRow = view.render().el;
			$(ehrtbody).append(dataElementDefaultRow);			
			//$(dataElementDefaultRow).find(".slcValueType").val(dataElementDefault.valueType.name);
			
			$(dataElementDefaultRow).find('.slcParent').append(optionsList);			
			$(dataElementDefaultRow).find(".slcParent").val("e"+dataElementDefault.linkId);
			
			$(dataElementDefaultRow).find('.slcValuesType').append(vtypesList); //new	
			
			var de_ids = dataElementDefault.ids;
			var ids=de_ids.split(";");			
			for (var i = 0; i < ids.length; i++) {	
				$(dataElementDefaultRow).find('.slcValuesType option[value='+ids[i]+']').attr("selected","selected") ;
			}				
			
		  });	
		}
				
		if ((dataElementDefaults == undefined)||(dataElementDefaults.length == 0)) { 	
			var linkId = App.ehrs.at(0).get('id');
			
			var emptyDataElementDefault = {"id":"-1","linkId":linkId,"location":"" /*,"valueType":{"enumType":"","name":"NotApplicable"} */};
			emptyDataElementDefault.parent = "element";
			this.model.timeId = -2;
			var dataElementDefaults = this.model.get('dataElementDefaults');
			dataElementDefaults.push(emptyDataElementDefault);
			this.model.set("dataElementDefaults" , dataElementDefaults);
			
			var view = new App.Views.DataElementsDefault({ model : emptyDataElementDefault, default_element: "ehr", parent:"element"});	
			var dataElementDefaultRow = view.render().el;
			
			$(ehrtbody).append(dataElementDefaultRow);	
			$(dataElementDefaultRow).find('.slcParent').append(optionsList);			
			$(dataElementDefaultRow).find(".slcParent").val("e"+emptyDataElementDefault.linkId);	
			
			$(dataElementDefaultRow).find('.slcValuesType').append(vtypesList); //new
		}		
	},
	
	changeVal : function(e) {
		this.model.attributes[e.target.name] = $(e.target).val();
	},
	changeCh : function(e) {
		if (e.target.name == 'measure' ) {
			if ( e.target.checked ) {				
				var measures = this.model.get("measures");
				measures.push({"mid" : e.target.id, "mname" : e.target.value});
				this.model.set("measures" , measures);
			} else {				
				var measures = this.model.get("measures");
				var removeIndex; 
				for (var i = 0; i < measures.length; i++) {
					if (measures[i].mid == e.target.id) {
						removeIndex = i;
					}
				}
				measures.splice(removeIndex,1);
			};
		};	
	},
	
	editDataElement : function(e) {
		e.preventDefault();	
		
		//var emptyValuesType = _.pluck(this.model.get('dataElementDefaults'),"ids").indexOf('');
		
		var emptyValuesType = _.indexOf(_.pluck(this.model.get('dataElementDefaults'),"ids"), '');
		
		if (emptyValuesType!=-1) 		{
			bootbox.alert("Please specify Values Type for [" + this.model.get('dataElementDefaults')[emptyValuesType].location + "] location.", function() {
			});
			
			return;			
		}	
		
		this.model.attributes.help = $('.helpAreaElement').val();		
		this.model.set({code:this.$el.find('#code').val()});
			
		this.model.save(null,{
	        success: function (model, response) {	           
	           if (response.resp=="ok") {	        	   
	        	   $('div#message-box').text("").append(response.message).removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2000).fadeOut(50);              	           
	        	   Backbone.history.navigate("element", true);
	           } else if (response.resp=="error") {
					var btn = '<button type="button" class="close">&times;</button>';
			    	$('div#message-box').text("").append(btn).append(response.message).removeClass().addClass('alert').addClass('alert-error').show();
		        	Backbone.history.navigate("element", true);	        	   
	           } 
	        },
	        error: function (model, response) {
	        	var btn = '<button type="button" class="close">&times;</button>';
		    	$('div#message-box').text("").append(btn).append(response.message).removeClass().addClass('alert').addClass('alert-error').show();
	            Backbone.history.navigate("element", true);
	        }
	    });
	},
	
	returnOnMain: function () {		
		Backbone.history.navigate("/element", true);				
	},
	
	showHelpDialog : function(){
		if (! $('.helpAreaElement').data("wysihtml5") )
			$('.helpAreaElement').wysihtml5();
		$('#myHelp')
		//.appendTo($("body"))
		.modal('show');
	}
	
});


//Single DataElement
App.Views.SingleDataElement = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-element').html()),			
			events : {
				'click #edit' : 'goToEdit',
				'click #destroy' : 'destroy'
			},

			render : function() {
				this.$el.html(this.template(this.model.toJSON()));			
				
				return this;
			},

			goToEdit : function() {											
				Backbone.history.navigate("element/"+this.model.get('id')+'/edit', true);
			},
			
			destroy : function(e){				
				e.preventDefault();
								
				var el = this.$el;
				var thisElement = this.model;
				
				bootbox.confirm("Are you sure you want to delete this Data Element?", function(result) {					
					if (result) {
						thisElement.destroy({
								wait: true,
							    success: function(model, response){							    	
							    	$('div#message-box').text("").append(response.message).removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2000).fadeOut(50);    
						    		el.remove();
							    	Backbone.history.navigate("element", true);
							     },
							     error: function (model, response) {							    	 							    	 
							    	 var btn = '<button type="button" class="close">&times;</button>';
							    	 $('div#message-box').text("").append(btn).append(response.responseText).removeClass().addClass('alert').addClass('alert-error').show();
							    	 Backbone.history.navigate("element", true);
							     }
						 });
						 
					 }
				});				
			}
		});