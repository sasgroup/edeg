_.extend(_.templateSettings, {
    interpolate : /\{\{(.+?)\}\}/g,
    evaluate : /\{!(.+?)!\}/g
});

window.App = {
	Models : {},
	Collections : {},
	Views : {},
	Routers : {}
};


$(function() {
	//get userRole and initialize the route		
	App.userRole = $('#app').data('role');
	
	if (App.userRole == 'admin') {
		new App.Routers.Administrator();		
		Backbone.history.start();		
	}
	else if (App.userRole == 'user') {
		new App.Routers.User();	
		Backbone.history.start();		
	}
					
	$('.nav a').click(function(){
		$('li.active').removeClass("active");
		$(this).closest('li').addClass("active");	
	});	
	
	$('.alert .close').live("click", function(e) {
	    $(this).parent().hide();
	});
			
	$.ajaxSetup({
		complete: function (xhr) {
			if (xhr.responseText.indexOf('<title>Login</title>') > 0) {				
				window.location.reload();
		    }
		}
	});
	
});
