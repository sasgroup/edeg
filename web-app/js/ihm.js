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
	//$('#app').data('role')
	//$('#app').data('login')
	   
	App.userRole = $('#app').data('role');
	
	if (App.userRole == 'admin') {
		new App.Routers.Administrator();		
		Backbone.history.start();
		Backbone.history.navigate("/product", true);		
	}
	else if (App.userRole == 'user') {
		new App.Routers.User();	
		Backbone.history.start();
				
		App.security = new App.Models.Security();		
		App.security.fetch().then(function(){		
			var hospital_id = App.security.get('curHospitalId');
			var first_product = "";
			//generate tabs
			App.ho = new App.Models.Hospital();		
			App.ho.fetch({data:{id: hospital_id}}).then(function(){		
				var products = App.ho.get('products');				
				$('nav#products-nav').empty();
				$.each( products, function( i, product ) {								
					$('nav#products-nav').append('<a href="#hospital/' + hospital_id + '/product/'+ product.id+ '">' + product.code + '</a>');
					if (first_product=="") {
					    first_product = '/hospital/' + hospital_id + '/product/'+ product.id;					    	
						Backbone.history.navigate(first_product, true);
					}		
				});			
			});	  
			var curHospital = App.security.get('curHospital');
			//show hospital-name		
			$('h3.hospital-name').text(curHospital);			
			App.availableHospitals = App.security.get('availableHospitals');			
			$.each(App.availableHospitals, function( i, hospital ) {
				var id = i.substr(3);		
				console.log(id,hospital);
				$('ul#hospital-list-dropdown').append('<li data-id='+ id +'><a href="#">' + hospital+ '</a></li>');				
			});	
		});   
					
		
		$('#hospital-list-dropdown li').live('click', function(){	 
			var hospital_name = $(this).find('a').text(); 
			var hospital_id = $(this).data('id');
			
			//show hospital-name
			$('h3.hospital-name').text(hospital_name);
	    	    
			$('#app').empty();
			$('#breadcrumb-box').empty();
	    
			var first_product = "";
			//generate tabs
			App.ho = new App.Models.Hospital();		
			App.ho.fetch({data:{id: hospital_id}}).then(function(){		
				var products = App.ho.get('products');
				$('nav#products-nav').empty();
				$.each( products, function( i, product ) {		           
					$('nav#products-nav').append('<a href="#hospital/' + hospital_id + '/product/'+ product.id+ '">' + product.code + '</a>');
					if (first_product=="") {
					    first_product = '/hospital/' + hospital_id + '/product/'+ product.id;					    	
						Backbone.history.navigate(first_product, true);
					}	
				});			
			})	    
		});		
	}
		
			
	$('.nav a').click(function(){
		$('li.active').removeClass("active");
		$(this).closest('li').addClass("active");	
	});	
	
});
