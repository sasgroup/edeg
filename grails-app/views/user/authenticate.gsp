<html>
  <head>
   <title>IHM Demo</title>
	<r:require module="ihm" />
	<r:require modules="jquery-validation-ui" />
	<g:if test="${session.user.role == 'admin'}">
    	 <meta name="layout" content="admin">
	</g:if>
	<g:else>
       	 <meta name="layout" content="user">
	</g:else>	
  </head>
  <body>
     <div class="page-content">
	 	<div id ='message-box'></div>
	 	<div id ='breadcrumb-box'></div>
		<div id ='app' class='app'></div>
	 </div>
  </body>
  </html>
  