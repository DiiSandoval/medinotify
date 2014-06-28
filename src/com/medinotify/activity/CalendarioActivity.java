package com.medinotify.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import com.medinotify.R;
import com.medinotify.R.id;
import com.medinotify.R.layout;
import com.medinotify.R.menu;
import com.medinotify.activity.calendario.DayActivity;
import com.medinotify.activity.calendario.DosisGridActivity;
import com.medinotify.activity.inutil.CalendarActivity;
import com.medinotify.activity.inutil.CalendarActivity.GridCellAdapter;
import com.medinotify.model.Session;
import com.medinotify.utility.LaunchActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;

public class CalendarioActivity extends Activity implements OnClickListener{

	private static final String tag = "MyCalendarActivity";
	private SharedPreferences settings = null;

	private TextView currentMonth;
	private Button selectedDayMonthYearButton;
	private ImageView prevMonth;
	private ImageView nextMonth;
	private GridView calendarView;
	private GridCellAdapter adapter;
	private Calendar _calendar;
	private int month, year;
	private int idUser = 0;

	private static final String dateTemplate = "MMMM yyyy";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);

		getActionBar().setTitle("MediNotify");
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    idUser = extras.getInt("idUser");
		}
		
		settings = getSharedPreferences("userNotyMedify", MODE_PRIVATE);
		
		// _calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+1"));
		_calendar = Calendar.getInstance();
		// _calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+1"),
		// Locale.getDefault());
		_calendar.setFirstDayOfWeek(Calendar.MONDAY);
		month = _calendar.get(Calendar.MONTH) + 1;
		year = _calendar.get(Calendar.YEAR);
		Log.d(tag, "Calendar Instance:= " + "Month: " + month + " " + "Year: "
				+ year);

		selectedDayMonthYearButton = (Button) this
				.findViewById(R.id.selectedDayMonthYear);
		selectedDayMonthYearButton.setText("D�a: ");

		prevMonth = (ImageView) this.findViewById(R.id.prevMonth);
		prevMonth.setOnClickListener(this);

		currentMonth = (TextView) this.findViewById(R.id.currentMonth);
		currentMonth.setText(DateFormat.format(dateTemplate,
				_calendar.getTime()));

		nextMonth = (ImageView) this.findViewById(R.id.nextMonth);
		nextMonth.setOnClickListener(this);

		calendarView = (GridView) this.findViewById(R.id.calendar);

		// Initialised
		adapter = new GridCellAdapter(getApplicationContext(),
				R.id.calendar_day_gridcell, month, year);
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
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
			View rootView = inflater.inflate(R.layout.fragment_calendario,
					container, false);
			return rootView;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	// Inner Class
		public class GridCellAdapter extends BaseAdapter implements OnClickListener {
			private static final String tag = "GridCellAdapter";
			private final Context _context;

			private final List<String> list;
			private static final int DAY_OFFSET = 1;
			private final String[] weekdays = new String[] { "Lun", "Mar", "Mie",
					"Jue", "Vie", "Sab", "Dom" };
			private final String[] months = { "Enero", "Febrero", "Marzo", "Abril",
					"Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre",
					"Noviembre", "Diciembre" };
			private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30,
					31, 30, 31 };
			private int daysInMonth;
			private int currentDayOfMonth;
			private int currentWeekDay;
			private Button gridcell;
			private TextView num_events_per_day;
			private final HashMap<String, Integer> eventsPerMonthMap;
			@SuppressLint("SimpleDateFormat")
			private final SimpleDateFormat dateFormatter = new SimpleDateFormat(
					"dd-MMM-yyyy");

			// Days in Current Month
			public GridCellAdapter(Context context, int textViewResourceId,
					int month, int year) {
				this._context = context;
				this.list = new ArrayList<String>();
				Log.d(tag, "==> Passed in Date FOR Month: " + month + " "
						+ "Year: " + year);
				Calendar calendar = Calendar.getInstance();
				calendar.setFirstDayOfWeek(Calendar.MONDAY);
				// Calendar calendar = new
				// GregorianCalendar(TimeZone.getTimeZone("GMT+1"),
				// Locale.getDefault());
				setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
				setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
				Log.d(tag, "New Calendar:= " + calendar.getTime().toString());
				Log.d(tag, "CurrentDayOfWeek :" + getCurrentWeekDay());
				Log.d(tag, "CurrentDayOfMonth :" + getCurrentDayOfMonth());

				// Print Month
				printMonth(month, year);

				// Find Number of Events
				eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
			}

			private String getMonthAsString(int i) {
				return months[i];
			}

			private String getWeekDayAsString(int i) {
				return weekdays[i];
			}

			private int getNumberOfDaysOfMonth(int i) {
				return daysOfMonth[i];
			}

			public String getItem(int position) {
				return list.get(position);
			}

			@Override
			public int getCount() {
				return list.size();
			}

			/**
			 * Prints Month
			 * 
			 * @param mm
			 * @param yy
			 */
			private void printMonth(int mm, int yy) {
				Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
				int trailingSpaces = 0;
				int daysInPrevMonth = 0;
				int prevMonth = 0;
				int prevYear = 0;
				int nextMonth = 0;
				int nextYear = 0;

				int currentMonth = mm - 1;
				String currentMonthName = getMonthAsString(currentMonth);
				daysInMonth = getNumberOfDaysOfMonth(currentMonth);

				Log.d(tag, "Current Month: " + " " + currentMonthName + " having "
						+ daysInMonth + " days.");

				GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
				cal.setFirstDayOfWeek(Calendar.MONDAY);
				Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());

				if (currentMonth == 11) {
					prevMonth = currentMonth - 1;
					daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
					nextMonth = 0;
					prevYear = yy;
					nextYear = yy + 1;
					Log.d(tag, "*->PrevYear: " + prevYear + " PrevMonth:"
							+ prevMonth + " NextMonth: " + nextMonth
							+ " NextYear: " + nextYear);
				} else if (currentMonth == 0) {
					prevMonth = 11;
					prevYear = yy - 1;
					nextYear = yy;
					daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
					nextMonth = 1;
					Log.d(tag, "**--> PrevYear: " + prevYear + " PrevMonth:"
							+ prevMonth + " NextMonth: " + nextMonth
							+ " NextYear: " + nextYear);
				} else {
					prevMonth = currentMonth - 1;
					nextMonth = currentMonth + 1;
					nextYear = yy;
					prevYear = yy;
					daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
					Log.d(tag, "***---> PrevYear: " + prevYear + " PrevMonth:"
							+ prevMonth + " NextMonth: " + nextMonth
							+ " NextYear: " + nextYear);
				}

				int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
				// trailingSpaces = currentWeekDay;
				int[] weekDaysToTraling = { 6, 0, 1, 2, 3, 4, 5 };
				trailingSpaces = weekDaysToTraling[currentWeekDay];

				Log.d(tag, "Week Day:" + currentWeekDay + " is "
						+ getWeekDayAsString(currentWeekDay));
				Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
				Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);

				if (cal.isLeapYear(cal.get(Calendar.YEAR)))
					if (mm == 2)
						++daysInMonth;
					else if (mm == 3)
						++daysInPrevMonth;

				// Trailing Month days
				for (int i = 0; i < trailingSpaces; i++) {
					Log.d(tag,
							"PREV MONTH:= "
									+ prevMonth
									+ " => "
									+ getMonthAsString(prevMonth)
									+ " "
									+ String.valueOf((daysInPrevMonth
											- trailingSpaces + DAY_OFFSET)
											+ i));
					list.add(String
							.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
									+ i)
							+ "-GREY"
							+ "-"
							+ getMonthAsString(prevMonth)
							+ "-"
							+ prevYear);
				}

				// Current Month Days
				for (int i = 1; i <= daysInMonth; i++) {
					Log.d(currentMonthName, String.valueOf(i) + " "
							+ getMonthAsString(currentMonth) + " " + yy);
					if (i == getCurrentDayOfMonth()) {
						list.add(String.valueOf(i) + "-BLUE" + "-"
								+ getMonthAsString(currentMonth) + "-" + yy);
					} else {
						list.add(String.valueOf(i) + "-WHITE" + "-"
								+ getMonthAsString(currentMonth) + "-" + yy);
					}
				}

				// Leading Month days
				for (int i = 0; i < list.size() % 7; i++) {
					Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
					list.add(String.valueOf(i + 1) + "-GREY" + "-"
							+ getMonthAsString(nextMonth) + "-" + nextYear);
				}
			}

			/**
			 * NOTE: YOU NEED TO IMPLEMENT THIS PART Given the YEAR, MONTH, retrieve
			 * ALL entries from a SQLite database for that month. Iterate over the
			 * List of All entries, and get the dateCreated, which is converted into
			 * day.
			 * 
			 * @param year
			 * @param month
			 * @return
			 */
			private HashMap<String, Integer> findNumberOfEventsPerMonth(int year,
					int month) {
				HashMap<String, Integer> map = new HashMap<String, Integer>();

				return map;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View row = convertView;
				if (row == null) {
					LayoutInflater inflater = (LayoutInflater) _context
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					row = inflater.inflate(R.layout.screen_gridcell, parent, false);
				}

				// Get a reference to the Day gridcell
				gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
				gridcell.setOnClickListener(this);

				// ACCOUNT FOR SPACING

				Log.d(tag, "Current Day: " + getCurrentDayOfMonth());
				String[] day_color = list.get(position).split("-");
				String theday = day_color[0];
				String themonth = day_color[2];
				String theyear = day_color[3];
				if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
					if (eventsPerMonthMap.containsKey(theday)) {
						num_events_per_day = (TextView) row
								.findViewById(R.id.num_events_per_day);
						Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
						num_events_per_day.setText(numEvents.toString());
					}
				}

				// Set the Day GridCell
				gridcell.setText(theday);
				gridcell.setTag(theday + "-" + themonth + "-" + theyear);
				Log.d(tag, "Setting GridCell " + theday + "-" + themonth + "-"
						+ theyear);

				if (day_color[1].equals("GREY")) {
					gridcell.setTextColor(getResources()
							.getColor(R.color.lightgray));
				}
				if (day_color[1].equals("WHITE")) {
					gridcell.setTextColor(getResources().getColor(
							R.color.lightgray02));
				}
				if (day_color[1].equals("BLUE")) {
					gridcell.setTextColor(getResources().getColor(R.color.orrange));
				}
				return row;
			}

			@Override
			public void onClick(View view) {
				String date_month_year = (String) view.getTag();
				selectedDayMonthYearButton.setText("D�a: " + date_month_year);
				showDayDosis(date_month_year);
				Log.e("Selected date", date_month_year);
				try {
					Date parsedDate = dateFormatter.parse(date_month_year);
					Log.d(tag, "Parsed Date: " + parsedDate.toString());

				} catch (ParseException e) {
					e.printStackTrace();
				}

			}

			public int getCurrentDayOfMonth() {
				return currentDayOfMonth;
			}

			private void setCurrentDayOfMonth(int currentDayOfMonth) {
				this.currentDayOfMonth = currentDayOfMonth;
			}

			public void setCurrentWeekDay(int currentWeekDay) {
				this.currentWeekDay = currentWeekDay;
			}

			public int getCurrentWeekDay() {
				return currentWeekDay;
			}
		}


		private void showDayDosis(String date_month_year) {
			Intent intent = new Intent(CalendarioActivity.this, DayActivity.class);
			intent.putExtra("date_month_year", date_month_year);
			startActivity(intent);
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.calendar_menu, menu);
			return super.onCreateOptionsMenu(menu);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			
		    switch (item.getItemId()) {
		    case R.id.itemAddCalendar:{
		    	Intent intent = new Intent(CalendarioActivity.this,
						DosisGridActivity.class);
				intent.putExtra("idUser", idUser);
				startActivity(intent);
				
		        return true;
		    }
			case R.id.CerrarSesion:
				Session.getInstance().setUsuarioActual(null);
				LaunchActivity.launchLoginActivity(this);
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
		}
	
}
