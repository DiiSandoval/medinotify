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

}
