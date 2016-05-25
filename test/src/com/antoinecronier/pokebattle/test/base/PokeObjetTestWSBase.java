/**************************************************************************
 * PokeObjetTestWSBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.data.PokeObjetWebServiceClientAdapter;
import com.antoinecronier.pokebattle.data.RestClient.RequestConstants;
import com.antoinecronier.pokebattle.entity.PokeObjet;
import com.antoinecronier.pokebattle.fixture.PokeObjetDataLoader;
import com.antoinecronier.pokebattle.test.utils.PokeObjetUtils;
import com.antoinecronier.pokebattle.test.utils.TestUtils;

import com.google.mockwebserver.MockResponse;

import junit.framework.Assert;

/** PokeObjet Web Service Test.
 * 
 * @see android.app.Fragment
 */
public abstract class PokeObjetTestWSBase extends TestWSBase {
    /** model {@link PokeObjet}. */
    protected PokeObjet model;
    /** web {@link PokeObjetWebServiceClientAdapter}. */
    protected PokeObjetWebServiceClientAdapter web;
    /** entities ArrayList<PokeObjet>. */
    protected ArrayList<PokeObjet> entities;
    /** nbEntities Number of entities. */
    protected int nbEntities = 0;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        String host = this.server.getHostName();
        int port = this.server.getPort();

        this.web = new PokeObjetWebServiceClientAdapter(
                this.ctx, host, port, RequestConstants.HTTP);
        
        this.entities = new ArrayList<PokeObjet>();        
        this.entities.addAll(PokeObjetDataLoader.getInstance(this.ctx).getMap().values());
        
        if (entities.size() > 0) {
            this.model = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += PokeObjetDataLoader.getInstance(this.ctx).getMap().size();
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
        this.server.enqueue(new MockResponse().setBody("{PokeObjets :"
            + this.web.itemsToJson(this.entities).toString() + "}"));

        ArrayList<PokeObjet> pokeObjetList = 
                new ArrayList<PokeObjet>();

        int result = this.web.getAll(pokeObjetList);

        Assert.assertEquals(pokeObjetList.size(), this.entities.size());
    }
    
    /** Test case Update Entity. */
    public void testUpdate() {
        this.server.enqueue(new MockResponse().setBody("{'result'='1'}"));

        int result = this.web.update(this.model);

        Assert.assertTrue(result >= 0);
        
        this.server.enqueue(new MockResponse().setBody(
                this.web.itemToJson(this.model).toString()));

        PokeObjet item = new PokeObjet();
        item.setId(this.model.getId());

        result = this.web.get(item);
        
        PokeObjetUtils.equals(this.model, item);
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
