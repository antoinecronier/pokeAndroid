/**************************************************************************
 * PokeTypePokemonCreateFragment.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.poketypepokemon;

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
import com.antoinecronier.pokebattle.entity.PokeTypePokemon;
import com.antoinecronier.pokebattle.entity.PokeType;
import com.antoinecronier.pokebattle.entity.PokeZone;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;
import com.antoinecronier.pokebattle.harmony.widget.MultiEntityWidget;
import com.antoinecronier.pokebattle.harmony.widget.SingleEntityWidget;
import com.antoinecronier.pokebattle.menu.SaveMenuWrapper.SaveMenuInterface;
import com.antoinecronier.pokebattle.provider.utils.PokeTypePokemonProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeTypeProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeZoneProviderUtils;

/**
 * PokeTypePokemon create fragment.
 *
 * This fragment gives you an interface to create a PokeTypePokemon.
 */
public class PokeTypePokemonCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected PokeTypePokemon model = new PokeTypePokemon();

    /** Fields View. */
    /** nom View. */
    protected EditText nomView;
    /** attaque View. */
    protected EditText attaqueView;
    /** attaque_spe View. */
    protected EditText attaque_speView;
    /** defence View. */
    protected EditText defenceView;
    /** defence_spe View. */
    protected EditText defence_speView;
    /** vitesse View. */
    protected EditText vitesseView;
    /** pv View. */
    protected EditText pvView;
    /** pokedex View. */
    protected EditText pokedexView;
    /** The evolue chooser component. */
    protected SingleEntityWidget evolueWidget;
    /** The evolue Adapter. */
    protected SingleEntityWidget.EntityAdapter<PokeTypePokemon> 
                evolueAdapter;
    /** The types chooser component. */
    protected MultiEntityWidget typesWidget;
    /** The types Adapter. */
    protected MultiEntityWidget.EntityAdapter<PokeType> 
                typesAdapter;
    /** The zones chooser component. */
    protected MultiEntityWidget zonesWidget;
    /** The zones Adapter. */
    protected MultiEntityWidget.EntityAdapter<PokeZone> 
                zonesAdapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (EditText) view.findViewById(R.id.poketypepokemon_nom);
        this.attaqueView =
            (EditText) view.findViewById(R.id.poketypepokemon_attaque);
        this.attaque_speView =
            (EditText) view.findViewById(R.id.poketypepokemon_attaque_spe);
        this.defenceView =
            (EditText) view.findViewById(R.id.poketypepokemon_defence);
        this.defence_speView =
            (EditText) view.findViewById(R.id.poketypepokemon_defence_spe);
        this.vitesseView =
            (EditText) view.findViewById(R.id.poketypepokemon_vitesse);
        this.pvView =
            (EditText) view.findViewById(R.id.poketypepokemon_pv);
        this.pokedexView =
            (EditText) view.findViewById(R.id.poketypepokemon_pokedex);
        this.evolueAdapter = 
                new SingleEntityWidget.EntityAdapter<PokeTypePokemon>() {
            @Override
            public String entityToString(PokeTypePokemon item) {
                return String.valueOf(item.getId());
            }
        };
        this.evolueWidget =
            (SingleEntityWidget) view.findViewById(R.id.poketypepokemon_evolue_button);
        this.evolueWidget.setAdapter(this.evolueAdapter);
        this.evolueWidget.setTitle(R.string.poketypepokemon_evolue_dialog_title);
        this.typesAdapter = 
                new MultiEntityWidget.EntityAdapter<PokeType>() {
            @Override
            public String entityToString(PokeType item) {
                return String.valueOf(item.getId());
            }
        };
        this.typesWidget =
            (MultiEntityWidget) view.findViewById(R.id.poketypepokemon_types_button);
        this.typesWidget.setAdapter(this.typesAdapter);
        this.typesWidget.setTitle(R.string.poketypepokemon_types_dialog_title);
        this.zonesAdapter = 
                new MultiEntityWidget.EntityAdapter<PokeZone>() {
            @Override
            public String entityToString(PokeZone item) {
                return String.valueOf(item.getId());
            }
        };
        this.zonesWidget =
            (MultiEntityWidget) view.findViewById(R.id.poketypepokemon_zones_button);
        this.zonesWidget.setAdapter(this.zonesAdapter);
        this.zonesWidget.setTitle(R.string.poketypepokemon_zones_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

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

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setAttaque(Integer.parseInt(
                    this.attaqueView.getEditableText().toString()));

        this.model.setAttaque_spe(Integer.parseInt(
                    this.attaque_speView.getEditableText().toString()));

        this.model.setDefence(Integer.parseInt(
                    this.defenceView.getEditableText().toString()));

        this.model.setDefence_spe(Integer.parseInt(
                    this.defence_speView.getEditableText().toString()));

        this.model.setVitesse(Integer.parseInt(
                    this.vitesseView.getEditableText().toString()));

        this.model.setPv(Integer.parseInt(
                    this.pvView.getEditableText().toString()));

        this.model.setPokedex(Integer.parseInt(
                    this.pokedexView.getEditableText().toString()));

        this.model.setEvolue(this.evolueAdapter.getSelectedItem());

        this.model.setTypes(this.typesAdapter.getCheckedItems());

        this.model.setZones(this.zonesAdapter.getCheckedItems());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.poketypepokemon_nom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.attaqueView.getText().toString().trim())) {
            error = R.string.poketypepokemon_attaque_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.attaque_speView.getText().toString().trim())) {
            error = R.string.poketypepokemon_attaque_spe_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.defenceView.getText().toString().trim())) {
            error = R.string.poketypepokemon_defence_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.defence_speView.getText().toString().trim())) {
            error = R.string.poketypepokemon_defence_spe_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.vitesseView.getText().toString().trim())) {
            error = R.string.poketypepokemon_vitesse_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.pvView.getText().toString().trim())) {
            error = R.string.poketypepokemon_pv_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.pokedexView.getText().toString().trim())) {
            error = R.string.poketypepokemon_pokedex_invalid_field_error;
        }
        if (this.typesAdapter.getCheckedItems().isEmpty()) {
            error = R.string.poketypepokemon_types_invalid_field_error;
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
                R.layout.fragment_poketypepokemon_create,
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
        private final PokeTypePokemon entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final PokeTypePokemonCreateFragment fragment,
                final PokeTypePokemon entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.poketypepokemon_progress_save_title),
                    this.ctx.getString(
                            R.string.poketypepokemon_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new PokeTypePokemonProviderUtils(this.ctx).insert(
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
                                R.string.poketypepokemon_error_create));
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
        private PokeTypePokemonCreateFragment fragment;
        /** evolue list. */
        private ArrayList<PokeTypePokemon> evolueList;
        /** types list. */
        private ArrayList<PokeType> typesList;
        /** zones list. */
        private ArrayList<PokeZone> zonesList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PokeTypePokemonCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.poketypepokemon_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.poketypepokemon_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.evolueList = 
                new PokeTypePokemonProviderUtils(this.ctx).queryAll();
            this.typesList = 
                new PokeTypeProviderUtils(this.ctx).queryAll();
            this.zonesList = 
                new PokeZoneProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.evolueAdapter.loadData(this.evolueList);
            this.fragment.typesAdapter.loadData(this.typesList);
            this.fragment.zonesAdapter.loadData(this.zonesList);
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
