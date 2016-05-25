
/**************************************************************************
 * PokeAreneSQLiteAdapterBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.data.PokeAreneSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeNpcSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeBadgeSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeZoneSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokePositionSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeAreneContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;
import com.antoinecronier.pokebattle.provider.contract.PokePositionContract;
import com.antoinecronier.pokebattle.entity.PokeArene;
import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.entity.PokeZone;
import com.antoinecronier.pokebattle.entity.PokePosition;


import com.antoinecronier.pokebattle.PokebattleApplication;



/** PokeArene adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PokeAreneAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PokeAreneSQLiteAdapterBase
                        extends SQLiteAdapter<PokeArene> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeAreneDBAdapter";


    /**
     * Get the table name used in DB for your PokeArene entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PokeAreneContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your PokeArene entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PokeAreneContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the PokeArene entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PokeAreneContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PokeAreneContract.TABLE_NAME    + " ("
        
         + PokeAreneContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PokeAreneContract.COL_NOM    + " VARCHAR NOT NULL,"
         + PokeAreneContract.COL_MAITRE_ID    + " INTEGER NOT NULL,"
         + PokeAreneContract.COL_BADGE_ID    + " INTEGER NOT NULL,"
         + PokeAreneContract.COL_ZONE_ID    + " INTEGER NOT NULL,"
         + PokeAreneContract.COL_POSITION_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + PokeAreneContract.COL_MAITRE_ID + ") REFERENCES " 
             + PokeNpcContract.TABLE_NAME 
                + " (" + PokeNpcContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokeAreneContract.COL_BADGE_ID + ") REFERENCES " 
             + PokeBadgeContract.TABLE_NAME 
                + " (" + PokeBadgeContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokeAreneContract.COL_ZONE_ID + ") REFERENCES " 
             + PokeZoneContract.TABLE_NAME 
                + " (" + PokeZoneContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokeAreneContract.COL_POSITION_ID + ") REFERENCES " 
             + PokePositionContract.TABLE_NAME 
                + " (" + PokePositionContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PokeAreneSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert PokeArene entity to Content Values for database.
     * @param item PokeArene entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final PokeArene item) {
        return PokeAreneContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to PokeArene entity.
     * @param cursor android.database.Cursor object
     * @return PokeArene entity
     */
    public PokeArene cursorToItem(final android.database.Cursor cursor) {
        return PokeAreneContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to PokeArene entity.
     * @param cursor android.database.Cursor object
     * @param result PokeArene entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final PokeArene result) {
        PokeAreneContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read PokeArene by id in database.
     *
     * @param id Identify of PokeArene
     * @return PokeArene entity
     */
    public PokeArene getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final PokeArene result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getMaitre() != null) {
            final PokeNpcSQLiteAdapter maitreAdapter =
                    new PokeNpcSQLiteAdapter(this.ctx);
            maitreAdapter.open(this.mDatabase);

            result.setMaitre(maitreAdapter.getByID(
                            result.getMaitre().getId()));
        }
        final PokeNpcSQLiteAdapter dresseursAdapter =
                new PokeNpcSQLiteAdapter(this.ctx);
        dresseursAdapter.open(this.mDatabase);
        android.database.Cursor dresseursCursor = dresseursAdapter
                    .getByPokeArenedresseursInternal(
                            result.getId(),
                            PokeNpcContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setDresseurs(dresseursAdapter.cursorToItems(dresseursCursor));

        dresseursCursor.close();
        if (result.getBadge() != null) {
            final PokeBadgeSQLiteAdapter badgeAdapter =
                    new PokeBadgeSQLiteAdapter(this.ctx);
            badgeAdapter.open(this.mDatabase);

            result.setBadge(badgeAdapter.getByID(
                            result.getBadge().getId()));
        }
        if (result.getZone() != null) {
            final PokeZoneSQLiteAdapter zoneAdapter =
                    new PokeZoneSQLiteAdapter(this.ctx);
            zoneAdapter.open(this.mDatabase);

            result.setZone(zoneAdapter.getByID(
                            result.getZone().getId()));
        }
        if (result.getPosition() != null) {
            final PokePositionSQLiteAdapter positionAdapter =
                    new PokePositionSQLiteAdapter(this.ctx);
            positionAdapter.open(this.mDatabase);

            result.setPosition(positionAdapter.getByID(
                            result.getPosition().getId()));
        }
        return result;
    }

    /**
     * Find & read PokeArene by maitre.
     * @param maitreId maitreId
     * @param orderBy Order by string (can be null)
     * @return List of PokeArene entities
     */
     public android.database.Cursor getByMaitre(final int maitreId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokeAreneContract.COL_MAITRE_ID + "= ?";
        String idSelectionArgs = String.valueOf(maitreId);
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
     * Find & read PokeArene by badge.
     * @param badgeId badgeId
     * @param orderBy Order by string (can be null)
     * @return List of PokeArene entities
     */
     public android.database.Cursor getByBadge(final int badgeId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokeAreneContract.COL_BADGE_ID + "= ?";
        String idSelectionArgs = String.valueOf(badgeId);
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
     * Find & read PokeArene by zone.
     * @param zoneId zoneId
     * @param orderBy Order by string (can be null)
     * @return List of PokeArene entities
     */
     public android.database.Cursor getByZone(final int zoneId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokeAreneContract.COL_ZONE_ID + "= ?";
        String idSelectionArgs = String.valueOf(zoneId);
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
     * Find & read PokeArene by position.
     * @param positionId positionId
     * @param orderBy Order by string (can be null)
     * @return List of PokeArene entities
     */
     public android.database.Cursor getByPosition(final int positionId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokeAreneContract.COL_POSITION_ID + "= ?";
        String idSelectionArgs = String.valueOf(positionId);
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
     * Read All PokeArenes entities.
     *
     * @return List of PokeArene entities
     */
    public ArrayList<PokeArene> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<PokeArene> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a PokeArene entity into database.
     *
     * @param item The PokeArene entity to persist
     * @return Id of the PokeArene entity
     */
    public long insert(final PokeArene item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokeAreneContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeAreneContract.itemToContentValues(item);
        values.remove(PokeAreneContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PokeAreneContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getDresseurs() != null) {
            PokeNpcSQLiteAdapterBase dresseursAdapter =
                    new PokeNpcSQLiteAdapter(this.ctx);
            dresseursAdapter.open(this.mDatabase);
            for (PokeNpc pokenpc
                        : item.getDresseurs()) {
                dresseursAdapter.insertOrUpdateWithPokeAreneDresseurs(
                                    pokenpc,
                                    insertResult);
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a PokeArene entity into database whether.
     * it already exists or not.
     *
     * @param item The PokeArene entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final PokeArene item) {
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
     * Update a PokeArene entity into database.
     *
     * @param item The PokeArene entity to persist
     * @return count of updated entities
     */
    public int update(final PokeArene item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokeAreneContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeAreneContract.itemToContentValues(item);
        final String whereClause =
                 PokeAreneContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a PokeArene entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PokeAreneContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PokeAreneContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param pokeArene The entity to delete
     * @return count of updated entities
     */
    public int delete(final PokeArene pokeArene) {
        return this.remove(pokeArene.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the PokeArene corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PokeAreneContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PokeAreneContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a PokeArene entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PokeAreneContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PokeAreneContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

