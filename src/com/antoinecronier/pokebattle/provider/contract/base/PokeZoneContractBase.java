/**************************************************************************
 * PokeZoneContractBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokeZone;



import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;

/** Pokebattle contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PokeZoneContractBase {


        /** PokeTypePokemonzonesInternal_id. */
    public static final String COL_POKETYPEPOKEMONZONESINTERNAL_ID =
            "PokeTypePokemon_zones_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_POKETYPEPOKEMONZONESINTERNAL_ID =
            PokeZoneContract.TABLE_NAME + "." + COL_POKETYPEPOKEMONZONESINTERNAL_ID;

    /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PokeZoneContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            PokeZoneContract.TABLE_NAME + "." + COL_NOM;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "PokeZone";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "PokeZone";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PokeZoneContract.COL_POKETYPEPOKEMONZONESINTERNAL_ID,
        
        PokeZoneContract.COL_ID,
        
        PokeZoneContract.COL_NOM
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PokeZoneContract.ALIASED_COL_POKETYPEPOKEMONZONESINTERNAL_ID,
        
        PokeZoneContract.ALIASED_COL_ID,
        
        PokeZoneContract.ALIASED_COL_NOM
    };

    /** Convert PokeZone entity to Content Values for database.
     *
     * @param item PokeZone entity object
     * @param poketypepokemonId poketypepokemon id
     * @return ContentValues object
     */
    public static ContentValues itemToContentValues(final PokeZone item,
                final int pokeTypePokemonzonesInternalId) {
        final ContentValues result = PokeZoneContract.itemToContentValues(item);
        result.put(PokeZoneContract.COL_POKETYPEPOKEMONZONESINTERNAL_ID,
                String.valueOf(pokeTypePokemonzonesInternalId));
        return result;
    }

    /**
     * Converts a PokeZone into a content values.
     *
     * @param item The PokeZone to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final PokeZone item) {
        final ContentValues result = new ContentValues();

              result.put(PokeZoneContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(PokeZoneContract.COL_NOM,
                    item.getNom());
            }


        return result;
    }

    /**
     * Converts a Cursor into a PokeZone.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted PokeZone
     */
    public static PokeZone cursorToItem(final android.database.Cursor cursor) {
        PokeZone result = new PokeZone();
        PokeZoneContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to PokeZone entity.
     * @param cursor Cursor object
     * @param result PokeZone entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final PokeZone result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PokeZoneContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokeZoneContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of PokeZone entity.
     * @param cursor Cursor object
     * @return Array of PokeZone entity
     */
    public static ArrayList<PokeZone> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<PokeZone> result = new ArrayList<PokeZone>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            PokeZone item;
            do {
                item = PokeZoneContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
