package com.medinotify.business;

import com.medinotify.bbdd.DBRequest;
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
		return hombre !=mujer && !exist(nombreUsuario)
				&& email.contains("@") && password.equals(password2);
	}

	private boolean exist(String nombreUsuario) {
		return new DBRequest().existUser(nombreUsuario);
	}

	private String getSexo(boolean hombre) {
		return (!hombre) ? "M" : "H";
	}
	
	

}
