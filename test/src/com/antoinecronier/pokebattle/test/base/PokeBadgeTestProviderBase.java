/**************************************************************************
 * PokeBadgeTestProviderBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.provider.PokeBadgeProviderAdapter;
import com.antoinecronier.pokebattle.provider.utils.PokeBadgeProviderUtils;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;

import com.antoinecronier.pokebattle.data.PokeBadgeSQLiteAdapter;

import com.antoinecronier.pokebattle.entity.PokeBadge;

import com.antoinecronier.pokebattle.fixture.PokeBadgeDataLoader;

import java.util.ArrayList;
import com.antoinecronier.pokebattle.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** PokeBadge database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokeBadgeTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class PokeBadgeTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected PokeBadgeSQLiteAdapter adapter;

    protected PokeBadge entity;
    protected ContentResolver provider;
    protected PokeBadgeProviderUtils providerUtils;

    protected ArrayList<PokeBadge> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new PokeBadgeSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<PokeBadge>();
        this.entities.addAll(PokeBadgeDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += PokeBadgeDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new PokeBadgeProviderUtils(this.getContext());
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
            PokeBadge pokeBadge = PokeBadgeUtils.generateRandom(this.ctx);

            try {
                ContentValues values = PokeBadgeContract.itemToContentValues(pokeBadge, 0);
                values.remove(PokeBadgeContract.COL_ID);
                result = this.provider.insert(PokeBadgeProviderAdapter.POKEBADGE_URI, values);

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
        PokeBadge result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        PokeBadgeProviderAdapter.POKEBADGE_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = PokeBadgeContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            PokeBadgeUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<PokeBadge> result = null;
        try {
            android.database.Cursor c = this.provider.query(PokeBadgeProviderAdapter.POKEBADGE_URI, this.adapter.getCols(), null, null, null);
            result = PokeBadgeContract.cursorToItems(c);
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
            PokeBadge pokeBadge = PokeBadgeUtils.generateRandom(this.ctx);

            try {
                pokeBadge.setId(this.entity.getId());

                ContentValues values = PokeBadgeContract.itemToContentValues(pokeBadge, 0);
                result = this.provider.update(
                    Uri.parse(PokeBadgeProviderAdapter.POKEBADGE_URI
                        + "/"
                        + pokeBadge.getId()),
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
                PokeBadge pokeBadge = PokeBadgeUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = PokeBadgeContract.itemToContentValues(pokeBadge, 0);
                    values.remove(PokeBadgeContract.COL_ID);
    
                    result = this.provider.update(PokeBadgeProviderAdapter.POKEBADGE_URI, values, null, null);
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
                        Uri.parse(PokeBadgeProviderAdapter.POKEBADGE_URI
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
                    result = this.provider.delete(PokeBadgeProviderAdapter.POKEBADGE_URI, null, null);
    
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
        PokeBadge result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            PokeBadgeUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<PokeBadge> result = null;
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
            PokeBadge pokeBadge = PokeBadgeUtils.generateRandom(this.ctx);

            pokeBadge.setId(this.entity.getId());
            result = this.providerUtils.update(pokeBadge);

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
