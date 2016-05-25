/**************************************************************************
 * PokeAreneProviderUtilsBase.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.provider.utils.base;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;

import android.content.ContentResolver;
import android.content.ContentValues;

import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;


import com.antoinecronier.pokebattle.provider.utils.ProviderUtils;
import com.antoinecronier.pokebattle.criterias.base.Criterion;
import com.antoinecronier.pokebattle.criterias.base.Criterion.Type;
import com.antoinecronier.pokebattle.criterias.base.value.ArrayValue;
import com.antoinecronier.pokebattle.criterias.base.CriteriaExpression;
import com.antoinecronier.pokebattle.criterias.base.CriteriaExpression.GroupType;

import com.antoinecronier.pokebattle.entity.PokeArene;
import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.entity.PokeZone;
import com.antoinecronier.pokebattle.entity.PokePosition;

import com.antoinecronier.pokebattle.provider.PokeAreneProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokeNpcProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokeBadgeProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokeZoneProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokePositionProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeAreneContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;
import com.antoinecronier.pokebattle.provider.contract.PokePositionContract;

/**
 * PokeArene Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PokeAreneProviderUtilsBase
            extends ProviderUtils<PokeArene> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PokeAreneProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PokeAreneProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final PokeArene item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PokeAreneContract.itemToContentValues(item);
        itemValues.remove(PokeAreneContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokeAreneProviderAdapter.POKEARENE_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getDresseurs() != null && item.getDresseurs().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokeNpcContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getDresseurs().size(); i++) {
                inValue.addValue(String.valueOf(item.getDresseurs().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokeNpcProviderAdapter.POKENPC_URI)
                    .withValueBackReference(
                            PokeNpcContract
                                    .COL_POKEARENEDRESSEURSINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }

        try {
            ContentProviderResult[] results =
                    prov.applyBatch(PokebattleProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setId(Integer.parseInt(result.getPathSegments().get(1)));
            }
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }


    /**
     * Delete from DB.
     * @param item PokeArene
     * @return number of row affected
     */
    public int delete(final PokeArene item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PokeAreneProviderAdapter.POKEARENE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return PokeArene
     */
    public PokeArene query(final PokeArene item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return PokeArene
     */
    public PokeArene query(final int id) {
        PokeArene result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PokeAreneContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            PokeAreneProviderAdapter.POKEARENE_URI,
            PokeAreneContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PokeAreneContract.cursorToItem(cursor);

            if (result.getMaitre() != null) {
                result.setMaitre(
                    this.getAssociateMaitre(result));
            }
            result.setDresseurs(
                this.getAssociateDresseurs(result));
            if (result.getBadge() != null) {
                result.setBadge(
                    this.getAssociateBadge(result));
            }
            if (result.getZone() != null) {
                result.setZone(
                    this.getAssociateZone(result));
            }
            if (result.getPosition() != null) {
                result.setPosition(
                    this.getAssociatePosition(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<PokeArene>
     */
    public ArrayList<PokeArene> queryAll() {
        ArrayList<PokeArene> result =
                    new ArrayList<PokeArene>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeAreneProviderAdapter.POKEARENE_URI,
                PokeAreneContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PokeAreneContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<PokeArene>
     */
    public ArrayList<PokeArene> query(CriteriaExpression expression) {
        ArrayList<PokeArene> result =
                    new ArrayList<PokeArene>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeAreneProviderAdapter.POKEARENE_URI,
                PokeAreneContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PokeAreneContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item PokeArene
     
     * @return number of rows updated
     */
    public int update(final PokeArene item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokeAreneContract.itemToContentValues(
                item);

        Uri uri = PokeAreneProviderAdapter.POKEARENE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getDresseurs() != null && item.getDresseurs().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new dresseurs for PokeArene
            CriteriaExpression dresseursCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokeNpcContract.COL_ID);
            crit.addValue(values);
            dresseursCrit.add(crit);


            for (PokeNpc dresseurs : item.getDresseurs()) {
                values.addValue(
                    String.valueOf(dresseurs.getId()));
            }
            selection = dresseursCrit.toSQLiteSelection();
            selectionArgs = dresseursCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokeNpcProviderAdapter.POKENPC_URI)
                    .withValue(
                            PokeNpcContract.COL_POKEARENEDRESSEURSINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated dresseurs
            crit.setType(Type.NOT_IN);
            dresseursCrit.add(PokeNpcContract.COL_POKEARENEDRESSEURSINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokeNpcProviderAdapter.POKENPC_URI)
                    .withValue(
                            PokeNpcContract.COL_POKEARENEDRESSEURSINTERNAL_ID,
                            null)
                    .withSelection(
                            dresseursCrit.toSQLiteSelection(),
                            dresseursCrit.toSQLiteSelectionArgs())
                    .build());
        }


        try {
            ContentProviderResult[] results = prov.applyBatch(PokebattleProvider.authority, operations);
            result = results[0].count;
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /** Relations operations. */
    /**
     * Get associate Maitre.
     * @param item PokeArene
     * @return PokeNpc
     */
    public PokeNpc getAssociateMaitre(
            final PokeArene item) {
        PokeNpc result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeNpcCursor = prov.query(
                PokeNpcProviderAdapter.POKENPC_URI,
                PokeNpcContract.ALIASED_COLS,
                PokeNpcContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getMaitre().getId())},
                null);

        if (pokeNpcCursor.getCount() > 0) {
            pokeNpcCursor.moveToFirst();
            result = PokeNpcContract.cursorToItem(pokeNpcCursor);
        } else {
            result = null;
        }
        pokeNpcCursor.close();

        return result;
    }

    /**
     * Get associate Dresseurs.
     * @param item PokeArene
     * @return PokeNpc
     */
    public ArrayList<PokeNpc> getAssociateDresseurs(
            final PokeArene item) {
        ArrayList<PokeNpc> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeNpcCursor = prov.query(
                PokeNpcProviderAdapter.POKENPC_URI,
                PokeNpcContract.ALIASED_COLS,
                PokeNpcContract.ALIASED_COL_POKEARENEDRESSEURSINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = PokeNpcContract.cursorToItems(
                        pokeNpcCursor);
        pokeNpcCursor.close();

        return result;
    }

    /**
     * Get associate Badge.
     * @param item PokeArene
     * @return PokeBadge
     */
    public PokeBadge getAssociateBadge(
            final PokeArene item) {
        PokeBadge result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeBadgeCursor = prov.query(
                PokeBadgeProviderAdapter.POKEBADGE_URI,
                PokeBadgeContract.ALIASED_COLS,
                PokeBadgeContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getBadge().getId())},
                null);

        if (pokeBadgeCursor.getCount() > 0) {
            pokeBadgeCursor.moveToFirst();
            result = PokeBadgeContract.cursorToItem(pokeBadgeCursor);
        } else {
            result = null;
        }
        pokeBadgeCursor.close();

        return result;
    }

    /**
     * Get associate Zone.
     * @param item PokeArene
     * @return PokeZone
     */
    public PokeZone getAssociateZone(
            final PokeArene item) {
        PokeZone result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeZoneCursor = prov.query(
                PokeZoneProviderAdapter.POKEZONE_URI,
                PokeZoneContract.ALIASED_COLS,
                PokeZoneContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getZone().getId())},
                null);

        if (pokeZoneCursor.getCount() > 0) {
            pokeZoneCursor.moveToFirst();
            result = PokeZoneContract.cursorToItem(pokeZoneCursor);
        } else {
            result = null;
        }
        pokeZoneCursor.close();

        return result;
    }

    /**
     * Get associate Position.
     * @param item PokeArene
     * @return PokePosition
     */
    public PokePosition getAssociatePosition(
            final PokeArene item) {
        PokePosition result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokePositionCursor = prov.query(
                PokePositionProviderAdapter.POKEPOSITION_URI,
                PokePositionContract.ALIASED_COLS,
                PokePositionContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getPosition().getId())},
                null);

        if (pokePositionCursor.getCount() > 0) {
            pokePositionCursor.moveToFirst();
            result = PokePositionContract.cursorToItem(pokePositionCursor);
        } else {
            result = null;
        }
        pokePositionCursor.close();

        return result;
    }

}
