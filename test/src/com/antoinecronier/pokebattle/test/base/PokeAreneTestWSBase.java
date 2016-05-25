/**************************************************************************
 * PokeAreneTestWSBase.java, pokebattle Android
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

import android.database.Cursor;

import com.antoinecronier.pokebattle.data.PokeAreneWebServiceClientAdapter;
import com.antoinecronier.pokebattle.data.RestClient.RequestConstants;
import com.antoinecronier.pokebattle.entity.PokeArene;
import com.antoinecronier.pokebattle.fixture.PokeAreneDataLoader;
import com.antoinecronier.pokebattle.test.utils.PokeAreneUtils;
import com.antoinecronier.pokebattle.test.utils.TestUtils;

import com.google.mockwebserver.MockResponse;

import junit.framework.Assert;

/** PokeArene Web Service Test.
 * 
 * @see android.app.Fragment
 */
public abstract class PokeAreneTestWSBase extends TestWSBase {
    /** model {@link PokeArene}. */
    protected PokeArene model;
    /** web {@link PokeAreneWebServiceClientAdapter}. */
    protected PokeAreneWebServiceClientAdapter web;
    /** entities ArrayList<PokeArene>. */
    protected ArrayList<PokeArene> entities;
    /** nbEntities Number of entities. */
    protected int nbEntities = 0;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        String host = this.server.getHostName();
        int port = this.server.getPort();

        this.web = new PokeAreneWebServiceClientAdapter(
                this.ctx, host, port, RequestConstants.HTTP);
        
        this.entities = new ArrayList<PokeArene>();        
        this.entities.addAll(PokeAreneDataLoader.getInstance(this.ctx).getMap().values());
        
        if (entities.size() > 0) {
            this.model = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += PokeAreneDataLoader.getInstance(this.ctx).getMap().size();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    /** Test case Create Entity */
    public void testInsert() {
        this.server.enqueue(new MockResponse().setBody("{'result'='0'}"));

        int result = this.web.insert(this.model);

        Assert.assertTrue(result >= 0);
    }
    
    /** Test case Get Entity. */
    public void testGet() {
        this.server.enqueue(new MockResponse().setBody(
                this.web.itemToJson(this.model).toString()));

        int result = this.web.get(this.model);

        Assert.assertTrue(result >= 0);
    }

    /** Test case Read Entity. */
    public void testQuery() {
        this.server.enqueue(new MockResponse().setBody(
                this.web.itemToJson(this.model).toString()));

        Cursor result = this.web.query(this.model.getId());
        
        Assert.assertTrue(result.getCount() >= 0);
    }

    /** Test case get all Entity. */
    public void testGetAll() {
        this.server.enqueue(new MockResponse().setBody("{PokeArenes :"
            + this.web.itemsToJson(this.entities).toString() + "}"));

        ArrayList<PokeArene> pokeAreneList = 
                new ArrayList<PokeArene>();

        int result = this.web.getAll(pokeAreneList);

        Assert.assertEquals(pokeAreneList.size(), this.entities.size());
    }
    
    /** Test case Update Entity. */
    public void testUpdate() {
        this.server.enqueue(new MockResponse().setBody("{'result'='1'}"));

        int result = this.web.update(this.model);

        Assert.assertTrue(result >= 0);
        
        this.server.enqueue(new MockResponse().setBody(
                this.web.itemToJson(this.model).toString()));

        PokeArene item = new PokeArene();
        item.setId(this.model.getId());

        result = this.web.get(item);
        
        PokeAreneUtils.equals(this.model, item);
    }
    
    /** Test case Delete Entity. */
    public void testDelete() {
        this.server.enqueue(new MockResponse().setBody("{'result'='1'}"));

        int result = this.web.delete(this.model);

        Assert.assertTrue(result == 0);

        this.server.enqueue(new MockResponse().setBody("{}"));

        result = this.web.get(this.model);

        Assert.assertTrue(result < 0);
    }
}
