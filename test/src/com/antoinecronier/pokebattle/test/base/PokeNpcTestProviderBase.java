/**************************************************************************
 * PokeNpcTestProviderBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.provider.PokeNpcProviderAdapter;
import com.antoinecronier.pokebattle.provider.utils.PokeNpcProviderUtils;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;

import com.antoinecronier.pokebattle.data.PokeNpcSQLiteAdapter;

import com.antoinecronier.pokebattle.entity.PokeNpc;

import com.antoinecronier.pokebattle.fixture.PokeNpcDataLoader;

import java.util.ArrayList;
import com.antoinecronier.pokebattle.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** PokeNpc database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokeNpcTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class PokeNpcTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected PokeNpcSQLiteAdapter adapter;

    protected PokeNpc entity;
    protected ContentResolver provider;
    protected PokeNpcProviderUtils providerUtils;

    protected ArrayList<PokeNpc> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new PokeNpcSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<PokeNpc>();
        this.entities.addAll(PokeNpcDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += PokeNpcDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new PokeNpcProviderUtils(this.getContext());
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
            PokeNpc pokeNpc = PokeNpcUtils.generateRandom(this.ctx);

            try {
                ContentValues values = PokeNpcContract.itemToContentValues(pokeNpc, 0, 0);
                values.remove(PokeNpcContract.COL_ID);
                result = this.provider.insert(PokeNpcProviderAdapter.POKENPC_URI, values);

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
        PokeNpc result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        PokeNpcProviderAdapter.POKENPC_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = PokeNpcContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            PokeNpcUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<PokeNpc> result = null;
        try {
            android.database.Cursor c = this.provider.query(PokeNpcProviderAdapter.POKENPC_URI, this.adapter.getCols(), null, null, null);
            result = PokeNpcContract.cursorToItems(c);
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
            PokeNpc pokeNpc = PokeNpcUtils.generateRandom(this.ctx);

            try {
                pokeNpc.setId(this.entity.getId());

                ContentValues values = PokeNpcContract.itemToContentValues(pokeNpc, 0, 0);
                result = this.provider.update(
                    Uri.parse(PokeNpcProviderAdapter.POKENPC_URI
                        + "/"
                        + pokeNpc.getId()),
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
                PokeNpc pokeNpc = PokeNpcUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = PokeNpcContract.itemToContentValues(pokeNpc, 0, 0);
                    values.remove(PokeNpcContract.COL_ID);
    
                    result = this.provider.update(PokeNpcProviderAdapter.POKENPC_URI, values, null, null);
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
                        Uri.parse(PokeNpcProviderAdapter.POKENPC_URI
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
                    result = this.provider.delete(PokeNpcProviderAdapter.POKENPC_URI, null, null);
    
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
        PokeNpc result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            PokeNpcUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<PokeNpc> result = null;
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
            PokeNpc pokeNpc = PokeNpcUtils.generateRandom(this.ctx);

            pokeNpc.setId(this.entity.getId());
            result = this.providerUtils.update(pokeNpc);

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
