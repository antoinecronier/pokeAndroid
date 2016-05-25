/**************************************************************************
 * PokeObjetCreateActivity.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokeobjet;

import com.antoinecronier.pokebattle.R;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * PokeObjet create Activity.
 *
 * This only contains a PokeObjetCreateFragment.
 *
 * @see android.app.Activity
 */
public class PokeObjetCreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_pokeobjet_create;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
