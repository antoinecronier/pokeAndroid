/**************************************************************************
 * PokeAttaqueEditFragment.java, pokebattle Android
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
import android.content.Intent;
import android.database.sqlite.SQLiteException;

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
import com.antoinecronier.pokebattle.provider.contract.PokeAttaqueContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;

/** PokeAttaque create fragment.
 *
 * This fragment gives you an interface to edit a PokeAttaque.
 *
 * @see android.app.Fragment
 */
public class PokeAttaqueEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected PokeAttaque model = new PokeAttaque();

    /** curr.fields View. */
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

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.nomView = (EditText) view.findViewById(
                R.id.pokeattaque_nom);
        this.puissanceView = (EditText) view.findViewById(
                R.id.pokeattaque_puissance);
        this.precisionView = (EditText) view.findViewById(
                R.id.pokeattaque_precision);
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

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        this.puissanceView.setText(String.valueOf(this.model.getPuissance()));
        this.precisionView.setText(String.valueOf(this.model.getPrecision()));

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
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
        final View view =
                inflater.inflate(R.layout.fragment_pokeattaque_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (PokeAttaque) intent.getParcelableExtra(
                PokeAttaqueContract.PARCEL);

        this.initializeComponent(view);
        this.loadData();

        return view;
    }

    /**
     * This class will update the entity into the DB.
     * It runs asynchronously and shows a progressDialog
     */
    public static class EditTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to update. */
        private final PokeAttaque entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final PokeAttaqueEditFragment fragment,
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
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new PokeAttaqueProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("PokeAttaqueEditFragment", e.getMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if (result > 0) {
                final HarmonyFragmentActivity activity =
                        (HarmonyFragmentActivity) this.ctx;
                activity.setResult(HarmonyFragmentActivity.RESULT_OK);
                activity.finish();
            } else {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(this.ctx.getString(
                        R.string.pokeattaque_error_edit));
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
        private PokeAttaqueEditFragment fragment;
        /** type list. */
        private ArrayList<PokeType> typeList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PokeAttaqueEditFragment fragment) {
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
            this.fragment.onTypeLoaded(this.typeList);

            this.progress.dismiss();
        }
    }

    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new EditTask(this, this.model).execute();
        }
    }

    /**
     * Called when type have been loaded.
     * @param items The loaded items
     */
    protected void onTypeLoaded(ArrayList<PokeType> items) {
        this.typeAdapter.loadData(items);
        
        if (this.model.getType() != null) {
            for (PokeType item : items) {
                if (item.getId() == this.model.getType().getId()) {
                    this.typeAdapter.selectItem(item);
                }
            }
        }
    }
}
