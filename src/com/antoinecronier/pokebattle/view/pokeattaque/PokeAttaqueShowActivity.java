/**************************************************************************
 * PokeAttaqueShowActivity.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokeattaque;

import com.antoinecronier.pokebattle.R;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;
import com.antoinecronier.pokebattle.view.pokeattaque.PokeAttaqueShowFragment.DeleteCallback;
import android.os.Bundle;

/** PokeAttaque show Activity.
 *
 * This only contains a PokeAttaqueShowFragment.
 *
 * @see android.app.Activity
 */
public class PokeAttaqueShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_pokeattaque_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
