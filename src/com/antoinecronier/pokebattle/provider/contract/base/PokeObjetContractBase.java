/**************************************************************************
 * PokeObjetContractBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokeObjet;
import com.antoinecronier.pokebattle.entity.PokeTypeObjet;



import com.antoinecronier.pokebattle.provider.contract.PokeObjetContract;

/** Pokebattle contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PokeObjetContractBase {


        /** PokeNpcobjetsInternal_id. */
    public static final String COL_POKENPCOBJETSINTERNAL_ID =
            "PokeNpc_objets_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_POKENPCOBJETSINTERNAL_ID =
            PokeObjetContract.TABLE_NAME + "." + COL_POKENPCOBJETSINTERNAL_ID;

    /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PokeObjetContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            PokeObjetContract.TABLE_NAME + "." + COL_NOM;

    /** quantity. */
    public static final String COL_QUANTITY =
            "quantity";
    /** Alias. */
    public static final String ALIASED_COL_QUANTITY =
            PokeObjetContract.TABLE_NAME + "." + COL_QUANTITY;

    /** type_id. */
    public static final String COL_TYPE_ID =
            "type_id";
    /** Alias. */
    public static final String ALIASED_COL_TYPE_ID =
            PokeObjetContract.TABLE_NAME + "." + COL_TYPE_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "PokeObjet";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "PokeObjet";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PokeObjetContract.COL_POKENPCOBJETSINTERNAL_ID,
        
        PokeObjetContract.COL_ID,
        
        PokeObjetContract.COL_NOM,
        
        PokeObjetContract.COL_QUANTITY,
        
        PokeObjetContract.COL_TYPE_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PokeObjetContract.ALIASED_COL_POKENPCOBJETSINTERNAL_ID,
        
        PokeObjetContract.ALIASED_COL_ID,
        
        PokeObjetContract.ALIASED_COL_NOM,
        
        PokeObjetContract.ALIASED_COL_QUANTITY,
        
        PokeObjetContract.ALIASED_COL_TYPE_ID
    };

    /** Convert PokeObjet entity to Content Values for database.
     *
     * @param item PokeObjet entity object
     * @param pokenpcId pokenpc id
     * @return ContentValues object
     */
    public static ContentValues itemToContentValues(final PokeObjet item,
                final int pokeNpcobjetsInternalId) {
        final ContentValues result = PokeObjetContract.itemToContentValues(item);
        result.put(PokeObjetContract.COL_POKENPCOBJETSINTERNAL_ID,
                String.valueOf(pokeNpcobjetsInternalId));
        return result;
    }

    /**
     * Converts a PokeObjet into a content values.
     *
     * @param item The PokeObjet to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final PokeObjet item) {
        final ContentValues result = new ContentValues();

              result.put(PokeObjetContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(PokeObjetContract.COL_NOM,
                    item.getNom());
            }

             result.put(PokeObjetContract.COL_QUANTITY,
                String.valueOf(item.getQuantity()));

             if (item.getType() != null) {
                result.put(PokeObjetContract.COL_TYPE_ID,
                    item.getType().getId());
            }


        return result;
    }

    /**
     * Converts a Cursor into a PokeObjet.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted PokeObjet
     */
    public static PokeObjet cursorToItem(final android.database.Cursor cursor) {
        PokeObjet result = new PokeObjet();
        PokeObjetContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to PokeObjet entity.
     * @param cursor Cursor object
     * @param result PokeObjet entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final PokeObjet result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PokeObjetContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokeObjetContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(PokeObjetContract.COL_QUANTITY);

            if (index > -1) {
                result.setQuantity(cursor.getInt(index));
            }
            if (result.getType() == null) {
                final PokeTypeObjet type = new PokeTypeObjet();
                index = cursor.getColumnIndex(PokeObjetContract.COL_TYPE_ID);

                if (index > -1) {
                    type.setId(cursor.getInt(index));
                    result.setType(type);
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of PokeObjet entity.
     * @param cursor Cursor object
     * @return Array of PokeObjet entity
     */
    public static ArrayList<PokeObjet> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<PokeObjet> result = new ArrayList<PokeObjet>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            PokeObjet item;
            do {
                item = PokeObjetContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
