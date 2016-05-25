/**************************************************************************
 * PokeTypeObjetEditActivity.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.poketypeobjet;

import com.antoinecronier.pokebattle.R;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** PokeTypeObjet edit Activity.
 *
 * This only contains a PokeTypeObjetEditFragment.
 *
 * @see android.app.Activity
 */
public class PokeTypeObjetEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_poketypeobjet_edit;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
