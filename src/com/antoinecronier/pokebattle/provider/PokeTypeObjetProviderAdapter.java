/**************************************************************************
 * PokeTypeObjetProviderAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.provider;

import com.antoinecronier.pokebattle.provider.base.PokeTypeObjetProviderAdapterBase;
import com.antoinecronier.pokebattle.provider.base.PokebattleProviderBase;

/**
 * PokeTypeObjetProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class PokeTypeObjetProviderAdapter
                    extends PokeTypeObjetProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public PokeTypeObjetProviderAdapter(
            final PokebattleProviderBase provider) {
        super(provider);
    }
}

