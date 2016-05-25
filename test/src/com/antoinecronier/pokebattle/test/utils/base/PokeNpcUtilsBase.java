/**************************************************************************
 * PokeNpcUtilsBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokeNpc;



import com.antoinecronier.pokebattle.test.utils.TestUtils;
import com.antoinecronier.pokebattle.entity.PokeObjet;
import com.antoinecronier.pokebattle.fixture.PokeObjetDataLoader;

import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.fixture.PokeBadgeDataLoader;

import com.antoinecronier.pokebattle.entity.PokePokemon;
import com.antoinecronier.pokebattle.fixture.PokePokemonDataLoader;

import com.antoinecronier.pokebattle.entity.PokePosition;
import com.antoinecronier.pokebattle.fixture.PokePositionDataLoader;

import com.antoinecronier.pokebattle.entity.PokeZone;
import com.antoinecronier.pokebattle.fixture.PokeZoneDataLoader;


import com.antoinecronier.pokebattle.entity.PokeProfession;
import java.util.ArrayList;

public abstract class PokeNpcUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static PokeNpc generateRandom(android.content.Context ctx){
        PokeNpc pokeNpc = new PokeNpc();

        pokeNpc.setId(TestUtils.generateRandomInt(0,100) + 1);
        pokeNpc.setNom("nom_"+TestUtils.generateRandomString(10));
        pokeNpc.setProfession(PokeProfession.values()[TestUtils.generateRandomInt(0,PokeProfession.values().length)]);
        pokeNpc.setDescription("description_"+TestUtils.generateRandomString(10));
        ArrayList<PokeObjet> objetss =
            new ArrayList<PokeObjet>();
        objetss.addAll(PokeObjetDataLoader.getInstance(ctx).getMap().values());
        ArrayList<PokeObjet> relatedObjetss = new ArrayList<PokeObjet>();
        if (!objetss.isEmpty()) {
            relatedObjetss.add(objetss.get(TestUtils.generateRandomInt(0, objetss.size())));
            pokeNpc.setObjets(relatedObjetss);
        }
        ArrayList<PokeBadge> badges =
            new ArrayList<PokeBadge>();
        badges.addAll(PokeBadgeDataLoader.getInstance(ctx).getMap().values());
        ArrayList<PokeBadge> relatedBadges = new ArrayList<PokeBadge>();
        if (!badges.isEmpty()) {
            relatedBadges.add(badges.get(TestUtils.generateRandomInt(0, badges.size())));
            pokeNpc.setBadge(relatedBadges);
        }
        ArrayList<PokePokemon> pokemonss =
            new ArrayList<PokePokemon>();
        pokemonss.addAll(PokePokemonDataLoader.getInstance(ctx).getMap().values());
        ArrayList<PokePokemon> relatedPokemonss = new ArrayList<PokePokemon>();
        if (!pokemonss.isEmpty()) {
            relatedPokemonss.add(pokemonss.get(TestUtils.generateRandomInt(0, pokemonss.size())));
            pokeNpc.setPokemons(relatedPokemonss);
        }
        ArrayList<PokePokemon> teams =
            new ArrayList<PokePokemon>();
        teams.addAll(PokePokemonDataLoader.getInstance(ctx).getMap().values());
        ArrayList<PokePokemon> relatedTeams = new ArrayList<PokePokemon>();
        if (!teams.isEmpty()) {
            relatedTeams.add(teams.get(TestUtils.generateRandomInt(0, teams.size())));
            pokeNpc.setTeam(relatedTeams);
        }
        ArrayList<PokePosition> positions =
            new ArrayList<PokePosition>();
        positions.addAll(PokePositionDataLoader.getInstance(ctx).getMap().values());
        if (!positions.isEmpty()) {
            pokeNpc.setPosition(positions.get(TestUtils.generateRandomInt(0, positions.size())));
        }
        ArrayList<PokeZone> zones =
            new ArrayList<PokeZone>();
        zones.addAll(PokeZoneDataLoader.getInstance(ctx).getMap().values());
        if (!zones.isEmpty()) {
            pokeNpc.setZone(zones.get(TestUtils.generateRandomInt(0, zones.size())));
        }

        return pokeNpc;
    }

    public static boolean equals(PokeNpc pokeNpc1,
            PokeNpc pokeNpc2){
        return equals(pokeNpc1, pokeNpc2, true);
    }
    
    public static boolean equals(PokeNpc pokeNpc1,
            PokeNpc pokeNpc2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(pokeNpc1);
        Assert.assertNotNull(pokeNpc2);
        if (pokeNpc1!=null && pokeNpc2 !=null){
            Assert.assertEquals(pokeNpc1.getId(), pokeNpc2.getId());
            Assert.assertEquals(pokeNpc1.getNom(), pokeNpc2.getNom());
            //TODO : Manage field type : enum / enum
            Assert.assertEquals(pokeNpc1.getDescription(), pokeNpc2.getDescription());
            if (pokeNpc1.getObjets() != null
                    && pokeNpc2.getObjets() != null) {
                Assert.assertEquals(pokeNpc1.getObjets().size(),
                    pokeNpc2.getObjets().size());
                if (checkRecursiveId) {
                    for (PokeObjet objets1 : pokeNpc1.getObjets()) {
                        boolean found = false;
                        for (PokeObjet objets2 : pokeNpc2.getObjets()) {
                            if (objets1.getId() == objets2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated objets (id = %s) in PokeNpc (id = %s)",
                                        objets1.getId(),
                                        pokeNpc1.getId()),
                                found);
                    }
                }
            }
            if (pokeNpc1.getBadge() != null
                    && pokeNpc2.getBadge() != null) {
                Assert.assertEquals(pokeNpc1.getBadge().size(),
                    pokeNpc2.getBadge().size());
                if (checkRecursiveId) {
                    for (PokeBadge badge1 : pokeNpc1.getBadge()) {
                        boolean found = false;
                        for (PokeBadge badge2 : pokeNpc2.getBadge()) {
                            if (badge1.getId() == badge2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated badge (id = %s) in PokeNpc (id = %s)",
                                        badge1.getId(),
                                        pokeNpc1.getId()),
                                found);
                    }
                }
            }
            if (pokeNpc1.getPokemons() != null
                    && pokeNpc2.getPokemons() != null) {
                Assert.assertEquals(pokeNpc1.getPokemons().size(),
                    pokeNpc2.getPokemons().size());
                if (checkRecursiveId) {
                    for (PokePokemon pokemons1 : pokeNpc1.getPokemons()) {
                        boolean found = false;
                        for (PokePokemon pokemons2 : pokeNpc2.getPokemons()) {
                            if (pokemons1.getId() == pokemons2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated pokemons (id = %s) in PokeNpc (id = %s)",
                                        pokemons1.getId(),
                                        pokeNpc1.getId()),
                                found);
                    }
                }
            }
            if (pokeNpc1.getTeam() != null
                    && pokeNpc2.getTeam() != null) {
                Assert.assertEquals(pokeNpc1.getTeam().size(),
                    pokeNpc2.getTeam().size());
                if (checkRecursiveId) {
                    for (PokePokemon team1 : pokeNpc1.getTeam()) {
                        boolean found = false;
                        for (PokePokemon team2 : pokeNpc2.getTeam()) {
                            if (team1.getId() == team2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated team (id = %s) in PokeNpc (id = %s)",
                                        team1.getId(),
                                        pokeNpc1.getId()),
                                found);
                    }
                }
            }
            if (pokeNpc1.getPosition() != null
                    && pokeNpc2.getPosition() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokeNpc1.getPosition().getId(),
                            pokeNpc2.getPosition().getId());
                }
            }
            if (pokeNpc1.getZone() != null
                    && pokeNpc2.getZone() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokeNpc1.getZone().getId(),
                            pokeNpc2.getZone().getId());
                }
            }
        }

        return ret;
    }
}

