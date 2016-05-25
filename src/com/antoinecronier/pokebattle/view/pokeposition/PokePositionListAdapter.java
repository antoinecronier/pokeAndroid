/**************************************************************************
 * PokePositionListAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokeposition;


import com.antoinecronier.pokebattle.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antoinecronier.pokebattle.entity.PokePosition;
import com.antoinecronier.pokebattle.harmony.view.HarmonyCursorAdapter;
import com.antoinecronier.pokebattle.harmony.view.HarmonyViewHolder;
import com.antoinecronier.pokebattle.provider.contract.PokePositionContract;

/**
 * List adapter for PokePosition entity.
 */
public class PokePositionListAdapter extends HarmonyCursorAdapter<PokePosition> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PokePositionListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PokePositionListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected PokePosition cursorToItem(Cursor cursor) {
        return PokePositionContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PokePositionContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<PokePosition> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<PokePosition> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_pokeposition);
        }

        /**
         * Populate row with a {@link PokePosition}.
         *
         * @param model {@link PokePosition} data
         */
        public void populate(final PokePosition model) {
            TextView xView = (TextView) this.getView().findViewById(
                    R.id.row_pokeposition_x);
                    
            TextView yView = (TextView) this.getView().findViewById(
                    R.id.row_pokeposition_y);
                    

            xView.setText(String.valueOf(model.getX()));
            yView.setText(String.valueOf(model.getY()));
        }
    }
}
