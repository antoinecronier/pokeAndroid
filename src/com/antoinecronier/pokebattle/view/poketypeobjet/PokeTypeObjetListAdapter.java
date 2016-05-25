/**************************************************************************
 * PokeTypeObjetListAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.poketypeobjet;


import com.antoinecronier.pokebattle.R;

import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antoinecronier.pokebattle.entity.PokeTypeObjet;
import com.antoinecronier.pokebattle.harmony.view.HarmonyCursorAdapter;
import com.antoinecronier.pokebattle.harmony.view.HarmonyViewHolder;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeObjetContract;

/**
 * List adapter for PokeTypeObjet entity.
 */
public class PokeTypeObjetListAdapter extends HarmonyCursorAdapter<PokeTypeObjet> {
    
    /**
     * Constructor.
     * @param ctx context
     */
    public PokeTypeObjetListAdapter(android.content.Context context) {
        super(context);
    }
    
    /**
     * Constructor.
     * @param ctx context
     * @param cursor cursor
     */
    public PokeTypeObjetListAdapter(android.content.Context context, Cursor cursor) {
        super(context, cursor);
    }
    
    @Override
    protected PokeTypeObjet cursorToItem(Cursor cursor) {
        return PokeTypeObjetContract.cursorToItem(cursor);
    }

    @Override
    protected String getColId() {
        return PokeTypeObjetContract.COL_ID;
    }
    
    @Override
    protected HarmonyViewHolder<PokeTypeObjet> getNewViewHolder(
            android.content.Context context,
            Cursor cursor, ViewGroup group) {
        return new ViewHolder(context, group);
    }
    
    /** Holder row. */
    private class ViewHolder extends HarmonyViewHolder<PokeTypeObjet> {
    
        /**
         * Constructor.
         *
         * @param context The context
         * @param parent Optional view to be the parent of the generated hierarchy
         */
        public ViewHolder(android.content.Context context, ViewGroup parent) {
            super(context, parent, R.layout.row_poketypeobjet);
        }

        /**
         * Populate row with a {@link PokeTypeObjet}.
         *
         * @param model {@link PokeTypeObjet} data
         */
        public void populate(final PokeTypeObjet model) {
            TextView nomView = (TextView) this.getView().findViewById(
                    R.id.row_poketypeobjet_nom);
                    

            if (model.getNom() != null) {
                nomView.setText(model.getNom());
            }
        }
    }
}
