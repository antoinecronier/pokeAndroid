/**************************************************************************
 * PokeTypePokemonTestDBBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.data.PokeTypePokemonSQLiteAdapter;
import com.antoinecronier.pokebattle.entity.PokeTypePokemon;

import com.antoinecronier.pokebattle.fixture.PokeTypePokemonDataLoader;

import com.antoinecronier.pokebattle.test.utils.*;

import junit.framework.Assert;

/** PokeTypePokemon database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokeTypePokemonTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class PokeTypePokemonTestDBBase extends TestDBBase {
    protected android.content.Context ctx;

    protected PokeTypePokemonSQLiteAdapter adapter;

    protected PokeTypePokemon entity;
    protected ArrayList<PokeTypePokemon> entities;
    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new PokeTypePokemonSQLiteAdapter(this.ctx);
        this.adapter.open();

        this.entities = new ArrayList<PokeTypePokemon>();        
        this.entities.addAll(PokeTypePokemonDataLoader.getInstance(this.ctx).getMap().values());
        if (entities.size()>0){
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += PokeTypePokemonDataLoader.getInstance(this.ctx).getMap().size();
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
            PokeTypePokemon pokeTypePokemon = PokeTypePokemonUtils.generateRandom(this.ctx);

            result = (int)this.adapter.insert(pokeTypePokemon);

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        PokeTypePokemon result = null;
        if (this.entity != null) {
            result = this.adapter.getByID(this.entity.getId());

            PokeTypePokemonUtils.equals(this.entity, result);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            PokeTypePokemon pokeTypePokemon = PokeTypePokemonUtils.generateRandom(this.ctx);
            pokeTypePokemon.setId(this.entity.getId());

            result = (int) this.adapter.update(pokeTypePokemon);

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
