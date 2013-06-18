<%@ page import="ihm_demo.Users" %>



<div class="fieldcontain ${hasErrors(bean: usersInstance, field: 'username', 'error')} ">
	<label for="username">
		<g:message code="users.username.label" default="Username" />
		
	</label>
	<g:textField name="username" value="${usersInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: usersInstance, field: 'password', 'error')} ">
	<label for="password">
		<g:message code="users.password.label" default="Password" />
		
	</label>
	<g:textField name="password" value="${usersInstance?.password}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: usersInstance, field: 'role', 'error')} ">
	<label for="role">
		<g:message code="users.role.label" default="Role" />
		
	</label>
	<g:select name="role" from="${usersInstance.constraints.role.inList}" value="${usersInstance?.role}" valueMessagePrefix="users.role" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: usersInstance, field: 'hospital', 'error')} required">
	<label for="hospital">
		<g:message code="users.hospital.label" default="Hospital" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="hospital" name="hospital.id" from="${ihm_demo.Hospital.list()}" optionKey="id" required="" value="${usersInstance?.hospital?.id}" class="many-to-one"/>
</div>

