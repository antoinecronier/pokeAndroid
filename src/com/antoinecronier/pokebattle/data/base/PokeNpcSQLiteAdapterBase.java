
/**************************************************************************
 * PokeNpcSQLiteAdapterBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.data.PokeNpcSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeObjetSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeBadgeSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokePokemonSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokePositionSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeZoneSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeAreneContract;
import com.antoinecronier.pokebattle.provider.contract.PokeDresseurContract;
import com.antoinecronier.pokebattle.provider.contract.PokeObjetContract;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;
import com.antoinecronier.pokebattle.provider.contract.PokePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokePositionContract;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;
import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.entity.PokeObjet;
import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.entity.PokePokemon;
import com.antoinecronier.pokebattle.entity.PokePosition;
import com.antoinecronier.pokebattle.entity.PokeZone;
import com.antoinecronier.pokebattle.entity.PokeProfession;


import com.antoinecronier.pokebattle.PokebattleApplication;



/** PokeNpc adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PokeNpcAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PokeNpcSQLiteAdapterBase
                        extends SQLiteAdapter<PokeNpc> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeNpcDBAdapter";


    /**
     * Get the table name used in DB for your PokeNpc entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PokeNpcContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your PokeNpc entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PokeNpcContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the PokeNpc entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PokeNpcContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PokeNpcContract.TABLE_NAME    + " ("
        
         + PokeNpcContract.COL_POKEARENEDRESSEURSINTERNAL_ID    + " INTEGER,"
         + PokeNpcContract.COL_POKEDRESSEURNPCSINTERNAL_ID    + " INTEGER,"
         + PokeNpcContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PokeNpcContract.COL_NOM    + " VARCHAR NOT NULL,"
         + PokeNpcContract.COL_PROFESSION    + " VARCHAR NOT NULL,"
         + PokeNpcContract.COL_DESCRIPTION    + " VARCHAR NOT NULL,"
         + PokeNpcContract.COL_POSITION_ID    + " INTEGER,"
         + PokeNpcContract.COL_ZONE_ID    + " INTEGER,"

        
         + "FOREIGN KEY(" + PokeNpcContract.COL_POKEARENEDRESSEURSINTERNAL_ID + ") REFERENCES " 
             + PokeAreneContract.TABLE_NAME 
                + " (" + PokeAreneContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokeNpcContract.COL_POKEDRESSEURNPCSINTERNAL_ID + ") REFERENCES " 
             + PokeDresseurContract.TABLE_NAME 
                + " (" + PokeDresseurContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokeNpcContract.COL_POSITION_ID + ") REFERENCES " 
             + PokePositionContract.TABLE_NAME 
                + " (" + PokePositionContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokeNpcContract.COL_ZONE_ID + ") REFERENCES " 
             + PokeZoneContract.TABLE_NAME 
                + " (" + PokeZoneContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PokeNpcSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert PokeNpc entity to Content Values for database.
     * @param item PokeNpc entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final PokeNpc item) {
        return PokeNpcContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to PokeNpc entity.
     * @param cursor android.database.Cursor object
     * @return PokeNpc entity
     */
    public PokeNpc cursorToItem(final android.database.Cursor cursor) {
        return PokeNpcContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to PokeNpc entity.
     * @param cursor android.database.Cursor object
     * @param result PokeNpc entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final PokeNpc result) {
        PokeNpcContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read PokeNpc by id in database.
     *
     * @param id Identify of PokeNpc
     * @return PokeNpc entity
     */
    public PokeNpc getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final PokeNpc result = this.cursorToItem(cursor);
        cursor.close();

        final PokeObjetSQLiteAdapter objetsAdapter =
                new PokeObjetSQLiteAdapter(this.ctx);
        objetsAdapter.open(this.mDatabase);
        android.database.Cursor objetsCursor = objetsAdapter
                    .getByPokeNpcobjetsInternal(
                            result.getId(),
                            PokeObjetContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setObjets(objetsAdapter.cursorToItems(objetsCursor));

        objetsCursor.close();
        final PokeBadgeSQLiteAdapter badgeAdapter =
                new PokeBadgeSQLiteAdapter(this.ctx);
        badgeAdapter.open(this.mDatabase);
        android.database.Cursor badgeCursor = badgeAdapter
                    .getByPokeNpcbadgeInternal(
                            result.getId(),
                            PokeBadgeContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setBadge(badgeAdapter.cursorToItems(badgeCursor));

        badgeCursor.close();
        final PokePokemonSQLiteAdapter pokemonsAdapter =
                new PokePokemonSQLiteAdapter(this.ctx);
        pokemonsAdapter.open(this.mDatabase);
        android.database.Cursor pokemonsCursor = pokemonsAdapter
                    .getByPokeNpcpokemonsInternal(
                            result.getId(),
                            PokePokemonContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setPokemons(pokemonsAdapter.cursorToItems(pokemonsCursor));

        pokemonsCursor.close();
        final PokePokemonSQLiteAdapter teamAdapter =
                new PokePokemonSQLiteAdapter(this.ctx);
        teamAdapter.open(this.mDatabase);
        android.database.Cursor teamCursor = teamAdapter
                    .getByPokeNpcteamInternal(
                            result.getId(),
                            PokePokemonContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setTeam(teamAdapter.cursorToItems(teamCursor));

        teamCursor.close();
        if (result.getPosition() != null) {
            final PokePositionSQLiteAdapter positionAdapter =
                    new PokePositionSQLiteAdapter(this.ctx);
            positionAdapter.open(this.mDatabase);

            result.setPosition(positionAdapter.getByID(
                            result.getPosition().getId()));
        }
        if (result.getZone() != null) {
            final PokeZoneSQLiteAdapter zoneAdapter =
                    new PokeZoneSQLiteAdapter(this.ctx);
            zoneAdapter.open(this.mDatabase);

            result.setZone(zoneAdapter.getByID(
                            result.getZone().getId()));
        }
        return result;
    }

    /**
     * Find & read PokeNpc by PokeArenedresseursInternal.
     * @param pokearenedresseursinternalId pokearenedresseursinternalId
     * @param orderBy Order by string (can be null)
     * @return List of PokeNpc entities
     */
     public android.database.Cursor getByPokeArenedresseursInternal(final int pokearenedresseursinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokeNpcContract.COL_POKEARENEDRESSEURSINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(pokearenedresseursinternalId);
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
     * Find & read PokeNpc by PokeDresseurnpcsInternal.
     * @param pokedresseurnpcsinternalId pokedresseurnpcsinternalId
     * @param orderBy Order by string (can be null)
     * @return List of PokeNpc entities
     */
     public android.database.Cursor getByPokeDresseurnpcsInternal(final int pokedresseurnpcsinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokeNpcContract.COL_POKEDRESSEURNPCSINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(pokedresseurnpcsinternalId);
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
     * Find & read PokeNpc by position.
     * @param positionId positionId
     * @param orderBy Order by string (can be null)
     * @return List of PokeNpc entities
     */
     public android.database.Cursor getByPosition(final int positionId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokeNpcContract.COL_POSITION_ID + "= ?";
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
     * Find & read PokeNpc by zone.
     * @param zoneId zoneId
     * @param orderBy Order by string (can be null)
     * @return List of PokeNpc entities
     */
     public android.database.Cursor getByZone(final int zoneId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokeNpcContract.COL_ZONE_ID + "= ?";
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
     * Read All PokeNpcs entities.
     *
     * @return List of PokeNpc entities
     */
    public ArrayList<PokeNpc> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<PokeNpc> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a PokeNpc entity into database.
     *
     * @param item The PokeNpc entity to persist
     * @return Id of the PokeNpc entity
     */
    public long insert(final PokeNpc item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokeNpcContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeNpcContract.itemToContentValues(item, 0, 0);
        values.remove(PokeNpcContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PokeNpcContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getObjets() != null) {
            PokeObjetSQLiteAdapterBase objetsAdapter =
                    new PokeObjetSQLiteAdapter(this.ctx);
            objetsAdapter.open(this.mDatabase);
            for (PokeObjet pokeobjet
                        : item.getObjets()) {
                objetsAdapter.insertOrUpdateWithPokeNpcObjets(
                                    pokeobjet,
                                    insertResult);
            }
        }
        if (item.getBadge() != null) {
            PokeBadgeSQLiteAdapterBase badgeAdapter =
                    new PokeBadgeSQLiteAdapter(this.ctx);
            badgeAdapter.open(this.mDatabase);
            for (PokeBadge pokebadge
                        : item.getBadge()) {
                badgeAdapter.insertOrUpdateWithPokeNpcBadge(
                                    pokebadge,
                                    insertResult);
            }
        }
        if (item.getPokemons() != null) {
            PokePokemonSQLiteAdapterBase pokemonsAdapter =
                    new PokePokemonSQLiteAdapter(this.ctx);
            pokemonsAdapter.open(this.mDatabase);
            for (PokePokemon pokepokemon
                        : item.getPokemons()) {
                pokemonsAdapter.insertOrUpdateWithPokeNpcPokemons(
                                    pokepokemon,
                                    insertResult);
            }
        }
        if (item.getTeam() != null) {
            PokePokemonSQLiteAdapterBase teamAdapter =
                    new PokePokemonSQLiteAdapter(this.ctx);
            teamAdapter.open(this.mDatabase);
            for (PokePokemon pokepokemon
                        : item.getTeam()) {
                teamAdapter.insertOrUpdateWithPokeNpcTeam(
                                    pokepokemon,
                                    insertResult);
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a PokeNpc entity into database whether.
     * it already exists or not.
     *
     * @param item The PokeNpc entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final PokeNpc item) {
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
     * Update a PokeNpc entity into database.
     *
     * @param item The PokeNpc entity to persist
     * @return count of updated entities
     */
    public int update(final PokeNpc item) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokeNpcContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokeNpcContract.itemToContentValues(item, 0, 0);
        final String whereClause =
                 PokeNpcContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Update a PokeNpc entity into database.
     *
     * @param item The PokeNpc entity to persist
     * @param pokeareneId The pokearene id
     * @return count of updated entities
     */
    public int updateWithPokeAreneDresseurs(
                    PokeNpc item,
                    int pokeArenedresseursInternalId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokeNpcContract.TABLE_NAME + ")");
        }

        ContentValues values =
                PokeNpcContract.itemToContentValues(item);
        values.put(
                PokeNpcContract.COL_POKEARENEDRESSEURSINTERNAL_ID,
                pokeArenedresseursInternalId);
        String whereClause =
                 PokeNpcContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a PokeNpc entity into database whether.
     * it already exists or not.
     *
     * @param item The PokeNpc entity to persist
     * @param pokeareneId The pokearene id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithPokeAreneDresseurs(
            PokeNpc item, int pokeareneId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithPokeAreneDresseurs(item,
                    pokeareneId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithPokeAreneDresseurs(item,
                    pokeareneId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a PokeNpc entity into database.
     *
     * @param item The PokeNpc entity to persist
     * @param pokeareneId The pokearene id
     * @return Id of the PokeNpc entity
     */
    public long insertWithPokeAreneDresseurs(
            PokeNpc item, int pokeareneId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokeNpcContract.TABLE_NAME + ")");
        }

        ContentValues values = PokeNpcContract.itemToContentValues(item,
                pokeareneId,
                0);
        values.remove(PokeNpcContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);

        PokeObjetSQLiteAdapter objetsAdapter =
                new PokeObjetSQLiteAdapter(this.ctx);
        objetsAdapter.open(this.mDatabase);
        if (item.getObjets() != null) {
            for (PokeObjet pokeobjet : item.getObjets()) {
                objetsAdapter.updateWithPokeNpcObjets(
                        pokeobjet, newid);
            }
        }
        PokeBadgeSQLiteAdapter badgeAdapter =
                new PokeBadgeSQLiteAdapter(this.ctx);
        badgeAdapter.open(this.mDatabase);
        if (item.getBadge() != null) {
            for (PokeBadge pokebadge : item.getBadge()) {
                badgeAdapter.updateWithPokeNpcBadge(
                        pokebadge, newid);
            }
        }
        PokePokemonSQLiteAdapter pokemonsAdapter =
                new PokePokemonSQLiteAdapter(this.ctx);
        pokemonsAdapter.open(this.mDatabase);
        if (item.getPokemons() != null) {
            for (PokePokemon pokepokemon : item.getPokemons()) {
                pokemonsAdapter.updateWithPokeNpcPokemons(
                        pokepokemon, newid);
            }
        }
        PokePokemonSQLiteAdapter teamAdapter =
                new PokePokemonSQLiteAdapter(this.ctx);
        teamAdapter.open(this.mDatabase);
        if (item.getTeam() != null) {
            for (PokePokemon pokepokemon : item.getTeam()) {
                teamAdapter.updateWithPokeNpcTeam(
                        pokepokemon, newid);
            }
        }

        return newid;
    }


    /**
     * Update a PokeNpc entity into database.
     *
     * @param item The PokeNpc entity to persist
     * @param pokedresseurId The pokedresseur id
     * @return count of updated entities
     */
    public int updateWithPokeDresseurNpcs(
                    PokeNpc item,
                    int pokeDresseurnpcsInternalId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokeNpcContract.TABLE_NAME + ")");
        }

        ContentValues values =
                PokeNpcContract.itemToContentValues(item);
        values.put(
                PokeNpcContract.COL_POKEDRESSEURNPCSINTERNAL_ID,
                pokeDresseurnpcsInternalId);
        String whereClause =
                 PokeNpcContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a PokeNpc entity into database whether.
     * it already exists or not.
     *
     * @param item The PokeNpc entity to persist
     * @param pokedresseurId The pokedresseur id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithPokeDresseurNpcs(
            PokeNpc item, int pokedresseurId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithPokeDresseurNpcs(item,
                    pokedresseurId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithPokeDresseurNpcs(item,
                    pokedresseurId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a PokeNpc entity into database.
     *
     * @param item The PokeNpc entity to persist
     * @param pokedresseurId The pokedresseur id
     * @return Id of the PokeNpc entity
     */
    public long insertWithPokeDresseurNpcs(
            PokeNpc item, int pokedresseurId) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokeNpcContract.TABLE_NAME + ")");
        }

        ContentValues values = PokeNpcContract.itemToContentValues(item,
                0,
                pokedresseurId);
        values.remove(PokeNpcContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);

        PokeObjetSQLiteAdapter objetsAdapter =
                new PokeObjetSQLiteAdapter(this.ctx);
        objetsAdapter.open(this.mDatabase);
        if (item.getObjets() != null) {
            for (PokeObjet pokeobjet : item.getObjets()) {
                objetsAdapter.updateWithPokeNpcObjets(
                        pokeobjet, newid);
            }
        }
        PokeBadgeSQLiteAdapter badgeAdapter =
                new PokeBadgeSQLiteAdapter(this.ctx);
        badgeAdapter.open(this.mDatabase);
        if (item.getBadge() != null) {
            for (PokeBadge pokebadge : item.getBadge()) {
                badgeAdapter.updateWithPokeNpcBadge(
                        pokebadge, newid);
            }
        }
        PokePokemonSQLiteAdapter pokemonsAdapter =
                new PokePokemonSQLiteAdapter(this.ctx);
        pokemonsAdapter.open(this.mDatabase);
        if (item.getPokemons() != null) {
            for (PokePokemon pokepokemon : item.getPokemons()) {
                pokemonsAdapter.updateWithPokeNpcPokemons(
                        pokepokemon, newid);
            }
        }
        PokePokemonSQLiteAdapter teamAdapter =
                new PokePokemonSQLiteAdapter(this.ctx);
        teamAdapter.open(this.mDatabase);
        if (item.getTeam() != null) {
            for (PokePokemon pokepokemon : item.getTeam()) {
                teamAdapter.updateWithPokeNpcTeam(
                        pokepokemon, newid);
            }
        }

        return newid;
    }


    /**
     * Delete a PokeNpc entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PokeNpcContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PokeNpcContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param pokeNpc The entity to delete
     * @return count of updated entities
     */
    public int delete(final PokeNpc pokeNpc) {
        return this.remove(pokeNpc.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the PokeNpc corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokebattleApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PokeNpcContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PokeNpcContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a PokeNpc entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PokeNpcContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PokeNpcContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

