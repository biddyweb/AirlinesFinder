package thebeginnerapp.fr.airlinesfinder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.sql.SQLException;

import thebeginnerapp.fr.airlinesfinder.DAO.DatabaseHelper;

public class MainActivity extends Activity implements View.OnClickListener {

	public static DatabaseHelper myDbHelper;

	public static DatabaseHelper getMyDbHelper()
	{
		return myDbHelper;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button Start = (Button) findViewById(R.id.button);
        Button jeu = (Button) findViewById(R.id.button_jeu);
        Button forum = (Button) findViewById(R.id.button_forum);


        jeu.setOnClickListener(this);
        forum.setOnClickListener(this);
		Start.setOnClickListener(this);
		initdata();

	}

	@Override
	public void onClick(View v)
	{
        Intent t;
        String url;
        switch(v.getId()){
            case R.id.button :
                t = new Intent(this, SettingResearch.class);
                startActivity(t);
                break;
            case R.id.button_jeu :
                url = getString(R.string.urlairlines);
                t = new Intent(Intent.ACTION_VIEW);
                t.setData(Uri.parse(url));
                startActivity(t);
                break;
            case R.id.button_forum :
                url = getString(R.string.urlairlinesforum);
                t = new Intent(Intent.ACTION_VIEW);
                t.setData(Uri.parse(url));
                startActivity(t);
                break;
        }
	}


	public void initdata()
	{
		myDbHelper = new DatabaseHelper(this);
		try
		{
			myDbHelper.createDataBase();
		} catch (IOException ioe)
		{
			throw new Error("Unable to create database");
		}
		try
		{
			myDbHelper.openDataBase();
		} catch (SQLException sqle)
		{
			try
			{
				throw sqle;
			} catch (SQLException e)
			{
				e.printStackTrace();
			}

		}
	}
}
