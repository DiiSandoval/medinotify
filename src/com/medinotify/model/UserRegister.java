package com.medinotify.model;

import android.widget.CheckBox;
import android.widget.EditText;

public class UserRegister {

	private EditText nombre;
	private EditText apellidos;
	private CheckBox sexoHombre;
	private CheckBox sexoMujer;
	private CheckBox diabeticoSI;
	private CheckBox diabeticoNO;
	private EditText nick;
	private EditText fechaNacimiento;
	private EditText password;
	private EditText repiteContrasena;
	private EditText email;

	public UserRegister(EditText nombre, EditText apellidos,
			CheckBox sexoHombre, CheckBox sexoMujer, CheckBox diabeticoSI,
			CheckBox diabeticoNO, EditText nombreUsuario,
			EditText fechaNacimiento, EditText contrasena,
			EditText repiteContrasena, EditText email) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.sexoHombre = sexoHombre;
		this.sexoMujer = sexoMujer;
		this.diabeticoSI = diabeticoSI;
		this.diabeticoNO = diabeticoNO;
		this.nick = nombreUsuario;
		this.fechaNacimiento = fechaNacimiento;
		this.password = contrasena;
		this.repiteContrasena = repiteContrasena;
		this.email = email;
	}

	public CheckBox getDiabeticoSI() {
		return diabeticoSI;
	}

	public void setDiabeticoSI(CheckBox diabeticoSI) {
		this.diabeticoSI = diabeticoSI;
	}

	public CheckBox getDiabeticoNO() {
		return diabeticoNO;
	}

	public void setDiabeticoNO(CheckBox diabeticoNO) {
		this.diabeticoNO = diabeticoNO;
	}


	public EditText getNombre() {
		return nombre;
	}

	public void setNombre(EditText nombre) {
		this.nombre = nombre;
	}

	public EditText getApellidos() {
		return apellidos;
	}

	public void setApellidos(EditText apellidos) {
		this.apellidos = apellidos;
	}

	public CheckBox getSexoHombre() {
		return sexoHombre;
	}

	public void setSexoHombre(CheckBox sexoHombre) {
		this.sexoHombre = sexoHombre;
	}

	public CheckBox getSexoMujer() {
		return sexoMujer;
	}

	public void setSexoMujer(CheckBox sexoMujer) {
		this.sexoMujer = sexoMujer;
	}

	public EditText getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(EditText fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public EditText getNick() {
		return nick;
	}

	public void setNick(EditText nick) {
		this.nick = nick;
	}

	public EditText getPassword() {
		return password;
	}

	public void setPassword(EditText password) {
		this.password = password;
	}

	public EditText getRepiteContrasena() {
		return repiteContrasena;
	}

	public void setRepiteContrasena(EditText repiteContrasena) {
		this.repiteContrasena = repiteContrasena;
	}

	public EditText getEmail() {
		return email;
	}

	public void setEmail(EditText email) {
		this.email = email;
	}

}
