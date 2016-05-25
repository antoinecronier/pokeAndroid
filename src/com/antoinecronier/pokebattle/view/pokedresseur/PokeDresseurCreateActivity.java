/**************************************************************************
 * PokeDresseurCreateActivity.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokedresseur;

import com.antoinecronier.pokebattle.R;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * PokeDresseur create Activity.
 *
 * This only contains a PokeDresseurCreateFragment.
 *
 * @see android.app.Activity
 */
public class PokeDresseurCreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_pokedresseur_create;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
