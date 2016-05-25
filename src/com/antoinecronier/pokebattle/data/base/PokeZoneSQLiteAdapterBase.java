
/**************************************************************************
 * PokeZoneSQLiteAdapterBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.data.PokeZoneSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;
import com.antoinecronier.pokebattle.entity.PokeZone;


import com.antoinecronier.pokebattle.PokebattleApplication;



/** PokeZone adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PokeZoneAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PokeZoneSQLiteAdapterBase
                        extends SQLiteAdapter<PokeZone> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeZoneDBAdapter";


    /**
     * Get the table name used in DB for your PokeZone entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PokeZoneContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your PokeZone entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PokeZoneContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the PokeZone entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PokeZoneContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PokeZoneContract.TABLE_NAME    + " ("
        
         + PokeZoneContract.COL_POKETYPEPOKEMONZONESINTERNAL_ID    + " INTEGER,"
         + PokeZoneContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PokeZoneContract.COL_NOM    + " VARCHAR NOT NULL,"

        
         + "FOREIGN KEY(" + PokeZoneContract.COL_POKETYPEPOKEMONZONESINTERNAL_ID + ") REFERENCES " 
             + PokeTypePokemonContract.TABLE_NAME 
                + " (" + PokeTypePokemonContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PokeZoneSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert PokeZone entity to Content Values for database.
     * @param item PokeZone entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final PokeZone item) {
        return PokeZoneContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to PokeZone entity.
     * @param cursor android.database.Cursor object
     * @return PokeZone entity
     */
    public PokeZone cursorToItem(final android.database.Cursor cursor) {
        return PokeZoneContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to PokeZone entity.
     * @param cursor android.database.Cursor object
     * @param result PokeZone entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final PokeZone result) {
        PokeZoneContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read PokeZone by id in database.
     *
     * @param id Identify of PokeZone
     * @return PokeZone entity
     */
    public PokeZone getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final PokeZone result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }

    /**
     * Find & read PokeZone by PokeTypePokemonzonesInternal.
     * @param poketypepokemonzonesinternalId poketypepokemonzonesinternalId
     * @param orderBy Order by string (can be null)
     * @return List of PokeZone entities
     */
     public android.database.Cursor getByPokeTypePokemonzonesInternal(final int poketypepokemonzonesinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokeZoneContract.COL_POKETYPEPOKEMONZONESINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(poketypepokemonzonesinternalId);
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
     * Read All PokeZones entities.
     *
     * @return List of PokeZone entities
     */
    public ArrayList<PokeZone> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<PokeZone> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a PokeZone entity into database.
     *
     * @param item The PokeZone entity to persist
     * @return Id of the PokeZone entity
     */
    public long insert(final PokeZone item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokeZoneContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeZoneContract.itemToContentValues(item, 0);
        values.remove(PokeZoneContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PokeZoneContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a PokeZone entity into database whether.
     * it already exists or not.
     *
     * @param item The PokeZone entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final PokeZone item) {
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
     * Update a PokeZone entity into database.
     *
     * @param item The PokeZone entity to persist
     * @return count of updated entities
     */
    public int update(final PokeZone item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokeZoneContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeZoneContract.itemToContentValues(item, 0);
        final String whereClause =
                 PokeZoneContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Update a PokeZone entity into database.
     *
     * @param item The PokeZone entity to persist
     * @param poketypepokemonId The poketypepokemon id
     * @return count of updated entities
     */
    public int updateWithPokeTypePokemonZones(
                    PokeZone item,
                    int pokeTypePokemonzonesInternalId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokeZoneContract.TABLE_NAME + ")");
        }

        ContentValues values =
                PokeZoneContract.itemToContentValues(item);
        values.put(
                PokeZoneContract.COL_POKETYPEPOKEMONZONESINTERNAL_ID,
                pokeTypePokemonzonesInternalId);
        String whereClause =
                 PokeZoneContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a PokeZone entity into database whether.
     * it already exists or not.
     *
     * @param item The PokeZone entity to persist
     * @param poketypepokemonId The poketypepokemon id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithPokeTypePokemonZones(
            PokeZone item, int poketypepokemonId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithPokeTypePokemonZones(item,
                    poketypepokemonId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithPokeTypePokemonZones(item,
                    poketypepokemonId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a PokeZone entity into database.
     *
     * @param item The PokeZone entity to persist
     * @param poketypepokemonId The poketypepokemon id
     * @return Id of the PokeZone entity
     */
    public long insertWithPokeTypePokemonZones(
            PokeZone item, int poketypepokemonId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokeZoneContract.TABLE_NAME + ")");
        }

        ContentValues values = PokeZoneContract.itemToContentValues(item,
                poketypepokemonId);
        values.remove(PokeZoneContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);


        return newid;
    }


    /**
     * Delete a PokeZone entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PokeZoneContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PokeZoneContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param pokeZone The entity to delete
     * @return count of updated entities
     */
    public int delete(final PokeZone pokeZone) {
        return this.remove(pokeZone.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the PokeZone corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PokeZoneContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PokeZoneContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a PokeZone entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PokeZoneContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PokeZoneContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

