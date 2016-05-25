/**************************************************************************
 * PokePositionTestWSBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.data.PokePositionWebServiceClientAdapter;
import com.antoinecronier.pokebattle.data.RestClient.RequestConstants;
import com.antoinecronier.pokebattle.entity.PokePosition;
import com.antoinecronier.pokebattle.fixture.PokePositionDataLoader;
import com.antoinecronier.pokebattle.test.utils.PokePositionUtils;
import com.antoinecronier.pokebattle.test.utils.TestUtils;

import com.google.mockwebserver.MockResponse;

import junit.framework.Assert;

/** PokePosition Web Service Test.
 * 
 * @see android.app.Fragment
 */
public abstract class PokePositionTestWSBase extends TestWSBase {
    /** model {@link PokePosition}. */
    protected PokePosition model;
    /** web {@link PokePositionWebServiceClientAdapter}. */
    protected PokePositionWebServiceClientAdapter web;
    /** entities ArrayList<PokePosition>. */
    protected ArrayList<PokePosition> entities;
    /** nbEntities Number of entities. */
    protected int nbEntities = 0;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        String host = this.server.getHostName();
        int port = this.server.getPort();

        this.web = new PokePositionWebServiceClientAdapter(
                this.ctx, host, port, RequestConstants.HTTP);
        
        this.entities = new ArrayList<PokePosition>();        
        this.entities.addAll(PokePositionDataLoader.getInstance(this.ctx).getMap().values());
        
        if (entities.size() > 0) {
            this.model = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += PokePositionDataLoader.getInstance(this.ctx).getMap().size();
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
        this.server.enqueue(new MockResponse().setBody("{PokePositions :"
            + this.web.itemsToJson(this.entities).toString() + "}"));

        ArrayList<PokePosition> pokePositionList = 
                new ArrayList<PokePosition>();

        int result = this.web.getAll(pokePositionList);

        Assert.assertEquals(pokePositionList.size(), this.entities.size());
    }
    
    /** Test case Update Entity. */
    public void testUpdate() {
        this.server.enqueue(new MockResponse().setBody("{'result'='1'}"));

        int result = this.web.update(this.model);

        Assert.assertTrue(result >= 0);
        
        this.server.enqueue(new MockResponse().setBody(
                this.web.itemToJson(this.model).toString()));

        PokePosition item = new PokePosition();
        item.setId(this.model.getId());

        result = this.web.get(item);
        
        PokePositionUtils.equals(this.model, item);
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
