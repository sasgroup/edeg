
<%@ page import="ihm_demo.Measure" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'measure.label', default: 'Measure')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-measure" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-measure" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="code" title="${message(code: 'measure.code.label', default: 'Code')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'measure.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="notes" title="${message(code: 'measure.notes.label', default: 'Notes')}" />
					
						<th><g:message code="measure.measureCategory.label" default="Measure Category" /></th>
					
						<th><g:message code="measure.cqmDomain.label" default="Cqm Domain" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${measureInstanceList}" status="i" var="measureInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${measureInstance.id}">${fieldValue(bean: measureInstance, field: "code")}</g:link></td>
					
						<td>${fieldValue(bean: measureInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: measureInstance, field: "notes")}</td>
					
						<td>${fieldValue(bean: measureInstance, field: "measureCategory")}</td>
					
						<td>${fieldValue(bean: measureInstance, field: "cqmDomain")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${measureInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
