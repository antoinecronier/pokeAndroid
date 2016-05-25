/**************************************************************************
 * PokeObjetShowFragment.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokeobjet;


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
import com.antoinecronier.pokebattle.entity.PokeObjet;
import com.antoinecronier.pokebattle.harmony.view.DeleteDialog;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;
import com.antoinecronier.pokebattle.harmony.view.MultiLoader;
import com.antoinecronier.pokebattle.harmony.view.MultiLoader.UriLoadedCallback;
import com.antoinecronier.pokebattle.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.antoinecronier.pokebattle.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.antoinecronier.pokebattle.provider.utils.PokeObjetProviderUtils;
import com.antoinecronier.pokebattle.provider.PokeObjetProviderAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeObjetContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeObjetContract;

/** PokeObjet show fragment.
 *
 * This fragment gives you an interface to show a PokeObjet.
 * 
 * @see android.app.Fragment
 */
public class PokeObjetShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected PokeObjet model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** nom View. */
    protected TextView nomView;
    /** quantity View. */
    protected TextView quantityView;
    /** type View. */
    protected TextView typeView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no PokeObjet. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (TextView) view.findViewById(
                    R.id.pokeobjet_nom);
        this.quantityView =
            (TextView) view.findViewById(
                    R.id.pokeobjet_quantity);
        this.typeView =
            (TextView) view.findViewById(
                    R.id.pokeobjet_type);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.pokeobjet_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.pokeobjet_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        this.quantityView.setText(String.valueOf(this.model.getQuantity()));
        if (this.model.getType() != null) {
            this.typeView.setText(
                    String.valueOf(this.model.getType().getId()));
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
                        R.layout.fragment_pokeobjet_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((PokeObjet) intent.getParcelableExtra(PokeObjetContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The PokeObjet to get the data from.
     */
    public void update(PokeObjet item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    PokeObjetProviderAdapter.POKEOBJET_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeObjetShowFragment.this.onPokeObjetLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/type"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeObjetShowFragment.this.onTypeLoaded(c);
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
    public void onPokeObjetLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            PokeObjetContract.cursorToItem(
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
    public void onTypeLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setType(PokeTypeObjetContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setType(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the PokeObjetEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    PokeObjetEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(PokeObjetContract.PARCEL, this.model);
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
        private PokeObjet item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build PokeObjetSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final PokeObjet item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new PokeObjetProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                PokeObjetShowFragment.this.onPostDelete();
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

