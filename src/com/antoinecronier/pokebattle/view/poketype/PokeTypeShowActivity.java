/**************************************************************************
 * PokeTypeShowActivity.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.poketype;

import com.antoinecronier.pokebattle.R;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;
import com.antoinecronier.pokebattle.view.poketype.PokeTypeShowFragment.DeleteCallback;
import android.os.Bundle;

/** PokeType show Activity.
 *
 * This only contains a PokeTypeShowFragment.
 *
 * @see android.app.Activity
 */
public class PokeTypeShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_poketype_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
