/**************************************************************************
 * PokeTypePokemonProviderAdapterBase.java, pokebattle Android
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



import com.antoinecronier.pokebattle.entity.PokeTypePokemon;
import com.antoinecronier.pokebattle.provider.ProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;
import com.antoinecronier.pokebattle.data.PokeTypePokemonSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeTypeSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeZoneSQLiteAdapter;

/**
 * PokeTypePokemonProviderAdapterBase.
 */
public abstract class PokeTypePokemonProviderAdapterBase
                extends ProviderAdapter<PokeTypePokemon> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokeTypePokemonProviderAdapter";

    /** POKETYPEPOKEMON_URI. */
    public      static Uri POKETYPEPOKEMON_URI;

    /** pokeTypePokemon type. */
    protected static final String pokeTypePokemonType =
            "poketypepokemon";

    /** POKETYPEPOKEMON_ALL. */
    protected static final int POKETYPEPOKEMON_ALL =
            881708736;
    /** POKETYPEPOKEMON_ONE. */
    protected static final int POKETYPEPOKEMON_ONE =
            881708737;

    /** POKETYPEPOKEMON_EVOLUE. */
    protected static final int POKETYPEPOKEMON_EVOLUE =
            881708738;
    /** POKETYPEPOKEMON_TYPES. */
    protected static final int POKETYPEPOKEMON_TYPES =
            881708739;
    /** POKETYPEPOKEMON_ZONES. */
    protected static final int POKETYPEPOKEMON_ZONES =
            881708740;

    /**
     * Static constructor.
     */
    static {
        POKETYPEPOKEMON_URI =
                PokebattleProvider.generateUri(
                        pokeTypePokemonType);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeTypePokemonType,
                POKETYPEPOKEMON_ALL);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeTypePokemonType + "/#",
                POKETYPEPOKEMON_ONE);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeTypePokemonType + "/#" + "/evolue",
                POKETYPEPOKEMON_EVOLUE);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeTypePokemonType + "/#" + "/types",
                POKETYPEPOKEMON_TYPES);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokeTypePokemonType + "/#" + "/zones",
                POKETYPEPOKEMON_ZONES);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PokeTypePokemonProviderAdapterBase(
            PokebattleProviderBase provider) {
        super(
            provider,
            new PokeTypePokemonSQLiteAdapter(provider.getContext()));

        this.uriIds.add(POKETYPEPOKEMON_ALL);
        this.uriIds.add(POKETYPEPOKEMON_ONE);
        this.uriIds.add(POKETYPEPOKEMON_EVOLUE);
        this.uriIds.add(POKETYPEPOKEMON_TYPES);
        this.uriIds.add(POKETYPEPOKEMON_ZONES);
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
            case POKETYPEPOKEMON_ALL:
                result = collection + "poketypepokemon";
                break;
            case POKETYPEPOKEMON_ONE:
                result = single + "poketypepokemon";
                break;
            case POKETYPEPOKEMON_EVOLUE:
                result = single + "poketypepokemon";
                break;
            case POKETYPEPOKEMON_TYPES:
                result = collection + "poketypepokemon";
                break;
            case POKETYPEPOKEMON_ZONES:
                result = collection + "poketypepokemon";
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
            case POKETYPEPOKEMON_ONE:
                String id = uri.getPathSegments().get(1);
                selection = PokeTypePokemonContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case POKETYPEPOKEMON_ALL:
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
            case POKETYPEPOKEMON_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PokeTypePokemonContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            POKETYPEPOKEMON_URI,
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
        android.database.Cursor pokeTypePokemonCursor;
        int poketypepokemonId;

        switch (matchedUri) {

            case POKETYPEPOKEMON_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case POKETYPEPOKEMON_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case POKETYPEPOKEMON_EVOLUE:
                pokeTypePokemonCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokeTypePokemonCursor.getCount() > 0) {
                    pokeTypePokemonCursor.moveToFirst();
                    int evolueId = pokeTypePokemonCursor.getInt(
                            pokeTypePokemonCursor.getColumnIndex(
                                    PokeTypePokemonContract.COL_EVOLUE_ID));

                    PokeTypePokemonSQLiteAdapter pokeTypePokemonAdapter = new PokeTypePokemonSQLiteAdapter(this.ctx);
                    pokeTypePokemonAdapter.open(this.getDb());
                    result = pokeTypePokemonAdapter.query(evolueId);
                }
                break;

            case POKETYPEPOKEMON_TYPES:
                poketypepokemonId = Integer.parseInt(uri.getPathSegments().get(1));
                PokeTypeSQLiteAdapter typesAdapter = new PokeTypeSQLiteAdapter(this.ctx);
                typesAdapter.open(this.getDb());
                result = typesAdapter.getByPokeTypePokemontypesInternal(poketypepokemonId, PokeTypeContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case POKETYPEPOKEMON_ZONES:
                poketypepokemonId = Integer.parseInt(uri.getPathSegments().get(1));
                PokeZoneSQLiteAdapter zonesAdapter = new PokeZoneSQLiteAdapter(this.ctx);
                zonesAdapter.open(this.getDb());
                result = zonesAdapter.getByPokeTypePokemonzonesInternal(poketypepokemonId, PokeZoneContract.ALIASED_COLS, selection, selectionArgs, null);
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
            case POKETYPEPOKEMON_ONE:
                selectionArgs = new String[1];
                selection = PokeTypePokemonContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case POKETYPEPOKEMON_ALL:
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
        return POKETYPEPOKEMON_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = PokeTypePokemonContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    PokeTypePokemonContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

