package com.medinotify.activity.inutil;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.medinotify.R;

public class RegisterActivity extends Activity {


	private EditText nombre = null;
	private EditText apellidos = null;
	private CheckBox hombre = null;
	private CheckBox mujer = null;
	private CheckBox diabeticoSI = null;
	private CheckBox diabeticoNO = null;
	private EditText fechaNacimiento = null;
	private EditText nombreUsuario = null;
	private EditText contrasena = null;
	private EditText repiteContrana = null;
	private EditText email = null;

	private Button botonAceptar = null;

	//private ServiceFactory serviceFactory = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		getActionBar().setTitle("MediNotify");
		
		initialize();

		botonAceptar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				comprobarCampos();
			}

		});

	}

	// Aqui vendría genial un composite para la comprobación de los campos
	// de una manera mucho mas limpia
	// Posible mejora
	private void comprobarCampos() {

//		user = new UserRegister(nombre, apellidos, hombre, mujer, diabeticoSI,
//				diabeticoNO, nombreUsuario, fechaNacimiento, contrasena,
//				repiteContrana, email);

		//serviceFactory.getUserService().registerUser(user);

	}

	private void initialize() {
		nombre = (EditText) findViewById(R.id.editTextNombre);
		apellidos = (EditText) findViewById(R.id.editTextApellidos);
		hombre = (CheckBox) findViewById(R.id.checkBoxHombre);
		mujer = (CheckBox) findViewById(R.id.checkBoxMujer);
		diabeticoSI = (CheckBox) findViewById(R.id.checkBoxDiabeticoSI);
		diabeticoNO = (CheckBox) findViewById(R.id.checkBoxDiabeticoNO);
		fechaNacimiento = (EditText) findViewById(R.id.editTextFechaNacimiento);
		nombreUsuario = (EditText) findViewById(R.id.editTextNombreUsuario);
		contrasena = (EditText) findViewById(R.id.editTextContrasena);
		repiteContrana = (EditText) findViewById(R.id.editTextRepiteContrasena);
		email = (EditText) findViewById(R.id.editTextEmail);

		botonAceptar = (Button) findViewById(R.id.buttonAceptarRegister);

		//serviceFactory = new MySQLServiceFactory(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
