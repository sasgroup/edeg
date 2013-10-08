<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <head>
    <meta name="layout" content="main" />
    <title>Login Error</title>
    <link rel="icon" type="image/x-icon" href="data:image/x-icon;base64,AAABAAEAEBAQAAEABAAoAQAAFgAAACgAAAAQAAAAIAAAAAEABAAAAAAAwAAAABMLAAATCwAAEAAAAAAAAACc73QA3OBRAIuGCgDT1ksAs7MuANTkWgCZlhkAxsg/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAYiIiIiIiIiJiIiIiIiIiImIiIiIiIiIiYiIiIiIiIiI0RiIiIiIiIlERN0ZiIiIiURERERN0YiJRERERERETdBERERERERERUREREREREREABREREREREQAABVERERERAAAAAFEREREAAAAAAFUREQAAAAAAAAURAAAAAAAAAAAAAExzAAAAAQAAamMAAAABAAAAAAAAc2wAAGUAAAASAAAAB3MAAGNlAABsbwAAAAAAAAAAAAAAAAAAAAAAAAwA">         
  </head>
  <body>
    <div class="body" style="width: 425px; height: 325px; border: 1px solid #006E86; margin: 150px auto 0 auto">    
     <div id="side" style="width: 130px; height: 325px; position:relative; float:left; background-color: #B3C3D3; /* for old browsers*/; 
     													background: -webkit-linear-gradient(top, #B3C3D3, #FAFBFC);
     													background: -moz-linear-gradient(top, #B3C3D3, #FAFBFC); /* Firefox 3.6+ */;
     													background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#B3C3D3), color-stop(100%,#FAFBFC));
     													background: -webkit-linear-gradient(top, #B3C3D3, #FAFBFC);
    													background: -o-linear-gradient(top, #B3C3D3, #FAFBFC); /* Opera 11.10+ */
   														background: -ms-linear-gradient(top, #B3C3D3, #FAFBFC); /* IE10 */
    													background: linear-gradient(top, #B3C3D3, #FAFBFC); /* CSS3 */ 
    													/*padding: 10px;*/
    													/*border: 1px solid #B3C3D3;*/"
     													>
     </div>	
     <div style="vertical-align: top; position:relative; float:left; "> 
     <img src="http://ihm-services.com/Portals/215627/assets/images/ihm-logo-TRANS-sm.png" style="margin-left: 75px; margin-top: 5px;">
     <div id="error-message" style="border : 2px solid red; color: red; margin-top: 20px; padding-left: 5px">Invalid user name or password</div>
		<form method="POST" style="margin-top: 10px" action='<%= response.encodeURL("j_security_check") %>' name="loginForm">
			<table class="enter">
				<tr>
					<td class="label"><strong>Username:</strong></td>
					<td><input type="text" name="j_username" maxlength="20" size="16" tabindex="1" id="username" style="width: 125px; background:#F0F0F0!important" autofocus="autofocus" /></td>
				</tr>
				<tr>
					<td class="label"><strong>Password:</strong></td>
					<td><input type="password" name="j_password" maxlength="50" size="16" tabindex="2" id="password" style="width: 125px; background:#F0F0F0!important"/></td>					
				</tr>
				<tr>
					<td></td>
					<td><input name="#" type="submit" id="loginButton" value="Login" tabindex="3" class="inputTools" style="float: right;" /></td>
				</tr>				
			</table>
		</form>		
	</div>	
    
    </div>
    <div style="width: 425px; margin: 2px auto 0 auto; font-size: 12px;"> Version 1.1 - Copyright 2011-2013, Institute for Health Metrics, All Rights Reserved.</div>   
  </body>
</html>