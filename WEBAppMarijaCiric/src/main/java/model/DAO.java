package model; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.jdbc.Statement;

import java.util.ArrayList;
import java.util.Date;
public class DAO {
      private DataSource ds;

// DEFINICIJA KONEKCIONIH STRINGOVA
	private static String SELECTKORISNIK = "SELECT * FROM korisnici";
	private static String SELECTDESTINACIJA="SELECT * FROM `destinacija`";
	private static String SELECTPREVOZ="SELECT * FROM `tip_prevoza`";
	private static String SELECTIDPREVOZA="SELECT id_tip_prevoza FROM tip_prevoza WHERE vrsta_prevoza=?";
	private static String SELECTEDKORISNIKIDLOZINKA = "SELECT* FROM korisnici WHERE korisnicko_ime=? and lozinka=?";
	private static String Putovanje = "SELECT putovanja.id_putovanja, korisnici.id_korisnika, korisnici.ime, korisnici.prezime, destinacija.id_destinacija, destinacija.naziv_destinacije, putovanja.datum_polaska, putovanja.datum_povratka, putovanja.duzina, putovanja.cena, tip_prevoza.id_tip_prevoza, tip_prevoza.vrsta_prevoza\r\n"
			+ "FROM putovanja join korisnici on putovanja.id_korisnika=korisnici.id_korisnika\r\n"
			+ "JOIN destinacija ON putovanja.id_destinacija=destinacija.id_destinacija\r\n"
			+ "JOIN tip_prevoza on putovanja.id_tip_prevoza= tip_prevoza.id_tip_prevoza";
	private static String BrisanjePutovanja = "DELETE FROM `putovanja` WHERE id_putovanja = ?";
	private static String InsertPutovanja = "INSERT INTO `putovanja`( `id_korisnika`, `id_destinacija`, `datum_polaska`, `datum_povratka`, `duzina`, `cena`, `id_tip_prevoza`) \r\n"
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	public DAO(){
	try {
		InitialContext cxt = new InitialContext();
		if ( cxt == null ) { 
		} 
		ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/mysql" ); 
		if ( ds == null ) { 
		} 		
		} catch (NamingException e) {
		}
	}
	 
