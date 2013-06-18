<%@ page import="ihm_demo.MeasureCategory" %>



<div class="fieldcontain ${hasErrors(bean: measureCategoryInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="measureCategory.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${measureCategoryInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: measureCategoryInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="measureCategory.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${measureCategoryInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: measureCategoryInstance, field: 'categoryType', 'error')} required">
	<label for="categoryType">
		<g:message code="measureCategory.categoryType.label" default="Category Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="categoryType" from="${ihm_demo.CategoryType?.values()}" keys="${ihm_demo.CategoryType.values()*.name()}" required="" value="${measureCategoryInstance?.categoryType?.name()}"/>
</div>

