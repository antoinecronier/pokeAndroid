/**************************************************************************
 * PokeTypeObjetUtilsBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokeTypeObjet;



import com.antoinecronier.pokebattle.test.utils.TestUtils;


public abstract class PokeTypeObjetUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static PokeTypeObjet generateRandom(android.content.Context ctx){
        PokeTypeObjet pokeTypeObjet = new PokeTypeObjet();

        pokeTypeObjet.setId(TestUtils.generateRandomInt(0,100) + 1);
        pokeTypeObjet.setNom("nom_"+TestUtils.generateRandomString(10));

        return pokeTypeObjet;
    }

    public static boolean equals(PokeTypeObjet pokeTypeObjet1,
            PokeTypeObjet pokeTypeObjet2){
        return equals(pokeTypeObjet1, pokeTypeObjet2, true);
    }
    
    public static boolean equals(PokeTypeObjet pokeTypeObjet1,
            PokeTypeObjet pokeTypeObjet2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(pokeTypeObjet1);
        Assert.assertNotNull(pokeTypeObjet2);
        if (pokeTypeObjet1!=null && pokeTypeObjet2 !=null){
            Assert.assertEquals(pokeTypeObjet1.getId(), pokeTypeObjet2.getId());
            Assert.assertEquals(pokeTypeObjet1.getNom(), pokeTypeObjet2.getNom());
        }

        return ret;
    }
}

