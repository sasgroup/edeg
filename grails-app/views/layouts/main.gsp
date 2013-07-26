<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<title><g:layoutTitle default="Grails"/></title>
		<g:layoutHead/>
		<r:layoutResources />
	</head>
	<body>
		<g:render template="/layouts/header" />
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