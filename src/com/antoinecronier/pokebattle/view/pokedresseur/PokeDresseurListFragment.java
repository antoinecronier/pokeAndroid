/**************************************************************************
 * PokeDresseurListFragment.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokedresseur;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.antoinecronier.pokebattle.criterias.base.CriteriaExpression;
import com.antoinecronier.pokebattle.menu.CrudCreateMenuWrapper.CrudCreateMenuInterface;
import com.antoinecronier.pokebattle.provider.PokeDresseurProviderAdapter;
import com.antoinecronier.pokebattle.provider.contract.PokeDresseurContract;
import com.antoinecronier.pokebattle.harmony.view.HarmonyListFragment;
import com.antoinecronier.pokebattle.R;
import com.antoinecronier.pokebattle.entity.PokeDresseur;


/** PokeDresseur list fragment.
 *
 * This fragment gives you an interface to list all your PokeDresseurs.
 *
 * @see android.app.Fragment
 */
public class PokeDresseurListFragment
        extends HarmonyListFragment<PokeDresseur>
        implements CrudCreateMenuInterface {

    /** The adapter which handles list population. */
    protected PokeDresseurListAdapter mAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        final View view =
                inflater.inflate(R.layout.fragment_pokedresseur_list,
                        null);

        this.initializeHackCustomList(view,
                R.id.pokedresseurProgressLayout,
                R.id.pokedresseurListContainer);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Give some text to display if there is no data.  In a real
        // application this would come from a resource.
        this.setEmptyText(this.getString(
                R.string.pokedresseur_empty_list));

        // Create an empty adapter we will use to display the loaded data.
        this.mAdapter = new PokeDresseurListAdapter(this.getActivity(), null);

        // Start out with a progress indicator.
        this.setListShown(false);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        /* Do click action inside your fragment here. */
    }

    @Override
    public Loader<android.database.Cursor> onCreateLoader(int id, Bundle bundle) {
        Loader<android.database.Cursor> result = null;
        CriteriaExpression crit = null;
        if (bundle != null) {
            crit = (CriteriaExpression) bundle.get(
                        CriteriaExpression.PARCELABLE);
        }

        if (crit != null) {
            result = new PokeDresseurListLoader(this.getActivity(),
                PokeDresseurProviderAdapter.POKEDRESSEUR_URI,
                PokeDresseurContract.ALIASED_COLS,
                crit,
                null);
        } else {
            result = new PokeDresseurListLoader(this.getActivity(),
                PokeDresseurProviderAdapter.POKEDRESSEUR_URI,
                PokeDresseurContract.ALIASED_COLS,
                null,
                null,
                null);
        }
        return result;
    }

    @Override
    public void onLoadFinished(
            Loader<android.database.Cursor> loader,
            android.database.Cursor data) {

        // Set the new data in the adapter.
        data.setNotificationUri(this.getActivity().getContentResolver(),
                PokeDresseurProviderAdapter.POKEDRESSEUR_URI);

        this.mAdapter.swapCursor(data);

        if (this.getListAdapter() == null) {
            this.setListAdapter(this.mAdapter);
        }

        // The list should now be shown.
        if (this.isResumed()) {
            this.setListShown(true);
        } else {
            this.setListShownNoAnimation(true);
        }

        super.onLoadFinished(loader, data);
    }

    @Override
    public void onLoaderReset(Loader<android.database.Cursor> loader) {
        // Clear the data in the adapter.
        this.mAdapter.swapCursor(null);
    }
    
    @Override
    public void onClickAdd() {
        Intent intent = new Intent(this.getActivity(),
                    PokeDresseurCreateActivity.class);
        this.startActivity(intent);
    }

}
