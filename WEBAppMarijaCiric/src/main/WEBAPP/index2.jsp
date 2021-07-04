<%@page import="model.Putovanje"%>
<%@page import="model.Destinacija"%>
<%@page import="model.Korisnik"%>
<%@page import="model.DAO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
if (request.getSession().getAttribute("korisnik") != null) {
%>



<%
DAO dao = new DAO();
ArrayList<Korisnik> lk = dao.selectKorisnik();
ArrayList<Destinacija> ld = dao.selectDestinacija();
ArrayList<Putovanje> lp = dao.putovanje();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>DESTINACIJE</title>
<link rel="stylesheet" type="text/css" href="still.css">
</head>

<body background-image: url(https://images.unsplash.com/photo-1502920514313-52581002a659?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8MjF8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60) >
	<div class="content">
	<p>Korisnik: ${sessionScope.korisnik.ime}  ${sessionScope.korisnik.prezime}</p>
	<p> <a href="Putovanja?action=Logout">Povratak na stranu logovanja</a></p>
       
	<h1>PODACI O DESTINACIJAMA</h1>
    
	<form action="Putovanja" method="post">
		
	</form>

	<p>${requestScope.msg}</p>
	<br>
	<li>
	<table class="center" border="1px solid #004080" border-style: outset>
		<thead>
			<tr>
				<th><font face="Verdana" size="2"color #400080>ID</th>
				<th><font face="Verdana" size="2"color #400080>NAZIV DESTINACIJE</th>
				
			</tr>
		</thead>
		<tbody>
			<%
			for (Destinacija pom : ld) {
			%>
			<tr>
				<td><font face="Verdana" color #400080><%=pom.getId_destinacije()%></td>
				<td><font face="Verdana" color #400080><%=pom.getNaziv_destinacije()%></td>
				
			</tr>
			<%
			}
			%>

		</tbody>
	</table>
  </li>
	</div>
</body>
</html>

<%
} else {
response.sendRedirect("index11.jsp");
}
%>
