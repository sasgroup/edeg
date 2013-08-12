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
																"p_index"  :cur_hospital_product.options.p_index,
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
				
				this.$el.attr("data-product", this.model.get('p_index'));
				this.$el.attr("data-measure", this.model.get('m_index'));
				this.$el.attr("id", "m"+this.model.get('id'));
				
				return this;
			},
			
			changeVal: function (e){				
				var ch_slc = 'input[name="' + e.target.name + '"]';
				var tr_slc = 'tr#' + $(e.target).closest('tr').attr('id');
				var sl = tr_slc + ' ' + ch_slc;
				var sl_val = $(e.target).is(':checked');					
				$(sl).attr('checked', sl_val);
				console.log(sl);
						
				$(tr_slc).each(function( index ) {
					//console.log( index + ": " + $(this).text() );
					$(this).attr('checked', sl_val);
					var p_index = $(this).data("product");
					var m_index = $(this).data("measure");
					
					console.log ("p_index "+p_index, " m_index "+m_index );
					//App.ho.get('products')
					App.hospital_products[p_index].measures[m_index].completed = sl_val;										
				});
				
				App.ho.set("products" , App.hospital_products);
		        
				App.ho.save();
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

			}	
		});