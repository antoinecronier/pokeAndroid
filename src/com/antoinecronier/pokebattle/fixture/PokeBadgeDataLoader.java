/**************************************************************************
 * PokeBadgeDataLoader.java, pokebattle Android
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



import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.entity.PokeBadgeBonus;


/**
 * PokeBadgeDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PokeBadgeDataLoader
                        extends FixtureBase<PokeBadge> {
    /** PokeBadgeDataLoader name. */
    private static final String FILE_NAME = "PokeBadge";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for bonus. */
    private static final String BONUS = "bonus";


    /** PokeBadgeDataLoader instance (Singleton). */
    private static PokeBadgeDataLoader instance;

    /**
     * Get the PokeBadgeDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PokeBadgeDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PokeBadgeDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PokeBadgeDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected PokeBadge extractItem(final Map<?, ?> columns) {
        final PokeBadge pokeBadge =
                new PokeBadge();

        return this.extractItem(columns, pokeBadge);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param pokeBadge Entity to extract
     * @return A PokeBadge entity
     */
    protected PokeBadge extractItem(final Map<?, ?> columns,
                PokeBadge pokeBadge) {
        pokeBadge.setId(this.parseIntField(columns, ID));
        pokeBadge.setNom(this.parseField(columns, NOM, String.class));
        pokeBadge.setBonus(PokeBadgeBonus.valueOf(this.parseField(columns, BONUS, String.class)));                        

        return pokeBadge;
    }
    /**
     * Loads PokeBadges into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final PokeBadge pokeBadge : this.items.values()) {
            int id = dataManager.persist(pokeBadge);
            pokeBadge.setId(id);

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
    protected PokeBadge get(final String key) {
        final PokeBadge result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
