/**************************************************************************
 * PokeTypePokemonTestProviderBase.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.antoinecronier.pokebattle.provider.PokeTypePokemonProviderAdapter;
import com.antoinecronier.pokebattle.provider.utils.PokeTypePokemonProviderUtils;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;

import com.antoinecronier.pokebattle.data.PokeTypePokemonSQLiteAdapter;

import com.antoinecronier.pokebattle.entity.PokeTypePokemon;

import com.antoinecronier.pokebattle.fixture.PokeTypePokemonDataLoader;

import java.util.ArrayList;
import com.antoinecronier.pokebattle.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** PokeTypePokemon database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokeTypePokemonTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class PokeTypePokemonTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected PokeTypePokemonSQLiteAdapter adapter;

    protected PokeTypePokemon entity;
    protected ContentResolver provider;
    protected PokeTypePokemonProviderUtils providerUtils;

    protected ArrayList<PokeTypePokemon> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new PokeTypePokemonSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<PokeTypePokemon>();
        this.entities.addAll(PokeTypePokemonDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += PokeTypePokemonDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new PokeTypePokemonProviderUtils(this.getContext());
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /********** Direct Provider calls. *******/

    /** Test case Create Entity */
    @SmallTest
    public void testCreate() {
        Uri result = null;
        if (this.entity != null) {
            PokeTypePokemon pokeTypePokemon = PokeTypePokemonUtils.generateRandom(this.ctx);

            try {
                ContentValues values = PokeTypePokemonContract.itemToContentValues(pokeTypePokemon);
                values.remove(PokeTypePokemonContract.COL_ID);
                result = this.provider.insert(PokeTypePokemonProviderAdapter.POKETYPEPOKEMON_URI, values);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertNotNull(result);
            Assert.assertTrue(Integer.parseInt(result.getPathSegments().get(1)) > 0);        
            
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        PokeTypePokemon result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        PokeTypePokemonProviderAdapter.POKETYPEPOKEMON_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = PokeTypePokemonContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            PokeTypePokemonUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<PokeTypePokemon> result = null;
        try {
            android.database.Cursor c = this.provider.query(PokeTypePokemonProviderAdapter.POKETYPEPOKEMON_URI, this.adapter.getCols(), null, null, null);
            result = PokeTypePokemonContract.cursorToItems(c);
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(result);
        if (result != null) {
            Assert.assertEquals(result.size(), this.nbEntities);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            PokeTypePokemon pokeTypePokemon = PokeTypePokemonUtils.generateRandom(this.ctx);

            try {
                pokeTypePokemon.setId(this.entity.getId());

                ContentValues values = PokeTypePokemonContract.itemToContentValues(pokeTypePokemon);
                result = this.provider.update(
                    Uri.parse(PokeTypePokemonProviderAdapter.POKETYPEPOKEMON_URI
                        + "/"
                        + pokeTypePokemon.getId()),
                    values,
                    null,
                    null);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertTrue(result > 0);
        }
    }

    /** Test case UpdateAll Entity */
    @SmallTest
    public void testUpdateAll() {
        int result = -1;
        if (this.entities != null) {
            if (this.entities.size() > 0) {
                PokeTypePokemon pokeTypePokemon = PokeTypePokemonUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = PokeTypePokemonContract.itemToContentValues(pokeTypePokemon);
                    values.remove(PokeTypePokemonContract.COL_ID);
                    values.remove(PokeTypePokemonContract.COL_POKEDEX);
    
                    result = this.provider.update(PokeTypePokemonProviderAdapter.POKETYPEPOKEMON_URI, values, null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    
                Assert.assertEquals(result, this.nbEntities);
            }
        }
    }

    /** Test case Delete Entity */
    @SmallTest
    public void testDelete() {
        int result = -1;
        if (this.entity != null) {
            try {
                result = this.provider.delete(
                        Uri.parse(PokeTypePokemonProviderAdapter.POKETYPEPOKEMON_URI
                            + "/" 
                            + this.entity.getId()),
                        null,
                        null);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Assert.assertTrue(result >= 0);
        }

    }

    /** Test case DeleteAll Entity */
    @SmallTest
    public void testDeleteAll() {
        int result = -1;
        if (this.entities != null) {
            if (this.entities.size() > 0) {
    
                try {
                    result = this.provider.delete(PokeTypePokemonProviderAdapter.POKETYPEPOKEMON_URI, null, null);
    
                } catch (Exception e) {
                    e.printStackTrace();
                }
    
                Assert.assertEquals(result, this.nbEntities);
            }
        }
    }

    /****** Provider Utils calls ********/

    /** Test case Read Entity by provider utils. */
    @SmallTest
    public void testUtilsRead() {
        PokeTypePokemon result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            PokeTypePokemonUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<PokeTypePokemon> result = null;
        result = this.providerUtils.queryAll();

        Assert.assertNotNull(result);
        if (result != null) {
            Assert.assertEquals(result.size(), this.nbEntities);
        }
    }

    /** Test case Update Entity by provider utils. */
    @SmallTest
    public void testUtilsUpdate() {
        int result = -1;
        if (this.entity != null) {
            PokeTypePokemon pokeTypePokemon = PokeTypePokemonUtils.generateRandom(this.ctx);

            pokeTypePokemon.setId(this.entity.getId());
            result = this.providerUtils.update(pokeTypePokemon);

            Assert.assertTrue(result > 0);
        }
    }


    /** Test case Delete Entity by provider utils. */
    @SmallTest
    public void testUtilsDelete() {
        int result = -1;
        if (this.entity != null) {
            result = this.providerUtils.delete(this.entity);
            Assert.assertTrue(result >= 0);
        }

    }
}