	public ArrayList<Korisnik> selectKorisnik(){
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Korisnik> lo = new ArrayList<Korisnik>();
		
		 try {
			 connect = ds.getConnection();
			 preparedStatement = connect.prepareStatement(SELECTKORISNIK);
			 preparedStatement.execute();

				resultSet = preparedStatement.getResultSet();

				while(resultSet.next()) {
					int id = resultSet.getInt("id_korisnika");
					String ime = resultSet.getString("ime");
					String prezime = resultSet.getString("prezime");
					String korisnickoIme = resultSet.getString("korisnicko_ime");
					String lozinka = resultSet.getString("lozinka");
					Korisnik k = new Korisnik(id, ime, prezime, korisnickoIme, lozinka);
					lo.add(k);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return lo; 
		}
	public Korisnik selecKorisnikByIdAndPassword(String korIme, String lozinka){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Korisnik> lo = new ArrayList<Korisnik>();
		Korisnik k = null;
				
            try {
			con = ds.getConnection();
			pstm = con.prepareStatement(SELECTEDKORISNIKIDLOZINKA);
			pstm.setString(1, korIme);
			pstm.setString(2, lozinka);
			pstm.execute();

			rs = pstm.getResultSet();

			while(rs.next()) {
				int id = rs.getInt("id_korisnika");
				String ime = rs.getString("ime");
				String prezime = rs.getString("prezime");
				String korisnickoIme = rs.getString("korisnicko_ime");
				String lozinka1 = rs.getString("lozinka");
				 k = new Korisnik(id, ime, prezime, korisnickoIme, lozinka1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return k; 
	}
	public ArrayList<Destinacija> selectDestinacija(){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Destinacija> lo = new ArrayList<Destinacija>();
		Destinacija d = null;
				
            try {
			con = ds.getConnection();
			pstm = con.prepareStatement(SELECTDESTINACIJA);
			pstm.execute();

			rs = pstm.getResultSet();

			while(rs.next()) {
				int id = rs.getInt("id_destinacija");
				String naziv = rs.getString("naziv_destinacije");
				 d = new Destinacija(id, naziv);
				lo.add(d);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lo; 
	}
	public ArrayList<TipPrevoza> prevoz(){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<TipPrevoza> lo = new ArrayList<TipPrevoza>();
		TipPrevoza vp = null;
				
            try {
			con = ds.getConnection();
			pstm = con.prepareStatement(SELECTPREVOZ);
			pstm.execute();

			rs = pstm.getResultSet();

			while(rs.next()) {
				int id = rs.getInt("id_tip_prevoza");
				String naziv = rs.getString("vrsta_prevoza");
				vp = new TipPrevoza(id, naziv);
				lo.add(vp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lo; 
	}
	
	public int selectIdPrevoza(String naziv){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Integer id = null;
		
				
            try {
			con = ds.getConnection();
			pstm = con.prepareStatement(SELECTIDPREVOZA);
			pstm.setString(1, naziv);
			pstm.execute();
			
			rs = pstm.getResultSet();
			
			if(rs.next()) {
				id = rs.getInt("id_tip_prevoza");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public ArrayList<Putovanje> putovanje(){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		ArrayList<Putovanje> lo = new ArrayList<Putovanje>();
		Putovanje put = null;
				
            try {
			con = ds.getConnection();
			pstm = con.prepareStatement(Putovanje);
			pstm.execute();

			resultSet = pstm.getResultSet();

			while(resultSet.next()) {
				int idPutovanja = resultSet.getInt("id_putovanja");
				
				int idKorisnika = resultSet.getInt("id_korisnika");
				String ime = resultSet.getString("ime");
				String prezime = resultSet.getString("prezime");
				Korisnik korisnik = new Korisnik();
				korisnik.setId_korisnik(idKorisnika);
				korisnik.setIme(ime);
				korisnik.setPrezime(prezime);
				
				int idDestinacije = resultSet.getInt("id_destinacija");
				String naziv = resultSet.getString("naziv_destinacije");
				Destinacija destinacija = new Destinacija(idDestinacije, naziv);
				
				Date vremePolaska = resultSet.getDate("datum_polaska");
				Date datumPovratka = resultSet.getDate("datum_povratka");
				int duzinaPuta = resultSet.getInt("duzina");
				double cena = resultSet.getDouble("cena");
				
				int idPrevoza = resultSet.getInt("id_tip_prevoza");
				String nazivPrevoza = resultSet.getString("vrsta_prevoza");
				TipPrevoza prevoz = new TipPrevoza(idPrevoza, nazivPrevoza);
				
				Putovanje novoPutovanje = new Putovanje(idPutovanja, korisnik, destinacija, vremePolaska, datumPovratka, duzinaPuta, cena, prevoz);
				lo.add(novoPutovanje);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lo; 
	}
	public void brisanjePutovanja (int id){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
            try {
			con = ds.getConnection();
			pstm = con.prepareStatement(BrisanjePutovanja);
			pstm.setInt(1, id);
			pstm.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void insertPutovanja (Putovanje p){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String datumPolaska = sdf.format(p.getPolazak());
		String datumPovratka = sdf.format(p.getPovratak());
				
            try {
			con = ds.getConnection();
			pstm = con.prepareStatement(InsertPutovanja);
			pstm.setInt(1, p.getKorisnik().getId_korisnik());
			pstm.setInt(2, p.getDestinacija().getId_destinacije());
			pstm.setString(3, datumPolaska);
			pstm.setString(4, datumPovratka);
			pstm.setInt(5, p.getDuzina());
			pstm.setDouble(6, p.getCena());
			pstm.setInt(7, p.getTipprevoza().getId_tip_prevoza());
			pstm.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int insertPutovanjeID (Putovanje p){
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Integer id = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String datumPolaska = sdf.format(p.getPolazak());
		String datumPovratka = sdf.format(p.getPovratak());
				
				
            try {
			con = ds.getConnection();
			pstm = con.prepareStatement(InsertPutovanja, Statement.RETURN_GENERATED_KEYS);
			pstm.setInt(1, p.getKorisnik().getId_korisnik());
			pstm.setInt(2, p.getDestinacija().getId_destinacije());
			pstm.setString(3, datumPolaska);
			pstm.setString(4, datumPovratka);
			pstm.setInt(5, p.getDuzina());
			pstm.setDouble(6, p.getCena());
			pstm.setInt(7, p.getTipprevoza().getId_tip_prevoza());
			pstm.execute();
			
			rs = pstm.getResultSet();
			ResultSet keys = pstm.getGeneratedKeys();
			keys.next();
			id = keys.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
}
