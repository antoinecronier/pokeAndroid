/**************************************************************************
 * PokeBadgeContractBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.entity.PokeBadgeBonus;



import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;

/** Pokebattle contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PokeBadgeContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PokeBadgeContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            PokeBadgeContract.TABLE_NAME + "." + COL_NOM;

    /** bonus. */
    public static final String COL_BONUS =
            "bonus";
    /** Alias. */
    public static final String ALIASED_COL_BONUS =
            PokeBadgeContract.TABLE_NAME + "." + COL_BONUS;

    /** PokeNpcbadgeInternal_id. */
    public static final String COL_POKENPCBADGEINTERNAL_ID =
            "PokeNpc_badge_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_POKENPCBADGEINTERNAL_ID =
            PokeBadgeContract.TABLE_NAME + "." + COL_POKENPCBADGEINTERNAL_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "PokeBadge";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "PokeBadge";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PokeBadgeContract.COL_ID,
        
        PokeBadgeContract.COL_NOM,
        
        PokeBadgeContract.COL_BONUS,
        
        PokeBadgeContract.COL_POKENPCBADGEINTERNAL_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PokeBadgeContract.ALIASED_COL_ID,
        
        PokeBadgeContract.ALIASED_COL_NOM,
        
        PokeBadgeContract.ALIASED_COL_BONUS,
        
        PokeBadgeContract.ALIASED_COL_POKENPCBADGEINTERNAL_ID
    };

    /** Convert PokeBadge entity to Content Values for database.
     *
     * @param item PokeBadge entity object
     * @param pokenpcId pokenpc id
     * @return ContentValues object
     */
    public static ContentValues itemToContentValues(final PokeBadge item,
                final int pokeNpcbadgeInternalId) {
        final ContentValues result = PokeBadgeContract.itemToContentValues(item);
        result.put(PokeBadgeContract.COL_POKENPCBADGEINTERNAL_ID,
                String.valueOf(pokeNpcbadgeInternalId));
        return result;
    }

    /**
     * Converts a PokeBadge into a content values.
     *
     * @param item The PokeBadge to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final PokeBadge item) {
        final ContentValues result = new ContentValues();

             result.put(PokeBadgeContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(PokeBadgeContract.COL_NOM,
                    item.getNom());
            }

             if (item.getBonus() != null) {
                result.put(PokeBadgeContract.COL_BONUS,
                    item.getBonus().name());
            }

 
        return result;
    }

    /**
     * Converts a Cursor into a PokeBadge.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted PokeBadge
     */
    public static PokeBadge cursorToItem(final android.database.Cursor cursor) {
        PokeBadge result = new PokeBadge();
        PokeBadgeContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to PokeBadge entity.
     * @param cursor Cursor object
     * @param result PokeBadge entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final PokeBadge result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PokeBadgeContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokeBadgeContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(PokeBadgeContract.COL_BONUS);

            if (index > -1) {
            result.setBonus(
                PokeBadgeBonus.valueOf(cursor.getString(index)));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of PokeBadge entity.
     * @param cursor Cursor object
     * @return Array of PokeBadge entity
     */
    public static ArrayList<PokeBadge> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<PokeBadge> result = new ArrayList<PokeBadge>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            PokeBadge item;
            do {
                item = PokeBadgeContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
