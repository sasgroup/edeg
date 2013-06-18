<%@ page import="ihm_demo.DataElement" %>



<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'code', 'error')} ">
	<label for="code">
		<g:message code="dataElement.code.label" default="Code" />
		
	</label>
	<g:textField name="code" value="${dataElementInstance?.code}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="dataElement.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${dataElementInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'notes', 'error')} ">
	<label for="notes">
		<g:message code="dataElement.notes.label" default="Notes" />
		
	</label>
	<g:textField name="notes" value="${dataElementInstance?.notes}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementInstance, field: 'measures', 'error')} ">
	<label for="measures">
		<g:message code="dataElement.measures.label" default="Measures" />
		
	</label>
	<g:select name="measures" from="${ihm_demo.Measure.list()}" multiple="multiple" optionKey="id" size="5" value="${dataElementInstance?.measures*.id}" class="many-to-many"/>
</div>

