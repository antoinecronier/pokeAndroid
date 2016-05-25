/**************************************************************************
 * PokeAttaqueTestDBBase.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.test.base;

import java.util.ArrayList;

import android.test.suitebuilder.annotation.SmallTest;

import com.antoinecronier.pokebattle.data.PokeAttaqueSQLiteAdapter;
import com.antoinecronier.pokebattle.entity.PokeAttaque;

import com.antoinecronier.pokebattle.fixture.PokeAttaqueDataLoader;

import com.antoinecronier.pokebattle.test.utils.*;

import junit.framework.Assert;

/** PokeAttaque database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokeAttaqueTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class PokeAttaqueTestDBBase extends TestDBBase {
    protected android.content.Context ctx;

    protected PokeAttaqueSQLiteAdapter adapter;

    protected PokeAttaque entity;
    protected ArrayList<PokeAttaque> entities;
    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new PokeAttaqueSQLiteAdapter(this.ctx);
        this.adapter.open();

        this.entities = new ArrayList<PokeAttaque>();        
        this.entities.addAll(PokeAttaqueDataLoader.getInstance(this.ctx).getMap().values());
        if (entities.size()>0){
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += PokeAttaqueDataLoader.getInstance(this.ctx).getMap().size();
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        this.adapter.close();

        super.tearDown();
    }

    /** Test case Create Entity */
    @SmallTest
    public void testCreate() {
        int result = -1;
        if (this.entity != null) {
            PokeAttaque pokeAttaque = PokeAttaqueUtils.generateRandom(this.ctx);

            result = (int)this.adapter.insert(pokeAttaque);

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        PokeAttaque result = null;
        if (this.entity != null) {
            result = this.adapter.getByID(this.entity.getId());

            PokeAttaqueUtils.equals(this.entity, result);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            PokeAttaque pokeAttaque = PokeAttaqueUtils.generateRandom(this.ctx);
            pokeAttaque.setId(this.entity.getId());

            result = (int) this.adapter.update(pokeAttaque);

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testDelete() {
        int result = -1;
        if (this.entity != null) {
            result = (int) this.adapter.remove(this.entity.getId());
            Assert.assertTrue(result >= 0);
        }
    }
    
    /** Test the get all method. */
    @SmallTest
    public void testAll() {
        int result = this.adapter.getAll().size();
        int expectedSize = this.nbEntities;
        Assert.assertEquals(expectedSize, result);
    }
}