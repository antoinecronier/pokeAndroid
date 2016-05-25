
/**************************************************************************
 * PokeTypePokemonSQLiteAdapterBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.data.PokeTypePokemonSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeTypeSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeZoneSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;
import com.antoinecronier.pokebattle.entity.PokeTypePokemon;
import com.antoinecronier.pokebattle.entity.PokeType;
import com.antoinecronier.pokebattle.entity.PokeZone;


import com.antoinecronier.pokebattle.PokebattleApplication;



/** PokeTypePokemon adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PokeTypePokemonAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PokeTypePokemonSQLiteAdapterBase
                        extends SQLiteAdapter<PokeTypePokemon> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeTypePokemonDBAdapter";


    /**
     * Get the table name used in DB for your PokeTypePokemon entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PokeTypePokemonContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your PokeTypePokemon entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PokeTypePokemonContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the PokeTypePokemon entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PokeTypePokemonContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PokeTypePokemonContract.TABLE_NAME    + " ("
        
         + PokeTypePokemonContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PokeTypePokemonContract.COL_NOM    + " VARCHAR NOT NULL,"
         + PokeTypePokemonContract.COL_ATTAQUE    + " INTEGER NOT NULL,"
         + PokeTypePokemonContract.COL_ATTAQUE_SPE    + " INTEGER NOT NULL,"
         + PokeTypePokemonContract.COL_DEFENCE    + " INTEGER NOT NULL,"
         + PokeTypePokemonContract.COL_DEFENCE_SPE    + " INTEGER NOT NULL,"
         + PokeTypePokemonContract.COL_VITESSE    + " INTEGER NOT NULL,"
         + PokeTypePokemonContract.COL_PV    + " INTEGER NOT NULL,"
         + PokeTypePokemonContract.COL_POKEDEX    + " INTEGER NOT NULL,"
         + PokeTypePokemonContract.COL_EVOLUE_ID    + " INTEGER,"

        
         + "FOREIGN KEY(" + PokeTypePokemonContract.COL_EVOLUE_ID + ") REFERENCES " 
             + PokeTypePokemonContract.TABLE_NAME 
                + " (" + PokeTypePokemonContract.COL_ID + ")"
        + ", UNIQUE(" + PokeTypePokemonContract.COL_POKEDEX + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PokeTypePokemonSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert PokeTypePokemon entity to Content Values for database.
     * @param item PokeTypePokemon entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final PokeTypePokemon item) {
        return PokeTypePokemonContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to PokeTypePokemon entity.
     * @param cursor android.database.Cursor object
     * @return PokeTypePokemon entity
     */
    public PokeTypePokemon cursorToItem(final android.database.Cursor cursor) {
        return PokeTypePokemonContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to PokeTypePokemon entity.
     * @param cursor android.database.Cursor object
     * @param result PokeTypePokemon entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final PokeTypePokemon result) {
        PokeTypePokemonContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read PokeTypePokemon by id in database.
     *
     * @param id Identify of PokeTypePokemon
     * @return PokeTypePokemon entity
     */
    public PokeTypePokemon getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final PokeTypePokemon result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getEvolue() != null) {
            final PokeTypePokemonSQLiteAdapter evolueAdapter =
                    new PokeTypePokemonSQLiteAdapter(this.ctx);
            evolueAdapter.open(this.mDatabase);

            result.setEvolue(evolueAdapter.getByID(
                            result.getEvolue().getId()));
        }
        final PokeTypeSQLiteAdapter typesAdapter =
                new PokeTypeSQLiteAdapter(this.ctx);
        typesAdapter.open(this.mDatabase);
        android.database.Cursor typesCursor = typesAdapter
                    .getByPokeTypePokemontypesInternal(
                            result.getId(),
                            PokeTypeContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setTypes(typesAdapter.cursorToItems(typesCursor));

        typesCursor.close();
        final PokeZoneSQLiteAdapter zonesAdapter =
                new PokeZoneSQLiteAdapter(this.ctx);
        zonesAdapter.open(this.mDatabase);
        android.database.Cursor zonesCursor = zonesAdapter
                    .getByPokeTypePokemonzonesInternal(
                            result.getId(),
                            PokeZoneContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setZones(zonesAdapter.cursorToItems(zonesCursor));

        zonesCursor.close();
        return result;
    }

    /**
     * Find & read PokeTypePokemon by evolue.
     * @param evolueId evolueId
     * @param orderBy Order by string (can be null)
     * @return List of PokeTypePokemon entities
     */
     public android.database.Cursor getByEvolue(final int evolueId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokeTypePokemonContract.COL_EVOLUE_ID + "= ?";
        String idSelectionArgs = String.valueOf(evolueId);
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
     * Read All PokeTypePokemons entities.
     *
     * @return List of PokeTypePokemon entities
     */
    public ArrayList<PokeTypePokemon> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<PokeTypePokemon> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a PokeTypePokemon entity into database.
     *
     * @param item The PokeTypePokemon entity to persist
     * @return Id of the PokeTypePokemon entity
     */
    public long insert(final PokeTypePokemon item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokeTypePokemonContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeTypePokemonContract.itemToContentValues(item);
        values.remove(PokeTypePokemonContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PokeTypePokemonContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getTypes() != null) {
            PokeTypeSQLiteAdapterBase typesAdapter =
                    new PokeTypeSQLiteAdapter(this.ctx);
            typesAdapter.open(this.mDatabase);
            for (PokeType poketype
                        : item.getTypes()) {
                typesAdapter.insertOrUpdateWithPokeTypePokemonTypes(
                                    poketype,
                                    insertResult);
            }
        }
        if (item.getZones() != null) {
            PokeZoneSQLiteAdapterBase zonesAdapter =
                    new PokeZoneSQLiteAdapter(this.ctx);
            zonesAdapter.open(this.mDatabase);
            for (PokeZone pokezone
                        : item.getZones()) {
                zonesAdapter.insertOrUpdateWithPokeTypePokemonZones(
                                    pokezone,
                                    insertResult);
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a PokeTypePokemon entity into database whether.
     * it already exists or not.
     *
     * @param item The PokeTypePokemon entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final PokeTypePokemon item) {
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
     * Update a PokeTypePokemon entity into database.
     *
     * @param item The PokeTypePokemon entity to persist
     * @return count of updated entities
     */
    public int update(final PokeTypePokemon item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokeTypePokemonContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeTypePokemonContract.itemToContentValues(item);
        final String whereClause =
                 PokeTypePokemonContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a PokeTypePokemon entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PokeTypePokemonContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PokeTypePokemonContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param pokeTypePokemon The entity to delete
     * @return count of updated entities
     */
    public int delete(final PokeTypePokemon pokeTypePokemon) {
        return this.remove(pokeTypePokemon.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the PokeTypePokemon corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PokeTypePokemonContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PokeTypePokemonContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a PokeTypePokemon entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PokeTypePokemonContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PokeTypePokemonContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

