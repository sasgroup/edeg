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