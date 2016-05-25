/**************************************************************************
 * PokePokemonProviderAdapterBase.java, pokebattle Android
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



import com.antoinecronier.pokebattle.entity.PokePokemon;
import com.antoinecronier.pokebattle.provider.ProviderAdapter;
import com.antoinecronier.pokebattle.provider.PokebattleProvider;
import com.antoinecronier.pokebattle.provider.contract.PokePokemonContract;
import com.antoinecronier.pokebattle.data.PokePokemonSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeTypePokemonSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeAttaqueSQLiteAdapter;

/**
 * PokePokemonProviderAdapterBase.
 */
public abstract class PokePokemonProviderAdapterBase
                extends ProviderAdapter<PokePokemon> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokePokemonProviderAdapter";

    /** POKEPOKEMON_URI. */
    public      static Uri POKEPOKEMON_URI;

    /** pokePokemon type. */
    protected static final String pokePokemonType =
            "pokepokemon";

    /** POKEPOKEMON_ALL. */
    protected static final int POKEPOKEMON_ALL =
            1288644902;
    /** POKEPOKEMON_ONE. */
    protected static final int POKEPOKEMON_ONE =
            1288644903;

    /** POKEPOKEMON_TYPE. */
    protected static final int POKEPOKEMON_TYPE =
            1288644904;
    /** POKEPOKEMON_ATTAQUE1. */
    protected static final int POKEPOKEMON_ATTAQUE1 =
            1288644905;
    /** POKEPOKEMON_ATTAQUE2. */
    protected static final int POKEPOKEMON_ATTAQUE2 =
            1288644906;
    /** POKEPOKEMON_ATTAQUE3. */
    protected static final int POKEPOKEMON_ATTAQUE3 =
            1288644907;
    /** POKEPOKEMON_ATTAQUE4. */
    protected static final int POKEPOKEMON_ATTAQUE4 =
            1288644908;

    /**
     * Static constructor.
     */
    static {
        POKEPOKEMON_URI =
                PokebattleProvider.generateUri(
                        pokePokemonType);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokePokemonType,
                POKEPOKEMON_ALL);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokePokemonType + "/#",
                POKEPOKEMON_ONE);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokePokemonType + "/#" + "/type",
                POKEPOKEMON_TYPE);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokePokemonType + "/#" + "/attaque1",
                POKEPOKEMON_ATTAQUE1);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokePokemonType + "/#" + "/attaque2",
                POKEPOKEMON_ATTAQUE2);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokePokemonType + "/#" + "/attaque3",
                POKEPOKEMON_ATTAQUE3);
        PokebattleProvider.getUriMatcher().addURI(
                PokebattleProvider.authority,
                pokePokemonType + "/#" + "/attaque4",
                POKEPOKEMON_ATTAQUE4);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PokePokemonProviderAdapterBase(
            PokebattleProviderBase provider) {
        super(
            provider,
            new PokePokemonSQLiteAdapter(provider.getContext()));

        this.uriIds.add(POKEPOKEMON_ALL);
        this.uriIds.add(POKEPOKEMON_ONE);
        this.uriIds.add(POKEPOKEMON_TYPE);
        this.uriIds.add(POKEPOKEMON_ATTAQUE1);
        this.uriIds.add(POKEPOKEMON_ATTAQUE2);
        this.uriIds.add(POKEPOKEMON_ATTAQUE3);
        this.uriIds.add(POKEPOKEMON_ATTAQUE4);
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
            case POKEPOKEMON_ALL:
                result = collection + "pokepokemon";
                break;
            case POKEPOKEMON_ONE:
                result = single + "pokepokemon";
                break;
            case POKEPOKEMON_TYPE:
                result = single + "pokepokemon";
                break;
            case POKEPOKEMON_ATTAQUE1:
                result = single + "pokepokemon";
                break;
            case POKEPOKEMON_ATTAQUE2:
                result = single + "pokepokemon";
                break;
            case POKEPOKEMON_ATTAQUE3:
                result = single + "pokepokemon";
                break;
            case POKEPOKEMON_ATTAQUE4:
                result = single + "pokepokemon";
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
            case POKEPOKEMON_ONE:
                String id = uri.getPathSegments().get(1);
                selection = PokePokemonContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case POKEPOKEMON_ALL:
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
            case POKEPOKEMON_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PokePokemonContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            POKEPOKEMON_URI,
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
        android.database.Cursor pokePokemonCursor;

        switch (matchedUri) {

            case POKEPOKEMON_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case POKEPOKEMON_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case POKEPOKEMON_TYPE:
                pokePokemonCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokePokemonCursor.getCount() > 0) {
                    pokePokemonCursor.moveToFirst();
                    int typeId = pokePokemonCursor.getInt(
                            pokePokemonCursor.getColumnIndex(
                                    PokePokemonContract.COL_TYPE_ID));

                    PokeTypePokemonSQLiteAdapter pokeTypePokemonAdapter = new PokeTypePokemonSQLiteAdapter(this.ctx);
                    pokeTypePokemonAdapter.open(this.getDb());
                    result = pokeTypePokemonAdapter.query(typeId);
                }
                break;

            case POKEPOKEMON_ATTAQUE1:
                pokePokemonCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokePokemonCursor.getCount() > 0) {
                    pokePokemonCursor.moveToFirst();
                    int attaque1Id = pokePokemonCursor.getInt(
                            pokePokemonCursor.getColumnIndex(
                                    PokePokemonContract.COL_ATTAQUE1_ID));

                    PokeAttaqueSQLiteAdapter pokeAttaqueAdapter = new PokeAttaqueSQLiteAdapter(this.ctx);
                    pokeAttaqueAdapter.open(this.getDb());
                    result = pokeAttaqueAdapter.query(attaque1Id);
                }
                break;

            case POKEPOKEMON_ATTAQUE2:
                pokePokemonCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokePokemonCursor.getCount() > 0) {
                    pokePokemonCursor.moveToFirst();
                    int attaque2Id = pokePokemonCursor.getInt(
                            pokePokemonCursor.getColumnIndex(
                                    PokePokemonContract.COL_ATTAQUE2_ID));

                    PokeAttaqueSQLiteAdapter pokeAttaqueAdapter = new PokeAttaqueSQLiteAdapter(this.ctx);
                    pokeAttaqueAdapter.open(this.getDb());
                    result = pokeAttaqueAdapter.query(attaque2Id);
                }
                break;

            case POKEPOKEMON_ATTAQUE3:
                pokePokemonCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokePokemonCursor.getCount() > 0) {
                    pokePokemonCursor.moveToFirst();
                    int attaque3Id = pokePokemonCursor.getInt(
                            pokePokemonCursor.getColumnIndex(
                                    PokePokemonContract.COL_ATTAQUE3_ID));

                    PokeAttaqueSQLiteAdapter pokeAttaqueAdapter = new PokeAttaqueSQLiteAdapter(this.ctx);
                    pokeAttaqueAdapter.open(this.getDb());
                    result = pokeAttaqueAdapter.query(attaque3Id);
                }
                break;

            case POKEPOKEMON_ATTAQUE4:
                pokePokemonCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokePokemonCursor.getCount() > 0) {
                    pokePokemonCursor.moveToFirst();
                    int attaque4Id = pokePokemonCursor.getInt(
                            pokePokemonCursor.getColumnIndex(
                                    PokePokemonContract.COL_ATTAQUE4_ID));

                    PokeAttaqueSQLiteAdapter pokeAttaqueAdapter = new PokeAttaqueSQLiteAdapter(this.ctx);
                    pokeAttaqueAdapter.open(this.getDb());
                    result = pokeAttaqueAdapter.query(attaque4Id);
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
            case POKEPOKEMON_ONE:
                selectionArgs = new String[1];
                selection = PokePokemonContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case POKEPOKEMON_ALL:
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
        return POKEPOKEMON_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = PokePokemonContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    PokePokemonContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

