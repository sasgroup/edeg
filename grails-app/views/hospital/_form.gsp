<%@ page import="ihm_demo.Hospital" %>



<div class="fieldcontain ${hasErrors(bean: hospitalInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="hospital.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${hospitalInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalInstance, field: 'notes', 'error')} ">
	<label for="notes">
		<g:message code="hospital.notes.label" default="Notes" />
		
	</label>
	<g:textArea name="notes" cols="40" rows="5" maxlength="5000" value="${hospitalInstance?.notes}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalInstance, field: 'ehr', 'error')} required">
	<label for="ehr">
		<g:message code="hospital.ehr.label" default="Ehr" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="ehr" name="ehr.id" from="${ihm_demo.Ehr.list()}" optionKey="id" required="" value="${hospitalInstance?.ehr?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalInstance, field: 'products', 'error')} ">
	<label for="products">
		<g:message code="hospital.products.label" default="Products" />
		
	</label>
	
</div>

