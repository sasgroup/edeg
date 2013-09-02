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
		var _el = this.$el; 
		var _colNames = [
			['Hospital', 'Product', 'Measure', 'Measure Name', 'Comp', 'Conf', 'Accp', 'Verf'],
			['Hospital', 'Product', 'Measure', 'Measure Name', 'Comp', 'Conf', 'Accp', 'Verf'],
			['Hospital', 'Product'],
			['Hospital', 'Product', 'Measure', 'Measure Name', 'Comp', 'Conf', 'Accp', 'Verf'],
			['Hospital', 'Code', 'Data Element', 'Location', 'EHR', 'Src', 'Vtype']
		];
		var _colModels = [
			[	{name:'hname',		index:'hname', 		width:100},
			 	{name:'pcode',		index:'pcode', 		width:30}, 
			 	{name:'mcode',		index:'mcode', 		width:60}, 
			 	{name:'mname',		index:'mname'}, 
			 	{name:'completed',	index:'completed', 	width:30, formatter: "checkbox"},
			 	{name:'confirmed',	index:'confirmed', 	width:30, formatter: "checkbox"},
			 	{name:'accepted',	index:'accepted', 	width:30, formatter: "checkbox"},
			 	{name:'verified',	index:'verified', 	width:30, formatter: "checkbox"}	],
			 	
		 	[	{name:'hname',		index:'hname', 		width:100},
			 	{name:'pcode',		index:'pcode', 		width:30}, 
			 	{name:'mcode',		index:'mcode', 		width:60}, 
			 	{name:'mname',		index:'mname'}, 
			 	{name:'completed',	index:'completed', 	width:30, formatter: "checkbox"},
			 	{name:'confirmed',	index:'confirmed', 	width:30, formatter: "checkbox"},
			 	{name:'accepted',	index:'accepted', 	width:30, formatter: "checkbox"},
			 	{name:'verified',	index:'verified', 	width:30, formatter: "checkbox"}	],
			 	
		 	[	{name:'hname',		index:'hname', 		width:100},
			 	{name:'pcode',		index:'pcode', 		width:60}	],
			 	
		 	[	{name:'hname',		index:'hname', 		width:100},
			 	{name:'pcode',		index:'pcode', 		width:30}, 
			 	{name:'mcode',		index:'mcode', 		width:60}, 
			 	{name:'mname',		index:'mname'}, 
			 	{name:'completed',	index:'completed', 	width:30, formatter: "checkbox"},
			 	{name:'confirmed',	index:'confirmed', 	width:30, formatter: "checkbox"},
			 	{name:'accepted',	index:'accepted', 	width:30, formatter: "checkbox"},
			 	{name:'verified',	index:'verified', 	width:30, formatter: "checkbox"}	],
			
			[	{name:'hname',		index:'hname', 		width:100},
			 	{name:'ecode',		index:'ecode', 		width:60}, 
			 	{name:'ename',		index:'ename'}, 
			 	{name:'location',	index:'location', 	width:120},
			 	{name:'sourceEHR',	index:'sourceEHR', 	width:30, formatter: "checkbox"},
			 	{name:'source',		index:'source', 	width:80},
			 	{name:'valueType',	index:'valueType', 	width:60} 		]
		];
		
		
		$.ajax({
			url: "/ihm/api/report/"+$("#slcReportType").val(),
			data:{
				hospital:$("#slcHospital").val(),
				ts: new Date()
			},
			error: function(req, err){
				
			},
			success: function(resp){
				_el.find("#tblReportResults").jqGrid('GridUnload');
				_el.find("#tblReportResults").jqGrid({ 
					datatype: "local",
					height: 450, 
					width: 920,
					colNames:_colNames[resp.rid - 1], 
					colModel:_colModels[resp.rid - 1], 
					multiselect: false, 
					caption: resp.report,
					loadonce: true,
					viewrecords: true,
					loadComplete : function () {
						return;
					}


				});
				
				//_el.find("#tblReportResults").jqGrid('navGrid','#pparams',{edit:false,add:false,del:false});
				
				for(var i=0;i<=resp.data.length;i++) 
					_el.find("#tblReportResults").jqGrid('addRowData',i+1,resp.data[i]);
				
				_el.find("#tblReportResults").jqGrid('setGridWidth', 920, true);
				
				//console.log(resp);
			}
		});
	},
	
	returnOnMain: function () {		
		Backbone.history.navigate("/reports", true);				
	}
	
});




