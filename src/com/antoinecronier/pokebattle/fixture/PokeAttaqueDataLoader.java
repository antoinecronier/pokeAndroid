/**************************************************************************
 * PokeAttaqueDataLoader.java, pokebattle Android
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




import com.antoinecronier.pokebattle.entity.PokeAttaque;


/**
 * PokeAttaqueDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PokeAttaqueDataLoader
                        extends FixtureBase<PokeAttaque> {
    /** PokeAttaqueDataLoader name. */
    private static final String FILE_NAME = "PokeAttaque";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for puissance. */
    private static final String PUISSANCE = "puissance";
    /** Constant field for precision. */
    private static final String PRECISION = "precision";
    /** Constant field for type. */
    private static final String TYPE = "type";


    /** PokeAttaqueDataLoader instance (Singleton). */
    private static PokeAttaqueDataLoader instance;

    /**
     * Get the PokeAttaqueDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PokeAttaqueDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PokeAttaqueDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PokeAttaqueDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected PokeAttaque extractItem(final Map<?, ?> columns) {
        final PokeAttaque pokeAttaque =
                new PokeAttaque();

        return this.extractItem(columns, pokeAttaque);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param pokeAttaque Entity to extract
     * @return A PokeAttaque entity
     */
    protected PokeAttaque extractItem(final Map<?, ?> columns,
                PokeAttaque pokeAttaque) {
        pokeAttaque.setId(this.parseIntField(columns, ID));
        pokeAttaque.setNom(this.parseField(columns, NOM, String.class));
        pokeAttaque.setPuissance(this.parseIntField(columns, PUISSANCE));
        pokeAttaque.setPrecision(this.parseIntField(columns, PRECISION));
        pokeAttaque.setType(this.parseSimpleRelationField(columns, TYPE, PokeTypeDataLoader.getInstance(this.ctx)));

        return pokeAttaque;
    }
    /**
     * Loads PokeAttaques into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final PokeAttaque pokeAttaque : this.items.values()) {
            int id = dataManager.persist(pokeAttaque);
            pokeAttaque.setId(id);

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
    protected PokeAttaque get(final String key) {
        final PokeAttaque result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
