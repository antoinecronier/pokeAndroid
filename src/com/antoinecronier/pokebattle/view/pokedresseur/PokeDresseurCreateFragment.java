/**************************************************************************
 * PokeDresseurCreateFragment.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokedresseur;

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
import com.antoinecronier.pokebattle.entity.PokeDresseur;
import com.antoinecronier.pokebattle.entity.PokeNpc;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;
import com.antoinecronier.pokebattle.harmony.widget.MultiEntityWidget;
import com.antoinecronier.pokebattle.menu.SaveMenuWrapper.SaveMenuInterface;
import com.antoinecronier.pokebattle.provider.utils.PokeDresseurProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeNpcProviderUtils;

/**
 * PokeDresseur create fragment.
 *
 * This fragment gives you an interface to create a PokeDresseur.
 */
public class PokeDresseurCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected PokeDresseur model = new PokeDresseur();

    /** Fields View. */
    /** pseudo View. */
    protected EditText pseudoView;
    /** login View. */
    protected EditText loginView;
    /** password View. */
    protected EditText passwordView;
    /** The npcs chooser component. */
    protected MultiEntityWidget npcsWidget;
    /** The npcs Adapter. */
    protected MultiEntityWidget.EntityAdapter<PokeNpc> 
                npcsAdapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.pseudoView =
            (EditText) view.findViewById(R.id.pokedresseur_pseudo);
        this.loginView =
            (EditText) view.findViewById(R.id.pokedresseur_login);
        this.passwordView =
            (EditText) view.findViewById(R.id.pokedresseur_password);
        this.npcsAdapter = 
                new MultiEntityWidget.EntityAdapter<PokeNpc>() {
            @Override
            public String entityToString(PokeNpc item) {
                return String.valueOf(item.getId());
            }
        };
        this.npcsWidget =
            (MultiEntityWidget) view.findViewById(R.id.pokedresseur_npcs_button);
        this.npcsWidget.setAdapter(this.npcsAdapter);
        this.npcsWidget.setTitle(R.string.pokedresseur_npcs_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getPseudo() != null) {
            this.pseudoView.setText(this.model.getPseudo());
        }
        if (this.model.getLogin() != null) {
            this.loginView.setText(this.model.getLogin());
        }
        if (this.model.getPassword() != null) {
            this.passwordView.setText(this.model.getPassword());
        }

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setPseudo(this.pseudoView.getEditableText().toString());

        this.model.setLogin(this.loginView.getEditableText().toString());

        this.model.setPassword(this.passwordView.getEditableText().toString());

        this.model.setNpcs(this.npcsAdapter.getCheckedItems());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.pseudoView.getText().toString().trim())) {
            error = R.string.pokedresseur_pseudo_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.loginView.getText().toString().trim())) {
            error = R.string.pokedresseur_login_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.passwordView.getText().toString().trim())) {
            error = R.string.pokedresseur_password_invalid_field_error;
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
                R.layout.fragment_pokedresseur_create,
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
        private final PokeDresseur entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final PokeDresseurCreateFragment fragment,
                final PokeDresseur entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.pokedresseur_progress_save_title),
                    this.ctx.getString(
                            R.string.pokedresseur_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new PokeDresseurProviderUtils(this.ctx).insert(
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
                                R.string.pokedresseur_error_create));
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
        private PokeDresseurCreateFragment fragment;
        /** npcs list. */
        private ArrayList<PokeNpc> npcsList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PokeDresseurCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.pokedresseur_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.pokedresseur_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.npcsList = 
                new PokeNpcProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.npcsAdapter.loadData(this.npcsList);
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
