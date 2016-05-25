/**************************************************************************
 * PokeTypeEditFragment.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokeType;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;
import com.antoinecronier.pokebattle.harmony.widget.MultiEntityWidget;
import com.antoinecronier.pokebattle.harmony.widget.SingleEntityWidget;
import com.antoinecronier.pokebattle.menu.SaveMenuWrapper.SaveMenuInterface;
import com.antoinecronier.pokebattle.provider.PokeTypeProviderAdapter;
import com.antoinecronier.pokebattle.provider.utils.PokeTypeProviderUtils;
import com.antoinecronier.pokebattle.data.PokeTypeSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeTypeSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;

/** PokeType create fragment.
 *
 * This fragment gives you an interface to edit a PokeType.
 *
 * @see android.app.Fragment
 */
public class PokeTypeEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected PokeType model = new PokeType();

    /** curr.fields View. */
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

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.nomView = (EditText) view.findViewById(
                R.id.poketype_nom);
        this.modificateurView = (EditText) view.findViewById(
                R.id.poketype_modificateur);
        this.typeFortAdapter =
                new MultiEntityWidget.EntityAdapter<PokeType>() {
            @Override
            public String entityToString(PokeType item) {
                return String.valueOf(item.getId());
            }
        };
        this.typeFortWidget = (MultiEntityWidget) view.findViewById(
                        R.id.poketype_typefort_button);
        this.typeFortWidget.setAdapter(this.typeFortAdapter);
        this.typeFortWidget.setTitle(R.string.poketype_typefort_dialog_title);
        this.typeFaibleAdapter =
                new MultiEntityWidget.EntityAdapter<PokeType>() {
            @Override
            public String entityToString(PokeType item) {
                return String.valueOf(item.getId());
            }
        };
        this.typeFaibleWidget = (MultiEntityWidget) view.findViewById(
                        R.id.poketype_typefaible_button);
        this.typeFaibleWidget.setAdapter(this.typeFaibleAdapter);
        this.typeFaibleWidget.setTitle(R.string.poketype_typefaible_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        this.modificateurView.setText(String.valueOf(this.model.getModificateur()));

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
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
        final View view =
                inflater.inflate(R.layout.fragment_poketype_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (PokeType) intent.getParcelableExtra(
                PokeTypeContract.PARCEL);

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
        private final PokeType entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final PokeTypeEditFragment fragment,
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
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new PokeTypeProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("PokeTypeEditFragment", e.getMessage());
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
                        R.string.poketype_error_edit));
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
        private PokeTypeEditFragment fragment;
        /** typeFort list. */
        private ArrayList<PokeType> typeFortList;
    /** typeFort list. */
        private ArrayList<PokeType> associatedTypeFortList;
        /** typeFaible list. */
        private ArrayList<PokeType> typeFaibleList;
    /** typeFaible list. */
        private ArrayList<PokeType> associatedTypeFaibleList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PokeTypeEditFragment fragment) {
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
            Uri typeFortUri = PokeTypeProviderAdapter.POKETYPE_URI;
            typeFortUri = Uri.withAppendedPath(typeFortUri, 
                                    String.valueOf(this.fragment.model.getId()));
            typeFortUri = Uri.withAppendedPath(typeFortUri, "typeFort");
            android.database.Cursor typeFortCursor = 
                    this.ctx.getContentResolver().query(
                            typeFortUri,
                            new String[]{PokeTypeContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedTypeFortList = new ArrayList<PokeType>();
            if (typeFortCursor != null && typeFortCursor.getCount() > 0) {
                while (typeFortCursor.moveToNext()) {
                    int typeFortId = typeFortCursor.getInt(
                            typeFortCursor.getColumnIndex(PokeTypeContract.COL_ID));
                    for (PokeType typeFort : this.typeFortList) {
                        if (typeFort.getId() ==  typeFortId) {
                            this.associatedTypeFortList.add(typeFort);
                        }
                    }
                }
                typeFortCursor.close();
            }
            this.typeFaibleList = 
                new PokeTypeProviderUtils(this.ctx).queryAll();
            Uri typeFaibleUri = PokeTypeProviderAdapter.POKETYPE_URI;
            typeFaibleUri = Uri.withAppendedPath(typeFaibleUri, 
                                    String.valueOf(this.fragment.model.getId()));
            typeFaibleUri = Uri.withAppendedPath(typeFaibleUri, "typeFaible");
            android.database.Cursor typeFaibleCursor = 
                    this.ctx.getContentResolver().query(
                            typeFaibleUri,
                            new String[]{PokeTypeContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedTypeFaibleList = new ArrayList<PokeType>();
            if (typeFaibleCursor != null && typeFaibleCursor.getCount() > 0) {
                while (typeFaibleCursor.moveToNext()) {
                    int typeFaibleId = typeFaibleCursor.getInt(
                            typeFaibleCursor.getColumnIndex(PokeTypeContract.COL_ID));
                    for (PokeType typeFaible : this.typeFaibleList) {
                        if (typeFaible.getId() ==  typeFaibleId) {
                            this.associatedTypeFaibleList.add(typeFaible);
                        }
                    }
                }
                typeFaibleCursor.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.model.setTypeFort(this.associatedTypeFortList);
            this.fragment.onTypeFortLoaded(this.typeFortList);
            this.fragment.model.setTypeFaible(this.associatedTypeFaibleList);
            this.fragment.onTypeFaibleLoaded(this.typeFaibleList);

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
     * Called when typeFort have been loaded.
     * @param items The loaded items
     */
    protected void onTypeFortLoaded(ArrayList<PokeType> items) {
        this.typeFortAdapter.loadData(items);
        this.typeFortAdapter.setCheckedItems(this.model.getTypeFort());
    }
    /**
     * Called when typeFaible have been loaded.
     * @param items The loaded items
     */
    protected void onTypeFaibleLoaded(ArrayList<PokeType> items) {
        this.typeFaibleAdapter.loadData(items);
        this.typeFaibleAdapter.setCheckedItems(this.model.getTypeFaible());
    }
}
