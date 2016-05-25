/**************************************************************************
 * PokeZoneProviderUtilsBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokeZone;

import com.antoinecronier.pokebattle.provider.PokeZoneProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;

/**
 * PokeZone Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PokeZoneProviderUtilsBase
            extends ProviderUtils<PokeZone> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PokeZoneProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PokeZoneProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final PokeZone item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PokeZoneContract.itemToContentValues(item);
        itemValues.remove(PokeZoneContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokeZoneProviderAdapter.POKEZONE_URI)
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
     * @param item PokeZone to insert
     * @param pokeTypePokemonzonesInternalId pokeTypePokemonzonesInternal Id
     * @return number of rows affected
     */
    public Uri insert(final PokeZone item,
                             final int pokeTypePokemonzonesInternalId) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();

        ContentValues itemValues = PokeZoneContract.itemToContentValues(item,
                    pokeTypePokemonzonesInternalId);
        itemValues.remove(PokeZoneContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokeZoneProviderAdapter.POKEZONE_URI)
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
     * @param item PokeZone
     * @return number of row affected
     */
    public int delete(final PokeZone item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PokeZoneProviderAdapter.POKEZONE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return PokeZone
     */
    public PokeZone query(final PokeZone item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return PokeZone
     */
    public PokeZone query(final int id) {
        PokeZone result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PokeZoneContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            PokeZoneProviderAdapter.POKEZONE_URI,
            PokeZoneContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PokeZoneContract.cursorToItem(cursor);

        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<PokeZone>
     */
    public ArrayList<PokeZone> queryAll() {
        ArrayList<PokeZone> result =
                    new ArrayList<PokeZone>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeZoneProviderAdapter.POKEZONE_URI,
                PokeZoneContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PokeZoneContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<PokeZone>
     */
    public ArrayList<PokeZone> query(CriteriaExpression expression) {
        ArrayList<PokeZone> result =
                    new ArrayList<PokeZone>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeZoneProviderAdapter.POKEZONE_URI,
                PokeZoneContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PokeZoneContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item PokeZone
     * @return number of rows updated
     */
    public int update(final PokeZone item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokeZoneContract.itemToContentValues(item);

        Uri uri = PokeZoneProviderAdapter.POKEZONE_URI;
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
     * @param item PokeZone
     * @param pokeTypePokemonzonesInternalId pokeTypePokemonzonesInternal Id
     * @return number of rows updated
     */
    public int update(final PokeZone item,
                             final int pokeTypePokemonzonesInternalId) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokeZoneContract.itemToContentValues(
                item,
                pokeTypePokemonzonesInternalId);

        Uri uri = PokeZoneProviderAdapter.POKEZONE_URI;
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
