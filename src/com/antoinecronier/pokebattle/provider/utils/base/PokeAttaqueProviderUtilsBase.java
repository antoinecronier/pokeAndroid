/**************************************************************************
 * PokeAttaqueProviderUtilsBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.criterias.base.CriteriaExpression;
import com.antoinecronier.pokebattle.criterias.base.CriteriaExpression.GroupType;

import com.antoinecronier.pokebattle.entity.PokeAttaque;
import com.antoinecronier.pokebattle.entity.PokeType;

import com.antoinecronier.pokebattle.provider.PokeAttaqueProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokeTypeProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeAttaqueContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;

/**
 * PokeAttaque Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PokeAttaqueProviderUtilsBase
            extends ProviderUtils<PokeAttaque> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PokeAttaqueProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PokeAttaqueProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final PokeAttaque item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PokeAttaqueContract.itemToContentValues(item);
        itemValues.remove(PokeAttaqueContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokeAttaqueProviderAdapter.POKEATTAQUE_URI)
                        .withValues(itemValues)
                        .build());


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
     * @param item PokeAttaque
     * @return number of row affected
     */
    public int delete(final PokeAttaque item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PokeAttaqueProviderAdapter.POKEATTAQUE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return PokeAttaque
     */
    public PokeAttaque query(final PokeAttaque item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return PokeAttaque
     */
    public PokeAttaque query(final int id) {
        PokeAttaque result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PokeAttaqueContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            PokeAttaqueProviderAdapter.POKEATTAQUE_URI,
            PokeAttaqueContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PokeAttaqueContract.cursorToItem(cursor);

            if (result.getType() != null) {
                result.setType(
                    this.getAssociateType(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<PokeAttaque>
     */
    public ArrayList<PokeAttaque> queryAll() {
        ArrayList<PokeAttaque> result =
                    new ArrayList<PokeAttaque>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeAttaqueProviderAdapter.POKEATTAQUE_URI,
                PokeAttaqueContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PokeAttaqueContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<PokeAttaque>
     */
    public ArrayList<PokeAttaque> query(CriteriaExpression expression) {
        ArrayList<PokeAttaque> result =
                    new ArrayList<PokeAttaque>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeAttaqueProviderAdapter.POKEATTAQUE_URI,
                PokeAttaqueContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PokeAttaqueContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item PokeAttaque
     
     * @return number of rows updated
     */
    public int update(final PokeAttaque item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokeAttaqueContract.itemToContentValues(
                item);

        Uri uri = PokeAttaqueProviderAdapter.POKEATTAQUE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());



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
     * Get associate Type.
     * @param item PokeAttaque
     * @return PokeType
     */
    public PokeType getAssociateType(
            final PokeAttaque item) {
        PokeType result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeTypeCursor = prov.query(
                PokeTypeProviderAdapter.POKETYPE_URI,
                PokeTypeContract.ALIASED_COLS,
                PokeTypeContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getType().getId())},
                null);

        if (pokeTypeCursor.getCount() > 0) {
            pokeTypeCursor.moveToFirst();
            result = PokeTypeContract.cursorToItem(pokeTypeCursor);
        } else {
            result = null;
        }
        pokeTypeCursor.close();

        return result;
    }

}
