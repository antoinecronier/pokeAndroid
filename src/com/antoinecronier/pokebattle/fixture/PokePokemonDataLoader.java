/**************************************************************************
 * PokePokemonDataLoader.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.fixture;

import java.util.Map;
import java.util.ArrayList;



import com.antoinecronier.pokebattle.entity.PokePokemon;


/**
 * PokePokemonDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PokePokemonDataLoader
                        extends FixtureBase<PokePokemon> {
    /** PokePokemonDataLoader name. */
    private static final String FILE_NAME = "PokePokemon";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for surnom. */
    private static final String SURNOM = "surnom";
    /** Constant field for niveau. */
    private static final String NIVEAU = "niveau";
    /** Constant field for capture. */
    private static final String CAPTURE = "capture";
    /** Constant field for type. */
    private static final String TYPE = "type";
    /** Constant field for attaque1. */
    private static final String ATTAQUE1 = "attaque1";
    /** Constant field for attaque2. */
    private static final String ATTAQUE2 = "attaque2";
    /** Constant field for attaque3. */
    private static final String ATTAQUE3 = "attaque3";
    /** Constant field for attaque4. */
    private static final String ATTAQUE4 = "attaque4";


    /** PokePokemonDataLoader instance (Singleton). */
    private static PokePokemonDataLoader instance;

    /**
     * Get the PokePokemonDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PokePokemonDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PokePokemonDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PokePokemonDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected PokePokemon extractItem(final Map<?, ?> columns) {
        final PokePokemon pokePokemon =
                new PokePokemon();

        return this.extractItem(columns, pokePokemon);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param pokePokemon Entity to extract
     * @return A PokePokemon entity
     */
    protected PokePokemon extractItem(final Map<?, ?> columns,
                PokePokemon pokePokemon) {
        pokePokemon.setId(this.parseIntField(columns, ID));
        pokePokemon.setSurnom(this.parseField(columns, SURNOM, String.class));
        pokePokemon.setNiveau(this.parseIntField(columns, NIVEAU));
        pokePokemon.setCapture(this.parseDateTimeField(columns, CAPTURE));
        pokePokemon.setType(this.parseSimpleRelationField(columns, TYPE, PokeTypePokemonDataLoader.getInstance(this.ctx)));
        pokePokemon.setAttaque1(this.parseSimpleRelationField(columns, ATTAQUE1, PokeAttaqueDataLoader.getInstance(this.ctx)));
        pokePokemon.setAttaque2(this.parseSimpleRelationField(columns, ATTAQUE2, PokeAttaqueDataLoader.getInstance(this.ctx)));
        pokePokemon.setAttaque3(this.parseSimpleRelationField(columns, ATTAQUE3, PokeAttaqueDataLoader.getInstance(this.ctx)));
        pokePokemon.setAttaque4(this.parseSimpleRelationField(columns, ATTAQUE4, PokeAttaqueDataLoader.getInstance(this.ctx)));

        return pokePokemon;
    }
    /**
     * Loads PokePokemons into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final PokePokemon pokePokemon : this.items.values()) {
            int id = dataManager.persist(pokePokemon);
            pokePokemon.setId(id);

        }
        dataManager.flush();
    }

    /**
     * Give priority for fixtures insertion in database.
     * 0 is the first.
     * @return The order
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * Get the fixture file name.
     * @return A String representing the file name
     */
    @Override
    public String getFixtureFileName() {
        return FILE_NAME;
    }

    @Override
    protected PokePokemon get(final String key) {
        final PokePokemon result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
