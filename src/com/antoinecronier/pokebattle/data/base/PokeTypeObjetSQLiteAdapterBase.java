
/**************************************************************************
 * PokeTypeObjetSQLiteAdapterBase.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.data.base;

import java.util.ArrayList;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.antoinecronier.pokebattle.data.SQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeTypeObjetSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeObjetContract;
import com.antoinecronier.pokebattle.entity.PokeTypeObjet;


import com.antoinecronier.pokebattle.PokebattleApplication;



/** PokeTypeObjet adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PokeTypeObjetAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PokeTypeObjetSQLiteAdapterBase
                        extends SQLiteAdapter<PokeTypeObjet> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeTypeObjetDBAdapter";


    /**
     * Get the table name used in DB for your PokeTypeObjet entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PokeTypeObjetContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your PokeTypeObjet entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PokeTypeObjetContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the PokeTypeObjet entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PokeTypeObjetContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PokeTypeObjetContract.TABLE_NAME    + " ("
        
         + PokeTypeObjetContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PokeTypeObjetContract.COL_NOM    + " VARCHAR NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PokeTypeObjetSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert PokeTypeObjet entity to Content Values for database.
     * @param item PokeTypeObjet entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final PokeTypeObjet item) {
        return PokeTypeObjetContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to PokeTypeObjet entity.
     * @param cursor android.database.Cursor object
     * @return PokeTypeObjet entity
     */
    public PokeTypeObjet cursorToItem(final android.database.Cursor cursor) {
        return PokeTypeObjetContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to PokeTypeObjet entity.
     * @param cursor android.database.Cursor object
     * @param result PokeTypeObjet entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final PokeTypeObjet result) {
        PokeTypeObjetContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read PokeTypeObjet by id in database.
     *
     * @param id Identify of PokeTypeObjet
     * @return PokeTypeObjet entity
     */
    public PokeTypeObjet getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final PokeTypeObjet result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }


    /**
     * Read All PokeTypeObjets entities.
     *
     * @return List of PokeTypeObjet entities
     */
    public ArrayList<PokeTypeObjet> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<PokeTypeObjet> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a PokeTypeObjet entity into database.
     *
     * @param item The PokeTypeObjet entity to persist
     * @return Id of the PokeTypeObjet entity
     */
    public long insert(final PokeTypeObjet item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokeTypeObjetContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeTypeObjetContract.itemToContentValues(item);
        values.remove(PokeTypeObjetContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PokeTypeObjetContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a PokeTypeObjet entity into database whether.
     * it already exists or not.
     *
     * @param item The PokeTypeObjet entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final PokeTypeObjet item) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.update(item);
        } else {
            // Item doesn't exist => create it
            final long id = this.insert(item);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }

    /**
     * Update a PokeTypeObjet entity into database.
     *
     * @param item The PokeTypeObjet entity to persist
     * @return count of updated entities
     */
    public int update(final PokeTypeObjet item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokeTypeObjetContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeTypeObjetContract.itemToContentValues(item);
        final String whereClause =
                 PokeTypeObjetContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a PokeTypeObjet entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PokeTypeObjetContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PokeTypeObjetContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param pokeTypeObjet The entity to delete
     * @return count of updated entities
     */
    public int delete(final PokeTypeObjet pokeTypeObjet) {
        return this.remove(pokeTypeObjet.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the PokeTypeObjet corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PokeTypeObjetContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PokeTypeObjetContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a PokeTypeObjet entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PokeTypeObjetContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PokeTypeObjetContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

