/**************************************************************************
 * PokeBadgeProviderUtilsBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.entity.PokeBadgeBonus;

import com.antoinecronier.pokebattle.provider.PokeBadgeProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;

/**
 * PokeBadge Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PokeBadgeProviderUtilsBase
            extends ProviderUtils<PokeBadge> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PokeBadgeProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PokeBadgeProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final PokeBadge item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PokeBadgeContract.itemToContentValues(item);
        itemValues.remove(PokeBadgeContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokeBadgeProviderAdapter.POKEBADGE_URI)
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
     * Insert into DB.
     * @param item PokeBadge to insert
     * @param pokeNpcbadgeInternalId pokeNpcbadgeInternal Id
     * @return number of rows affected
     */
    public Uri insert(final PokeBadge item,
                             final int pokeNpcbadgeInternalId) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();

        ContentValues itemValues = PokeBadgeContract.itemToContentValues(item,
                    pokeNpcbadgeInternalId);
        itemValues.remove(PokeBadgeContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokeBadgeProviderAdapter.POKEBADGE_URI)
                    .withValues(itemValues)
                    .build());



        try {
            ContentProviderResult[] results =
                prov.applyBatch(PokebattleProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setId(Integer.parseInt(result.getLastPathSegment()));
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
     * @param item PokeBadge
     * @return number of row affected
     */
    public int delete(final PokeBadge item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PokeBadgeProviderAdapter.POKEBADGE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return PokeBadge
     */
    public PokeBadge query(final PokeBadge item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return PokeBadge
     */
    public PokeBadge query(final int id) {
        PokeBadge result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PokeBadgeContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            PokeBadgeProviderAdapter.POKEBADGE_URI,
            PokeBadgeContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PokeBadgeContract.cursorToItem(cursor);

        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<PokeBadge>
     */
    public ArrayList<PokeBadge> queryAll() {
        ArrayList<PokeBadge> result =
                    new ArrayList<PokeBadge>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeBadgeProviderAdapter.POKEBADGE_URI,
                PokeBadgeContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PokeBadgeContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<PokeBadge>
     */
    public ArrayList<PokeBadge> query(CriteriaExpression expression) {
        ArrayList<PokeBadge> result =
                    new ArrayList<PokeBadge>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeBadgeProviderAdapter.POKEBADGE_URI,
                PokeBadgeContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PokeBadgeContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item PokeBadge
     * @return number of rows updated
     */
    public int update(final PokeBadge item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokeBadgeContract.itemToContentValues(item);

        Uri uri = PokeBadgeProviderAdapter.POKEBADGE_URI;
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

    /**
     * Updates the DB.
     * @param item PokeBadge
     * @param pokeNpcbadgeInternalId pokeNpcbadgeInternal Id
     * @return number of rows updated
     */
    public int update(final PokeBadge item,
                             final int pokeNpcbadgeInternalId) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokeBadgeContract.itemToContentValues(
                item,
                pokeNpcbadgeInternalId);

        Uri uri = PokeBadgeProviderAdapter.POKEBADGE_URI;
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
}
