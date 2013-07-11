App.Views.DataElementsDefault = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-data-elements-default').html()),			
			
			events : {
				'click #plus-btn' : 'addRow',
				'click #minus-btn': 'removeRow',
				'change .slcCodeType, .slcValueType, .slcParent, #location' : 'changeVal'
			},
						
			render : function() {	
				this.$el.html(this.template({loc:this.model.location, code_type: this.model.codeType.name, value_type: this.model.valueType.name, ehr: this.model.linkId}));				
				return this;
			},
			changeVal : function(e){
				var curId = this.model.id
				var dataElementDefaults = App[this.model.parent].get('dataElementDefaults');
				 dataElementDefaults.forEach(function( dataElementDefault ){
					if (dataElementDefault.id == curId){
						if (e.currentTarget.className == "location")
							dataElementDefault.location = e.target.value;
						if (e.currentTarget.className == "slcCodeType")
							dataElementDefault.codeType.name = e.target.value;
						if (e.currentTarget.className == "slcValueType")
							dataElementDefault.valueType.name = e.target.value;
						if (e.currentTarget.className == "slcParent")
							dataElementDefault.linkId = e.target.value.replace('e','');
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
						html = html + temp({id:'e'+ehr.get('id'), name:ehr.get('name')}); //hardcode
					});			
				} else 
				
				if (default_element_option=="element") {				
					App.dataElements.each(function(element) {
						html = html + temp({id:'e'+element.get('id'), name:element.get('name')});
					});			
				} 					
				
				return html;
			},
			
			addRow : function (){
				var timeId = App[this.model.parent].timeId;
				var emptyDataElementDefault = {"id":timeId,"parent":"ehr","location":"","linkId":"","valueType":{"enumType":"","name":""},"codeType":{"enumType":"","name":""}};
				var dataElementDefaults = App[this.model.parent].get('dataElementDefaults');
				dataElementDefaults.push(emptyDataElementDefault);
				App[this.model.parent].set("dataElementDefaults" , dataElementDefaults);
				App[this.model.parent].timeId = parseInt(timeId-1);
				var view = new App.Views.DataElementsDefault({ model : emptyDataElementDefault, default_element: this.options.default_element});
				var ehrtbody = this.$el.closest('tbody');
				$(ehrtbody).append(view.render().el);					
				$(view.render().el).find('.slcParent').append(this.defaultElementOptions());
				console.log(App[this.model.parent].get('dataElementDefaults'));
			},
			
			removeRow : function (e){
				var dataElementDefaults = App[this.model.parent].get('dataElementDefaults');
				for (var i = 0; i < dataElementDefaults.length; i++) {
					if (dataElementDefaults[i].id = this.model.id) {
						removeIndex = i;
					}
				}
				dataElementDefaults.splice(removeIndex,1);
				App[this.model.parent].set("dataElementDefaults" , dataElementDefaults);
				this.$el.remove();
				console.log(App[this.model.parent].get('dataElementDefaults'));
			}
			
		});
;