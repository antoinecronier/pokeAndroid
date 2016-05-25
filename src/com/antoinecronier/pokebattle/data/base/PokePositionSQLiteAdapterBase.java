
/**************************************************************************
 * PokePositionSQLiteAdapterBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.data.PokePositionSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokePositionContract;
import com.antoinecronier.pokebattle.entity.PokePosition;


import com.antoinecronier.pokebattle.PokebattleApplication;



/** PokePosition adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PokePositionAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PokePositionSQLiteAdapterBase
                        extends SQLiteAdapter<PokePosition> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokePositionDBAdapter";


    /**
     * Get the table name used in DB for your PokePosition entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PokePositionContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your PokePosition entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PokePositionContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the PokePosition entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PokePositionContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PokePositionContract.TABLE_NAME    + " ("
        
         + PokePositionContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PokePositionContract.COL_X    + " INTEGER NOT NULL,"
         + PokePositionContract.COL_Y    + " INTEGER NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PokePositionSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert PokePosition entity to Content Values for database.
     * @param item PokePosition entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final PokePosition item) {
        return PokePositionContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to PokePosition entity.
     * @param cursor android.database.Cursor object
     * @return PokePosition entity
     */
    public PokePosition cursorToItem(final android.database.Cursor cursor) {
        return PokePositionContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to PokePosition entity.
     * @param cursor android.database.Cursor object
     * @param result PokePosition entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final PokePosition result) {
        PokePositionContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read PokePosition by id in database.
     *
     * @param id Identify of PokePosition
     * @return PokePosition entity
     */
    public PokePosition getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final PokePosition result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }


    /**
     * Read All PokePositions entities.
     *
     * @return List of PokePosition entities
     */
    public ArrayList<PokePosition> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<PokePosition> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a PokePosition entity into database.
     *
     * @param item The PokePosition entity to persist
     * @return Id of the PokePosition entity
     */
    public long insert(final PokePosition item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokePositionContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokePositionContract.itemToContentValues(item);
        values.remove(PokePositionContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PokePositionContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a PokePosition entity into database whether.
     * it already exists or not.
     *
     * @param item The PokePosition entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final PokePosition item) {
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
     * Update a PokePosition entity into database.
     *
     * @param item The PokePosition entity to persist
     * @return count of updated entities
     */
    public int update(final PokePosition item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokePositionContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokePositionContract.itemToContentValues(item);
        final String whereClause =
                 PokePositionContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a PokePosition entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PokePositionContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PokePositionContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param pokePosition The entity to delete
     * @return count of updated entities
     */
    public int delete(final PokePosition pokePosition) {
        return this.remove(pokePosition.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the PokePosition corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PokePositionContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PokePositionContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a PokePosition entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PokePositionContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PokePositionContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

