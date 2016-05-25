/**************************************************************************
 * PokePositionDataLoader.java, pokebattle Android
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




import com.antoinecronier.pokebattle.entity.PokePosition;


/**
 * PokePositionDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PokePositionDataLoader
                        extends FixtureBase<PokePosition> {
    /** PokePositionDataLoader name. */
    private static final String FILE_NAME = "PokePosition";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for x. */
    private static final String X = "x";
    /** Constant field for y. */
    private static final String Y = "y";


    /** PokePositionDataLoader instance (Singleton). */
    private static PokePositionDataLoader instance;

    /**
     * Get the PokePositionDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PokePositionDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PokePositionDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PokePositionDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected PokePosition extractItem(final Map<?, ?> columns) {
        final PokePosition pokePosition =
                new PokePosition();

        return this.extractItem(columns, pokePosition);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param pokePosition Entity to extract
     * @return A PokePosition entity
     */
    protected PokePosition extractItem(final Map<?, ?> columns,
                PokePosition pokePosition) {
        pokePosition.setId(this.parseIntField(columns, ID));
        pokePosition.setX(this.parseIntField(columns, X));
        pokePosition.setY(this.parseIntField(columns, Y));

        return pokePosition;
    }
    /**
     * Loads PokePositions into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final PokePosition pokePosition : this.items.values()) {
            int id = dataManager.persist(pokePosition);
            pokePosition.setId(id);

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
    protected PokePosition get(final String key) {
        final PokePosition result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
