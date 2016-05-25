/**************************************************************************
 * PokePokemonShowActivity.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokepokemon;

import com.antoinecronier.pokebattle.R;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;
import com.antoinecronier.pokebattle.view.pokepokemon.PokePokemonShowFragment.DeleteCallback;
import android.os.Bundle;

/** PokePokemon show Activity.
 *
 * This only contains a PokePokemonShowFragment.
 *
 * @see android.app.Activity
 */
public class PokePokemonShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_pokepokemon_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
