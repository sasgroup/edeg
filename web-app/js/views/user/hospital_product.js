//Hospital Product
App.Views.HospitalProduct = Backbone.View.extend({	
	template :  _.template($('#user-hospital-measure_table').html()),

	render : function() {					
		this.$el.html(this.template());		
		var measures = this.model.measures;			
		this.renderHospitalMeasureTable(measures,this);				
		return this;
	},
	
	renderHospitalMeasureTable: function(measures, cur_hospital_product){		
		$.each( measures, function( m_index, measure ) {			
			var hospitalMeasure	 =  new App.Models.HospitalMeasure({"id":measure.id,
																"code":measure.code,
																"name":measure.name,
																"accepted" :measure.accepted,
																"completed":measure.completed,
																"confirmed":measure.confirmed,
																"included" :measure.included,
																"verified" :measure.verified,																
																"m_index"  :m_index,
																"product_code": cur_hospital_product.model.code,
																"product_id": cur_hospital_product.model.id,
																"notes"       : measure.notes
																});	
					
			var view = new App.Views.HospitalMeasure({ model : hospitalMeasure });					
			cur_hospital_product.$el.find('.hospitalMeasureTable tbody').append(view.render().el);
		});		
	}
});	

//Hospital_Measure
App.Views.HospitalMeasure = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#user-hospital_measure').html()),			
			events : {				
				'click a#customLink'       	      : 'goToDataElements',
				'change input[name="completed"]'  : 'changeVal',
				'click .show_info'                : 'showInfo'	
			},

			render : function() {						
				var ch_included  = (this.model.get('included'))  ? "checked" : "";
				var ch_completed = (this.model.get('completed')) ? "checked" : "";
				var ch_confirmed = (this.model.get('confirmed')) ? "checked" : "";				
				var ch_accepted  = (this.model.get('accepted'))  ? "checked" : "";
				var ch_verified  = (this.model.get('verified'))  ? "checked" : "";
																
				this.$el.html(this.template({id:this.model.get('id'),
											 code:this.model.get('code'),
											 name:this.model.get('name'),
											 included:ch_included,
											 completed:ch_completed,
											 confirmed:ch_confirmed,
											 accepted:ch_accepted,
											 verified:ch_verified
											}));
				this.$el.html(this.template(this.model.toJSON()));
				
				//console.log(this.model.get('product_code'));
				
				return this;
			},
			
			changeVal: function (){
				this.model.save();
			},
			
			showInfo: function() {
				//if (this.model.get('notes')!=null) {
				//alert(this.model.get('notes'));
					//$('.alert-info').html(this.model.get('notes'));
					//$('.alert-info').fadeIn(500).delay(1500).fadeOut(500);
				    console.log(this.model.get('notes'));
					$('.show_info').attr('title',this.model.get('notes'));
				//}
			},
			
			goToDataElements : function(e) {
				e.preventDefault();				
				//Backbone.history.navigate(this.model.get('product_code')+ "/" + this.model.get('code') + "/" + "elements", true);
				Backbone.history.navigate("product/" + this.model.get('product_id')+ "/measure/" + this.model.get('id'), true);
				//Backbone.history.navigate(this.model.get('product_code')+ "/" + this.model.get('id') + "/" + "elements", true);
			}	
		});