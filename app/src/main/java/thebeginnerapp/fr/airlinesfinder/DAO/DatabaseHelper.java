package thebeginnerapp.fr.airlinesfinder.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by Kevin Giroux on 14/07/2014. Update the 17/05/2015
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH;
    private static String DB_NAME = "AirlinesFinder.db";
    private final Context myContext;
    private int VERSION_NUMBER = 2;
    private SQLiteDatabase myDataBase;
    private String[] list;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
        list = myContext.fileList();
        DB_PATH = context.getDatabasePath(DB_NAME).getPath();
    }

    public String[] getList() {
        return list;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.e("Update Data", "Update DB new Version Available" + sqLiteDatabase.getVersion());
        Log.e("DB", String.format("AirlinesFinder.onUpgrade(%d -> %d)", oldVersion, newVersion));
        myContext.deleteDatabase(DB_NAME);
        this.getWritableDatabase();
        //this.getReadableDatabase();
        //Sthis.close();
        try {
            //Copy the database from assests
            copyDataBase();
            Log.e("DB", "UPDATE DB complete");
        } catch (IOException mIOException) {
            throw new Error("ErrorCopyingDataBase");
        }
        try {
            openDataBase();
            myDataBase.setVersion(newVersion);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDataBase();
                Log.e("DB", "createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
            try {
                openDataBase();
                myDataBase.setVersion(VERSION_NUMBER);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            try {
                openDataBase();
                if (VERSION_NUMBER > myDataBase.getVersion()) {
                    this.onUpgrade(myDataBase, myDataBase.getVersion(), VERSION_NUMBER);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        String myPath = DB_PATH + DB_NAME;
        File dbFile = myContext.getDatabasePath(myPath);
        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public SQLiteDatabase getMyDataBase() {
        this.getWritableDatabase();
        myDataBase.setVersion(VERSION_NUMBER);
        return myDataBase;
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openOrCreateDatabase(myPath, null);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // onUpgrade(db,oldVersion,newVersion);
    }

    public synchronized void close() {

        if (myDataBase != null) {
            myDataBase.close();
        }

        super.close();

    }
    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

}

