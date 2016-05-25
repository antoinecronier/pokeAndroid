/**************************************************************************
 * PokeAreneEditFragment.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokearene;

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
import com.antoinecronier.pokebattle.entity.PokeArene;
import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.entity.PokeZone;
import com.antoinecronier.pokebattle.entity.PokePosition;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;
import com.antoinecronier.pokebattle.harmony.widget.MultiEntityWidget;
import com.antoinecronier.pokebattle.harmony.widget.SingleEntityWidget;
import com.antoinecronier.pokebattle.menu.SaveMenuWrapper.SaveMenuInterface;
import com.antoinecronier.pokebattle.provider.PokeAreneProviderAdapter;
import com.antoinecronier.pokebattle.provider.utils.PokeAreneProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeNpcProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeBadgeProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeZoneProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokePositionProviderUtils;
import com.antoinecronier.pokebattle.data.PokeNpcSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeAreneContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;
import com.antoinecronier.pokebattle.provider.contract.PokePositionContract;

/** PokeArene create fragment.
 *
 * This fragment gives you an interface to edit a PokeArene.
 *
 * @see android.app.Fragment
 */
public class PokeAreneEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected PokeArene model = new PokeArene();

    /** curr.fields View. */
    /** nom View. */
    protected EditText nomView;
    /** The maitre chooser component. */
    protected SingleEntityWidget maitreWidget;
    /** The maitre Adapter. */
    protected SingleEntityWidget.EntityAdapter<PokeNpc>
            maitreAdapter;
    /** The dresseurs chooser component. */
    protected MultiEntityWidget dresseursWidget;
    /** The dresseurs Adapter. */
    protected MultiEntityWidget.EntityAdapter<PokeNpc>
            dresseursAdapter;
    /** The badge chooser component. */
    protected SingleEntityWidget badgeWidget;
    /** The badge Adapter. */
    protected SingleEntityWidget.EntityAdapter<PokeBadge>
            badgeAdapter;
    /** The zone chooser component. */
    protected SingleEntityWidget zoneWidget;
    /** The zone Adapter. */
    protected SingleEntityWidget.EntityAdapter<PokeZone>
            zoneAdapter;
    /** The position chooser component. */
    protected SingleEntityWidget positionWidget;
    /** The position Adapter. */
    protected SingleEntityWidget.EntityAdapter<PokePosition>
            positionAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.nomView = (EditText) view.findViewById(
                R.id.pokearene_nom);
        this.maitreAdapter =
                new SingleEntityWidget.EntityAdapter<PokeNpc>() {
            @Override
            public String entityToString(PokeNpc item) {
                return String.valueOf(item.getId());
            }
        };
        this.maitreWidget =
            (SingleEntityWidget) view.findViewById(R.id.pokearene_maitre_button);
        this.maitreWidget.setAdapter(this.maitreAdapter);
        this.maitreWidget.setTitle(R.string.pokearene_maitre_dialog_title);
        this.dresseursAdapter =
                new MultiEntityWidget.EntityAdapter<PokeNpc>() {
            @Override
            public String entityToString(PokeNpc item) {
                return String.valueOf(item.getId());
            }
        };
        this.dresseursWidget = (MultiEntityWidget) view.findViewById(
                        R.id.pokearene_dresseurs_button);
        this.dresseursWidget.setAdapter(this.dresseursAdapter);
        this.dresseursWidget.setTitle(R.string.pokearene_dresseurs_dialog_title);
        this.badgeAdapter =
                new SingleEntityWidget.EntityAdapter<PokeBadge>() {
            @Override
            public String entityToString(PokeBadge item) {
                return String.valueOf(item.getId());
            }
        };
        this.badgeWidget =
            (SingleEntityWidget) view.findViewById(R.id.pokearene_badge_button);
        this.badgeWidget.setAdapter(this.badgeAdapter);
        this.badgeWidget.setTitle(R.string.pokearene_badge_dialog_title);
        this.zoneAdapter =
                new SingleEntityWidget.EntityAdapter<PokeZone>() {
            @Override
            public String entityToString(PokeZone item) {
                return String.valueOf(item.getId());
            }
        };
        this.zoneWidget =
            (SingleEntityWidget) view.findViewById(R.id.pokearene_zone_button);
        this.zoneWidget.setAdapter(this.zoneAdapter);
        this.zoneWidget.setTitle(R.string.pokearene_zone_dialog_title);
        this.positionAdapter =
                new SingleEntityWidget.EntityAdapter<PokePosition>() {
            @Override
            public String entityToString(PokePosition item) {
                return String.valueOf(item.getId());
            }
        };
        this.positionWidget =
            (SingleEntityWidget) view.findViewById(R.id.pokearene_position_button);
        this.positionWidget.setAdapter(this.positionAdapter);
        this.positionWidget.setTitle(R.string.pokearene_position_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setMaitre(this.maitreAdapter.getSelectedItem());

        this.model.setDresseurs(this.dresseursAdapter.getCheckedItems());

        this.model.setBadge(this.badgeAdapter.getSelectedItem());

        this.model.setZone(this.zoneAdapter.getSelectedItem());

        this.model.setPosition(this.positionAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.pokearene_nom_invalid_field_error;
        }
        if (this.maitreAdapter.getSelectedItem() == null) {
            error = R.string.pokearene_maitre_invalid_field_error;
        }
        if (this.badgeAdapter.getSelectedItem() == null) {
            error = R.string.pokearene_badge_invalid_field_error;
        }
        if (this.zoneAdapter.getSelectedItem() == null) {
            error = R.string.pokearene_zone_invalid_field_error;
        }
        if (this.positionAdapter.getSelectedItem() == null) {
            error = R.string.pokearene_position_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_pokearene_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (PokeArene) intent.getParcelableExtra(
                PokeAreneContract.PARCEL);

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
        private final PokeArene entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final PokeAreneEditFragment fragment,
                    final PokeArene entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.pokearene_progress_save_title),
                    this.ctx.getString(
                            R.string.pokearene_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new PokeAreneProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("PokeAreneEditFragment", e.getMessage());
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
                        R.string.pokearene_error_edit));
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
        private PokeAreneEditFragment fragment;
        /** maitre list. */
        private ArrayList<PokeNpc> maitreList;
        /** dresseurs list. */
        private ArrayList<PokeNpc> dresseursList;
    /** dresseurs list. */
        private ArrayList<PokeNpc> associatedDresseursList;
        /** badge list. */
        private ArrayList<PokeBadge> badgeList;
        /** zone list. */
        private ArrayList<PokeZone> zoneList;
        /** position list. */
        private ArrayList<PokePosition> positionList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PokeAreneEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.pokearene_progress_load_relations_title),
                this.ctx.getString(
                    R.string.pokearene_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.maitreList = 
                new PokeNpcProviderUtils(this.ctx).queryAll();
            this.dresseursList = 
                new PokeNpcProviderUtils(this.ctx).queryAll();
            Uri dresseursUri = PokeAreneProviderAdapter.POKEARENE_URI;
            dresseursUri = Uri.withAppendedPath(dresseursUri, 
                                    String.valueOf(this.fragment.model.getId()));
            dresseursUri = Uri.withAppendedPath(dresseursUri, "dresseurs");
            android.database.Cursor dresseursCursor = 
                    this.ctx.getContentResolver().query(
                            dresseursUri,
                            new String[]{PokeNpcContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedDresseursList = new ArrayList<PokeNpc>();
            if (dresseursCursor != null && dresseursCursor.getCount() > 0) {
                while (dresseursCursor.moveToNext()) {
                    int dresseursId = dresseursCursor.getInt(
                            dresseursCursor.getColumnIndex(PokeNpcContract.COL_ID));
                    for (PokeNpc dresseurs : this.dresseursList) {
                        if (dresseurs.getId() ==  dresseursId) {
                            this.associatedDresseursList.add(dresseurs);
                        }
                    }
                }
                dresseursCursor.close();
            }
            this.badgeList = 
                new PokeBadgeProviderUtils(this.ctx).queryAll();
            this.zoneList = 
                new PokeZoneProviderUtils(this.ctx).queryAll();
            this.positionList = 
                new PokePositionProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.onMaitreLoaded(this.maitreList);
            this.fragment.model.setDresseurs(this.associatedDresseursList);
            this.fragment.onDresseursLoaded(this.dresseursList);
            this.fragment.onBadgeLoaded(this.badgeList);
            this.fragment.onZoneLoaded(this.zoneList);
            this.fragment.onPositionLoaded(this.positionList);

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
     * Called when maitre have been loaded.
     * @param items The loaded items
     */
    protected void onMaitreLoaded(ArrayList<PokeNpc> items) {
        this.maitreAdapter.loadData(items);
        
        if (this.model.getMaitre() != null) {
            for (PokeNpc item : items) {
                if (item.getId() == this.model.getMaitre().getId()) {
                    this.maitreAdapter.selectItem(item);
                }
            }
        }
    }
    /**
     * Called when dresseurs have been loaded.
     * @param items The loaded items
     */
    protected void onDresseursLoaded(ArrayList<PokeNpc> items) {
        this.dresseursAdapter.loadData(items);
        this.dresseursAdapter.setCheckedItems(this.model.getDresseurs());
    }
    /**
     * Called when badge have been loaded.
     * @param items The loaded items
     */
    protected void onBadgeLoaded(ArrayList<PokeBadge> items) {
        this.badgeAdapter.loadData(items);
        
        if (this.model.getBadge() != null) {
            for (PokeBadge item : items) {
                if (item.getId() == this.model.getBadge().getId()) {
                    this.badgeAdapter.selectItem(item);
                }
            }
        }
    }
    /**
     * Called when zone have been loaded.
     * @param items The loaded items
     */
    protected void onZoneLoaded(ArrayList<PokeZone> items) {
        this.zoneAdapter.loadData(items);
        
        if (this.model.getZone() != null) {
            for (PokeZone item : items) {
                if (item.getId() == this.model.getZone().getId()) {
                    this.zoneAdapter.selectItem(item);
                }
            }
        }
    }
    /**
     * Called when position have been loaded.
     * @param items The loaded items
     */
    protected void onPositionLoaded(ArrayList<PokePosition> items) {
        this.positionAdapter.loadData(items);
        
        if (this.model.getPosition() != null) {
            for (PokePosition item : items) {
                if (item.getId() == this.model.getPosition().getId()) {
                    this.positionAdapter.selectItem(item);
                }
            }
        }
    }
}
