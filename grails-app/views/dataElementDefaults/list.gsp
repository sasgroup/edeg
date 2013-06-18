
<%@ page import="ihm_demo.DataElementDefaults" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'dataElementDefaults.label', default: 'DataElementDefaults')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-dataElementDefaults" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-dataElementDefaults" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="isIMO" title="${message(code: 'dataElementDefaults.isIMO.label', default: 'Is IMO')}" />
					
						<g:sortableColumn property="location" title="${message(code: 'dataElementDefaults.location.label', default: 'Location')}" />
					
						<g:sortableColumn property="queryMnemonic" title="${message(code: 'dataElementDefaults.queryMnemonic.label', default: 'Query Mnemonic')}" />
					
						<g:sortableColumn property="valueSet" title="${message(code: 'dataElementDefaults.valueSet.label', default: 'Value Set')}" />
					
						<g:sortableColumn property="valueSetRequired" title="${message(code: 'dataElementDefaults.valueSetRequired.label', default: 'Value Set Required')}" />
					
						<g:sortableColumn property="locationtype" title="${message(code: 'dataElementDefaults.locationtype.label', default: 'Locationtype')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${dataElementDefaultsInstanceList}" status="i" var="dataElementDefaultsInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${dataElementDefaultsInstance.id}">${fieldValue(bean: dataElementDefaultsInstance, field: "isIMO")}</g:link></td>
					
						<td>${fieldValue(bean: dataElementDefaultsInstance, field: "location")}</td>
					
						<td>${fieldValue(bean: dataElementDefaultsInstance, field: "queryMnemonic")}</td>
					
						<td>${fieldValue(bean: dataElementDefaultsInstance, field: "valueSet")}</td>
					
						<td><g:formatBoolean boolean="${dataElementDefaultsInstance.valueSetRequired}" /></td>
					
						<td>${fieldValue(bean: dataElementDefaultsInstance, field: "locationtype")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${dataElementDefaultsInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
