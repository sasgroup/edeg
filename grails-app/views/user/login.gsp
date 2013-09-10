<html>
  <head>
    <meta name="layout" content="main" />
    <title>Login</title>         
  </head>
  <body>
    <div class="body" style="width: 420px; height: 360px; border: 1px solid #006E86; margin: 150px auto 0 auto">
    <table id="main" style="border-collapse: collapse">
    <tr> 
     <td id="side" style="width: 100px; height: 350px;  background: #B3C3D3; /* for old browsers*/; 
     													background: -webkit-linear-gradient(top, #B3C3D3, #FAFBFC);
     													background: -moz-linear-gradient(top, #B3C3D3, #FAFBFC); /* Firefox 3.6+ */;
     													background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#B3C3D3), color-stop(100%,#FAFBFC));
     													background: -webkit-linear-gradient(top, #B3C3D3, #FAFBFC);
    													background: -o-linear-gradient(top, #B3C3D3, #FAFBFC); /* Opera 11.10+ */
   														background: -ms-linear-gradient(top, #B3C3D3, #FAFBFC); /* IE10 */
    													background: linear-gradient(top, #B3C3D3, #FAFBFC); /* CSS3 */ 
    													padding: 10px;
    													/*border: 1px solid #B3C3D3;*/"
     													>
     </td>	
     <td style="vertical-align: top"> 
     <img src="http://ihm-services.com/Portals/215627/assets/images/ihm-logo-TRANS-sm.png" style="margin-left: 75px;">
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
		<form method="POST" style="margin-top: 50px" action='<%= response.encodeURL("j_security_check") %>' name="loginForm">
			<table class="enter">
				<tr>
					<td class="label"><strong>Username:</strong></td>
					<td><input type="text" name="j_username" maxlength="20" size="16" tabindex="1" id="username" class="input" style="background:#F0F0F0!important"/></td>
				</tr>
				<tr>
					<td class="label"><strong>Password:</strong></td>
					<td><input type="password" name="j_password" maxlength="50" size="16" tabindex="2" id="password" class="input"/></td>					
				</tr>
				<tr>
					<td></td>
					<td><input name="#" type="submit" id="loginButton" value="Login" tabindex="3" class="inputTools" style="float: right;" /></td>
				</tr>
			</table>
		</form>		
	</td>	
	</tr>	
    </table>    
    </div>
    <div style="width: 420px; margin: 2px auto 0 auto; font-size: 12px;"> Version 1.1 - Copyright 2011-2013, Institute for Health Metrics, All Rights Reserved.</div>   
  </body>
</html>