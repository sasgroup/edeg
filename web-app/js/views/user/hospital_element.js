//Hospital Measure Breadcrumb
App.Views.HospitalMeasureBreadcrumb = Backbone.View.extend({	
	template :  _.template($('#user-measure-breadcrumb').html()),
	
	events : {				
		'click .show-help'                : 'showHelp'		
	},
	
	render : function() {
		this.$el.html(this.template({product_code:this.options.product_code, product_id:this.options.product_id, hospital_id:this.options.hospital_id, measure_code:this.model.code, notes:this.model.notes}));		
		return this;
	},
	
	showHelp : function(evt){		
		var _mid = $(evt.target).attr('mid');
		var _help = this.model.help;
		var _show = $('.show-help').hasClass('show');
		var _code = this.model.code;

		$('.show-help.show')
		.removeClass('show')
		.popover('hide');
		
		if (!_show){
			$('.show-help')
			.addClass('show')
			.popover({html:true,placement:'left',title:'Help for ['+_code+']',content:_help||"No help were supplied..."})			
			.popover('show');
			
			$('#breadcrumb-box .popover').css('top','0px');
			
			this.adjustPopover();
		}
		
		evt.preventDefault();
		evt.stopPropagation();		
	},
	
	adjustPopover:function(){
		$('.popover')
		.unbind('mousedown')
		.mousedown(function(e){
			e.preventDefault();
			e.stopPropagation();
		})
	}	
	
});	