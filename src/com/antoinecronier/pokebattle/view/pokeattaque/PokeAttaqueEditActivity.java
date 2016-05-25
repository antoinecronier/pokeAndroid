/**************************************************************************
 * PokeAttaqueEditActivity.java, pokebattle Android
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

import android.os.Bundle;

/** PokeAttaque edit Activity.
 *
 * This only contains a PokeAttaqueEditFragment.
 *
 * @see android.app.Activity
 */
public class PokeAttaqueEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_pokeattaque_edit;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
