
<%@ page import="ihm_demo.HospitalElement" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hospitalElement.label', default: 'HospitalElement')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-hospitalElement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-hospitalElement" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list hospitalElement">
			
				<g:if test="${hospitalElementInstance?.answer}">
				<li class="fieldcontain">
					<span id="answer-label" class="property-label"><g:message code="hospitalElement.answer.label" default="Answer" /></span>
					
						<span class="property-value" aria-labelledby="answer-label"><g:fieldValue bean="${hospitalElementInstance}" field="answer"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalElementInstance?.question}">
				<li class="fieldcontain">
					<span id="question-label" class="property-label"><g:message code="hospitalElement.question.label" default="Question" /></span>
					
						<span class="property-value" aria-labelledby="question-label"><g:fieldValue bean="${hospitalElementInstance}" field="question"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalElementInstance?.isIMO}">
				<li class="fieldcontain">
					<span id="isIMO-label" class="property-label"><g:message code="hospitalElement.isIMO.label" default="Is IMO" /></span>
					
						<span class="property-value" aria-labelledby="isIMO-label"><g:formatBoolean boolean="${hospitalElementInstance?.isIMO}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalElementInstance?.location}">
				<li class="fieldcontain">
					<span id="location-label" class="property-label"><g:message code="hospitalElement.location.label" default="Location" /></span>
					
						<span class="property-value" aria-labelledby="location-label"><g:fieldValue bean="${hospitalElementInstance}" field="location"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalElementInstance?.queryMnemonic}">
				<li class="fieldcontain">
					<span id="queryMnemonic-label" class="property-label"><g:message code="hospitalElement.queryMnemonic.label" default="Query Mnemonic" /></span>
					
						<span class="property-value" aria-labelledby="queryMnemonic-label"><g:fieldValue bean="${hospitalElementInstance}" field="queryMnemonic"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalElementInstance?.valueSet}">
				<li class="fieldcontain">
					<span id="valueSet-label" class="property-label"><g:message code="hospitalElement.valueSet.label" default="Value Set" /></span>
					
						<span class="property-value" aria-labelledby="valueSet-label"><g:fieldValue bean="${hospitalElementInstance}" field="valueSet"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalElementInstance?.valueSetRequired}">
				<li class="fieldcontain">
					<span id="valueSetRequired-label" class="property-label"><g:message code="hospitalElement.valueSetRequired.label" default="Value Set Required" /></span>
					
						<span class="property-value" aria-labelledby="valueSetRequired-label"><g:formatBoolean boolean="${hospitalElementInstance?.valueSetRequired}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalElementInstance?.locationtype}">
				<li class="fieldcontain">
					<span id="locationtype-label" class="property-label"><g:message code="hospitalElement.locationtype.label" default="Locationtype" /></span>
					
						<span class="property-value" aria-labelledby="locationtype-label"><g:fieldValue bean="${hospitalElementInstance}" field="locationtype"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalElementInstance?.dataElementDefaults}">
				<li class="fieldcontain">
					<span id="dataElementDefaults-label" class="property-label"><g:message code="hospitalElement.dataElementDefaults.label" default="Data Element Defaults" /></span>
					
						<g:each in="${hospitalElementInstance.dataElementDefaults}" var="d">
						<span class="property-value" aria-labelledby="dataElementDefaults-label"><g:link controller="dataElementDefaults" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalElementInstance?.dataElements}">
				<li class="fieldcontain">
					<span id="dataElements-label" class="property-label"><g:message code="hospitalElement.dataElements.label" default="Data Elements" /></span>
					
						<g:each in="${hospitalElementInstance.dataElements}" var="d">
						<span class="property-value" aria-labelledby="dataElements-label"><g:link controller="dataElement" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalElementInstance?.hospitalMeasure}">
				<li class="fieldcontain">
					<span id="hospitalMeasure-label" class="property-label"><g:message code="hospitalElement.hospitalMeasure.label" default="Hospital Measure" /></span>
					
						<g:each in="${hospitalElementInstance.hospitalMeasure}" var="h">
						<span class="property-value" aria-labelledby="hospitalMeasure-label"><g:link controller="hospitalMeasure" action="show" id="${h.id}">${h?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${hospitalElementInstance?.id}" />
					<g:link class="edit" action="edit" id="${hospitalElementInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
