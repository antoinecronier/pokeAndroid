/**************************************************************************
 * PokebattleSQLiteOpenHelper.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.data;

import com.antoinecronier.pokebattle.data.base.PokebattleSQLiteOpenHelperBase;

import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * This class makes it easy for ContentProvider implementations to defer <br />
 * opening and upgrading the database until first use, to avoid blocking <br />
 * application startup with long-running database upgrades.
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class PokebattleSQLiteOpenHelper
                    extends PokebattleSQLiteOpenHelperBase {

    /**
     * Constructor.
     * @param ctx context
     * @param name name
     * @param factory factory
     * @param version version
     */
    public PokebattleSQLiteOpenHelper(final android.content.Context ctx,
           final String name, final CursorFactory factory, final int version) {
        super(ctx, name, factory, version);
    }

}
