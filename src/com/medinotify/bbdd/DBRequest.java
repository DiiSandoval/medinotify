package com.medinotify.bbdd;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.medinotify.model.Dosis;
import com.medinotify.model.Medicina;
import com.medinotify.model.Usuario;

public class DBRequest extends DBConnection {

	private static DBConnection con = new DBConnection();

	public static Usuario login(String nick, String password) {
		Usuario usuario = null;
		String q = "SELECT * from Usuario where nick = '" + nick
				+ "' and password = '" + password + "'";
		try {
			DBConnection.crearConexion();
			ResultSet rs = con.executeQuery(q);
			while (rs.next()) {
				usuario = new Usuario(rs.getString("nick"),
						rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("sexo"), rs.getString("fechaNacimiento"),
						rs.getString("email"), rs.getString("password"));
				usuario.setId(rs.getInt("id"));
			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}
		return usuario;
	}

	public static List<Medicina> getAllMedicines(int idUser) {
		List<Medicina> medicinas = null;
		String q = "SELECT m.* from Medicina m, Botiquin b, Usuario u  where u.id=b.id_user and m.id=b.id_med and u.id = "
				+ idUser ;
		try {
			DBConnection.crearConexion();
			ResultSet rs = con.executeQuery(q);
			medicinas = new ArrayList<Medicina>();
			while (rs.next()) {
				medicinas.add(new Medicina(rs.getString("nombre"), rs
						.getString("funcion"), rs.getString("comentario"), rs
						.getString("code"), rs.getString("metodo")));
			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}
		return medicinas;
	}
	
	public static List<Dosis> getAllDosis(Long idUsuario) {
		List<Dosis> dosis = null;
		try {
			DBConnection.crearConexion();
			String q = "SELECT * from dosis where idUsuario = '" + idUsuario
					+ "'";
			ResultSet rs = con.executeQuery(q);
			dosis = new ArrayList<Dosis>();
			while (rs.next()) {
				dosis.add(new Dosis(rs.getString("cantidad"), rs
						.getString("funcion"), rs.getString("frecuencia"), rs
						.getString("fecha"), rs.getString("tomado")));
			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}
		return dosis;
	}

	public static Usuario register(String nombreUsuario, String nombre,
			String apellidos, String sexo, String fechaNacimiento,
			String email, String password) {
		Usuario usuario = null;
		try {
			DBConnection.crearConexion();
			String q = "INSERT INTO Usuario" + "(NICK,NOMBRE,"
					+ "APELLIDOS,SEXO,fechaNacimiento,email,password) "
					+ "VALUES ('" + nombreUsuario + "','" + nombre + "','"
					+ apellidos + "','" + sexo + "','" + fechaNacimiento
					+ "','" + email + "','" + password + "')";
			con.executeUpdate(q);
			usuario = new Usuario(nombreUsuario, nombre, apellidos, sexo,
					fechaNacimiento, email, password);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}
		return usuario;
	}

	public void addMedicine(String nombre, String funcion, String comentario,
			String metodo) {
		try {
			DBConnection.crearConexion();
			String q = "INSERT INTO medicina" + "(nombre,"
					+ "funcion,comentario,metodo) " + "VALUES ('" + nombre
					+ "','" + funcion + "','" + comentario + "','" + metodo
					+ "')";
			con.executeUpdate(q);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}
	}

	private String getCode() {
		String code = null;
		try {
			DBConnection.crearConexion();
			String q = "select max id from medicina";
			ResultSet rs = con.executeQuery(q);
			while (rs.next()) {
				code = rs.getString(1);
			}
			if (code != null) {
				code = "MED" + code;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}
		return code;
	}

	public void addDosis(Long id_usuario, Long id_med, String cantidad,
			String fecha) {
		try {
			DBConnection.crearConexion();
			String q = "INSERT INTO DOSIS" + "(ID_USER,ID_MED,"
					+ "CANTIDAD,FRECUENCIA,fecha) " + "VALUES ('" + id_usuario
					+ "','" + id_med + "','" + cantidad + "','" + fecha
					+ "',false)";
			con.executeUpdate(q);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}
	}

	public void tomarDosis(Long id_usuario, Long id_med) {
		try {
			DBConnection.crearConexion();
			String q = "update dosis set tomado=true where id_user= '"
					+ id_usuario + "' and id_med= '" + id_med + "'";
			ResultSet rs = con.executeQuery(q);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}
	}

	public boolean existUser(String nombreUsuario) {
		try {
			DBConnection.crearConexion();
			String q = "SELECT * from Usuario where nick= '" + nombreUsuario
					+ "'";
			int n = con.executeUpdate(q);
			return n == 1;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}
		return false;
	}

}
