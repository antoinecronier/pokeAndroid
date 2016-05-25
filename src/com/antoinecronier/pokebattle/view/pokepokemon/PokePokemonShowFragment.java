/**************************************************************************
 * PokePokemonShowFragment.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokepokemon;


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
import com.antoinecronier.pokebattle.entity.PokePokemon;
import com.antoinecronier.pokebattle.harmony.util.DateUtils;
import com.antoinecronier.pokebattle.harmony.view.DeleteDialog;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;
import com.antoinecronier.pokebattle.harmony.view.MultiLoader;
import com.antoinecronier.pokebattle.harmony.view.MultiLoader.UriLoadedCallback;
import com.antoinecronier.pokebattle.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.antoinecronier.pokebattle.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.antoinecronier.pokebattle.provider.utils.PokePokemonProviderUtils;
import com.antoinecronier.pokebattle.provider.PokePokemonProviderAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokeAttaqueContract;
import com.antoinecronier.pokebattle.provider.contract.PokeAttaqueContract;
import com.antoinecronier.pokebattle.provider.contract.PokeAttaqueContract;
import com.antoinecronier.pokebattle.provider.contract.PokeAttaqueContract;

/** PokePokemon show fragment.
 *
 * This fragment gives you an interface to show a PokePokemon.
 * 
 * @see android.app.Fragment
 */
public class PokePokemonShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected PokePokemon model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** surnom View. */
    protected TextView surnomView;
    /** niveau View. */
    protected TextView niveauView;
    /** capture View. */
    protected TextView captureView;
    /** type View. */
    protected TextView typeView;
    /** attaque1 View. */
    protected TextView attaque1View;
    /** attaque2 View. */
    protected TextView attaque2View;
    /** attaque3 View. */
    protected TextView attaque3View;
    /** attaque4 View. */
    protected TextView attaque4View;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no PokePokemon. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.surnomView =
            (TextView) view.findViewById(
                    R.id.pokepokemon_surnom);
        this.niveauView =
            (TextView) view.findViewById(
                    R.id.pokepokemon_niveau);
        this.captureView =
            (TextView) view.findViewById(
                    R.id.pokepokemon_capture);
        this.typeView =
            (TextView) view.findViewById(
                    R.id.pokepokemon_type);
        this.attaque1View =
            (TextView) view.findViewById(
                    R.id.pokepokemon_attaque1);
        this.attaque2View =
            (TextView) view.findViewById(
                    R.id.pokepokemon_attaque2);
        this.attaque3View =
            (TextView) view.findViewById(
                    R.id.pokepokemon_attaque3);
        this.attaque4View =
            (TextView) view.findViewById(
                    R.id.pokepokemon_attaque4);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.pokepokemon_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.pokepokemon_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getSurnom() != null) {
            this.surnomView.setText(this.model.getSurnom());
        }
        this.niveauView.setText(String.valueOf(this.model.getNiveau()));
        if (this.model.getCapture() != null) {
            this.captureView.setText(
                    DateUtils.formatDateTimeToString(
                            this.model.getCapture()));
        }
        if (this.model.getType() != null) {
            this.typeView.setText(
                    String.valueOf(this.model.getType().getId()));
        }
        if (this.model.getAttaque1() != null) {
            this.attaque1View.setText(
                    String.valueOf(this.model.getAttaque1().getId()));
        }
        if (this.model.getAttaque2() != null) {
            this.attaque2View.setText(
                    String.valueOf(this.model.getAttaque2().getId()));
        }
        if (this.model.getAttaque3() != null) {
            this.attaque3View.setText(
                    String.valueOf(this.model.getAttaque3().getId()));
        }
        if (this.model.getAttaque4() != null) {
            this.attaque4View.setText(
                    String.valueOf(this.model.getAttaque4().getId()));
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
                        R.layout.fragment_pokepokemon_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((PokePokemon) intent.getParcelableExtra(PokePokemonContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The PokePokemon to get the data from.
     */
    public void update(PokePokemon item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    PokePokemonProviderAdapter.POKEPOKEMON_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokePokemonShowFragment.this.onPokePokemonLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/type"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokePokemonShowFragment.this.onTypeLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/attaque1"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokePokemonShowFragment.this.onAttaque1Loaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/attaque2"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokePokemonShowFragment.this.onAttaque2Loaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/attaque3"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokePokemonShowFragment.this.onAttaque3Loaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/attaque4"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokePokemonShowFragment.this.onAttaque4Loaded(c);
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
    public void onPokePokemonLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            PokePokemonContract.cursorToItem(
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
                    this.model.setType(PokeTypePokemonContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setType(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onAttaque1Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setAttaque1(PokeAttaqueContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setAttaque1(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onAttaque2Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setAttaque2(PokeAttaqueContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setAttaque2(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onAttaque3Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setAttaque3(PokeAttaqueContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setAttaque3(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onAttaque4Loaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setAttaque4(PokeAttaqueContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setAttaque4(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the PokePokemonEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    PokePokemonEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(PokePokemonContract.PARCEL, this.model);
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
        private PokePokemon item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build PokePokemonSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final PokePokemon item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new PokePokemonProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                PokePokemonShowFragment.this.onPostDelete();
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

