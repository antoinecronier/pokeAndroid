/**************************************************************************
 * PokeAreneUtilsBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokeArene;



import com.antoinecronier.pokebattle.test.utils.TestUtils;
import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.fixture.PokeNpcDataLoader;

import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.fixture.PokeBadgeDataLoader;

import com.antoinecronier.pokebattle.entity.PokeZone;
import com.antoinecronier.pokebattle.fixture.PokeZoneDataLoader;

import com.antoinecronier.pokebattle.entity.PokePosition;
import com.antoinecronier.pokebattle.fixture.PokePositionDataLoader;


import java.util.ArrayList;

public abstract class PokeAreneUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static PokeArene generateRandom(android.content.Context ctx){
        PokeArene pokeArene = new PokeArene();

        pokeArene.setId(TestUtils.generateRandomInt(0,100) + 1);
        pokeArene.setNom("nom_"+TestUtils.generateRandomString(10));
        ArrayList<PokeNpc> maitres =
            new ArrayList<PokeNpc>();
        maitres.addAll(PokeNpcDataLoader.getInstance(ctx).getMap().values());
        if (!maitres.isEmpty()) {
            pokeArene.setMaitre(maitres.get(TestUtils.generateRandomInt(0, maitres.size())));
        }
        ArrayList<PokeNpc> dresseurss =
            new ArrayList<PokeNpc>();
        dresseurss.addAll(PokeNpcDataLoader.getInstance(ctx).getMap().values());
        ArrayList<PokeNpc> relatedDresseurss = new ArrayList<PokeNpc>();
        if (!dresseurss.isEmpty()) {
            relatedDresseurss.add(dresseurss.get(TestUtils.generateRandomInt(0, dresseurss.size())));
            pokeArene.setDresseurs(relatedDresseurss);
        }
        ArrayList<PokeBadge> badges =
            new ArrayList<PokeBadge>();
        badges.addAll(PokeBadgeDataLoader.getInstance(ctx).getMap().values());
        if (!badges.isEmpty()) {
            pokeArene.setBadge(badges.get(TestUtils.generateRandomInt(0, badges.size())));
        }
        ArrayList<PokeZone> zones =
            new ArrayList<PokeZone>();
        zones.addAll(PokeZoneDataLoader.getInstance(ctx).getMap().values());
        if (!zones.isEmpty()) {
            pokeArene.setZone(zones.get(TestUtils.generateRandomInt(0, zones.size())));
        }
        ArrayList<PokePosition> positions =
            new ArrayList<PokePosition>();
        positions.addAll(PokePositionDataLoader.getInstance(ctx).getMap().values());
        if (!positions.isEmpty()) {
            pokeArene.setPosition(positions.get(TestUtils.generateRandomInt(0, positions.size())));
        }

        return pokeArene;
    }

    public static boolean equals(PokeArene pokeArene1,
            PokeArene pokeArene2){
        return equals(pokeArene1, pokeArene2, true);
    }
    
    public static boolean equals(PokeArene pokeArene1,
            PokeArene pokeArene2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(pokeArene1);
        Assert.assertNotNull(pokeArene2);
        if (pokeArene1!=null && pokeArene2 !=null){
            Assert.assertEquals(pokeArene1.getId(), pokeArene2.getId());
            Assert.assertEquals(pokeArene1.getNom(), pokeArene2.getNom());
            if (pokeArene1.getMaitre() != null
                    && pokeArene2.getMaitre() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokeArene1.getMaitre().getId(),
                            pokeArene2.getMaitre().getId());
                }
            }
            if (pokeArene1.getDresseurs() != null
                    && pokeArene2.getDresseurs() != null) {
                Assert.assertEquals(pokeArene1.getDresseurs().size(),
                    pokeArene2.getDresseurs().size());
                if (checkRecursiveId) {
                    for (PokeNpc dresseurs1 : pokeArene1.getDresseurs()) {
                        boolean found = false;
                        for (PokeNpc dresseurs2 : pokeArene2.getDresseurs()) {
                            if (dresseurs1.getId() == dresseurs2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated dresseurs (id = %s) in PokeArene (id = %s)",
                                        dresseurs1.getId(),
                                        pokeArene1.getId()),
                                found);
                    }
                }
            }
            if (pokeArene1.getBadge() != null
                    && pokeArene2.getBadge() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokeArene1.getBadge().getId(),
                            pokeArene2.getBadge().getId());
                }
            }
            if (pokeArene1.getZone() != null
                    && pokeArene2.getZone() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokeArene1.getZone().getId(),
                            pokeArene2.getZone().getId());
                }
            }
            if (pokeArene1.getPosition() != null
                    && pokeArene2.getPosition() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokeArene1.getPosition().getId(),
                            pokeArene2.getPosition().getId());
                }
            }
        }

        return ret;
    }
}

