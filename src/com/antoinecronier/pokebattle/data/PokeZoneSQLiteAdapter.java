/**************************************************************************
 * PokeZoneSQLiteAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.data;

import com.antoinecronier.pokebattle.data.base.PokeZoneSQLiteAdapterBase;


/**
 * PokeZone adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class PokeZoneSQLiteAdapter extends PokeZoneSQLiteAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public PokeZoneSQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }
}
