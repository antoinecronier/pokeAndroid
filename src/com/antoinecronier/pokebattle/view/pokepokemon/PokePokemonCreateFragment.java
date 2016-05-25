/**************************************************************************
 * PokePokemonCreateFragment.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokepokemon;

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
import com.antoinecronier.pokebattle.entity.PokePokemon;
import com.antoinecronier.pokebattle.entity.PokeTypePokemon;
import com.antoinecronier.pokebattle.entity.PokeAttaque;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;
import com.antoinecronier.pokebattle.harmony.widget.DateTimeWidget;

import com.antoinecronier.pokebattle.harmony.widget.SingleEntityWidget;
import com.antoinecronier.pokebattle.menu.SaveMenuWrapper.SaveMenuInterface;
import com.antoinecronier.pokebattle.provider.utils.PokePokemonProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeTypePokemonProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeAttaqueProviderUtils;

/**
 * PokePokemon create fragment.
 *
 * This fragment gives you an interface to create a PokePokemon.
 */
public class PokePokemonCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected PokePokemon model = new PokePokemon();

    /** Fields View. */
    /** surnom View. */
    protected EditText surnomView;
    /** niveau View. */
    protected EditText niveauView;
    /** capture DateTime View. */
    protected DateTimeWidget captureView;
    /** The type chooser component. */
    protected SingleEntityWidget typeWidget;
    /** The type Adapter. */
    protected SingleEntityWidget.EntityAdapter<PokeTypePokemon> 
                typeAdapter;
    /** The attaque1 chooser component. */
    protected SingleEntityWidget attaque1Widget;
    /** The attaque1 Adapter. */
    protected SingleEntityWidget.EntityAdapter<PokeAttaque> 
                attaque1Adapter;
    /** The attaque2 chooser component. */
    protected SingleEntityWidget attaque2Widget;
    /** The attaque2 Adapter. */
    protected SingleEntityWidget.EntityAdapter<PokeAttaque> 
                attaque2Adapter;
    /** The attaque3 chooser component. */
    protected SingleEntityWidget attaque3Widget;
    /** The attaque3 Adapter. */
    protected SingleEntityWidget.EntityAdapter<PokeAttaque> 
                attaque3Adapter;
    /** The attaque4 chooser component. */
    protected SingleEntityWidget attaque4Widget;
    /** The attaque4 Adapter. */
    protected SingleEntityWidget.EntityAdapter<PokeAttaque> 
                attaque4Adapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.surnomView =
            (EditText) view.findViewById(R.id.pokepokemon_surnom);
        this.niveauView =
            (EditText) view.findViewById(R.id.pokepokemon_niveau);
        this.captureView =
                (DateTimeWidget) view.findViewById(R.id.pokepokemon_capture);
        this.typeAdapter = 
                new SingleEntityWidget.EntityAdapter<PokeTypePokemon>() {
            @Override
            public String entityToString(PokeTypePokemon item) {
                return String.valueOf(item.getId());
            }
        };
        this.typeWidget =
            (SingleEntityWidget) view.findViewById(R.id.pokepokemon_type_button);
        this.typeWidget.setAdapter(this.typeAdapter);
        this.typeWidget.setTitle(R.string.pokepokemon_type_dialog_title);
        this.attaque1Adapter = 
                new SingleEntityWidget.EntityAdapter<PokeAttaque>() {
            @Override
            public String entityToString(PokeAttaque item) {
                return String.valueOf(item.getId());
            }
        };
        this.attaque1Widget =
            (SingleEntityWidget) view.findViewById(R.id.pokepokemon_attaque1_button);
        this.attaque1Widget.setAdapter(this.attaque1Adapter);
        this.attaque1Widget.setTitle(R.string.pokepokemon_attaque1_dialog_title);
        this.attaque2Adapter = 
                new SingleEntityWidget.EntityAdapter<PokeAttaque>() {
            @Override
            public String entityToString(PokeAttaque item) {
                return String.valueOf(item.getId());
            }
        };
        this.attaque2Widget =
            (SingleEntityWidget) view.findViewById(R.id.pokepokemon_attaque2_button);
        this.attaque2Widget.setAdapter(this.attaque2Adapter);
        this.attaque2Widget.setTitle(R.string.pokepokemon_attaque2_dialog_title);
        this.attaque3Adapter = 
                new SingleEntityWidget.EntityAdapter<PokeAttaque>() {
            @Override
            public String entityToString(PokeAttaque item) {
                return String.valueOf(item.getId());
            }
        };
        this.attaque3Widget =
            (SingleEntityWidget) view.findViewById(R.id.pokepokemon_attaque3_button);
        this.attaque3Widget.setAdapter(this.attaque3Adapter);
        this.attaque3Widget.setTitle(R.string.pokepokemon_attaque3_dialog_title);
        this.attaque4Adapter = 
                new SingleEntityWidget.EntityAdapter<PokeAttaque>() {
            @Override
            public String entityToString(PokeAttaque item) {
                return String.valueOf(item.getId());
            }
        };
        this.attaque4Widget =
            (SingleEntityWidget) view.findViewById(R.id.pokepokemon_attaque4_button);
        this.attaque4Widget.setAdapter(this.attaque4Adapter);
        this.attaque4Widget.setTitle(R.string.pokepokemon_attaque4_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getSurnom() != null) {
            this.surnomView.setText(this.model.getSurnom());
        }
        this.niveauView.setText(String.valueOf(this.model.getNiveau()));
        if (this.model.getCapture() != null) {
            this.captureView.setDateTime(this.model.getCapture());
        }

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setSurnom(this.surnomView.getEditableText().toString());

        this.model.setNiveau(Integer.parseInt(
                    this.niveauView.getEditableText().toString()));

        this.model.setCapture(this.captureView.getDateTime());

        this.model.setType(this.typeAdapter.getSelectedItem());

        this.model.setAttaque1(this.attaque1Adapter.getSelectedItem());

        this.model.setAttaque2(this.attaque2Adapter.getSelectedItem());

        this.model.setAttaque3(this.attaque3Adapter.getSelectedItem());

        this.model.setAttaque4(this.attaque4Adapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.surnomView.getText().toString().trim())) {
            error = R.string.pokepokemon_surnom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.niveauView.getText().toString().trim())) {
            error = R.string.pokepokemon_niveau_invalid_field_error;
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
                R.layout.fragment_pokepokemon_create,
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
        private final PokePokemon entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final PokePokemonCreateFragment fragment,
                final PokePokemon entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.pokepokemon_progress_save_title),
                    this.ctx.getString(
                            R.string.pokepokemon_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new PokePokemonProviderUtils(this.ctx).insert(
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
                                R.string.pokepokemon_error_create));
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
        private PokePokemonCreateFragment fragment;
        /** type list. */
        private ArrayList<PokeTypePokemon> typeList;
        /** attaque1 list. */
        private ArrayList<PokeAttaque> attaque1List;
        /** attaque2 list. */
        private ArrayList<PokeAttaque> attaque2List;
        /** attaque3 list. */
        private ArrayList<PokeAttaque> attaque3List;
        /** attaque4 list. */
        private ArrayList<PokeAttaque> attaque4List;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PokePokemonCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.pokepokemon_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.pokepokemon_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.typeList = 
                new PokeTypePokemonProviderUtils(this.ctx).queryAll();
            this.attaque1List = 
                new PokeAttaqueProviderUtils(this.ctx).queryAll();
            this.attaque2List = 
                new PokeAttaqueProviderUtils(this.ctx).queryAll();
            this.attaque3List = 
                new PokeAttaqueProviderUtils(this.ctx).queryAll();
            this.attaque4List = 
                new PokeAttaqueProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.typeAdapter.loadData(this.typeList);
            this.fragment.attaque1Adapter.loadData(this.attaque1List);
            this.fragment.attaque2Adapter.loadData(this.attaque2List);
            this.fragment.attaque3Adapter.loadData(this.attaque3List);
            this.fragment.attaque4Adapter.loadData(this.attaque4List);
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
