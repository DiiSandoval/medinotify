package com.medinotify.activity;

import java.util.Calendar;
import java.util.List;

import com.medinotify.R;
import com.medinotify.R.id;
import com.medinotify.R.layout;
import com.medinotify.R.menu;
import com.medinotify.activity.inutil.DosisGridActivity;
import com.medinotify.activity.inutil.MediListActivity;
import com.medinotify.business.Business;
import com.medinotify.business.BusinessImpl;
import com.medinotify.model.Dosis;
import com.medinotify.model.Medicina;
import com.medinotify.model.Session;
import com.medinotify.model.Usuario;

import android.app.Activity;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.preference.EditTextPreference;

public class NewDosisActivity extends Activity {
	private Button buttonEscogeMedicamento;
	private Button buttonAgregarDosis;

	private TextView editFechaInicio;
	private TextView editNombreMedicamento;
	private Spinner spFrecuencia;
	private Spinner spCantidad;

	private int year;
	private int month;
	private int day;

	private Business business = new BusinessImpl();

	static final int DATE_DIALOG_ID = 999;
	private boolean fechaSeleccionada = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_dosis);

		getActionBar().setTitle("MediNotify");

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		buttonEscogeMedicamento = (Button) findViewById(R.id.buttonEscogerMedicamento);
		buttonAgregarDosis = (Button) findViewById(R.id.btAddDosis);

		spCantidad = (Spinner) findViewById(R.id.spinnerMetodo);
		spFrecuencia = (Spinner) findViewById(R.id.spinnerFrecuenciaGrid);

		editFechaInicio = (TextView) findViewById(R.id.textViewFechaInicioChange);
		// editFechaInicio.setText(day + "-" + month + 1 + "-" + year);
		editFechaInicio.setText("Escoge fecha");
		spCantidad = (Spinner) findViewById(R.id.spinnerMetodo);
		editNombreMedicamento = (TextView) findViewById(R.id.textViewNombreMedicamento);
		Medicina med = Session.getInstance().getMedicinaEscogida();

		if (med != null) {
			editNombreMedicamento
					.setText(med.getNombre() + " (" + med.getComentario()
							+ ")  - Funcion: " + med.getFuncion());
		}

		chooseMed();

		defineSpinners();

		changeDateBegin();
		buttonAgregarDosis.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Session.getInstance().getMedicinaEscogida() != null
						&& !editFechaInicio.getText().toString()
								.equals("Escoge fecha")) {
					Dosis dosis = business.addDosis(Session.getInstance()
							.getUsuarioActual().getId(), Session.getInstance()
							.getMedicinaEscogida().getId(), spCantidad
							.getSelectedItem().toString(), spFrecuencia
							.getSelectedItem().toString(), editFechaInicio
							.getText().toString());
					Session.getInstance()
							.getUsuarioActual()
							.setDosisAlmacenadas(
									business.getAllDosis(Session.getInstance()
											.getUsuarioActual().getId()));
					Intent intent = new Intent(NewDosisActivity.this,
							CalendarioActivity.class);
					startActivity(intent);

				} else
					Toast.makeText(
							getApplicationContext(),
							"No tienes ninguna medicina y/o fecha seleccionada",
							Toast.LENGTH_SHORT).show();

			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
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
			View rootView = inflater.inflate(R.layout.fragment_new_dosis,
					container, false);
			return rootView;
		}
	}

	private void chooseMed() {
		buttonEscogeMedicamento.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Usuario user = Session.getInstance().getUsuarioActual();
				if (user != null) {
					List<Medicina> meds = business.getAllMedicinas(user.getId());
					Session.getInstance().setMedicinas(meds);
					Intent intent = new Intent(NewDosisActivity.this,
							ListMedicamentosActivity.class);
					startActivity(intent);
				}
			}
		});

	}

	private void changeDateBegin() {
		editFechaInicio.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// seleccionamos inicio
				fechaSeleccionada = true;
				showDateDialog();
			}
		});
	}

	private void defineSpinners() {
		defineSpFrecuencia();
		defineSpCantidad(R.array.cantArray);
	}

	private void defineSpFrecuencia() {
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.frecArray1, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spFrecuencia.setAdapter(adapter);
	}

	private void defineSpCantidad(int array) {
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spCantidad.setAdapter(adapter);
	}

	@SuppressWarnings("deprecation")
	private void showDateDialog() {
		showDialog(DATE_DIALOG_ID);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		DatePickerDialog dialog = null;
		switch (id) {
		case DATE_DIALOG_ID: {
			// set date picker as current date
			dialog = new DatePickerDialog(this, datePickerListener, year,
					month, day);
		}
		}
		return dialog;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// set selected date into textview

			if (fechaSeleccionada == true) {
				editFechaInicio.setText(new StringBuilder().append(day)
						.append("/").append(month + 1).append("/").append(year)
						.append(" "));
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dosis_grid, menu);
		return true;
	}

}
