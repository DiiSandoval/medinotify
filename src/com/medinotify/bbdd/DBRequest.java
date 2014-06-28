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
				+ idUser;
		try {
			DBConnection.crearConexion();
			ResultSet rs = con.executeQuery(q);
			medicinas = new ArrayList<Medicina>();
			while (rs.next()) {
				medicinas.add(new Medicina(rs.getInt("id"), rs
						.getString("nombre"), rs.getString("funcion"), rs
						.getString("comentario"), rs.getString("metodo")));
			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}
		return medicinas;
	}

	public static List<Dosis> getAllDosis(int idUser) {
		List<Dosis> dosis = null;
		Dosis d = null;
		try {
			DBConnection.crearConexion();
			String q = "SELECT * from Dosis where id_user = '" + idUser + "'";
			ResultSet rs = con.executeQuery(q);
			dosis = new ArrayList<Dosis>();
			while (rs.next()) {
				d = new Dosis(rs.getString("cantidad"),
						rs.getString("frecuencia"), rs.getString("fecha"),
						rs.getString("tomado"));
				d.setId_med(rs.getInt("id_med"));
				dosis.add(d);
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

	public Medicina addMedicine(int idUser, String nombre, String funcion,
			String comentario, String metodo) {
		Medicina m = null;
		try {
			DBConnection.crearConexion();
			String q = "INSERT INTO Medicina" + "(nombre,"
					+ "funcion,comentario,metodo) " + "VALUES ('" + nombre
					+ "','" + funcion + "','" + comentario + "','" + metodo
					+ "')";
			con.executeUpdate(q);
			m = new Medicina(-1, nombre, funcion, comentario, metodo);

			insertInBotiquin(idUser, nombre, funcion, comentario, metodo);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}

		return m;
	}

	private void insertInBotiquin(int idUser, String nombre, String funcion,
			String comentario, String metodo) {

		String q = "SELECT id from Medicina m where nombre='" + nombre
				+ "' and funcion='" + funcion + "' and metodo='" + metodo + "'";
		try {
			DBConnection.crearConexion();
			ResultSet rs = con.executeQuery(q);
			int id = -1;
			while (rs.next()) {
				id = rs.getInt("id");
			}
			if (id != -1) {
				q = "INSERT INTO Botiquin" + "(id_user," + "id_med) "
						+ "VALUES ('" + idUser + "','" + id + "')";
				con.executeUpdate(q);
			}

			rs.close();
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

	public Dosis addDosis(int idUser, int idMed, String cantidad,
			String frecuencia, String fecha) {
		Dosis d = null;
		try {
			DBConnection.crearConexion();
			String q = "INSERT INTO Dosis" + "(ID_USER,ID_MED,"
					+ "CANTIDAD,FRECUENCIA,fecha,tomado) " + "VALUES ('"
					+ idUser + "','" + idMed + "','" + cantidad + "','"
					+ frecuencia + "','" + fecha + "','false')";
			con.executeUpdate(q);
			d = new Dosis(cantidad, frecuencia, fecha, "false");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}
		return d;
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

	public void tomarDosis(int iduser, String fechaTomada, String cantidad) {
		try {
			DBConnection.crearConexion();
			String q = "update Dosis set tomado='true' where id_user= '"
					+ iduser + "' and fecha='" + fechaTomada
					+ "' and cantidad='" + cantidad + "'";
			con.executeUpdate(q);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}
	}

}
