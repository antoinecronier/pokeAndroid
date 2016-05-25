/**************************************************************************
 * PokePositionEditFragment.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokeposition;



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
import com.antoinecronier.pokebattle.entity.PokePosition;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;

import com.antoinecronier.pokebattle.menu.SaveMenuWrapper.SaveMenuInterface;

import com.antoinecronier.pokebattle.provider.utils.PokePositionProviderUtils;
import com.antoinecronier.pokebattle.provider.contract.PokePositionContract;

/** PokePosition create fragment.
 *
 * This fragment gives you an interface to edit a PokePosition.
 *
 * @see android.app.Fragment
 */
public class PokePositionEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected PokePosition model = new PokePosition();

    /** curr.fields View. */
    /** x View. */
    protected EditText xView;
    /** y View. */
    protected EditText yView;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.xView = (EditText) view.findViewById(
                R.id.pokeposition_x);
        this.yView = (EditText) view.findViewById(
                R.id.pokeposition_y);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        this.xView.setText(String.valueOf(this.model.getX()));
        this.yView.setText(String.valueOf(this.model.getY()));


    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setX(Integer.parseInt(
                    this.xView.getEditableText().toString()));

        this.model.setY(Integer.parseInt(
                    this.yView.getEditableText().toString()));

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.xView.getText().toString().trim())) {
            error = R.string.pokeposition_x_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.yView.getText().toString().trim())) {
            error = R.string.pokeposition_y_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_pokeposition_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (PokePosition) intent.getParcelableExtra(
                PokePositionContract.PARCEL);

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
        private final PokePosition entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final PokePositionEditFragment fragment,
                    final PokePosition entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.pokeposition_progress_save_title),
                    this.ctx.getString(
                            R.string.pokeposition_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new PokePositionProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("PokePositionEditFragment", e.getMessage());
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
                        R.string.pokeposition_error_edit));
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



    @Override
    public void onClickSave() {
        if (this.validateData()) {
            this.saveData();
            new EditTask(this, this.model).execute();
        }
    }

}
