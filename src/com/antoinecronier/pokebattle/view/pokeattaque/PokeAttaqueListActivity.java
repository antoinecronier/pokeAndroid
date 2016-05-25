/**************************************************************************
 * PokeAttaqueListActivity.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.view.pokeattaque;

import com.antoinecronier.pokebattle.R;

import com.antoinecronier.pokebattle.harmony.view.HarmonyFragmentActivity;
import com.antoinecronier.pokebattle.harmony.view.HarmonyListFragment;
import com.antoinecronier.pokebattle.entity.PokeAttaque;
import com.antoinecronier.pokebattle.provider.contract.PokeAttaqueContract;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * This class will display PokeAttaque entities in a list.
 *
 * This activity has a different behaviour wether you're on a tablet or a phone.
 * - On a phone, you will have a PokeAttaque list. A click on an item will get 
 * you to PokeAttaqueShowActivity.
 * - On a tablet, the PokeAttaqueShowFragment will be displayed right next to
 * the list.
 */
public class PokeAttaqueListActivity 
        extends HarmonyFragmentActivity
        implements HarmonyListFragment.OnClickCallback,
                HarmonyListFragment.OnLoadCallback {

    /** Associated list fragment. */
    protected PokeAttaqueListFragment listFragment;
    /** Associated detail fragment if any (in case of tablet). */
    protected PokeAttaqueShowFragment detailFragment;
    /** Last selected item position in the list. */
    private int lastSelectedItemPosition = 0;
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        this.detailFragment = (PokeAttaqueShowFragment) 
                        this.getSupportFragmentManager().findFragmentById(
                                R.id.fragment_show);
        this.listFragment = (PokeAttaqueListFragment)
                        this.getSupportFragmentManager().findFragmentById(
                                R.id.fragment_list);
        
        if (this.isDualMode() && this.detailFragment != null) {
            this.listFragment.setRetainInstance(true);
            this.detailFragment.setRetainInstance(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_pokeattaque_list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(
                int requestCode,
                int resultCode,
                Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode <= SUPPORT_V4_RESULT_HACK) {
            switch(requestCode) {
                default:
                    break;
            }
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        this.lastSelectedItemPosition = position;
        
        if (this.isDualMode()) {
            this.selectListItem(this.lastSelectedItemPosition);
        } else {
            final Intent intent = new Intent(this, PokeAttaqueShowActivity.class);
            final PokeAttaque item = PokeAttaqueContract
                    .cursorToItem((android.database.Cursor)
                            l.getItemAtPosition(position));
            Bundle extras = new Bundle();
            extras.putParcelable(PokeAttaqueContract.PARCEL, item);
            intent.putExtras(extras);
            this.startActivity(intent);
        }
    }

    /** 
     * Load the detail fragment of the given item.
     * 
     * @param item The item to load
     */
    private void loadDetailFragment(PokeAttaque item) {
        this.detailFragment.update(item);
    }

    /**
     * Select a list item.
     *
     * @param listPosition The position of the item.
     */
    private void selectListItem(int listPosition) {
        int listSize = this.listFragment.getListAdapter().getCount();
        
        if (listSize > 0) {
            if (listPosition >= listSize) {
                listPosition = listSize - 1;
            } else if (listPosition < 0) {
                listPosition = 0;
            }
            
            this.listFragment.getListView().setItemChecked(listPosition, true);
            PokeAttaque item = PokeAttaqueContract.cursorToItem(
                    (android.database.Cursor) this.listFragment
                            .getListAdapter().getItem(listPosition));
            this.loadDetailFragment(item);
        } else {
            this.loadDetailFragment(null);
        }
    }

    @Override
    public void onListLoaded() {
        if (this.isDualMode() && this.lastSelectedItemPosition >= 0) {
            this.selectListItem(this.lastSelectedItemPosition);
        }
    }
}
