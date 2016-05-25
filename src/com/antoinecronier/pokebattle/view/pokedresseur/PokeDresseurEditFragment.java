/**************************************************************************
 * PokeDresseurEditFragment.java, pokebattle Android
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
import android.content.Intent;
import android.database.sqlite.SQLiteException;
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
import com.antoinecronier.pokebattle.provider.PokeDresseurProviderAdapter;
import com.antoinecronier.pokebattle.provider.utils.PokeDresseurProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeNpcProviderUtils;
import com.antoinecronier.pokebattle.data.PokeNpcSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeDresseurContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;

/** PokeDresseur create fragment.
 *
 * This fragment gives you an interface to edit a PokeDresseur.
 *
 * @see android.app.Fragment
 */
public class PokeDresseurEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected PokeDresseur model = new PokeDresseur();

    /** curr.fields View. */
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

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.pseudoView = (EditText) view.findViewById(
                R.id.pokedresseur_pseudo);
        this.loginView = (EditText) view.findViewById(
                R.id.pokedresseur_login);
        this.passwordView = (EditText) view.findViewById(
                R.id.pokedresseur_password);
        this.npcsAdapter =
                new MultiEntityWidget.EntityAdapter<PokeNpc>() {
            @Override
            public String entityToString(PokeNpc item) {
                return String.valueOf(item.getId());
            }
        };
        this.npcsWidget = (MultiEntityWidget) view.findViewById(
                        R.id.pokedresseur_npcs_button);
        this.npcsWidget.setAdapter(this.npcsAdapter);
        this.npcsWidget.setTitle(R.string.pokedresseur_npcs_dialog_title);
    }

    /** Load data from model to curr.fields view. */
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

    /** Save data from curr.fields view to model. */
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
        final View view =
                inflater.inflate(R.layout.fragment_pokedresseur_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (PokeDresseur) intent.getParcelableExtra(
                PokeDresseurContract.PARCEL);

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
        private final PokeDresseur entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final PokeDresseurEditFragment fragment,
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
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new PokeDresseurProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("PokeDresseurEditFragment", e.getMessage());
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
                        R.string.pokedresseur_error_edit));
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
        private PokeDresseurEditFragment fragment;
        /** npcs list. */
        private ArrayList<PokeNpc> npcsList;
    /** npcs list. */
        private ArrayList<PokeNpc> associatedNpcsList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PokeDresseurEditFragment fragment) {
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
            Uri npcsUri = PokeDresseurProviderAdapter.POKEDRESSEUR_URI;
            npcsUri = Uri.withAppendedPath(npcsUri, 
                                    String.valueOf(this.fragment.model.getId()));
            npcsUri = Uri.withAppendedPath(npcsUri, "npcs");
            android.database.Cursor npcsCursor = 
                    this.ctx.getContentResolver().query(
                            npcsUri,
                            new String[]{PokeNpcContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedNpcsList = new ArrayList<PokeNpc>();
            if (npcsCursor != null && npcsCursor.getCount() > 0) {
                while (npcsCursor.moveToNext()) {
                    int npcsId = npcsCursor.getInt(
                            npcsCursor.getColumnIndex(PokeNpcContract.COL_ID));
                    for (PokeNpc npcs : this.npcsList) {
                        if (npcs.getId() ==  npcsId) {
                            this.associatedNpcsList.add(npcs);
                        }
                    }
                }
                npcsCursor.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.model.setNpcs(this.associatedNpcsList);
            this.fragment.onNpcsLoaded(this.npcsList);

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
     * Called when npcs have been loaded.
     * @param items The loaded items
     */
    protected void onNpcsLoaded(ArrayList<PokeNpc> items) {
        this.npcsAdapter.loadData(items);
        this.npcsAdapter.setCheckedItems(this.model.getNpcs());
    }
}
