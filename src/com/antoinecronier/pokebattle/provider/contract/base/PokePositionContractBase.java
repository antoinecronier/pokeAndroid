/**************************************************************************
 * PokePositionContractBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokePosition;



import com.antoinecronier.pokebattle.provider.contract.PokePositionContract;

/** Pokebattle contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PokePositionContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PokePositionContract.TABLE_NAME + "." + COL_ID;

    /** x. */
    public static final String COL_X =
            "x";
    /** Alias. */
    public static final String ALIASED_COL_X =
            PokePositionContract.TABLE_NAME + "." + COL_X;

    /** y. */
    public static final String COL_Y =
            "y";
    /** Alias. */
    public static final String ALIASED_COL_Y =
            PokePositionContract.TABLE_NAME + "." + COL_Y;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "PokePosition";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "PokePosition";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PokePositionContract.COL_ID,
        
        PokePositionContract.COL_X,
        
        PokePositionContract.COL_Y
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PokePositionContract.ALIASED_COL_ID,
        
        PokePositionContract.ALIASED_COL_X,
        
        PokePositionContract.ALIASED_COL_Y
    };


    /**
     * Converts a PokePosition into a content values.
     *
     * @param item The PokePosition to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final PokePosition item) {
        final ContentValues result = new ContentValues();

             result.put(PokePositionContract.COL_ID,
                String.valueOf(item.getId()));

             result.put(PokePositionContract.COL_X,
                String.valueOf(item.getX()));

             result.put(PokePositionContract.COL_Y,
                String.valueOf(item.getY()));


        return result;
    }

    /**
     * Converts a Cursor into a PokePosition.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted PokePosition
     */
    public static PokePosition cursorToItem(final android.database.Cursor cursor) {
        PokePosition result = new PokePosition();
        PokePositionContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to PokePosition entity.
     * @param cursor Cursor object
     * @param result PokePosition entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final PokePosition result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PokePositionContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokePositionContract.COL_X);

            if (index > -1) {
                result.setX(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokePositionContract.COL_Y);

            if (index > -1) {
                result.setY(cursor.getInt(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of PokePosition entity.
     * @param cursor Cursor object
     * @return Array of PokePosition entity
     */
    public static ArrayList<PokePosition> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<PokePosition> result = new ArrayList<PokePosition>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            PokePosition item;
            do {
                item = PokePositionContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
