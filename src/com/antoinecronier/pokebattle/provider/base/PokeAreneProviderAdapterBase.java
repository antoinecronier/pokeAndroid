/**************************************************************************
 * PokeAreneProviderAdapterBase.java, pokebattle Android
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



import com.antoinecronier.pokebattle.entity.PokeArene;
import com.antoinecronier.pokebattle.provider.ProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeAreneContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.data.PokeAreneSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeNpcSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeBadgeSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeZoneSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokePositionSQLiteAdapter;

/**
 * PokeAreneProviderAdapterBase.
 */
public abstract class PokeAreneProviderAdapterBase
                extends ProviderAdapter<PokeArene> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeAreneProviderAdapter";

    /** POKEARENE_URI. */
    public      static Uri POKEARENE_URI;

    /** pokeArene type. */
    protected static final String pokeAreneType =
            "pokearene";

    /** POKEARENE_ALL. */
    protected static final int POKEARENE_ALL =
            744665810;
    /** POKEARENE_ONE. */
    protected static final int POKEARENE_ONE =
            744665811;

    /** POKEARENE_MAITRE. */
    protected static final int POKEARENE_MAITRE =
            744665812;
    /** POKEARENE_DRESSEURS. */
    protected static final int POKEARENE_DRESSEURS =
            744665813;
    /** POKEARENE_BADGE. */
    protected static final int POKEARENE_BADGE =
            744665814;
    /** POKEARENE_ZONE. */
    protected static final int POKEARENE_ZONE =
            744665815;
    /** POKEARENE_POSITION. */
    protected static final int POKEARENE_POSITION =
            744665816;

    /**
     * Static constructor.
     */
    static {
        POKEARENE_URI =
                PokebattleProvider.generateUri(
                        pokeAreneType);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeAreneType,
                POKEARENE_ALL);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeAreneType + "/#",
                POKEARENE_ONE);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeAreneType + "/#" + "/maitre",
                POKEARENE_MAITRE);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeAreneType + "/#" + "/dresseurs",
                POKEARENE_DRESSEURS);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeAreneType + "/#" + "/badge",
                POKEARENE_BADGE);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeAreneType + "/#" + "/zone",
                POKEARENE_ZONE);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeAreneType + "/#" + "/position",
                POKEARENE_POSITION);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PokeAreneProviderAdapterBase(
            PokebattleProviderBase provider) {
        super(
            provider,
            new PokeAreneSQLiteAdapter(provider.getContext()));

        this.uriIds.add(POKEARENE_ALL);
        this.uriIds.add(POKEARENE_ONE);
        this.uriIds.add(POKEARENE_MAITRE);
        this.uriIds.add(POKEARENE_DRESSEURS);
        this.uriIds.add(POKEARENE_BADGE);
        this.uriIds.add(POKEARENE_ZONE);
        this.uriIds.add(POKEARENE_POSITION);
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
            case POKEARENE_ALL:
                result = collection + "pokearene";
                break;
            case POKEARENE_ONE:
                result = single + "pokearene";
                break;
            case POKEARENE_MAITRE:
                result = single + "pokearene";
                break;
            case POKEARENE_DRESSEURS:
                result = collection + "pokearene";
                break;
            case POKEARENE_BADGE:
                result = single + "pokearene";
                break;
            case POKEARENE_ZONE:
                result = single + "pokearene";
                break;
            case POKEARENE_POSITION:
                result = single + "pokearene";
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
            case POKEARENE_ONE:
                String id = uri.getPathSegments().get(1);
                selection = PokeAreneContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case POKEARENE_ALL:
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
            case POKEARENE_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PokeAreneContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            POKEARENE_URI,
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
        android.database.Cursor pokeAreneCursor;
        int pokeareneId;

        switch (matchedUri) {

            case POKEARENE_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case POKEARENE_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case POKEARENE_MAITRE:
                pokeAreneCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokeAreneCursor.getCount() > 0) {
                    pokeAreneCursor.moveToFirst();
                    int maitreId = pokeAreneCursor.getInt(
                            pokeAreneCursor.getColumnIndex(
                                    PokeAreneContract.COL_MAITRE_ID));

                    PokeNpcSQLiteAdapter pokeNpcAdapter = new PokeNpcSQLiteAdapter(this.ctx);
                    pokeNpcAdapter.open(this.getDb());
                    result = pokeNpcAdapter.query(maitreId);
                }
                break;

            case POKEARENE_DRESSEURS:
                pokeareneId = Integer.parseInt(uri.getPathSegments().get(1));
                PokeNpcSQLiteAdapter dresseursAdapter = new PokeNpcSQLiteAdapter(this.ctx);
                dresseursAdapter.open(this.getDb());
                result = dresseursAdapter.getByPokeArenedresseursInternal(pokeareneId, PokeNpcContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case POKEARENE_BADGE:
                pokeAreneCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokeAreneCursor.getCount() > 0) {
                    pokeAreneCursor.moveToFirst();
                    int badgeId = pokeAreneCursor.getInt(
                            pokeAreneCursor.getColumnIndex(
                                    PokeAreneContract.COL_BADGE_ID));

                    PokeBadgeSQLiteAdapter pokeBadgeAdapter = new PokeBadgeSQLiteAdapter(this.ctx);
                    pokeBadgeAdapter.open(this.getDb());
                    result = pokeBadgeAdapter.query(badgeId);
                }
                break;

            case POKEARENE_ZONE:
                pokeAreneCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokeAreneCursor.getCount() > 0) {
                    pokeAreneCursor.moveToFirst();
                    int zoneId = pokeAreneCursor.getInt(
                            pokeAreneCursor.getColumnIndex(
                                    PokeAreneContract.COL_ZONE_ID));

                    PokeZoneSQLiteAdapter pokeZoneAdapter = new PokeZoneSQLiteAdapter(this.ctx);
                    pokeZoneAdapter.open(this.getDb());
                    result = pokeZoneAdapter.query(zoneId);
                }
                break;

            case POKEARENE_POSITION:
                pokeAreneCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokeAreneCursor.getCount() > 0) {
                    pokeAreneCursor.moveToFirst();
                    int positionId = pokeAreneCursor.getInt(
                            pokeAreneCursor.getColumnIndex(
                                    PokeAreneContract.COL_POSITION_ID));

                    PokePositionSQLiteAdapter pokePositionAdapter = new PokePositionSQLiteAdapter(this.ctx);
                    pokePositionAdapter.open(this.getDb());
                    result = pokePositionAdapter.query(positionId);
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
            case POKEARENE_ONE:
                selectionArgs = new String[1];
                selection = PokeAreneContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case POKEARENE_ALL:
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
        return POKEARENE_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = PokeAreneContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    PokeAreneContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

