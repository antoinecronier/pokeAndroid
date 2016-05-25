/**************************************************************************
 * PokeTypeObjetProviderAdapterBase.java, pokebattle Android
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



import com.antoinecronier.pokebattle.entity.PokeTypeObjet;
import com.antoinecronier.pokebattle.provider.ProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeObjetContract;
import com.antoinecronier.pokebattle.data.PokeTypeObjetSQLiteAdapter;

/**
 * PokeTypeObjetProviderAdapterBase.
 */
public abstract class PokeTypeObjetProviderAdapterBase
                extends ProviderAdapter<PokeTypeObjet> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeTypeObjetProviderAdapter";

    /** POKETYPEOBJET_URI. */
    public      static Uri POKETYPEOBJET_URI;

    /** pokeTypeObjet type. */
    protected static final String pokeTypeObjetType =
            "poketypeobjet";

    /** POKETYPEOBJET_ALL. */
    protected static final int POKETYPEOBJET_ALL =
            285638931;
    /** POKETYPEOBJET_ONE. */
    protected static final int POKETYPEOBJET_ONE =
            285638932;


    /**
     * Static constructor.
     */
    static {
        POKETYPEOBJET_URI =
                PokebattleProvider.generateUri(
                        pokeTypeObjetType);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeTypeObjetType,
                POKETYPEOBJET_ALL);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeTypeObjetType + "/#",
                POKETYPEOBJET_ONE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PokeTypeObjetProviderAdapterBase(
            PokebattleProviderBase provider) {
        super(
            provider,
            new PokeTypeObjetSQLiteAdapter(provider.getContext()));

        this.uriIds.add(POKETYPEOBJET_ALL);
        this.uriIds.add(POKETYPEOBJET_ONE);
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
            case POKETYPEOBJET_ALL:
                result = collection + "poketypeobjet";
                break;
            case POKETYPEOBJET_ONE:
                result = single + "poketypeobjet";
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
            case POKETYPEOBJET_ONE:
                String id = uri.getPathSegments().get(1);
                selection = PokeTypeObjetContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case POKETYPEOBJET_ALL:
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
            case POKETYPEOBJET_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PokeTypeObjetContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            POKETYPEOBJET_URI,
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

            case POKETYPEOBJET_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case POKETYPEOBJET_ONE:
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
            case POKETYPEOBJET_ONE:
                selectionArgs = new String[1];
                selection = PokeTypeObjetContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case POKETYPEOBJET_ALL:
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
        return POKETYPEOBJET_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = PokeTypeObjetContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    PokeTypeObjetContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

