package com.medinotify.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.medinotify.R;
import com.medinotify.model.Medicina;
import com.medinotify.model.Session;
import com.medinotify.utility.ExpandableListAdapter;
import com.medinotify.utility.LaunchActivity;

public class ListMedicamentosActivity extends Activity {

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_medicamentos);

		getActionBar().setTitle("MediNotify");

		
		expListView = (ExpandableListView) findViewById(R.id.expandableListViewHistorial);

		
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

				Medicina med = Session.getInstance().getMedicinaByNombre(
						listDataChild.get(listDataHeader.get(groupPosition))
								.get(childPosition));
				if (med != null) {
					Session.getInstance().setMedicinaEscogida(med);
					LaunchActivity.launchNewDosisActivity(ListMedicamentosActivity.this);

				}

				return false;
			}
		});

	}

	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		listDataHeader.add("Pulse para desplegar la lista de medicamentos");

		List<String> medsTaca = new ArrayList<String>();
		addMeds(medsTaca);

		listDataChild.put(listDataHeader.get(0), medsTaca);
	}

	private void addMeds(List<String> medsMorning) {
		List<Medicina> meds = Session.getInstance().getMedicinas();
		if (meds != null && !meds.isEmpty())
			for (Medicina medicina : meds)
				medsMorning.add(medicina.getNombre());
		else
			medsMorning.add("El usuario no tiene medicinas almacenadas");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list_medicamentos, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.CerrarSesion:
			Session.getInstance().setUsuarioActual(null);
			LaunchActivity.launchLoginActivity(this);
			return true;
		case R.id.itemAddCalendar: {
			LaunchActivity.launchAddMedicineActivity(ListMedicamentosActivity.this);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
