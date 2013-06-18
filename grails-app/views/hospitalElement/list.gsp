
<%@ page import="ihm_demo.HospitalElement" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hospitalElement.label', default: 'HospitalElement')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-hospitalElement" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-hospitalElement" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="answer" title="${message(code: 'hospitalElement.answer.label', default: 'Answer')}" />
					
						<g:sortableColumn property="question" title="${message(code: 'hospitalElement.question.label', default: 'Question')}" />
					
						<g:sortableColumn property="isIMO" title="${message(code: 'hospitalElement.isIMO.label', default: 'Is IMO')}" />
					
						<g:sortableColumn property="location" title="${message(code: 'hospitalElement.location.label', default: 'Location')}" />
					
						<g:sortableColumn property="queryMnemonic" title="${message(code: 'hospitalElement.queryMnemonic.label', default: 'Query Mnemonic')}" />
					
						<g:sortableColumn property="valueSet" title="${message(code: 'hospitalElement.valueSet.label', default: 'Value Set')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${hospitalElementInstanceList}" status="i" var="hospitalElementInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${hospitalElementInstance.id}">${fieldValue(bean: hospitalElementInstance, field: "answer")}</g:link></td>
					
						<td>${fieldValue(bean: hospitalElementInstance, field: "question")}</td>
					
						<td><g:formatBoolean boolean="${hospitalElementInstance.isIMO}" /></td>
					
						<td>${fieldValue(bean: hospitalElementInstance, field: "location")}</td>
					
						<td>${fieldValue(bean: hospitalElementInstance, field: "queryMnemonic")}</td>
					
						<td>${fieldValue(bean: hospitalElementInstance, field: "valueSet")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${hospitalElementInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
