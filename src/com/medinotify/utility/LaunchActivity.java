package com.medinotify.utility;

import android.app.Activity;
import android.content.Intent;

import com.medinotify.activity.AddMedicineActivity;
import com.medinotify.activity.CalendarioActivity;
import com.medinotify.activity.DiaActivity;
import com.medinotify.activity.HistorialActivity;
import com.medinotify.activity.ListMedicamentosActivity;
import com.medinotify.activity.LoginActivity;
import com.medinotify.activity.NewDosisActivity;
import com.medinotify.activity.RegistroActivity;

public class LaunchActivity {

	public static void launchCalendarActivity(Activity activity) {
		Intent intent = new Intent(activity, CalendarioActivity.class);
		activity.startActivity(intent);
	}

	public static void launchLoginActivity(Activity activity) {
		Intent intent = new Intent(activity, LoginActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}

	public static void launchRegisterActivity(Activity activity) {
		Intent intent = new Intent(activity, RegistroActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}
	public static void launchAddMedicineActivity(Activity activity) {
		Intent intent = new Intent(activity, AddMedicineActivity.class);
		activity.startActivity(intent);
	}
	
	public static void launchDiaActivity(Activity activity) {
		Intent intent = new Intent(activity, DiaActivity.class);
		activity.startActivity(intent);
	}
	public static void launchListMedicamentosActivity(Activity activity) {
		Intent intent = new Intent(activity, ListMedicamentosActivity.class);
		activity.startActivity(intent);
	}
	public static void launchNewDosisActivity(Activity activity) {
		Intent intent = new Intent(activity, NewDosisActivity.class);
		activity.startActivity(intent);
	}
	public static void launchHistorialActivity(Activity activity) {
		Intent intent = new Intent(activity, HistorialActivity.class);
		activity.startActivity(intent);
	}
	
}
