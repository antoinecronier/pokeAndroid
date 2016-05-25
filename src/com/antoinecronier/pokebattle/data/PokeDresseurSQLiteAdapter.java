/**************************************************************************
 * PokeDresseurSQLiteAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.data;

import com.antoinecronier.pokebattle.data.base.PokeDresseurSQLiteAdapterBase;


/**
 * PokeDresseur adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class PokeDresseurSQLiteAdapter extends PokeDresseurSQLiteAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public PokeDresseurSQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }
}
