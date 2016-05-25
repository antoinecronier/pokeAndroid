/**************************************************************************
 * HomeActivity.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;
import com.antoinecronier.pokebattle.view.pokearene.PokeAreneListActivity;
import com.antoinecronier.pokebattle.view.pokebadge.PokeBadgeListActivity;
import com.antoinecronier.pokebattle.view.pokezone.PokeZoneListActivity;
import com.antoinecronier.pokebattle.view.poketype.PokeTypeListActivity;
import com.antoinecronier.pokebattle.view.pokeposition.PokePositionListActivity;
import com.antoinecronier.pokebattle.view.pokepokemon.PokePokemonListActivity;
import com.antoinecronier.pokebattle.view.pokeobjet.PokeObjetListActivity;
import com.antoinecronier.pokebattle.view.pokeattaque.PokeAttaqueListActivity;
import com.antoinecronier.pokebattle.view.pokedresseur.PokeDresseurListActivity;
import com.antoinecronier.pokebattle.view.poketypepokemon.PokeTypePokemonListActivity;
import com.antoinecronier.pokebattle.view.poketypeobjet.PokeTypeObjetListActivity;
import com.antoinecronier.pokebattle.view.pokenpc.PokeNpcListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Home Activity.
 * This is from where you can access to your entities activities by default.
 * BEWARE : This class is regenerated with orm:generate:crud. Don't modify it.
 * @see android.app.Activity
 */
public class HomeActivity extends HarmonyFragmentActivity 
        implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.initButtons();
    }
    
    @Override
    protected int getContentView() {
        return R.layout.main;
    }

    /**
     * Initialize the buttons click listeners.
     */
    private void initButtons() {
        this.findViewById(R.id.pokearene_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.pokebadge_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.pokezone_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.poketype_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.pokeposition_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.pokepokemon_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.pokeobjet_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.pokeattaque_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.pokedresseur_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.poketypepokemon_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.poketypeobjet_list_button)
                        .setOnClickListener(this);
        this.findViewById(R.id.pokenpc_list_button)
                        .setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pokearene_list_button:
                intent = new Intent(this,
                        PokeAreneListActivity.class);
                break;

            case R.id.pokebadge_list_button:
                intent = new Intent(this,
                        PokeBadgeListActivity.class);
                break;

            case R.id.pokezone_list_button:
                intent = new Intent(this,
                        PokeZoneListActivity.class);
                break;

            case R.id.poketype_list_button:
                intent = new Intent(this,
                        PokeTypeListActivity.class);
                break;

            case R.id.pokeposition_list_button:
                intent = new Intent(this,
                        PokePositionListActivity.class);
                break;

            case R.id.pokepokemon_list_button:
                intent = new Intent(this,
                        PokePokemonListActivity.class);
                break;

            case R.id.pokeobjet_list_button:
                intent = new Intent(this,
                        PokeObjetListActivity.class);
                break;

            case R.id.pokeattaque_list_button:
                intent = new Intent(this,
                        PokeAttaqueListActivity.class);
                break;

            case R.id.pokedresseur_list_button:
                intent = new Intent(this,
                        PokeDresseurListActivity.class);
                break;

            case R.id.poketypepokemon_list_button:
                intent = new Intent(this,
                        PokeTypePokemonListActivity.class);
                break;

            case R.id.poketypeobjet_list_button:
                intent = new Intent(this,
                        PokeTypeObjetListActivity.class);
                break;

            case R.id.pokenpc_list_button:
                intent = new Intent(this,
                        PokeNpcListActivity.class);
                break;

            default:
                intent = null;
                break;
        }

        if (intent != null) {
            this.startActivity(intent);
        }
    }
}
