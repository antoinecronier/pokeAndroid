/**************************************************************************
 * PokeNpcEditFragment.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokenpc;

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
import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.entity.PokeObjet;
import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.entity.PokePokemon;
import com.antoinecronier.pokebattle.entity.PokePosition;
import com.antoinecronier.pokebattle.entity.PokeZone;
import com.antoinecronier.pokebattle.entity.PokeProfession;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;
import com.antoinecronier.pokebattle.harmony.view.HarmonyFragment;
import com.antoinecronier.pokebattle.harmony.widget.MultiEntityWidget;
import com.antoinecronier.pokebattle.harmony.widget.SingleEntityWidget;
import com.antoinecronier.pokebattle.harmony.widget.EnumSpinner;
import com.antoinecronier.pokebattle.menu.SaveMenuWrapper.SaveMenuInterface;
import com.antoinecronier.pokebattle.provider.PokeNpcProviderAdapter;
import com.antoinecronier.pokebattle.provider.utils.PokeNpcProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeObjetProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeBadgeProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokePokemonProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokePositionProviderUtils;
import com.antoinecronier.pokebattle.provider.utils.PokeZoneProviderUtils;
import com.antoinecronier.pokebattle.data.PokeObjetSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokeBadgeSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokePokemonSQLiteAdapter;
import com.antoinecronier.pokebattle.data.PokePokemonSQLiteAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;
import com.antoinecronier.pokebattle.provider.contract.PokeAreneContract;
import com.antoinecronier.pokebattle.provider.contract.PokeDresseurContract;
import com.antoinecronier.pokebattle.provider.contract.PokeObjetContract;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;
import com.antoinecronier.pokebattle.provider.contract.PokePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokePokemonContract;
import com.antoinecronier.pokebattle.provider.contract.PokePositionContract;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;

/** PokeNpc create fragment.
 *
 * This fragment gives you an interface to edit a PokeNpc.
 *
 * @see android.app.Fragment
 */
