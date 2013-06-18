
<%@ page import="ihm_demo.HospitalMeasure" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hospitalMeasure.label', default: 'HospitalMeasure')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-hospitalMeasure" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-hospitalMeasure" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="approved" title="${message(code: 'hospitalMeasure.approved.label', default: 'Approved')}" />
					
						<g:sortableColumn property="completed" title="${message(code: 'hospitalMeasure.completed.label', default: 'Completed')}" />
					
						<g:sortableColumn property="confirmed" title="${message(code: 'hospitalMeasure.confirmed.label', default: 'Confirmed')}" />
					
						<g:sortableColumn property="included" title="${message(code: 'hospitalMeasure.included.label', default: 'Included')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${hospitalMeasureInstanceList}" status="i" var="hospitalMeasureInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${hospitalMeasureInstance.id}">${fieldValue(bean: hospitalMeasureInstance, field: "approved")}</g:link></td>
					
						<td><g:formatBoolean boolean="${hospitalMeasureInstance.completed}" /></td>
					
						<td><g:formatBoolean boolean="${hospitalMeasureInstance.confirmed}" /></td>
					
						<td><g:formatBoolean boolean="${hospitalMeasureInstance.included}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${hospitalMeasureInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
