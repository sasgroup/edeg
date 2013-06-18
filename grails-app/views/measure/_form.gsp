<%@ page import="ihm_demo.Measure" %>



<div class="fieldcontain ${hasErrors(bean: measureInstance, field: 'code', 'error')} ">
	<label for="code">
		<g:message code="measure.code.label" default="Code" />
		
	</label>
	<g:textField name="code" value="${measureInstance?.code}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: measureInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="measure.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${measureInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: measureInstance, field: 'notes', 'error')} ">
	<label for="notes">
		<g:message code="measure.notes.label" default="Notes" />
		
	</label>
	<g:textField name="notes" value="${measureInstance?.notes}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: measureInstance, field: 'measureCategory', 'error')} required">
	<label for="measureCategory">
		<g:message code="measure.measureCategory.label" default="Measure Category" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="measureCategory" name="measureCategory.id" from="${ihm_demo.MeasureCategory.list()}" optionKey="id" required="" value="${measureInstance?.measureCategory?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: measureInstance, field: 'cqmDomain', 'error')} required">
	<label for="cqmDomain">
		<g:message code="measure.cqmDomain.label" default="Cqm Domain" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="cqmDomain" name="cqmDomain.id" from="${ihm_demo.CqmDomain.list()}" optionKey="id" required="" value="${measureInstance?.cqmDomain?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: measureInstance, field: 'dataElements', 'error')} ">
	<label for="dataElements">
		<g:message code="measure.dataElements.label" default="Data Elements" />
		
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: measureInstance, field: 'products', 'error')} ">
	<label for="products">
		<g:message code="measure.products.label" default="Products" />
		
	</label>
	<g:select name="products" from="${ihm_demo.Product.list()}" multiple="multiple" optionKey="id" size="5" value="${measureInstance?.products*.id}" class="many-to-many"/>
</div>

