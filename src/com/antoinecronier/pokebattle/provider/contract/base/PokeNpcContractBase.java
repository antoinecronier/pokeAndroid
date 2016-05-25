/**************************************************************************
 * PokeNpcContractBase.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.entity.PokeObjet;
import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.entity.PokePokemon;
import com.antoinecronier.pokebattle.entity.PokePosition;
import com.antoinecronier.pokebattle.entity.PokeZone;
import com.antoinecronier.pokebattle.entity.PokeProfession;



import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;

/** Pokebattle contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PokeNpcContractBase {


        /** PokeArenedresseursInternal_id. */
    public static final String COL_POKEARENEDRESSEURSINTERNAL_ID =
            "PokeArene_dresseurs_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_POKEARENEDRESSEURSINTERNAL_ID =
            PokeNpcContract.TABLE_NAME + "." + COL_POKEARENEDRESSEURSINTERNAL_ID;

    /** PokeDresseurnpcsInternal_id. */
    public static final String COL_POKEDRESSEURNPCSINTERNAL_ID =
            "PokeDresseur_npcs_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_POKEDRESSEURNPCSINTERNAL_ID =
            PokeNpcContract.TABLE_NAME + "." + COL_POKEDRESSEURNPCSINTERNAL_ID;

    /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PokeNpcContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            PokeNpcContract.TABLE_NAME + "." + COL_NOM;

    /** profession. */
    public static final String COL_PROFESSION =
            "profession";
    /** Alias. */
    public static final String ALIASED_COL_PROFESSION =
            PokeNpcContract.TABLE_NAME + "." + COL_PROFESSION;

    /** description. */
    public static final String COL_DESCRIPTION =
            "description";
    /** Alias. */
    public static final String ALIASED_COL_DESCRIPTION =
            PokeNpcContract.TABLE_NAME + "." + COL_DESCRIPTION;

    /** position_id. */
    public static final String COL_POSITION_ID =
            "position_id";
    /** Alias. */
    public static final String ALIASED_COL_POSITION_ID =
            PokeNpcContract.TABLE_NAME + "." + COL_POSITION_ID;

    /** zone_id. */
    public static final String COL_ZONE_ID =
            "zone_id";
    /** Alias. */
    public static final String ALIASED_COL_ZONE_ID =
            PokeNpcContract.TABLE_NAME + "." + COL_ZONE_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "PokeNpc";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "PokeNpc";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PokeNpcContract.COL_POKEARENEDRESSEURSINTERNAL_ID,
        
        PokeNpcContract.COL_POKEDRESSEURNPCSINTERNAL_ID,
        
        PokeNpcContract.COL_ID,
        
        PokeNpcContract.COL_NOM,
        
        PokeNpcContract.COL_PROFESSION,
        
        PokeNpcContract.COL_DESCRIPTION,
        
        PokeNpcContract.COL_POSITION_ID,
        
        PokeNpcContract.COL_ZONE_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PokeNpcContract.ALIASED_COL_POKEARENEDRESSEURSINTERNAL_ID,
        
        PokeNpcContract.ALIASED_COL_POKEDRESSEURNPCSINTERNAL_ID,
        
        PokeNpcContract.ALIASED_COL_ID,
        
        PokeNpcContract.ALIASED_COL_NOM,
        
        PokeNpcContract.ALIASED_COL_PROFESSION,
        
        PokeNpcContract.ALIASED_COL_DESCRIPTION,
        
        
        
        
        
        PokeNpcContract.ALIASED_COL_POSITION_ID,
        
        PokeNpcContract.ALIASED_COL_ZONE_ID
    };

    /** Convert PokeNpc entity to Content Values for database.
     *
     * @param item PokeNpc entity object
     * @param pokeareneId pokearene id
     * @param pokedresseurId pokedresseur id
     * @return ContentValues object
     */
    public static ContentValues itemToContentValues(final PokeNpc item,
                final int pokeArenedresseursInternalId,
                final int pokeDresseurnpcsInternalId) {
        final ContentValues result = PokeNpcContract.itemToContentValues(item);
        result.put(PokeNpcContract.COL_POKEARENEDRESSEURSINTERNAL_ID,
                String.valueOf(pokeArenedresseursInternalId));
        result.put(PokeNpcContract.COL_POKEDRESSEURNPCSINTERNAL_ID,
                String.valueOf(pokeDresseurnpcsInternalId));
        return result;
    }

    /**
     * Converts a PokeNpc into a content values.
     *
     * @param item The PokeNpc to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final PokeNpc item) {
        final ContentValues result = new ContentValues();

               result.put(PokeNpcContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(PokeNpcContract.COL_NOM,
                    item.getNom());
            }

             if (item.getProfession() != null) {
                result.put(PokeNpcContract.COL_PROFESSION,
                    item.getProfession().name());
            }

             if (item.getDescription() != null) {
                result.put(PokeNpcContract.COL_DESCRIPTION,
                    item.getDescription());
            }

                 if (item.getPosition() != null) {
                result.put(PokeNpcContract.COL_POSITION_ID,
                    item.getPosition().getId());
            } else {
                result.put(PokeNpcContract.COL_POSITION_ID, (String) null);
            }

             if (item.getZone() != null) {
                result.put(PokeNpcContract.COL_ZONE_ID,
                    item.getZone().getId());
            } else {
                result.put(PokeNpcContract.COL_ZONE_ID, (String) null);
            }


        return result;
    }

    /**
     * Converts a Cursor into a PokeNpc.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted PokeNpc
     */
    public static PokeNpc cursorToItem(final android.database.Cursor cursor) {
        PokeNpc result = new PokeNpc();
        PokeNpcContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to PokeNpc entity.
     * @param cursor Cursor object
     * @param result PokeNpc entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final PokeNpc result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PokeNpcContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokeNpcContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(PokeNpcContract.COL_PROFESSION);

            if (index > -1) {
            result.setProfession(
                PokeProfession.valueOf(cursor.getString(index)));
            }
            index = cursor.getColumnIndex(PokeNpcContract.COL_DESCRIPTION);

            if (index > -1) {
                result.setDescription(cursor.getString(index));
            }
            if (result.getPosition() == null) {
                final PokePosition position = new PokePosition();
                index = cursor.getColumnIndex(PokeNpcContract.COL_POSITION_ID);

                if (index > -1) {
                    if (!cursor.isNull(index)) {
                        position.setId(cursor.getInt(index));
                        result.setPosition(position);
                    }
                }

            }
            if (result.getZone() == null) {
                final PokeZone zone = new PokeZone();
                index = cursor.getColumnIndex(PokeNpcContract.COL_ZONE_ID);

                if (index > -1) {
                    if (!cursor.isNull(index)) {
                        zone.setId(cursor.getInt(index));
                        result.setZone(zone);
                    }
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of PokeNpc entity.
     * @param cursor Cursor object
     * @return Array of PokeNpc entity
     */
    public static ArrayList<PokeNpc> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<PokeNpc> result = new ArrayList<PokeNpc>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            PokeNpc item;
            do {
                item = PokeNpcContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
