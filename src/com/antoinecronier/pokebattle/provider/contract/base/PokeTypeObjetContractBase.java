/**************************************************************************
 * PokeTypeObjetContractBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokeTypeObjet;



import com.antoinecronier.pokebattle.provider.contract.PokeTypeObjetContract;

/** Pokebattle contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PokeTypeObjetContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PokeTypeObjetContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            PokeTypeObjetContract.TABLE_NAME + "." + COL_NOM;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "PokeTypeObjet";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "PokeTypeObjet";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PokeTypeObjetContract.COL_ID,
        
        PokeTypeObjetContract.COL_NOM
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PokeTypeObjetContract.ALIASED_COL_ID,
        
        PokeTypeObjetContract.ALIASED_COL_NOM
    };


    /**
     * Converts a PokeTypeObjet into a content values.
     *
     * @param item The PokeTypeObjet to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final PokeTypeObjet item) {
        final ContentValues result = new ContentValues();

             result.put(PokeTypeObjetContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(PokeTypeObjetContract.COL_NOM,
                    item.getNom());
            }


        return result;
    }

    /**
     * Converts a Cursor into a PokeTypeObjet.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted PokeTypeObjet
     */
    public static PokeTypeObjet cursorToItem(final android.database.Cursor cursor) {
        PokeTypeObjet result = new PokeTypeObjet();
        PokeTypeObjetContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to PokeTypeObjet entity.
     * @param cursor Cursor object
     * @param result PokeTypeObjet entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final PokeTypeObjet result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PokeTypeObjetContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokeTypeObjetContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of PokeTypeObjet entity.
     * @param cursor Cursor object
     * @return Array of PokeTypeObjet entity
     */
    public static ArrayList<PokeTypeObjet> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<PokeTypeObjet> result = new ArrayList<PokeTypeObjet>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            PokeTypeObjet item;
            do {
                item = PokeTypeObjetContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
