/**************************************************************************
 * PokeDresseurContractBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokeDresseur;
import com.antoinecronier.pokebattle.entity.PokeNpc;



import com.antoinecronier.pokebattle.provider.contract.PokeDresseurContract;

/** Pokebattle contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PokeDresseurContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PokeDresseurContract.TABLE_NAME + "." + COL_ID;

    /** pseudo. */
    public static final String COL_PSEUDO =
            "pseudo";
    /** Alias. */
    public static final String ALIASED_COL_PSEUDO =
            PokeDresseurContract.TABLE_NAME + "." + COL_PSEUDO;

    /** login. */
    public static final String COL_LOGIN =
            "login";
    /** Alias. */
    public static final String ALIASED_COL_LOGIN =
            PokeDresseurContract.TABLE_NAME + "." + COL_LOGIN;

    /** password. */
    public static final String COL_PASSWORD =
            "password";
    /** Alias. */
    public static final String ALIASED_COL_PASSWORD =
            PokeDresseurContract.TABLE_NAME + "." + COL_PASSWORD;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "PokeDresseur";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "PokeDresseur";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PokeDresseurContract.COL_ID,
        
        PokeDresseurContract.COL_PSEUDO,
        
        PokeDresseurContract.COL_LOGIN,
        
        PokeDresseurContract.COL_PASSWORD,
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PokeDresseurContract.ALIASED_COL_ID,
        
        PokeDresseurContract.ALIASED_COL_PSEUDO,
        
        PokeDresseurContract.ALIASED_COL_LOGIN,
        
        PokeDresseurContract.ALIASED_COL_PASSWORD,
        
    };


    /**
     * Converts a PokeDresseur into a content values.
     *
     * @param item The PokeDresseur to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final PokeDresseur item) {
        final ContentValues result = new ContentValues();

             result.put(PokeDresseurContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getPseudo() != null) {
                result.put(PokeDresseurContract.COL_PSEUDO,
                    item.getPseudo());
            }

             if (item.getLogin() != null) {
                result.put(PokeDresseurContract.COL_LOGIN,
                    item.getLogin());
            }

             if (item.getPassword() != null) {
                result.put(PokeDresseurContract.COL_PASSWORD,
                    item.getPassword());
            }

 
        return result;
    }

    /**
     * Converts a Cursor into a PokeDresseur.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted PokeDresseur
     */
    public static PokeDresseur cursorToItem(final android.database.Cursor cursor) {
        PokeDresseur result = new PokeDresseur();
        PokeDresseurContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to PokeDresseur entity.
     * @param cursor Cursor object
     * @param result PokeDresseur entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final PokeDresseur result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PokeDresseurContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokeDresseurContract.COL_PSEUDO);

            if (index > -1) {
                result.setPseudo(cursor.getString(index));
            }
            index = cursor.getColumnIndex(PokeDresseurContract.COL_LOGIN);

            if (index > -1) {
                result.setLogin(cursor.getString(index));
            }
            index = cursor.getColumnIndex(PokeDresseurContract.COL_PASSWORD);

            if (index > -1) {
                result.setPassword(cursor.getString(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of PokeDresseur entity.
     * @param cursor Cursor object
     * @return Array of PokeDresseur entity
     */
    public static ArrayList<PokeDresseur> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<PokeDresseur> result = new ArrayList<PokeDresseur>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            PokeDresseur item;
            do {
                item = PokeDresseurContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