public class PokeNpcEditFragment extends HarmonyFragment
            implements SaveMenuInterface {
    /** Model data. */
    protected PokeNpc model = new PokeNpc();

    /** curr.fields View. */
    /** nom View. */
    protected EditText nomView;
    /** profession View. */
    protected EnumSpinner professionView;
    /** description View. */
    protected EditText descriptionView;
    /** The objets chooser component. */
    protected MultiEntityWidget objetsWidget;
    /** The objets Adapter. */
    protected MultiEntityWidget.EntityAdapter<PokeObjet>
            objetsAdapter;
    /** The badge chooser component. */
    protected MultiEntityWidget badgeWidget;
    /** The badge Adapter. */
    protected MultiEntityWidget.EntityAdapter<PokeBadge>
            badgeAdapter;
    /** The pokemons chooser component. */
    protected MultiEntityWidget pokemonsWidget;
    /** The pokemons Adapter. */
    protected MultiEntityWidget.EntityAdapter<PokePokemon>
            pokemonsAdapter;
    /** The team chooser component. */
    protected MultiEntityWidget teamWidget;
    /** The team Adapter. */
    protected MultiEntityWidget.EntityAdapter<PokePokemon>
            teamAdapter;
    /** The position chooser component. */
    protected SingleEntityWidget positionWidget;
    /** The position Adapter. */
    protected SingleEntityWidget.EntityAdapter<PokePosition>
            positionAdapter;
    /** The zone chooser component. */
    protected SingleEntityWidget zoneWidget;
    /** The zone Adapter. */
    protected SingleEntityWidget.EntityAdapter<PokeZone>
            zoneAdapter;

    /** Initialize view of curr.fields.
     *
     * @param view The layout inflating
     */
    protected void initializeComponent(View view) {
        this.nomView = (EditText) view.findViewById(
                R.id.pokenpc_nom);
        this.professionView = (EnumSpinner) view.findViewById(
                R.id.pokenpc_profession);
        this.professionView.setEnum(PokeProfession.class);
        this.descriptionView = (EditText) view.findViewById(
                R.id.pokenpc_description);
        this.objetsAdapter =
                new MultiEntityWidget.EntityAdapter<PokeObjet>() {
            @Override
            public String entityToString(PokeObjet item) {
                return String.valueOf(item.getId());
            }
        };
        this.objetsWidget = (MultiEntityWidget) view.findViewById(
                        R.id.pokenpc_objets_button);
        this.objetsWidget.setAdapter(this.objetsAdapter);
        this.objetsWidget.setTitle(R.string.pokenpc_objets_dialog_title);
        this.badgeAdapter =
                new MultiEntityWidget.EntityAdapter<PokeBadge>() {
            @Override
            public String entityToString(PokeBadge item) {
                return String.valueOf(item.getId());
            }
        };
        this.badgeWidget = (MultiEntityWidget) view.findViewById(
                        R.id.pokenpc_badge_button);
        this.badgeWidget.setAdapter(this.badgeAdapter);
        this.badgeWidget.setTitle(R.string.pokenpc_badge_dialog_title);
        this.pokemonsAdapter =
                new MultiEntityWidget.EntityAdapter<PokePokemon>() {
            @Override
            public String entityToString(PokePokemon item) {
                return String.valueOf(item.getId());
            }
        };
        this.pokemonsWidget = (MultiEntityWidget) view.findViewById(
                        R.id.pokenpc_pokemons_button);
        this.pokemonsWidget.setAdapter(this.pokemonsAdapter);
        this.pokemonsWidget.setTitle(R.string.pokenpc_pokemons_dialog_title);
        this.teamAdapter =
                new MultiEntityWidget.EntityAdapter<PokePokemon>() {
            @Override
            public String entityToString(PokePokemon item) {
                return String.valueOf(item.getId());
            }
        };
        this.teamWidget = (MultiEntityWidget) view.findViewById(
                        R.id.pokenpc_team_button);
        this.teamWidget.setAdapter(this.teamAdapter);
        this.teamWidget.setTitle(R.string.pokenpc_team_dialog_title);
        this.positionAdapter =
                new SingleEntityWidget.EntityAdapter<PokePosition>() {
            @Override
            public String entityToString(PokePosition item) {
                return String.valueOf(item.getId());
            }
        };
        this.positionWidget =
            (SingleEntityWidget) view.findViewById(R.id.pokenpc_position_button);
        this.positionWidget.setAdapter(this.positionAdapter);
        this.positionWidget.setTitle(R.string.pokenpc_position_dialog_title);
        this.zoneAdapter =
                new SingleEntityWidget.EntityAdapter<PokeZone>() {
            @Override
            public String entityToString(PokeZone item) {
                return String.valueOf(item.getId());
            }
        };
        this.zoneWidget =
            (SingleEntityWidget) view.findViewById(R.id.pokenpc_zone_button);
        this.zoneWidget.setAdapter(this.zoneAdapter);
        this.zoneWidget.setTitle(R.string.pokenpc_zone_dialog_title);
    }

    /** Load data from model to curr.fields view. */
    public void loadData() {

        if (this.model.getNom() != null) {
            this.nomView.setText(this.model.getNom());
        }
        if (this.model.getProfession() != null) {
            this.professionView.setSelectedItem(this.model.getProfession());
        }
        if (this.model.getDescription() != null) {
            this.descriptionView.setText(this.model.getDescription());
        }

        new LoadTask(this).execute();
    }

    /** Save data from curr.fields view to model. */
    public void saveData() {

        this.model.setNom(this.nomView.getEditableText().toString());

        this.model.setProfession((PokeProfession) this.professionView.getSelectedItem());

        this.model.setDescription(this.descriptionView.getEditableText().toString());

        this.model.setObjets(this.objetsAdapter.getCheckedItems());

        this.model.setBadge(this.badgeAdapter.getCheckedItems());

        this.model.setPokemons(this.pokemonsAdapter.getCheckedItems());

        this.model.setTeam(this.teamAdapter.getCheckedItems());

        this.model.setPosition(this.positionAdapter.getSelectedItem());

        this.model.setZone(this.zoneAdapter.getSelectedItem());

    }

    /** Check data is valid.
     *
     * @return true if valid
     */
    public boolean validateData() {
        int error = 0;

        if (Strings.isNullOrEmpty(
                    this.nomView.getText().toString().trim())) {
            error = R.string.pokenpc_nom_invalid_field_error;
        }
        if (Strings.isNullOrEmpty(
                    this.descriptionView.getText().toString().trim())) {
            error = R.string.pokenpc_description_invalid_field_error;
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
                inflater.inflate(R.layout.fragment_pokenpc_edit,
                        container,
                        false);

        final Intent intent =  getActivity().getIntent();
        this.model = (PokeNpc) intent.getParcelableExtra(
                PokeNpcContract.PARCEL);

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
        private final PokeNpc entity;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param entity The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public EditTask(final PokeNpcEditFragment fragment,
                    final PokeNpc entity) {
            super();
            this.ctx = fragment.getActivity();
            this.entity = entity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.pokenpc_progress_save_title),
                    this.ctx.getString(
                            R.string.pokenpc_progress_save_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                result = new PokeNpcProviderUtils(this.ctx).update(
                    this.entity);
            } catch (SQLiteException e) {
                android.util.Log.e("PokeNpcEditFragment", e.getMessage());
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
                        R.string.pokenpc_error_edit));
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
        private PokeNpcEditFragment fragment;
        /** objets list. */
        private ArrayList<PokeObjet> objetsList;
    /** objets list. */
        private ArrayList<PokeObjet> associatedObjetsList;
        /** badge list. */
        private ArrayList<PokeBadge> badgeList;
    /** badge list. */
        private ArrayList<PokeBadge> associatedBadgeList;
        /** pokemons list. */
        private ArrayList<PokePokemon> pokemonsList;
    /** pokemons list. */
        private ArrayList<PokePokemon> associatedPokemonsList;
        /** team list. */
        private ArrayList<PokePokemon> teamList;
    /** team list. */
        private ArrayList<PokePokemon> associatedTeamList;
        /** position list. */
        private ArrayList<PokePosition> positionList;
        /** zone list. */
        private ArrayList<PokeZone> zoneList;

        /**
         * Constructor of the task.
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LoadTask(final PokeNpcEditFragment fragment) {
            super();
            this.ctx = fragment.getActivity();
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = ProgressDialog.show(this.ctx,
                this.ctx.getString(
                    R.string.pokenpc_progress_load_relations_title),
                this.ctx.getString(
                    R.string.pokenpc_progress_load_relations_message));
        }

        @Override
        protected Void doInBackground(Void... params) {
            this.objetsList = 
                new PokeObjetProviderUtils(this.ctx).queryAll();
            Uri objetsUri = PokeNpcProviderAdapter.POKENPC_URI;
            objetsUri = Uri.withAppendedPath(objetsUri, 
                                    String.valueOf(this.fragment.model.getId()));
            objetsUri = Uri.withAppendedPath(objetsUri, "objets");
            android.database.Cursor objetsCursor = 
                    this.ctx.getContentResolver().query(
                            objetsUri,
                            new String[]{PokeObjetContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedObjetsList = new ArrayList<PokeObjet>();
            if (objetsCursor != null && objetsCursor.getCount() > 0) {
                while (objetsCursor.moveToNext()) {
                    int objetsId = objetsCursor.getInt(
                            objetsCursor.getColumnIndex(PokeObjetContract.COL_ID));
                    for (PokeObjet objets : this.objetsList) {
                        if (objets.getId() ==  objetsId) {
                            this.associatedObjetsList.add(objets);
                        }
                    }
                }
                objetsCursor.close();
            }
            this.badgeList = 
                new PokeBadgeProviderUtils(this.ctx).queryAll();
            Uri badgeUri = PokeNpcProviderAdapter.POKENPC_URI;
            badgeUri = Uri.withAppendedPath(badgeUri, 
                                    String.valueOf(this.fragment.model.getId()));
            badgeUri = Uri.withAppendedPath(badgeUri, "badge");
            android.database.Cursor badgeCursor = 
                    this.ctx.getContentResolver().query(
                            badgeUri,
                            new String[]{PokeBadgeContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedBadgeList = new ArrayList<PokeBadge>();
            if (badgeCursor != null && badgeCursor.getCount() > 0) {
                while (badgeCursor.moveToNext()) {
                    int badgeId = badgeCursor.getInt(
                            badgeCursor.getColumnIndex(PokeBadgeContract.COL_ID));
                    for (PokeBadge badge : this.badgeList) {
                        if (badge.getId() ==  badgeId) {
                            this.associatedBadgeList.add(badge);
                        }
                    }
                }
                badgeCursor.close();
            }
            this.pokemonsList = 
                new PokePokemonProviderUtils(this.ctx).queryAll();
            Uri pokemonsUri = PokeNpcProviderAdapter.POKENPC_URI;
            pokemonsUri = Uri.withAppendedPath(pokemonsUri, 
                                    String.valueOf(this.fragment.model.getId()));
            pokemonsUri = Uri.withAppendedPath(pokemonsUri, "pokemons");
            android.database.Cursor pokemonsCursor = 
                    this.ctx.getContentResolver().query(
                            pokemonsUri,
                            new String[]{PokePokemonContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedPokemonsList = new ArrayList<PokePokemon>();
            if (pokemonsCursor != null && pokemonsCursor.getCount() > 0) {
                while (pokemonsCursor.moveToNext()) {
                    int pokemonsId = pokemonsCursor.getInt(
                            pokemonsCursor.getColumnIndex(PokePokemonContract.COL_ID));
                    for (PokePokemon pokemons : this.pokemonsList) {
                        if (pokemons.getId() ==  pokemonsId) {
                            this.associatedPokemonsList.add(pokemons);
                        }
                    }
                }
                pokemonsCursor.close();
            }
            this.teamList = 
                new PokePokemonProviderUtils(this.ctx).queryAll();
            Uri teamUri = PokeNpcProviderAdapter.POKENPC_URI;
            teamUri = Uri.withAppendedPath(teamUri, 
                                    String.valueOf(this.fragment.model.getId()));
            teamUri = Uri.withAppendedPath(teamUri, "team");
            android.database.Cursor teamCursor = 
                    this.ctx.getContentResolver().query(
                            teamUri,
                            new String[]{PokePokemonContract.ALIASED_COL_ID},
                            null,
                            null, 
                            null);
            
            this.associatedTeamList = new ArrayList<PokePokemon>();
            if (teamCursor != null && teamCursor.getCount() > 0) {
                while (teamCursor.moveToNext()) {
                    int teamId = teamCursor.getInt(
                            teamCursor.getColumnIndex(PokePokemonContract.COL_ID));
                    for (PokePokemon team : this.teamList) {
                        if (team.getId() ==  teamId) {
                            this.associatedTeamList.add(team);
                        }
                    }
                }
                teamCursor.close();
            }
            this.positionList = 
                new PokePositionProviderUtils(this.ctx).queryAll();
            this.zoneList = 
                new PokeZoneProviderUtils(this.ctx).queryAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            this.fragment.model.setObjets(this.associatedObjetsList);
            this.fragment.onObjetsLoaded(this.objetsList);
            this.fragment.model.setBadge(this.associatedBadgeList);
            this.fragment.onBadgeLoaded(this.badgeList);
            this.fragment.model.setPokemons(this.associatedPokemonsList);
            this.fragment.onPokemonsLoaded(this.pokemonsList);
            this.fragment.model.setTeam(this.associatedTeamList);
            this.fragment.onTeamLoaded(this.teamList);
            this.fragment.onPositionLoaded(this.positionList);
            this.fragment.onZoneLoaded(this.zoneList);

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
     * Called when objets have been loaded.
     * @param items The loaded items
     */
    protected void onObjetsLoaded(ArrayList<PokeObjet> items) {
        this.objetsAdapter.loadData(items);
        this.objetsAdapter.setCheckedItems(this.model.getObjets());
    }
    /**
     * Called when badge have been loaded.
     * @param items The loaded items
     */
    protected void onBadgeLoaded(ArrayList<PokeBadge> items) {
        this.badgeAdapter.loadData(items);
        this.badgeAdapter.setCheckedItems(this.model.getBadge());
    }
    /**
     * Called when pokemons have been loaded.
     * @param items The loaded items
     */
    protected void onPokemonsLoaded(ArrayList<PokePokemon> items) {
        this.pokemonsAdapter.loadData(items);
        this.pokemonsAdapter.setCheckedItems(this.model.getPokemons());
    }
    /**
     * Called when team have been loaded.
     * @param items The loaded items
     */
    protected void onTeamLoaded(ArrayList<PokePokemon> items) {
        this.teamAdapter.loadData(items);
        this.teamAdapter.setCheckedItems(this.model.getTeam());
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
}
