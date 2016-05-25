/**************************************************************************
 * PokeTypeShowFragment.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.poketype;


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
import com.antoinecronier.pokebattle.entity.PokeType;
import com.antoinecronier.pokebattle.harmony.view.DeleteDialog;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;
import com.antoinecronier.pokebattle.harmony.view.MultiLoader;
import com.antoinecronier.pokebattle.harmony.view.MultiLoader.UriLoadedCallback;
import com.antoinecronier.pokebattle.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.antoinecronier.pokebattle.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.antoinecronier.pokebattle.provider.utils.PokeTypeProviderUtils;
import com.antoinecronier.pokebattle.provider.PokeTypeProviderAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;

/** PokeType show fragment.
 *
 * This fragment gives you an interface to show a PokeType.
 * 
 * @see android.app.Fragment
 */
public class PokeTypeShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected PokeType model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** nom View. */
    protected TextView nomView;
    /** modificateur View. */
    protected TextView modificateurView;
    /** typeFort View. */
    protected TextView typeFortView;
    /** typeFaible View. */
    protected TextView typeFaibleView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no PokeType. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (TextView) view.findViewById(
                    R.id.poketype_nom);
        this.modificateurView =
            (TextView) view.findViewById(
                    R.id.poketype_modificateur);
        this.typeFortView =
            (TextView) view.findViewById(
                    R.id.poketype_typefort);
        this.typeFaibleView =
            (TextView) view.findViewById(
                    R.id.poketype_typefaible);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.poketype_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.poketype_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        this.modificateurView.setText(String.valueOf(this.model.getModificateur()));
        if (this.model.getTypeFort() != null) {
            String typeFortValue = "";
            for (PokeType item : this.model.getTypeFort()) {
                typeFortValue += item.getId() + ",";
            }
            this.typeFortView.setText(typeFortValue);
        }
        if (this.model.getTypeFaible() != null) {
            String typeFaibleValue = "";
            for (PokeType item : this.model.getTypeFaible()) {
                typeFaibleValue += item.getId() + ",";
            }
            this.typeFaibleView.setText(typeFaibleValue);
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
                        R.layout.fragment_poketype_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((PokeType) intent.getParcelableExtra(PokeTypeContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The PokeType to get the data from.
     */
    public void update(PokeType item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    PokeTypeProviderAdapter.POKETYPE_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeTypeShowFragment.this.onPokeTypeLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/typefort"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeTypeShowFragment.this.onTypeFortLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/typefaible"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeTypeShowFragment.this.onTypeFaibleLoaded(c);
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
    public void onPokeTypeLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            PokeTypeContract.cursorToItem(
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
    public void onTypeFortLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setTypeFort(PokeTypeContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setTypeFort(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onTypeFaibleLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setTypeFaible(PokeTypeContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setTypeFaible(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the PokeTypeEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    PokeTypeEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(PokeTypeContract.PARCEL, this.model);
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
        private PokeType item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build PokeTypeSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final PokeType item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new PokeTypeProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                PokeTypeShowFragment.this.onPostDelete();
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

