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
	
	//new App.Routers.Administrator();	
	new App.Routers.User();
	Backbone.history.start();	
//  window.location.href = "/ihm/user";
//	Backbone.history.navigate("user/", true);
	 
				
	$('.nav a').click(function(){
		$('li.active').removeClass("active");
		$(this).closest('li').addClass("active");	
	});
	
});
