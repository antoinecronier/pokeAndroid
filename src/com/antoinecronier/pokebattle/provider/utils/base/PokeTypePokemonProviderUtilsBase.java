/**************************************************************************
 * PokeTypePokemonProviderUtilsBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokeTypePokemon;
import com.antoinecronier.pokebattle.entity.PokeType;
import com.antoinecronier.pokebattle.entity.PokeZone;

import com.antoinecronier.pokebattle.provider.PokeTypePokemonProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokeTypeProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokeZoneProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;

/**
 * PokeTypePokemon Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PokeTypePokemonProviderUtilsBase
            extends ProviderUtils<PokeTypePokemon> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PokeTypePokemonProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PokeTypePokemonProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final PokeTypePokemon item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PokeTypePokemonContract.itemToContentValues(item);
        itemValues.remove(PokeTypePokemonContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokeTypePokemonProviderAdapter.POKETYPEPOKEMON_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getTypes() != null && item.getTypes().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokeTypeContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getTypes().size(); i++) {
                inValue.addValue(String.valueOf(item.getTypes().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokeTypeProviderAdapter.POKETYPE_URI)
                    .withValueBackReference(
                            PokeTypeContract
                                    .COL_POKETYPEPOKEMONTYPESINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getZones() != null && item.getZones().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokeZoneContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getZones().size(); i++) {
                inValue.addValue(String.valueOf(item.getZones().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokeZoneProviderAdapter.POKEZONE_URI)
                    .withValueBackReference(
                            PokeZoneContract
                                    .COL_POKETYPEPOKEMONZONESINTERNAL_ID,
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
     * @param item PokeTypePokemon
     * @return number of row affected
     */
    public int delete(final PokeTypePokemon item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PokeTypePokemonProviderAdapter.POKETYPEPOKEMON_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return PokeTypePokemon
     */
    public PokeTypePokemon query(final PokeTypePokemon item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return PokeTypePokemon
     */
    public PokeTypePokemon query(final int id) {
        PokeTypePokemon result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PokeTypePokemonContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            PokeTypePokemonProviderAdapter.POKETYPEPOKEMON_URI,
            PokeTypePokemonContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PokeTypePokemonContract.cursorToItem(cursor);

            if (result.getEvolue() != null) {
                result.setEvolue(
                    this.getAssociateEvolue(result));
            }
            result.setTypes(
                this.getAssociateTypes(result));
            result.setZones(
                this.getAssociateZones(result));
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<PokeTypePokemon>
     */
    public ArrayList<PokeTypePokemon> queryAll() {
        ArrayList<PokeTypePokemon> result =
                    new ArrayList<PokeTypePokemon>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeTypePokemonProviderAdapter.POKETYPEPOKEMON_URI,
                PokeTypePokemonContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PokeTypePokemonContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<PokeTypePokemon>
     */
    public ArrayList<PokeTypePokemon> query(CriteriaExpression expression) {
        ArrayList<PokeTypePokemon> result =
                    new ArrayList<PokeTypePokemon>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeTypePokemonProviderAdapter.POKETYPEPOKEMON_URI,
                PokeTypePokemonContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PokeTypePokemonContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item PokeTypePokemon
     
     * @return number of rows updated
     */
    public int update(final PokeTypePokemon item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokeTypePokemonContract.itemToContentValues(
                item);

        Uri uri = PokeTypePokemonProviderAdapter.POKETYPEPOKEMON_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getTypes() != null && item.getTypes().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new types for PokeTypePokemon
            CriteriaExpression typesCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokeTypeContract.COL_ID);
            crit.addValue(values);
            typesCrit.add(crit);


            for (PokeType types : item.getTypes()) {
                values.addValue(
                    String.valueOf(types.getId()));
            }
            selection = typesCrit.toSQLiteSelection();
            selectionArgs = typesCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokeTypeProviderAdapter.POKETYPE_URI)
                    .withValue(
                            PokeTypeContract.COL_POKETYPEPOKEMONTYPESINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated types
            crit.setType(Type.NOT_IN);
            typesCrit.add(PokeTypeContract.COL_POKETYPEPOKEMONTYPESINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokeTypeProviderAdapter.POKETYPE_URI)
                    .withValue(
                            PokeTypeContract.COL_POKETYPEPOKEMONTYPESINTERNAL_ID,
                            null)
                    .withSelection(
                            typesCrit.toSQLiteSelection(),
                            typesCrit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getZones() != null && item.getZones().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new zones for PokeTypePokemon
            CriteriaExpression zonesCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokeZoneContract.COL_ID);
            crit.addValue(values);
            zonesCrit.add(crit);


            for (PokeZone zones : item.getZones()) {
                values.addValue(
                    String.valueOf(zones.getId()));
            }
            selection = zonesCrit.toSQLiteSelection();
            selectionArgs = zonesCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokeZoneProviderAdapter.POKEZONE_URI)
                    .withValue(
                            PokeZoneContract.COL_POKETYPEPOKEMONZONESINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated zones
            crit.setType(Type.NOT_IN);
            zonesCrit.add(PokeZoneContract.COL_POKETYPEPOKEMONZONESINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokeZoneProviderAdapter.POKEZONE_URI)
                    .withValue(
                            PokeZoneContract.COL_POKETYPEPOKEMONZONESINTERNAL_ID,
                            null)
                    .withSelection(
                            zonesCrit.toSQLiteSelection(),
                            zonesCrit.toSQLiteSelectionArgs())
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
     * Get associate Evolue.
     * @param item PokeTypePokemon
     * @return PokeTypePokemon
     */
    public PokeTypePokemon getAssociateEvolue(
            final PokeTypePokemon item) {
        PokeTypePokemon result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeTypePokemonCursor = prov.query(
                PokeTypePokemonProviderAdapter.POKETYPEPOKEMON_URI,
                PokeTypePokemonContract.ALIASED_COLS,
                PokeTypePokemonContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getEvolue().getId())},
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
     * Get associate Types.
     * @param item PokeTypePokemon
     * @return PokeType
     */
    public ArrayList<PokeType> getAssociateTypes(
            final PokeTypePokemon item) {
        ArrayList<PokeType> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeTypeCursor = prov.query(
                PokeTypeProviderAdapter.POKETYPE_URI,
                PokeTypeContract.ALIASED_COLS,
                PokeTypeContract.ALIASED_COL_POKETYPEPOKEMONTYPESINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = PokeTypeContract.cursorToItems(
                        pokeTypeCursor);
        pokeTypeCursor.close();

        return result;
    }

    /**
     * Get associate Zones.
     * @param item PokeTypePokemon
     * @return PokeZone
     */
    public ArrayList<PokeZone> getAssociateZones(
            final PokeTypePokemon item) {
        ArrayList<PokeZone> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeZoneCursor = prov.query(
                PokeZoneProviderAdapter.POKEZONE_URI,
                PokeZoneContract.ALIASED_COLS,
                PokeZoneContract.ALIASED_COL_POKETYPEPOKEMONZONESINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = PokeZoneContract.cursorToItems(
                        pokeZoneCursor);
        pokeZoneCursor.close();

        return result;
    }

}
