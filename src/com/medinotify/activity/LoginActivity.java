package com.medinotify.activity;

import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.medinotify.R;
import com.medinotify.business.Business;
import com.medinotify.business.BusinessImpl;
import com.medinotify.model.Dosis;
import com.medinotify.model.Medicina;
import com.medinotify.model.Usuario;
import com.medinotify.utility.LaunchActivity;

public class LoginActivity extends Activity {

	private EditText nameUser = null;
	private EditText passwordUser = null;
	private Button buttonAccept = null;
	private Button buttonRegister = null;

	private Business business = null;

	SharedPreferences settings = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		getActionBar().setTitle("MediNotify");

		initialize();

		buttonAccept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				login();
			}
		});

		buttonRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LaunchActivity.launchRegisterActivity(LoginActivity.this);
			}
		});

	}

	private void login() {
		Usuario usuario = business.login(nameUser.getText().toString(),
				passwordUser.getText().toString());

		if (usuario != null) {
			List<Medicina> meds = business.getAllMedicinas(usuario.getId());
			
			List<Dosis> dosis = business.getAllDosis(usuario.getId());
			com.medinotify.model.Session.getInstance()
			.setMedicinas(meds);
			usuario.setDosisAlmacenadas(dosis);
			com.medinotify.model.Session.getInstance()
			.setUsuarioActual(usuario);
			
			LaunchActivity.launchCalendarActivity(LoginActivity.this);
			Toast.makeText(getApplicationContext(), "Login correcto como " + usuario.getNick(), Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(getApplicationContext(), "Datos incorrectos",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void initialize() {
		nameUser = (EditText) findViewById(R.id.editText_User);
		passwordUser = (EditText) findViewById(R.id.editText_Pasword);
		buttonAccept = (Button) findViewById(R.id.button_Accept);
		buttonRegister = (Button) findViewById(R.id.button_register);
		business = new BusinessImpl();

		settings = getSharedPreferences("userNotyMedify", MODE_PRIVATE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
