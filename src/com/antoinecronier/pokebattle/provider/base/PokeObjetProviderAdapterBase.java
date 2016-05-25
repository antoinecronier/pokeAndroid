/**************************************************************************
 * PokeObjetProviderAdapterBase.java, pokebattle Android
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



import com.antoinecronier.pokebattle.entity.PokeObjet;
import com.antoinecronier.pokebattle.provider.ProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeObjetContract;
import com.antoinecronier.pokebattle.data.PokeObjetSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeTypeObjetSQLiteAdapter;

/**
 * PokeObjetProviderAdapterBase.
 */
public abstract class PokeObjetProviderAdapterBase
                extends ProviderAdapter<PokeObjet> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeObjetProviderAdapter";

    /** POKEOBJET_URI. */
    public      static Uri POKEOBJET_URI;

    /** pokeObjet type. */
    protected static final String pokeObjetType =
            "pokeobjet";

    /** POKEOBJET_ALL. */
    protected static final int POKEOBJET_ALL =
            757122989;
    /** POKEOBJET_ONE. */
    protected static final int POKEOBJET_ONE =
            757122990;

    /** POKEOBJET_TYPE. */
    protected static final int POKEOBJET_TYPE =
            757122991;

    /**
     * Static constructor.
     */
    static {
        POKEOBJET_URI =
                PokebattleProvider.generateUri(
                        pokeObjetType);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeObjetType,
                POKEOBJET_ALL);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeObjetType + "/#",
                POKEOBJET_ONE);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeObjetType + "/#" + "/type",
                POKEOBJET_TYPE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PokeObjetProviderAdapterBase(
            PokebattleProviderBase provider) {
        super(
            provider,
            new PokeObjetSQLiteAdapter(provider.getContext()));

        this.uriIds.add(POKEOBJET_ALL);
        this.uriIds.add(POKEOBJET_ONE);
        this.uriIds.add(POKEOBJET_TYPE);
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
            case POKEOBJET_ALL:
                result = collection + "pokeobjet";
                break;
            case POKEOBJET_ONE:
                result = single + "pokeobjet";
                break;
            case POKEOBJET_TYPE:
                result = single + "pokeobjet";
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
            case POKEOBJET_ONE:
                String id = uri.getPathSegments().get(1);
                selection = PokeObjetContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case POKEOBJET_ALL:
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
            case POKEOBJET_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PokeObjetContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            POKEOBJET_URI,
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
        android.database.Cursor pokeObjetCursor;

        switch (matchedUri) {

            case POKEOBJET_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case POKEOBJET_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case POKEOBJET_TYPE:
                pokeObjetCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokeObjetCursor.getCount() > 0) {
                    pokeObjetCursor.moveToFirst();
                    int typeId = pokeObjetCursor.getInt(
                            pokeObjetCursor.getColumnIndex(
                                    PokeObjetContract.COL_TYPE_ID));

                    PokeTypeObjetSQLiteAdapter pokeTypeObjetAdapter = new PokeTypeObjetSQLiteAdapter(this.ctx);
                    pokeTypeObjetAdapter.open(this.getDb());
                    result = pokeTypeObjetAdapter.query(typeId);
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
            case POKEOBJET_ONE:
                selectionArgs = new String[1];
                selection = PokeObjetContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case POKEOBJET_ALL:
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
        return POKEOBJET_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = PokeObjetContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    PokeObjetContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

