modules = {
	ihm {
		dependsOn 'jquery, underscore, backbone, bootstrap'
		resource url: 'css/ihm.css'		
		resource url: 'js/ihm.js'
		resource url: 'js/models.js'
		resource url: 'js/collections.js'
		resource url: 'js/views.js'
		resource url: 'js/router.js'		
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
	
}


