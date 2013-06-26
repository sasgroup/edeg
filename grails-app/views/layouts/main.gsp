<!doctype html>
<html>
	<head>
		<title><g:layoutTitle default="Grails"/></title>
		<g:layoutHead/>
		<r:layoutResources />
	</head>
	<body>
		<g:render template="/layouts/header" />
		<g:render template="/layouts/template/message" />
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