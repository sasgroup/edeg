
<%@ page import="ihm_demo.DataElementDefaults" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dataElementDefaults.label', default: 'DataElementDefaults')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-dataElementDefaults" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-dataElementDefaults" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list dataElementDefaults">
			
				<g:if test="${dataElementDefaultsInstance?.isIMO}">
				<li class="fieldcontain">
					<span id="isIMO-label" class="property-label"><g:message code="dataElementDefaults.isIMO.label" default="Is IMO" /></span>
					
						<span class="property-value" aria-labelledby="isIMO-label"><g:formatBoolean boolean="${dataElementDefaultsInstance?.isIMO}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementDefaultsInstance?.location}">
				<li class="fieldcontain">
					<span id="location-label" class="property-label"><g:message code="dataElementDefaults.location.label" default="Location" /></span>
					
						<span class="property-value" aria-labelledby="location-label"><g:fieldValue bean="${dataElementDefaultsInstance}" field="location"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementDefaultsInstance?.queryMnemonic}">
				<li class="fieldcontain">
					<span id="queryMnemonic-label" class="property-label"><g:message code="dataElementDefaults.queryMnemonic.label" default="Query Mnemonic" /></span>
					
						<span class="property-value" aria-labelledby="queryMnemonic-label"><g:fieldValue bean="${dataElementDefaultsInstance}" field="queryMnemonic"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementDefaultsInstance?.valueSet}">
				<li class="fieldcontain">
					<span id="valueSet-label" class="property-label"><g:message code="dataElementDefaults.valueSet.label" default="Value Set" /></span>
					
						<span class="property-value" aria-labelledby="valueSet-label"><g:fieldValue bean="${dataElementDefaultsInstance}" field="valueSet"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementDefaultsInstance?.valueSetRequired}">
				<li class="fieldcontain">
					<span id="valueSetRequired-label" class="property-label"><g:message code="dataElementDefaults.valueSetRequired.label" default="Value Set Required" /></span>
					
						<span class="property-value" aria-labelledby="valueSetRequired-label"><g:formatBoolean boolean="${dataElementDefaultsInstance?.valueSetRequired}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementDefaultsInstance?.locationtype}">
				<li class="fieldcontain">
					<span id="locationtype-label" class="property-label"><g:message code="dataElementDefaults.locationtype.label" default="Locationtype" /></span>
					
						<span class="property-value" aria-labelledby="locationtype-label"><g:fieldValue bean="${dataElementDefaultsInstance}" field="locationtype"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementDefaultsInstance?.dataElements}">
				<li class="fieldcontain">
					<span id="dataElements-label" class="property-label"><g:message code="dataElementDefaults.dataElements.label" default="Data Elements" /></span>
					
						<g:each in="${dataElementDefaultsInstance.dataElements}" var="d">
						<span class="property-value" aria-labelledby="dataElements-label"><g:link controller="dataElement" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${dataElementDefaultsInstance?.ehrs}">
				<li class="fieldcontain">
					<span id="ehrs-label" class="property-label"><g:message code="dataElementDefaults.ehrs.label" default="Ehrs" /></span>
					
						<g:each in="${dataElementDefaultsInstance.ehrs}" var="e">
						<span class="property-value" aria-labelledby="ehrs-label"><g:link controller="ehr" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${dataElementDefaultsInstance?.id}" />
					<g:link class="edit" action="edit" id="${dataElementDefaultsInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
