<%@ page import="ihm_demo.Ehr" %>



<div class="fieldcontain ${hasErrors(bean: ehrInstance, field: 'code', 'error')} ">
	<label for="code">
		<g:message code="ehr.code.label" default="Code" />
		
	</label>
	<g:textField name="code" value="${ehrInstance?.code}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ehrInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="ehr.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${ehrInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ehrInstance, field: 'notes', 'error')} ">
	<label for="notes">
		<g:message code="ehr.notes.label" default="Notes" />
		
	</label>
	<g:textArea name="notes" cols="40" rows="5" maxlength="5000" value="${ehrInstance?.notes}"/>
</div>

