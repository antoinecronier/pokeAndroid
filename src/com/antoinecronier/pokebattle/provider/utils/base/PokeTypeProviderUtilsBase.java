/**************************************************************************
 * PokeTypeProviderUtilsBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokeType;

import com.antoinecronier.pokebattle.provider.PokeTypeProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;

/**
 * PokeType Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PokeTypeProviderUtilsBase
            extends ProviderUtils<PokeType> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PokeTypeProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PokeTypeProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final PokeType item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PokeTypeContract.itemToContentValues(item);
        itemValues.remove(PokeTypeContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokeTypeProviderAdapter.POKETYPE_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getTypeFort() != null && item.getTypeFort().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokeTypeContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getTypeFort().size(); i++) {
                inValue.addValue(String.valueOf(item.getTypeFort().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokeTypeProviderAdapter.POKETYPE_URI)
                    .withValueBackReference(
                            PokeTypeContract
                                    .COL_POKETYPETYPEFORTINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getTypeFaible() != null && item.getTypeFaible().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokeTypeContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getTypeFaible().size(); i++) {
                inValue.addValue(String.valueOf(item.getTypeFaible().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokeTypeProviderAdapter.POKETYPE_URI)
                    .withValueBackReference(
                            PokeTypeContract
                                    .COL_POKETYPETYPEFAIBLEINTERNAL_ID,
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
     * Insert into DB.
     * @param item PokeType to insert
     * @param pokeTypetypeFortInternalId pokeTypetypeFortInternal Id* @param pokeTypetypeFaibleInternalId pokeTypetypeFaibleInternal Id* @param pokeTypePokemontypesInternalId pokeTypePokemontypesInternal Id
     * @return number of rows affected
     */
    public Uri insert(final PokeType item,
                             final int pokeTypetypeFortInternalId,
                             final int pokeTypetypeFaibleInternalId,
                             final int pokeTypePokemontypesInternalId) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();

        ContentValues itemValues = PokeTypeContract.itemToContentValues(item,
                    pokeTypetypeFortInternalId,
                    pokeTypetypeFaibleInternalId,
                    pokeTypePokemontypesInternalId);
        itemValues.remove(PokeTypeContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokeTypeProviderAdapter.POKETYPE_URI)
                    .withValues(itemValues)
                    .build());


        if (item.getTypeFort() != null && item.getTypeFort().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokeTypeContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getTypeFort().size(); i++) {
                inValue.addValue(String.valueOf(item.getTypeFort().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokeTypeProviderAdapter.POKETYPE_URI)
                    .withValueBackReference(
                            PokeTypeContract
                                    .COL_POKETYPETYPEFORTINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getTypeFaible() != null && item.getTypeFaible().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokeTypeContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getTypeFaible().size(); i++) {
                inValue.addValue(String.valueOf(item.getTypeFaible().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokeTypeProviderAdapter.POKETYPE_URI)
                    .withValueBackReference(
                            PokeTypeContract
                                    .COL_POKETYPETYPEFAIBLEINTERNAL_ID,
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
     * @param item PokeType
     * @return number of row affected
     */
    public int delete(final PokeType item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PokeTypeProviderAdapter.POKETYPE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return PokeType
     */
    public PokeType query(final PokeType item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return PokeType
     */
    public PokeType query(final int id) {
        PokeType result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PokeTypeContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            PokeTypeProviderAdapter.POKETYPE_URI,
            PokeTypeContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PokeTypeContract.cursorToItem(cursor);

            result.setTypeFort(
                this.getAssociateTypeFort(result));
            result.setTypeFaible(
                this.getAssociateTypeFaible(result));
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<PokeType>
     */
    public ArrayList<PokeType> queryAll() {
        ArrayList<PokeType> result =
                    new ArrayList<PokeType>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeTypeProviderAdapter.POKETYPE_URI,
                PokeTypeContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PokeTypeContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<PokeType>
     */
    public ArrayList<PokeType> query(CriteriaExpression expression) {
        ArrayList<PokeType> result =
                    new ArrayList<PokeType>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeTypeProviderAdapter.POKETYPE_URI,
                PokeTypeContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PokeTypeContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item PokeType
     * @return number of rows updated
     */
    public int update(final PokeType item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokeTypeContract.itemToContentValues(item);

        Uri uri = PokeTypeProviderAdapter.POKETYPE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getTypeFort() != null && item.getTypeFort().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new typeFort for PokeType
            CriteriaExpression typeFortCrit = 
                    new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokeTypeContract.COL_ID);
            crit.addValue(values);
            typeFortCrit.add(crit);


            for (PokeType typeFort : item.getTypeFort()) {
                values.addValue(
                    String.valueOf(typeFort.getId()));
            }
            selection = typeFortCrit.toSQLiteSelection();
            selectionArgs = typeFortCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokeTypeProviderAdapter.POKETYPE_URI)
                    .withValue(
                            PokeTypeContract.COL_POKETYPETYPEFORTINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated typeFort
            crit.setType(Type.NOT_IN);
            typeFortCrit.add(PokeTypeContract.COL_POKETYPETYPEFORTINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokeTypeProviderAdapter.POKETYPE_URI)
                    .withValue(
                            PokeTypeContract.COL_POKETYPETYPEFORTINTERNAL_ID,
                            null)
                    .withSelection(
                            typeFortCrit.toSQLiteSelection(),
                            typeFortCrit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getTypeFaible() != null && item.getTypeFaible().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new typeFaible for PokeType
            CriteriaExpression typeFaibleCrit = 
                    new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokeTypeContract.COL_ID);
            crit.addValue(values);
            typeFaibleCrit.add(crit);


            for (PokeType typeFaible : item.getTypeFaible()) {
                values.addValue(
                    String.valueOf(typeFaible.getId()));
            }
            selection = typeFaibleCrit.toSQLiteSelection();
            selectionArgs = typeFaibleCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokeTypeProviderAdapter.POKETYPE_URI)
                    .withValue(
                            PokeTypeContract.COL_POKETYPETYPEFAIBLEINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated typeFaible
            crit.setType(Type.NOT_IN);
            typeFaibleCrit.add(PokeTypeContract.COL_POKETYPETYPEFAIBLEINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokeTypeProviderAdapter.POKETYPE_URI)
                    .withValue(
                            PokeTypeContract.COL_POKETYPETYPEFAIBLEINTERNAL_ID,
                            null)
                    .withSelection(
                            typeFaibleCrit.toSQLiteSelection(),
                            typeFaibleCrit.toSQLiteSelectionArgs())
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

    /**
     * Updates the DB.
     * @param item PokeType
     * @param pokeTypetypeFortInternalId pokeTypetypeFortInternal Id* @param pokeTypetypeFaibleInternalId pokeTypetypeFaibleInternal Id* @param pokeTypePokemontypesInternalId pokeTypePokemontypesInternal Id
     * @return number of rows updated
     */
    public int update(final PokeType item,
                             final int pokeTypetypeFortInternalId,
                             final int pokeTypetypeFaibleInternalId,
                             final int pokeTypePokemontypesInternalId) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokeTypeContract.itemToContentValues(
                item,
                pokeTypetypeFortInternalId,
                pokeTypetypeFaibleInternalId,
                pokeTypePokemontypesInternalId);

        Uri uri = PokeTypeProviderAdapter.POKETYPE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getTypeFort() != null && item.getTypeFort().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new typeFort for PokeType
            CriteriaExpression typeFortCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokeTypeContract.COL_ID);
            crit.addValue(values);
            typeFortCrit.add(crit);


            for (PokeType typeFort : item.getTypeFort()) {
                values.addValue(
                    String.valueOf(typeFort.getId()));
            }
            selection = typeFortCrit.toSQLiteSelection();
            selectionArgs = typeFortCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokeTypeProviderAdapter.POKETYPE_URI)
                    .withValue(
                            PokeTypeContract.COL_POKETYPETYPEFORTINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated typeFort
            crit.setType(Type.NOT_IN);
            typeFortCrit.add(PokeTypeContract.COL_POKETYPETYPEFORTINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokeTypeProviderAdapter.POKETYPE_URI)
                    .withValue(
                            PokeTypeContract.COL_POKETYPETYPEFORTINTERNAL_ID,
                            null)
                    .withSelection(
                            typeFortCrit.toSQLiteSelection(),
                            typeFortCrit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getTypeFaible() != null && item.getTypeFaible().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new typeFaible for PokeType
            CriteriaExpression typeFaibleCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokeTypeContract.COL_ID);
            crit.addValue(values);
            typeFaibleCrit.add(crit);


            for (PokeType typeFaible : item.getTypeFaible()) {
                values.addValue(
                    String.valueOf(typeFaible.getId()));
            }
            selection = typeFaibleCrit.toSQLiteSelection();
            selectionArgs = typeFaibleCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokeTypeProviderAdapter.POKETYPE_URI)
                    .withValue(
                            PokeTypeContract.COL_POKETYPETYPEFAIBLEINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated typeFaible
            crit.setType(Type.NOT_IN);
            typeFaibleCrit.add(PokeTypeContract.COL_POKETYPETYPEFAIBLEINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokeTypeProviderAdapter.POKETYPE_URI)
                    .withValue(
                            PokeTypeContract.COL_POKETYPETYPEFAIBLEINTERNAL_ID,
                            null)
                    .withSelection(
                            typeFaibleCrit.toSQLiteSelection(),
                            typeFaibleCrit.toSQLiteSelectionArgs())
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
     * Get associate TypeFort.
     * @param item PokeType
     * @return PokeType
     */
    public ArrayList<PokeType> getAssociateTypeFort(
            final PokeType item) {
        ArrayList<PokeType> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeTypeCursor = prov.query(
                PokeTypeProviderAdapter.POKETYPE_URI,
                PokeTypeContract.ALIASED_COLS,
                PokeTypeContract.ALIASED_COL_POKETYPETYPEFORTINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = PokeTypeContract.cursorToItems(
                        pokeTypeCursor);
        pokeTypeCursor.close();

        return result;
    }

    /**
     * Get associate TypeFaible.
     * @param item PokeType
     * @return PokeType
     */
    public ArrayList<PokeType> getAssociateTypeFaible(
            final PokeType item) {
        ArrayList<PokeType> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeTypeCursor = prov.query(
                PokeTypeProviderAdapter.POKETYPE_URI,
                PokeTypeContract.ALIASED_COLS,
                PokeTypeContract.ALIASED_COL_POKETYPETYPEFAIBLEINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = PokeTypeContract.cursorToItems(
                        pokeTypeCursor);
        pokeTypeCursor.close();

        return result;
    }

}
