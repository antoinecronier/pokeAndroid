/**************************************************************************
 * PokeNpcCreateActivity.java, pokebattle Android
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

/** 
 * PokeNpc create Activity.
 *
 * This only contains a PokeNpcCreateFragment.
 *
 * @see android.app.Activity
 */
public class PokeNpcCreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_pokenpc_create;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
