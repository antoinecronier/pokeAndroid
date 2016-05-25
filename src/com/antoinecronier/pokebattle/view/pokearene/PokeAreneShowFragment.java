/**************************************************************************
 * PokeAreneShowFragment.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokearene;


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
import com.antoinecronier.pokebattle.entity.PokeArene;
import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.harmony.view.DeleteDialog;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;
import com.antoinecronier.pokebattle.harmony.view.MultiLoader;
import com.antoinecronier.pokebattle.harmony.view.MultiLoader.UriLoadedCallback;
import com.antoinecronier.pokebattle.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.antoinecronier.pokebattle.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.antoinecronier.pokebattle.provider.utils.PokeAreneProviderUtils;
import com.antoinecronier.pokebattle.provider.PokeAreneProviderAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeAreneContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;
import com.antoinecronier.pokebattle.provider.contract.PokePositionContract;

/** PokeArene show fragment.
 *
 * This fragment gives you an interface to show a PokeArene.
 * 
 * @see android.app.Fragment
 */
public class PokeAreneShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected PokeArene model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** nom View. */
    protected TextView nomView;
    /** maitre View. */
    protected TextView maitreView;
    /** dresseurs View. */
    protected TextView dresseursView;
    /** badge View. */
    protected TextView badgeView;
    /** zone View. */
    protected TextView zoneView;
    /** position View. */
    protected TextView positionView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no PokeArene. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (TextView) view.findViewById(
                    R.id.pokearene_nom);
        this.maitreView =
            (TextView) view.findViewById(
                    R.id.pokearene_maitre);
        this.dresseursView =
            (TextView) view.findViewById(
                    R.id.pokearene_dresseurs);
        this.badgeView =
            (TextView) view.findViewById(
                    R.id.pokearene_badge);
        this.zoneView =
            (TextView) view.findViewById(
                    R.id.pokearene_zone);
        this.positionView =
            (TextView) view.findViewById(
                    R.id.pokearene_position);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.pokearene_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.pokearene_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        if (this.model.getMaitre() != null) {
            this.maitreView.setText(
                    String.valueOf(this.model.getMaitre().getId()));
        }
        if (this.model.getDresseurs() != null) {
            String dresseursValue = "";
            for (PokeNpc item : this.model.getDresseurs()) {
                dresseursValue += item.getId() + ",";
            }
            this.dresseursView.setText(dresseursValue);
        }
        if (this.model.getBadge() != null) {
            this.badgeView.setText(
                    String.valueOf(this.model.getBadge().getId()));
        }
        if (this.model.getZone() != null) {
            this.zoneView.setText(
                    String.valueOf(this.model.getZone().getId()));
        }
        if (this.model.getPosition() != null) {
            this.positionView.setText(
                    String.valueOf(this.model.getPosition().getId()));
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
                        R.layout.fragment_pokearene_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((PokeArene) intent.getParcelableExtra(PokeAreneContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The PokeArene to get the data from.
     */
    public void update(PokeArene item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    PokeAreneProviderAdapter.POKEARENE_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeAreneShowFragment.this.onPokeAreneLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/maitre"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeAreneShowFragment.this.onMaitreLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/dresseurs"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeAreneShowFragment.this.onDresseursLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/badge"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeAreneShowFragment.this.onBadgeLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/zone"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeAreneShowFragment.this.onZoneLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/position"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeAreneShowFragment.this.onPositionLoaded(c);
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
    public void onPokeAreneLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            PokeAreneContract.cursorToItem(
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
    public void onMaitreLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setMaitre(PokeNpcContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setMaitre(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onDresseursLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setDresseurs(PokeNpcContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setDresseurs(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onBadgeLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setBadge(PokeBadgeContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setBadge(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onZoneLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setZone(PokeZoneContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setZone(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onPositionLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    this.model.setPosition(PokePositionContract.cursorToItem(c));
                    this.loadData();
                }
            } else {
                this.model.setPosition(null);
                    this.loadData();
            }
        }
    }

    /**
     * Calls the PokeAreneEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    PokeAreneEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(PokeAreneContract.PARCEL, this.model);
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
        private PokeArene item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build PokeAreneSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final PokeArene item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new PokeAreneProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                PokeAreneShowFragment.this.onPostDelete();
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

