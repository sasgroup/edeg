<%@ page import="ihm_demo.HospitalElement" %>



<div class="fieldcontain ${hasErrors(bean: hospitalElementInstance, field: 'answer', 'error')} ">
	<label for="answer">
		<g:message code="hospitalElement.answer.label" default="Answer" />
		
	</label>
	<g:textField name="answer" value="${hospitalElementInstance?.answer}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalElementInstance, field: 'question', 'error')} ">
	<label for="question">
		<g:message code="hospitalElement.question.label" default="Question" />
		
	</label>
	<g:textField name="question" value="${hospitalElementInstance?.question}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalElementInstance, field: 'isIMO', 'error')} ">
	<label for="isIMO">
		<g:message code="hospitalElement.isIMO.label" default="Is IMO" />
		
	</label>
	<g:checkBox name="isIMO" value="${hospitalElementInstance?.isIMO}" />
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalElementInstance, field: 'location', 'error')} ">
	<label for="location">
		<g:message code="hospitalElement.location.label" default="Location" />
		
	</label>
	<g:textField name="location" value="${hospitalElementInstance?.location}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalElementInstance, field: 'queryMnemonic', 'error')} ">
	<label for="queryMnemonic">
		<g:message code="hospitalElement.queryMnemonic.label" default="Query Mnemonic" />
		
	</label>
	<g:textField name="queryMnemonic" value="${hospitalElementInstance?.queryMnemonic}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalElementInstance, field: 'valueSet', 'error')} ">
	<label for="valueSet">
		<g:message code="hospitalElement.valueSet.label" default="Value Set" />
		
	</label>
	<g:textField name="valueSet" value="${hospitalElementInstance?.valueSet}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalElementInstance, field: 'valueSetRequired', 'error')} ">
	<label for="valueSetRequired">
		<g:message code="hospitalElement.valueSetRequired.label" default="Value Set Required" />
		
	</label>
	<g:checkBox name="valueSetRequired" value="${hospitalElementInstance?.valueSetRequired}" />
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalElementInstance, field: 'locationtype', 'error')} required">
	<label for="locationtype">
		<g:message code="hospitalElement.locationtype.label" default="Locationtype" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="locationtype" from="${ihm_demo.LocationType?.values()}" keys="${ihm_demo.LocationType.values()*.name()}" required="" value="${hospitalElementInstance?.locationtype?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalElementInstance, field: 'dataElementDefaults', 'error')} ">
	<label for="dataElementDefaults">
		<g:message code="hospitalElement.dataElementDefaults.label" default="Data Element Defaults" />
		
	</label>
	<g:select name="dataElementDefaults" from="${ihm_demo.DataElementDefaults.list()}" multiple="multiple" optionKey="id" size="5" value="${hospitalElementInstance?.dataElementDefaults*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalElementInstance, field: 'dataElements', 'error')} ">
	<label for="dataElements">
		<g:message code="hospitalElement.dataElements.label" default="Data Elements" />
		
	</label>
	<g:select name="dataElements" from="${ihm_demo.DataElement.list()}" multiple="multiple" optionKey="id" size="5" value="${hospitalElementInstance?.dataElements*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalElementInstance, field: 'hospitalMeasure', 'error')} ">
	<label for="hospitalMeasure">
		<g:message code="hospitalElement.hospitalMeasure.label" default="Hospital Measure" />
		
	</label>
	<g:select name="hospitalMeasure" from="${ihm_demo.HospitalMeasure.list()}" multiple="multiple" optionKey="id" size="5" value="${hospitalElementInstance?.hospitalMeasure*.id}" class="many-to-many"/>
</div>

