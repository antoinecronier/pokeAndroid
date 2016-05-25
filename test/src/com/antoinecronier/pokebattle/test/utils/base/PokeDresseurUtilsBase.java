/**************************************************************************
 * PokeDresseurUtilsBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokeDresseur;



import com.antoinecronier.pokebattle.test.utils.TestUtils;
import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.fixture.PokeNpcDataLoader;


import java.util.ArrayList;

public abstract class PokeDresseurUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static PokeDresseur generateRandom(android.content.Context ctx){
        PokeDresseur pokeDresseur = new PokeDresseur();

        pokeDresseur.setId(TestUtils.generateRandomInt(0,100) + 1);
        pokeDresseur.setPseudo("pseudo_"+TestUtils.generateRandomString(10));
        pokeDresseur.setLogin("login_"+TestUtils.generateRandomString(10));
        pokeDresseur.setPassword("password_"+TestUtils.generateRandomString(10));
        ArrayList<PokeNpc> npcss =
            new ArrayList<PokeNpc>();
        npcss.addAll(PokeNpcDataLoader.getInstance(ctx).getMap().values());
        ArrayList<PokeNpc> relatedNpcss = new ArrayList<PokeNpc>();
        if (!npcss.isEmpty()) {
            relatedNpcss.add(npcss.get(TestUtils.generateRandomInt(0, npcss.size())));
            pokeDresseur.setNpcs(relatedNpcss);
        }

        return pokeDresseur;
    }

    public static boolean equals(PokeDresseur pokeDresseur1,
            PokeDresseur pokeDresseur2){
        return equals(pokeDresseur1, pokeDresseur2, true);
    }
    
    public static boolean equals(PokeDresseur pokeDresseur1,
            PokeDresseur pokeDresseur2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(pokeDresseur1);
        Assert.assertNotNull(pokeDresseur2);
        if (pokeDresseur1!=null && pokeDresseur2 !=null){
            Assert.assertEquals(pokeDresseur1.getId(), pokeDresseur2.getId());
            Assert.assertEquals(pokeDresseur1.getPseudo(), pokeDresseur2.getPseudo());
            Assert.assertEquals(pokeDresseur1.getLogin(), pokeDresseur2.getLogin());
            Assert.assertEquals(pokeDresseur1.getPassword(), pokeDresseur2.getPassword());
            if (pokeDresseur1.getNpcs() != null
                    && pokeDresseur2.getNpcs() != null) {
                Assert.assertEquals(pokeDresseur1.getNpcs().size(),
                    pokeDresseur2.getNpcs().size());
                if (checkRecursiveId) {
                    for (PokeNpc npcs1 : pokeDresseur1.getNpcs()) {
                        boolean found = false;
                        for (PokeNpc npcs2 : pokeDresseur2.getNpcs()) {
                            if (npcs1.getId() == npcs2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated npcs (id = %s) in PokeDresseur (id = %s)",
                                        npcs1.getId(),
                                        pokeDresseur1.getId()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}

