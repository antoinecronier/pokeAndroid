/**************************************************************************
 * PokeZoneListAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokezone;


import com.antoinecronier.pokebattle.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antoinecronier.pokebattle.entity.PokeZone;
import com.antoinecronier.pokebattle.harmony.view.HarmonyCursorAdapter;
import com.antoinecronier.pokebattle.harmony.view.HarmonyViewHolder;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;

/**
 * List adapter for PokeZone entity.
 */
public class PokeZoneListAdapter extends HarmonyCursorAdapter<PokeZone> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PokeZoneListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PokeZoneListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected PokeZone cursorToItem(Cursor cursor) {
        return PokeZoneContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PokeZoneContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<PokeZone> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<PokeZone> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_pokezone);
        }

        /**
         * Populate row with a {@link PokeZone}.
         *
         * @param model {@link PokeZone} data
         */
        public void populate(final PokeZone model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_pokezone_nom);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
        }
    }
}
