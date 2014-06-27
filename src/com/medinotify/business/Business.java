package com.medinotify.business;

import com.medinotify.model.Usuario;

public interface Business {

	public Usuario login(String nombre, String apellidos);

	public Usuario register(String nombreUsuario, String nombre, String apellidos,
			boolean hombre, boolean mujer, String fechaNacimiento, String email,
			String password, String password2);
}
