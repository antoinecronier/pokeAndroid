/**************************************************************************
 * PokeAreneContractBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokeArene;
import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.entity.PokeZone;
import com.antoinecronier.pokebattle.entity.PokePosition;



import com.antoinecronier.pokebattle.provider.contract.PokeAreneContract;

/** Pokebattle contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PokeAreneContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PokeAreneContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            PokeAreneContract.TABLE_NAME + "." + COL_NOM;

    /** maitre_id. */
    public static final String COL_MAITRE_ID =
            "maitre_id";
    /** Alias. */
    public static final String ALIASED_COL_MAITRE_ID =
            PokeAreneContract.TABLE_NAME + "." + COL_MAITRE_ID;

    /** badge_id. */
    public static final String COL_BADGE_ID =
            "badge_id";
    /** Alias. */
    public static final String ALIASED_COL_BADGE_ID =
            PokeAreneContract.TABLE_NAME + "." + COL_BADGE_ID;

    /** zone_id. */
    public static final String COL_ZONE_ID =
            "zone_id";
    /** Alias. */
    public static final String ALIASED_COL_ZONE_ID =
            PokeAreneContract.TABLE_NAME + "." + COL_ZONE_ID;

    /** position_id. */
    public static final String COL_POSITION_ID =
            "position_id";
    /** Alias. */
    public static final String ALIASED_COL_POSITION_ID =
            PokeAreneContract.TABLE_NAME + "." + COL_POSITION_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "PokeArene";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "PokeArene";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PokeAreneContract.COL_ID,
        
        PokeAreneContract.COL_NOM,
        
        PokeAreneContract.COL_MAITRE_ID,
        
        PokeAreneContract.COL_BADGE_ID,
        
        PokeAreneContract.COL_ZONE_ID,
        
        PokeAreneContract.COL_POSITION_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PokeAreneContract.ALIASED_COL_ID,
        
        PokeAreneContract.ALIASED_COL_NOM,
        
        PokeAreneContract.ALIASED_COL_MAITRE_ID,
        
        
        PokeAreneContract.ALIASED_COL_BADGE_ID,
        
        PokeAreneContract.ALIASED_COL_ZONE_ID,
        
        PokeAreneContract.ALIASED_COL_POSITION_ID
    };


    /**
     * Converts a PokeArene into a content values.
     *
     * @param item The PokeArene to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final PokeArene item) {
        final ContentValues result = new ContentValues();

             result.put(PokeAreneContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(PokeAreneContract.COL_NOM,
                    item.getNom());
            }

             if (item.getMaitre() != null) {
                result.put(PokeAreneContract.COL_MAITRE_ID,
                    item.getMaitre().getId());
            }

              if (item.getBadge() != null) {
                result.put(PokeAreneContract.COL_BADGE_ID,
                    item.getBadge().getId());
            }

             if (item.getZone() != null) {
                result.put(PokeAreneContract.COL_ZONE_ID,
                    item.getZone().getId());
            }

             if (item.getPosition() != null) {
                result.put(PokeAreneContract.COL_POSITION_ID,
                    item.getPosition().getId());
            }


        return result;
    }

    /**
     * Converts a Cursor into a PokeArene.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted PokeArene
     */
    public static PokeArene cursorToItem(final android.database.Cursor cursor) {
        PokeArene result = new PokeArene();
        PokeAreneContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to PokeArene entity.
     * @param cursor Cursor object
     * @param result PokeArene entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final PokeArene result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PokeAreneContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokeAreneContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            if (result.getMaitre() == null) {
                final PokeNpc maitre = new PokeNpc();
                index = cursor.getColumnIndex(PokeAreneContract.COL_MAITRE_ID);

                if (index > -1) {
                    maitre.setId(cursor.getInt(index));
                    result.setMaitre(maitre);
                }

            }
            if (result.getBadge() == null) {
                final PokeBadge badge = new PokeBadge();
                index = cursor.getColumnIndex(PokeAreneContract.COL_BADGE_ID);

                if (index > -1) {
                    badge.setId(cursor.getInt(index));
                    result.setBadge(badge);
                }

            }
            if (result.getZone() == null) {
                final PokeZone zone = new PokeZone();
                index = cursor.getColumnIndex(PokeAreneContract.COL_ZONE_ID);

                if (index > -1) {
                    zone.setId(cursor.getInt(index));
                    result.setZone(zone);
                }

            }
            if (result.getPosition() == null) {
                final PokePosition position = new PokePosition();
                index = cursor.getColumnIndex(PokeAreneContract.COL_POSITION_ID);

                if (index > -1) {
                    position.setId(cursor.getInt(index));
                    result.setPosition(position);
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of PokeArene entity.
     * @param cursor Cursor object
     * @return Array of PokeArene entity
     */
    public static ArrayList<PokeArene> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<PokeArene> result = new ArrayList<PokeArene>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            PokeArene item;
            do {
                item = PokeAreneContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
