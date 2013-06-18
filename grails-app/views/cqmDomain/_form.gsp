<%@ page import="ihm_demo.CqmDomain" %>



<div class="fieldcontain ${hasErrors(bean: cqmDomainInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="cqmDomain.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${cqmDomainInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cqmDomainInstance, field: 'notes', 'error')} ">
	<label for="notes">
		<g:message code="cqmDomain.notes.label" default="Notes" />
		
	</label>
	<g:textArea name="notes" cols="40" rows="5" maxlength="5000" value="${cqmDomainInstance?.notes}"/>
</div>

