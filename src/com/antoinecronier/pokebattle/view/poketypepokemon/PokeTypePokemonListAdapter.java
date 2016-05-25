/**************************************************************************
 * PokeTypePokemonListAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.poketypepokemon;


import com.antoinecronier.pokebattle.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antoinecronier.pokebattle.entity.PokeTypePokemon;
import com.antoinecronier.pokebattle.harmony.view.HarmonyCursorAdapter;
import com.antoinecronier.pokebattle.harmony.view.HarmonyViewHolder;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;

/**
 * List adapter for PokeTypePokemon entity.
 */
public class PokeTypePokemonListAdapter extends HarmonyCursorAdapter<PokeTypePokemon> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PokeTypePokemonListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PokeTypePokemonListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected PokeTypePokemon cursorToItem(Cursor cursor) {
        return PokeTypePokemonContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PokeTypePokemonContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<PokeTypePokemon> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<PokeTypePokemon> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_poketypepokemon);
        }

        /**
         * Populate row with a {@link PokeTypePokemon}.
         *
         * @param model {@link PokeTypePokemon} data
         */
        public void populate(final PokeTypePokemon model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_poketypepokemon_nom);
                    
            TextView attaqueView = (TextView) this.getView().findViewById(
                    R.id.row_poketypepokemon_attaque);
                    
            TextView attaque_speView = (TextView) this.getView().findViewById(
                    R.id.row_poketypepokemon_attaque_spe);
                    
            TextView defenceView = (TextView) this.getView().findViewById(
                    R.id.row_poketypepokemon_defence);
                    
            TextView defence_speView = (TextView) this.getView().findViewById(
                    R.id.row_poketypepokemon_defence_spe);
                    
            TextView vitesseView = (TextView) this.getView().findViewById(
                    R.id.row_poketypepokemon_vitesse);
                    
            TextView pvView = (TextView) this.getView().findViewById(
                    R.id.row_poketypepokemon_pv);
                    
            TextView pokedexView = (TextView) this.getView().findViewById(
                    R.id.row_poketypepokemon_pokedex);
                    
            TextView evolueView = (TextView) this.getView().findViewById(
                    R.id.row_poketypepokemon_evolue);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            attaqueView.setText(String.valueOf(model.getAttaque()));
            attaque_speView.setText(String.valueOf(model.getAttaque_spe()));
            defenceView.setText(String.valueOf(model.getDefence()));
            defence_speView.setText(String.valueOf(model.getDefence_spe()));
            vitesseView.setText(String.valueOf(model.getVitesse()));
            pvView.setText(String.valueOf(model.getPv()));
            pokedexView.setText(String.valueOf(model.getPokedex()));
            if (model.getEvolue() != null) {
                evolueView.setText(
                        String.valueOf(model.getEvolue().getId()));
            }
        }
    }
}
