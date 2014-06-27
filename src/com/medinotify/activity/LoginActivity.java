package com.medinotify.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.service.textservice.SpellCheckerService.Session;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.medinotify.R;
import com.medinotify.activity.calendario.CalendarActivity;
import com.medinotify.business.Business;
import com.medinotify.business.BusinessImpl;
import com.medinotify.model.*;

public class LoginActivity extends Activity {

	private EditText nameUser = null;
	private EditText passwordUser = null;
	private Button buttonAccept = null;
	private Button buttonRegister = null;
	// private ServiceFactory serviceFactory = null;

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
				// LaunchActivity.launchRegisterActivity(LoginActivity.this);
				Intent intent = new Intent(LoginActivity.this,
						RegistroActivity.class);
				startActivity(intent);
			}
		});

	}

	private void login() {
		// serviceFactory.getUserService().login(
		// nameUser.getText().toString(),
		// passwordUser.getText().toString());
		Usuario usuario = business.login(nameUser.getText().toString(),
				passwordUser.getText().toString());

		if (usuario != null) {
			com.medinotify.model.Session.getInstance()
					.setUsuarioActual(usuario);
			Intent intent = new Intent(LoginActivity.this,
					CalendarActivity.class);
			startActivity(intent);
			Toast.makeText(getApplicationContext(), "s", Toast.LENGTH_SHORT)
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

		// serviceFactory = new MySQLServiceFactory(this);

		settings = getSharedPreferences("userNotyMedify", MODE_PRIVATE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
