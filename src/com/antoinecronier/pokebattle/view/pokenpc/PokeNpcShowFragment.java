/**************************************************************************
 * PokeNpcShowFragment.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokenpc;


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
import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.entity.PokeObjet;
import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.entity.PokePokemon;
import com.antoinecronier.pokebattle.harmony.view.DeleteDialog;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;
import com.antoinecronier.pokebattle.harmony.view.MultiLoader;
import com.antoinecronier.pokebattle.harmony.view.MultiLoader.UriLoadedCallback;
import com.antoinecronier.pokebattle.menu.CrudDeleteMenuWrapper.CrudDeleteMenuInterface;
import com.antoinecronier.pokebattle.menu.CrudEditMenuWrapper.CrudEditMenuInterface;
import com.antoinecronier.pokebattle.provider.utils.PokeNpcProviderUtils;
import com.antoinecronier.pokebattle.provider.PokeNpcProviderAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeAreneContract;
import com.antoinecronier.pokebattle.provider.contract.PokeDresseurContract;
import com.antoinecronier.pokebattle.provider.contract.PokeObjetContract;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;
import com.antoinecronier.pokebattle.provider.contract.PokePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokePositionContract;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;

/** PokeNpc show fragment.
 *
 * This fragment gives you an interface to show a PokeNpc.
 * 
 * @see android.app.Fragment
 */
