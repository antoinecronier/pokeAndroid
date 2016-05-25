/**************************************************************************
 * PokeDresseurShowFragment.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokedresseur;


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
import com.antoinecronier.pokebattle.entity.PokeDresseur;
import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.harmony.view.DeleteDialog;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;
import com.antoinecronier.pokebattle.harmony.view.MultiLoader;
import com.antoinecronier.pokebattle.harmony.view.MultiLoader.UriLoadedCallback;
import com.antoinecronier.pokebattle.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.antoinecronier.pokebattle.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.antoinecronier.pokebattle.provider.utils.PokeDresseurProviderUtils;
import com.antoinecronier.pokebattle.provider.PokeDresseurProviderAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeDresseurContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;

/** PokeDresseur show fragment.
 *
 * This fragment gives you an interface to show a PokeDresseur.
 * 
 * @see android.app.Fragment
 */
public class PokeDresseurShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected PokeDresseur model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** pseudo View. */
    protected TextView pseudoView;
    /** login View. */
    protected TextView loginView;
    /** password View. */
    protected TextView passwordView;
    /** npcs View. */
    protected TextView npcsView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no PokeDresseur. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.pseudoView =
            (TextView) view.findViewById(
                    R.id.pokedresseur_pseudo);
        this.loginView =
            (TextView) view.findViewById(
                    R.id.pokedresseur_login);
        this.passwordView =
            (TextView) view.findViewById(
                    R.id.pokedresseur_password);
        this.npcsView =
            (TextView) view.findViewById(
                    R.id.pokedresseur_npcs);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.pokedresseur_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.pokedresseur_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getPseudo() != null) {
            this.pseudoView.setText(this.model.getPseudo());
        }
        if (this.model.getLogin() != null) {
            this.loginView.setText(this.model.getLogin());
        }
        if (this.model.getPassword() != null) {
            this.passwordView.setText(this.model.getPassword());
        }
        if (this.model.getNpcs() != null) {
            String npcsValue = "";
            for (PokeNpc item : this.model.getNpcs()) {
                npcsValue += item.getId() + ",";
            }
            this.npcsView.setText(npcsValue);
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
                        R.layout.fragment_pokedresseur_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((PokeDresseur) intent.getParcelableExtra(PokeDresseurContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The PokeDresseur to get the data from.
     */
    public void update(PokeDresseur item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    PokeDresseurProviderAdapter.POKEDRESSEUR_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeDresseurShowFragment.this.onPokeDresseurLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/npcs"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeDresseurShowFragment.this.onNpcsLoaded(c);
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
    public void onPokeDresseurLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            PokeDresseurContract.cursorToItem(
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
    public void onNpcsLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setNpcs(PokeNpcContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setNpcs(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the PokeDresseurEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    PokeDresseurEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(PokeDresseurContract.PARCEL, this.model);
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
        private PokeDresseur item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build PokeDresseurSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final PokeDresseur item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new PokeDresseurProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                PokeDresseurShowFragment.this.onPostDelete();
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

