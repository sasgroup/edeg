<!DOCTYPE html>
<html>
	<head>
		<title>IHM Demo</title>
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

	</head>
	<body>	    
	    	<div class="page-content">
	    		<div id ='message-box'></div>
	    		<div id ='breadcrumb-box'></div>
				<div id ='app' class='app' data-login="${session.user?.login}" data-role="${session.user?.role}" data-hospital="${session.curHospital}"></div>
			</div>
	</body>
</html>