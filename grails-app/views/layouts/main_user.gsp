<!DOCTYPE html>
<!--[if lt IE 9]>
<script src="/ihm/static/js/html5shiv.js"></script>
<![endif]-->
	<head>
		<title><g:layoutTitle default="Grails"/></title>
		<g:layoutHead/>
		<r:layoutResources />
	</head>
	<body>
		<g:render template="/layouts/header_user" />
		<g:render template="/layouts/template/data_element_default" />	
		<g:render template="/layouts/template/ehr" />
		<g:render template="/layouts/template/element" />
		<g:render template="/layouts/template/hospital" />
		<g:render template="/layouts/template/measure" />
		<g:render template="/layouts/template/product" />		
		<g:layoutBody/>
		<g:render template="/layouts/footer" />
		<r:layoutResources />
	</body>
</html>