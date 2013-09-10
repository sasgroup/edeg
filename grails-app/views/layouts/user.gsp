<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!--[if lt IE 9]>
<script src="/ihm/static/js/html5shiv.js"></script>
<![endif]-->
	<head>
		<title><g:layoutTitle default="Grails"/></title>
		<g:layoutHead/>
		<r:layoutResources />
		
		<!--[if IE 7]> 
			<link href="/ihm/static/css/ie7-fix.css" type="text/css" rel="stylesheet" />
		<![endif]-->
		<!--[if lt IE 8]>
    		<link href="/ihm/static/css/bootstrap-ie7buttonfix.css" rel="stylesheet">
		<![endif]-->	
		<!--[if IE 7]>
			<style>
			.bootstrap-select.btn-group .dropdown-menu li > a {
			    min-height: none;
			}
			</style>
			<![endif]-->	
	</head>
	<body>
		<g:render template="/layouts/header_user" />
		<g:render template="/layouts/templates" />
		<g:layoutBody/>
		<g:render template="/layouts/footer" />
		<r:layoutResources />
	</body>
</html>