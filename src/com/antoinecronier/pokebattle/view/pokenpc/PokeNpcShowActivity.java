/**************************************************************************
 * PokeNpcShowActivity.java, pokebattle Android
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
import com.antoinecronier.pokebattle.view.pokenpc.PokeNpcShowFragment.DeleteCallback;
import android.os.Bundle;

/** PokeNpc show Activity.
 *
 * This only contains a PokeNpcShowFragment.
 *
 * @see android.app.Activity
 */
public class PokeNpcShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_pokenpc_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
