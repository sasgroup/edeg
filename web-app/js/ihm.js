_.extend(_.templateSettings, {
    interpolate : /\{\{(.+?)\}\}/g,
    evaluate : /\{!(.+?)!\}/g
});

window.App = {
	Models : {},
	Collections : {},
	Views : {},
	Router : {}
};


$(function() {	
	new App.Router();
	Backbone.history.start();	
	//Backbone.history.start({pushState: true});
			
	$('.nav a').click(function(){
		$('li.active').removeClass("active");
		$(this).closest('li').addClass("active");	
	});
	
});
