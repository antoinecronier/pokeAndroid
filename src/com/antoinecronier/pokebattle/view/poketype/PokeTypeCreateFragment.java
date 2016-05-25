/**************************************************************************
 * PokeTypeCreateFragment.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.poketype;

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
import com.antoinecronier.pokebattle.entity.PokeType;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;
import com.antoinecronier.pokebattle.harmony.widget.MultiEntityWidget;
import com.antoinecronier.pokebattle.harmony.widget.SingleEntityWidget;
import com.antoinecronier.pokebattle.menu.SaveMenuWrapper.SaveMenuInterface;
import com.antoinecronier.pokebattle.provider.utils.PokeTypeProviderUtils;

/**
 * PokeType create fragment.
 *
 * This fragment gives you an interface to create a PokeType.
 */
public class PokeTypeCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected PokeType model = new PokeType();

    /** Fields View. */
    /** nom View. */
    protected EditText nomView;
    /** modificateur View. */
    protected EditText modificateurView;
    /** The typeFort chooser component. */
    protected MultiEntityWidget typeFortWidget;
    /** The typeFort Adapter. */
    protected MultiEntityWidget.EntityAdapter<PokeType> 
                typeFortAdapter;
    /** The typeFaible chooser component. */
    protected MultiEntityWidget typeFaibleWidget;
    /** The typeFaible Adapter. */
    protected MultiEntityWidget.EntityAdapter<PokeType> 
                typeFaibleAdapter;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (EditText) view.findViewById(R.id.poketype_nom);
        this.modificateurView =
            (EditText) view.findViewById(R.id.poketype_modificateur);
        this.typeFortAdapter = 
                new MultiEntityWidget.EntityAdapter<PokeType>() {
            @Override
            public String entityToString(PokeType item) {
                return String.valueOf(item.getId());
            }
        };
        this.typeFortWidget =
            (MultiEntityWidget) view.findViewById(R.id.poketype_typefort_button);
        this.typeFortWidget.setAdapter(this.typeFortAdapter);
        this.typeFortWidget.setTitle(R.string.poketype_typefort_dialog_title);
        this.typeFaibleAdapter = 
                new MultiEntityWidget.EntityAdapter<PokeType>() {
            @Override
            public String entityToString(PokeType item) {
                return String.valueOf(item.getId());
            }
        };
        this.typeFaibleWidget =
            (MultiEntityWidget) view.findViewById(R.id.poketype_typefaible_button);
        this.typeFaibleWidget.setAdapter(this.typeFaibleAdapter);
        this.typeFaibleWidget.setTitle(R.string.poketype_typefaible_dialog_title);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        this.modificateurView.setText(String.valueOf(this.model.getModificateur()));

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setModificateur(Integer.parseInt(
                    this.modificateurView.getEditableText().toString()));

        this.model.setTypeFort(this.typeFortAdapter.getCheckedItems());

        this.model.setTypeFaible(this.typeFaibleAdapter.getCheckedItems());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.poketype_nom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.modificateurView.getText().toString().trim())) {
            error = R.string.poketype_modificateur_invalid_field_error;
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
                R.layout.fragment_poketype_create,
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
        private final PokeType entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final PokeTypeCreateFragment fragment,
                final PokeType entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.poketype_progress_save_title),
                    this.ctx.getString(
                            R.string.poketype_progress_save_message));
        }

        @Override
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new PokeTypeProviderUtils(this.ctx).insert(
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
                                R.string.poketype_error_create));
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
        private PokeTypeCreateFragment fragment;
        /** typeFort list. */
        private ArrayList<PokeType> typeFortList;
        /** typeFaible list. */
        private ArrayList<PokeType> typeFaibleList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PokeTypeCreateFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.poketype_progress_load_relations_title),
                    this.ctx.getString(
                            R.string.poketype_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.typeFortList = 
                new PokeTypeProviderUtils(this.ctx).queryAll();
            this.typeFaibleList = 
                new PokeTypeProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.typeFortAdapter.loadData(this.typeFortList);
            this.fragment.typeFaibleAdapter.loadData(this.typeFaibleList);
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
