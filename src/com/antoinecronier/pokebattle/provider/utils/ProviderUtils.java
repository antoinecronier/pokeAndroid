/**************************************************************************
 * ProviderUtils.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.provider.utils;

import com.antoinecronier.pokebattle.provider.utils.base.ProviderUtilsBase;



/**
 * Generic Proxy class for the provider calls.
 *
 * Feel free to modify it and your own generic methods in it.
 *
 * @param <T>     The entity type
 */
public abstract class ProviderUtils<T> extends ProviderUtilsBase<T> {

    /**
     * Constructor.
     * @param context android.content.Context
     */
    public ProviderUtils(android.content.Context context) {
        super(context);
    }
}

