
<%@ page import="ihm_demo.Hospital" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hospital.label', default: 'Hospital')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-hospital" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-hospital" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list hospital">
			
				<g:if test="${hospitalInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="hospital.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${hospitalInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalInstance?.notes}">
				<li class="fieldcontain">
					<span id="notes-label" class="property-label"><g:message code="hospital.notes.label" default="Notes" /></span>
					
						<span class="property-value" aria-labelledby="notes-label"><g:fieldValue bean="${hospitalInstance}" field="notes"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalInstance?.ehr}">
				<li class="fieldcontain">
					<span id="ehr-label" class="property-label"><g:message code="hospital.ehr.label" default="Ehr" /></span>
					
						<span class="property-value" aria-labelledby="ehr-label"><g:link controller="ehr" action="show" id="${hospitalInstance?.ehr?.id}">${hospitalInstance?.ehr?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalInstance?.products}">
				<li class="fieldcontain">
					<span id="products-label" class="property-label"><g:message code="hospital.products.label" default="Products" /></span>
					
						<g:each in="${hospitalInstance.products}" var="p">
						<span class="property-value" aria-labelledby="products-label"><g:link controller="product" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${hospitalInstance?.id}" />
					<g:link class="edit" action="edit" id="${hospitalInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
