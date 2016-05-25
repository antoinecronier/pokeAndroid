/**************************************************************************
 * PokeAreneDataLoader.java, pokebattle Android
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




import com.antoinecronier.pokebattle.entity.PokeArene;


/**
 * PokeAreneDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PokeAreneDataLoader
                        extends FixtureBase<PokeArene> {
    /** PokeAreneDataLoader name. */
    private static final String FILE_NAME = "PokeArene";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for maitre. */
    private static final String MAITRE = "maitre";
    /** Constant field for dresseurs. */
    private static final String DRESSEURS = "dresseurs";
    /** Constant field for badge. */
    private static final String BADGE = "badge";
    /** Constant field for zone. */
    private static final String ZONE = "zone";
    /** Constant field for position. */
    private static final String POSITION = "position";


    /** PokeAreneDataLoader instance (Singleton). */
    private static PokeAreneDataLoader instance;

    /**
     * Get the PokeAreneDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PokeAreneDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PokeAreneDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PokeAreneDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected PokeArene extractItem(final Map<?, ?> columns) {
        final PokeArene pokeArene =
                new PokeArene();

        return this.extractItem(columns, pokeArene);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param pokeArene Entity to extract
     * @return A PokeArene entity
     */
    protected PokeArene extractItem(final Map<?, ?> columns,
                PokeArene pokeArene) {
        pokeArene.setId(this.parseIntField(columns, ID));
        pokeArene.setNom(this.parseField(columns, NOM, String.class));
        pokeArene.setMaitre(this.parseSimpleRelationField(columns, MAITRE, PokeNpcDataLoader.getInstance(this.ctx)));
        pokeArene.setDresseurs(this.parseMultiRelationField(columns, DRESSEURS, PokeNpcDataLoader.getInstance(this.ctx)));
        pokeArene.setBadge(this.parseSimpleRelationField(columns, BADGE, PokeBadgeDataLoader.getInstance(this.ctx)));
        pokeArene.setZone(this.parseSimpleRelationField(columns, ZONE, PokeZoneDataLoader.getInstance(this.ctx)));
        pokeArene.setPosition(this.parseSimpleRelationField(columns, POSITION, PokePositionDataLoader.getInstance(this.ctx)));

        return pokeArene;
    }
    /**
     * Loads PokeArenes into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final PokeArene pokeArene : this.items.values()) {
            int id = dataManager.persist(pokeArene);
            pokeArene.setId(id);

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
    protected PokeArene get(final String key) {
        final PokeArene result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
