package control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO;
import model.Destinacija;
import model.Korisnik;
import model.Putovanje;
import model.TipPrevoza;


@WebServlet("/Putovanja")
public class PutovanjeKontrola extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PutovanjeKontrola() {
        super();
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ids = request.getParameter("id");

		String action = request.getParameter("action");
		if (action != null && action.trim().length() > 0) {
			switch (action) {
			case "Delete":

				DAO dao = new DAO();

				try {
					int id = Integer.parseInt(ids);
					dao.brisanjePutovanja(id);
					request.setAttribute("msg", "USPESNO STE IZBRISALI PUTOVANJE id: " + id);
					request.getRequestDispatcher("putovanje11.jsp").forward(request, response);
				} catch (Exception e) {
					request.setAttribute("msg", "DOSLO JE DO GRESKE!");
					request.getRequestDispatcher("putovanje11.jsp").forward(request, response);
				}
				break;
			case "Logout":
				request.getSession().invalidate();
				response.sendRedirect("index11.jsp");
			}

		} else {
			response.getWriter().println("GRESKA");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DAO dao = new DAO();

		String action = request.getParameter("action");
		if (action != null && action.trim().length() > 0) {
			switch (action) {
			case "Logovanje":
				String username = request.getParameter("username");
				String password = request.getParameter("password");

				if (username != null && username.trim().length() > 0 && password != null
						&& password.trim().length() > 0) {
					Korisnik korisnik = dao.selecKorisnikByIdAndPassword(username, password);

					if (korisnik != null) {
						request.getSession().setAttribute("korisnik", korisnik);
						response.sendRedirect("putovanje11.jsp");
					} else {
						request.setAttribute("msg", "NEISPRAVNO KORISNICKO IME ILI LOZINKA");
						request.getRequestDispatcher("index11.jsp").forward(request, response);
					}
				} else {
					request.setAttribute("msg", "MORATE UNETI SVA POLJA!");
					request.getRequestDispatcher("index11.jsp").forward(request, response);
				}
				break;
			case "Dodaj":
				String id_korisnika = request.getParameter("imePrezime");
				String id_destinacije = request.getParameter("destinacija");
				String datumPolaska = request.getParameter("datum_polaska");
				String datum_povratka = request.getParameter("datum_povratka");
				String duzina = request.getParameter("duzina");
				String cena = request.getParameter("cena");
				String prevoz = request.getParameter("prevoz");

				if (id_korisnika != null && id_korisnika.trim().length() > 0 && id_destinacije != null
						&& id_destinacije.trim().length() > 0 && datumPolaska != null
						&& datumPolaska.trim().length() > 0 && datum_povratka != null
						&& datum_povratka.trim().length() > 0 && duzina != null && duzina.trim().length() > 0
						&& cena != null && cena.trim().length() > 0 && prevoz != null && prevoz.trim().length() > 0) {

					try {
						int idKorisnika = Integer.parseInt(id_korisnika);
						int idDestinacije = Integer.parseInt(id_destinacije);
						Date dtPolaska = new SimpleDateFormat("yyyy-MM-dd").parse(datumPolaska);
						Date dtPovratka = new SimpleDateFormat("yyyy-MM-dd").parse(datum_povratka);
						if(!dtPovratka.before(dtPolaska)) {
						int duzinaPuta = Integer.parseInt(duzina);
						double cenaD = Double.parseDouble(cena);
						Korisnik k = new Korisnik();
						k.setId_korisnik(idKorisnika);
						Destinacija d = new Destinacija();
						d.setId_destinacije(idDestinacije);
						TipPrevoza vp = new TipPrevoza();
						vp.setId_tip_prevoza(dao.selectIdPrevoza(prevoz));
						vp.setVrsta_prevoza(prevoz);

						Putovanje p = new Putovanje(0, k, d, dtPolaska, dtPovratka, duzinaPuta, cenaD, vp);
						int idPutovanja = dao.insertPutovanjeID(p);

						request.setAttribute("msg", "USPESNO STE UNELI PUTOBANJE");
						request.getRequestDispatcher("putovanje11.jsp").forward(request, response);
						} else {
							request.setAttribute("msg", "POGRESAN DATUM");
							request.getRequestDispatcher("putovanje11.jsp").forward(request, response);
						}

					} catch (Exception e) {
						request.setAttribute("msg", "NISPRAVAN FORMAT PODATAKA. POKUSAJTE PONOVO");
						request.getRequestDispatcher("putovanje11.jsp").forward(request, response);
					}

				} else {
					request.setAttribute("msg", "MORATE UNETI SVA POLJA!");
					request.getRequestDispatcher("putovanje11.jsp").forward(request, response);
				}
				break;
			case "Destinacije":
				String username1 = request.getParameter("username");
				String password1 = request.getParameter("password");

				if (username1 != null && username1.trim().length() > 0 && password1 != null
						&& password1.trim().length() > 0) {
					Korisnik korisnik = dao.selecKorisnikByIdAndPassword(username1, password1);

					if (korisnik != null) {
						request.getSession().setAttribute("korisnik", korisnik);
						response.sendRedirect("index2.jsp");
					} else {
						request.setAttribute("msg", "NEISPRAVNO KORISNICKO IME ILI LOZINKA");
						request.getRequestDispatcher("index11.jsp").forward(request, response);
					}
				} else {
					request.setAttribute("msg", "MORATE UNETI SVA POLJA!");
					request.getRequestDispatcher("index11.jsp").forward(request, response);
				}
			}
		} else {
			request.setAttribute("msg", "GRESKA!");
			request.getRequestDispatcher("index11.jsp").forward(request, response);
		}
	}

}
