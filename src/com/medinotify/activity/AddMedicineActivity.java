package com.medinotify.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.medinotify.R;
import com.medinotify.business.Business;
import com.medinotify.business.BusinessImpl;
import com.medinotify.model.Medicina;
import com.medinotify.model.Session;

public class AddMedicineActivity extends Activity {

	private Button btAccept = null;
	private EditText txtNombre = null;
	private EditText txtFuncion = null;
	private EditText txtComentarios = null;
	private Spinner spinner = null;
	private Business business = new BusinessImpl();
	// private ServiceFactory serviceFactory = null;
	SharedPreferences settings = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_medicine);
		txtNombre = (EditText) findViewById(R.id.editTextNewNombre);
		txtFuncion = (EditText) findViewById(R.id.editTextNewFuncion);
		txtComentarios = (EditText) findViewById(R.id.editTextNewComentario);
		spinner = (Spinner) findViewById(R.id.spinnerMetodo);
		rellenaSpinner();
		// serviceFactory = new MySQLServiceFactory(this);
		settings = getSharedPreferences("userNotyMedify", MODE_PRIVATE);
		btAccept = (Button) findViewById(R.id.btAceptarNewMedicine);
		btAccept.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pressAceptar();
			}
		});
	}

	private void rellenaSpinner() {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter
				.createFromResource(this, R.array.metodoArray,
						android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_medicine, menu);
		return true;
	}

	public void pressAceptar() {
		// serviceFactory.getMedicineService().anadirMedicina(String.valueOf(idUser),
		// txtNombre.getText().toString(),
		// txtFuncion.getText().toString(),
		// txtComentarios.getText().toString());
		Medicina med = business.addMedicine(Session.getInstance()
				.getUsuarioActual().getId(), txtNombre.getText().toString(),
				txtFuncion.getText().toString(), txtComentarios.getText()
						.toString(), spinner.getSelectedItem().toString());
		if (med != null) {
			List<Medicina> meds = business.getAllMedicinas(Session
					.getInstance().getUsuarioActual().getId());
			Session.getInstance().setMedicinas(meds);
			Intent intent = new Intent(AddMedicineActivity.this,
					ListMedicamentosActivity.class);
			startActivity(intent);
		} else
			Toast.makeText(getApplicationContext(), "Datos incompletos",
					Toast.LENGTH_SHORT).show();

	}
}
