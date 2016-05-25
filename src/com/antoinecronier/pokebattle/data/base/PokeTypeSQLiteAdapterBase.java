
/**************************************************************************
 * PokeTypeSQLiteAdapterBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.data.PokeTypeSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;
import com.antoinecronier.pokebattle.entity.PokeType;


import com.antoinecronier.pokebattle.PokebattleApplication;



/** PokeType adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PokeTypeAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PokeTypeSQLiteAdapterBase
                        extends SQLiteAdapter<PokeType> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeTypeDBAdapter";


    /**
     * Get the table name used in DB for your PokeType entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PokeTypeContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your PokeType entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PokeTypeContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the PokeType entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PokeTypeContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PokeTypeContract.TABLE_NAME    + " ("
        
         + PokeTypeContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PokeTypeContract.COL_NOM    + " VARCHAR NOT NULL,"
         + PokeTypeContract.COL_MODIFICATEUR    + " INTEGER NOT NULL,"
         + PokeTypeContract.COL_POKETYPETYPEFORTINTERNAL_ID    + " INTEGER,"
         + PokeTypeContract.COL_POKETYPETYPEFAIBLEINTERNAL_ID    + " INTEGER,"
         + PokeTypeContract.COL_POKETYPEPOKEMONTYPESINTERNAL_ID    + " INTEGER,"

        
         + "FOREIGN KEY(" + PokeTypeContract.COL_POKETYPETYPEFORTINTERNAL_ID + ") REFERENCES " 
             + PokeTypeContract.TABLE_NAME 
                + " (" + PokeTypeContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokeTypeContract.COL_POKETYPETYPEFAIBLEINTERNAL_ID + ") REFERENCES " 
             + PokeTypeContract.TABLE_NAME 
                + " (" + PokeTypeContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokeTypeContract.COL_POKETYPEPOKEMONTYPESINTERNAL_ID + ") REFERENCES " 
             + PokeTypePokemonContract.TABLE_NAME 
                + " (" + PokeTypePokemonContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PokeTypeSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert PokeType entity to Content Values for database.
     * @param item PokeType entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final PokeType item) {
        return PokeTypeContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to PokeType entity.
     * @param cursor android.database.Cursor object
     * @return PokeType entity
     */
    public PokeType cursorToItem(final android.database.Cursor cursor) {
        return PokeTypeContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to PokeType entity.
     * @param cursor android.database.Cursor object
     * @param result PokeType entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final PokeType result) {
        PokeTypeContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read PokeType by id in database.
     *
     * @param id Identify of PokeType
     * @return PokeType entity
     */
    public PokeType getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final PokeType result = this.cursorToItem(cursor);
        cursor.close();

        final PokeTypeSQLiteAdapter typeFortAdapter =
                new PokeTypeSQLiteAdapter(this.ctx);
        typeFortAdapter.open(this.mDatabase);
        android.database.Cursor typefortCursor = typeFortAdapter
                    .getByPokeTypetypeFortInternal(
                            result.getId(),
                            PokeTypeContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setTypeFort(typeFortAdapter.cursorToItems(typefortCursor));

        typefortCursor.close();
        final PokeTypeSQLiteAdapter typeFaibleAdapter =
                new PokeTypeSQLiteAdapter(this.ctx);
        typeFaibleAdapter.open(this.mDatabase);
        android.database.Cursor typefaibleCursor = typeFaibleAdapter
                    .getByPokeTypetypeFaibleInternal(
                            result.getId(),
                            PokeTypeContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setTypeFaible(typeFaibleAdapter.cursorToItems(typefaibleCursor));

        typefaibleCursor.close();
        return result;
    }

    /**
     * Find & read PokeType by PokeTypetypeFortInternal.
     * @param poketypetypefortinternalId poketypetypefortinternalId
     * @param orderBy Order by string (can be null)
     * @return List of PokeType entities
     */
     public android.database.Cursor getByPokeTypetypeFortInternal(final int poketypetypefortinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokeTypeContract.COL_POKETYPETYPEFORTINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(poketypetypefortinternalId);
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
     * Find & read PokeType by PokeTypetypeFaibleInternal.
     * @param poketypetypefaibleinternalId poketypetypefaibleinternalId
     * @param orderBy Order by string (can be null)
     * @return List of PokeType entities
     */
     public android.database.Cursor getByPokeTypetypeFaibleInternal(final int poketypetypefaibleinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokeTypeContract.COL_POKETYPETYPEFAIBLEINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(poketypetypefaibleinternalId);
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
     * Find & read PokeType by PokeTypePokemontypesInternal.
     * @param poketypepokemontypesinternalId poketypepokemontypesinternalId
     * @param orderBy Order by string (can be null)
     * @return List of PokeType entities
     */
     public android.database.Cursor getByPokeTypePokemontypesInternal(final int poketypepokemontypesinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokeTypeContract.COL_POKETYPEPOKEMONTYPESINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(poketypepokemontypesinternalId);
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
     * Read All PokeTypes entities.
     *
     * @return List of PokeType entities
     */
    public ArrayList<PokeType> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<PokeType> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a PokeType entity into database.
     *
     * @param item The PokeType entity to persist
     * @return Id of the PokeType entity
     */
    public long insert(final PokeType item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokeTypeContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeTypeContract.itemToContentValues(item, 0, 0, 0);
        values.remove(PokeTypeContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PokeTypeContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getTypeFort() != null) {
            PokeTypeSQLiteAdapterBase typeFortAdapter =
                    new PokeTypeSQLiteAdapter(this.ctx);
            typeFortAdapter.open(this.mDatabase);
            for (PokeType poketype
                        : item.getTypeFort()) {
                typeFortAdapter.insertOrUpdateWithPokeTypeTypeFort(
                                    poketype,
                                    insertResult);
            }
        }
        if (item.getTypeFaible() != null) {
            PokeTypeSQLiteAdapterBase typeFaibleAdapter =
                    new PokeTypeSQLiteAdapter(this.ctx);
            typeFaibleAdapter.open(this.mDatabase);
            for (PokeType poketype
                        : item.getTypeFaible()) {
                typeFaibleAdapter.insertOrUpdateWithPokeTypeTypeFaible(
                                    poketype,
                                    insertResult);
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a PokeType entity into database whether.
     * it already exists or not.
     *
     * @param item The PokeType entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final PokeType item) {
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
     * Update a PokeType entity into database.
     *
     * @param item The PokeType entity to persist
     * @return count of updated entities
     */
    public int update(final PokeType item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokeTypeContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeTypeContract.itemToContentValues(item, 0, 0, 0);
        final String whereClause =
                 PokeTypeContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Update a PokeType entity into database.
     *
     * @param item The PokeType entity to persist
     * @param poketypeId The poketype id
     * @return count of updated entities
     */
    public int updateWithPokeTypeTypeFort(
                    PokeType item,
                    int pokeTypetypeFortInternalId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokeTypeContract.TABLE_NAME + ")");
        }

        ContentValues values =
                PokeTypeContract.itemToContentValues(item);
        values.put(
                PokeTypeContract.COL_POKETYPETYPEFORTINTERNAL_ID,
                pokeTypetypeFortInternalId);
        String whereClause =
                 PokeTypeContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a PokeType entity into database whether.
     * it already exists or not.
     *
     * @param item The PokeType entity to persist
     * @param poketypeId The poketype id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithPokeTypeTypeFort(
            PokeType item, int poketypeId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithPokeTypeTypeFort(item,
                    poketypeId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithPokeTypeTypeFort(item,
                    poketypeId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a PokeType entity into database.
     *
     * @param item The PokeType entity to persist
     * @param poketypeId The poketype id
     * @return Id of the PokeType entity
     */
    public long insertWithPokeTypeTypeFort(
            PokeType item, int poketypeId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokeTypeContract.TABLE_NAME + ")");
        }

        ContentValues values = PokeTypeContract.itemToContentValues(item,
                poketypeId,
                0,
                0);
        values.remove(PokeTypeContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);

        PokeTypeSQLiteAdapter typeFortAdapter =
                new PokeTypeSQLiteAdapter(this.ctx);
        typeFortAdapter.open(this.mDatabase);
        if (item.getTypeFort() != null) {
            for (PokeType poketype : item.getTypeFort()) {
                typeFortAdapter.updateWithPokeTypeTypeFort(
                        poketype, newid);
            }
        }
        PokeTypeSQLiteAdapter typeFaibleAdapter =
                new PokeTypeSQLiteAdapter(this.ctx);
        typeFaibleAdapter.open(this.mDatabase);
        if (item.getTypeFaible() != null) {
            for (PokeType poketype : item.getTypeFaible()) {
                typeFaibleAdapter.updateWithPokeTypeTypeFaible(
                        poketype, newid);
            }
        }

        return newid;
    }


    /**
     * Update a PokeType entity into database.
     *
     * @param item The PokeType entity to persist
     * @param poketypeId The poketype id
     * @return count of updated entities
     */
    public int updateWithPokeTypeTypeFaible(
                    PokeType item,
                    int pokeTypetypeFaibleInternalId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokeTypeContract.TABLE_NAME + ")");
        }

        ContentValues values =
                PokeTypeContract.itemToContentValues(item);
        values.put(
                PokeTypeContract.COL_POKETYPETYPEFAIBLEINTERNAL_ID,
                pokeTypetypeFaibleInternalId);
        String whereClause =
                 PokeTypeContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a PokeType entity into database whether.
     * it already exists or not.
     *
     * @param item The PokeType entity to persist
     * @param poketypeId The poketype id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithPokeTypeTypeFaible(
            PokeType item, int poketypeId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithPokeTypeTypeFaible(item,
                    poketypeId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithPokeTypeTypeFaible(item,
                    poketypeId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a PokeType entity into database.
     *
     * @param item The PokeType entity to persist
     * @param poketypeId The poketype id
     * @return Id of the PokeType entity
     */
    public long insertWithPokeTypeTypeFaible(
            PokeType item, int poketypeId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokeTypeContract.TABLE_NAME + ")");
        }

        ContentValues values = PokeTypeContract.itemToContentValues(item,
                0,
                poketypeId,
                0);
        values.remove(PokeTypeContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);

        PokeTypeSQLiteAdapter typeFortAdapter =
                new PokeTypeSQLiteAdapter(this.ctx);
        typeFortAdapter.open(this.mDatabase);
        if (item.getTypeFort() != null) {
            for (PokeType poketype : item.getTypeFort()) {
                typeFortAdapter.updateWithPokeTypeTypeFort(
                        poketype, newid);
            }
        }
        PokeTypeSQLiteAdapter typeFaibleAdapter =
                new PokeTypeSQLiteAdapter(this.ctx);
        typeFaibleAdapter.open(this.mDatabase);
        if (item.getTypeFaible() != null) {
            for (PokeType poketype : item.getTypeFaible()) {
                typeFaibleAdapter.updateWithPokeTypeTypeFaible(
                        poketype, newid);
            }
        }

        return newid;
    }


    /**
     * Update a PokeType entity into database.
     *
     * @param item The PokeType entity to persist
     * @param poketypepokemonId The poketypepokemon id
     * @return count of updated entities
     */
    public int updateWithPokeTypePokemonTypes(
                    PokeType item,
                    int pokeTypePokemontypesInternalId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokeTypeContract.TABLE_NAME + ")");
        }

        ContentValues values =
                PokeTypeContract.itemToContentValues(item);
        values.put(
                PokeTypeContract.COL_POKETYPEPOKEMONTYPESINTERNAL_ID,
                pokeTypePokemontypesInternalId);
        String whereClause =
                 PokeTypeContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a PokeType entity into database whether.
     * it already exists or not.
     *
     * @param item The PokeType entity to persist
     * @param poketypepokemonId The poketypepokemon id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithPokeTypePokemonTypes(
            PokeType item, int poketypepokemonId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithPokeTypePokemonTypes(item,
                    poketypepokemonId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithPokeTypePokemonTypes(item,
                    poketypepokemonId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a PokeType entity into database.
     *
     * @param item The PokeType entity to persist
     * @param poketypepokemonId The poketypepokemon id
     * @return Id of the PokeType entity
     */
    public long insertWithPokeTypePokemonTypes(
            PokeType item, int poketypepokemonId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokeTypeContract.TABLE_NAME + ")");
        }

        ContentValues values = PokeTypeContract.itemToContentValues(item,
                0,
                0,
                poketypepokemonId);
        values.remove(PokeTypeContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);

        PokeTypeSQLiteAdapter typeFortAdapter =
                new PokeTypeSQLiteAdapter(this.ctx);
        typeFortAdapter.open(this.mDatabase);
        if (item.getTypeFort() != null) {
            for (PokeType poketype : item.getTypeFort()) {
                typeFortAdapter.updateWithPokeTypeTypeFort(
                        poketype, newid);
            }
        }
        PokeTypeSQLiteAdapter typeFaibleAdapter =
                new PokeTypeSQLiteAdapter(this.ctx);
        typeFaibleAdapter.open(this.mDatabase);
        if (item.getTypeFaible() != null) {
            for (PokeType poketype : item.getTypeFaible()) {
                typeFaibleAdapter.updateWithPokeTypeTypeFaible(
                        poketype, newid);
            }
        }

        return newid;
    }


    /**
     * Delete a PokeType entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PokeTypeContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PokeTypeContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param pokeType The entity to delete
     * @return count of updated entities
     */
    public int delete(final PokeType pokeType) {
        return this.remove(pokeType.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the PokeType corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PokeTypeContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PokeTypeContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a PokeType entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PokeTypeContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PokeTypeContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

