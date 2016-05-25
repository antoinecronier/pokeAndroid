/**************************************************************************
 * PokeZoneDataLoader.java, pokebattle Android
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



import com.antoinecronier.pokebattle.entity.PokeZone;


/**
 * PokeZoneDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PokeZoneDataLoader
                        extends FixtureBase<PokeZone> {
    /** PokeZoneDataLoader name. */
    private static final String FILE_NAME = "PokeZone";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";


    /** PokeZoneDataLoader instance (Singleton). */
    private static PokeZoneDataLoader instance;

    /**
     * Get the PokeZoneDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PokeZoneDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PokeZoneDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PokeZoneDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected PokeZone extractItem(final Map<?, ?> columns) {
        final PokeZone pokeZone =
                new PokeZone();

        return this.extractItem(columns, pokeZone);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param pokeZone Entity to extract
     * @return A PokeZone entity
     */
    protected PokeZone extractItem(final Map<?, ?> columns,
                PokeZone pokeZone) {
        pokeZone.setId(this.parseIntField(columns, ID));
        pokeZone.setNom(this.parseField(columns, NOM, String.class));

        return pokeZone;
    }
    /**
     * Loads PokeZones into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final PokeZone pokeZone : this.items.values()) {
            int id = dataManager.persist(pokeZone);
            pokeZone.setId(id);

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
    protected PokeZone get(final String key) {
        final PokeZone result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
