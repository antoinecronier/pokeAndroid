/**************************************************************************
 * PokeTypePokemonDataLoader.java, pokebattle Android
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




import com.antoinecronier.pokebattle.entity.PokeTypePokemon;


/**
 * PokeTypePokemonDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PokeTypePokemonDataLoader
                        extends FixtureBase<PokeTypePokemon> {
    /** PokeTypePokemonDataLoader name. */
    private static final String FILE_NAME = "PokeTypePokemon";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for attaque. */
    private static final String ATTAQUE = "attaque";
    /** Constant field for attaque_spe. */
    private static final String ATTAQUE_SPE = "attaque_spe";
    /** Constant field for defence. */
    private static final String DEFENCE = "defence";
    /** Constant field for defence_spe. */
    private static final String DEFENCE_SPE = "defence_spe";
    /** Constant field for vitesse. */
    private static final String VITESSE = "vitesse";
    /** Constant field for pv. */
    private static final String PV = "pv";
    /** Constant field for pokedex. */
    private static final String POKEDEX = "pokedex";
    /** Constant field for evolue. */
    private static final String EVOLUE = "evolue";
    /** Constant field for types. */
    private static final String TYPES = "types";
    /** Constant field for zones. */
    private static final String ZONES = "zones";


    /** PokeTypePokemonDataLoader instance (Singleton). */
    private static PokeTypePokemonDataLoader instance;

    /**
     * Get the PokeTypePokemonDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PokeTypePokemonDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PokeTypePokemonDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PokeTypePokemonDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected PokeTypePokemon extractItem(final Map<?, ?> columns) {
        final PokeTypePokemon pokeTypePokemon =
                new PokeTypePokemon();

        return this.extractItem(columns, pokeTypePokemon);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param pokeTypePokemon Entity to extract
     * @return A PokeTypePokemon entity
     */
    protected PokeTypePokemon extractItem(final Map<?, ?> columns,
                PokeTypePokemon pokeTypePokemon) {
        pokeTypePokemon.setId(this.parseIntField(columns, ID));
        pokeTypePokemon.setNom(this.parseField(columns, NOM, String.class));
        pokeTypePokemon.setAttaque(this.parseIntField(columns, ATTAQUE));
        pokeTypePokemon.setAttaque_spe(this.parseIntField(columns, ATTAQUE_SPE));
        pokeTypePokemon.setDefence(this.parseIntField(columns, DEFENCE));
        pokeTypePokemon.setDefence_spe(this.parseIntField(columns, DEFENCE_SPE));
        pokeTypePokemon.setVitesse(this.parseIntField(columns, VITESSE));
        pokeTypePokemon.setPv(this.parseIntField(columns, PV));
        pokeTypePokemon.setPokedex(this.parseIntField(columns, POKEDEX));
        pokeTypePokemon.setEvolue(this.parseSimpleRelationField(columns, EVOLUE, PokeTypePokemonDataLoader.getInstance(this.ctx)));
        pokeTypePokemon.setTypes(this.parseMultiRelationField(columns, TYPES, PokeTypeDataLoader.getInstance(this.ctx)));
        pokeTypePokemon.setZones(this.parseMultiRelationField(columns, ZONES, PokeZoneDataLoader.getInstance(this.ctx)));

        return pokeTypePokemon;
    }
    /**
     * Loads PokeTypePokemons into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final PokeTypePokemon pokeTypePokemon : this.items.values()) {
            int id = dataManager.persist(pokeTypePokemon);
            pokeTypePokemon.setId(id);

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
    protected PokeTypePokemon get(final String key) {
        final PokeTypePokemon result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
