/**************************************************************************
 * PokeDresseurDataLoader.java, pokebattle Android
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




import com.antoinecronier.pokebattle.entity.PokeDresseur;


/**
 * PokeDresseurDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PokeDresseurDataLoader
                        extends FixtureBase<PokeDresseur> {
    /** PokeDresseurDataLoader name. */
    private static final String FILE_NAME = "PokeDresseur";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for pseudo. */
    private static final String PSEUDO = "pseudo";
    /** Constant field for login. */
    private static final String LOGIN = "login";
    /** Constant field for password. */
    private static final String PASSWORD = "password";
    /** Constant field for npcs. */
    private static final String NPCS = "npcs";


    /** PokeDresseurDataLoader instance (Singleton). */
    private static PokeDresseurDataLoader instance;

    /**
     * Get the PokeDresseurDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PokeDresseurDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PokeDresseurDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PokeDresseurDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected PokeDresseur extractItem(final Map<?, ?> columns) {
        final PokeDresseur pokeDresseur =
                new PokeDresseur();

        return this.extractItem(columns, pokeDresseur);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param pokeDresseur Entity to extract
     * @return A PokeDresseur entity
     */
    protected PokeDresseur extractItem(final Map<?, ?> columns,
                PokeDresseur pokeDresseur) {
        pokeDresseur.setId(this.parseIntField(columns, ID));
        pokeDresseur.setPseudo(this.parseField(columns, PSEUDO, String.class));
        pokeDresseur.setLogin(this.parseField(columns, LOGIN, String.class));
        pokeDresseur.setPassword(this.parseField(columns, PASSWORD, String.class));
        pokeDresseur.setNpcs(this.parseMultiRelationField(columns, NPCS, PokeNpcDataLoader.getInstance(this.ctx)));

        return pokeDresseur;
    }
    /**
     * Loads PokeDresseurs into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final PokeDresseur pokeDresseur : this.items.values()) {
            int id = dataManager.persist(pokeDresseur);
            pokeDresseur.setId(id);

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
    protected PokeDresseur get(final String key) {
        final PokeDresseur result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
