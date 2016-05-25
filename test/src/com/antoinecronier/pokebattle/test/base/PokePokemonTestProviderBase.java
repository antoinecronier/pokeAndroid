/**************************************************************************
 * PokePokemonTestProviderBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.provider.PokePokemonProviderAdapter;
import com.antoinecronier.pokebattle.provider.utils.PokePokemonProviderUtils;
import com.antoinecronier.pokebattle.provider.contract.PokePokemonContract;

import com.antoinecronier.pokebattle.data.PokePokemonSQLiteAdapter;

import com.antoinecronier.pokebattle.entity.PokePokemon;

import com.antoinecronier.pokebattle.fixture.PokePokemonDataLoader;

import java.util.ArrayList;
import com.antoinecronier.pokebattle.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** PokePokemon database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokePokemonTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class PokePokemonTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected PokePokemonSQLiteAdapter adapter;

    protected PokePokemon entity;
    protected ContentResolver provider;
    protected PokePokemonProviderUtils providerUtils;

    protected ArrayList<PokePokemon> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new PokePokemonSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<PokePokemon>();
        this.entities.addAll(PokePokemonDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += PokePokemonDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new PokePokemonProviderUtils(this.getContext());
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
            PokePokemon pokePokemon = PokePokemonUtils.generateRandom(this.ctx);

            try {
                ContentValues values = PokePokemonContract.itemToContentValues(pokePokemon, 0, 0);
                values.remove(PokePokemonContract.COL_ID);
                result = this.provider.insert(PokePokemonProviderAdapter.POKEPOKEMON_URI, values);

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
        PokePokemon result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        PokePokemonProviderAdapter.POKEPOKEMON_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = PokePokemonContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            PokePokemonUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<PokePokemon> result = null;
        try {
            android.database.Cursor c = this.provider.query(PokePokemonProviderAdapter.POKEPOKEMON_URI, this.adapter.getCols(), null, null, null);
            result = PokePokemonContract.cursorToItems(c);
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
            PokePokemon pokePokemon = PokePokemonUtils.generateRandom(this.ctx);

            try {
                pokePokemon.setId(this.entity.getId());

                ContentValues values = PokePokemonContract.itemToContentValues(pokePokemon, 0, 0);
                result = this.provider.update(
                    Uri.parse(PokePokemonProviderAdapter.POKEPOKEMON_URI
                        + "/"
                        + pokePokemon.getId()),
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
                PokePokemon pokePokemon = PokePokemonUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = PokePokemonContract.itemToContentValues(pokePokemon, 0, 0);
                    values.remove(PokePokemonContract.COL_ID);
    
                    result = this.provider.update(PokePokemonProviderAdapter.POKEPOKEMON_URI, values, null, null);
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
                        Uri.parse(PokePokemonProviderAdapter.POKEPOKEMON_URI
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
                    result = this.provider.delete(PokePokemonProviderAdapter.POKEPOKEMON_URI, null, null);
    
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
        PokePokemon result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            PokePokemonUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<PokePokemon> result = null;
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
            PokePokemon pokePokemon = PokePokemonUtils.generateRandom(this.ctx);

            pokePokemon.setId(this.entity.getId());
            result = this.providerUtils.update(pokePokemon);

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
