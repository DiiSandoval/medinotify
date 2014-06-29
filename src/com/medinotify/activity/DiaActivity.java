package com.medinotify.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;

import com.medinotify.R;
import com.medinotify.business.Business;
import com.medinotify.business.BusinessImpl;
import com.medinotify.model.Dosis;
import com.medinotify.model.Medicina;
import com.medinotify.model.Session;
import com.medinotify.utility.ExpandableListAdapter;
import com.medinotify.utility.LaunchActivity;

public class DiaActivity extends Activity {
	private String date_month_year;

	private TextView dateDayView;

	String formatofecha = Session.getInstance().getDay_month_year();
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	String fechaTomada;
	Business business = new BusinessImpl();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dia);

		getActionBar().setTitle("MediNotify");

		setDateDayView((TextView) findViewById(R.id.textViewDateDay));
		writeDate();

		
		expListView = (ExpandableListView) findViewById(R.id.expandableListViewMorning);

		
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader,
				listDataChild);

		
		expListView.setAdapter(listAdapter);

		
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return false;
			}
		});

		
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Expanded",
						Toast.LENGTH_SHORT).show();
			}
		});

		
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Collapsed",
						Toast.LENGTH_SHORT).show();

			}
		});

		
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Toast.makeText(
						getApplicationContext(),
						"Has tomado "
								+ listDataHeader.get(groupPosition)
								+ " : "
								+ listDataChild.get(
										listDataHeader.get(groupPosition)).get(
										childPosition), Toast.LENGTH_SHORT)
						.show();

				business.tomarDosis(Session.getInstance().getUsuarioActual()
						.getId(), fechaTomada,
						listDataChild.get(listDataHeader.get(groupPosition))
								.get(childPosition));
				
				List<Dosis> dosis = business.getAllDosis(Session.getInstance().getUsuarioActual().getId());
				Session.getInstance().getUsuarioActual().setDosisAlmacenadas(dosis);
				

				LaunchActivity.launchCalendarActivity(DiaActivity.this);
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.dia, menu);
		return true;
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
			View rootView = inflater.inflate(R.layout.fragment_dia, container,
					false);
			return rootView;
		}
	}

	/**
	 * Pone la fecha arriba del todo de la ventana con el día seleccionado en el
	 * calendario de la activity anterior.
	 */
	private void writeDate() {
		Session.getInstance().setDay_month_year(date_month_year);

	}

	/**
	 * Falta meter la relacion con la persistencia en este metodo
	 */
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		listDataHeader.add("Mañana");
		listDataHeader.add("Tarde");
		listDataHeader.add("Noche");


		List<String> medsMorning = new ArrayList<String>();
		addMeds(medsMorning, "Mañana");

		List<String> medsNoon = new ArrayList<String>();
		addMeds(medsNoon, "Tarde");

		List<String> medsNight = new ArrayList<String>();
		addMeds(medsNight, "Noche");

		listDataChild.put(listDataHeader.get(0), medsMorning); 
		listDataChild.put(listDataHeader.get(1), medsNoon);
		listDataChild.put(listDataHeader.get(2), medsNight);
	}

	/**
	 * Metodo secundario para meter los medicamentos en cada lista del horario
	 * Falta cambiar el random por la obtencion de datos de persistencia.
	 * 
	 * @param medsMorning
	 */
	private void addMeds(List<String> medsMorning, String frecuencia) {
		List<Dosis> dosis = Session.getInstance().getUsuarioActual()
				.getDosisAlmacenadas();
		boolean vacio = true;
		if (!dosis.isEmpty()) {
			for (Dosis dosis2 : dosis) {
				String fecha = dosis2.getFecha();
				if (dosis2.getFrecuencia().equalsIgnoreCase(frecuencia)
						&& dosis2.getTomado().equals("false")
						&& fecha.equals(cambiarFormatoFecha(formatofecha))) {
					vacio = false;
					Medicina med = Session.getInstance().getMedicinaById(
							dosis2.getId_med());
					if (med != null)
						medsMorning.add(med.getNombre() + " - Funcion: "
								+ med.getFuncion() + " - Cantidad:"
								+ dosis2.getCantidad());
				}

			}
			if (vacio)
				medsMorning.add("No tienes nada que tomar :)");

		} else
			medsMorning.add("No tienes nada que tomar :)");
	}

	private String cambiarFormatoFecha(String formatofecha) {
		String[] parts = formatofecha.split("-");
		String mes = getMes(parts[1]);
		fechaTomada = parts[0] + "/" + mes + "/" + parts[2] + " ";
		return fechaTomada;
	}

	private String getMes(String string) {
		if (string.equalsIgnoreCase("Enero"))
			return "1";
		else if (string.equalsIgnoreCase("Febrero"))
			return "2";
		else if (string.equalsIgnoreCase("Marzo"))
			return "3";
		else if (string.equalsIgnoreCase("Abril"))
			return "4";
		else if (string.equalsIgnoreCase("Mayo"))
			return "5";
		else if (string.equalsIgnoreCase("Junio"))
			return "6";
		else if (string.equalsIgnoreCase("Julio"))
			return "7";
		else if (string.equalsIgnoreCase("Agosto"))
			return "8";
		else if (string.equalsIgnoreCase("Septiembre"))
			return "9";
		else if (string.equalsIgnoreCase("Octubre"))
			return "10";
		else if (string.equalsIgnoreCase("Noviembre"))
			return "11";
		else if (string.equalsIgnoreCase("Diciembre"))
			return "12";
		else
			return null;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch (item.getItemId()) {
		case R.id.CerrarSesion:
			Session.getInstance().setUsuarioActual(null);
			LaunchActivity.launchLoginActivity(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public TextView getDateDayView() {
		return dateDayView;
	}

	public void setDateDayView(TextView dateDayView) {
		this.dateDayView = dateDayView;
	}
}
