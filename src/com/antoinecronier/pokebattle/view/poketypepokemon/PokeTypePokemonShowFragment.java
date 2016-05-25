/**************************************************************************
 * PokeTypePokemonShowFragment.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.poketypepokemon;


import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.antoinecronier.pokebattle.R;
import com.antoinecronier.pokebattle.entity.PokeTypePokemon;
import com.antoinecronier.pokebattle.entity.PokeType;
import com.antoinecronier.pokebattle.entity.PokeZone;
import com.antoinecronier.pokebattle.harmony.view.DeleteDialog;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;
import com.antoinecronier.pokebattle.harmony.view.MultiLoader;
import com.antoinecronier.pokebattle.harmony.view.MultiLoader.UriLoadedCallback;
import com.antoinecronier.pokebattle.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.antoinecronier.pokebattle.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.antoinecronier.pokebattle.provider.utils.PokeTypePokemonProviderUtils;
import com.antoinecronier.pokebattle.provider.PokeTypePokemonProviderAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;

/** PokeTypePokemon show fragment.
 *
 * This fragment gives you an interface to show a PokeTypePokemon.
 * 
 * @see android.app.Fragment
 */
public class PokeTypePokemonShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected PokeTypePokemon model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** nom View. */
    protected TextView nomView;
    /** attaque View. */
    protected TextView attaqueView;
    /** attaque_spe View. */
    protected TextView attaque_speView;
    /** defence View. */
    protected TextView defenceView;
    /** defence_spe View. */
    protected TextView defence_speView;
    /** vitesse View. */
    protected TextView vitesseView;
    /** pv View. */
    protected TextView pvView;
    /** pokedex View. */
    protected TextView pokedexView;
    /** evolue View. */
    protected TextView evolueView;
    /** types View. */
    protected TextView typesView;
    /** zones View. */
    protected TextView zonesView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no PokeTypePokemon. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (TextView) view.findViewById(
                    R.id.poketypepokemon_nom);
        this.attaqueView =
            (TextView) view.findViewById(
                    R.id.poketypepokemon_attaque);
        this.attaque_speView =
            (TextView) view.findViewById(
                    R.id.poketypepokemon_attaque_spe);
        this.defenceView =
            (TextView) view.findViewById(
                    R.id.poketypepokemon_defence);
        this.defence_speView =
            (TextView) view.findViewById(
                    R.id.poketypepokemon_defence_spe);
        this.vitesseView =
            (TextView) view.findViewById(
                    R.id.poketypepokemon_vitesse);
        this.pvView =
            (TextView) view.findViewById(
                    R.id.poketypepokemon_pv);
        this.pokedexView =
            (TextView) view.findViewById(
                    R.id.poketypepokemon_pokedex);
        this.evolueView =
            (TextView) view.findViewById(
                    R.id.poketypepokemon_evolue);
        this.typesView =
            (TextView) view.findViewById(
                    R.id.poketypepokemon_types);
        this.zonesView =
            (TextView) view.findViewById(
                    R.id.poketypepokemon_zones);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.poketypepokemon_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.poketypepokemon_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        this.attaqueView.setText(String.valueOf(this.model.getAttaque()));
        this.attaque_speView.setText(String.valueOf(this.model.getAttaque_spe()));
        this.defenceView.setText(String.valueOf(this.model.getDefence()));
        this.defence_speView.setText(String.valueOf(this.model.getDefence_spe()));
        this.vitesseView.setText(String.valueOf(this.model.getVitesse()));
        this.pvView.setText(String.valueOf(this.model.getPv()));
        this.pokedexView.setText(String.valueOf(this.model.getPokedex()));
        if (this.model.getEvolue() != null) {
            this.evolueView.setText(
                    String.valueOf(this.model.getEvolue().getId()));
        }
        if (this.model.getTypes() != null) {
            String typesValue = "";
            for (PokeType item : this.model.getTypes()) {
                typesValue += item.getId() + ",";
            }
            this.typesView.setText(typesValue);
        }
        if (this.model.getZones() != null) {
            String zonesValue = "";
            for (PokeZone item : this.model.getZones()) {
                zonesValue += item.getId() + ",";
            }
            this.zonesView.setText(zonesValue);
        }
        } else {
            this.dataLayout.setVisibility(View.GONE);
            this.emptyText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view =
                inflater.inflate(
                        R.layout.fragment_poketypepokemon_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((PokeTypePokemon) intent.getParcelableExtra(PokeTypePokemonContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The PokeTypePokemon to get the data from.
     */
    public void update(PokeTypePokemon item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    PokeTypePokemonProviderAdapter.POKETYPEPOKEMON_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeTypePokemonShowFragment.this.onPokeTypePokemonLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/evolue"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeTypePokemonShowFragment.this.onEvolueLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/types"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeTypePokemonShowFragment.this.onTypesLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/zones"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeTypePokemonShowFragment.this.onZonesLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.init();
        }
    }

    /**
     * Called when the entity has been loaded.
     * 
     * @param c The cursor of this entity
     */
    public void onPokeTypePokemonLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            PokeTypePokemonContract.cursorToItem(
                        c,
                        this.model);
            this.loadData();
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onEvolueLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setEvolue(PokeTypePokemonContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setEvolue(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onTypesLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setTypes(PokeTypeContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setTypes(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onZonesLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setZones(PokeZoneContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setZones(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the PokeTypePokemonEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    PokeTypePokemonEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(PokeTypePokemonContract.PARCEL, this.model);
        intent.putExtras(extras);

        this.getActivity().startActivity(intent);
    }
    /**
     * Shows a confirmation dialog.
     */
    @Override
    public void onClickDelete() {
        new DeleteDialog(this.getActivity(), this).show();
    }

    @Override
    public void onDeleteDialogClose(boolean ok) {
        if (ok) {
            new DeleteTask(this.getActivity(), this.model).execute();
        }
    }
    
    /** 
     * Called when delete task is done.
     */    
    public void onPostDelete() {
        if (this.deleteCallback != null) {
            this.deleteCallback.onItemDeleted();
        }
    }

    /**
     * This class will remove the entity into the DB.
     * It runs asynchronously.
     */
    private class DeleteTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private android.content.Context ctx;
        /** Entity to delete. */
        private PokeTypePokemon item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build PokeTypePokemonSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final PokeTypePokemon item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new PokeTypePokemonProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                PokeTypePokemonShowFragment.this.onPostDelete();
            }
            super.onPostExecute(result);
        }
        
        

    }

    /**
     * Callback for item deletion.
     */ 
    public interface DeleteCallback {
        /** Called when current item has been deleted. */
        void onItemDeleted();
    }
}

