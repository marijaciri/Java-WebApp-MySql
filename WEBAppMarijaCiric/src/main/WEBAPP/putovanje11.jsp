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
<title>PUTOVANJA</title>
<link rel="stylesheet" type="text/css" href="putovanje11.css">
</head>

<body background-image: url(https://images.unsplash.com/photo-1502920514313-52581002a659?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8MjF8fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60)>
	<div class="content">
	<p>Korisnik: ${sessionScope.korisnik.ime}  ${sessionScope.korisnik.prezime}</p>
	<p></p>

	<br>
	<a href="Putovanja?action=Logout">Povratak na stranu logovanja</a>

	<h1>PODACI O PUTOVANJU</h1>

	<form action="Putovanja" method="post">
		<ul>
			<li><label for="imePrezime">IME I PREZIME</label> <select
				name="imePrezime">
					<%
					for (Korisnik k : lk) {
					%>
					<option value="<%=k.getId_korisnik()%>"><%=k.getIme() + " " + k.getPrezime()%></option>
					<%
					}
					%>
			</select></li>
			<li><label for="destinacija">DESTINACIJA</label> <select
				name="destinacija">
					<%
					for (Destinacija d : ld) {
					%>
					<option value="<%=d.getId_destinacije()%>"><%=d.getNaziv_destinacije()%></option>
					<%
					}
					%>
			</select></li>
			<li><label for="datum_polaska">DATUM POLASKA</label> <input
				type="date" name="datum_polaska"></li>
			<li><label for="datum_povratka">DATUM POVRATKA</label> <input
				type="date" name="datum_povratka"><br></li>
			<li><label for="duzina">DUZINA PUTA</label> <input type="number"
				name="duzina"></li>
			<li><label for="cena">CENA KARTE</label> <input type="number"
				name="cena"></li>
			<li class="radioB"><label for="prevoz">PREVOZ</label><br> <label
				for="avion">AVION</label> <input type="radio" name="prevoz"
				value="avion" id="izbor"> <label for="autobus">AUTOBUS</label>
				<input type="radio" name="prevoz" value="autobus" id="izbor">
			</li>
		</ul>
		 <button input type="submit" name="action" value="Dodaj">DODAJ</button>
	</form>

	<p>${requestScope.msg}</p>
	<br>
	<table border="1px solid #004080" border-style: outset>
		<thead>
			<tr>
				<th>ID</th>
				<th><font face="Verdana" size="2" color #400080>IME</th>
				<th><font face="Verdana" size="2" color #400080>PREZIME</th>
				<th><font face="Verdana" size="2" color #400080>DESTINACIJA</th>
				<th><font face="Verdana" size="2" color #400080>POLAZAK</th>
				<th><font face="Verdana" size="2" color #400080>POVRATAK</th>
				<th><font face="Verdana" size="2" color #400080>DUZINA</th>
				<th><font face="Verdana" size="2" color #400080>CENA</th>
				<th><font face="Verdana" size="2" color #400080>PREVOZ</th>
				<th><font face="Verdana" size="2" color #400080>AKCIJA</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (Putovanje pom : lp) {
			%>
			<tr>
				<td><font face="Verdana" color #400080><%=pom.getId_putovanja()%></td>
				<td><font face="Verdana" color #400080><%=pom.getKorisnik().getIme()%></td>
				<td><font face="Verdana" color #400080><%=pom.getKorisnik().getPrezime()%></td>
				<td><font face="Verdana" color #400080><%=pom.getDestinacija().getNaziv_destinacije()%></td>
				<td><font face="Verdana" color #400080><%=pom.getPolazak()%></td>
				<td><font face="Verdana" color #400080><%=pom.getPovratak()%></td>
				<td><font face="Verdana" color #400080 ><%=pom.getDuzina()%></td>
				<td><font face="Verdana" color #400080><%=pom.getCena()%></td>
				<td><font face="Verdana" color #400080 ><%=pom.getTipprevoza().getVrsta_prevoza()%></td>
				<td name="id" value="<%=pom.getId_putovanja()%>"><a
					href="Putovanja?action=Delete&id=<%=pom.getId_putovanja()%>">OBRISI</a></td>
			</tr>
			<%
			}
			%>

		</tbody>
	</table>

	</div>
</body>
</html>

<%
} else {
response.sendRedirect("index11.jsp");
}
%>
