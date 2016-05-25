/**************************************************************************
 * PokeObjetProviderUtilsBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokeObjet;
import com.antoinecronier.pokebattle.entity.PokeTypeObjet;

import com.antoinecronier.pokebattle.provider.PokeObjetProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokeTypeObjetProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeObjetContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeObjetContract;

/**
 * PokeObjet Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PokeObjetProviderUtilsBase
            extends ProviderUtils<PokeObjet> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PokeObjetProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PokeObjetProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final PokeObjet item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PokeObjetContract.itemToContentValues(item);
        itemValues.remove(PokeObjetContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokeObjetProviderAdapter.POKEOBJET_URI)
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
     * @param item PokeObjet to insert
     * @param pokeNpcobjetsInternalId pokeNpcobjetsInternal Id
     * @return number of rows affected
     */
    public Uri insert(final PokeObjet item,
                             final int pokeNpcobjetsInternalId) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();

        ContentValues itemValues = PokeObjetContract.itemToContentValues(item,
                    pokeNpcobjetsInternalId);
        itemValues.remove(PokeObjetContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokeObjetProviderAdapter.POKEOBJET_URI)
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
     * @param item PokeObjet
     * @return number of row affected
     */
    public int delete(final PokeObjet item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PokeObjetProviderAdapter.POKEOBJET_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return PokeObjet
     */
    public PokeObjet query(final PokeObjet item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return PokeObjet
     */
    public PokeObjet query(final int id) {
        PokeObjet result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PokeObjetContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            PokeObjetProviderAdapter.POKEOBJET_URI,
            PokeObjetContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PokeObjetContract.cursorToItem(cursor);

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
     * @return ArrayList<PokeObjet>
     */
    public ArrayList<PokeObjet> queryAll() {
        ArrayList<PokeObjet> result =
                    new ArrayList<PokeObjet>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeObjetProviderAdapter.POKEOBJET_URI,
                PokeObjetContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PokeObjetContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<PokeObjet>
     */
    public ArrayList<PokeObjet> query(CriteriaExpression expression) {
        ArrayList<PokeObjet> result =
                    new ArrayList<PokeObjet>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeObjetProviderAdapter.POKEOBJET_URI,
                PokeObjetContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PokeObjetContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item PokeObjet
     * @return number of rows updated
     */
    public int update(final PokeObjet item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokeObjetContract.itemToContentValues(item);

        Uri uri = PokeObjetProviderAdapter.POKEOBJET_URI;
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
     * @param item PokeObjet
     * @param pokeNpcobjetsInternalId pokeNpcobjetsInternal Id
     * @return number of rows updated
     */
    public int update(final PokeObjet item,
                             final int pokeNpcobjetsInternalId) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokeObjetContract.itemToContentValues(
                item,
                pokeNpcobjetsInternalId);

        Uri uri = PokeObjetProviderAdapter.POKEOBJET_URI;
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
     * @param item PokeObjet
     * @return PokeTypeObjet
     */
    public PokeTypeObjet getAssociateType(
            final PokeObjet item) {
        PokeTypeObjet result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeTypeObjetCursor = prov.query(
                PokeTypeObjetProviderAdapter.POKETYPEOBJET_URI,
                PokeTypeObjetContract.ALIASED_COLS,
                PokeTypeObjetContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getType().getId())},
                null);

        if (pokeTypeObjetCursor.getCount() > 0) {
            pokeTypeObjetCursor.moveToFirst();
            result = PokeTypeObjetContract.cursorToItem(pokeTypeObjetCursor);
        } else {
            result = null;
        }
        pokeTypeObjetCursor.close();

        return result;
    }

}
