package com.medinotify.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.medinotify.model.Dosis;
import com.medinotify.model.Medicina;
import com.medinotify.model.Usuario;

public class DBRequest extends DBConnection {

	private static DBConnection con = new DBConnection();
	private static Connection conexion;
	private static String SQLGetAllMedicines = "SELECT m.* from medicina m, botiquin b, usuario u  where u.id=b.id_user and m.id=b.id_med and u.id=?";
	private static String SQLGetAllDosis = "SELECT * from dosis where idUsuario=? and fecha_desde <= ? and fecha_hasta>=?";
	private static String SQLLogin = "SELECT * from Usuario where nick=? and password=?";
	private static String SQLRegistrarUsuario = "INSERT INTO USUARIO"
			+ "(NICK,NOMBRE,"
			+ "APELLIDOS,SEXO,fechaNacimiento,email,password) "
			+ "VALUES (?,?,?,?,?,?,?)";
	private static String SQLAddMedicine = "INSERT INTO medicina" + "(nombre,"
			+ "funcion,comentario,code,metodo) " + "VALUES (?,?,?,?,?,?)";
	private static String SQL_CODE = "select max id from medicina";
	private static String SQLAddDosis = "INSERT INTO DOSIS"
			+ "(ID_USER,ID_MED,"
			+ "CANTIDAD,FRECUENCIA,fecha,tomado) "
			+ "VALUES (?,?,?,?,?,false)";
	private static String SQL_TomarDosis = "update dosis set tomado=true where id_user=? and id_med=?";
	
	
	
	public Usuario login(String nick, String password) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Usuario usuario = null;
		try {
			conexion = DBConnection.crearConexion();
			ps = conexion.prepareStatement(SQLLogin);
			ps.setString(1, nick);
			ps.setString(2, password);
			rs = ps.executeQuery();
			while (rs.next()) {
				usuario = new Usuario(rs.getString("nick"),
						rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("sexo"), rs.getString("fechaNacimiento"),
						rs.getString("email"),
						rs.getString("password"));
			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}
		return usuario;
	}

	public static List<Medicina> getAllMedicines(Long idUsuario) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Medicina> medicinas = null;
		try {
			conexion = DBConnection.crearConexion();
			ps = conexion.prepareStatement(SQLGetAllMedicines);
			medicinas = new ArrayList<Medicina>();
			ps.setLong(1, idUsuario);
			rs = ps.executeQuery();
			while (rs.next()) {
				medicinas.add(new Medicina(rs.getString("nombre"), rs
						.getString("funcion"), rs.getString("comentario"), rs
						.getString("code"),rs.getString("metodo")));
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Dosis> dosis = null;
		try {
			conexion = DBConnection.crearConexion();
			ps = conexion.prepareStatement(SQLGetAllDosis);
			dosis = new ArrayList<Dosis>();
			ps.setLong(1, idUsuario);
			rs = ps.executeQuery();
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		Usuario usuario = null;
		try {
			conexion = DBConnection.crearConexion();
			ps = conexion.prepareStatement(SQLRegistrarUsuario);
			ps.setString(1, nombreUsuario);
			ps.setString(2, nombre);
			ps.setString(3, apellidos);
			ps.setString(4, sexo);
			ps.setString(5, fechaNacimiento);
			ps.setString(6, email);
			ps.setString(7, password);
			rs = ps.executeQuery();
			while (rs.next()) {
				usuario = new Usuario(rs.getString("nombreUsuario"),
						rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("sexo"), rs.getString("fechaNacimiento"),
						rs.getString("email"),
						rs.getString("password"));
			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}
		return usuario;
	}

	public void addMedicine(String nombre, String funcion, String comentario,String metodo) {
		PreparedStatement ps = null;
		try {
			conexion = DBConnection.crearConexion();
			ps = conexion.prepareStatement(SQLAddMedicine);
			ps.setString(1, nombre);
			ps.setString(2, funcion);
			ps.setString(3, comentario);
			ps.setString(4, getCode());
			ps.setString(5,metodo);

			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}
	}

	private String getCode() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		String code = null;
		try {
			pst = conexion.prepareStatement(SQL_CODE);
			rs = pst.executeQuery();
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
		PreparedStatement ps = null;
		try {
			conexion = DBConnection.crearConexion();
			ps = conexion.prepareStatement(SQLAddDosis);
			ps.setLong(1, id_usuario);
			ps.setLong(2, id_med);
			ps.setString(3, cantidad);
			ps.setString(4, fecha);

			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}
	}

	public void tomarDosis(Long id_usuario, Long id_med) {
		PreparedStatement ps = null;
		try {
			conexion = DBConnection.crearConexion();
			ps = conexion.prepareStatement(SQL_TomarDosis);
			ps.setLong(1, id_usuario);
			ps.setLong(2, id_med);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}
	}

}
