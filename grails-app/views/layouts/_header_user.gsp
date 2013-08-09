<div class="page-container">      
	<div class="page-header">    
	    <header>
	        <a class="logo" href="#"></a>	 
	        <h3 class="hospital-name"></h3>
	        <div id="loginHeader" class="pull-right">
					<g:loginControl />
					<input id="role" type="hidden" value= ${session.user?.role}>
			</div> 
	        
	        <div class="hospital-dropdown btn-group">
  				<button type="button" class="btn btn-default">Hospitals</button>
  				<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
    			<span class="caret"></span>
  			</button>  
  			<ul class="dropdown-menu" id="hospital-list-dropdown">
    			<!-- <li><a href="#">Lindemann Mental Health Center</a></li>  -->    			
  			</ul>
			</div>
									
	        <nav id="products-nav" class="header-nav">
	            <!-- <a href="#product1">Product1</a> 
	            <a href="#product2">Product2</a>
	            <a href="#product3">Product3</a>
	            <a href="#product4">Product4</a>
	            <a href="#product5">Product5</a>-->	            
	        </nav>
	        
	    </header>
	</div>