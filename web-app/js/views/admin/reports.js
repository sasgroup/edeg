// New/Edit Product
App.Views.Reports = Backbone.View.extend({
	template : _.template($('#reports-template').html()),

	events : {
		'click #btnGenerateReports' : 'generateReports',
		'click #btnGenerateExcel' 	: 'generateExcel'
	},
	
	//reportData : "",
	
	render : function() {	
		//reports-hospitals-template
		var temp = _.template($('#reports-hospitals-template').html());
		var pme = _.template($('#reports-pme-template').html());
		this.$el.html(this.template(this.model.H.toJSON()));
		var _el = this.$el; 
		//var _json = this.model.toJSON(); 
		$(this.model.H.toJSON()).each(function(i, m){
			_el.find('#slcHospital').append(temp({name:m.name,id:m.id}));	
		});
		$(this.model.P.toJSON()).each(function(i, m){
			_el.find('#slcProduct').append(pme({name:m.name,id:m.id,code:m.code}));	
		});
		$(this.model.M.toJSON()).each(function(i, m){
			_el.find('#slcMeasure').append(pme({name:m.name,id:m.id,code:m.code}));
		});
		$(this.model.E.toJSON()).each(function(i, m){
			_el.find('#slcElement').append(pme({name:m.name,id:m.id,code:m.code}));	
		});
		
		return this;
	},


	generateReports : function(){
		var _el = this.$el; 
		var _colNames = [
			['Hospital', 'Product', 'Measure', 'Measure Name', 'Comp', 'Accp', 'NRev', 'Verf'],
			['Hospital', 'Product', 'Measure', 'Measure Name', 'Comp', 'Accp', 'NRev', 'Verf'],
			['Hospital', 'Product'],
			['Hospital', 'Product', 'Measure', 'Measure Name', 'Comp', 'Accp', 'NRev', 'Verf'],
			['Hospital', 'Code', 'Data Element', 'Location', 'EHR', 'Vtype']
		];
		var _colModels = [
			[	{name:'hname',		index:'hname', 		width:100},
			 	{name:'pcode',		index:'pcode', 		width:30}, 
			 	{name:'mcode',		index:'mcode', 		width:60}, 
			 	{name:'mname',		index:'mname'}, 
			 	{name:'completed',	index:'completed', 	width:30, formatter: "checkbox"},
			 	{name:'accepted',	index:'accepted', 	width:30, formatter: "checkbox"},
			 	{name:'confirmed',	index:'confirmed', 	width:30, formatter: "checkbox"},
			 	{name:'verified',	index:'verified', 	width:30, formatter: "checkbox"}	],
			 	
		 	[	{name:'hname',		index:'hname', 		width:100},
			 	{name:'pcode',		index:'pcode', 		width:30}, 
			 	{name:'mcode',		index:'mcode', 		width:60}, 
			 	{name:'mname',		index:'mname'}, 
			 	{name:'completed',	index:'completed', 	width:30, formatter: "checkbox"},
			 	{name:'accepted',	index:'accepted', 	width:30, formatter: "checkbox"},
			 	{name:'confirmed',	index:'confirmed', 	width:30, formatter: "checkbox"},
			 	{name:'verified',	index:'verified', 	width:30, formatter: "checkbox"}	],
			 	
		 	[	{name:'hname',		index:'hname', 		width:100},
			 	{name:'pcode',		index:'pcode', 		width:60}	],
			 	
		 	[	{name:'hname',		index:'hname', 		width:100},
			 	{name:'pcode',		index:'pcode', 		width:30}, 
			 	{name:'mcode',		index:'mcode', 		width:60}, 
			 	{name:'mname',		index:'mname'}, 
			 	{name:'completed',	index:'completed', 	width:30, formatter: "checkbox"},
			 	{name:'accepted',	index:'accepted', 	width:30, formatter: "checkbox"},
			 	{name:'confirmed',	index:'confirmed', 	width:30, formatter: "checkbox"},
			 	{name:'verified',	index:'verified', 	width:30, formatter: "checkbox"}	],
			
			[	{name:'hname',		index:'hname', 		width:100},
			 	{name:'ecode',		index:'ecode', 		width:60}, 
			 	{name:'ename',		index:'ename'}, 
			 	{name:'location',	index:'location', 	width:120},
			 	//{name:'sourceEHR',	index:'sourceEHR', 	width:30, formatter: "checkbox"},
			 	{name:'source',		index:'source', 	width:80},
			 	{name:'valueType',	index:'valueType', 	width:60} 		]
		];
		
		//this.reportData = "";
		var tRepData = "";
		
		$.ajax({
			url: "/ihm/api/report/"+$("#slcReportType").val(),
			data:{
				hospital:$("#slcHospital").val(),
				product	:$("#slcProduct").val(),
				measure	:$("#slcMeasure").val(),
				element	:$("#slcElement").val(),
				ts		:(new Date()).getTime()
			},
			error: function(req, err){
				
			},
			success: function(resp){
				tRepData = resp;
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
				
				tRepData ="<tr>";
				for(var i=0; i< _colNames[resp.rid - 1].length;i++)
					tRepData +="<th>"+_colNames[resp.rid - 1][i]+"</th>";
				tRepData +="</tr>";
				for(var j=0; j < resp.data.length;j++){
					tRepData +="<tr>";
					for(var i=0; i < _colModels[resp.rid - 1].length; i++)
						if ("checkbox" == (_colModels[resp.rid - 1][i]).formatter)
							tRepData +="<td>"+( resp.data[j][ ((_colModels[resp.rid - 1][i]).name) ] ? "Yes" : "No")+"</td>";
						else
							tRepData +="<td>"+resp.data[j][ ((_colModels[resp.rid - 1][i]).name) ]+"</td>";
					tRepData +="</tr>";
				}
				
				_el.find("#divRepData").text(tRepData);
				
				for(var i=0;i<=resp.data.length;i++) 
					_el.find("#tblReportResults").jqGrid('addRowData',i+1,resp.data[i]);
				_el.find("#tblReportResults").jqGrid('setGridWidth', 920, true);
			}
		});
	},
	
	
	generateExcel : function(){
		var _el = this.$el; 
		var _html = _el.find("#divRepData").text();
		var uri = 'data:application/vnd.ms-excel;base64,'
			 , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
			 , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
			 , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
			 
			 
		 var ctx = {worksheet: name || 'Worksheet', table: _html}
		 window.location.href = uri + base64(format(template, ctx))
			 
	},
	
	returnOnMain: function () {		
		Backbone.history.navigate("/reports", true);				
	}
	
});




