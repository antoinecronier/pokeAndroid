/**************************************************************************
 * PokeNpcCreateFragment.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokenpc;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.antoinecronier.pokebattle.R;
import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.entity.PokeObjet;
import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.entity.PokePokemon;
import com.antoinecronier.pokebattle.entity.PokePosition;
import com.antoinecronier.pokebattle.entity.PokeZone;
import com.antoinecronier.pokebattle.entity.PokeProfession;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;
import com.antoinecronier.pokebattle.harmony.widget.MultiEntityWidget;
import com.antoinecronier.pokebattle.harmony.widget.SingleEntityWidget;
import com.antoinecronier.pokebattle.harmony.widget.EnumSpinner;
import com.antoinecronier.pokebattle.menu.SaveMenuWrapper.SaveMenuInterface;
import com.antoinecronier.pokebattle.provider.utils.PokeNpcProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeObjetProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeBadgeProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokePokemonProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokePositionProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeZoneProviderUtils;

/**
 * PokeNpc create fragment.
 *
 * This fragment gives you an interface to create a PokeNpc.
 */
public class PokeNpcCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected PokeNpc model = new PokeNpc();

    /** Fields View. */
    /** nom View. */
    protected EditText nomView;
    /** profession View. */
    protected EnumSpinner professionView;
    /** description View. */
    protected EditText descriptionView;
    /** The objets chooser component. */
    protected MultiEntityWidget objetsWidget;
    /** The objets Adapter. */
    protected MultiEntityWidget.EntityAdapter<PokeObjet> 
                objetsAdapter;
    /** The badge chooser component. */
    protected MultiEntityWidget badgeWidget;
    /** The badge Adapter. */
    protected MultiEntityWidget.EntityAdapter<PokeBadge> 
                badgeAdapter;
    /** The pokemons chooser component. */
    protected MultiEntityWidget pokemonsWidget;
    /** The pokemons Adapter. */
    protected MultiEntityWidget.EntityAdapter<PokePokemon> 
                pokemonsAdapter;
    /** The team chooser component. */
    protected MultiEntityWidget teamWidget;
    /** The team Adapter. */
    protected MultiEntityWidget.EntityAdapter<PokePokemon> 
                teamAdapter;
    /** The position chooser component. */
    protected SingleEntityWidget positionWidget;
    /** The position Adapter. */
    protected SingleEntityWidget.EntityAdapter<PokePosition> 
                positionAdapter;
    /** The zone chooser component. */
    protected SingleEntityWidget zoneWidget;
    /** The zone Adapter. */
    protected SingleEntityWidget.EntityAdapter<PokeZone> 
                zoneAdapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (EditText) view.findViewById(R.id.pokenpc_nom);
        this.professionView =
            (EnumSpinner) view.findViewById(R.id.pokenpc_profession);
        this.professionView.setEnum(PokeProfession.class);
        this.descriptionView =
            (EditText) view.findViewById(R.id.pokenpc_description);
        this.objetsAdapter = 
                new MultiEntityWidget.EntityAdapter<PokeObjet>() {
            @Override
            public String entityToString(PokeObjet item) {
                return String.valueOf(item.getId());
            }
        };
        this.objetsWidget =
            (MultiEntityWidget) view.findViewById(R.id.pokenpc_objets_button);
        this.objetsWidget.setAdapter(this.objetsAdapter);
        this.objetsWidget.setTitle(R.string.pokenpc_objets_dialog_title);
        this.badgeAdapter = 
                new MultiEntityWidget.EntityAdapter<PokeBadge>() {
            @Override
            public String entityToString(PokeBadge item) {
                return String.valueOf(item.getId());
            }
        };
        this.badgeWidget =
            (MultiEntityWidget) view.findViewById(R.id.pokenpc_badge_button);
        this.badgeWidget.setAdapter(this.badgeAdapter);
        this.badgeWidget.setTitle(R.string.pokenpc_badge_dialog_title);
        this.pokemonsAdapter = 
                new MultiEntityWidget.EntityAdapter<PokePokemon>() {
            @Override
            public String entityToString(PokePokemon item) {
                return String.valueOf(item.getId());
            }
        };
        this.pokemonsWidget =
            (MultiEntityWidget) view.findViewById(R.id.pokenpc_pokemons_button);
        this.pokemonsWidget.setAdapter(this.pokemonsAdapter);
        this.pokemonsWidget.setTitle(R.string.pokenpc_pokemons_dialog_title);
        this.teamAdapter = 
                new MultiEntityWidget.EntityAdapter<PokePokemon>() {
            @Override
            public String entityToString(PokePokemon item) {
                return String.valueOf(item.getId());
            }
        };
        this.teamWidget =
            (MultiEntityWidget) view.findViewById(R.id.pokenpc_team_button);
        this.teamWidget.setAdapter(this.teamAdapter);
        this.teamWidget.setTitle(R.string.pokenpc_team_dialog_title);
        this.positionAdapter = 
                new SingleEntityWidget.EntityAdapter<PokePosition>() {
            @Override
            public String entityToString(PokePosition item) {
                return String.valueOf(item.getId());
            }
        };
        this.positionWidget =
            (SingleEntityWidget) view.findViewById(R.id.pokenpc_position_button);
        this.positionWidget.setAdapter(this.positionAdapter);
        this.positionWidget.setTitle(R.string.pokenpc_position_dialog_title);
        this.zoneAdapter = 
                new SingleEntityWidget.EntityAdapter<PokeZone>() {
            @Override
            public String entityToString(PokeZone item) {
                return String.valueOf(item.getId());
            }
        };
        this.zoneWidget =
            (SingleEntityWidget) view.findViewById(R.id.pokenpc_zone_button);
        this.zoneWidget.setAdapter(this.zoneAdapter);
        this.zoneWidget.setTitle(R.string.pokenpc_zone_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        if (this.model.getProfession() != null) {
            this.professionView.setSelectedItem(this.model.getProfession());
        }
        if (this.model.getDescription() != null) {
            this.descriptionView.setText(this.model.getDescription());
        }

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setProfession((PokeProfession) this.professionView.getSelectedItem());

        this.model.setDescription(this.descriptionView.getEditableText().toString());

        this.model.setObjets(this.objetsAdapter.getCheckedItems());

        this.model.setBadge(this.badgeAdapter.getCheckedItems());

        this.model.setPokemons(this.pokemonsAdapter.getCheckedItems());

        this.model.setTeam(this.teamAdapter.getCheckedItems());

        this.model.setPosition(this.positionAdapter.getSelectedItem());

        this.model.setZone(this.zoneAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.pokenpc_nom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.descriptionView.getText().toString().trim())) {
            error = R.string.pokenpc_description_invalid_field_error;
        }
    
        if (error > 0) {
            Toast.makeText(this.getActivity(),
                this.getActivity().getString(error),
                Toast.LENGTH_SHORT).show();
        }
        return error == 0;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(
                R.layout.fragment_pokenpc_create,
                container,
                false);

        this.initializeComponent(view);
        this.loadData();
        return view;
    }

    /**
     * This class will save the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class CreateTask extends AsyncTask<Void, Void, Uri> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to persist. */
        private final PokeNpc entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final PokeNpcCreateFragment fragment,
                final PokeNpc entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.pokenpc_progress_save_title),
                    this.ctx.getString(
                            R.string.pokenpc_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new PokeNpcProviderUtils(this.ctx).insert(
                        this.entity);

            return result;
        }

        @Override
        protected void onPostExecute(Uri result) {
            super.onPostExecute(result);
            if (result != null) {
                final HarmonyFragmentActivity activity =
                                         (HarmonyFragmentActivity) this.ctx;
                activity.finish();
            } else {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(
                        this.ctx.getString(
                                R.string.pokenpc_error_create));
                builder.setPositiveButton(
                        this.ctx.getString(android.R.string.yes),
                        new Dialog.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int which) {

                            }
                        });
                builder.show();
            }

            this.progress.dismiss();
        }
    }

    /**
     * This class will save the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class LoadTask extends AsyncTask<Void, Void, Void> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Progress Dialog. */
        private ProgressDialog progress;
        /** Fragment. */
        private PokeNpcCreateFragment fragment;
        /** objets list. */
        private ArrayList<PokeObjet> objetsList;
        /** badge list. */
        private ArrayList<PokeBadge> badgeList;
        /** pokemons list. */
        private ArrayList<PokePokemon> pokemonsList;
        /** team list. */
        private ArrayList<PokePokemon> teamList;
        /** position list. */
        private ArrayList<PokePosition> positionList;
        /** zone list. */
        private ArrayList<PokeZone> zoneList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PokeNpcCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.pokenpc_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.pokenpc_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.objetsList = 
                new PokeObjetProviderUtils(this.ctx).queryAll();
            this.badgeList = 
                new PokeBadgeProviderUtils(this.ctx).queryAll();
            this.pokemonsList = 
                new PokePokemonProviderUtils(this.ctx).queryAll();
            this.teamList = 
                new PokePokemonProviderUtils(this.ctx).queryAll();
            this.positionList = 
                new PokePositionProviderUtils(this.ctx).queryAll();
            this.zoneList = 
                new PokeZoneProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.objetsAdapter.loadData(this.objetsList);
            this.fragment.badgeAdapter.loadData(this.badgeList);
            this.fragment.pokemonsAdapter.loadData(this.pokemonsList);
            this.fragment.teamAdapter.loadData(this.teamList);
            this.fragment.positionAdapter.loadData(this.positionList);
            this.fragment.zoneAdapter.loadData(this.zoneList);
            this.progress.dismiss();
        }
    }

    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new CreateTask(this, this.model).execute();
        }
    }
}
