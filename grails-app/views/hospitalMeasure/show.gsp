
<%@ page import="ihm_demo.HospitalMeasure" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hospitalMeasure.label', default: 'HospitalMeasure')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-hospitalMeasure" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-hospitalMeasure" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list hospitalMeasure">
			
				<g:if test="${hospitalMeasureInstance?.approved}">
				<li class="fieldcontain">
					<span id="approved-label" class="property-label"><g:message code="hospitalMeasure.approved.label" default="Approved" /></span>
					
						<span class="property-value" aria-labelledby="approved-label"><g:formatBoolean boolean="${hospitalMeasureInstance?.approved}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalMeasureInstance?.completed}">
				<li class="fieldcontain">
					<span id="completed-label" class="property-label"><g:message code="hospitalMeasure.completed.label" default="Completed" /></span>
					
						<span class="property-value" aria-labelledby="completed-label"><g:formatBoolean boolean="${hospitalMeasureInstance?.completed}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalMeasureInstance?.confirmed}">
				<li class="fieldcontain">
					<span id="confirmed-label" class="property-label"><g:message code="hospitalMeasure.confirmed.label" default="Confirmed" /></span>
					
						<span class="property-value" aria-labelledby="confirmed-label"><g:formatBoolean boolean="${hospitalMeasureInstance?.confirmed}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalMeasureInstance?.included}">
				<li class="fieldcontain">
					<span id="included-label" class="property-label"><g:message code="hospitalMeasure.included.label" default="Included" /></span>
					
						<span class="property-value" aria-labelledby="included-label"><g:formatBoolean boolean="${hospitalMeasureInstance?.included}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalMeasureInstance?.hospitalElement}">
				<li class="fieldcontain">
					<span id="hospitalElement-label" class="property-label"><g:message code="hospitalMeasure.hospitalElement.label" default="Hospital Element" /></span>
					
						<g:each in="${hospitalMeasureInstance.hospitalElement}" var="h">
						<span class="property-value" aria-labelledby="hospitalElement-label"><g:link controller="hospitalElement" action="show" id="${h.id}">${h?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalMeasureInstance?.hospitals}">
				<li class="fieldcontain">
					<span id="hospitals-label" class="property-label"><g:message code="hospitalMeasure.hospitals.label" default="Hospitals" /></span>
					
						<g:each in="${hospitalMeasureInstance.hospitals}" var="h">
						<span class="property-value" aria-labelledby="hospitals-label"><g:link controller="hospital" action="show" id="${h.id}">${h?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${hospitalMeasureInstance?.measures}">
				<li class="fieldcontain">
					<span id="measures-label" class="property-label"><g:message code="hospitalMeasure.measures.label" default="Measures" /></span>
					
						<g:each in="${hospitalMeasureInstance.measures}" var="m">
						<span class="property-value" aria-labelledby="measures-label"><g:link controller="measure" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${hospitalMeasureInstance?.id}" />
					<g:link class="edit" action="edit" id="${hospitalMeasureInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
