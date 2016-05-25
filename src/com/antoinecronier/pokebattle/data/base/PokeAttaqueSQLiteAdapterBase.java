
/**************************************************************************
 * PokeAttaqueSQLiteAdapterBase.java, pokebattle Android
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


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.antoinecronier.pokebattle.data.SQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeAttaqueSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeTypeSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeAttaqueContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.entity.PokeAttaque;
import com.antoinecronier.pokebattle.entity.PokeType;


import com.antoinecronier.pokebattle.PokebattleApplication;



/** PokeAttaque adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PokeAttaqueAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PokeAttaqueSQLiteAdapterBase
                        extends SQLiteAdapter<PokeAttaque> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeAttaqueDBAdapter";


    /**
     * Get the table name used in DB for your PokeAttaque entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PokeAttaqueContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your PokeAttaque entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PokeAttaqueContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the PokeAttaque entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PokeAttaqueContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PokeAttaqueContract.TABLE_NAME    + " ("
        
         + PokeAttaqueContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PokeAttaqueContract.COL_NOM    + " VARCHAR NOT NULL,"
         + PokeAttaqueContract.COL_PUISSANCE    + " INTEGER NOT NULL,"
         + PokeAttaqueContract.COL_PRECISION    + " INTEGER NOT NULL,"
         + PokeAttaqueContract.COL_TYPE_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + PokeAttaqueContract.COL_TYPE_ID + ") REFERENCES " 
             + PokeTypeContract.TABLE_NAME 
                + " (" + PokeTypeContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PokeAttaqueSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert PokeAttaque entity to Content Values for database.
     * @param item PokeAttaque entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final PokeAttaque item) {
        return PokeAttaqueContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to PokeAttaque entity.
     * @param cursor android.database.Cursor object
     * @return PokeAttaque entity
     */
    public PokeAttaque cursorToItem(final android.database.Cursor cursor) {
        return PokeAttaqueContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to PokeAttaque entity.
     * @param cursor android.database.Cursor object
     * @param result PokeAttaque entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final PokeAttaque result) {
        PokeAttaqueContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read PokeAttaque by id in database.
     *
     * @param id Identify of PokeAttaque
     * @return PokeAttaque entity
     */
    public PokeAttaque getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final PokeAttaque result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getType() != null) {
            final PokeTypeSQLiteAdapter typeAdapter =
                    new PokeTypeSQLiteAdapter(this.ctx);
            typeAdapter.open(this.mDatabase);

            result.setType(typeAdapter.getByID(
                            result.getType().getId()));
        }
        return result;
    }

    /**
     * Find & read PokeAttaque by type.
     * @param typeId typeId
     * @param orderBy Order by string (can be null)
     * @return List of PokeAttaque entities
     */
     public android.database.Cursor getByType(final int typeId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokeAttaqueContract.COL_TYPE_ID + "= ?";
        String idSelectionArgs = String.valueOf(typeId);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }

    /**
     * Read All PokeAttaques entities.
     *
     * @return List of PokeAttaque entities
     */
    public ArrayList<PokeAttaque> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<PokeAttaque> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a PokeAttaque entity into database.
     *
     * @param item The PokeAttaque entity to persist
     * @return Id of the PokeAttaque entity
     */
    public long insert(final PokeAttaque item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokeAttaqueContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeAttaqueContract.itemToContentValues(item);
        values.remove(PokeAttaqueContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PokeAttaqueContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a PokeAttaque entity into database whether.
     * it already exists or not.
     *
     * @param item The PokeAttaque entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final PokeAttaque item) {
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
     * Update a PokeAttaque entity into database.
     *
     * @param item The PokeAttaque entity to persist
     * @return count of updated entities
     */
    public int update(final PokeAttaque item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokeAttaqueContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeAttaqueContract.itemToContentValues(item);
        final String whereClause =
                 PokeAttaqueContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a PokeAttaque entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PokeAttaqueContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PokeAttaqueContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param pokeAttaque The entity to delete
     * @return count of updated entities
     */
    public int delete(final PokeAttaque pokeAttaque) {
        return this.remove(pokeAttaque.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the PokeAttaque corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PokeAttaqueContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PokeAttaqueContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a PokeAttaque entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PokeAttaqueContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PokeAttaqueContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

