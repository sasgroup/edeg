
<%@ page import="ihm_demo.Ehr" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'ehr.label', default: 'Ehr')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-ehr" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-ehr" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="code" title="${message(code: 'ehr.code.label', default: 'Code')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'ehr.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="notes" title="${message(code: 'ehr.notes.label', default: 'Notes')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${ehrInstanceList}" status="i" var="ehrInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${ehrInstance.id}">${fieldValue(bean: ehrInstance, field: "code")}</g:link></td>
					
						<td>${fieldValue(bean: ehrInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: ehrInstance, field: "notes")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${ehrInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
