/**************************************************************************
 * PokeZoneUtilsBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokeZone;



import com.antoinecronier.pokebattle.test.utils.TestUtils;


public abstract class PokeZoneUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static PokeZone generateRandom(android.content.Context ctx){
        PokeZone pokeZone = new PokeZone();

        pokeZone.setId(TestUtils.generateRandomInt(0,100) + 1);
        pokeZone.setNom("nom_"+TestUtils.generateRandomString(10));

        return pokeZone;
    }

    public static boolean equals(PokeZone pokeZone1,
            PokeZone pokeZone2){
        return equals(pokeZone1, pokeZone2, true);
    }
    
    public static boolean equals(PokeZone pokeZone1,
            PokeZone pokeZone2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(pokeZone1);
        Assert.assertNotNull(pokeZone2);
        if (pokeZone1!=null && pokeZone2 !=null){
            Assert.assertEquals(pokeZone1.getId(), pokeZone2.getId());
            Assert.assertEquals(pokeZone1.getNom(), pokeZone2.getNom());
        }

        return ret;
    }
}

