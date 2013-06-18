<%@ page import="ihm_demo.HospitalMeasure" %>



<div class="fieldcontain ${hasErrors(bean: hospitalMeasureInstance, field: 'approved', 'error')} ">
	<label for="approved">
		<g:message code="hospitalMeasure.approved.label" default="Approved" />
		
	</label>
	<g:checkBox name="approved" value="${hospitalMeasureInstance?.approved}" />
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalMeasureInstance, field: 'completed', 'error')} ">
	<label for="completed">
		<g:message code="hospitalMeasure.completed.label" default="Completed" />
		
	</label>
	<g:checkBox name="completed" value="${hospitalMeasureInstance?.completed}" />
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalMeasureInstance, field: 'confirmed', 'error')} ">
	<label for="confirmed">
		<g:message code="hospitalMeasure.confirmed.label" default="Confirmed" />
		
	</label>
	<g:checkBox name="confirmed" value="${hospitalMeasureInstance?.confirmed}" />
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalMeasureInstance, field: 'included', 'error')} ">
	<label for="included">
		<g:message code="hospitalMeasure.included.label" default="Included" />
		
	</label>
	<g:checkBox name="included" value="${hospitalMeasureInstance?.included}" />
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalMeasureInstance, field: 'hospitalElement', 'error')} ">
	<label for="hospitalElement">
		<g:message code="hospitalMeasure.hospitalElement.label" default="Hospital Element" />
		
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalMeasureInstance, field: 'hospitals', 'error')} ">
	<label for="hospitals">
		<g:message code="hospitalMeasure.hospitals.label" default="Hospitals" />
		
	</label>
	<g:select name="hospitals" from="${ihm_demo.Hospital.list()}" multiple="multiple" optionKey="id" size="5" value="${hospitalMeasureInstance?.hospitals*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hospitalMeasureInstance, field: 'measures', 'error')} ">
	<label for="measures">
		<g:message code="hospitalMeasure.measures.label" default="Measures" />
		
	</label>
	<g:select name="measures" from="${ihm_demo.Measure.list()}" multiple="multiple" optionKey="id" size="5" value="${hospitalMeasureInstance?.measures*.id}" class="many-to-many"/>
</div>

