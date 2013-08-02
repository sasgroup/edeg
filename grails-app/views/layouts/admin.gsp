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
		<g:render template="/layouts/header" />
		<g:render template="/layouts/template/admin/data_element_default" />	
		<g:render template="/layouts/template/admin/ehr" />
		<g:render template="/layouts/template/admin/element" />
		<g:render template="/layouts/template/admin/hospital" />
		<g:render template="/layouts/template/admin/measure" />
		<g:render template="/layouts/template/admin/product" />	
		<g:render template="/layouts/template/user/hospital_measure" />
		<g:render template="/layouts/template/user/data_element" />			
		<g:layoutBody/>
		<g:render template="/layouts/footer" />
		<r:layoutResources />
	</body>
</html>