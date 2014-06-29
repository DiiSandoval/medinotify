package com.medinotify.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.medinotify.R;
import com.medinotify.business.Business;
import com.medinotify.business.BusinessImpl;
import com.medinotify.model.Session;
import com.medinotify.model.Usuario;
import com.medinotify.utility.LaunchActivity;

public class RegistroActivity extends Activity {

	private EditText nombre = null;
	private EditText apellidos = null;
	private CheckBox hombre = null;
	private CheckBox mujer = null;
	private EditText fechaNacimiento = null;
	private EditText nombreUsuario = null;
	private EditText contrasena = null;
	private EditText repiteContrana = null;
	private EditText email = null;

	private Business business = null;

	private Button botonAceptar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);

		initialize();

		botonAceptar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				register();
			}

		});

	}

	private void register() {
		Usuario usuario = business.register(nombreUsuario.getText().toString(),
				nombre.getText().toString(), apellidos.getText().toString(),
				hombre.isChecked(), mujer.isChecked(), fechaNacimiento
						.getText().toString(), email.getText().toString(),
				contrasena.getText().toString(), repiteContrana.getText()
						.toString());
		if (usuario != null) {
			Session.getInstance().setUsuarioActual(usuario);
			LaunchActivity.launchLoginActivity(RegistroActivity.this);
		} else {
			Toast.makeText(
					getApplicationContext(),
					"Datos incorrectos/incompletos o puede que el usuario introducido ya exista.",
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.registro, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_registro,
					container, false);
			return rootView;
		}
	}

	private void initialize() {
		nombre = (EditText) findViewById(R.id.editTextNombre);
		apellidos = (EditText) findViewById(R.id.editTextApellidos);
		hombre = (CheckBox) findViewById(R.id.checkBoxHombre);
		mujer = (CheckBox) findViewById(R.id.checkBoxMujer);
		fechaNacimiento = (EditText) findViewById(R.id.editTextFechaNacimiento);
		nombreUsuario = (EditText) findViewById(R.id.editTextNombreUsuario);
		contrasena = (EditText) findViewById(R.id.editTextContrasena);
		repiteContrana = (EditText) findViewById(R.id.editTextRepiteContrasena);
		email = (EditText) findViewById(R.id.editTextEmail);

		botonAceptar = (Button) findViewById(R.id.buttonAceptarRegister);
		business = new BusinessImpl();

	}

}
