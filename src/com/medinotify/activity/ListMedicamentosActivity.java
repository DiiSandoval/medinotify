package com.medinotify.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.medinotify.R;
import com.medinotify.R.id;
import com.medinotify.R.layout;
import com.medinotify.R.menu;
import com.medinotify.activity.calendario.AddMedicineActivity;
import com.medinotify.model.Medicina;
import com.medinotify.model.Session;
import com.medinotify.utility.ExpandableListAdapter;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.os.Build;

public class ListMedicamentosActivity extends Activity {
	private int idUser = 0;

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_medicamentos);
		
		getActionBar().setTitle("MediNotify");

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    idUser = extras.getInt("idUser");
		}
		
		// get the listview
        expListView = (ExpandableListView) findViewById(
        		R.id.expandableListViewMedicines);
 
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
//                Toast.makeText(
//                        getApplicationContext(),
//                        listDataHeader.get(groupPosition)
//                                + " : "
//                                + listDataChild.get(
//                                        listDataHeader.get(groupPosition)).get(
//                                        childPosition), Toast.LENGTH_SHORT)
//                        .show();
                
                Medicina med = Session.getInstance().getMedicinaByNombre(listDataChild.get(
                                        listDataHeader.get(groupPosition)).get(
                                        childPosition));
                if(med!=null){
                	Session.getInstance().setMedicinaEscogida(med);
                	Intent intent = new Intent(ListMedicamentosActivity.this,
    						NewDosisActivity.class);
    				startActivity(intent);
                	
              
                }
                
                return false;
            }
        });
		
	}

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        
        listDataHeader.add("Pulse para desplegar la lista de medicamentos");

        //Esto está hardcodeado de guay
       
        List<String> medsTaca = new ArrayList<String>();
        addMeds(medsTaca);
 
        listDataChild.put(listDataHeader.get(0), medsTaca);
    }
	
    /**
     * Metodo secundario para meter los medicamentos en cada lista del horario
     * Falta cambiar el random por la obtencion de datos de persistencia.
     * @param medsMorning
     */
	private void addMeds(List<String> medsMorning) {
		List<Medicina> meds = Session.getInstance().getMedicinas();
		if(meds!=null)
			for (Medicina medicina : meds) 
				medsMorning.add(medicina.getNombre());
		else
			medsMorning.add("El usuario no tiene medicinas almacenadas");
		
//		int taca = 5;
//		for (int i = 0; i < taca; i++)
//			medsMorning.add("Taca " + i);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.medi_choose, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
		
	    switch (item.getItemId()) {
	        case R.id.itemAddMediChoose:
				Intent intent = new Intent(ListMedicamentosActivity.this, AddMedicineActivity.class);
				intent.putExtra("idUser", idUser);
				startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}


}
