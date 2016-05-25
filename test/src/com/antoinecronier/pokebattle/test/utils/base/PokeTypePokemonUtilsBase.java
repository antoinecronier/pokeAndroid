/**************************************************************************
 * PokeTypePokemonUtilsBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokeTypePokemon;



import com.antoinecronier.pokebattle.test.utils.TestUtils;
import com.antoinecronier.pokebattle.entity.PokeTypePokemon;
import com.antoinecronier.pokebattle.fixture.PokeTypePokemonDataLoader;

import com.antoinecronier.pokebattle.entity.PokeType;
import com.antoinecronier.pokebattle.fixture.PokeTypeDataLoader;

import com.antoinecronier.pokebattle.entity.PokeZone;
import com.antoinecronier.pokebattle.fixture.PokeZoneDataLoader;


import java.util.ArrayList;

public abstract class PokeTypePokemonUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static PokeTypePokemon generateRandom(android.content.Context ctx){
        PokeTypePokemon pokeTypePokemon = new PokeTypePokemon();

        pokeTypePokemon.setId(TestUtils.generateRandomInt(0,100) + 1);
        pokeTypePokemon.setNom("nom_"+TestUtils.generateRandomString(10));
        pokeTypePokemon.setAttaque(TestUtils.generateRandomInt(0,100));
        pokeTypePokemon.setAttaque_spe(TestUtils.generateRandomInt(0,100));
        pokeTypePokemon.setDefence(TestUtils.generateRandomInt(0,100));
        pokeTypePokemon.setDefence_spe(TestUtils.generateRandomInt(0,100));
        pokeTypePokemon.setVitesse(TestUtils.generateRandomInt(0,100));
        pokeTypePokemon.setPv(TestUtils.generateRandomInt(0,100));
        pokeTypePokemon.setPokedex(TestUtils.generateRandomInt(0,100));
        ArrayList<PokeTypePokemon> evolues =
            new ArrayList<PokeTypePokemon>();
        evolues.addAll(PokeTypePokemonDataLoader.getInstance(ctx).getMap().values());
        if (!evolues.isEmpty()) {
            pokeTypePokemon.setEvolue(evolues.get(TestUtils.generateRandomInt(0, evolues.size())));
        }
        ArrayList<PokeType> typess =
            new ArrayList<PokeType>();
        typess.addAll(PokeTypeDataLoader.getInstance(ctx).getMap().values());
        ArrayList<PokeType> relatedTypess = new ArrayList<PokeType>();
        if (!typess.isEmpty()) {
            relatedTypess.add(typess.get(TestUtils.generateRandomInt(0, typess.size())));
            pokeTypePokemon.setTypes(relatedTypess);
        }
        ArrayList<PokeZone> zoness =
            new ArrayList<PokeZone>();
        zoness.addAll(PokeZoneDataLoader.getInstance(ctx).getMap().values());
        ArrayList<PokeZone> relatedZoness = new ArrayList<PokeZone>();
        if (!zoness.isEmpty()) {
            relatedZoness.add(zoness.get(TestUtils.generateRandomInt(0, zoness.size())));
            pokeTypePokemon.setZones(relatedZoness);
        }

        return pokeTypePokemon;
    }

    public static boolean equals(PokeTypePokemon pokeTypePokemon1,
            PokeTypePokemon pokeTypePokemon2){
        return equals(pokeTypePokemon1, pokeTypePokemon2, true);
    }
    
    public static boolean equals(PokeTypePokemon pokeTypePokemon1,
            PokeTypePokemon pokeTypePokemon2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(pokeTypePokemon1);
        Assert.assertNotNull(pokeTypePokemon2);
        if (pokeTypePokemon1!=null && pokeTypePokemon2 !=null){
            Assert.assertEquals(pokeTypePokemon1.getId(), pokeTypePokemon2.getId());
            Assert.assertEquals(pokeTypePokemon1.getNom(), pokeTypePokemon2.getNom());
            Assert.assertEquals(pokeTypePokemon1.getAttaque(), pokeTypePokemon2.getAttaque());
            Assert.assertEquals(pokeTypePokemon1.getAttaque_spe(), pokeTypePokemon2.getAttaque_spe());
            Assert.assertEquals(pokeTypePokemon1.getDefence(), pokeTypePokemon2.getDefence());
            Assert.assertEquals(pokeTypePokemon1.getDefence_spe(), pokeTypePokemon2.getDefence_spe());
            Assert.assertEquals(pokeTypePokemon1.getVitesse(), pokeTypePokemon2.getVitesse());
            Assert.assertEquals(pokeTypePokemon1.getPv(), pokeTypePokemon2.getPv());
            Assert.assertEquals(pokeTypePokemon1.getPokedex(), pokeTypePokemon2.getPokedex());
            if (pokeTypePokemon1.getEvolue() != null
                    && pokeTypePokemon2.getEvolue() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokeTypePokemon1.getEvolue().getId(),
                            pokeTypePokemon2.getEvolue().getId());
                }
            }
            if (pokeTypePokemon1.getTypes() != null
                    && pokeTypePokemon2.getTypes() != null) {
                Assert.assertEquals(pokeTypePokemon1.getTypes().size(),
                    pokeTypePokemon2.getTypes().size());
                if (checkRecursiveId) {
                    for (PokeType types1 : pokeTypePokemon1.getTypes()) {
                        boolean found = false;
                        for (PokeType types2 : pokeTypePokemon2.getTypes()) {
                            if (types1.getId() == types2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated types (id = %s) in PokeTypePokemon (id = %s)",
                                        types1.getId(),
                                        pokeTypePokemon1.getId()),
                                found);
                    }
                }
            }
            if (pokeTypePokemon1.getZones() != null
                    && pokeTypePokemon2.getZones() != null) {
                Assert.assertEquals(pokeTypePokemon1.getZones().size(),
                    pokeTypePokemon2.getZones().size());
                if (checkRecursiveId) {
                    for (PokeZone zones1 : pokeTypePokemon1.getZones()) {
                        boolean found = false;
                        for (PokeZone zones2 : pokeTypePokemon2.getZones()) {
                            if (zones1.getId() == zones2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated zones (id = %s) in PokeTypePokemon (id = %s)",
                                        zones1.getId(),
                                        pokeTypePokemon1.getId()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}

