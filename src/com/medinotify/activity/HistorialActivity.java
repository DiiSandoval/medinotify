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
import android.widget.Toast;

import com.medinotify.R;
import com.medinotify.model.Dosis;
import com.medinotify.model.Medicina;
import com.medinotify.model.Session;
import com.medinotify.utility.ExpandableListAdapter;

public class HistorialActivity extends Activity {
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historial);

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

				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.historial, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
			View rootView = inflater.inflate(R.layout.fragment_historial,
					container, false);
			return rootView;
		}
	}

	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		listDataHeader.add("Pulse para desplegar el historial");

		List<String> medsTaca = new ArrayList<String>();
		addMeds(medsTaca);

		listDataChild.put(listDataHeader.get(0), medsTaca);
	}

	private void addMeds(List<String> medsMorning) {
		List<Dosis> dos = Session.getInstance().getUsuarioActual()
				.getDosisAlmacenadas();
		
		if (dos != null && !dos.isEmpty())
			for (Dosis dosis : dos) {
				if (dosis.getTomado().equals("true")){
					Medicina m = Session.getInstance().getMedicinaById(
							dosis.getId_med());
					medsMorning.add(dosis.getFecha()+ ": Has tomado " + dosis.getCantidad()  + " unidades de " + m.getNombre() +  "."  );
			  }
			}
		else
			medsMorning.add("Historial vacío");

	}

}
