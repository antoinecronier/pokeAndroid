/**************************************************************************
 * SQLiteAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.data;



import com.antoinecronier.pokebattle.data.base.SQLiteAdapterBase;

/**
 * This is the SQLiteAdapter.
 *
 * Feel free to add any generic custom method in here.
 *
 * This is the base class for all basic operations for your sqlite adapters.
 *
 * @param <T> Entity type of this adapter.
 */
public abstract class SQLiteAdapter<T> extends SQLiteAdapterBase<T> {

    /**
     * Constructor.
     * @param ctx context
     */
    protected SQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }
}
