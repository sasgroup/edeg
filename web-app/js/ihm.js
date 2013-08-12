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
	
	var curUser = $('#role').val();
	
	if (curUser == 'admin') {
		new App.Routers.Administrator();	
	}
	else {
		new App.Routers.User();	
		
		$('#hospital-list-dropdown li').live('click', function(){	 
			var hospital_name = $(this).find('a').text(); 
			var hospital_id = $(this).data('id');
			
			//show hospital-name
			$('h3.hospital-name').text(hospital_name);
	    	    
			$('#app').empty();
			$('#breadcrumb-box').empty();
	    
			//generate tabs
			App.ho = new App.Models.Hospital();		
			App.ho.fetch({data:{id: hospital_id}}).then(function(){		
				var products = App.ho.get('products');
				$('nav#products-nav').empty();
				$.each( products, function( i, product ) {		           
					$('nav#products-nav').append('<a href="#hospital/' + hospital_id + '/product/'+ product.id+ '">' + product.code + '</a>');
				});			
			})	    
		});
	}
		
	Backbone.history.start();	
		
	$('.nav a').click(function(){
		$('li.active').removeClass("active");
		$(this).closest('li').addClass("active");	
	});	
	
});
