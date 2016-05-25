/**************************************************************************
 * PokeNpcListAdapter.java, pokebattle Android
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

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.harmony.view.HarmonyCursorAdapter;
import com.antoinecronier.pokebattle.harmony.view.HarmonyViewHolder;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeAreneContract;
import com.antoinecronier.pokebattle.provider.contract.PokeDresseurContract;
import com.antoinecronier.pokebattle.provider.contract.PokeObjetContract;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;
import com.antoinecronier.pokebattle.provider.contract.PokePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokePositionContract;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;

/**
 * List adapter for PokeNpc entity.
 */
public class PokeNpcListAdapter extends HarmonyCursorAdapter<PokeNpc> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PokeNpcListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PokeNpcListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected PokeNpc cursorToItem(Cursor cursor) {
        return PokeNpcContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PokeNpcContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<PokeNpc> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<PokeNpc> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_pokenpc);
        }

        /**
         * Populate row with a {@link PokeNpc}.
         *
         * @param model {@link PokeNpc} data
         */
        public void populate(final PokeNpc model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_pokenpc_nom);
                    
            TextView professionView = (TextView) this.getView().findViewById(
                    R.id.row_pokenpc_profession);
                    
            TextView descriptionView = (TextView) this.getView().findViewById(
                    R.id.row_pokenpc_description);
                    
            TextView positionView = (TextView) this.getView().findViewById(
                    R.id.row_pokenpc_position);
                    
            TextView zoneView = (TextView) this.getView().findViewById(
                    R.id.row_pokenpc_zone);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            if (model.getProfession() != null) {
                professionView.setText(model.getProfession().name());
            }
            if (model.getDescription() != null) {
                descriptionView.setText(model.getDescription());
            }
            if (model.getPosition() != null) {
                positionView.setText(
                        String.valueOf(model.getPosition().getId()));
            }
            if (model.getZone() != null) {
                zoneView.setText(
                        String.valueOf(model.getZone().getId()));
            }
        }
    }
}
