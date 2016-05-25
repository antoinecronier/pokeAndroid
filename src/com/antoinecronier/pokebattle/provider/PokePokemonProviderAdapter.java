/**************************************************************************
 * PokePokemonProviderAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.provider;

import com.antoinecronier.pokebattle.provider.base.PokePokemonProviderAdapterBase;
import com.antoinecronier.pokebattle.provider.base.PokebattleProviderBase;

/**
 * PokePokemonProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class PokePokemonProviderAdapter
                    extends PokePokemonProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public PokePokemonProviderAdapter(
            final PokebattleProviderBase provider) {
        super(provider);
    }
}

