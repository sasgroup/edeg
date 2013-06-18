
<%@ page import="ihm_demo.DataElement" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dataElement.label', default: 'DataElement')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-dataElement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-dataElement" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list dataElement">
			
				<g:if test="${dataElementInstance?.code}">
				<li class="fieldcontain">
					<span id="code-label" class="property-label"><g:message code="dataElement.code.label" default="Code" /></span>
					
						<span class="property-value" aria-labelledby="code-label"><g:fieldValue bean="${dataElementInstance}" field="code"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="dataElement.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${dataElementInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementInstance?.notes}">
				<li class="fieldcontain">
					<span id="notes-label" class="property-label"><g:message code="dataElement.notes.label" default="Notes" /></span>
					
						<span class="property-value" aria-labelledby="notes-label"><g:fieldValue bean="${dataElementInstance}" field="notes"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementInstance?.measures}">
				<li class="fieldcontain">
					<span id="measures-label" class="property-label"><g:message code="dataElement.measures.label" default="Measures" /></span>
					
						<g:each in="${dataElementInstance.measures}" var="m">
						<span class="property-value" aria-labelledby="measures-label"><g:link controller="measure" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${dataElementInstance?.id}" />
					<g:link class="edit" action="edit" id="${dataElementInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
