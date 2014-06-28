package com.medinotify.business;

import java.util.List;

import com.medinotify.bbdd.DBRequest;
import com.medinotify.model.Dosis;
import com.medinotify.model.Medicina;
import com.medinotify.model.Usuario;

public class BusinessImpl implements Business {

	@Override
	public Usuario login(String nombre, String password) {
		if (nombre != "" && password != "")
			return new DBRequest().login(nombre, password);
		else
			return null;
	}

	@Override
	public Usuario register(String nombreUsuario, String nombre,
			String apellidos, boolean hombre, boolean mujer,
			String fechaNacimiento, String email, String password,
			String password2) {
		if (datosCorrectos(nombreUsuario, hombre, mujer, email, password,
				password2))
			return new DBRequest().register(nombreUsuario, nombre, apellidos,
					getSexo(hombre), fechaNacimiento, email, password);

		return null;
	}

	private boolean datosCorrectos(String nombreUsuario, boolean hombre,
			boolean mujer, String email, String password, String password2) {
		return hombre != mujer && !exist(nombreUsuario) && email.contains("@")
				&& password.equals(password2);
	}

	private boolean exist(String nombreUsuario) {
		// return new DBRequest().existUser(nombreUsuario);
		return false;
	}

	private String getSexo(boolean hombre) {
		return (!hombre) ? "M" : "H";
	}

	@Override
	public List<Medicina> getAllMedicinas(int idUser) {
		// TODO Auto-generated method stub
		return new DBRequest().getAllMedicines(idUser);
	}
	@Override
	public List<Dosis> getAllDosis(int idUser) {
		// TODO Auto-generated method stub
		return new DBRequest().getAllDosis(idUser);
	}

	@Override
	public Medicina addMedicine(int idUser, String nombre, String funcion,
			String comentario, String metodo) {
		if (isDatosCorrectos(nombre, funcion, metodo))
			return new DBRequest().addMedicine(idUser, nombre, funcion,
					comentario, metodo);
		return null;
	}

	private boolean isDatosCorrectos(String nombre, String funcion,
			String metodo) {
		return nombre != "" && funcion != "" && metodo != "";
	}

	@Override
	public Dosis addDosis(int idUser, int idMed, String cantidad,
			String frecuencia, String fecha) {
		return new DBRequest().addDosis(idUser, idMed, cantidad, frecuencia,
				fecha);
	}

	@Override
	public void tomarDosis(int iduser,String fechaTomada, String dosisString) {
		// TODO Auto-generated method stub
		String cantidad = getCantidad(dosisString);
		new DBRequest().tomarDosis(iduser,fechaTomada, cantidad);
	}

	private String getCantidad(String dosisString) {
		String[] parts = dosisString.split(" - ");
		String part1 = parts[2];
		String[] parts2 = part1.split("Cantidad:");
		return parts2[1];
	}



}
