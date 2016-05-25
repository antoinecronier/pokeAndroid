/**************************************************************************
 * PokeAreneListAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokearene;


import com.antoinecronier.pokebattle.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antoinecronier.pokebattle.entity.PokeArene;
import com.antoinecronier.pokebattle.harmony.view.HarmonyCursorAdapter;
import com.antoinecronier.pokebattle.harmony.view.HarmonyViewHolder;
import com.antoinecronier.pokebattle.provider.contract.PokeAreneContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;
import com.antoinecronier.pokebattle.provider.contract.PokePositionContract;

/**
 * List adapter for PokeArene entity.
 */
public class PokeAreneListAdapter extends HarmonyCursorAdapter<PokeArene> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PokeAreneListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PokeAreneListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected PokeArene cursorToItem(Cursor cursor) {
        return PokeAreneContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PokeAreneContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<PokeArene> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<PokeArene> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_pokearene);
        }

        /**
         * Populate row with a {@link PokeArene}.
         *
         * @param model {@link PokeArene} data
         */
        public void populate(final PokeArene model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_pokearene_nom);
                    
            TextView maitreView = (TextView) this.getView().findViewById(
                    R.id.row_pokearene_maitre);
                    
            TextView badgeView = (TextView) this.getView().findViewById(
                    R.id.row_pokearene_badge);
                    
            TextView zoneView = (TextView) this.getView().findViewById(
                    R.id.row_pokearene_zone);
                    
            TextView positionView = (TextView) this.getView().findViewById(
                    R.id.row_pokearene_position);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            if (model.getMaitre() != null) {
                maitreView.setText(
                        String.valueOf(model.getMaitre().getId()));
            }
            if (model.getBadge() != null) {
                badgeView.setText(
                        String.valueOf(model.getBadge().getId()));
            }
            if (model.getZone() != null) {
                zoneView.setText(
                        String.valueOf(model.getZone().getId()));
            }
            if (model.getPosition() != null) {
                positionView.setText(
                        String.valueOf(model.getPosition().getId()));
            }
        }
    }
}
