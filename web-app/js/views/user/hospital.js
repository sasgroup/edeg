//Hospital
App.Views.UserHospitalProduct = Backbone.View.extend({		
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
																"m_index"  :m_index
																});	
					
			var view = new App.Views.SingleUserHospitalMeasure({ model : hospitalMeasure });					
			cur_hospital_product.$el.find('.hospitalMeasureTable tbody').append(view.render().el);
		});		
	}
});	

//Single User Hospital_Measure
App.Views.SingleUserHospitalMeasure = Backbone.View
		.extend({
			tagName : 'tr',
			template: _.template($('#user-single-hospital_measure').html()),			
			events : {				
				//'click a#customLink'       	  : 'goToDataElements',
				//'change input[name="included"], input[name="completed"], input[name="confirmed"], input[name="accepted"], input[name="verified"]'  : 'changeVal'				
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
				
				return this;
			}
		});