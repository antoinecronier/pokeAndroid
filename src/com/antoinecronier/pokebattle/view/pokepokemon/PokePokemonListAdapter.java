/**************************************************************************
 * PokePokemonListAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokepokemon;


import com.antoinecronier.pokebattle.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antoinecronier.pokebattle.harmony.util.DateUtils;
import com.antoinecronier.pokebattle.entity.PokePokemon;
import com.antoinecronier.pokebattle.harmony.view.HarmonyCursorAdapter;
import com.antoinecronier.pokebattle.harmony.view.HarmonyViewHolder;
import com.antoinecronier.pokebattle.provider.contract.PokePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokeAttaqueContract;

/**
 * List adapter for PokePokemon entity.
 */
public class PokePokemonListAdapter extends HarmonyCursorAdapter<PokePokemon> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PokePokemonListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PokePokemonListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected PokePokemon cursorToItem(Cursor cursor) {
        return PokePokemonContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PokePokemonContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<PokePokemon> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<PokePokemon> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_pokepokemon);
        }

        /**
         * Populate row with a {@link PokePokemon}.
         *
         * @param model {@link PokePokemon} data
         */
        public void populate(final PokePokemon model) {
            TextView surnomView = (TextView) this.getView().findViewById(
                    R.id.row_pokepokemon_surnom);
                    
            TextView niveauView = (TextView) this.getView().findViewById(
                    R.id.row_pokepokemon_niveau);
                    
            TextView captureView = (TextView) this.getView().findViewById(
                    R.id.row_pokepokemon_capture);
                    
            TextView typeView = (TextView) this.getView().findViewById(
                    R.id.row_pokepokemon_type);
                    
            TextView attaque1View = (TextView) this.getView().findViewById(
                    R.id.row_pokepokemon_attaque1);
                    
            TextView attaque2View = (TextView) this.getView().findViewById(
                    R.id.row_pokepokemon_attaque2);
                    
            TextView attaque3View = (TextView) this.getView().findViewById(
                    R.id.row_pokepokemon_attaque3);
                    
            TextView attaque4View = (TextView) this.getView().findViewById(
                    R.id.row_pokepokemon_attaque4);
                    

            if (model.getSurnom() != null) {
                surnomView.setText(model.getSurnom());
            }
            niveauView.setText(String.valueOf(model.getNiveau()));
            if (model.getCapture() != null) {
                captureView.setText(DateUtils.formatDateTimeToString(model.getCapture()));
            }
            if (model.getType() != null) {
                typeView.setText(
                        String.valueOf(model.getType().getId()));
            }
            if (model.getAttaque1() != null) {
                attaque1View.setText(
                        String.valueOf(model.getAttaque1().getId()));
            }
            if (model.getAttaque2() != null) {
                attaque2View.setText(
                        String.valueOf(model.getAttaque2().getId()));
            }
            if (model.getAttaque3() != null) {
                attaque3View.setText(
                        String.valueOf(model.getAttaque3().getId()));
            }
            if (model.getAttaque4() != null) {
                attaque4View.setText(
                        String.valueOf(model.getAttaque4().getId()));
            }
        }
    }
}
