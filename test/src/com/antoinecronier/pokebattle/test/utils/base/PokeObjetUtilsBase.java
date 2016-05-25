/**************************************************************************
 * PokeObjetUtilsBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokeObjet;



import com.antoinecronier.pokebattle.test.utils.TestUtils;
import com.antoinecronier.pokebattle.entity.PokeTypeObjet;
import com.antoinecronier.pokebattle.fixture.PokeTypeObjetDataLoader;


import java.util.ArrayList;

public abstract class PokeObjetUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static PokeObjet generateRandom(android.content.Context ctx){
        PokeObjet pokeObjet = new PokeObjet();

        pokeObjet.setId(TestUtils.generateRandomInt(0,100) + 1);
        pokeObjet.setNom("nom_"+TestUtils.generateRandomString(10));
        pokeObjet.setQuantity(TestUtils.generateRandomInt(0,100));
        ArrayList<PokeTypeObjet> types =
            new ArrayList<PokeTypeObjet>();
        types.addAll(PokeTypeObjetDataLoader.getInstance(ctx).getMap().values());
        if (!types.isEmpty()) {
            pokeObjet.setType(types.get(TestUtils.generateRandomInt(0, types.size())));
        }

        return pokeObjet;
    }

    public static boolean equals(PokeObjet pokeObjet1,
            PokeObjet pokeObjet2){
        return equals(pokeObjet1, pokeObjet2, true);
    }
    
    public static boolean equals(PokeObjet pokeObjet1,
            PokeObjet pokeObjet2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(pokeObjet1);
        Assert.assertNotNull(pokeObjet2);
        if (pokeObjet1!=null && pokeObjet2 !=null){
            Assert.assertEquals(pokeObjet1.getId(), pokeObjet2.getId());
            Assert.assertEquals(pokeObjet1.getNom(), pokeObjet2.getNom());
            Assert.assertEquals(pokeObjet1.getQuantity(), pokeObjet2.getQuantity());
            if (pokeObjet1.getType() != null
                    && pokeObjet2.getType() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokeObjet1.getType().getId(),
                            pokeObjet2.getType().getId());
                }
            }
        }

        return ret;
    }
}

