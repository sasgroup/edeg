
<%@ page import="ihm_demo.Measure" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'measure.label', default: 'Measure')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-measure" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-measure" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list measure">
			
				<g:if test="${measureInstance?.code}">
				<li class="fieldcontain">
					<span id="code-label" class="property-label"><g:message code="measure.code.label" default="Code" /></span>
					
						<span class="property-value" aria-labelledby="code-label"><g:fieldValue bean="${measureInstance}" field="code"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${measureInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="measure.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${measureInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${measureInstance?.notes}">
				<li class="fieldcontain">
					<span id="notes-label" class="property-label"><g:message code="measure.notes.label" default="Notes" /></span>
					
						<span class="property-value" aria-labelledby="notes-label"><g:fieldValue bean="${measureInstance}" field="notes"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${measureInstance?.measureCategory}">
				<li class="fieldcontain">
					<span id="measureCategory-label" class="property-label"><g:message code="measure.measureCategory.label" default="Measure Category" /></span>
					
						<span class="property-value" aria-labelledby="measureCategory-label"><g:link controller="measureCategory" action="show" id="${measureInstance?.measureCategory?.id}">${measureInstance?.measureCategory?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${measureInstance?.cqmDomain}">
				<li class="fieldcontain">
					<span id="cqmDomain-label" class="property-label"><g:message code="measure.cqmDomain.label" default="Cqm Domain" /></span>
					
						<span class="property-value" aria-labelledby="cqmDomain-label"><g:link controller="cqmDomain" action="show" id="${measureInstance?.cqmDomain?.id}">${measureInstance?.cqmDomain?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${measureInstance?.dataElements}">
				<li class="fieldcontain">
					<span id="dataElements-label" class="property-label"><g:message code="measure.dataElements.label" default="Data Elements" /></span>
					
						<g:each in="${measureInstance.dataElements}" var="d">
						<span class="property-value" aria-labelledby="dataElements-label"><g:link controller="dataElement" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${measureInstance?.products}">
				<li class="fieldcontain">
					<span id="products-label" class="property-label"><g:message code="measure.products.label" default="Products" /></span>
					
						<g:each in="${measureInstance.products}" var="p">
						<span class="property-value" aria-labelledby="products-label"><g:link controller="product" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${measureInstance?.id}" />
					<g:link class="edit" action="edit" id="${measureInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
