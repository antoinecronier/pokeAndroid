/**************************************************************************
 * PokeTypeDataLoader.java, pokebattle Android
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



import com.antoinecronier.pokebattle.entity.PokeType;


/**
 * PokeTypeDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PokeTypeDataLoader
                        extends FixtureBase<PokeType> {
    /** PokeTypeDataLoader name. */
    private static final String FILE_NAME = "PokeType";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for modificateur. */
    private static final String MODIFICATEUR = "modificateur";
    /** Constant field for typeFort. */
    private static final String TYPEFORT = "typeFort";
    /** Constant field for typeFaible. */
    private static final String TYPEFAIBLE = "typeFaible";


    /** PokeTypeDataLoader instance (Singleton). */
    private static PokeTypeDataLoader instance;

    /**
     * Get the PokeTypeDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PokeTypeDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PokeTypeDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PokeTypeDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected PokeType extractItem(final Map<?, ?> columns) {
        final PokeType pokeType =
                new PokeType();

        return this.extractItem(columns, pokeType);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param pokeType Entity to extract
     * @return A PokeType entity
     */
    protected PokeType extractItem(final Map<?, ?> columns,
                PokeType pokeType) {
        pokeType.setId(this.parseIntField(columns, ID));
        pokeType.setNom(this.parseField(columns, NOM, String.class));
        pokeType.setModificateur(this.parseIntField(columns, MODIFICATEUR));
        pokeType.setTypeFort(this.parseMultiRelationField(columns, TYPEFORT, PokeTypeDataLoader.getInstance(this.ctx)));
        pokeType.setTypeFaible(this.parseMultiRelationField(columns, TYPEFAIBLE, PokeTypeDataLoader.getInstance(this.ctx)));

        return pokeType;
    }
    /**
     * Loads PokeTypes into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final PokeType pokeType : this.items.values()) {
            int id = dataManager.persist(pokeType);
            pokeType.setId(id);

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
    protected PokeType get(final String key) {
        final PokeType result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
