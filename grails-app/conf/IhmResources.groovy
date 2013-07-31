modules = {
	ihm {
		dependsOn 'jquery, underscore, backbone, bootstrap, jqgrid, jqmultiselect, datatable, html5shiv'
		resource url: 'css/ihm.css'		
		resource url: 'js/ihm.js'
		resource url: 'js/models.js'
		resource url: 'js/collections.js'
		
			
		resource url: 'js/views/admin/data_element_default.js'
		resource url: 'js/views/admin/ehr.js'
		resource url: 'js/views/admin/element.js'
		resource url: 'js/views/admin/hospital.js'
		resource url: 'js/views/admin/measure.js'
		resource url: 'js/views/admin/product.js'
		
				
		resource url: 'js/routers/admin.js'	
		resource url: 'js/routers/user.js'
	}
	
	underscore {
		resource url: 'js/underscore.js'
	}
	
	backbone {
		resource url: 'js/backbone.js'
	}
	
		
	bootstrap{
		resource url: 'css/bootstrap.css'
		resource url: 'js/bootstrap.js'
	}
	
		
	jqgrid {
		resource url: 'js/jqgrid/grid.locale-en.js'
		resource url: 'js/jqgrid/jquery-ui-custom.min.js'
		resource url: 'js/jqgrid/jquery.contextmenu.js'
		resource url: 'js/jqgrid/jquery.jqGrid.js'
		resource url: 'js/jqgrid/jquery.layout.js'
		resource url: 'js/jqgrid/jquery.tablednd.js'
		resource url: 'js/jqgrid/ui.multiselect.js'
		
		
		resource url: 'css/jqgrid/jquery-ui-custom.css'
		resource url: 'css/jqgrid/ui.jqgrid.css'
		resource url: 'css/jqgrid/ui.multiselect.css'
	}
	
	
	jqmultiselect {
		resource url: 'js/multiselect/jquery.multiselect.js'
		resource url: 'css/multiselect/jquery.multiselect.css'
		resource url: 'css/multiselect/multiselect.style.css'
		resource url: 'css/multiselect/prettify.css'		
	}
	
	datatable {
		resource url: 'js/datatable/jquery.dataTables.min.js'
		resource url: 'js/datatable/fixed_columns.js'
		resource url: 'js/datatable/fixed_header.min.js'
		resource url: 'css/datatable/jquery.dataTables.css'
	}
	
	html5shiv {
		resource url: 'js/html5shiv.js'
	}
	
}


