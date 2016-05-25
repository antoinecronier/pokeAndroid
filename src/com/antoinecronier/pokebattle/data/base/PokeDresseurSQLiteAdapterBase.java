
/**************************************************************************
 * PokeDresseurSQLiteAdapterBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.data.PokeDresseurSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeNpcSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeDresseurContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.entity.PokeDresseur;
import com.antoinecronier.pokebattle.entity.PokeNpc;


import com.antoinecronier.pokebattle.PokebattleApplication;



/** PokeDresseur adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PokeDresseurAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PokeDresseurSQLiteAdapterBase
                        extends SQLiteAdapter<PokeDresseur> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeDresseurDBAdapter";


    /**
     * Get the table name used in DB for your PokeDresseur entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PokeDresseurContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your PokeDresseur entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PokeDresseurContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the PokeDresseur entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PokeDresseurContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PokeDresseurContract.TABLE_NAME    + " ("
        
         + PokeDresseurContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PokeDresseurContract.COL_PSEUDO    + " VARCHAR NOT NULL,"
         + PokeDresseurContract.COL_LOGIN    + " VARCHAR NOT NULL,"
         + PokeDresseurContract.COL_PASSWORD    + " VARCHAR NOT NULL"

        
        + ", UNIQUE(" + PokeDresseurContract.COL_LOGIN + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PokeDresseurSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert PokeDresseur entity to Content Values for database.
     * @param item PokeDresseur entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final PokeDresseur item) {
        return PokeDresseurContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to PokeDresseur entity.
     * @param cursor android.database.Cursor object
     * @return PokeDresseur entity
     */
    public PokeDresseur cursorToItem(final android.database.Cursor cursor) {
        return PokeDresseurContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to PokeDresseur entity.
     * @param cursor android.database.Cursor object
     * @param result PokeDresseur entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final PokeDresseur result) {
        PokeDresseurContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read PokeDresseur by id in database.
     *
     * @param id Identify of PokeDresseur
     * @return PokeDresseur entity
     */
    public PokeDresseur getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final PokeDresseur result = this.cursorToItem(cursor);
        cursor.close();

        final PokeNpcSQLiteAdapter npcsAdapter =
                new PokeNpcSQLiteAdapter(this.ctx);
        npcsAdapter.open(this.mDatabase);
        android.database.Cursor npcsCursor = npcsAdapter
                    .getByPokeDresseurnpcsInternal(
                            result.getId(),
                            PokeNpcContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setNpcs(npcsAdapter.cursorToItems(npcsCursor));

        npcsCursor.close();
        return result;
    }


    /**
     * Read All PokeDresseurs entities.
     *
     * @return List of PokeDresseur entities
     */
    public ArrayList<PokeDresseur> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<PokeDresseur> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a PokeDresseur entity into database.
     *
     * @param item The PokeDresseur entity to persist
     * @return Id of the PokeDresseur entity
     */
    public long insert(final PokeDresseur item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokeDresseurContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeDresseurContract.itemToContentValues(item);
        values.remove(PokeDresseurContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PokeDresseurContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getNpcs() != null) {
            PokeNpcSQLiteAdapterBase npcsAdapter =
                    new PokeNpcSQLiteAdapter(this.ctx);
            npcsAdapter.open(this.mDatabase);
            for (PokeNpc pokenpc
                        : item.getNpcs()) {
                npcsAdapter.insertOrUpdateWithPokeDresseurNpcs(
                                    pokenpc,
                                    insertResult);
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a PokeDresseur entity into database whether.
     * it already exists or not.
     *
     * @param item The PokeDresseur entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final PokeDresseur item) {
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
     * Update a PokeDresseur entity into database.
     *
     * @param item The PokeDresseur entity to persist
     * @return count of updated entities
     */
    public int update(final PokeDresseur item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokeDresseurContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeDresseurContract.itemToContentValues(item);
        final String whereClause =
                 PokeDresseurContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a PokeDresseur entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PokeDresseurContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PokeDresseurContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param pokeDresseur The entity to delete
     * @return count of updated entities
     */
    public int delete(final PokeDresseur pokeDresseur) {
        return this.remove(pokeDresseur.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the PokeDresseur corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PokeDresseurContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PokeDresseurContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a PokeDresseur entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PokeDresseurContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PokeDresseurContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

