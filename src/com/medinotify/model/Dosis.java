package com.medinotify.model;



public class Dosis {

	private String cantidad;
	private String funcion;
	private String frecuencia;
	private String fecha;
	private String tomado;

	public Dosis(String cantidad,String funcion,
			String frecuencia, String fecha,String tomado) {
		this.cantidad = cantidad;
		this.funcion = funcion;
		this.frecuencia = frecuencia;
		this.fecha = fecha;
		this.tomado=tomado;
	}



	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	public String getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}



	public String getFecha() {
		return fecha;
	}



	public void setFecha(String fecha) {
		this.fecha = fecha;
	}



	public String getTomado() {
		return tomado;
	}



	public void setTomado(String tomado) {
		this.tomado = tomado;
	}


	

}
