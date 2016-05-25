/**************************************************************************
 * PokeTypeObjetShowActivity.java, pokebattle Android
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
import com.antoinecronier.pokebattle.view.poketypeobjet.PokeTypeObjetShowFragment.DeleteCallback;
import android.os.Bundle;

/** PokeTypeObjet show Activity.
 *
 * This only contains a PokeTypeObjetShowFragment.
 *
 * @see android.app.Activity
 */
public class PokeTypeObjetShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_poketypeobjet_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
