package thebeginnerapp.fr.airlinesfinder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import thebeginnerapp.fr.airlinesfinder.DAO.DaoAirport;
import thebeginnerapp.fr.airlinesfinder.DAO.DaoAvion;
import thebeginnerapp.fr.airlinesfinder.DAO.DaoCountry;
import thebeginnerapp.fr.airlinesfinder.Utils.Airplane;
import thebeginnerapp.fr.airlinesfinder.Utils.Airport;
import thebeginnerapp.fr.airlinesfinder.Utils.Country;
import thebeginnerapp.fr.airlinesfinder.Utils.RangeSeekBar;
import thebeginnerapp.fr.airlinesfinder.adapter.ListAdapterCustom;


public class SettingResearch extends ActionBarActivity implements View.OnClickListener{

	public int position_avion = 0;
	public  ArrayList<Airplane> airplanes;
	public ArrayList<Airport> airports_list;
	public ArrayList<Country> country_list;
	public int maxCat=10;
	public int minCat=0;
	public String mintime ="0h00";
	public String maxtime ="45h00";
	public ArrayList<Integer> mSelectedItems;

	public static int RAYON_EARTH = 6371;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_research);
		String []list =  getResources().getStringArray(R.array.Continents);
	  mSelectedItems = new ArrayList<Integer>();
		for(int i = 0; i<list.length; i++){
			mSelectedItems.add(i);
		}

		ArrayList<String> arraylist = new ArrayList<>();
		Collections.addAll(arraylist, list);
		//ListAdapterCustom dataAdapter = new ListAdapterCustom(this,R.layout.abc_list_menu_item_checkbox,arraylist);
		//listView.setAdapter(dataAdapter);
		Spinner Spinner = (Spinner) findViewById(R.id.spinnerAvion);


		DaoAirport daoAirport = new DaoAirport(MainActivity.getMyDbHelper().getMyDataBase());
		DaoCountry daoCountry = new DaoCountry(MainActivity.getMyDbHelper().getMyDataBase());
		country_list = daoCountry.getCountry();
		airports_list = daoAirport.getAirport();
		String []airports = new String[airports_list.size()];
		for(int i = 0; i<airports_list.size(); i++){
			airports[i] = airports_list.get(i).toString();
		}

		Button buttonChoixContinent = (Button) findViewById(R.id.buttonChoixContinent);
		buttonChoixContinent.setOnClickListener(this);

		AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, airports);
		autoCompleteTextView.setAdapter(adapter);

		DaoAvion daoAvion = new DaoAvion(MainActivity.getMyDbHelper().getMyDataBase());
		airplanes = daoAvion.getAvion();
		String [] avionlist = new String[airplanes.size()];

		for(int i = 0; i<airplanes.size(); i++){
			avionlist[i] = airplanes.get(i).toString();
		}

		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, avionlist); //selected item will look like a spinner set from XML
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner.setAdapter(spinnerArrayAdapter);
		Spinner.setSelection(0);
		Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				position_avion = position;
				LinearLayout sublinearSeekBar = (LinearLayout) findViewById(R.id.sublinearSeekBar);
				sublinearSeekBar.removeAllViews();
				LinearLayout subLinearLayoutcategorie = (LinearLayout) findViewById(R.id.sublinearSeekBarcategorie);
				subLinearLayoutcategorie.removeAllViews();

				SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
				seekBar.setMax(airplanes.get(position).getAirRange());
				seekBar.setProgress(airplanes.get(position).getAirRange());
				seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
					{
						TextView textView = (TextView) findViewById(R.id.valuesseekbar);
						textView.setText(String.valueOf(progress));
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar)
					{

					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar)
					{

					}
				});
				double number = (((double) (airplanes.get(position).getAirRange()) / airplanes.get(position).getSpeed())) * 2 + 2;
				int partie_entier = (int) number;
				double partie_decimal = (number - partie_entier) * 100;
				partie_entier *= 4;
				if (partie_decimal < 26)
				{
					partie_entier++;
				}
				else if (partie_decimal >= 26 && partie_decimal < 51)
				{
					partie_entier += 2;
				}
				else if (partie_decimal >= 51 && partie_decimal < 76)
				{
					partie_entier += 3;
				}
				else if (partie_decimal >= 76)
				{
					partie_entier += 4;
				}





				RangeSeekBar<Integer> seekBartime = new RangeSeekBar<Integer>(getApplication());
				seekBartime.setRangeValues(0, partie_entier);
				seekBartime.setSelectedMinValue(0);
				seekBartime.setSelectedMaxValue(partie_entier);
				seekBartime.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
					@Override
					public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue)
					{
						// handle changed range values
						int min = minValue;
						int max = maxValue;
						mintime = timeSlider(getConvertedValue(min));
						maxtime = timeSlider(getConvertedValue(max));

						Log.i("SeekBar", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
						TextView textView = (TextView) findViewById(R.id.valuesseekbartime);
						textView.setText(String.valueOf(timeSlider(getConvertedValue(min)) + " - " + timeSlider(getConvertedValue(max))));
					}
				});
				TextView textView = (TextView) findViewById(R.id.valuesseekbartime);
				textView.setText(String.valueOf(timeSlider(getConvertedValue(seekBartime.getAbsoluteMinValue())) + " - " + timeSlider(getConvertedValue(seekBartime.getSelectedMaxValue()))));

				sublinearSeekBar.addView(seekBartime);

				int categorie = airplanes.get(position).getCategorie();



				RangeSeekBar<Integer> seekBarcategorie = new RangeSeekBar<Integer>(getApplication());
				seekBarcategorie.setRangeValues(categorie, 10);
				seekBarcategorie.setSelectedMinValue(categorie);
				seekBarcategorie.setSelectedMaxValue(10);
				seekBarcategorie.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
					@Override
					public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue)
					{
						// handle changed range values
						int min = minValue;
						int max = maxValue;
						minCat = min;
						maxCat = max;
						Log.i("SeekBar", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
						TextView textView = (TextView) findViewById(R.id.valuesseekcategorie);
						textView.setText("Catégories : " + min + " - " + max);
					}
				});
				TextView textView_categorie = (TextView) findViewById(R.id.valuesseekcategorie);
				textView_categorie.setText("Catégories : " + seekBarcategorie.getAbsoluteMinValue() + " - " + seekBarcategorie.getAbsoluteMaxValue());

				subLinearLayoutcategorie.addView(seekBarcategorie);



			}
			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(this);


}
public double getConvertedValue(int intVal){
		double floatVal = 0.00;
		floatVal = .25f * intVal;
		return floatVal;
	}
	public String timeSlider(double number){
		int [] numberresult = new int[2];
		int hour = 0;
		int minute = 0;

		int indexpoint = String.valueOf(number).indexOf(".");

		hour = Integer.parseInt(String.valueOf(number).substring(0,indexpoint));
		minute = Integer.parseInt(String.valueOf(number).substring(indexpoint+1,String.valueOf(number).length()));

		if(minute ==  5)
			minute = minute*10;

		if (minute < 26) {
			numberresult[1] = 15;
		} else if (minute >= 26 && minute < 51) {
			numberresult[1] = 30;
		} else if (minute >= 51 && minute < 76) {
			numberresult[1] = 45;
		} else if (minute >= 76) {
			hour++;
			numberresult[1] =0;
		}

		if(minute == 0){
			numberresult[1] = 0;
		}
		if(numberresult[1] ==0)
			return hour + "h"+"00";
		else{
			return hour + "h"+numberresult[1];
		}


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_setting_research, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v)
	{
		if(v.getId() == R.id.button){
			// Research click //
			/* Récupération des variables */
			Airport airport = null;
			AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
			String hub = autoCompleteTextView.getText().toString();
			Airplane airplane = airplanes.get(position_avion);
			for(int i = 0; i<airports_list.size(); i++){
				if(hub.substring(0, 3).equals(airports_list.get(i).getTrigramme())){
					airport = airports_list.get(i);
				}
			}
			if(airport != null)
				research_function(airport,airplane,airports_list);
		}else if(v.getId() == R.id.buttonChoixContinent){
				Dialog dialog = onCreateDialog();
				dialog.show();
		}
	}

	public void research_function(Airport airport, Airplane airplane, ArrayList<Airport> airports_list){
		ArrayList<Country> temp_country = new ArrayList<Country>();
		ArrayList<Airport> temp_airport = new ArrayList<Airport>();

		/* Récupération des pays disponibles */
		for(int i : mSelectedItems){
			for(Country c : country_list){
				if(c.getContinent() == (i+1)){
					temp_country.add(c);
				}
			}
		}

		/* Récupération des aéroports disponible */
		for(Airport a : airports_list){
			for(Country c : temp_country){
				if(c.getId() == a.getIdCountry()){
					temp_airport.add(a);
				}
			}
		}
		for(Airport a : temp_airport){
			if(airport.getId() != a.getId()){
				if(a.getLongSign() == 1){
					a.setLongRad((-1*a.getLongRad()));
				}
				if(a.getLatSign() == 1){
					a.setLatRad((-1 * a.getLatRad()));
				}

				double dLat = a.getLatRad() - airport.getLatRad();
				double dLong = a.getLongRad() - airport.getLongRad();

				double angle = Math.sin((dLat/2.0)) * Math.sin((dLat/2.0)) + Math.sin((dLong/2.0))*Math.sin((dLong/2.0)) * Math.cos(airport.getLatRad())*Math.cos(a.getLatRad());
				double course = 2 * Math.atan2(Math.sqrt(angle),Math.sqrt(1-angle));
				double dist = Math.round(RAYON_EARTH*course);
				if(dist <= airplane.getAirRange() ){
					double tempvol = (dist/airplane.getSpeed())*2+2;
					tempvol = (double) (Math.round(tempvol * 100.0))/ 100.0;
					int heure = (int)tempvol;
					double minute = ((tempvol*100)-(heure*100))*0.6;
					if (minute == 0) {
						minute = 0;
					} else if (minute > 0 && minute <= 15) {
						minute = 15;
					} else if(minute > 15 && minute <= 30) {
						minute = 30;
					} else if(minute > 30 && minute <= 45) {
						minute = 45;
					} else if(minute > 45 && minute <= 60) {
						minute = 0;
						heure++;
					}
					String temp_vol_string = heure + "h" +(int)minute;
					Log.e("Result_recherche",temp_vol_string);
				}

			}
		}


	}

	public Dialog onCreateDialog() {  // Where we track the selected items
		boolean [] checked = new boolean[getResources().getStringArray(R.array.Continents).length];
		for(int i = 0; i<mSelectedItems.size(); i++)
			checked[mSelectedItems.get(i)] = true;




		AlertDialog.Builder builder = new AlertDialog.Builder(SettingResearch.this);
		// Set the dialog title
		builder.setTitle(R.string.continents)
				// Specify the list array, the items to be selected by default (null for none),
				// and the listener through which to receive callbacks when items are selected
				.setMultiChoiceItems(R.array.Continents, checked,
						new DialogInterface.OnMultiChoiceClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which,
							                    boolean isChecked) {
								if (isChecked) {
									// If the user checked the item, add it to the selected items
									mSelectedItems.add(which);
								} else if (mSelectedItems.contains(which)) {
									// Else, if the item is already in the array, remove it
									mSelectedItems.remove(Integer.valueOf(which));
								}
							}
						})
						// Set the action buttons
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// User clicked OK, so save the mSelectedItems results somewhere
						// or return them to the component that opened the dialog
						//...
						Toast.makeText(getApplicationContext(),mSelectedItems.toString(),Toast.LENGTH_LONG).show();
					}
				})
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						//...
					}
				});

		return builder.create();
	}
}
