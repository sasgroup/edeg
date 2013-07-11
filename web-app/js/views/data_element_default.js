App.Views.DataElementsDefault = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#single-data-elements-default').html()),			
			
			events : {
				'click #plus-btn' : 'addRow',
				'click #minus-btn': 'removeRow'
			},
						
			render : function() {								
				this.$el.html(this.template({loc:this.model.location, code_type: this.model.codeType.name, value_type: this.model.valueType.name, ehr: this.model.linkId}));				
				return this;
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
				var emptyDataElementDefault = {"location":"","linkId":"","valueType":{"enumType":"","name":""},"codeType":{"enumType":"","name":""}}; 
				var view = new App.Views.DataElementsDefault({ model : emptyDataElementDefault, default_element: this.options.default_element});
				var ehrtbody = this.$el.closest('tbody');
				$(ehrtbody).append(view.render().el);					
				$(view.render().el).find('.slcEHR').append(this.defaultElementOptions());	
			},
			
			removeRow : function (){				
				this.$el.remove();					
			}
			
		});
