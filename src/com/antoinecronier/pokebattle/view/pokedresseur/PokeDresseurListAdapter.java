/**************************************************************************
 * PokeDresseurListAdapter.java, pokebattle Android
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

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antoinecronier.pokebattle.entity.PokeDresseur;
import com.antoinecronier.pokebattle.harmony.view.HarmonyCursorAdapter;
import com.antoinecronier.pokebattle.harmony.view.HarmonyViewHolder;
import com.antoinecronier.pokebattle.provider.contract.PokeDresseurContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;

/**
 * List adapter for PokeDresseur entity.
 */
public class PokeDresseurListAdapter extends HarmonyCursorAdapter<PokeDresseur> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PokeDresseurListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PokeDresseurListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected PokeDresseur cursorToItem(Cursor cursor) {
        return PokeDresseurContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PokeDresseurContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<PokeDresseur> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<PokeDresseur> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_pokedresseur);
        }

        /**
         * Populate row with a {@link PokeDresseur}.
         *
         * @param model {@link PokeDresseur} data
         */
        public void populate(final PokeDresseur model) {
            TextView pseudoView = (TextView) this.getView().findViewById(
                    R.id.row_pokedresseur_pseudo);
                    
            TextView loginView = (TextView) this.getView().findViewById(
                    R.id.row_pokedresseur_login);
                    
            TextView passwordView = (TextView) this.getView().findViewById(
                    R.id.row_pokedresseur_password);
                    

            if (model.getPseudo() != null) {
                pseudoView.setText(model.getPseudo());
            }
            if (model.getLogin() != null) {
                loginView.setText(model.getLogin());
            }
            if (model.getPassword() != null) {
                passwordView.setText(model.getPassword());
            }
        }
    }
}
