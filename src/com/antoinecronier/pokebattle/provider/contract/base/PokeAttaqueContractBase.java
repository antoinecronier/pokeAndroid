/**************************************************************************
 * PokeAttaqueContractBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokeAttaque;
import com.antoinecronier.pokebattle.entity.PokeType;



import com.antoinecronier.pokebattle.provider.contract.PokeAttaqueContract;

/** Pokebattle contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PokeAttaqueContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PokeAttaqueContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            PokeAttaqueContract.TABLE_NAME + "." + COL_NOM;

    /** puissance. */
    public static final String COL_PUISSANCE =
            "puissance";
    /** Alias. */
    public static final String ALIASED_COL_PUISSANCE =
            PokeAttaqueContract.TABLE_NAME + "." + COL_PUISSANCE;

    /** precision. */
    public static final String COL_PRECISION =
            "precision";
    /** Alias. */
    public static final String ALIASED_COL_PRECISION =
            PokeAttaqueContract.TABLE_NAME + "." + COL_PRECISION;

    /** type_id. */
    public static final String COL_TYPE_ID =
            "type_id";
    /** Alias. */
    public static final String ALIASED_COL_TYPE_ID =
            PokeAttaqueContract.TABLE_NAME + "." + COL_TYPE_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "PokeAttaque";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "PokeAttaque";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PokeAttaqueContract.COL_ID,
        
        PokeAttaqueContract.COL_NOM,
        
        PokeAttaqueContract.COL_PUISSANCE,
        
        PokeAttaqueContract.COL_PRECISION,
        
        PokeAttaqueContract.COL_TYPE_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PokeAttaqueContract.ALIASED_COL_ID,
        
        PokeAttaqueContract.ALIASED_COL_NOM,
        
        PokeAttaqueContract.ALIASED_COL_PUISSANCE,
        
        PokeAttaqueContract.ALIASED_COL_PRECISION,
        
        PokeAttaqueContract.ALIASED_COL_TYPE_ID
    };


    /**
     * Converts a PokeAttaque into a content values.
     *
     * @param item The PokeAttaque to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final PokeAttaque item) {
        final ContentValues result = new ContentValues();

             result.put(PokeAttaqueContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(PokeAttaqueContract.COL_NOM,
                    item.getNom());
            }

             result.put(PokeAttaqueContract.COL_PUISSANCE,
                String.valueOf(item.getPuissance()));

             result.put(PokeAttaqueContract.COL_PRECISION,
                String.valueOf(item.getPrecision()));

             if (item.getType() != null) {
                result.put(PokeAttaqueContract.COL_TYPE_ID,
                    item.getType().getId());
            }


        return result;
    }

    /**
     * Converts a Cursor into a PokeAttaque.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted PokeAttaque
     */
    public static PokeAttaque cursorToItem(final android.database.Cursor cursor) {
        PokeAttaque result = new PokeAttaque();
        PokeAttaqueContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to PokeAttaque entity.
     * @param cursor Cursor object
     * @param result PokeAttaque entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final PokeAttaque result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PokeAttaqueContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokeAttaqueContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(PokeAttaqueContract.COL_PUISSANCE);

            if (index > -1) {
                result.setPuissance(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokeAttaqueContract.COL_PRECISION);

            if (index > -1) {
                result.setPrecision(cursor.getInt(index));
            }
            if (result.getType() == null) {
                final PokeType type = new PokeType();
                index = cursor.getColumnIndex(PokeAttaqueContract.COL_TYPE_ID);

                if (index > -1) {
                    type.setId(cursor.getInt(index));
                    result.setType(type);
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of PokeAttaque entity.
     * @param cursor Cursor object
     * @return Array of PokeAttaque entity
     */
    public static ArrayList<PokeAttaque> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<PokeAttaque> result = new ArrayList<PokeAttaque>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            PokeAttaque item;
            do {
                item = PokeAttaqueContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
