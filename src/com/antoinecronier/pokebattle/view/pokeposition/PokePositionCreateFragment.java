/**************************************************************************
 * PokePositionCreateFragment.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokePosition;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;

import com.antoinecronier.pokebattle.menu.SaveMenuWrapper.SaveMenuInterface;
import com.antoinecronier.pokebattle.provider.utils.PokePositionProviderUtils;

/**
 * PokePosition create fragment.
 *
 * This fragment gives you an interface to create a PokePosition.
 */
public class PokePositionCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected PokePosition model = new PokePosition();

    /** Fields View. */
    /** x View. */
    protected EditText xView;
    /** y View. */
    protected EditText yView;

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.xView =
            (EditText) view.findViewById(R.id.pokeposition_x);
        this.yView =
            (EditText) view.findViewById(R.id.pokeposition_y);
    }

    /** Load data from model to fields view. */
    public void loadData() {

        this.xView.setText(String.valueOf(this.model.getX()));
        this.yView.setText(String.valueOf(this.model.getY()));


    }

    /** Save data from fields view to model. */
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
        final View view = inflater.inflate(
                R.layout.fragment_pokeposition_create,
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
        private final PokePosition entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final PokePositionCreateFragment fragment,
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
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new PokePositionProviderUtils(this.ctx).insert(
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
                                R.string.pokeposition_error_create));
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
            new CreateTask(this, this.model).execute();
        }
    }
}
