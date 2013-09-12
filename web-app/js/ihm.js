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
			var curHospital = App.security.get('curHospital');
			//show hospital-name		
			$('h3.hospital-name').text(curHospital).attr("data-id",hospital_id);			
			
			var first_product = "";
			//generate tabs
			App.ho = new App.Models.Hospital();		
			App.ho.fetch({data:{id: hospital_id}}).then(function(){		
				var products = App.ho.get('products');				
				$('nav#products-nav').empty();
				$.each( products, function( i, product ) {								
					$('nav#products-nav').append('<a href="#hospital/' + hospital_id + '/product/'+ product.id+ '">' + product.code + '</a>');
					/*if (first_product=="") {
					    first_product = '/hospital/' + hospital_id + '/product/'+ product.id;					    	
						Backbone.history.navigate(first_product, true);
					}	*/	
				});	
								
				Backbone.history.navigate('/home/' + hospital_id , true);
			});	  
							
			App.availableHospitals = App.security.get('availableHospitals');			
			var output = new Array();

			$.each(App.availableHospitals, function(key, value) {
				output.push({id: key, name: value});
			});
			output.sort(function(a,b) {

			    if(a.name > b.name) return 1;
			    if(a.name < b.name) return -1;
			    return 0;
			});

			for(var index in output) {
				$('ul#hospital-list-dropdown').append('<li data-id='+ output[index].id +'><a href="#">' + output[index].name+ '</a></li>');	
			}
		});   
							
		$('#hospital-list-dropdown li').live('click', function(){	 
			var hospital_name = $(this).find('a').text(); 
			var hospital_id = $(this).data('id');
			
			//show hospital-name
			$('h3.hospital-name').text(hospital_name).attr("data-id",hospital_id);	
			    	    
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
				});		
								
				Backbone.history.navigate('/home/' + hospital_id , true);
			})	    
		});		
	}
		
			
	$('.nav a').click(function(){
		$('li.active').removeClass("active");
		$(this).closest('li').addClass("active");	
	});	
	
	$('.alert .close').live("click", function(e) {
	    $(this).parent().hide();
	});
	
});
