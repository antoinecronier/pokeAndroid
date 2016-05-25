/**************************************************************************
 * PokeAreneCreateFragment.java, pokebattle Android
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
import com.antoinecronier.pokebattle.provider.utils.PokeAreneProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeNpcProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeBadgeProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeZoneProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokePositionProviderUtils;

/**
 * PokeArene create fragment.
 *
 * This fragment gives you an interface to create a PokeArene.
 */
public class PokeAreneCreateFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected PokeArene model = new PokeArene();

    /** Fields View. */
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

    /** Initialize view of fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(final View view) {
        this.nomView =
            (EditText) view.findViewById(R.id.pokearene_nom);
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
        this.dresseursWidget =
            (MultiEntityWidget) view.findViewById(R.id.pokearene_dresseurs_button);
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

    /** Load data from model to fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }

        new LoadTask(this).execute();
    }

    /** Save data from fields view to model. */
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
        final View view = inflater.inflate(
                R.layout.fragment_pokearene_create,
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
        private final PokeArene entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public CreateTask(final PokeAreneCreateFragment fragment,
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
        protected Uri doInBackground(Void... params) {
            Uri result = null;

            result = new PokeAreneProviderUtils(this.ctx).insert(
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
                                R.string.pokearene_error_create));
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
        private PokeAreneCreateFragment fragment;
        /** maitre list. */
        private ArrayList<PokeNpc> maitreList;
        /** dresseurs list. */
        private ArrayList<PokeNpc> dresseursList;
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
        public LoadTask(final PokeAreneCreateFragment fragment) {
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
            this.fragment.maitreAdapter.loadData(this.maitreList);
            this.fragment.dresseursAdapter.loadData(this.dresseursList);
            this.fragment.badgeAdapter.loadData(this.badgeList);
            this.fragment.zoneAdapter.loadData(this.zoneList);
            this.fragment.positionAdapter.loadData(this.positionList);
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
