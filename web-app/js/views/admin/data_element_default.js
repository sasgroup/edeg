App.Views.DataElementsDefault = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-data-elements-default').html()),			
			
			events : {
				'click #plus-btn' : 'addRow',
				'click #minus-btn': 'removeRow',
				'change .slcValueType, .slcParent, #location' : 'changeVal',
				'change .slcValuesType'                       : 'changeValuesType'
			},
						
			render : function() {	
				this.$el.html(this.template({loc:this.model.location, value_type: this.model.valueType.name, ehr: this.model.linkId}));		
				this.$el.attr("id", "d"+this.model.id);
				return this;
			},
			changeVal : function(e){
				var curId = this.model.id
				var dataElementDefaults = App[this.model.parent].get('dataElementDefaults');
				 dataElementDefaults.forEach(function( dataElementDefault ){
					if (dataElementDefault.id == curId){
						if (e.currentTarget.className == "location")
							dataElementDefault.location = e.target.value;					
						if (e.currentTarget.className == "slcValueType")
							dataElementDefault.valueType.name = e.target.value;
						if (e.currentTarget.className == "slcParent")
							dataElementDefault.linkId = e.target.value.replace('e','');
					};	
				});
				App[this.model.parent].set("dataElementDefaults" , dataElementDefaults);
			},
			
			changeValuesType : function(e){				
				var curId = this.model.id
				var dataElementDefaults = App[this.model.parent].get('dataElementDefaults');
				 dataElementDefaults.forEach(function( dataElementDefault ){
					var cur_ids='';
					if (dataElementDefault.id == curId){						
						$(e.target).multiselect('getChecked').each(function( index ) {  					
							cur_ids = cur_ids + ';'  + $(this).val();
						});	
						
						if (cur_ids.substring(0, 1) == ';') { 
							cur_ids = cur_ids.substring(1);
						}
						
						//alert('changeValuesType: ' + cur_ids);
						dataElementDefault.ids = cur_ids;
					};	
				});
				App[this.model.parent].set("dataElementDefaults" , dataElementDefaults);
			},
			
			defaultElementOptions: function() {
				var temp = _.template($('#default-element-option').html());
				var html= '';	
				
				var default_element_option = this.options.default_element;
				
				if (default_element_option=="ehr") {				
					App.ehrs.each(function(ehr) {
						html = html + temp({id:'e'+ehr.get('id'), code:ehr.get('code')}); //replace name to code
					});			
				} else 
				
				if (default_element_option=="element") {				
					App.dataElements.each(function(element) {
						html = html + temp({id:'e'+element.get('id'), code:element.get('code')});
					});			
				} 					
				
				return html;
			},
			
			
			getFirstDefaultElementOptions: function() {			
				var default_element_option = this.options.default_element;
				
				if (default_element_option=="ehr") {				
						return App.ehrs.at(0).get('id');	
				} else 
				
				if (default_element_option=="element") {				
					   return App.dataElements.at(0).get('id');
				}
			},
			
			
			
			valuesTypeOptions: function() {
				var temp = _.template($('#multiple-default-element-option').html());
				var html= '';	
				
				App.valuesTypes.each(function(vtype) {
						html = html + temp({id:vtype.get('id'), code:vtype.get('name')}); 
				});			
				
				return html;
			},
			
			addRow : function (){
				if (window.console) console.log(App[this.model.parent].get('dataElementDefaults'));
				var timeId = parseInt(App[this.model.parent].timeId);
				
				
				//var linkId = $(el).find('.slcParent').val().substring(1);
				var linkId = this.getFirstDefaultElementOptions();
				
				var emptyDataElementDefault = {"id":timeId, "linkId":linkId, "parent":this.options.parent, "location":"", "valueType":{"enumType":"","name":"NotApplicable"}};
				
				var dataElementDefaults = App[this.model.parent].get('dataElementDefaults');
				dataElementDefaults.push(emptyDataElementDefault);
				App[this.model.parent].set("dataElementDefaults" , dataElementDefaults);
				App[this.model.parent].timeId = parseInt(timeId-1);
				
				var view = new App.Views.DataElementsDefault({ model : emptyDataElementDefault, default_element: this.options.default_element, parent:this.options.parent});
				var ehrtbody = this.$el.closest('tbody');
				$(ehrtbody).append(view.render().el);					
				
				
				var el = view.render().el;
				$(el).find('.slcParent').append(this.defaultElementOptions());
				if (window.console) console.log(App[this.model.parent].get('dataElementDefaults'));
				
				$(el).find('.slcValuesType').append(this.valuesTypeOptions()); //new
				
				$(el).find('.slcValuesType').multiselect({
			        multiple : true,
			        header : true,
			        noneSelectedText : "Select",
			        selectedList : 1,
			        height: "auto"			       
			    });		
				
				$(el).find('.slcValuesType').multiselect("uncheckAll");
			},
			
			removeRow : function (e){

				var dataElementDefaults = App[this.model.parent].get('dataElementDefaults');
				for (var i = 0; i < dataElementDefaults.length; i++) {
					if (dataElementDefaults[i].id == this.model.id) {
						removeIndex = i;
					}
				}
				dataElementDefaults.splice(removeIndex,1);
				
				App[this.model.parent].set("dataElementDefaults" , dataElementDefaults);
				if (App[this.model.parent].get('dataElementDefaults').length == 0)
					this.addRow();				
				this.$el.remove();

			}
			
		});
;