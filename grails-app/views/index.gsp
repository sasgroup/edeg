<!DOCTYPE html>
  <!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
    <!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
    <!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
    <!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
    <!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<title>eDEG System</title>
		<!-- <r:require module="ihm" />
		<r:require modules="jquery-validation-ui" />-->
		<g:setUser />
		<g:if test="${session.user?.role == 'admin'}">
    		<meta name="layout" content="admin">
		</g:if>
		<g:elseif test="${session.user?.role == 'user'}">
    		<meta name="layout" content="user">
		</g:elseif>
		<g:else>
    		<meta name="layout" content="login">
		</g:else>	
		<link rel="icon" type="image/x-icon" href="/ihm/static/images/favicon.ico">	
	</head>
	<body>	    
	    	<div class="page-content">
	    		<div id ='message-box' class="alert">	    			
	    			
	    		</div>
	    		<div id ='breadcrumb-box'></div>
				<div id ='app' class='app' data-login="${session.user?.login}" data-role="${session.user?.role}"></div>
				
			</div>
	</body>
</html>