/**************************************************************************
 * PokeTypeObjetDataLoader.java, pokebattle Android
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




import com.antoinecronier.pokebattle.entity.PokeTypeObjet;


/**
 * PokeTypeObjetDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PokeTypeObjetDataLoader
                        extends FixtureBase<PokeTypeObjet> {
    /** PokeTypeObjetDataLoader name. */
    private static final String FILE_NAME = "PokeTypeObjet";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";


    /** PokeTypeObjetDataLoader instance (Singleton). */
    private static PokeTypeObjetDataLoader instance;

    /**
     * Get the PokeTypeObjetDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PokeTypeObjetDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PokeTypeObjetDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PokeTypeObjetDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected PokeTypeObjet extractItem(final Map<?, ?> columns) {
        final PokeTypeObjet pokeTypeObjet =
                new PokeTypeObjet();

        return this.extractItem(columns, pokeTypeObjet);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param pokeTypeObjet Entity to extract
     * @return A PokeTypeObjet entity
     */
    protected PokeTypeObjet extractItem(final Map<?, ?> columns,
                PokeTypeObjet pokeTypeObjet) {
        pokeTypeObjet.setId(this.parseIntField(columns, ID));
        pokeTypeObjet.setNom(this.parseField(columns, NOM, String.class));

        return pokeTypeObjet;
    }
    /**
     * Loads PokeTypeObjets into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final PokeTypeObjet pokeTypeObjet : this.items.values()) {
            int id = dataManager.persist(pokeTypeObjet);
            pokeTypeObjet.setId(id);

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
    protected PokeTypeObjet get(final String key) {
        final PokeTypeObjet result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
