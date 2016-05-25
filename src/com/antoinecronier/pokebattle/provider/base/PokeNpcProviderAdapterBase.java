/**************************************************************************
 * PokeNpcProviderAdapterBase.java, pokebattle Android
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



import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.provider.ProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeObjetContract;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;
import com.antoinecronier.pokebattle.provider.contract.PokePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokePokemonContract;
import com.antoinecronier.pokebattle.data.PokeNpcSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeObjetSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeBadgeSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokePokemonSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokePositionSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeZoneSQLiteAdapter;

/**
 * PokeNpcProviderAdapterBase.
 */
public abstract class PokeNpcProviderAdapterBase
                extends ProviderAdapter<PokeNpc> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeNpcProviderAdapter";

    /** POKENPC_URI. */
    public      static Uri POKENPC_URI;

    /** pokeNpc type. */
    protected static final String pokeNpcType =
            "pokenpc";

    /** POKENPC_ALL. */
    protected static final int POKENPC_ALL =
            1265590376;
    /** POKENPC_ONE. */
    protected static final int POKENPC_ONE =
            1265590377;

    /** POKENPC_OBJETS. */
    protected static final int POKENPC_OBJETS =
            1265590378;
    /** POKENPC_BADGE. */
    protected static final int POKENPC_BADGE =
            1265590379;
    /** POKENPC_POKEMONS. */
    protected static final int POKENPC_POKEMONS =
            1265590380;
    /** POKENPC_TEAM. */
    protected static final int POKENPC_TEAM =
            1265590381;
    /** POKENPC_POSITION. */
    protected static final int POKENPC_POSITION =
            1265590382;
    /** POKENPC_ZONE. */
    protected static final int POKENPC_ZONE =
            1265590383;

    /**
     * Static constructor.
     */
    static {
        POKENPC_URI =
                PokebattleProvider.generateUri(
                        pokeNpcType);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeNpcType,
                POKENPC_ALL);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeNpcType + "/#",
                POKENPC_ONE);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeNpcType + "/#" + "/objets",
                POKENPC_OBJETS);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeNpcType + "/#" + "/badge",
                POKENPC_BADGE);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeNpcType + "/#" + "/pokemons",
                POKENPC_POKEMONS);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeNpcType + "/#" + "/team",
                POKENPC_TEAM);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeNpcType + "/#" + "/position",
                POKENPC_POSITION);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeNpcType + "/#" + "/zone",
                POKENPC_ZONE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PokeNpcProviderAdapterBase(
            PokebattleProviderBase provider) {
        super(
            provider,
            new PokeNpcSQLiteAdapter(provider.getContext()));

        this.uriIds.add(POKENPC_ALL);
        this.uriIds.add(POKENPC_ONE);
        this.uriIds.add(POKENPC_OBJETS);
        this.uriIds.add(POKENPC_BADGE);
        this.uriIds.add(POKENPC_POKEMONS);
        this.uriIds.add(POKENPC_TEAM);
        this.uriIds.add(POKENPC_POSITION);
        this.uriIds.add(POKENPC_ZONE);
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
            case POKENPC_ALL:
                result = collection + "pokenpc";
                break;
            case POKENPC_ONE:
                result = single + "pokenpc";
                break;
            case POKENPC_OBJETS:
                result = collection + "pokenpc";
                break;
            case POKENPC_BADGE:
                result = collection + "pokenpc";
                break;
            case POKENPC_POKEMONS:
                result = collection + "pokenpc";
                break;
            case POKENPC_TEAM:
                result = collection + "pokenpc";
                break;
            case POKENPC_POSITION:
                result = single + "pokenpc";
                break;
            case POKENPC_ZONE:
                result = single + "pokenpc";
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
            case POKENPC_ONE:
                String id = uri.getPathSegments().get(1);
                selection = PokeNpcContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case POKENPC_ALL:
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
            case POKENPC_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PokeNpcContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            POKENPC_URI,
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
        android.database.Cursor pokeNpcCursor;
        int pokenpcId;

        switch (matchedUri) {

            case POKENPC_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case POKENPC_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case POKENPC_OBJETS:
                pokenpcId = Integer.parseInt(uri.getPathSegments().get(1));
                PokeObjetSQLiteAdapter objetsAdapter = new PokeObjetSQLiteAdapter(this.ctx);
                objetsAdapter.open(this.getDb());
                result = objetsAdapter.getByPokeNpcobjetsInternal(pokenpcId, PokeObjetContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case POKENPC_BADGE:
                pokenpcId = Integer.parseInt(uri.getPathSegments().get(1));
                PokeBadgeSQLiteAdapter badgeAdapter = new PokeBadgeSQLiteAdapter(this.ctx);
                badgeAdapter.open(this.getDb());
                result = badgeAdapter.getByPokeNpcbadgeInternal(pokenpcId, PokeBadgeContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case POKENPC_POKEMONS:
                pokenpcId = Integer.parseInt(uri.getPathSegments().get(1));
                PokePokemonSQLiteAdapter pokemonsAdapter = new PokePokemonSQLiteAdapter(this.ctx);
                pokemonsAdapter.open(this.getDb());
                result = pokemonsAdapter.getByPokeNpcpokemonsInternal(pokenpcId, PokePokemonContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case POKENPC_TEAM:
                pokenpcId = Integer.parseInt(uri.getPathSegments().get(1));
                PokePokemonSQLiteAdapter teamAdapter = new PokePokemonSQLiteAdapter(this.ctx);
                teamAdapter.open(this.getDb());
                result = teamAdapter.getByPokeNpcteamInternal(pokenpcId, PokePokemonContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case POKENPC_POSITION:
                pokeNpcCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokeNpcCursor.getCount() > 0) {
                    pokeNpcCursor.moveToFirst();
                    int positionId = pokeNpcCursor.getInt(
                            pokeNpcCursor.getColumnIndex(
                                    PokeNpcContract.COL_POSITION_ID));

                    PokePositionSQLiteAdapter pokePositionAdapter = new PokePositionSQLiteAdapter(this.ctx);
                    pokePositionAdapter.open(this.getDb());
                    result = pokePositionAdapter.query(positionId);
                }
                break;

            case POKENPC_ZONE:
                pokeNpcCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokeNpcCursor.getCount() > 0) {
                    pokeNpcCursor.moveToFirst();
                    int zoneId = pokeNpcCursor.getInt(
                            pokeNpcCursor.getColumnIndex(
                                    PokeNpcContract.COL_ZONE_ID));

                    PokeZoneSQLiteAdapter pokeZoneAdapter = new PokeZoneSQLiteAdapter(this.ctx);
                    pokeZoneAdapter.open(this.getDb());
                    result = pokeZoneAdapter.query(zoneId);
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
            case POKENPC_ONE:
                selectionArgs = new String[1];
                selection = PokeNpcContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case POKENPC_ALL:
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
        return POKENPC_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = PokeNpcContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    PokeNpcContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

