<%@ page import="ihm_demo.Product" %>



<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'code', 'error')} ">
	<label for="code">
		<g:message code="product.code.label" default="Code" />
		
	</label>
	<g:textField name="code" value="${productInstance?.code}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="product.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${productInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'notes', 'error')} ">
	<label for="notes">
		<g:message code="product.notes.label" default="Notes" />
		
	</label>
	<g:textArea name="notes" cols="40" rows="5" maxlength="5000" value="${productInstance?.notes}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'hospitals', 'error')} ">
	<label for="hospitals">
		<g:message code="product.hospitals.label" default="Hospitals" />
		
	</label>
	<g:select name="hospitals" from="${ihm_demo.Hospital.list()}" multiple="multiple" optionKey="id" size="5" value="${productInstance?.hospitals*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'measures', 'error')} ">
	<label for="measures">
		<g:message code="product.measures.label" default="Measures" />
		
	</label>
	
</div>

