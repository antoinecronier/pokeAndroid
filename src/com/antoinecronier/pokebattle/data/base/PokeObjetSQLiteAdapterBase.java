
/**************************************************************************
 * PokeObjetSQLiteAdapterBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.data.PokeObjetSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeTypeObjetSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeObjetContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeObjetContract;
import com.antoinecronier.pokebattle.entity.PokeObjet;
import com.antoinecronier.pokebattle.entity.PokeTypeObjet;


import com.antoinecronier.pokebattle.PokebattleApplication;



/** PokeObjet adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PokeObjetAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PokeObjetSQLiteAdapterBase
                        extends SQLiteAdapter<PokeObjet> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeObjetDBAdapter";


    /**
     * Get the table name used in DB for your PokeObjet entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PokeObjetContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your PokeObjet entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PokeObjetContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the PokeObjet entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PokeObjetContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PokeObjetContract.TABLE_NAME    + " ("
        
         + PokeObjetContract.COL_POKENPCOBJETSINTERNAL_ID    + " INTEGER,"
         + PokeObjetContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PokeObjetContract.COL_NOM    + " VARCHAR NOT NULL,"
         + PokeObjetContract.COL_QUANTITY    + " INTEGER NOT NULL,"
         + PokeObjetContract.COL_TYPE_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + PokeObjetContract.COL_POKENPCOBJETSINTERNAL_ID + ") REFERENCES " 
             + PokeNpcContract.TABLE_NAME 
                + " (" + PokeNpcContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokeObjetContract.COL_TYPE_ID + ") REFERENCES " 
             + PokeTypeObjetContract.TABLE_NAME 
                + " (" + PokeTypeObjetContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PokeObjetSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert PokeObjet entity to Content Values for database.
     * @param item PokeObjet entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final PokeObjet item) {
        return PokeObjetContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to PokeObjet entity.
     * @param cursor android.database.Cursor object
     * @return PokeObjet entity
     */
    public PokeObjet cursorToItem(final android.database.Cursor cursor) {
        return PokeObjetContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to PokeObjet entity.
     * @param cursor android.database.Cursor object
     * @param result PokeObjet entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final PokeObjet result) {
        PokeObjetContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read PokeObjet by id in database.
     *
     * @param id Identify of PokeObjet
     * @return PokeObjet entity
     */
    public PokeObjet getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final PokeObjet result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getType() != null) {
            final PokeTypeObjetSQLiteAdapter typeAdapter =
                    new PokeTypeObjetSQLiteAdapter(this.ctx);
            typeAdapter.open(this.mDatabase);

            result.setType(typeAdapter.getByID(
                            result.getType().getId()));
        }
        return result;
    }

    /**
     * Find & read PokeObjet by PokeNpcobjetsInternal.
     * @param pokenpcobjetsinternalId pokenpcobjetsinternalId
     * @param orderBy Order by string (can be null)
     * @return List of PokeObjet entities
     */
     public android.database.Cursor getByPokeNpcobjetsInternal(final int pokenpcobjetsinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokeObjetContract.COL_POKENPCOBJETSINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(pokenpcobjetsinternalId);
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
     * Find & read PokeObjet by type.
     * @param typeId typeId
     * @param orderBy Order by string (can be null)
     * @return List of PokeObjet entities
     */
     public android.database.Cursor getByType(final int typeId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokeObjetContract.COL_TYPE_ID + "= ?";
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
     * Read All PokeObjets entities.
     *
     * @return List of PokeObjet entities
     */
    public ArrayList<PokeObjet> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<PokeObjet> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a PokeObjet entity into database.
     *
     * @param item The PokeObjet entity to persist
     * @return Id of the PokeObjet entity
     */
    public long insert(final PokeObjet item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokeObjetContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeObjetContract.itemToContentValues(item, 0);
        values.remove(PokeObjetContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PokeObjetContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a PokeObjet entity into database whether.
     * it already exists or not.
     *
     * @param item The PokeObjet entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final PokeObjet item) {
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
     * Update a PokeObjet entity into database.
     *
     * @param item The PokeObjet entity to persist
     * @return count of updated entities
     */
    public int update(final PokeObjet item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokeObjetContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeObjetContract.itemToContentValues(item, 0);
        final String whereClause =
                 PokeObjetContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Update a PokeObjet entity into database.
     *
     * @param item The PokeObjet entity to persist
     * @param pokenpcId The pokenpc id
     * @return count of updated entities
     */
    public int updateWithPokeNpcObjets(
                    PokeObjet item,
                    int pokeNpcobjetsInternalId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokeObjetContract.TABLE_NAME + ")");
        }

        ContentValues values =
                PokeObjetContract.itemToContentValues(item);
        values.put(
                PokeObjetContract.COL_POKENPCOBJETSINTERNAL_ID,
                pokeNpcobjetsInternalId);
        String whereClause =
                 PokeObjetContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a PokeObjet entity into database whether.
     * it already exists or not.
     *
     * @param item The PokeObjet entity to persist
     * @param pokenpcId The pokenpc id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithPokeNpcObjets(
            PokeObjet item, int pokenpcId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithPokeNpcObjets(item,
                    pokenpcId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithPokeNpcObjets(item,
                    pokenpcId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a PokeObjet entity into database.
     *
     * @param item The PokeObjet entity to persist
     * @param pokenpcId The pokenpc id
     * @return Id of the PokeObjet entity
     */
    public long insertWithPokeNpcObjets(
            PokeObjet item, int pokenpcId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokeObjetContract.TABLE_NAME + ")");
        }

        ContentValues values = PokeObjetContract.itemToContentValues(item,
                pokenpcId);
        values.remove(PokeObjetContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);


        return newid;
    }


    /**
     * Delete a PokeObjet entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PokeObjetContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PokeObjetContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param pokeObjet The entity to delete
     * @return count of updated entities
     */
    public int delete(final PokeObjet pokeObjet) {
        return this.remove(pokeObjet.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the PokeObjet corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PokeObjetContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PokeObjetContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a PokeObjet entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PokeObjetContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PokeObjetContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

