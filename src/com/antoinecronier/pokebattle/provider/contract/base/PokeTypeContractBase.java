/**************************************************************************
 * PokeTypeContractBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokeType;



import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;

/** Pokebattle contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PokeTypeContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PokeTypeContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            PokeTypeContract.TABLE_NAME + "." + COL_NOM;

    /** modificateur. */
    public static final String COL_MODIFICATEUR =
            "modificateur";
    /** Alias. */
    public static final String ALIASED_COL_MODIFICATEUR =
            PokeTypeContract.TABLE_NAME + "." + COL_MODIFICATEUR;

    /** PokeTypetypeFortInternal_id. */
    public static final String COL_POKETYPETYPEFORTINTERNAL_ID =
            "PokeType_typeFort_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_POKETYPETYPEFORTINTERNAL_ID =
            PokeTypeContract.TABLE_NAME + "." + COL_POKETYPETYPEFORTINTERNAL_ID;

    /** PokeTypetypeFaibleInternal_id. */
    public static final String COL_POKETYPETYPEFAIBLEINTERNAL_ID =
            "PokeType_typeFaible_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_POKETYPETYPEFAIBLEINTERNAL_ID =
            PokeTypeContract.TABLE_NAME + "." + COL_POKETYPETYPEFAIBLEINTERNAL_ID;

    /** PokeTypePokemontypesInternal_id. */
    public static final String COL_POKETYPEPOKEMONTYPESINTERNAL_ID =
            "PokeTypePokemon_types_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_POKETYPEPOKEMONTYPESINTERNAL_ID =
            PokeTypeContract.TABLE_NAME + "." + COL_POKETYPEPOKEMONTYPESINTERNAL_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "PokeType";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "PokeType";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PokeTypeContract.COL_ID,
        
        PokeTypeContract.COL_NOM,
        
        PokeTypeContract.COL_MODIFICATEUR,
        
        PokeTypeContract.COL_POKETYPETYPEFORTINTERNAL_ID,
        
        PokeTypeContract.COL_POKETYPETYPEFAIBLEINTERNAL_ID,
        
        PokeTypeContract.COL_POKETYPEPOKEMONTYPESINTERNAL_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PokeTypeContract.ALIASED_COL_ID,
        
        PokeTypeContract.ALIASED_COL_NOM,
        
        PokeTypeContract.ALIASED_COL_MODIFICATEUR,
        
        PokeTypeContract.ALIASED_COL_POKETYPETYPEFORTINTERNAL_ID,
        
        
        PokeTypeContract.ALIASED_COL_POKETYPETYPEFAIBLEINTERNAL_ID,
        
        
        PokeTypeContract.ALIASED_COL_POKETYPEPOKEMONTYPESINTERNAL_ID
    };

    /** Convert PokeType entity to Content Values for database.
     *
     * @param item PokeType entity object
     * @param poketypeId poketype id
     * @param poketypeId poketype id
     * @param poketypepokemonId poketypepokemon id
     * @return ContentValues object
     */
    public static ContentValues itemToContentValues(final PokeType item,
                final int pokeTypetypeFortInternalId,
                final int pokeTypetypeFaibleInternalId,
                final int pokeTypePokemontypesInternalId) {
        final ContentValues result = PokeTypeContract.itemToContentValues(item);
        result.put(PokeTypeContract.COL_POKETYPETYPEFORTINTERNAL_ID,
                String.valueOf(pokeTypetypeFortInternalId));
        result.put(PokeTypeContract.COL_POKETYPETYPEFAIBLEINTERNAL_ID,
                String.valueOf(pokeTypetypeFaibleInternalId));
        result.put(PokeTypeContract.COL_POKETYPEPOKEMONTYPESINTERNAL_ID,
                String.valueOf(pokeTypePokemontypesInternalId));
        return result;
    }

    /**
     * Converts a PokeType into a content values.
     *
     * @param item The PokeType to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final PokeType item) {
        final ContentValues result = new ContentValues();

             result.put(PokeTypeContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(PokeTypeContract.COL_NOM,
                    item.getNom());
            }

             result.put(PokeTypeContract.COL_MODIFICATEUR,
                String.valueOf(item.getModificateur()));

     
        return result;
    }

    /**
     * Converts a Cursor into a PokeType.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted PokeType
     */
    public static PokeType cursorToItem(final android.database.Cursor cursor) {
        PokeType result = new PokeType();
        PokeTypeContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to PokeType entity.
     * @param cursor Cursor object
     * @param result PokeType entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final PokeType result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PokeTypeContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokeTypeContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(PokeTypeContract.COL_MODIFICATEUR);

            if (index > -1) {
                result.setModificateur(cursor.getInt(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of PokeType entity.
     * @param cursor Cursor object
     * @return Array of PokeType entity
     */
    public static ArrayList<PokeType> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<PokeType> result = new ArrayList<PokeType>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            PokeType item;
            do {
                item = PokeTypeContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
