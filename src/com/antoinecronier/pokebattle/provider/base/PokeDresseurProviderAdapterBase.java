/**************************************************************************
 * PokeDresseurProviderAdapterBase.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.provider.base;

import android.content.ContentUris;
import android.content.ContentValues;


import com.google.common.collect.ObjectArrays;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;



import com.antoinecronier.pokebattle.entity.PokeDresseur;
import com.antoinecronier.pokebattle.provider.ProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeDresseurContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.data.PokeDresseurSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeNpcSQLiteAdapter;

/**
 * PokeDresseurProviderAdapterBase.
 */
public abstract class PokeDresseurProviderAdapterBase
                extends ProviderAdapter<PokeDresseur> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeDresseurProviderAdapter";

    /** POKEDRESSEUR_URI. */
    public      static Uri POKEDRESSEUR_URI;

    /** pokeDresseur type. */
    protected static final String pokeDresseurType =
            "pokedresseur";

    /** POKEDRESSEUR_ALL. */
    protected static final int POKEDRESSEUR_ALL =
            1771661060;
    /** POKEDRESSEUR_ONE. */
    protected static final int POKEDRESSEUR_ONE =
            1771661061;

    /** POKEDRESSEUR_NPCS. */
    protected static final int POKEDRESSEUR_NPCS =
            1771661062;

    /**
     * Static constructor.
     */
    static {
        POKEDRESSEUR_URI =
                PokebattleProvider.generateUri(
                        pokeDresseurType);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeDresseurType,
                POKEDRESSEUR_ALL);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeDresseurType + "/#",
                POKEDRESSEUR_ONE);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeDresseurType + "/#" + "/npcs",
                POKEDRESSEUR_NPCS);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PokeDresseurProviderAdapterBase(
            PokebattleProviderBase provider) {
        super(
            provider,
            new PokeDresseurSQLiteAdapter(provider.getContext()));

        this.uriIds.add(POKEDRESSEUR_ALL);
        this.uriIds.add(POKEDRESSEUR_ONE);
        this.uriIds.add(POKEDRESSEUR_NPCS);
    }

    @Override
    public String getType(final Uri uri) {
        String result;
        final String single =
                "vnc.android.cursor.item/"
                    + PokebattleProvider.authority + ".";
        final String collection =
                "vnc.android.cursor.collection/"
                    + PokebattleProvider.authority + ".";

        int matchedUri = PokebattleProviderBase
                .getUriMatcher().match(uri);

        switch (matchedUri) {
            case POKEDRESSEUR_ALL:
                result = collection + "pokedresseur";
                break;
            case POKEDRESSEUR_ONE:
                result = single + "pokedresseur";
                break;
            case POKEDRESSEUR_NPCS:
                result = collection + "pokedresseur";
                break;
            default:
                result = null;
                break;
        }

        return result;
    }

    @Override
    public int delete(
            final Uri uri,
            String selection,
            String[] selectionArgs) {
        int matchedUri = PokebattleProviderBase
                    .getUriMatcher().match(uri);
        int result = -1;
        switch (matchedUri) {
            case POKEDRESSEUR_ONE:
                String id = uri.getPathSegments().get(1);
                selection = PokeDresseurContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case POKEDRESSEUR_ALL:
                result = this.adapter.delete(
                            selection,
                            selectionArgs);
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }

    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        int matchedUri = PokebattleProviderBase
                .getUriMatcher().match(uri);
                Uri result = null;
        int id = 0;
        switch (matchedUri) {
            case POKEDRESSEUR_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PokeDresseurContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            POKEDRESSEUR_URI,
                            String.valueOf(id));
                }
                break;
            default:
                result = null;
                break;
        }
        return result;
    }

    @Override
    public android.database.Cursor query(final Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        final String sortOrder) {

        int matchedUri = PokebattleProviderBase.getUriMatcher()
                .match(uri);
        android.database.Cursor result = null;
        int pokedresseurId;

        switch (matchedUri) {

            case POKEDRESSEUR_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case POKEDRESSEUR_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case POKEDRESSEUR_NPCS:
                pokedresseurId = Integer.parseInt(uri.getPathSegments().get(1));
                PokeNpcSQLiteAdapter npcsAdapter = new PokeNpcSQLiteAdapter(this.ctx);
                npcsAdapter.open(this.getDb());
                result = npcsAdapter.getByPokeDresseurnpcsInternal(pokedresseurId, PokeNpcContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            default:
                result = null;
                break;
        }

        return result;
    }

    @Override
    public int update(
            final Uri uri,
            final ContentValues values,
            String selection,
            String[] selectionArgs) {

        
        int matchedUri = PokebattleProviderBase.getUriMatcher()
                .match(uri);
        int result = -1;
        switch (matchedUri) {
            case POKEDRESSEUR_ONE:
                selectionArgs = new String[1];
                selection = PokeDresseurContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case POKEDRESSEUR_ALL:
                result = this.adapter.update(
                            values,
                            selection,
                            selectionArgs);
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }



    /**
     * Get the entity URI.
     * @return The URI
     */
    @Override
    public Uri getUri() {
        return POKEDRESSEUR_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = PokeDresseurContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    PokeDresseurContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

