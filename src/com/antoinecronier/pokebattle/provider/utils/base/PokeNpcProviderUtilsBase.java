/**************************************************************************
 * PokeNpcProviderUtilsBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.entity.PokeObjet;
import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.entity.PokePokemon;
import com.antoinecronier.pokebattle.entity.PokePosition;
import com.antoinecronier.pokebattle.entity.PokeZone;
import com.antoinecronier.pokebattle.entity.PokeProfession;

import com.antoinecronier.pokebattle.provider.PokeNpcProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokeObjetProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokeBadgeProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokePokemonProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokePositionProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokeZoneProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeAreneContract;
import com.antoinecronier.pokebattle.provider.contract.PokeDresseurContract;
import com.antoinecronier.pokebattle.provider.contract.PokeObjetContract;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;
import com.antoinecronier.pokebattle.provider.contract.PokePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokePositionContract;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;

/**
 * PokeNpc Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PokeNpcProviderUtilsBase
            extends ProviderUtils<PokeNpc> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PokeNpcProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PokeNpcProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final PokeNpc item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PokeNpcContract.itemToContentValues(item);
        itemValues.remove(PokeNpcContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokeNpcProviderAdapter.POKENPC_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getObjets() != null && item.getObjets().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokeObjetContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getObjets().size(); i++) {
                inValue.addValue(String.valueOf(item.getObjets().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokeObjetProviderAdapter.POKEOBJET_URI)
                    .withValueBackReference(
                            PokeObjetContract
                                    .COL_POKENPCOBJETSINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getBadge() != null && item.getBadge().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokeBadgeContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getBadge().size(); i++) {
                inValue.addValue(String.valueOf(item.getBadge().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokeBadgeProviderAdapter.POKEBADGE_URI)
                    .withValueBackReference(
                            PokeBadgeContract
                                    .COL_POKENPCBADGEINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getPokemons() != null && item.getPokemons().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokePokemonContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getPokemons().size(); i++) {
                inValue.addValue(String.valueOf(item.getPokemons().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokePokemonProviderAdapter.POKEPOKEMON_URI)
                    .withValueBackReference(
                            PokePokemonContract
                                    .COL_POKENPCPOKEMONSINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getTeam() != null && item.getTeam().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokePokemonContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getTeam().size(); i++) {
                inValue.addValue(String.valueOf(item.getTeam().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokePokemonProviderAdapter.POKEPOKEMON_URI)
                    .withValueBackReference(
                            PokePokemonContract
                                    .COL_POKENPCTEAMINTERNAL_ID,
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
     * @param item PokeNpc to insert
     * @param pokeArenedresseursInternalId pokeArenedresseursInternal Id* @param pokeDresseurnpcsInternalId pokeDresseurnpcsInternal Id
     * @return number of rows affected
     */
    public Uri insert(final PokeNpc item,
                             final int pokeArenedresseursInternalId,
                             final int pokeDresseurnpcsInternalId) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();

        ContentValues itemValues = PokeNpcContract.itemToContentValues(item,
                    pokeArenedresseursInternalId,
                    pokeDresseurnpcsInternalId);
        itemValues.remove(PokeNpcContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokeNpcProviderAdapter.POKENPC_URI)
                    .withValues(itemValues)
                    .build());


        if (item.getObjets() != null && item.getObjets().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokeObjetContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getObjets().size(); i++) {
                inValue.addValue(String.valueOf(item.getObjets().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokeObjetProviderAdapter.POKEOBJET_URI)
                    .withValueBackReference(
                            PokeObjetContract
                                    .COL_POKENPCOBJETSINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getBadge() != null && item.getBadge().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokeBadgeContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getBadge().size(); i++) {
                inValue.addValue(String.valueOf(item.getBadge().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokeBadgeProviderAdapter.POKEBADGE_URI)
                    .withValueBackReference(
                            PokeBadgeContract
                                    .COL_POKENPCBADGEINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getPokemons() != null && item.getPokemons().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokePokemonContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getPokemons().size(); i++) {
                inValue.addValue(String.valueOf(item.getPokemons().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokePokemonProviderAdapter.POKEPOKEMON_URI)
                    .withValueBackReference(
                            PokePokemonContract
                                    .COL_POKENPCPOKEMONSINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getTeam() != null && item.getTeam().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokePokemonContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getTeam().size(); i++) {
                inValue.addValue(String.valueOf(item.getTeam().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokePokemonProviderAdapter.POKEPOKEMON_URI)
                    .withValueBackReference(
                            PokePokemonContract
                                    .COL_POKENPCTEAMINTERNAL_ID,
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
     * @param item PokeNpc
     * @return number of row affected
     */
    public int delete(final PokeNpc item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PokeNpcProviderAdapter.POKENPC_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return PokeNpc
     */
    public PokeNpc query(final PokeNpc item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return PokeNpc
     */
    public PokeNpc query(final int id) {
        PokeNpc result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PokeNpcContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            PokeNpcProviderAdapter.POKENPC_URI,
            PokeNpcContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PokeNpcContract.cursorToItem(cursor);

            result.setObjets(
                this.getAssociateObjets(result));
            result.setBadge(
                this.getAssociateBadge(result));
            result.setPokemons(
                this.getAssociatePokemons(result));
            result.setTeam(
                this.getAssociateTeam(result));
            if (result.getPosition() != null) {
                result.setPosition(
                    this.getAssociatePosition(result));
            }
            if (result.getZone() != null) {
                result.setZone(
                    this.getAssociateZone(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<PokeNpc>
     */
    public ArrayList<PokeNpc> queryAll() {
        ArrayList<PokeNpc> result =
                    new ArrayList<PokeNpc>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeNpcProviderAdapter.POKENPC_URI,
                PokeNpcContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PokeNpcContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<PokeNpc>
     */
    public ArrayList<PokeNpc> query(CriteriaExpression expression) {
        ArrayList<PokeNpc> result =
                    new ArrayList<PokeNpc>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokeNpcProviderAdapter.POKENPC_URI,
                PokeNpcContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PokeNpcContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item PokeNpc
     * @return number of rows updated
     */
    public int update(final PokeNpc item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokeNpcContract.itemToContentValues(item);

        Uri uri = PokeNpcProviderAdapter.POKENPC_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getObjets() != null && item.getObjets().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new objets for PokeNpc
            CriteriaExpression objetsCrit = 
                    new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokeObjetContract.COL_ID);
            crit.addValue(values);
            objetsCrit.add(crit);


            for (PokeObjet objets : item.getObjets()) {
                values.addValue(
                    String.valueOf(objets.getId()));
            }
            selection = objetsCrit.toSQLiteSelection();
            selectionArgs = objetsCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokeObjetProviderAdapter.POKEOBJET_URI)
                    .withValue(
                            PokeObjetContract.COL_POKENPCOBJETSINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated objets
            crit.setType(Type.NOT_IN);
            objetsCrit.add(PokeObjetContract.COL_POKENPCOBJETSINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokeObjetProviderAdapter.POKEOBJET_URI)
                    .withValue(
                            PokeObjetContract.COL_POKENPCOBJETSINTERNAL_ID,
                            null)
                    .withSelection(
                            objetsCrit.toSQLiteSelection(),
                            objetsCrit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getBadge() != null && item.getBadge().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new badge for PokeNpc
            CriteriaExpression badgeCrit = 
                    new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokeBadgeContract.COL_ID);
            crit.addValue(values);
            badgeCrit.add(crit);


            for (PokeBadge badge : item.getBadge()) {
                values.addValue(
                    String.valueOf(badge.getId()));
            }
            selection = badgeCrit.toSQLiteSelection();
            selectionArgs = badgeCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokeBadgeProviderAdapter.POKEBADGE_URI)
                    .withValue(
                            PokeBadgeContract.COL_POKENPCBADGEINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated badge
            crit.setType(Type.NOT_IN);
            badgeCrit.add(PokeBadgeContract.COL_POKENPCBADGEINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokeBadgeProviderAdapter.POKEBADGE_URI)
                    .withValue(
                            PokeBadgeContract.COL_POKENPCBADGEINTERNAL_ID,
                            null)
                    .withSelection(
                            badgeCrit.toSQLiteSelection(),
                            badgeCrit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getPokemons() != null && item.getPokemons().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new pokemons for PokeNpc
            CriteriaExpression pokemonsCrit = 
                    new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokePokemonContract.COL_ID);
            crit.addValue(values);
            pokemonsCrit.add(crit);


            for (PokePokemon pokemons : item.getPokemons()) {
                values.addValue(
                    String.valueOf(pokemons.getId()));
            }
            selection = pokemonsCrit.toSQLiteSelection();
            selectionArgs = pokemonsCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokePokemonProviderAdapter.POKEPOKEMON_URI)
                    .withValue(
                            PokePokemonContract.COL_POKENPCPOKEMONSINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated pokemons
            crit.setType(Type.NOT_IN);
            pokemonsCrit.add(PokePokemonContract.COL_POKENPCPOKEMONSINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokePokemonProviderAdapter.POKEPOKEMON_URI)
                    .withValue(
                            PokePokemonContract.COL_POKENPCPOKEMONSINTERNAL_ID,
                            null)
                    .withSelection(
                            pokemonsCrit.toSQLiteSelection(),
                            pokemonsCrit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getTeam() != null && item.getTeam().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new team for PokeNpc
            CriteriaExpression teamCrit = 
                    new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokePokemonContract.COL_ID);
            crit.addValue(values);
            teamCrit.add(crit);


            for (PokePokemon team : item.getTeam()) {
                values.addValue(
                    String.valueOf(team.getId()));
            }
            selection = teamCrit.toSQLiteSelection();
            selectionArgs = teamCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokePokemonProviderAdapter.POKEPOKEMON_URI)
                    .withValue(
                            PokePokemonContract.COL_POKENPCTEAMINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated team
            crit.setType(Type.NOT_IN);
            teamCrit.add(PokePokemonContract.COL_POKENPCTEAMINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokePokemonProviderAdapter.POKEPOKEMON_URI)
                    .withValue(
                            PokePokemonContract.COL_POKENPCTEAMINTERNAL_ID,
                            null)
                    .withSelection(
                            teamCrit.toSQLiteSelection(),
                            teamCrit.toSQLiteSelectionArgs())
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
     * @param item PokeNpc
     * @param pokeArenedresseursInternalId pokeArenedresseursInternal Id* @param pokeDresseurnpcsInternalId pokeDresseurnpcsInternal Id
     * @return number of rows updated
     */
    public int update(final PokeNpc item,
                             final int pokeArenedresseursInternalId,
                             final int pokeDresseurnpcsInternalId) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokeNpcContract.itemToContentValues(
                item,
                pokeArenedresseursInternalId,
                pokeDresseurnpcsInternalId);

        Uri uri = PokeNpcProviderAdapter.POKENPC_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getObjets() != null && item.getObjets().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new objets for PokeNpc
            CriteriaExpression objetsCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokeObjetContract.COL_ID);
            crit.addValue(values);
            objetsCrit.add(crit);


            for (PokeObjet objets : item.getObjets()) {
                values.addValue(
                    String.valueOf(objets.getId()));
            }
            selection = objetsCrit.toSQLiteSelection();
            selectionArgs = objetsCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokeObjetProviderAdapter.POKEOBJET_URI)
                    .withValue(
                            PokeObjetContract.COL_POKENPCOBJETSINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated objets
            crit.setType(Type.NOT_IN);
            objetsCrit.add(PokeObjetContract.COL_POKENPCOBJETSINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokeObjetProviderAdapter.POKEOBJET_URI)
                    .withValue(
                            PokeObjetContract.COL_POKENPCOBJETSINTERNAL_ID,
                            null)
                    .withSelection(
                            objetsCrit.toSQLiteSelection(),
                            objetsCrit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getBadge() != null && item.getBadge().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new badge for PokeNpc
            CriteriaExpression badgeCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokeBadgeContract.COL_ID);
            crit.addValue(values);
            badgeCrit.add(crit);


            for (PokeBadge badge : item.getBadge()) {
                values.addValue(
                    String.valueOf(badge.getId()));
            }
            selection = badgeCrit.toSQLiteSelection();
            selectionArgs = badgeCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokeBadgeProviderAdapter.POKEBADGE_URI)
                    .withValue(
                            PokeBadgeContract.COL_POKENPCBADGEINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated badge
            crit.setType(Type.NOT_IN);
            badgeCrit.add(PokeBadgeContract.COL_POKENPCBADGEINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokeBadgeProviderAdapter.POKEBADGE_URI)
                    .withValue(
                            PokeBadgeContract.COL_POKENPCBADGEINTERNAL_ID,
                            null)
                    .withSelection(
                            badgeCrit.toSQLiteSelection(),
                            badgeCrit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getPokemons() != null && item.getPokemons().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new pokemons for PokeNpc
            CriteriaExpression pokemonsCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokePokemonContract.COL_ID);
            crit.addValue(values);
            pokemonsCrit.add(crit);


            for (PokePokemon pokemons : item.getPokemons()) {
                values.addValue(
                    String.valueOf(pokemons.getId()));
            }
            selection = pokemonsCrit.toSQLiteSelection();
            selectionArgs = pokemonsCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokePokemonProviderAdapter.POKEPOKEMON_URI)
                    .withValue(
                            PokePokemonContract.COL_POKENPCPOKEMONSINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated pokemons
            crit.setType(Type.NOT_IN);
            pokemonsCrit.add(PokePokemonContract.COL_POKENPCPOKEMONSINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokePokemonProviderAdapter.POKEPOKEMON_URI)
                    .withValue(
                            PokePokemonContract.COL_POKENPCPOKEMONSINTERNAL_ID,
                            null)
                    .withSelection(
                            pokemonsCrit.toSQLiteSelection(),
                            pokemonsCrit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getTeam() != null && item.getTeam().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new team for PokeNpc
            CriteriaExpression teamCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokePokemonContract.COL_ID);
            crit.addValue(values);
            teamCrit.add(crit);


            for (PokePokemon team : item.getTeam()) {
                values.addValue(
                    String.valueOf(team.getId()));
            }
            selection = teamCrit.toSQLiteSelection();
            selectionArgs = teamCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokePokemonProviderAdapter.POKEPOKEMON_URI)
                    .withValue(
                            PokePokemonContract.COL_POKENPCTEAMINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated team
            crit.setType(Type.NOT_IN);
            teamCrit.add(PokePokemonContract.COL_POKENPCTEAMINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokePokemonProviderAdapter.POKEPOKEMON_URI)
                    .withValue(
                            PokePokemonContract.COL_POKENPCTEAMINTERNAL_ID,
                            null)
                    .withSelection(
                            teamCrit.toSQLiteSelection(),
                            teamCrit.toSQLiteSelectionArgs())
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
     * Get associate Objets.
     * @param item PokeNpc
     * @return PokeObjet
     */
    public ArrayList<PokeObjet> getAssociateObjets(
            final PokeNpc item) {
        ArrayList<PokeObjet> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeObjetCursor = prov.query(
                PokeObjetProviderAdapter.POKEOBJET_URI,
                PokeObjetContract.ALIASED_COLS,
                PokeObjetContract.ALIASED_COL_POKENPCOBJETSINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = PokeObjetContract.cursorToItems(
                        pokeObjetCursor);
        pokeObjetCursor.close();

        return result;
    }

    /**
     * Get associate Badge.
     * @param item PokeNpc
     * @return PokeBadge
     */
    public ArrayList<PokeBadge> getAssociateBadge(
            final PokeNpc item) {
        ArrayList<PokeBadge> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokeBadgeCursor = prov.query(
                PokeBadgeProviderAdapter.POKEBADGE_URI,
                PokeBadgeContract.ALIASED_COLS,
                PokeBadgeContract.ALIASED_COL_POKENPCBADGEINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = PokeBadgeContract.cursorToItems(
                        pokeBadgeCursor);
        pokeBadgeCursor.close();

        return result;
    }

    /**
     * Get associate Pokemons.
     * @param item PokeNpc
     * @return PokePokemon
     */
    public ArrayList<PokePokemon> getAssociatePokemons(
            final PokeNpc item) {
        ArrayList<PokePokemon> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokePokemonCursor = prov.query(
                PokePokemonProviderAdapter.POKEPOKEMON_URI,
                PokePokemonContract.ALIASED_COLS,
                PokePokemonContract.ALIASED_COL_POKENPCPOKEMONSINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = PokePokemonContract.cursorToItems(
                        pokePokemonCursor);
        pokePokemonCursor.close();

        return result;
    }

    /**
     * Get associate Team.
     * @param item PokeNpc
     * @return PokePokemon
     */
    public ArrayList<PokePokemon> getAssociateTeam(
            final PokeNpc item) {
        ArrayList<PokePokemon> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokePokemonCursor = prov.query(
                PokePokemonProviderAdapter.POKEPOKEMON_URI,
                PokePokemonContract.ALIASED_COLS,
                PokePokemonContract.ALIASED_COL_POKENPCTEAMINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = PokePokemonContract.cursorToItems(
                        pokePokemonCursor);
        pokePokemonCursor.close();

        return result;
    }

    /**
     * Get associate Position.
     * @param item PokeNpc
     * @return PokePosition
     */
    public PokePosition getAssociatePosition(
            final PokeNpc item) {
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

    /**
     * Get associate Zone.
     * @param item PokeNpc
     * @return PokeZone
     */
    public PokeZone getAssociateZone(
            final PokeNpc item) {
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

}
