/**************************************************************************
 * PokeAttaqueListAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokeattaque;


import com.antoinecronier.pokebattle.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antoinecronier.pokebattle.entity.PokeAttaque;
import com.antoinecronier.pokebattle.harmony.view.HarmonyCursorAdapter;
import com.antoinecronier.pokebattle.harmony.view.HarmonyViewHolder;
import com.antoinecronier.pokebattle.provider.contract.PokeAttaqueContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;

/**
 * List adapter for PokeAttaque entity.
 */
public class PokeAttaqueListAdapter extends HarmonyCursorAdapter<PokeAttaque> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PokeAttaqueListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PokeAttaqueListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected PokeAttaque cursorToItem(Cursor cursor) {
        return PokeAttaqueContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PokeAttaqueContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<PokeAttaque> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<PokeAttaque> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_pokeattaque);
        }

        /**
         * Populate row with a {@link PokeAttaque}.
         *
         * @param model {@link PokeAttaque} data
         */
        public void populate(final PokeAttaque model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_pokeattaque_nom);
                    
            TextView puissanceView = (TextView) this.getView().findViewById(
                    R.id.row_pokeattaque_puissance);
                    
            TextView precisionView = (TextView) this.getView().findViewById(
                    R.id.row_pokeattaque_precision);
                    
            TextView typeView = (TextView) this.getView().findViewById(
                    R.id.row_pokeattaque_type);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            puissanceView.setText(String.valueOf(model.getPuissance()));
            precisionView.setText(String.valueOf(model.getPrecision()));
            if (model.getType() != null) {
                typeView.setText(
                        String.valueOf(model.getType().getId()));
            }
        }
    }
}
