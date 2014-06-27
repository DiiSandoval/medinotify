package com.medinotify.bbdd;


import java.sql.ResultSet;
import java.util.ArrayList;

import com.medinotify.model.Dosis;

public class DBHelper extends DBConnection {

	private static DBConnection con = new DBConnection();

	public static ArrayList<Dosis> getAllDosis() {

		ArrayList<Dosis> p = new ArrayList<Dosis>();
		String q = "SELECT * from Dosis";

		try {
			DBConnection.crearConexion();
			ResultSet rs = con.executeQuery(q);
			while (rs.next()) {
				p.add(new Dosis(rs.getString("id_user"), rs.getString("id_med"),
						rs.getString("cantidad"), rs.getString("frecuencia"), rs
								.getString("fecha")));
			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cerrarConexion();
		}
		return p;

	}

}
