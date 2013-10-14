//Hospital Measure Breadcrumb
App.Views.HospitalMeasureBreadcrumb = Backbone.View.extend({	
	template :  _.template($('#user-measure-breadcrumb').html()),
	
	events : {				
		'click .show-help'                : 'showHelp',
		'click .show-notes'               : 'showNotes',
		'click .edit-notes'               : 'editNotes'
	},
	
	render : function() {
		this.$el.html(this.template({product_code:this.options.product_code, product_id:this.options.product_id, hospital_id:this.options.hospital_id, measure_code:this.model.code, notifyUser:this.options.notifyUser}));
		
		$('body')
		.unbind('mousedown')
		.mousedown(function(){
			$('.show')
			.removeClass('show')
			.popover('destroy');	
		});
		
		return this;
	},
	
	showHelp : function(evt){		
		var _help = this.model.help;
		var _show = $('.show-help').hasClass('show');
		var _code = this.model.code;

		$('.show').removeClass('show').popover('destroy');
		
		if (!_show){
			$('.show-help').addClass('show')
			.popover({html:true,placement:'left',title:'Help for ['+_code+']',content:_help||"No help were supplied..."}).popover('show');
			$('#breadcrumb-box .popover').css('top','0px');
			this.adjustPopover();
		}
		evt.stopPropagation();		
	},
	
	showNotes : function(evt){		
		var _help = this.model.notes;
		var _show = $('.show-notes').hasClass('show');
		var _code = this.model.code;

		$('.show-notes.show')
		.removeClass('show')
		.popover('destroy');
		
		if (!_show){
			$('.show-notes')
			.addClass('show')
			.popover({html:true,placement:'left',title:'Notes for ['+_code+']',content:_help||"No notes were supplied..."})			
			.popover('show');
			
			$('#breadcrumb-box .popover').css('top','0px');
			
			this.adjustPopover();
		}
		evt.stopPropagation();		
	},
	
	editNotes : function(evt){		
		$('.edit-notes').removeClass('btn-info');
		
		var thisHospMeasure= this;
		var m_id = this.model.id;
				
		App.cur_measure = new App.Models.HospitalMeasure();
		
		App.cur_measure.fetch({data:{id: m_id,hm: true}}).then(function(){		
		
		//var qa_view = new App.Views.QA({ model : App.cur_measure});  
		//var _my_content =  qa_view.render().el; 
			
		// mark as read
		App.cur_measure.set({"notifyUser":false});   
		App.cur_measure.save();
				
		var qa_view = new App.Views.QA({ model : App.cur_measure});  
		var _my_content =  qa_view.render().el;  	
				
		var _code = thisHospMeasure.model.code;
		var _show = $('.edit-notes').hasClass('show');

		$('.show').removeClass('show').popover('destroy');
		
		if (!_show){
			$('.edit-notes').addClass('show')
			.popover({html:true,placement:'left',title:'Notes for [' + _code + ']',content:_my_content||"No notes were supplied..."}).popover('show');
			$('#breadcrumb-box .popover').css('top','0px');
			thisHospMeasure.adjustPopover();
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