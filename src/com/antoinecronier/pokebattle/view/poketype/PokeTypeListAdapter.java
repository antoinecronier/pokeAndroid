/**************************************************************************
 * PokeTypeListAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.poketype;


import com.antoinecronier.pokebattle.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antoinecronier.pokebattle.entity.PokeType;
import com.antoinecronier.pokebattle.harmony.view.HarmonyCursorAdapter;
import com.antoinecronier.pokebattle.harmony.view.HarmonyViewHolder;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;

/**
 * List adapter for PokeType entity.
 */
public class PokeTypeListAdapter extends HarmonyCursorAdapter<PokeType> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PokeTypeListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PokeTypeListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected PokeType cursorToItem(Cursor cursor) {
        return PokeTypeContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PokeTypeContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<PokeType> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<PokeType> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_poketype);
        }

        /**
         * Populate row with a {@link PokeType}.
         *
         * @param model {@link PokeType} data
         */
        public void populate(final PokeType model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_poketype_nom);
                    
            TextView modificateurView = (TextView) this.getView().findViewById(
                    R.id.row_poketype_modificateur);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
            modificateurView.setText(String.valueOf(model.getModificateur()));
        }
    }
}
