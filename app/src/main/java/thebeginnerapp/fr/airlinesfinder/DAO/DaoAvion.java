package thebeginnerapp.fr.airlinesfinder.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import thebeginnerapp.fr.airlinesfinder.Utils.Airplane;

/**
 * File created by Kevin on 08/02/2015 for AirlinesFinder
 */
public class DaoAvion {
	SQLiteDatabase myDatabase;
	Cursor c;
	String request;

	public DaoAvion(SQLiteDatabase database)
	{
		myDatabase = database;
	}
	public ArrayList<Airplane> getAvion(){
		ArrayList<Airplane> airplanes = new ArrayList<>();
		request = "SELECT * FROM airplane";
		c = myDatabase.rawQuery(request, null);
		while(c.moveToNext()){
			airplanes.add(new Airplane(c.getInt(0),c.getString(1),c.getInt(2),c.getInt(3),c.getFloat(4),c.getInt(5),c.getInt(6),c.getInt(7),c.getInt(8),c.getInt(9),c.getString(10),c.getInt(11),c.getInt(12)));
		}
			return airplanes;
	}

}

