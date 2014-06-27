package com.medinotify.model;


public class Medicina {
	private String nombre;
	private String funcion;
	private String comentario;
	private String code;
	private String metodo;

	public Medicina( String nombre, String funcion,
			String comentario,String code,String metodo) {
		super();
		this.nombre = nombre;
		this.funcion = funcion;
		this.comentario = comentario;
		this.code = code;
		this.metodo=metodo;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	
	
	
}
