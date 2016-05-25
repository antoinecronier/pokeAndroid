/**************************************************************************
 * PokePokemonProviderUtilsBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokePokemon;
import com.antoinecronier.pokebattle.entity.PokeTypePokemon;
import com.antoinecronier.pokebattle.entity.PokeAttaque;

import com.antoinecronier.pokebattle.provider.PokePokemonProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokeTypePokemonProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokeAttaqueProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokeAttaqueContract;

/**
 * PokePokemon Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PokePokemonProviderUtilsBase
            extends ProviderUtils<PokePokemon> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PokePokemonProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PokePokemonProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final PokePokemon item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PokePokemonContract.itemToContentValues(item);
        itemValues.remove(PokePokemonContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokePokemonProviderAdapter.POKEPOKEMON_URI)
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
     * @param item PokePokemon to insert
     * @param pokeNpcpokemonsInternalId pokeNpcpokemonsInternal Id* @param pokeNpcteamInternalId pokeNpcteamInternal Id
     * @return number of rows affected
     */
    public Uri insert(final PokePokemon item,
                             final int pokeNpcpokemonsInternalId,
                             final int pokeNpcteamInternalId) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();

        ContentValues itemValues = PokePokemonContract.itemToContentValues(item,
                    pokeNpcpokemonsInternalId,
                    pokeNpcteamInternalId);
        itemValues.remove(PokePokemonContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokePokemonProviderAdapter.POKEPOKEMON_URI)
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
     * @param item PokePokemon
     * @return number of row affected
     */
    public int delete(final PokePokemon item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PokePokemonProviderAdapter.POKEPOKEMON_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return PokePokemon
     */
    public PokePokemon query(final PokePokemon item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return PokePokemon
     */
    public PokePokemon query(final int id) {
        PokePokemon result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PokePokemonContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            PokePokemonProviderAdapter.POKEPOKEMON_URI,
            PokePokemonContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PokePokemonContract.cursorToItem(cursor);

            if (result.getType() != null) {
                result.setType(
                    this.getAssociateType(result));
            }
            if (result.getAttaque1() != null) {
                result.setAttaque1(
                    this.getAssociateAttaque1(result));
            }
            if (result.getAttaque2() != null) {
                result.setAttaque2(
                    this.getAssociateAttaque2(result));
            }
            if (result.getAttaque3() != null) {
                result.setAttaque3(
                    this.getAssociateAttaque3(result));
            }
            if (result.getAttaque4() != null) {
                result.setAttaque4(
                    this.getAssociateAttaque4(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<PokePokemon>
     */
    public ArrayList<PokePokemon> queryAll() {
        ArrayList<PokePokemon> result =
                    new ArrayList<PokePokemon>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokePokemonProviderAdapter.POKEPOKEMON_URI,
                PokePokemonContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PokePokemonContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<PokePokemon>
     */
    public ArrayList<PokePokemon> query(CriteriaExpression expression) {
        ArrayList<PokePokemon> result =
                    new ArrayList<PokePokemon>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokePokemonProviderAdapter.POKEPOKEMON_URI,
                PokePokemonContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PokePokemonContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item PokePokemon
     * @return number of rows updated
     */
    public int update(final PokePokemon item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokePokemonContract.itemToContentValues(item);

        Uri uri = PokePokemonProviderAdapter.POKEPOKEMON_URI;
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
     * @param item PokePokemon
     * @param pokeNpcpokemonsInternalId pokeNpcpokemonsInternal Id* @param pokeNpcteamInternalId pokeNpcteamInternal Id
     * @return number of rows updated
     */
    public int update(final PokePokemon item,
                             final int pokeNpcpokemonsInternalId,
                             final int pokeNpcteamInternalId) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokePokemonContract.itemToContentValues(
                item,
                pokeNpcpokemonsInternalId,
                pokeNpcteamInternalId);

        Uri uri = PokePokemonProviderAdapter.POKEPOKEMON_URI;
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
     * @param item PokePokemon
     * @return PokeTypePokemon
     */
    public PokeTypePokemon getAssociateType(
            final PokePokemon item) {
        PokeTypePokemon result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeTypePokemonCursor = prov.query(
                PokeTypePokemonProviderAdapter.POKETYPEPOKEMON_URI,
                PokeTypePokemonContract.ALIASED_COLS,
                PokeTypePokemonContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getType().getId())},
                null);

        if (pokeTypePokemonCursor.getCount() > 0) {
            pokeTypePokemonCursor.moveToFirst();
            result = PokeTypePokemonContract.cursorToItem(pokeTypePokemonCursor);
        } else {
            result = null;
        }
        pokeTypePokemonCursor.close();

        return result;
    }

    /**
     * Get associate Attaque1.
     * @param item PokePokemon
     * @return PokeAttaque
     */
    public PokeAttaque getAssociateAttaque1(
            final PokePokemon item) {
        PokeAttaque result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeAttaqueCursor = prov.query(
                PokeAttaqueProviderAdapter.POKEATTAQUE_URI,
                PokeAttaqueContract.ALIASED_COLS,
                PokeAttaqueContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getAttaque1().getId())},
                null);

        if (pokeAttaqueCursor.getCount() > 0) {
            pokeAttaqueCursor.moveToFirst();
            result = PokeAttaqueContract.cursorToItem(pokeAttaqueCursor);
        } else {
            result = null;
        }
        pokeAttaqueCursor.close();

        return result;
    }

    /**
     * Get associate Attaque2.
     * @param item PokePokemon
     * @return PokeAttaque
     */
    public PokeAttaque getAssociateAttaque2(
            final PokePokemon item) {
        PokeAttaque result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeAttaqueCursor = prov.query(
                PokeAttaqueProviderAdapter.POKEATTAQUE_URI,
                PokeAttaqueContract.ALIASED_COLS,
                PokeAttaqueContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getAttaque2().getId())},
                null);

        if (pokeAttaqueCursor.getCount() > 0) {
            pokeAttaqueCursor.moveToFirst();
            result = PokeAttaqueContract.cursorToItem(pokeAttaqueCursor);
        } else {
            result = null;
        }
        pokeAttaqueCursor.close();

        return result;
    }

    /**
     * Get associate Attaque3.
     * @param item PokePokemon
     * @return PokeAttaque
     */
    public PokeAttaque getAssociateAttaque3(
            final PokePokemon item) {
        PokeAttaque result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeAttaqueCursor = prov.query(
                PokeAttaqueProviderAdapter.POKEATTAQUE_URI,
                PokeAttaqueContract.ALIASED_COLS,
                PokeAttaqueContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getAttaque3().getId())},
                null);

        if (pokeAttaqueCursor.getCount() > 0) {
            pokeAttaqueCursor.moveToFirst();
            result = PokeAttaqueContract.cursorToItem(pokeAttaqueCursor);
        } else {
            result = null;
        }
        pokeAttaqueCursor.close();

        return result;
    }

    /**
     * Get associate Attaque4.
     * @param item PokePokemon
     * @return PokeAttaque
     */
    public PokeAttaque getAssociateAttaque4(
            final PokePokemon item) {
        PokeAttaque result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeAttaqueCursor = prov.query(
                PokeAttaqueProviderAdapter.POKEATTAQUE_URI,
                PokeAttaqueContract.ALIASED_COLS,
                PokeAttaqueContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getAttaque4().getId())},
                null);

        if (pokeAttaqueCursor.getCount() > 0) {
            pokeAttaqueCursor.moveToFirst();
            result = PokeAttaqueContract.cursorToItem(pokeAttaqueCursor);
        } else {
            result = null;
        }
        pokeAttaqueCursor.close();

        return result;
    }

}
