/**************************************************************************
 * PokeObjetListAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokeobjet;


import com.antoinecronier.pokebattle.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antoinecronier.pokebattle.entity.PokeObjet;
import com.antoinecronier.pokebattle.harmony.view.HarmonyCursorAdapter;
import com.antoinecronier.pokebattle.harmony.view.HarmonyViewHolder;
import com.antoinecronier.pokebattle.provider.contract.PokeObjetContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeObjetContract;

/**
 * List adapter for PokeObjet entity.
 */
public class PokeObjetListAdapter extends HarmonyCursorAdapter<PokeObjet> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PokeObjetListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PokeObjetListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected PokeObjet cursorToItem(Cursor cursor) {
        return PokeObjetContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PokeObjetContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<PokeObjet> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<PokeObjet> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_pokeobjet);
        }

        /**
         * Populate row with a {@link PokeObjet}.
         *
         * @param model {@link PokeObjet} data
         */
        public void populate(final PokeObjet model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_pokeobjet_nom);
                    
            TextView quantityView = (TextView) this.getView().findViewById(
                    R.id.row_pokeobjet_quantity);
                    
            TextView typeView = (TextView) this.getView().findViewById(
                    R.id.row_pokeobjet_type);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            quantityView.setText(String.valueOf(model.getQuantity()));
            if (model.getType() != null) {
                typeView.setText(
                        String.valueOf(model.getType().getId()));
            }
        }
    }
}
