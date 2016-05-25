/**************************************************************************
 * PokeAreneProviderAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.provider;

import com.antoinecronier.pokebattle.provider.base.PokeAreneProviderAdapterBase;
import com.antoinecronier.pokebattle.provider.base.PokebattleProviderBase;

/**
 * PokeAreneProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class PokeAreneProviderAdapter
                    extends PokeAreneProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public PokeAreneProviderAdapter(
            final PokebattleProviderBase provider) {
        super(provider);
    }
}

