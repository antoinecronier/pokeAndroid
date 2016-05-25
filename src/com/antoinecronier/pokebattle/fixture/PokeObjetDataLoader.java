/**************************************************************************
 * PokeObjetDataLoader.java, pokebattle Android
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



import com.antoinecronier.pokebattle.entity.PokeObjet;


/**
 * PokeObjetDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PokeObjetDataLoader
                        extends FixtureBase<PokeObjet> {
    /** PokeObjetDataLoader name. */
    private static final String FILE_NAME = "PokeObjet";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for quantity. */
    private static final String QUANTITY = "quantity";
    /** Constant field for type. */
    private static final String TYPE = "type";


    /** PokeObjetDataLoader instance (Singleton). */
    private static PokeObjetDataLoader instance;

    /**
     * Get the PokeObjetDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PokeObjetDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PokeObjetDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PokeObjetDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected PokeObjet extractItem(final Map<?, ?> columns) {
        final PokeObjet pokeObjet =
                new PokeObjet();

        return this.extractItem(columns, pokeObjet);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param pokeObjet Entity to extract
     * @return A PokeObjet entity
     */
    protected PokeObjet extractItem(final Map<?, ?> columns,
                PokeObjet pokeObjet) {
        pokeObjet.setId(this.parseIntField(columns, ID));
        pokeObjet.setNom(this.parseField(columns, NOM, String.class));
        pokeObjet.setQuantity(this.parseIntField(columns, QUANTITY));
        pokeObjet.setType(this.parseSimpleRelationField(columns, TYPE, PokeTypeObjetDataLoader.getInstance(this.ctx)));

        return pokeObjet;
    }
    /**
     * Loads PokeObjets into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final PokeObjet pokeObjet : this.items.values()) {
            int id = dataManager.persist(pokeObjet);
            pokeObjet.setId(id);

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
    protected PokeObjet get(final String key) {
        final PokeObjet result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
