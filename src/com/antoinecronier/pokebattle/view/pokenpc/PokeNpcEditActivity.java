/**************************************************************************
 * PokeNpcEditActivity.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokenpc;

import com.antoinecronier.pokebattle.R;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** PokeNpc edit Activity.
 *
 * This only contains a PokeNpcEditFragment.
 *
 * @see android.app.Activity
 */
public class PokeNpcEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_pokenpc_edit;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
