/**************************************************************************
 * PokeBadgeProviderAdapterBase.java, pokebattle Android
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



import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.provider.ProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;
import com.antoinecronier.pokebattle.data.PokeBadgeSQLiteAdapter;

/**
 * PokeBadgeProviderAdapterBase.
 */
public abstract class PokeBadgeProviderAdapterBase
                extends ProviderAdapter<PokeBadge> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeBadgeProviderAdapter";

    /** POKEBADGE_URI. */
    public      static Uri POKEBADGE_URI;

    /** pokeBadge type. */
    protected static final String pokeBadgeType =
            "pokebadge";

    /** POKEBADGE_ALL. */
    protected static final int POKEBADGE_ALL =
            745081706;
    /** POKEBADGE_ONE. */
    protected static final int POKEBADGE_ONE =
            745081707;


    /**
     * Static constructor.
     */
    static {
        POKEBADGE_URI =
                PokebattleProvider.generateUri(
                        pokeBadgeType);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeBadgeType,
                POKEBADGE_ALL);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeBadgeType + "/#",
                POKEBADGE_ONE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PokeBadgeProviderAdapterBase(
            PokebattleProviderBase provider) {
        super(
            provider,
            new PokeBadgeSQLiteAdapter(provider.getContext()));

        this.uriIds.add(POKEBADGE_ALL);
        this.uriIds.add(POKEBADGE_ONE);
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
            case POKEBADGE_ALL:
                result = collection + "pokebadge";
                break;
            case POKEBADGE_ONE:
                result = single + "pokebadge";
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
            case POKEBADGE_ONE:
                String id = uri.getPathSegments().get(1);
                selection = PokeBadgeContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case POKEBADGE_ALL:
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
            case POKEBADGE_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PokeBadgeContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            POKEBADGE_URI,
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
        android.database.Cursor pokeBadgeCursor;

        switch (matchedUri) {

            case POKEBADGE_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case POKEBADGE_ONE:
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
            case POKEBADGE_ONE:
                selectionArgs = new String[1];
                selection = PokeBadgeContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case POKEBADGE_ALL:
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
        return POKEBADGE_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = PokeBadgeContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    PokeBadgeContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

