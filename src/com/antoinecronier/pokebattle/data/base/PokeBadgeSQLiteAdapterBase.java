
/**************************************************************************
 * PokeBadgeSQLiteAdapterBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.data.PokeBadgeSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.entity.PokeBadgeBonus;


import com.antoinecronier.pokebattle.PokebattleApplication;



/** PokeBadge adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PokeBadgeAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PokeBadgeSQLiteAdapterBase
                        extends SQLiteAdapter<PokeBadge> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeBadgeDBAdapter";


    /**
     * Get the table name used in DB for your PokeBadge entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PokeBadgeContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your PokeBadge entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PokeBadgeContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the PokeBadge entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PokeBadgeContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PokeBadgeContract.TABLE_NAME    + " ("
        
         + PokeBadgeContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PokeBadgeContract.COL_NOM    + " VARCHAR NOT NULL,"
         + PokeBadgeContract.COL_BONUS    + " VARCHAR NOT NULL,"
         + PokeBadgeContract.COL_POKENPCBADGEINTERNAL_ID    + " INTEGER,"

        
         + "FOREIGN KEY(" + PokeBadgeContract.COL_POKENPCBADGEINTERNAL_ID + ") REFERENCES " 
             + PokeNpcContract.TABLE_NAME 
                + " (" + PokeNpcContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PokeBadgeSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert PokeBadge entity to Content Values for database.
     * @param item PokeBadge entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final PokeBadge item) {
        return PokeBadgeContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to PokeBadge entity.
     * @param cursor android.database.Cursor object
     * @return PokeBadge entity
     */
    public PokeBadge cursorToItem(final android.database.Cursor cursor) {
        return PokeBadgeContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to PokeBadge entity.
     * @param cursor android.database.Cursor object
     * @param result PokeBadge entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final PokeBadge result) {
        PokeBadgeContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read PokeBadge by id in database.
     *
     * @param id Identify of PokeBadge
     * @return PokeBadge entity
     */
    public PokeBadge getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final PokeBadge result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }

    /**
     * Find & read PokeBadge by PokeNpcbadgeInternal.
     * @param pokenpcbadgeinternalId pokenpcbadgeinternalId
     * @param orderBy Order by string (can be null)
     * @return List of PokeBadge entities
     */
     public android.database.Cursor getByPokeNpcbadgeInternal(final int pokenpcbadgeinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokeBadgeContract.COL_POKENPCBADGEINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(pokenpcbadgeinternalId);
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
     * Read All PokeBadges entities.
     *
     * @return List of PokeBadge entities
     */
    public ArrayList<PokeBadge> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<PokeBadge> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a PokeBadge entity into database.
     *
     * @param item The PokeBadge entity to persist
     * @return Id of the PokeBadge entity
     */
    public long insert(final PokeBadge item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokeBadgeContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeBadgeContract.itemToContentValues(item, 0);
        values.remove(PokeBadgeContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PokeBadgeContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a PokeBadge entity into database whether.
     * it already exists or not.
     *
     * @param item The PokeBadge entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final PokeBadge item) {
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
     * Update a PokeBadge entity into database.
     *
     * @param item The PokeBadge entity to persist
     * @return count of updated entities
     */
    public int update(final PokeBadge item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokeBadgeContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeBadgeContract.itemToContentValues(item, 0);
        final String whereClause =
                 PokeBadgeContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Update a PokeBadge entity into database.
     *
     * @param item The PokeBadge entity to persist
     * @param pokenpcId The pokenpc id
     * @return count of updated entities
     */
    public int updateWithPokeNpcBadge(
                    PokeBadge item,
                    int pokeNpcbadgeInternalId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokeBadgeContract.TABLE_NAME + ")");
        }

        ContentValues values =
                PokeBadgeContract.itemToContentValues(item);
        values.put(
                PokeBadgeContract.COL_POKENPCBADGEINTERNAL_ID,
                pokeNpcbadgeInternalId);
        String whereClause =
                 PokeBadgeContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a PokeBadge entity into database whether.
     * it already exists or not.
     *
     * @param item The PokeBadge entity to persist
     * @param pokenpcId The pokenpc id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithPokeNpcBadge(
            PokeBadge item, int pokenpcId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithPokeNpcBadge(item,
                    pokenpcId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithPokeNpcBadge(item,
                    pokenpcId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a PokeBadge entity into database.
     *
     * @param item The PokeBadge entity to persist
     * @param pokenpcId The pokenpc id
     * @return Id of the PokeBadge entity
     */
    public long insertWithPokeNpcBadge(
            PokeBadge item, int pokenpcId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokeBadgeContract.TABLE_NAME + ")");
        }

        ContentValues values = PokeBadgeContract.itemToContentValues(item,
                pokenpcId);
        values.remove(PokeBadgeContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);


        return newid;
    }


    /**
     * Delete a PokeBadge entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PokeBadgeContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PokeBadgeContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param pokeBadge The entity to delete
     * @return count of updated entities
     */
    public int delete(final PokeBadge pokeBadge) {
        return this.remove(pokeBadge.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the PokeBadge corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PokeBadgeContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PokeBadgeContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a PokeBadge entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PokeBadgeContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PokeBadgeContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

