/**************************************************************************
 * PokeDresseurProviderUtilsBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokeDresseur;
import com.antoinecronier.pokebattle.entity.PokeNpc;

import com.antoinecronier.pokebattle.provider.PokeDresseurProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokeNpcProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeDresseurContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;

/**
 * PokeDresseur Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PokeDresseurProviderUtilsBase
            extends ProviderUtils<PokeDresseur> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PokeDresseurProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PokeDresseurProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final PokeDresseur item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PokeDresseurContract.itemToContentValues(item);
        itemValues.remove(PokeDresseurContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokeDresseurProviderAdapter.POKEDRESSEUR_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getNpcs() != null && item.getNpcs().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokeNpcContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getNpcs().size(); i++) {
                inValue.addValue(String.valueOf(item.getNpcs().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokeNpcProviderAdapter.POKENPC_URI)
                    .withValueBackReference(
                            PokeNpcContract
                                    .COL_POKEDRESSEURNPCSINTERNAL_ID,
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
     * @param item PokeDresseur
     * @return number of row affected
     */
    public int delete(final PokeDresseur item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PokeDresseurProviderAdapter.POKEDRESSEUR_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return PokeDresseur
     */
    public PokeDresseur query(final PokeDresseur item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return PokeDresseur
     */
    public PokeDresseur query(final int id) {
        PokeDresseur result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PokeDresseurContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            PokeDresseurProviderAdapter.POKEDRESSEUR_URI,
            PokeDresseurContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PokeDresseurContract.cursorToItem(cursor);

            result.setNpcs(
                this.getAssociateNpcs(result));
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<PokeDresseur>
     */
    public ArrayList<PokeDresseur> queryAll() {
        ArrayList<PokeDresseur> result =
                    new ArrayList<PokeDresseur>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeDresseurProviderAdapter.POKEDRESSEUR_URI,
                PokeDresseurContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PokeDresseurContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<PokeDresseur>
     */
    public ArrayList<PokeDresseur> query(CriteriaExpression expression) {
        ArrayList<PokeDresseur> result =
                    new ArrayList<PokeDresseur>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeDresseurProviderAdapter.POKEDRESSEUR_URI,
                PokeDresseurContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PokeDresseurContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item PokeDresseur
     
     * @return number of rows updated
     */
    public int update(final PokeDresseur item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokeDresseurContract.itemToContentValues(
                item);

        Uri uri = PokeDresseurProviderAdapter.POKEDRESSEUR_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getNpcs() != null && item.getNpcs().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new npcs for PokeDresseur
            CriteriaExpression npcsCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokeNpcContract.COL_ID);
            crit.addValue(values);
            npcsCrit.add(crit);


            for (PokeNpc npcs : item.getNpcs()) {
                values.addValue(
                    String.valueOf(npcs.getId()));
            }
            selection = npcsCrit.toSQLiteSelection();
            selectionArgs = npcsCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokeNpcProviderAdapter.POKENPC_URI)
                    .withValue(
                            PokeNpcContract.COL_POKEDRESSEURNPCSINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated npcs
            crit.setType(Type.NOT_IN);
            npcsCrit.add(PokeNpcContract.COL_POKEDRESSEURNPCSINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokeNpcProviderAdapter.POKENPC_URI)
                    .withValue(
                            PokeNpcContract.COL_POKEDRESSEURNPCSINTERNAL_ID,
                            null)
                    .withSelection(
                            npcsCrit.toSQLiteSelection(),
                            npcsCrit.toSQLiteSelectionArgs())
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
     * Get associate Npcs.
     * @param item PokeDresseur
     * @return PokeNpc
     */
    public ArrayList<PokeNpc> getAssociateNpcs(
            final PokeDresseur item) {
        ArrayList<PokeNpc> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeNpcCursor = prov.query(
                PokeNpcProviderAdapter.POKENPC_URI,
                PokeNpcContract.ALIASED_COLS,
                PokeNpcContract.ALIASED_COL_POKEDRESSEURNPCSINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = PokeNpcContract.cursorToItems(
                        pokeNpcCursor);
        pokeNpcCursor.close();

        return result;
    }

}
