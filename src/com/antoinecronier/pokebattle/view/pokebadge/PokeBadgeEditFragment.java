/**************************************************************************
 * PokeBadgeEditFragment.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokebadge;

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
import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.entity.PokeBadgeBonus;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;

import com.antoinecronier.pokebattle.harmony.widget.SingleEntityWidget;
import com.antoinecronier.pokebattle.harmony.widget.EnumSpinner;
import com.antoinecronier.pokebattle.menu.SaveMenuWrapper.SaveMenuInterface;

import com.antoinecronier.pokebattle.provider.utils.PokeBadgeProviderUtils;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;

/** PokeBadge create fragment.
 *
 * This fragment gives you an interface to edit a PokeBadge.
 *
 * @see android.app.Fragment
 */
public class PokeBadgeEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected PokeBadge model = new PokeBadge();

    /** curr.fields View. */
    /** nom View. */
    protected EditText nomView;
    /** bonus View. */
    protected EnumSpinner bonusView;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.nomView = (EditText) view.findViewById(
                R.id.pokebadge_nom);
        this.bonusView = (EnumSpinner) view.findViewById(
                R.id.pokebadge_bonus);
        this.bonusView.setEnum(PokeBadgeBonus.class);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        if (this.model.getBonus() != null) {
            this.bonusView.setSelectedItem(this.model.getBonus());
        }

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setBonus((PokeBadgeBonus) this.bonusView.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.pokebadge_nom_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_pokebadge_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (PokeBadge) intent.getParcelableExtra(
                PokeBadgeContract.PARCEL);

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
        private final PokeBadge entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final PokeBadgeEditFragment fragment,
                    final PokeBadge entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.pokebadge_progress_save_title),
                    this.ctx.getString(
                            R.string.pokebadge_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new PokeBadgeProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("PokeBadgeEditFragment", e.getMessage());
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
                        R.string.pokebadge_error_edit));
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
        private PokeBadgeEditFragment fragment;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PokeBadgeEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.pokebadge_progress_load_relations_title),
                this.ctx.getString(
                    R.string.pokebadge_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

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

}
