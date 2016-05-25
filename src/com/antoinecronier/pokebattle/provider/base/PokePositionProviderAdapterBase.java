/**************************************************************************
 * PokePositionProviderAdapterBase.java, pokebattle Android
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



import com.antoinecronier.pokebattle.entity.PokePosition;
import com.antoinecronier.pokebattle.provider.ProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokePositionContract;
import com.antoinecronier.pokebattle.data.PokePositionSQLiteAdapter;

/**
 * PokePositionProviderAdapterBase.
 */
public abstract class PokePositionProviderAdapterBase
                extends ProviderAdapter<PokePosition> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokePositionProviderAdapter";

    /** POKEPOSITION_URI. */
    public      static Uri POKEPOSITION_URI;

    /** pokePosition type. */
    protected static final String pokePositionType =
            "pokeposition";

    /** POKEPOSITION_ALL. */
    protected static final int POKEPOSITION_ALL =
            1060356094;
    /** POKEPOSITION_ONE. */
    protected static final int POKEPOSITION_ONE =
            1060356095;


    /**
     * Static constructor.
     */
    static {
        POKEPOSITION_URI =
                PokebattleProvider.generateUri(
                        pokePositionType);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokePositionType,
                POKEPOSITION_ALL);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokePositionType + "/#",
                POKEPOSITION_ONE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PokePositionProviderAdapterBase(
            PokebattleProviderBase provider) {
        super(
            provider,
            new PokePositionSQLiteAdapter(provider.getContext()));

        this.uriIds.add(POKEPOSITION_ALL);
        this.uriIds.add(POKEPOSITION_ONE);
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
            case POKEPOSITION_ALL:
                result = collection + "pokeposition";
                break;
            case POKEPOSITION_ONE:
                result = single + "pokeposition";
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
            case POKEPOSITION_ONE:
                String id = uri.getPathSegments().get(1);
                selection = PokePositionContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case POKEPOSITION_ALL:
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
            case POKEPOSITION_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PokePositionContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            POKEPOSITION_URI,
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

        switch (matchedUri) {

            case POKEPOSITION_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case POKEPOSITION_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
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
            case POKEPOSITION_ONE:
                selectionArgs = new String[1];
                selection = PokePositionContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case POKEPOSITION_ALL:
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
        return POKEPOSITION_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = PokePositionContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    PokePositionContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

