<%@ page import="ihm_demo.DataElementDefaults" %>



<div class="fieldcontain ${hasErrors(bean: dataElementDefaultsInstance, field: 'isIMO', 'error')} ">
	<label for="isIMO">
		<g:message code="dataElementDefaults.isIMO.label" default="Is IMO" />
		
	</label>
	<g:checkBox name="isIMO" value="${dataElementDefaultsInstance?.isIMO}" />
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementDefaultsInstance, field: 'location', 'error')} ">
	<label for="location">
		<g:message code="dataElementDefaults.location.label" default="Location" />
		
	</label>
	<g:textField name="location" value="${dataElementDefaultsInstance?.location}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementDefaultsInstance, field: 'queryMnemonic', 'error')} ">
	<label for="queryMnemonic">
		<g:message code="dataElementDefaults.queryMnemonic.label" default="Query Mnemonic" />
		
	</label>
	<g:textField name="queryMnemonic" value="${dataElementDefaultsInstance?.queryMnemonic}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementDefaultsInstance, field: 'valueSet', 'error')} ">
	<label for="valueSet">
		<g:message code="dataElementDefaults.valueSet.label" default="Value Set" />
		
	</label>
	<g:textField name="valueSet" value="${dataElementDefaultsInstance?.valueSet}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementDefaultsInstance, field: 'valueSetRequired', 'error')} ">
	<label for="valueSetRequired">
		<g:message code="dataElementDefaults.valueSetRequired.label" default="Value Set Required" />
		
	</label>
	<g:checkBox name="valueSetRequired" value="${dataElementDefaultsInstance?.valueSetRequired}" />
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementDefaultsInstance, field: 'locationtype', 'error')} required">
	<label for="locationtype">
		<g:message code="dataElementDefaults.locationtype.label" default="Locationtype" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="locationtype" from="${ihm_demo.LocationType?.values()}" keys="${ihm_demo.LocationType.values()*.name()}" required="" value="${dataElementDefaultsInstance?.locationtype?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementDefaultsInstance, field: 'dataElements', 'error')} ">
	<label for="dataElements">
		<g:message code="dataElementDefaults.dataElements.label" default="Data Elements" />
		
	</label>
	<g:select name="dataElements" from="${ihm_demo.DataElement.list()}" multiple="multiple" optionKey="id" size="5" value="${dataElementDefaultsInstance?.dataElements*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: dataElementDefaultsInstance, field: 'ehrs', 'error')} ">
	<label for="ehrs">
		<g:message code="dataElementDefaults.ehrs.label" default="Ehrs" />
		
	</label>
	<g:select name="ehrs" from="${ihm_demo.Ehr.list()}" multiple="multiple" optionKey="id" size="5" value="${dataElementDefaultsInstance?.ehrs*.id}" class="many-to-many"/>
</div>

