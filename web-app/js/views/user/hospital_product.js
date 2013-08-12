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
																"h_id"     :cur_hospital_product.options.h_id,
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
				
				this.$el.attr("id", "m"+this.model.get('id'));				
				return this;
			},
			
			changeVal: function (e){			
				// measure value
				var sl_val = $(e.target).is(':checked');				
				// measure_id
				var m_id = $(e.target).closest('tr').attr('id').replace("m",'');
				
				// for all products
				$.each( App.ho.get('products') , function(p_index, product ) {					
					$.each( product.measures, function(m_index, measure ) {
						if (measure.id == m_id ){
							console.log(p_index + ": " + product.id);
							console.log(m_index + ": " + measure.id);
							console.log(JSON.stringify(measure));
							console.log("sl_val: " + sl_val);							
							measure.completed = sl_val;
						}
							
					});	
				
				});					
				
				App.ho.set({products : App.ho.get('products')});		        
			    
				App.ho.save(null,{
			        success: function (model, response) {
			           if (window.console) console.log(response);
			           $('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
		               //Backbone.history.navigate("hospital", true);	           
			        },
			        error: function (model, response) {
			        	$('div#message-box').text("").append(response.message).fadeIn(500).delay(1500).fadeOut(500);
			            //Backbone.history.navigate("hospital", true);	        	
			        }
			    });
			},
			
			showInfo: function() {
				//if (this.model.get('notes')!=null) {
				//alert(this.model.get('notes'));
					//$('.alert-info').html(this.model.get('notes'));
					//$('.alert-info').fadeIn(500).delay(1500).fadeOut(500);
				if (window.console) console.log(this.model.get('notes'));
					$('.show_info').attr('title',this.model.get('notes'));
				//}
			},
			
			goToDataElements : function(e) {
				e.preventDefault();				
				//Backbone.history.navigate(this.model.get('product_code')+ "/" + this.model.get('code') + "/" + "elements", true);
				//Backbone.history.navigate("product/" + this.model.get('product_id')+ "/measure/" + this.model.get('id'), true);
				Backbone.history.navigate("/hospital/" +  this.model.get('h_id')+ "/product/" + this.model.get('product_id')+ "/measure/" + this.model.get('id'), true);		
			}	
		});