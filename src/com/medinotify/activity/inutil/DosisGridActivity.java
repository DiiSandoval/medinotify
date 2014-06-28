package com.medinotify.activity.inutil;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.medinotify.R;

public class DosisGridActivity extends Activity {
	
	private Button buttonEscogeMedicamento;
	
	private TextView editFechaInicio;
	private TextView editFechaTermino;
	private Spinner spFrecuencia;
	private Spinner spCantidad;
	
	private int year;
	private int month;
	private int day;
	private int idUser=0;
	
	static final int DATE_DIALOG_ID = 999;
	private boolean fechaSeleccionada = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dosis_grid);
		
		getActionBar().setTitle("MediNotify");
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    idUser = extras.getInt("idUser");
		}
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		
		buttonEscogeMedicamento = (Button) findViewById(R.id.buttonEscogerMedicamento);
		
		spCantidad = (Spinner) findViewById(R.id.spinnerCantidadGrid);
		spFrecuencia = (Spinner) findViewById(R.id.spinnerFrecuenciaGrid);
		
		editFechaInicio = (TextView) findViewById(R.id.textViewFechaInicioChange);
		editFechaTermino = (TextView) findViewById(R.id.textViewFechaTerminoChange);
		editFechaInicio.setText(day + "-" + month+1 + "-" + year);
		editFechaTermino.setText(day + "-" + month+1 + "-" + year);
		
		chooseMed();
		
		defineSpinners();
		
		changeDateBegin();
		
		changeDateEnd();
	}

	private void chooseMed() {
		buttonEscogeMedicamento.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DosisGridActivity.this, MediListActivity.class);
				startActivity(intent);
				intent.putExtra("idUser", idUser);
				startActivity(intent);
			}
		});
		
	}

	private void changeDateBegin() {
		editFechaInicio.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//seleccionamos inicio
				fechaSeleccionada = true;
				showDateDialog();
			}
		});
	}
	
	private void changeDateEnd() {
		editFechaTermino.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//seleccionamos termino
				fechaSeleccionada = false;
				showDateDialog();
			}
		});
	}

	private void defineSpinners() {
		defineSpFrecuencia();
		defineSpCantidad(R.array.cantArray);
	}
	
	private void defineSpFrecuencia() {
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.frecArray1, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spFrecuencia.setAdapter(adapter);
	}
	
	private void defineSpCantidad(int array) {
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        array, android.R.layout.simple_spinner_item);
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
		case DATE_DIALOG_ID:{
		   // set date picker as current date
		   dialog = new DatePickerDialog(this, datePickerListener, 
                         year, month, day);
			}
		}
		return dialog;
	}
 
	private DatePickerDialog.OnDateSetListener datePickerListener
                = new DatePickerDialog.OnDateSetListener() {
 
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			
			// set selected date into textview
			
			if(fechaSeleccionada == true){
				editFechaInicio.setText(new StringBuilder().append(month + 1)
						.append("-").append(day).append("-").append(year)
						.append(" "));
			}
			else{
				editFechaTermino.setText(new StringBuilder().append(month + 1)
						   .append("-").append(day).append("-").append(year)
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
