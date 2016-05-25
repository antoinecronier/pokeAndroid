/**************************************************************************
 * PokeNpcDataLoader.java, pokebattle Android
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



import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.entity.PokeProfession;


/**
 * PokeNpcDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PokeNpcDataLoader
                        extends FixtureBase<PokeNpc> {
    /** PokeNpcDataLoader name. */
    private static final String FILE_NAME = "PokeNpc";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for profession. */
    private static final String PROFESSION = "profession";
    /** Constant field for description. */
    private static final String DESCRIPTION = "description";
    /** Constant field for objets. */
    private static final String OBJETS = "objets";
    /** Constant field for badge. */
    private static final String BADGE = "badge";
    /** Constant field for pokemons. */
    private static final String POKEMONS = "pokemons";
    /** Constant field for team. */
    private static final String TEAM = "team";
    /** Constant field for position. */
    private static final String POSITION = "position";
    /** Constant field for zone. */
    private static final String ZONE = "zone";


    /** PokeNpcDataLoader instance (Singleton). */
    private static PokeNpcDataLoader instance;

    /**
     * Get the PokeNpcDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PokeNpcDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PokeNpcDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PokeNpcDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected PokeNpc extractItem(final Map<?, ?> columns) {
        final PokeNpc pokeNpc =
                new PokeNpc();

        return this.extractItem(columns, pokeNpc);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param pokeNpc Entity to extract
     * @return A PokeNpc entity
     */
    protected PokeNpc extractItem(final Map<?, ?> columns,
                PokeNpc pokeNpc) {
        pokeNpc.setId(this.parseIntField(columns, ID));
        pokeNpc.setNom(this.parseField(columns, NOM, String.class));
        pokeNpc.setProfession(PokeProfession.valueOf(this.parseField(columns, PROFESSION, String.class)));                        
        pokeNpc.setDescription(this.parseField(columns, DESCRIPTION, String.class));
        pokeNpc.setObjets(this.parseMultiRelationField(columns, OBJETS, PokeObjetDataLoader.getInstance(this.ctx)));
        pokeNpc.setBadge(this.parseMultiRelationField(columns, BADGE, PokeBadgeDataLoader.getInstance(this.ctx)));
        pokeNpc.setPokemons(this.parseMultiRelationField(columns, POKEMONS, PokePokemonDataLoader.getInstance(this.ctx)));
        pokeNpc.setTeam(this.parseMultiRelationField(columns, TEAM, PokePokemonDataLoader.getInstance(this.ctx)));
        pokeNpc.setPosition(this.parseSimpleRelationField(columns, POSITION, PokePositionDataLoader.getInstance(this.ctx)));
        pokeNpc.setZone(this.parseSimpleRelationField(columns, ZONE, PokeZoneDataLoader.getInstance(this.ctx)));

        return pokeNpc;
    }
    /**
     * Loads PokeNpcs into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final PokeNpc pokeNpc : this.items.values()) {
            int id = dataManager.persist(pokeNpc);
            pokeNpc.setId(id);

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
    protected PokeNpc get(final String key) {
        final PokeNpc result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
