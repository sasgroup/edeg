// New/Edit Product
App.Views.Reports = Backbone.View.extend({
	template : _.template($('#reports-template').html()),

	events : {
		'click #btnGenerateReports' : 'generateReports'       
	},
	
	render : function() {	
		//reports-hospitals-template
		var temp = _.template($('#reports-hospitals-template').html());
		this.$el.html(this.template(this.model.toJSON()));
		var _el = this.$el; 
		$(this.model.toJSON()).each(function(i, m){
			_el.find('#slcHospital').append(temp({name:m.name,id:m.id}));	
		})
		return this;
	},


	generateReports : function(){
		$.ajax({
			url: "/ihm/api/reports/"+$("#slcReportType").val(),
			data:{},
			error: function(req, err){
				
			},
			success: function(resp){
				console.log(resp);
			}
		});
	},
	
	returnOnMain: function () {		
		Backbone.history.navigate("/reports", true);				
	}
	
});




