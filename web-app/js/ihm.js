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
	
	new App.Routers.Administrator();
	Backbone.history.start();	
	//Backbone.history.start({pushState: true});
			
	$('.nav a').click(function(){
		$('li.active').removeClass("active");
		$(this).closest('li').addClass("active");	
	});
	
});
