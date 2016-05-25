/**************************************************************************
 * PokeAttaqueProviderAdapterBase.java, pokebattle Android
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



import com.antoinecronier.pokebattle.entity.PokeAttaque;
import com.antoinecronier.pokebattle.provider.ProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeAttaqueContract;
import com.antoinecronier.pokebattle.data.PokeAttaqueSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeTypeSQLiteAdapter;

/**
 * PokeAttaqueProviderAdapterBase.
 */
public abstract class PokeAttaqueProviderAdapterBase
                extends ProviderAdapter<PokeAttaque> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeAttaqueProviderAdapter";

    /** POKEATTAQUE_URI. */
    public      static Uri POKEATTAQUE_URI;

    /** pokeAttaque type. */
    protected static final String pokeAttaqueType =
            "pokeattaque";

    /** POKEATTAQUE_ALL. */
    protected static final int POKEATTAQUE_ALL =
            1564955928;
    /** POKEATTAQUE_ONE. */
    protected static final int POKEATTAQUE_ONE =
            1564955929;

    /** POKEATTAQUE_TYPE. */
    protected static final int POKEATTAQUE_TYPE =
            1564955930;

    /**
     * Static constructor.
     */
    static {
        POKEATTAQUE_URI =
                PokebattleProvider.generateUri(
                        pokeAttaqueType);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeAttaqueType,
                POKEATTAQUE_ALL);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeAttaqueType + "/#",
                POKEATTAQUE_ONE);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeAttaqueType + "/#" + "/type",
                POKEATTAQUE_TYPE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PokeAttaqueProviderAdapterBase(
            PokebattleProviderBase provider) {
        super(
            provider,
            new PokeAttaqueSQLiteAdapter(provider.getContext()));

        this.uriIds.add(POKEATTAQUE_ALL);
        this.uriIds.add(POKEATTAQUE_ONE);
        this.uriIds.add(POKEATTAQUE_TYPE);
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
            case POKEATTAQUE_ALL:
                result = collection + "pokeattaque";
                break;
            case POKEATTAQUE_ONE:
                result = single + "pokeattaque";
                break;
            case POKEATTAQUE_TYPE:
                result = single + "pokeattaque";
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
            case POKEATTAQUE_ONE:
                String id = uri.getPathSegments().get(1);
                selection = PokeAttaqueContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case POKEATTAQUE_ALL:
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
            case POKEATTAQUE_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PokeAttaqueContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            POKEATTAQUE_URI,
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
        android.database.Cursor pokeAttaqueCursor;

        switch (matchedUri) {

            case POKEATTAQUE_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case POKEATTAQUE_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case POKEATTAQUE_TYPE:
                pokeAttaqueCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokeAttaqueCursor.getCount() > 0) {
                    pokeAttaqueCursor.moveToFirst();
                    int typeId = pokeAttaqueCursor.getInt(
                            pokeAttaqueCursor.getColumnIndex(
                                    PokeAttaqueContract.COL_TYPE_ID));

                    PokeTypeSQLiteAdapter pokeTypeAdapter = new PokeTypeSQLiteAdapter(this.ctx);
                    pokeTypeAdapter.open(this.getDb());
                    result = pokeTypeAdapter.query(typeId);
                }
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
            case POKEATTAQUE_ONE:
                selectionArgs = new String[1];
                selection = PokeAttaqueContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case POKEATTAQUE_ALL:
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
        return POKEATTAQUE_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = PokeAttaqueContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    PokeAttaqueContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

