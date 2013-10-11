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
		
		_el.find('#slcReportType').change(function(){
			var _val = _el.find('#slcReportType').val();
			_el.find('label[hideFor], select[hideFor], div[hideFor]').each(function(i, e){
				if ($(e).attr('hideFor').indexOf(_val) != -1)
					$(e).hide();
				else
					$(e).show();
			})
		});
		
		var _model = this.model;
		_el.find('#slcEntityType').change(function(){
			var _val = _el.find('#slcEntityType').val();
			switch (_val){
				case "P": 
					_el.find('#slcHospital, label[for=slcHospital]').hide();
					_el.find('#slcEntity').html("<option value='0'> -Select - </option>");
					$(_model.P.toJSON()).each(function(i, m){ _el.find('#slcEntity').append(pme({name:m.name,id:m.id,code:m.code})); }); break;
				
				case "M": 
					_el.find('#slcHospital, label[for=slcHospital]').hide();
					_el.find('#slcEntity').html("<option value='0'> -Select - </option>");
					$(_model.M.toJSON()).each(function(i, m){ _el.find('#slcEntity').append(pme({name:m.name,id:m.id,code:m.code})); }); break;
				
				case "E": 
					_el.find('#slcHospital, label[for=slcHospital]').hide();
					_el.find('#slcEntity').html("<option value='0'> -Select - </option>"); 
					$(_model.E.toJSON()).each(function(i, m){ _el.find('#slcEntity').append(pme({name:m.name,id:m.id,code:m.code})); }); break;
				
				case "H": 
					_el.find('#slcHospital, label[for=slcHospital]').show(); 
					_el.find('#slcEntity').html("<option value='0'> -Select - </option>"); break;
					
				case "HP": 
					_el.find('#slcHospital, label[for=slcHospital]').show();
					_el.find('#slcEntity').html("<option value='0'> -Select - </option>");
					$(_model.P.toJSON()).each(function(i, m){ _el.find('#slcEntity').append(pme({name:m.name,id:m.id,code:m.code})); }); break;
				
				case "HM": 
					_el.find('#slcHospital, label[for=slcHospital]').show();
					_el.find('#slcEntity').html("<option value='0'> -Select - </option>");
					$(_model.M.toJSON()).each(function(i, m){ _el.find('#slcEntity').append(pme({name:m.name,id:m.id,code:m.code})); }); break;
				
				case "HE": 
					_el.find('#slcHospital, label[for=slcHospital]').show();
					_el.find('#slcEntity').html("<option value='0'> -Select - </option>");					
					$(_model.E.toJSON()).each(function(i, m){ _el.find('#slcEntity').append(pme({name:m.name,id:m.id,code:m.code})); }); break;
				
				case "0": 
					_el.find('#slcHospital, label[for=slcHospital]').hide();
					_el.find('#slcEntity').html("<option value='0'> -Select - </option>"); break;
			}
		});
		
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
		
		
		_el.find('label[hideFor="1,2,3,4,5,6"], select[hideFor="1,2,3,4,5,6"], div[hideFor="1,2,3,4,5,6"]').hide();
		_el.find('#dpFrom, #dpTill').datepicker({format:'mm/dd/yy'});
		return this;
	},


	generateReports : function(){
		var _el = this.$el; 
		var _colNames = [
			['Hospital', 'Product', 'Measure', 'Measure Name', 'Comp', 'Accp', 'NRev', 'Verf'],
			['Hospital', 'Product', 'Measure', 'Measure Name', 'Comp', 'Accp', 'NRev', 'Verf'],
			['Hospital', 'Product'],
			['Hospital', 'Product', 'Measure', 'Measure Name', 'Comp', 'Accp', 'NRev', 'Verf'],
			['Hospital', 'Code', 'Data Element', 'Location', 'EHR', 'Vtype'],
			['Hospital', 'Product', 'Measure', 'Code', 'Data Element', 'Location', 'EHR', 'Vtype'],
			['Date', 'User', 'Event', 'Entity Type', 'Property', 'Old Value', 'New Value', 'OID']
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
			 	{name:'source',		index:'source', 	width:80},
			 	{name:'valueType',	index:'valueType', 	width:60} 		],
		 	
			 [	{name:'hname',		index:'hname', 		width:100},
		 	 	{name:'pcode',		index:'pcode', 		width:50},
		 	 	{name:'mcode',		index:'mcode', 		width:50},
			 	{name:'ecode',		index:'ecode', 		width:60}, 
			 	{name:'ename',		index:'ename'				}, 
			 	{name:'location',	index:'location', 	width:120},
			 	{name:'source',		index:'source', 	width:80},
			 	{name:'valueType',	index:'valueType', 	width:60} 		],
			 	
			[	{name:'updated',	index:'updated', 	width:60},
		 	 	{name:'actor',		index:'actor', 		width:50},
		 	 	{name:'event',		index:'event', 		width:30},
			 	{name:'entity',		index:'entity', 	width:65}, 
			 	{name:'property',	index:'property',	width:70}, 
			 	{name:'ovalue',		index:'ovalue'				},
			 	{name:'nvalue',		index:'nvalue'				},
			 	{name:'poid',		index:'poid', 		width:30} 		]
		];
		
		$.ajax({
			url: "/ihm/api/report/"+$("#slcReportType").val(),
			data:{
				hospital:$("#slcHospital").val(),
				product	:$("#slcProduct").val(),
				measure	:$("#slcMeasure").val(),
				element	:$("#slcElement").val(),
				etype	:$("#slcEntityType").val(),
				entity	:$("#slcEntity").val(),
				dpFrom	:$("#dpFrom").val(),
				dpTill	:$("#dpTill").val(),
				ts		:(new Date()).getTime()
			},
			error: function(req, err){
				
			},
			success: function(resp){
				
				_el.find("#tblReportResults").jqGrid('GridUnload');
				_el.find("#tblReportResults").jqGrid({ 
					datatype: "local",
					data: resp.data,
					height: 450, 
					width: 920,
					colNames:_colNames[resp.rid - 1], 
					colModel:_colModels[resp.rid - 1], 
					multiselect: false, 
					caption: resp.report,
					gridview: true,
					ignoreCase: true,
					rownumbers: true,
					loadonce: true,
					rowNum : 100000,
					viewrecords: true,
					loadComplete : function () {
						return;
					}
				});
				
				if (resp.rid == 7){
					_el.find("#tblReportResults").jqGrid('groupingGroupBy', 'poid',{
						groupOrder : ['asc'],
                        groupColumnShow: [false],
                        groupText : ['<b>OID: {0}</b>'],
                        groupCollapse: false
					});
				}
				
				$("#tblReportResults").jqGrid('setGridWidth', 940, true);
					
			}
		});
	},
	
	
	generateExcel : function(){
		window.location.href = "/ihm/api/excel/"+$("#slcReportType").val() +
								"?hospital="+$("#slcHospital").val() +
								"&product="+$("#slcProduct").val()+
								"&measure="+$("#slcMeasure").val()+
								"&element="+$("#slcElement").val()+
								"&etype="+$("#slcEntityType").val()+
								"&entity="+$("#slcEntity").val()+
								"&dpFrom="+$("#dpFrom").val()+
								"&dpTill="+$("#dpTill").val()+
								"&ts="+(new Date()).getTime()
	},
	
	returnOnMain: function () {		
		Backbone.history.navigate("/reports", true);				
	}
	
});
