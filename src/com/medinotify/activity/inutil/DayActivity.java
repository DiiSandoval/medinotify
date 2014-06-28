package com.medinotify.activity.inutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;

import com.medinotify.R;
import com.medinotify.utility.ExpandableListAdapter;

public class DayActivity extends Activity {

	private String date_month_year;
	
	private TextView dateDayView;
	
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day);
		
		getActionBar().setTitle("MediNotify");
		
		dateDayView = (TextView) findViewById(R.id.textViewDateDay);
		writeDate();
		
		// get the listview
        expListView = (ExpandableListView) findViewById(
        		R.id.expandableListViewMorning);
 
        // preparing list data
        prepareListData();
 
        listAdapter = new ExpandableListAdapter(this, listDataHeader, 
        		listDataChild);
 
        // setting list adapter
        expListView.setAdapter(listAdapter);
 
        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {
 
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                    int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });
 
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
 
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });
 
        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
 
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();
 
            }
        });
 
        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {
 
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                        listDataHeader.get(groupPosition)).get(
                                        childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
	}

	/**
	 * Pone la fecha arriba del todo de la ventana con el día seleccionado en
	 * el calendario de la activity anterior.
	 */
	private void writeDate() {
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    date_month_year = extras.getString("date_month_year");
		}
		dateDayView.setText(date_month_year);
	}

	/**
	 * Falta meter la relacion con la persistencia en este metodo
	 */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        
        listDataHeader.add("Mañana");
        listDataHeader.add("Mediodía");
        listDataHeader.add("Noche");

        //Esto está hardcodeado de guay
       
        List<String> medsMorning = new ArrayList<String>();
        addMeds(medsMorning);
 
        List<String> medsNoon = new ArrayList<String>();
        addMeds(medsNoon);
 
        List<String> medsNight = new ArrayList<String>();
        addMeds(medsNight);
 
        listDataChild.put(listDataHeader.get(0), medsMorning); // Header, Child data
        listDataChild.put(listDataHeader.get(1), medsNoon);
        listDataChild.put(listDataHeader.get(2), medsNight);
    }
	
    /**
     * Metodo secundario para meter los medicamentos en cada lista del horario
     * Falta cambiar el random por la obtencion de datos de persistencia.
     * @param medsMorning
     */
	private void addMeds(List<String> medsMorning) {
		int taca = (int) (Math.random() * 5);
		for (int i = 0; i < taca; i++)
			medsMorning.add("Taca " + i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.day, menu);
		return true;
	}

}
