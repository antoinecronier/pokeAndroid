/**************************************************************************
 * PokeAttaqueCreateFragment.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokeattaque;

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
import com.antoinecronier.pokebattle.entity.PokeAttaque;
import com.antoinecronier.pokebattle.entity.PokeType;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;

import com.antoinecronier.pokebattle.harmony.widget.SingleEntityWidget;
import com.antoinecronier.pokebattle.menu.SaveMenuWrapper.SaveMenuInterface;
import com.antoinecronier.pokebattle.provider.utils.PokeAttaqueProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeTypeProviderUtils;

/**
 * PokeAttaque create fragment.
 *
 * This fragment gives you an interface to create a PokeAttaque.
 */
public class PokeAttaqueCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected PokeAttaque model = new PokeAttaque();

    /** Fields View. */
    /** nom View. */
    protected EditText nomView;
    /** puissance View. */
    protected EditText puissanceView;
    /** precision View. */
    protected EditText precisionView;
    /** The type chooser component. */
    protected SingleEntityWidget typeWidget;
    /** The type Adapter. */
    protected SingleEntityWidget.EntityAdapter<PokeType> 
                typeAdapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (EditText) view.findViewById(R.id.pokeattaque_nom);
        this.puissanceView =
            (EditText) view.findViewById(R.id.pokeattaque_puissance);
        this.precisionView =
            (EditText) view.findViewById(R.id.pokeattaque_precision);
        this.typeAdapter = 
                new SingleEntityWidget.EntityAdapter<PokeType>() {
            @Override
            public String entityToString(PokeType item) {
                return String.valueOf(item.getId());
            }
        };
        this.typeWidget =
            (SingleEntityWidget) view.findViewById(R.id.pokeattaque_type_button);
        this.typeWidget.setAdapter(this.typeAdapter);
        this.typeWidget.setTitle(R.string.pokeattaque_type_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        this.puissanceView.setText(String.valueOf(this.model.getPuissance()));
        this.precisionView.setText(String.valueOf(this.model.getPrecision()));

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setPuissance(Integer.parseInt(
                    this.puissanceView.getEditableText().toString()));

        this.model.setPrecision(Integer.parseInt(
                    this.precisionView.getEditableText().toString()));

        this.model.setType(this.typeAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.pokeattaque_nom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.puissanceView.getText().toString().trim())) {
            error = R.string.pokeattaque_puissance_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.precisionView.getText().toString().trim())) {
            error = R.string.pokeattaque_precision_invalid_field_error;
        }
        if (this.typeAdapter.getSelectedItem() == null) {
            error = R.string.pokeattaque_type_invalid_field_error;
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
                R.layout.fragment_pokeattaque_create,
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
        private final PokeAttaque entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final PokeAttaqueCreateFragment fragment,
                final PokeAttaque entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.pokeattaque_progress_save_title),
                    this.ctx.getString(
                            R.string.pokeattaque_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new PokeAttaqueProviderUtils(this.ctx).insert(
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
                                R.string.pokeattaque_error_create));
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
        private PokeAttaqueCreateFragment fragment;
        /** type list. */
        private ArrayList<PokeType> typeList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PokeAttaqueCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.pokeattaque_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.pokeattaque_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.typeList = 
                new PokeTypeProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.typeAdapter.loadData(this.typeList);
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
