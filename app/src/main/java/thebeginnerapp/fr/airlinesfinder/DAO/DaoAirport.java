package thebeginnerapp.fr.airlinesfinder.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import thebeginnerapp.fr.airlinesfinder.Utils.Airplane;
import thebeginnerapp.fr.airlinesfinder.Utils.Airport;

/**
 * File created by Kevin on 09/02/2015 for AirlinesFinder
 */
public class DaoAirport{
	SQLiteDatabase myDatabase;
	Cursor c;
	String request;

	public DaoAirport(SQLiteDatabase database)
	{
		myDatabase = database;
	}
	public ArrayList<Airport> getAirport(){
		ArrayList<Airport> airport = new ArrayList<>();
		request = "SELECT * FROM airport";
		c = myDatabase.rawQuery(request, null);
		while(c.moveToNext()){
			airport.add(new Airport(c.getInt(0),c.getInt(1),c.getString(2),c.getString(3),c.getString(4),c.getDouble(5),c.getInt(6),c.getDouble(7),c.getInt(8),c.getInt(9)));
		}
		return airport;
	}

}
