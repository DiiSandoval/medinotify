package com.medinotify.activity.calendario;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.medinotify.R;

public class AddMedicineActivity extends Activity {

	private Button btAccept = null;
	private EditText txtNombre = null;
	private EditText txtFuncion = null;
	private EditText txtComentarios = null;
	private int idUser = 0;
	//private ServiceFactory serviceFactory = null;
	SharedPreferences settings = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_medicine);
		txtNombre = (EditText) findViewById(R.id.editTextName);
		txtFuncion = (EditText) findViewById(R.id.editTextFuncion);
		txtComentarios = (EditText) findViewById(R.id.editTextComentarios);
		//serviceFactory = new MySQLServiceFactory(this);
		settings = getSharedPreferences("userNotyMedify", MODE_PRIVATE);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			idUser = extras.getInt("idUser");
		}
		btAccept = (Button) findViewById(R.id.btAceptt);
		btAccept.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pressAceptar();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_medicine, menu);
		return true;
	}

	public void pressAceptar() {
//		serviceFactory.getMedicineService().anadirMedicina(String.valueOf(idUser),
//				txtNombre.getText().toString(),
//				txtFuncion.getText().toString(),
//				txtComentarios.getText().toString());
	}
}
