
/**************************************************************************
 * PokePokemonSQLiteAdapterBase.java, pokebattle Android
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
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.antoinecronier.pokebattle.data.SQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokePokemonSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeTypePokemonSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeAttaqueSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokeAttaqueContract;
import com.antoinecronier.pokebattle.entity.PokePokemon;
import com.antoinecronier.pokebattle.entity.PokeTypePokemon;
import com.antoinecronier.pokebattle.entity.PokeAttaque;

import com.antoinecronier.pokebattle.harmony.util.DateUtils;
import com.antoinecronier.pokebattle.PokebattleApplication;



/** PokePokemon adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PokePokemonAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PokePokemonSQLiteAdapterBase
                        extends SQLiteAdapter<PokePokemon> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokePokemonDBAdapter";


    /**
     * Get the table name used in DB for your PokePokemon entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PokePokemonContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your PokePokemon entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PokePokemonContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the PokePokemon entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PokePokemonContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PokePokemonContract.TABLE_NAME    + " ("
        
         + PokePokemonContract.COL_POKENPCPOKEMONSINTERNAL_ID    + " INTEGER,"
         + PokePokemonContract.COL_POKENPCTEAMINTERNAL_ID    + " INTEGER,"
         + PokePokemonContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PokePokemonContract.COL_SURNOM    + " VARCHAR NOT NULL,"
         + PokePokemonContract.COL_NIVEAU    + " INTEGER NOT NULL,"
         + PokePokemonContract.COL_CAPTURE    + " DATETIME,"
         + PokePokemonContract.COL_TYPE_ID    + " INTEGER,"
         + PokePokemonContract.COL_ATTAQUE1_ID    + " INTEGER,"
         + PokePokemonContract.COL_ATTAQUE2_ID    + " INTEGER,"
         + PokePokemonContract.COL_ATTAQUE3_ID    + " INTEGER,"
         + PokePokemonContract.COL_ATTAQUE4_ID    + " INTEGER,"

        
         + "FOREIGN KEY(" + PokePokemonContract.COL_POKENPCPOKEMONSINTERNAL_ID + ") REFERENCES " 
             + PokeNpcContract.TABLE_NAME 
                + " (" + PokeNpcContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokePokemonContract.COL_POKENPCTEAMINTERNAL_ID + ") REFERENCES " 
             + PokeNpcContract.TABLE_NAME 
                + " (" + PokeNpcContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokePokemonContract.COL_TYPE_ID + ") REFERENCES " 
             + PokeTypePokemonContract.TABLE_NAME 
                + " (" + PokeTypePokemonContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokePokemonContract.COL_ATTAQUE1_ID + ") REFERENCES " 
             + PokeAttaqueContract.TABLE_NAME 
                + " (" + PokeAttaqueContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokePokemonContract.COL_ATTAQUE2_ID + ") REFERENCES " 
             + PokeAttaqueContract.TABLE_NAME 
                + " (" + PokeAttaqueContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokePokemonContract.COL_ATTAQUE3_ID + ") REFERENCES " 
             + PokeAttaqueContract.TABLE_NAME 
                + " (" + PokeAttaqueContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokePokemonContract.COL_ATTAQUE4_ID + ") REFERENCES " 
             + PokeAttaqueContract.TABLE_NAME 
                + " (" + PokeAttaqueContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PokePokemonSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert PokePokemon entity to Content Values for database.
     * @param item PokePokemon entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final PokePokemon item) {
        return PokePokemonContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to PokePokemon entity.
     * @param cursor android.database.Cursor object
     * @return PokePokemon entity
     */
    public PokePokemon cursorToItem(final android.database.Cursor cursor) {
        return PokePokemonContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to PokePokemon entity.
     * @param cursor android.database.Cursor object
     * @param result PokePokemon entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final PokePokemon result) {
        PokePokemonContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read PokePokemon by id in database.
     *
     * @param id Identify of PokePokemon
     * @return PokePokemon entity
     */
    public PokePokemon getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final PokePokemon result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getType() != null) {
            final PokeTypePokemonSQLiteAdapter typeAdapter =
                    new PokeTypePokemonSQLiteAdapter(this.ctx);
            typeAdapter.open(this.mDatabase);

            result.setType(typeAdapter.getByID(
                            result.getType().getId()));
        }
        if (result.getAttaque1() != null) {
            final PokeAttaqueSQLiteAdapter attaque1Adapter =
                    new PokeAttaqueSQLiteAdapter(this.ctx);
            attaque1Adapter.open(this.mDatabase);

            result.setAttaque1(attaque1Adapter.getByID(
                            result.getAttaque1().getId()));
        }
        if (result.getAttaque2() != null) {
            final PokeAttaqueSQLiteAdapter attaque2Adapter =
                    new PokeAttaqueSQLiteAdapter(this.ctx);
            attaque2Adapter.open(this.mDatabase);

            result.setAttaque2(attaque2Adapter.getByID(
                            result.getAttaque2().getId()));
        }
        if (result.getAttaque3() != null) {
            final PokeAttaqueSQLiteAdapter attaque3Adapter =
                    new PokeAttaqueSQLiteAdapter(this.ctx);
            attaque3Adapter.open(this.mDatabase);

            result.setAttaque3(attaque3Adapter.getByID(
                            result.getAttaque3().getId()));
        }
        if (result.getAttaque4() != null) {
            final PokeAttaqueSQLiteAdapter attaque4Adapter =
                    new PokeAttaqueSQLiteAdapter(this.ctx);
            attaque4Adapter.open(this.mDatabase);

            result.setAttaque4(attaque4Adapter.getByID(
                            result.getAttaque4().getId()));
        }
        return result;
    }

    /**
     * Find & read PokePokemon by PokeNpcpokemonsInternal.
     * @param pokenpcpokemonsinternalId pokenpcpokemonsinternalId
     * @param orderBy Order by string (can be null)
     * @return List of PokePokemon entities
     */
     public android.database.Cursor getByPokeNpcpokemonsInternal(final int pokenpcpokemonsinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokePokemonContract.COL_POKENPCPOKEMONSINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(pokenpcpokemonsinternalId);
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
     * Find & read PokePokemon by PokeNpcteamInternal.
     * @param pokenpcteaminternalId pokenpcteaminternalId
     * @param orderBy Order by string (can be null)
     * @return List of PokePokemon entities
     */
     public android.database.Cursor getByPokeNpcteamInternal(final int pokenpcteaminternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokePokemonContract.COL_POKENPCTEAMINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(pokenpcteaminternalId);
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
     * Find & read PokePokemon by type.
     * @param typeId typeId
     * @param orderBy Order by string (can be null)
     * @return List of PokePokemon entities
     */
     public android.database.Cursor getByType(final int typeId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokePokemonContract.COL_TYPE_ID + "= ?";
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
     * Find & read PokePokemon by attaque1.
     * @param attaque1Id attaque1Id
     * @param orderBy Order by string (can be null)
     * @return List of PokePokemon entities
     */
     public android.database.Cursor getByAttaque1(final int attaque1Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokePokemonContract.COL_ATTAQUE1_ID + "= ?";
        String idSelectionArgs = String.valueOf(attaque1Id);
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
     * Find & read PokePokemon by attaque2.
     * @param attaque2Id attaque2Id
     * @param orderBy Order by string (can be null)
     * @return List of PokePokemon entities
     */
     public android.database.Cursor getByAttaque2(final int attaque2Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokePokemonContract.COL_ATTAQUE2_ID + "= ?";
        String idSelectionArgs = String.valueOf(attaque2Id);
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
     * Find & read PokePokemon by attaque3.
     * @param attaque3Id attaque3Id
     * @param orderBy Order by string (can be null)
     * @return List of PokePokemon entities
     */
     public android.database.Cursor getByAttaque3(final int attaque3Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokePokemonContract.COL_ATTAQUE3_ID + "= ?";
        String idSelectionArgs = String.valueOf(attaque3Id);
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
     * Find & read PokePokemon by attaque4.
     * @param attaque4Id attaque4Id
     * @param orderBy Order by string (can be null)
     * @return List of PokePokemon entities
     */
     public android.database.Cursor getByAttaque4(final int attaque4Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokePokemonContract.COL_ATTAQUE4_ID + "= ?";
        String idSelectionArgs = String.valueOf(attaque4Id);
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
     * Read All PokePokemons entities.
     *
     * @return List of PokePokemon entities
     */
    public ArrayList<PokePokemon> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<PokePokemon> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a PokePokemon entity into database.
     *
     * @param item The PokePokemon entity to persist
     * @return Id of the PokePokemon entity
     */
    public long insert(final PokePokemon item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokePokemonContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokePokemonContract.itemToContentValues(item, 0, 0);
        values.remove(PokePokemonContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PokePokemonContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a PokePokemon entity into database whether.
     * it already exists or not.
     *
     * @param item The PokePokemon entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final PokePokemon item) {
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
     * Update a PokePokemon entity into database.
     *
     * @param item The PokePokemon entity to persist
     * @return count of updated entities
     */
    public int update(final PokePokemon item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokePokemonContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokePokemonContract.itemToContentValues(item, 0, 0);
        final String whereClause =
                 PokePokemonContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Update a PokePokemon entity into database.
     *
     * @param item The PokePokemon entity to persist
     * @param pokenpcId The pokenpc id
     * @return count of updated entities
     */
    public int updateWithPokeNpcPokemons(
                    PokePokemon item,
                    int pokeNpcpokemonsInternalId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokePokemonContract.TABLE_NAME + ")");
        }

        ContentValues values =
                PokePokemonContract.itemToContentValues(item);
        values.put(
                PokePokemonContract.COL_POKENPCPOKEMONSINTERNAL_ID,
                pokeNpcpokemonsInternalId);
        String whereClause =
                 PokePokemonContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a PokePokemon entity into database whether.
     * it already exists or not.
     *
     * @param item The PokePokemon entity to persist
     * @param pokenpcId The pokenpc id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithPokeNpcPokemons(
            PokePokemon item, int pokenpcId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithPokeNpcPokemons(item,
                    pokenpcId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithPokeNpcPokemons(item,
                    pokenpcId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a PokePokemon entity into database.
     *
     * @param item The PokePokemon entity to persist
     * @param pokenpcId The pokenpc id
     * @return Id of the PokePokemon entity
     */
    public long insertWithPokeNpcPokemons(
            PokePokemon item, int pokenpcId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokePokemonContract.TABLE_NAME + ")");
        }

        ContentValues values = PokePokemonContract.itemToContentValues(item,
                pokenpcId,
                0);
        values.remove(PokePokemonContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);


        return newid;
    }


    /**
     * Update a PokePokemon entity into database.
     *
     * @param item The PokePokemon entity to persist
     * @param pokenpcId The pokenpc id
     * @return count of updated entities
     */
    public int updateWithPokeNpcTeam(
                    PokePokemon item,
                    int pokeNpcteamInternalId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokePokemonContract.TABLE_NAME + ")");
        }

        ContentValues values =
                PokePokemonContract.itemToContentValues(item);
        values.put(
                PokePokemonContract.COL_POKENPCTEAMINTERNAL_ID,
                pokeNpcteamInternalId);
        String whereClause =
                 PokePokemonContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a PokePokemon entity into database whether.
     * it already exists or not.
     *
     * @param item The PokePokemon entity to persist
     * @param pokenpcId The pokenpc id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithPokeNpcTeam(
            PokePokemon item, int pokenpcId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithPokeNpcTeam(item,
                    pokenpcId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithPokeNpcTeam(item,
                    pokenpcId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a PokePokemon entity into database.
     *
     * @param item The PokePokemon entity to persist
     * @param pokenpcId The pokenpc id
     * @return Id of the PokePokemon entity
     */
    public long insertWithPokeNpcTeam(
            PokePokemon item, int pokenpcId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokePokemonContract.TABLE_NAME + ")");
        }

        ContentValues values = PokePokemonContract.itemToContentValues(item,
                0,
                pokenpcId);
        values.remove(PokePokemonContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);


        return newid;
    }


    /**
     * Delete a PokePokemon entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PokePokemonContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PokePokemonContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param pokePokemon The entity to delete
     * @return count of updated entities
     */
    public int delete(final PokePokemon pokePokemon) {
        return this.remove(pokePokemon.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the PokePokemon corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PokePokemonContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PokePokemonContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a PokePokemon entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PokePokemonContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PokePokemonContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

