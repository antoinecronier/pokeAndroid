/**************************************************************************
 * PokeNpcSQLiteAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.data;

import com.antoinecronier.pokebattle.data.base.PokeNpcSQLiteAdapterBase;


/**
 * PokeNpc adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class PokeNpcSQLiteAdapter extends PokeNpcSQLiteAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public PokeNpcSQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }
}
