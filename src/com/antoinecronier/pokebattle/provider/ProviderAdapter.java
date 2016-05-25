/**************************************************************************
 * ProviderAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.provider;

import com.antoinecronier.pokebattle.provider.base.ProviderAdapterBase;
import com.antoinecronier.pokebattle.provider.base.PokebattleProviderBase;
import com.antoinecronier.pokebattle.data.base.SQLiteAdapterBase;

/**
 * ProviderAdapter<T>. 
 *
 * Feel free to add your custom generic methods here.
 *
 * @param <T> must extends Serializable
 */
public abstract class ProviderAdapter<T> extends ProviderAdapterBase<T> {

    /**
     * Provider Adapter Base constructor.
     *
     * @param context The context.
     */
    public ProviderAdapter(
                final PokebattleProviderBase provider,
                final SQLiteAdapterBase<T> adapter) {
        super(provider, adapter);
    }
}
