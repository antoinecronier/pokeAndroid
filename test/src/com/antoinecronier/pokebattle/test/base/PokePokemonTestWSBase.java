/**************************************************************************
 * PokePokemonTestWSBase.java, pokebattle Android
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

import com.antoinecronier.pokebattle.data.PokePokemonWebServiceClientAdapter;
import com.antoinecronier.pokebattle.data.RestClient.RequestConstants;
import com.antoinecronier.pokebattle.entity.PokePokemon;
import com.antoinecronier.pokebattle.fixture.PokePokemonDataLoader;
import com.antoinecronier.pokebattle.test.utils.PokePokemonUtils;
import com.antoinecronier.pokebattle.test.utils.TestUtils;

import com.google.mockwebserver.MockResponse;

import junit.framework.Assert;

/** PokePokemon Web Service Test.
 * 
 * @see android.app.Fragment
 */
public abstract class PokePokemonTestWSBase extends TestWSBase {
    /** model {@link PokePokemon}. */
    protected PokePokemon model;
    /** web {@link PokePokemonWebServiceClientAdapter}. */
    protected PokePokemonWebServiceClientAdapter web;
    /** entities ArrayList<PokePokemon>. */
    protected ArrayList<PokePokemon> entities;
    /** nbEntities Number of entities. */
    protected int nbEntities = 0;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        String host = this.server.getHostName();
        int port = this.server.getPort();

        this.web = new PokePokemonWebServiceClientAdapter(
                this.ctx, host, port, RequestConstants.HTTP);
        
        this.entities = new ArrayList<PokePokemon>();        
        this.entities.addAll(PokePokemonDataLoader.getInstance(this.ctx).getMap().values());
        
        if (entities.size() > 0) {
            this.model = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += PokePokemonDataLoader.getInstance(this.ctx).getMap().size();
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
        this.server.enqueue(new MockResponse().setBody("{PokePokemons :"
            + this.web.itemsToJson(this.entities).toString() + "}"));

        ArrayList<PokePokemon> pokePokemonList = 
                new ArrayList<PokePokemon>();

        int result = this.web.getAll(pokePokemonList);

        Assert.assertEquals(pokePokemonList.size(), this.entities.size());
    }
    
    /** Test case Update Entity. */
    public void testUpdate() {
        this.server.enqueue(new MockResponse().setBody("{'result'='1'}"));

        int result = this.web.update(this.model);

        Assert.assertTrue(result >= 0);
        
        this.server.enqueue(new MockResponse().setBody(
                this.web.itemToJson(this.model).toString()));

        PokePokemon item = new PokePokemon();
        item.setId(this.model.getId());

        result = this.web.get(item);
        
        PokePokemonUtils.equals(this.model, item);
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
