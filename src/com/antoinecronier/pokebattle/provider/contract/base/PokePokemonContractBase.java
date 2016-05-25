/**************************************************************************
 * PokePokemonContractBase.java, pokebattle Android
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

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import com.antoinecronier.pokebattle.entity.PokePokemon;
import com.antoinecronier.pokebattle.entity.PokeTypePokemon;
import com.antoinecronier.pokebattle.entity.PokeAttaque;



import com.antoinecronier.pokebattle.provider.contract.PokePokemonContract;
import com.antoinecronier.pokebattle.harmony.util.DateUtils;

/** Pokebattle contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PokePokemonContractBase {


        /** PokeNpcpokemonsInternal_id. */
    public static final String COL_POKENPCPOKEMONSINTERNAL_ID =
            "PokeNpc_pokemons_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_POKENPCPOKEMONSINTERNAL_ID =
            PokePokemonContract.TABLE_NAME + "." + COL_POKENPCPOKEMONSINTERNAL_ID;

    /** PokeNpcteamInternal_id. */
    public static final String COL_POKENPCTEAMINTERNAL_ID =
            "PokeNpc_team_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_POKENPCTEAMINTERNAL_ID =
            PokePokemonContract.TABLE_NAME + "." + COL_POKENPCTEAMINTERNAL_ID;

    /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PokePokemonContract.TABLE_NAME + "." + COL_ID;

    /** surnom. */
    public static final String COL_SURNOM =
            "surnom";
    /** Alias. */
    public static final String ALIASED_COL_SURNOM =
            PokePokemonContract.TABLE_NAME + "." + COL_SURNOM;

    /** niveau. */
    public static final String COL_NIVEAU =
            "niveau";
    /** Alias. */
    public static final String ALIASED_COL_NIVEAU =
            PokePokemonContract.TABLE_NAME + "." + COL_NIVEAU;

    /** capture. */
    public static final String COL_CAPTURE =
            "capture";
    /** Alias. */
    public static final String ALIASED_COL_CAPTURE =
            PokePokemonContract.TABLE_NAME + "." + COL_CAPTURE;

    /** type_id. */
    public static final String COL_TYPE_ID =
            "type_id";
    /** Alias. */
    public static final String ALIASED_COL_TYPE_ID =
            PokePokemonContract.TABLE_NAME + "." + COL_TYPE_ID;

    /** attaque1_id. */
    public static final String COL_ATTAQUE1_ID =
            "attaque1_id";
    /** Alias. */
    public static final String ALIASED_COL_ATTAQUE1_ID =
            PokePokemonContract.TABLE_NAME + "." + COL_ATTAQUE1_ID;

    /** attaque2_id. */
    public static final String COL_ATTAQUE2_ID =
            "attaque2_id";
    /** Alias. */
    public static final String ALIASED_COL_ATTAQUE2_ID =
            PokePokemonContract.TABLE_NAME + "." + COL_ATTAQUE2_ID;

    /** attaque3_id. */
    public static final String COL_ATTAQUE3_ID =
            "attaque3_id";
    /** Alias. */
    public static final String ALIASED_COL_ATTAQUE3_ID =
            PokePokemonContract.TABLE_NAME + "." + COL_ATTAQUE3_ID;

    /** attaque4_id. */
    public static final String COL_ATTAQUE4_ID =
            "attaque4_id";
    /** Alias. */
    public static final String ALIASED_COL_ATTAQUE4_ID =
            PokePokemonContract.TABLE_NAME + "." + COL_ATTAQUE4_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "PokePokemon";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "PokePokemon";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PokePokemonContract.COL_POKENPCPOKEMONSINTERNAL_ID,
        
        PokePokemonContract.COL_POKENPCTEAMINTERNAL_ID,
        
        PokePokemonContract.COL_ID,
        
        PokePokemonContract.COL_SURNOM,
        
        PokePokemonContract.COL_NIVEAU,
        
        PokePokemonContract.COL_CAPTURE,
        
        PokePokemonContract.COL_TYPE_ID,
        
        PokePokemonContract.COL_ATTAQUE1_ID,
        
        PokePokemonContract.COL_ATTAQUE2_ID,
        
        PokePokemonContract.COL_ATTAQUE3_ID,
        
        PokePokemonContract.COL_ATTAQUE4_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PokePokemonContract.ALIASED_COL_POKENPCPOKEMONSINTERNAL_ID,
        
        PokePokemonContract.ALIASED_COL_POKENPCTEAMINTERNAL_ID,
        
        PokePokemonContract.ALIASED_COL_ID,
        
        PokePokemonContract.ALIASED_COL_SURNOM,
        
        PokePokemonContract.ALIASED_COL_NIVEAU,
        
        PokePokemonContract.ALIASED_COL_CAPTURE,
        
        PokePokemonContract.ALIASED_COL_TYPE_ID,
        
        PokePokemonContract.ALIASED_COL_ATTAQUE1_ID,
        
        PokePokemonContract.ALIASED_COL_ATTAQUE2_ID,
        
        PokePokemonContract.ALIASED_COL_ATTAQUE3_ID,
        
        PokePokemonContract.ALIASED_COL_ATTAQUE4_ID
    };

    /** Convert PokePokemon entity to Content Values for database.
     *
     * @param item PokePokemon entity object
     * @param pokenpcId pokenpc id
     * @param pokenpcId pokenpc id
     * @return ContentValues object
     */
    public static ContentValues itemToContentValues(final PokePokemon item,
                final int pokeNpcpokemonsInternalId,
                final int pokeNpcteamInternalId) {
        final ContentValues result = PokePokemonContract.itemToContentValues(item);
        result.put(PokePokemonContract.COL_POKENPCPOKEMONSINTERNAL_ID,
                String.valueOf(pokeNpcpokemonsInternalId));
        result.put(PokePokemonContract.COL_POKENPCTEAMINTERNAL_ID,
                String.valueOf(pokeNpcteamInternalId));
        return result;
    }

    /**
     * Converts a PokePokemon into a content values.
     *
     * @param item The PokePokemon to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final PokePokemon item) {
        final ContentValues result = new ContentValues();

               result.put(PokePokemonContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getSurnom() != null) {
                result.put(PokePokemonContract.COL_SURNOM,
                    item.getSurnom());
            }

             result.put(PokePokemonContract.COL_NIVEAU,
                String.valueOf(item.getNiveau()));

             if (item.getCapture() != null) {
                result.put(PokePokemonContract.COL_CAPTURE,
                    item.getCapture().toString(ISODateTimeFormat.dateTime()));
            } else {
                result.put(PokePokemonContract.COL_CAPTURE, (String) null);
            }

             if (item.getType() != null) {
                result.put(PokePokemonContract.COL_TYPE_ID,
                    item.getType().getId());
            } else {
                result.put(PokePokemonContract.COL_TYPE_ID, (String) null);
            }

             if (item.getAttaque1() != null) {
                result.put(PokePokemonContract.COL_ATTAQUE1_ID,
                    item.getAttaque1().getId());
            } else {
                result.put(PokePokemonContract.COL_ATTAQUE1_ID, (String) null);
            }

             if (item.getAttaque2() != null) {
                result.put(PokePokemonContract.COL_ATTAQUE2_ID,
                    item.getAttaque2().getId());
            } else {
                result.put(PokePokemonContract.COL_ATTAQUE2_ID, (String) null);
            }

             if (item.getAttaque3() != null) {
                result.put(PokePokemonContract.COL_ATTAQUE3_ID,
                    item.getAttaque3().getId());
            } else {
                result.put(PokePokemonContract.COL_ATTAQUE3_ID, (String) null);
            }

             if (item.getAttaque4() != null) {
                result.put(PokePokemonContract.COL_ATTAQUE4_ID,
                    item.getAttaque4().getId());
            } else {
                result.put(PokePokemonContract.COL_ATTAQUE4_ID, (String) null);
            }


        return result;
    }

    /**
     * Converts a Cursor into a PokePokemon.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted PokePokemon
     */
    public static PokePokemon cursorToItem(final android.database.Cursor cursor) {
        PokePokemon result = new PokePokemon();
        PokePokemonContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to PokePokemon entity.
     * @param cursor Cursor object
     * @param result PokePokemon entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final PokePokemon result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PokePokemonContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokePokemonContract.COL_SURNOM);

            if (index > -1) {
                result.setSurnom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(PokePokemonContract.COL_NIVEAU);

            if (index > -1) {
                result.setNiveau(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokePokemonContract.COL_CAPTURE);

            if (index > -1) {
            if (!cursor.isNull(index)) {
                    final DateTime dtCapture =
                        DateUtils.formatISOStringToDateTime(cursor.getString(index));
                    if (dtCapture != null) {
                            result.setCapture(dtCapture);
                    } else {
                        result.setCapture(new DateTime());
                    }
            }
            }
            if (result.getType() == null) {
                final PokeTypePokemon type = new PokeTypePokemon();
                index = cursor.getColumnIndex(PokePokemonContract.COL_TYPE_ID);

                if (index > -1) {
                    if (!cursor.isNull(index)) {
                        type.setId(cursor.getInt(index));
                        result.setType(type);
                    }
                }

            }
            if (result.getAttaque1() == null) {
                final PokeAttaque attaque1 = new PokeAttaque();
                index = cursor.getColumnIndex(PokePokemonContract.COL_ATTAQUE1_ID);

                if (index > -1) {
                    if (!cursor.isNull(index)) {
                        attaque1.setId(cursor.getInt(index));
                        result.setAttaque1(attaque1);
                    }
                }

            }
            if (result.getAttaque2() == null) {
                final PokeAttaque attaque2 = new PokeAttaque();
                index = cursor.getColumnIndex(PokePokemonContract.COL_ATTAQUE2_ID);

                if (index > -1) {
                    if (!cursor.isNull(index)) {
                        attaque2.setId(cursor.getInt(index));
                        result.setAttaque2(attaque2);
                    }
                }

            }
            if (result.getAttaque3() == null) {
                final PokeAttaque attaque3 = new PokeAttaque();
                index = cursor.getColumnIndex(PokePokemonContract.COL_ATTAQUE3_ID);

                if (index > -1) {
                    if (!cursor.isNull(index)) {
                        attaque3.setId(cursor.getInt(index));
                        result.setAttaque3(attaque3);
                    }
                }

            }
            if (result.getAttaque4() == null) {
                final PokeAttaque attaque4 = new PokeAttaque();
                index = cursor.getColumnIndex(PokePokemonContract.COL_ATTAQUE4_ID);

                if (index > -1) {
                    if (!cursor.isNull(index)) {
                        attaque4.setId(cursor.getInt(index));
                        result.setAttaque4(attaque4);
                    }
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of PokePokemon entity.
     * @param cursor Cursor object
     * @return Array of PokePokemon entity
     */
    public static ArrayList<PokePokemon> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<PokePokemon> result = new ArrayList<PokePokemon>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            PokePokemon item;
            do {
                item = PokePokemonContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
