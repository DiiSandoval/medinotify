package com.medinotify.utility;

import android.app.Activity;
import android.content.Intent;

import com.medinotify.activity.LoginActivity;
import com.medinotify.activity.RegisterActivity;
import com.medinotify.activity.calendario.CalendarActivity;

public class LaunchActivity {

	public static void launchCalendarActivity(Activity activity) {
		Intent intent = new Intent(activity, CalendarActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}

	public static void launchLoginActivity(Activity activity) {
		Intent intent = new Intent(activity, LoginActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}

	public static void launchRegisterActivity(Activity activity) {
		Intent intent = new Intent(activity, RegisterActivity.class);
		activity.startActivity(intent);
	}
}