public class PokeNpcShowFragment
        extends HarmonyFragment
        implements CrudDeleteMenuInterface,
                DeleteDialog.DeleteDialogCallback,
                CrudEditMenuInterface {
    /** Model data. */
    protected PokeNpc model;
    /** DeleteCallback. */
    protected DeleteCallback deleteCallback;

    /* This entity's fields views */
    /** nom View. */
    protected TextView nomView;
    /** profession View. */
    protected TextView professionView;
    /** description View. */
    protected TextView descriptionView;
    /** objets View. */
    protected TextView objetsView;
    /** badge View. */
    protected TextView badgeView;
    /** pokemons View. */
    protected TextView pokemonsView;
    /** team View. */
    protected TextView teamView;
    /** position View. */
    protected TextView positionView;
    /** zone View. */
    protected TextView zoneView;
    /** Data layout. */
    protected RelativeLayout dataLayout;
    /** Text view for no PokeNpc. */
    protected TextView emptyText;


    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (TextView) view.findViewById(
                    R.id.pokenpc_nom);
        this.professionView =
            (TextView) view.findViewById(
                    R.id.pokenpc_profession);
        this.descriptionView =
            (TextView) view.findViewById(
                    R.id.pokenpc_description);
        this.objetsView =
            (TextView) view.findViewById(
                    R.id.pokenpc_objets);
        this.badgeView =
            (TextView) view.findViewById(
                    R.id.pokenpc_badge);
        this.pokemonsView =
            (TextView) view.findViewById(
                    R.id.pokenpc_pokemons);
        this.teamView =
            (TextView) view.findViewById(
                    R.id.pokenpc_team);
        this.positionView =
            (TextView) view.findViewById(
                    R.id.pokenpc_position);
        this.zoneView =
            (TextView) view.findViewById(
                    R.id.pokenpc_zone);

        this.dataLayout =
                (RelativeLayout) view.findViewById(
                        R.id.pokenpc_data_layout);
        this.emptyText =
                (TextView) view.findViewById(
                        R.id.pokenpc_empty);
    }

    /** Load data from model to fields view. */
    public void loadData() {
        if (this.model != null) {

            this.dataLayout.setVisibility(View.VISIBLE);
            this.emptyText.setVisibility(View.GONE);


        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        if (this.model.getProfession() != null) {
            this.professionView.setText(this.model.getProfession().toString());
        }
        if (this.model.getDescription() != null) {
            this.descriptionView.setText(this.model.getDescription());
        }
        if (this.model.getObjets() != null) {
            String objetsValue = "";
            for (PokeObjet item : this.model.getObjets()) {
                objetsValue += item.getId() + ",";
            }
            this.objetsView.setText(objetsValue);
        }
        if (this.model.getBadge() != null) {
            String badgeValue = "";
            for (PokeBadge item : this.model.getBadge()) {
                badgeValue += item.getId() + ",";
            }
            this.badgeView.setText(badgeValue);
        }
        if (this.model.getPokemons() != null) {
            String pokemonsValue = "";
            for (PokePokemon item : this.model.getPokemons()) {
                pokemonsValue += item.getId() + ",";
            }
            this.pokemonsView.setText(pokemonsValue);
        }
        if (this.model.getTeam() != null) {
            String teamValue = "";
            for (PokePokemon item : this.model.getTeam()) {
                teamValue += item.getId() + ",";
            }
            this.teamView.setText(teamValue);
        }
        if (this.model.getPosition() != null) {
            this.positionView.setText(
                    String.valueOf(this.model.getPosition().getId()));
        }
        if (this.model.getZone() != null) {
            this.zoneView.setText(
                    String.valueOf(this.model.getZone().getId()));
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
                        R.layout.fragment_pokenpc_show,
                        container,
                        false);  
        if (this.getActivity() instanceof DeleteCallback) {
            this.deleteCallback = (DeleteCallback) this.getActivity();
        }

        this.initializeComponent(view);
        
        final Intent intent =  getActivity().getIntent();
        this.update((PokeNpc) intent.getParcelableExtra(PokeNpcContract.PARCEL));

        return view;
    }

    /**
     * Updates the view with the given data.
     *
     * @param item The PokeNpc to get the data from.
     */
    public void update(PokeNpc item) {
        this.model = item;
        
        this.loadData();
        
        if (this.model != null) {
            MultiLoader loader = new MultiLoader(this);
            String baseUri = 
                    PokeNpcProviderAdapter.POKENPC_URI 
                    + "/" 
                    + this.model.getId();

            loader.addUri(Uri.parse(baseUri), new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeNpcShowFragment.this.onPokeNpcLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/objets"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeNpcShowFragment.this.onObjetsLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/badge"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeNpcShowFragment.this.onBadgeLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/pokemons"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeNpcShowFragment.this.onPokemonsLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/team"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeNpcShowFragment.this.onTeamLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/position"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeNpcShowFragment.this.onPositionLoaded(c);
                }

                @Override
                public void onLoaderReset() {

                }
            });
            loader.addUri(Uri.parse(baseUri + "/zone"), 
                    new UriLoadedCallback() {

                @Override
                public void onLoadComplete(android.database.Cursor c) {
                    PokeNpcShowFragment.this.onZoneLoaded(c);
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
    public void onPokeNpcLoaded(android.database.Cursor c) {
        if (c.getCount() > 0) {
            c.moveToFirst();
            
            PokeNpcContract.cursorToItem(
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
    public void onObjetsLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setObjets(PokeObjetContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setObjets(null);
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
            this.model.setBadge(PokeBadgeContract.cursorToItems(c));
            this.loadData();
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
    public void onPokemonsLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setPokemons(PokePokemonContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setPokemons(null);
                    this.loadData();
            }
        }
    }
    /**
     * Called when the relation has been loaded.
     * 
     * @param c The cursor of this relation
     */
    public void onTeamLoaded(android.database.Cursor c) {
        if (this.model != null) {
            if (c != null) {
            this.model.setTeam(PokePokemonContract.cursorToItems(c));
            this.loadData();
            } else {
                this.model.setTeam(null);
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
     * Calls the PokeNpcEditActivity.
     */
    @Override
    public void onClickEdit() {
        final Intent intent = new Intent(getActivity(),
                                    PokeNpcEditActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(PokeNpcContract.PARCEL, this.model);
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
        private PokeNpc item;

        /**
         * Constructor of the task.
         * @param item The entity to remove from DB
         * @param ctx A context to build PokeNpcSQLiteAdapter
         */
        public DeleteTask(final android.content.Context ctx,
                    final PokeNpc item) {
            super();
            this.ctx = ctx;
            this.item = item;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = -1;

            result = new PokeNpcProviderUtils(this.ctx)
                    .delete(this.item);

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result >= 0) {
                PokeNpcShowFragment.this.onPostDelete();
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

