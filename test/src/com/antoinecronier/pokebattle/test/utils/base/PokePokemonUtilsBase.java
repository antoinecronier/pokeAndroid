/**************************************************************************
 * PokePokemonUtilsBase.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.test.utils.base;


import junit.framework.Assert;
import com.antoinecronier.pokebattle.entity.PokePokemon;



import com.antoinecronier.pokebattle.test.utils.TestUtils;
import com.antoinecronier.pokebattle.entity.PokeTypePokemon;
import com.antoinecronier.pokebattle.fixture.PokeTypePokemonDataLoader;

import com.antoinecronier.pokebattle.entity.PokeAttaque;
import com.antoinecronier.pokebattle.fixture.PokeAttaqueDataLoader;


import java.util.ArrayList;

public abstract class PokePokemonUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static PokePokemon generateRandom(android.content.Context ctx){
        PokePokemon pokePokemon = new PokePokemon();

        pokePokemon.setId(TestUtils.generateRandomInt(0,100) + 1);
        pokePokemon.setSurnom("surnom_"+TestUtils.generateRandomString(10));
        pokePokemon.setNiveau(TestUtils.generateRandomInt(0,100));
        pokePokemon.setCapture(TestUtils.generateRandomDateTime());
        ArrayList<PokeTypePokemon> types =
            new ArrayList<PokeTypePokemon>();
        types.addAll(PokeTypePokemonDataLoader.getInstance(ctx).getMap().values());
        if (!types.isEmpty()) {
            pokePokemon.setType(types.get(TestUtils.generateRandomInt(0, types.size())));
        }
        ArrayList<PokeAttaque> attaque1s =
            new ArrayList<PokeAttaque>();
        attaque1s.addAll(PokeAttaqueDataLoader.getInstance(ctx).getMap().values());
        if (!attaque1s.isEmpty()) {
            pokePokemon.setAttaque1(attaque1s.get(TestUtils.generateRandomInt(0, attaque1s.size())));
        }
        ArrayList<PokeAttaque> attaque2s =
            new ArrayList<PokeAttaque>();
        attaque2s.addAll(PokeAttaqueDataLoader.getInstance(ctx).getMap().values());
        if (!attaque2s.isEmpty()) {
            pokePokemon.setAttaque2(attaque2s.get(TestUtils.generateRandomInt(0, attaque2s.size())));
        }
        ArrayList<PokeAttaque> attaque3s =
            new ArrayList<PokeAttaque>();
        attaque3s.addAll(PokeAttaqueDataLoader.getInstance(ctx).getMap().values());
        if (!attaque3s.isEmpty()) {
            pokePokemon.setAttaque3(attaque3s.get(TestUtils.generateRandomInt(0, attaque3s.size())));
        }
        ArrayList<PokeAttaque> attaque4s =
            new ArrayList<PokeAttaque>();
        attaque4s.addAll(PokeAttaqueDataLoader.getInstance(ctx).getMap().values());
        if (!attaque4s.isEmpty()) {
            pokePokemon.setAttaque4(attaque4s.get(TestUtils.generateRandomInt(0, attaque4s.size())));
        }

        return pokePokemon;
    }

    public static boolean equals(PokePokemon pokePokemon1,
            PokePokemon pokePokemon2){
        return equals(pokePokemon1, pokePokemon2, true);
    }
    
    public static boolean equals(PokePokemon pokePokemon1,
            PokePokemon pokePokemon2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(pokePokemon1);
        Assert.assertNotNull(pokePokemon2);
        if (pokePokemon1!=null && pokePokemon2 !=null){
            Assert.assertEquals(pokePokemon1.getId(), pokePokemon2.getId());
            Assert.assertEquals(pokePokemon1.getSurnom(), pokePokemon2.getSurnom());
            Assert.assertEquals(pokePokemon1.getNiveau(), pokePokemon2.getNiveau());
            Assert.assertEquals(pokePokemon1.getCapture(), pokePokemon2.getCapture());
            if (pokePokemon1.getType() != null
                    && pokePokemon2.getType() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokePokemon1.getType().getId(),
                            pokePokemon2.getType().getId());
                }
            }
            if (pokePokemon1.getAttaque1() != null
                    && pokePokemon2.getAttaque1() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokePokemon1.getAttaque1().getId(),
                            pokePokemon2.getAttaque1().getId());
                }
            }
            if (pokePokemon1.getAttaque2() != null
                    && pokePokemon2.getAttaque2() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokePokemon1.getAttaque2().getId(),
                            pokePokemon2.getAttaque2().getId());
                }
            }
            if (pokePokemon1.getAttaque3() != null
                    && pokePokemon2.getAttaque3() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokePokemon1.getAttaque3().getId(),
                            pokePokemon2.getAttaque3().getId());
                }
            }
            if (pokePokemon1.getAttaque4() != null
                    && pokePokemon2.getAttaque4() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokePokemon1.getAttaque4().getId(),
                            pokePokemon2.getAttaque4().getId());
                }
            }
        }

        return ret;
    }
}

