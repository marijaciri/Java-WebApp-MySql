<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" type="text/css" href="index11.css">
</head>
<body class="login">

<div class="login-page">
  <div class="form">
  
  <h1>LOGOVANJE</h1>
  <form class="login-form" action="Putovanja" method="post">
    <ul>
      <li>
        <label>Korisnicko ime:</label>
        <input type="text" name="username" value="${param.username}">
      </li>


      <li>
        <label>Lozinka:</label>
        <input type="password" name="password" value="${param.password}">
      </li>

      
      <li>
       <button  input  type="submit" name="action" value="Logovanje">LOGOVANJE</button>

       <li>
         
       </li>
       <button  input  type="submit" name="action" value="Destinacije">DESTINACIJE</button>
        
      </li>
    </ul>
    </form>
  </form>
  ${requestScope.msg}<br>
  
  </div>
  </div>
  
 
</body>
</html>