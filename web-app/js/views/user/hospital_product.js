//Hospital Product
App.Views.HospitalProduct = Backbone.View.extend({	
	template :  _.template($('#user-hospital-measure_table').html()),

	render : function() {					
		this.$el.html(this.template());		
		var measures = this.model.measures;			
		this.renderHospitalMeasureTable(measures,this);	
		
		$('body')
		.unbind('mousedown')
		.mousedown(function(){
			$('.show')
			.removeClass('show')
			.popover('destroy');	
		});
		
		return this;
	},
	
	renderHospitalMeasureTable: function(measures, cur_hospital_product){		
		var sorted_measures = [];
		
		$.each( measures, function( m_index, measure ){			
			var sortcode = -10;
			switch(measure.measureCategory)
			{
			case 'CORE':
				sortcode = 0;
			  break;
			case 'MENU':
				sortcode = 1;
			  break;
			case 'CQM':
				sortcode = 2;
			  break;
			}
						
			sorted_measures.push({
				"id"		    : measure.id,
				"code"			: measure.code,
				"name"			: measure.name,
				"accepted" 		: measure.accepted,
				"completed"		: measure.completed,
				"confirmed"		: measure.confirmed,
				"included" 		: measure.included,
				"verified" 		: measure.verified,																
				"h_id"     		: cur_hospital_product.options.h_id,
				"product_code"	: cur_hospital_product.model.code,
				"product_id"	: cur_hospital_product.model.id,
				"notes"       	: measure.notes,
				"help"			: measure.help,
				"measureCategory":measure.measureCategory,
				"sortcode"       :sortcode});			
		});	
		
		sorted_measures.sort(function(a, b){
			
			if (a.included == b.included){
				if (a.sortcode == b.sortcode){
					return a.code.toLowerCase() < b.code.toLowerCase() ? -1:1;
				}
				else
					return a.sortcode - b.sortcode;
			}
			else
				return a.included ? -1 : 1;
						
			//CORE-0 MENU-1 CQM-2			
		    if(a.sortcode < b.sortcode) return -1;
		    if(a.sortcode > b.sortcode) return 1;
		    return 0;
		});
				
		$.each( sorted_measures, function( m_index, measure ) {			
			var hospitalMeasure	 =  new App.Models.HospitalMeasure({"id"		: measure.id,
																"code"			: measure.code,
																"name"			: measure.name,
																"accepted" 		: measure.accepted,
																"completed"		: measure.completed,
																"confirmed"		: measure.confirmed,
																"included" 		: measure.included,
																"verified" 		: measure.verified,																
																"h_id"     		: cur_hospital_product.options.h_id,
																"product_code"	: cur_hospital_product.model.code,
																"product_id"	: cur_hospital_product.model.id,
																"notes"       	: measure.notes,
																"help"			: measure.help,
																"measureCategory":measure.measureCategory
																});	
					
			var view = new App.Views.HospitalMeasure({ model : hospitalMeasure });					
			cur_hospital_product.$el.find('.hospitalMeasureTable tbody').append(view.render().el);
		});		
	}
});	

