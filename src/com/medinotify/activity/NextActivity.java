package com.medinotify.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.medinotify.R;
import com.medinotify.utility.LaunchActivity;

public class NextActivity extends Activity {

	private SharedPreferences settings = null;

	private TextView textoPrueba = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_next);

		getActionBar().setTitle("MediNotify");
		
		initialize();

		int idNombre = settings.getInt("user", 0);
		textoPrueba.setText(String.valueOf(idNombre));

	}

	private void initialize() {
		textoPrueba = (TextView) findViewById(R.id.textPrueba1);
		settings = getSharedPreferences("userNotyMedify", MODE_PRIVATE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.next, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.MnuOpc1:
			settings.edit().remove("user").commit();
			LaunchActivity.launchLoginActivity(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
