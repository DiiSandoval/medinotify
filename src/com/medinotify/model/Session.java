package com.medinotify.model;


public class Session {
	private static Session INSTANCE = null;
	private Usuario usuarioActual;

	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public void setUsuarioActual(Usuario usuarioActual) {
		this.usuarioActual = usuarioActual;
	}

	// Private constructor suppresses
	private Session() {
	}

	// creador sincronizado para protegerse de posibles problemas multi-hilo
	// otra prueba para evitar instanciación múltiple
	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Session();
		}
	}

	public static Session getInstance() {
		if (INSTANCE == null)
			createInstance();
		return INSTANCE;
	}
}
