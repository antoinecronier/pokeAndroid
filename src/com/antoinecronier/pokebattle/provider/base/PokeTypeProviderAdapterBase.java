/**************************************************************************
 * PokeTypeProviderAdapterBase.java, pokebattle Android
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



import com.antoinecronier.pokebattle.entity.PokeType;
import com.antoinecronier.pokebattle.provider.ProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.data.PokeTypeSQLiteAdapter;

/**
 * PokeTypeProviderAdapterBase.
 */
public abstract class PokeTypeProviderAdapterBase
                extends ProviderAdapter<PokeType> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeTypeProviderAdapter";

    /** POKETYPE_URI. */
    public      static Uri POKETYPE_URI;

    /** pokeType type. */
    protected static final String pokeTypeType =
            "poketype";

    /** POKETYPE_ALL. */
    protected static final int POKETYPE_ALL =
            578783891;
    /** POKETYPE_ONE. */
    protected static final int POKETYPE_ONE =
            578783892;

    /** POKETYPE_TYPEFORT. */
    protected static final int POKETYPE_TYPEFORT =
            578783893;
    /** POKETYPE_TYPEFAIBLE. */
    protected static final int POKETYPE_TYPEFAIBLE =
            578783894;

    /**
     * Static constructor.
     */
    static {
        POKETYPE_URI =
                PokebattleProvider.generateUri(
                        pokeTypeType);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeTypeType,
                POKETYPE_ALL);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeTypeType + "/#",
                POKETYPE_ONE);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeTypeType + "/#" + "/typefort",
                POKETYPE_TYPEFORT);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeTypeType + "/#" + "/typefaible",
                POKETYPE_TYPEFAIBLE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PokeTypeProviderAdapterBase(
            PokebattleProviderBase provider) {
        super(
            provider,
            new PokeTypeSQLiteAdapter(provider.getContext()));

        this.uriIds.add(POKETYPE_ALL);
        this.uriIds.add(POKETYPE_ONE);
        this.uriIds.add(POKETYPE_TYPEFORT);
        this.uriIds.add(POKETYPE_TYPEFAIBLE);
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
            case POKETYPE_ALL:
                result = collection + "poketype";
                break;
            case POKETYPE_ONE:
                result = single + "poketype";
                break;
            case POKETYPE_TYPEFORT:
                result = collection + "poketype";
                break;
            case POKETYPE_TYPEFAIBLE:
                result = collection + "poketype";
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
            case POKETYPE_ONE:
                String id = uri.getPathSegments().get(1);
                selection = PokeTypeContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case POKETYPE_ALL:
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
            case POKETYPE_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PokeTypeContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            POKETYPE_URI,
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
        android.database.Cursor pokeTypeCursor;
        int poketypeId;

        switch (matchedUri) {

            case POKETYPE_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case POKETYPE_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case POKETYPE_TYPEFORT:
                poketypeId = Integer.parseInt(uri.getPathSegments().get(1));
                PokeTypeSQLiteAdapter typeFortAdapter = new PokeTypeSQLiteAdapter(this.ctx);
                typeFortAdapter.open(this.getDb());
                result = typeFortAdapter.getByPokeTypetypeFortInternal(poketypeId, PokeTypeContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case POKETYPE_TYPEFAIBLE:
                poketypeId = Integer.parseInt(uri.getPathSegments().get(1));
                PokeTypeSQLiteAdapter typeFaibleAdapter = new PokeTypeSQLiteAdapter(this.ctx);
                typeFaibleAdapter.open(this.getDb());
                result = typeFaibleAdapter.getByPokeTypetypeFaibleInternal(poketypeId, PokeTypeContract.ALIASED_COLS, selection, selectionArgs, null);
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
            case POKETYPE_ONE:
                selectionArgs = new String[1];
                selection = PokeTypeContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case POKETYPE_ALL:
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
        return POKETYPE_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = PokeTypeContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    PokeTypeContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

