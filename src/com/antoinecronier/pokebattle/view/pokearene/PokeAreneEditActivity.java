/**************************************************************************
 * PokeAreneEditActivity.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokearene;

import com.antoinecronier.pokebattle.R;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** PokeArene edit Activity.
 *
 * This only contains a PokeAreneEditFragment.
 *
 * @see android.app.Activity
 */
public class PokeAreneEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_pokearene_edit;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
