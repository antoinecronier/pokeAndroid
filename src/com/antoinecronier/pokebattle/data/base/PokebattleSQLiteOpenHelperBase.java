
/**************************************************************************
 * PokebattleSQLiteOpenHelperBase.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.data.base;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.antoinecronier.pokebattle.data.PokebattleSQLiteOpenHelper;
import com.antoinecronier.pokebattle.data.PokeAreneSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeAreneContract;
import com.antoinecronier.pokebattle.data.PokeBadgeSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;
import com.antoinecronier.pokebattle.data.PokeZoneSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;
import com.antoinecronier.pokebattle.data.PokeTypeSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.data.PokePositionSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokePositionContract;
import com.antoinecronier.pokebattle.data.PokePokemonSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokePokemonContract;
import com.antoinecronier.pokebattle.data.PokeObjetSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeObjetContract;
import com.antoinecronier.pokebattle.data.PokeAttaqueSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeAttaqueContract;
import com.antoinecronier.pokebattle.data.PokeDresseurSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeDresseurContract;
import com.antoinecronier.pokebattle.data.PokeTypePokemonSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;
import com.antoinecronier.pokebattle.data.PokeTypeObjetSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeObjetContract;
import com.antoinecronier.pokebattle.data.PokeNpcSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.PokebattleApplication;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


import com.antoinecronier.pokebattle.fixture.DataLoader;


/**
 * This class makes it easy for ContentProvider implementations to defer <br />
 * opening and upgrading the database until first use, to avoid blocking <br />
 * application startup with long-running database upgrades.
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class PokebattleSQLiteOpenHelperBase extends SQLiteOpenHelper {
    /** TAG for debug purpose. */
    protected static final String TAG = "DatabaseHelper";
    /** Context. */
    protected android.content.Context ctx;

    /** Android's default system path of the database. */
    private static String DB_PATH;
    /** database name. */
    private static String DB_NAME;
    /** is assets exist.*/
    private static boolean assetsExist;
    /** Are we in a JUnit context ?*/
    public static boolean isJUnit = false;

    /**
     * Constructor.
     * @param ctx Context
     * @param name name
     * @param factory factory
     * @param version version
     */
    public PokebattleSQLiteOpenHelperBase(final android.content.Context ctx,
           final String name, final CursorFactory factory, final int version) {
        super(ctx, name, factory, version);
        this.ctx = ctx;
        DB_NAME = name;
        DB_PATH = ctx.getDatabasePath(DB_NAME).getAbsolutePath();

        try {
            this.ctx.getAssets().open(DB_NAME);
            assetsExist = true;
        } catch (IOException e) {
            assetsExist = false;
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // Activation of SQLiteConstraints
        //db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        android.util.Log.i(TAG, "Create database..");

        if (!assetsExist) {
            /// Create Schema

            if (PokebattleApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : PokeArene");
            }
            db.execSQL(PokeAreneSQLiteAdapter.getSchema());

            if (PokebattleApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : PokeBadge");
            }
            db.execSQL(PokeBadgeSQLiteAdapter.getSchema());

            if (PokebattleApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : PokeZone");
            }
            db.execSQL(PokeZoneSQLiteAdapter.getSchema());

            if (PokebattleApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : PokeType");
            }
            db.execSQL(PokeTypeSQLiteAdapter.getSchema());

            if (PokebattleApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : PokePosition");
            }
            db.execSQL(PokePositionSQLiteAdapter.getSchema());

            if (PokebattleApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : PokePokemon");
            }
            db.execSQL(PokePokemonSQLiteAdapter.getSchema());

            if (PokebattleApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : PokeObjet");
            }
            db.execSQL(PokeObjetSQLiteAdapter.getSchema());

            if (PokebattleApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : PokeAttaque");
            }
            db.execSQL(PokeAttaqueSQLiteAdapter.getSchema());

            if (PokebattleApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : PokeDresseur");
            }
            db.execSQL(PokeDresseurSQLiteAdapter.getSchema());

            if (PokebattleApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : PokeTypePokemon");
            }
            db.execSQL(PokeTypePokemonSQLiteAdapter.getSchema());

            if (PokebattleApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : PokeTypeObjet");
            }
            db.execSQL(PokeTypeObjetSQLiteAdapter.getSchema());

            if (PokebattleApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : PokeNpc");
            }
            db.execSQL(PokeNpcSQLiteAdapter.getSchema());
            db.execSQL("PRAGMA foreign_keys = ON;");
            if (!PokebattleSQLiteOpenHelper.isJUnit) {
                this.loadData(db);
            }
        }

    }

    /**
     * Clear the database given in parameters.
     * @param db The database to clear
     */
    public static void clearDatabase(final SQLiteDatabase db) {
        android.util.Log.i(TAG, "Clearing database...");

        db.delete(PokeAreneContract.TABLE_NAME,
                null,
                null);
        db.delete(PokeBadgeContract.TABLE_NAME,
                null,
                null);
        db.delete(PokeZoneContract.TABLE_NAME,
                null,
                null);
        db.delete(PokeTypeContract.TABLE_NAME,
                null,
                null);
        db.delete(PokePositionContract.TABLE_NAME,
                null,
                null);
        db.delete(PokePokemonContract.TABLE_NAME,
                null,
                null);
        db.delete(PokeObjetContract.TABLE_NAME,
                null,
                null);
        db.delete(PokeAttaqueContract.TABLE_NAME,
                null,
                null);
        db.delete(PokeDresseurContract.TABLE_NAME,
                null,
                null);
        db.delete(PokeTypePokemonContract.TABLE_NAME,
                null,
                null);
        db.delete(PokeTypeObjetContract.TABLE_NAME,
                null,
                null);
        db.delete(PokeNpcContract.TABLE_NAME,
                null,
                null);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
            final int newVersion) {
        android.util.Log.i(TAG, "Update database..");

        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Upgrading database from version " + oldVersion
                       + " to " + newVersion);
        }

        // TODO : Upgrade your tables !
    }

    /**
     * Loads data from the fixture files.
     * @param db The database to populate with fixtures
     */
    private void loadData(final SQLiteDatabase db) {
        final DataLoader dataLoader = new DataLoader(this.ctx);
        dataLoader.clean();
        int mode = DataLoader.MODE_APP;
        if (PokebattleApplication.DEBUG) {
            mode = DataLoader.MODE_APP | DataLoader.MODE_DEBUG;
        }
        dataLoader.loadData(db, mode);
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     * @throws IOException if error has occured while copying files
     */
    public void createDataBase() throws IOException {
        if (assetsExist && !checkDataBase()) {
            // By calling this method and empty database will be created into
            // the default system path
            // so we're gonna be able to overwrite that database with ours
            this.getReadableDatabase();

            try {
                copyDataBase();

            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        boolean result;

        SQLiteDatabase checkDB = null;
        try {
            final String myPath = DB_PATH + DB_NAME;
            // NOTE : the system throw error message : "Database is locked"
            // when the Database is not found (incorrect path)
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
            result = true;
        } catch (SQLiteException e) {
            // database doesn't exist yet.
            result = false;
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return result;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     * @throws IOException if error has occured while copying files
     * */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        final InputStream myInput = this.ctx.getAssets().open(DB_NAME);

        // Path to the just created empty db
        final String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        final OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        final byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while (length > 0) {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
}
