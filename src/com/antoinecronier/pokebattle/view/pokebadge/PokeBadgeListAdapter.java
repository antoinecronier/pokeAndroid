/**************************************************************************
 * PokeBadgeListAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokebadge;


import com.antoinecronier.pokebattle.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.harmony.view.HarmonyCursorAdapter;
import com.antoinecronier.pokebattle.harmony.view.HarmonyViewHolder;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;

/**
 * List adapter for PokeBadge entity.
 */
public class PokeBadgeListAdapter extends HarmonyCursorAdapter<PokeBadge> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PokeBadgeListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PokeBadgeListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected PokeBadge cursorToItem(Cursor cursor) {
        return PokeBadgeContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PokeBadgeContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<PokeBadge> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<PokeBadge> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_pokebadge);
        }

        /**
         * Populate row with a {@link PokeBadge}.
         *
         * @param model {@link PokeBadge} data
         */
        public void populate(final PokeBadge model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_pokebadge_nom);
                    
            TextView bonusView = (TextView) this.getView().findViewById(
                    R.id.row_pokebadge_bonus);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            if (model.getBonus() != null) {
                bonusView.setText(model.getBonus().name());
            }
        }
    }
}
