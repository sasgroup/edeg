<html>
  <head>
    <meta name="layout" content="main" />
    <title>Login</title>         
  </head>
  <body>
    <div class="body">
      <h1>Login</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
		<form method="POST" action='<%= response.encodeURL("j_security_check") %>' name="loginForm">
			<table class="enter">
				<tr>
					<td class="label">Username:</td>
					<td><input type="text" name="j_username" maxlength="20" size="16" tabindex="1" id="username" class="input"/></td>
				</tr>
				<tr>
					<td class="label">Password:</td>
					<td><input type="password" name="j_password" maxlength="50" size="16" tabindex="2" id="password" class="input"/></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><input name="#" type="submit" id="loginButton" value="Login" tabindex="3" class="inputTools" /></td>
				</tr>
			</table>
		</form>
    </div>
  </body>
</html>