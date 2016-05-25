/**************************************************************************
 * PokePositionShowActivity.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokeposition;

import com.antoinecronier.pokebattle.R;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;
import com.antoinecronier.pokebattle.view.pokeposition.PokePositionShowFragment.DeleteCallback;
import android.os.Bundle;

/** PokePosition show Activity.
 *
 * This only contains a PokePositionShowFragment.
 *
 * @see android.app.Activity
 */
public class PokePositionShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_pokeposition_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
