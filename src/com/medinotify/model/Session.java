package com.medinotify.model;

import java.util.List;


public class Session {
	private static Session INSTANCE = null;
	private Usuario usuarioActual;
	private List<Medicina> medicinas;
	private Medicina medicinaEscogida;

	public List<Medicina> getMedicinas() {
		return medicinas;
	}

	public void setMedicinas(List<Medicina> medicinas) {
		this.medicinas = medicinas;
	}

	public Medicina getMedicinaEscogida() {
		return medicinaEscogida;
	}

	public void setMedicinaEscogida(Medicina medicinaEscogida) {
		this.medicinaEscogida = medicinaEscogida;
	}

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
	
	public Medicina getMedicinaByNombre(String nombre){
		for (Medicina med : getInstance().getMedicinas()) {
			if(med.getNombre().equalsIgnoreCase(nombre))
				return med;
		}
		return null;
	}
}
