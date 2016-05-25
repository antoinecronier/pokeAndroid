/**************************************************************************
 * PokeTypePokemonContractBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokeTypePokemon;
import com.antoinecronier.pokebattle.entity.PokeType;
import com.antoinecronier.pokebattle.entity.PokeZone;



import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;

/** Pokebattle contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PokeTypePokemonContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PokeTypePokemonContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            PokeTypePokemonContract.TABLE_NAME + "." + COL_NOM;

    /** attaque. */
    public static final String COL_ATTAQUE =
            "attaque";
    /** Alias. */
    public static final String ALIASED_COL_ATTAQUE =
            PokeTypePokemonContract.TABLE_NAME + "." + COL_ATTAQUE;

    /** attaque_spe. */
    public static final String COL_ATTAQUE_SPE =
            "attaque_spe";
    /** Alias. */
    public static final String ALIASED_COL_ATTAQUE_SPE =
            PokeTypePokemonContract.TABLE_NAME + "." + COL_ATTAQUE_SPE;

    /** defence. */
    public static final String COL_DEFENCE =
            "defence";
    /** Alias. */
    public static final String ALIASED_COL_DEFENCE =
            PokeTypePokemonContract.TABLE_NAME + "." + COL_DEFENCE;

    /** defence_spe. */
    public static final String COL_DEFENCE_SPE =
            "defence_spe";
    /** Alias. */
    public static final String ALIASED_COL_DEFENCE_SPE =
            PokeTypePokemonContract.TABLE_NAME + "." + COL_DEFENCE_SPE;

    /** vitesse. */
    public static final String COL_VITESSE =
            "vitesse";
    /** Alias. */
    public static final String ALIASED_COL_VITESSE =
            PokeTypePokemonContract.TABLE_NAME + "." + COL_VITESSE;

    /** pv. */
    public static final String COL_PV =
            "pv";
    /** Alias. */
    public static final String ALIASED_COL_PV =
            PokeTypePokemonContract.TABLE_NAME + "." + COL_PV;

    /** pokedex. */
    public static final String COL_POKEDEX =
            "pokedex";
    /** Alias. */
    public static final String ALIASED_COL_POKEDEX =
            PokeTypePokemonContract.TABLE_NAME + "." + COL_POKEDEX;

    /** evolue_id. */
    public static final String COL_EVOLUE_ID =
            "evolue_id";
    /** Alias. */
    public static final String ALIASED_COL_EVOLUE_ID =
            PokeTypePokemonContract.TABLE_NAME + "." + COL_EVOLUE_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "PokeTypePokemon";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "PokeTypePokemon";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PokeTypePokemonContract.COL_ID,
        
        PokeTypePokemonContract.COL_NOM,
        
        PokeTypePokemonContract.COL_ATTAQUE,
        
        PokeTypePokemonContract.COL_ATTAQUE_SPE,
        
        PokeTypePokemonContract.COL_DEFENCE,
        
        PokeTypePokemonContract.COL_DEFENCE_SPE,
        
        PokeTypePokemonContract.COL_VITESSE,
        
        PokeTypePokemonContract.COL_PV,
        
        PokeTypePokemonContract.COL_POKEDEX,
        
        PokeTypePokemonContract.COL_EVOLUE_ID,
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PokeTypePokemonContract.ALIASED_COL_ID,
        
        PokeTypePokemonContract.ALIASED_COL_NOM,
        
        PokeTypePokemonContract.ALIASED_COL_ATTAQUE,
        
        PokeTypePokemonContract.ALIASED_COL_ATTAQUE_SPE,
        
        PokeTypePokemonContract.ALIASED_COL_DEFENCE,
        
        PokeTypePokemonContract.ALIASED_COL_DEFENCE_SPE,
        
        PokeTypePokemonContract.ALIASED_COL_VITESSE,
        
        PokeTypePokemonContract.ALIASED_COL_PV,
        
        PokeTypePokemonContract.ALIASED_COL_POKEDEX,
        
        PokeTypePokemonContract.ALIASED_COL_EVOLUE_ID,
        
        
    };


    /**
     * Converts a PokeTypePokemon into a content values.
     *
     * @param item The PokeTypePokemon to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final PokeTypePokemon item) {
        final ContentValues result = new ContentValues();

             result.put(PokeTypePokemonContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(PokeTypePokemonContract.COL_NOM,
                    item.getNom());
            }

             result.put(PokeTypePokemonContract.COL_ATTAQUE,
                String.valueOf(item.getAttaque()));

             result.put(PokeTypePokemonContract.COL_ATTAQUE_SPE,
                String.valueOf(item.getAttaque_spe()));

             result.put(PokeTypePokemonContract.COL_DEFENCE,
                String.valueOf(item.getDefence()));

             result.put(PokeTypePokemonContract.COL_DEFENCE_SPE,
                String.valueOf(item.getDefence_spe()));

             result.put(PokeTypePokemonContract.COL_VITESSE,
                String.valueOf(item.getVitesse()));

             result.put(PokeTypePokemonContract.COL_PV,
                String.valueOf(item.getPv()));

             result.put(PokeTypePokemonContract.COL_POKEDEX,
                String.valueOf(item.getPokedex()));

             if (item.getEvolue() != null) {
                result.put(PokeTypePokemonContract.COL_EVOLUE_ID,
                    item.getEvolue().getId());
            } else {
                result.put(PokeTypePokemonContract.COL_EVOLUE_ID, (String) null);
            }

  
        return result;
    }

    /**
     * Converts a Cursor into a PokeTypePokemon.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted PokeTypePokemon
     */
    public static PokeTypePokemon cursorToItem(final android.database.Cursor cursor) {
        PokeTypePokemon result = new PokeTypePokemon();
        PokeTypePokemonContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to PokeTypePokemon entity.
     * @param cursor Cursor object
     * @param result PokeTypePokemon entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final PokeTypePokemon result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PokeTypePokemonContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokeTypePokemonContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(PokeTypePokemonContract.COL_ATTAQUE);

            if (index > -1) {
                result.setAttaque(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokeTypePokemonContract.COL_ATTAQUE_SPE);

            if (index > -1) {
                result.setAttaque_spe(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokeTypePokemonContract.COL_DEFENCE);

            if (index > -1) {
                result.setDefence(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokeTypePokemonContract.COL_DEFENCE_SPE);

            if (index > -1) {
                result.setDefence_spe(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokeTypePokemonContract.COL_VITESSE);

            if (index > -1) {
                result.setVitesse(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokeTypePokemonContract.COL_PV);

            if (index > -1) {
                result.setPv(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokeTypePokemonContract.COL_POKEDEX);

            if (index > -1) {
                result.setPokedex(cursor.getInt(index));
            }
            if (result.getEvolue() == null) {
                final PokeTypePokemon evolue = new PokeTypePokemon();
                index = cursor.getColumnIndex(PokeTypePokemonContract.COL_EVOLUE_ID);

                if (index > -1) {
                    if (!cursor.isNull(index)) {
                        evolue.setId(cursor.getInt(index));
                        result.setEvolue(evolue);
                    }
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of PokeTypePokemon entity.
     * @param cursor Cursor object
     * @return Array of PokeTypePokemon entity
     */
    public static ArrayList<PokeTypePokemon> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<PokeTypePokemon> result = new ArrayList<PokeTypePokemon>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            PokeTypePokemon item;
            do {
                item = PokeTypePokemonContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