//Hospital Product Breadcrumb
App.Views.HospitalProductBreadcrumb = Backbone.View.extend({	
	template :  _.template($('#user-hospital-breadcrumb').html()),
	
	events : {				
		'click .show-help'                : 'showHelp',
		'click .show-notes'               : 'showNotes',
		'click .edit-notes'               : 'editNotes'
	},
	
	render : function() {					
		this.$el.html(this.template({product_code:this.model.code, notes:this.model.notes, h_id:this.options.h_id, notifyUser:this.options.notifyUser}));		
		return this;
	},
	
	showHelp : function(evt){		
		var _help = this.model.help;
		var _code = this.model.code;
		var _show = $('.show-help').hasClass('show');

		$('.show').removeClass('show').popover('destroy');
		
		if (!_show){
			$('.show-help').addClass('show')
			.popover({html:true,placement:'left',title:'Help for [' + _code + ']',content:_help||"No help were supplied..."}).popover('show');
			$('#breadcrumb-box .popover').css('top','0px');
			this.adjustPopover();
		}
		evt.stopPropagation();		
	},
	
	showNotes : function(evt){		
		var _help = this.model.notes;
		var _code = this.model.code;
		var _show = $('.show-notes').hasClass('show');

		$('.show').removeClass('show').popover('destroy');
		
		if (!_show){
			$('.show-notes').addClass('show')
			.popover({html:true,placement:'left',title:'Notes for [' + _code + ']',content:_help||"No notes were supplied..."}).popover('show');
			$('#breadcrumb-box .popover').css('top','0px');
			this.adjustPopover();
		}
		evt.stopPropagation();		
	},
	
	editNotes : function(evt){			
		$('.edit-notes').removeClass('btn-info');	
		
		var thisHospProduct = this;
		var h_id = this.options.h_id;
		var p_id = this.model.id;
			
		App.cur_product = new App.Models.HospitalProduct();
		
		App.cur_product.fetch({data:{p_id:p_id, h_id:h_id}}).then(function(){					
			// mark as read
			App.cur_product.set({"notifyUser":false});   
			App.cur_product.save();
				
			var qa_view = new App.Views.QA({ model : App.cur_product});  
			var _my_content =  qa_view.render().el;  
										
			var _code = thisHospProduct.model.code;
			var _show = $('.edit-notes').hasClass('show');
	
			$('.show').removeClass('show').popover('destroy');
			
			if (!_show){
				$('.edit-notes').addClass('show')
				.popover({html:true,placement:'left',title:'Notes for [' + _code + ']',content:_my_content||"No notes were supplied..."}).popover('show');
				$('#breadcrumb-box .popover').css('top','0px');
				thisHospProduct.adjustPopover();
			}	
					
		});
		
		evt.stopPropagation();
	},
	
	
	adjustPopover:function(){
		$('.popover')
		.unbind('mousedown')
		.mousedown(function(e){
			e.stopPropagation();
		})
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
											 notes:this.model.get('notes'),
											 included:ch_included,
											 completed:ch_completed,
											 confirmed:ch_confirmed,
											 accepted:ch_accepted,
											 verified:ch_verified,
											 measureCategory:this.model.get('measureCategory') 
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
							/*console.log(p_index + ": " + product.id);
							console.log(m_index + ": " + measure.id);
							console.log(JSON.stringify(measure));
							console.log("sl_val: " + sl_val);			*/				
							measure.completed = sl_val;
						}
							
					});	
				
				});					
				
				App.ho.set({products : App.ho.get('products')});		        
			    
				App.ho.save(null,{
			        success: function (model, response) {
			           if (window.console) console.log(response);			          
			           $('div#message-box').text("").append(response.message).removeClass().addClass('alert').addClass('alert-success').fadeIn(10).delay(2000).fadeOut(50);			           
			        },
			        error: function (model, response) {			        	
			        	var btn = '<button type="button" class="close">&times;</button>';
				        $('div#message-box').text("").append(btn).append(response.responseText).removeClass().addClass('alert').addClass('alert-error').show();			            	
			        }
			    });
			},
			
			showInfo: function(evt) {							
				if (!($(evt.target).closest('a').hasClass('disabled'))) {
					
					var _mid = $(evt.target).attr('mid');
					var _help = this.model.get('help');
					var _code = this.model.get('code');			
				
					var _show = $('.show_info[mid='+_mid+']').hasClass('show');
	
					$('.show').removeClass('show').popover('destroy');
					
					if (!_show){
						$('.show_info[mid='+_mid+']').addClass('show')
						.popover({html:true,placement:'left',title:'Instructions for ['+_code+']',content:_help||"No Instructions were supplied..."}).popover('show');
						this.adjustPopover();
					}
					evt.stopPropagation();
				}
			},
			
			adjustPopover:function(){
				$('.popover')
				.unbind('mousedown')
				.mousedown(function(e){
					e.stopPropagation();
				})
			},
			
			goToDataElements : function(e) {
				e.preventDefault();				
				Backbone.history.navigate("/hospital/" +  this.model.get('h_id')+ "/product/" + this.model.get('product_id')+ "/measure/" + this.model.get('id'), true);		
			}	
		});