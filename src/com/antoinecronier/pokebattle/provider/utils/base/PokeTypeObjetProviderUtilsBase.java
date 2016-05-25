/**************************************************************************
 * PokeTypeObjetProviderUtilsBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokeTypeObjet;

import com.antoinecronier.pokebattle.provider.PokeTypeObjetProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeObjetContract;

/**
 * PokeTypeObjet Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PokeTypeObjetProviderUtilsBase
            extends ProviderUtils<PokeTypeObjet> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PokeTypeObjetProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PokeTypeObjetProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final PokeTypeObjet item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PokeTypeObjetContract.itemToContentValues(item);
        itemValues.remove(PokeTypeObjetContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokeTypeObjetProviderAdapter.POKETYPEOBJET_URI)
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
     * @param item PokeTypeObjet
     * @return number of row affected
     */
    public int delete(final PokeTypeObjet item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PokeTypeObjetProviderAdapter.POKETYPEOBJET_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return PokeTypeObjet
     */
    public PokeTypeObjet query(final PokeTypeObjet item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return PokeTypeObjet
     */
    public PokeTypeObjet query(final int id) {
        PokeTypeObjet result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PokeTypeObjetContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            PokeTypeObjetProviderAdapter.POKETYPEOBJET_URI,
            PokeTypeObjetContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PokeTypeObjetContract.cursorToItem(cursor);

        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<PokeTypeObjet>
     */
    public ArrayList<PokeTypeObjet> queryAll() {
        ArrayList<PokeTypeObjet> result =
                    new ArrayList<PokeTypeObjet>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeTypeObjetProviderAdapter.POKETYPEOBJET_URI,
                PokeTypeObjetContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PokeTypeObjetContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<PokeTypeObjet>
     */
    public ArrayList<PokeTypeObjet> query(CriteriaExpression expression) {
        ArrayList<PokeTypeObjet> result =
                    new ArrayList<PokeTypeObjet>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeTypeObjetProviderAdapter.POKETYPEOBJET_URI,
                PokeTypeObjetContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PokeTypeObjetContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item PokeTypeObjet
     
     * @return number of rows updated
     */
    public int update(final PokeTypeObjet item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokeTypeObjetContract.itemToContentValues(
                item);

        Uri uri = PokeTypeObjetProviderAdapter.POKETYPEOBJET_URI;
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

    
}
