// List of DataElements
App.Views.DataElements = Backbone.View.extend({
	template : _.template($('#element-list-template').html()),

	events : {
		'click #create_dataElement' : 'createDataElement'
	},

	initialize : function() {		
		this.collection.on('add', this.appendDataElement, this);
		this.collection.on('change', this.render, this);
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
		if (!this.model.toJSON().id) {
			this.model.set("state" , "New");
		};				
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
		
	appendMeasures : function(){
		var temp = _.template($('#single-element-measure').html());	
		checked = [];
		unchecked = [];
				
		var mids = _.pluck(this.model.get('measures'), 'mid');					
		var measure_ids = App.measures.pluck('id');
				
		App.measures.forEach(function(m){			
			if (window.console) console.log(m);		
			if (_.contains(mids, m.get('id'))) {				
				checked.push({name:m.get('name'),id:m.get('id')});
			} else {
				unchecked.push({name:m.get('name'),id:m.get('id')});
			}
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
		if (window.console) console.log("dataElementDefaults " + dataElementDefaults);
		
		var optionsList = this.ehrOptions();
				
		if (dataElementDefaults !== undefined) {
		  $.each( dataElementDefaults, function( i, dataElementDefault ) {				
			  dataElementDefault.parent = "element";
			
			var view = new App.Views.DataElementsDefault({ model : dataElementDefault, default_element: "ehr", parent:"element"});		
			var dataElementDefaultRow = view.render().el;
			$(ehrtbody).append(dataElementDefaultRow);	
			$(dataElementDefaultRow).find(".slcCodeType").val(dataElementDefault.codeType.name);
			$(dataElementDefaultRow).find(".slcValueType").val(dataElementDefault.valueType.name);
			
			$(dataElementDefaultRow).find('.slcParent').append(optionsList);			
			$(dataElementDefaultRow).find(".slcParent").val("e"+dataElementDefault.linkId);			
		  });	
		}
				
		if ((dataElementDefaults == undefined)||(dataElementDefaults.length == 0)) { 	
			var emptyDataElementDefault = {"id":"-1","linkId":"1","location":"","valueType":{"enumType":"","name":""},"codeType":{"enumType":"","name":""}};
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
		}		
	},
	
	changeVal : function(e) {
		this.model.attributes[e.target.name] = $(e.target).val();
	},
	changeCh : function(e) {
		if (window.console) console.log(e.target.value + ' ' + e.target.id + ' ' + e.target.checked+ ' '+e.target.name);
		if (e.target.name == 'measure' ) {
			if ( e.target.checked ) {
				if (window.console) console.log("Push measure");
				var measures = this.model.get("measures");
				measures.push({"mid" : e.target.id, "mname" : e.target.value});
				this.model.set("measures" , measures);
			} else {
				if (window.console) console.log("Remove measures");
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
		this.model.attributes.help = $('.helpArea').val();
		
		this.model.set({code:this.$el.find('#code').val()});
		
		this.model.save(null,{
	        success: function (model, response) {
	        	if (window.console) console.log(response);
	           $('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
               Backbone.history.navigate("element", true);
	        },
	        error: function (model, response) {
	        	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
	            Backbone.history.navigate("element", true);
	        }
	    });
	},
	
	returnOnMain: function () {		
		Backbone.history.navigate("/element", true);				
	},
	
	showHelpDialog : function(){
		if (! $('.helpArea').data("wysihtml5") )
			$('.helpArea').wysihtml5();
		$('#myHelp').modal('show');
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
				if (window.console) console.log(this.model);
				if (window.console) console.log("goToEdit",this.model.get('id'));							
				Backbone.history.navigate("element/"+this.model.get('id')+'/edit', true);
			},
			
			destroy : function(e){
				if (window.console) console.log("destroy");
				e.preventDefault();
				
				if (confirm('Are you sure you want to delete this DataElement?')) {
								
				var el = this.$el;
				
				this.model.destroy({
					wait: true,
				    success: function(model, response){
				    	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
			    		el.remove();
				    	Backbone.history.navigate("element", true);
				     },
				     error: function (model, response) {
				    	 if (window.console) console.log(response);
				    	 $('div#message-box').text("").append(response.responseText).fadeIn(500).delay(1500).fadeOut(500);
				            Backbone.history.navigate("element", true);
				     }
				});
				}
			}
		});