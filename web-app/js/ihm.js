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
		
	var curUser = 'user';
	
	if (curUser == 'admin') {
		new App.Routers.Administrator();	
	}
	else {
		new App.Routers.User();		
	}
		
	Backbone.history.start();	
			
	$('#hospital-list-dropdown li').live('click', function(){	 
		var hospital_name = $(this).find('a').text(); 
		var hospital_id = $(this).data('id');
		//show hospital-name
	    $('h3.hospital-name').text(hospital_name);
	    console.log(hospital_name,hospital_id);
	    
	    //generate tabs
	    App.ho = new App.Models.Hospital();		
		App.ho.fetch({data:{id: hospital_id}}).then(function(){		
			var products = App.ho.get('products');
			$('nav#products-nav').empty();
			$.each( products, function( i, product ) { 	
				console.log(product.code);			
				$('nav#products-nav').append('<a href="#'+ product.code+ '">' + product.code + '</a>');		           
			});			
		})
	    
	});
	
	$('.nav a').click(function(){
		$('li.active').removeClass("active");
		$(this).closest('li').addClass("active");	
	});

	
	
});
