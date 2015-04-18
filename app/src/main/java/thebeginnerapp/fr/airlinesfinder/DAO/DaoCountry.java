package thebeginnerapp.fr.airlinesfinder.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import thebeginnerapp.fr.airlinesfinder.Utils.Country;

/**
 * File created by Kevin on 09/02/2015 for AirlinesFinder
 */
public class DaoCountry {
	SQLiteDatabase myDatabase;
	Cursor c;
	String request;

	public DaoCountry(SQLiteDatabase database)
	{
		myDatabase = database;
	}
	public ArrayList<Country> getCountry(){
		ArrayList<Country> country = new ArrayList<>();
		request = "SELECT * FROM country";
		c = myDatabase.rawQuery(request, null);
		while(c.moveToNext()){
			country.add(new Country(c.getInt(0),c.getString(1),c.getString(2),c.getInt(3)));
		}
		return country;
	}
}
