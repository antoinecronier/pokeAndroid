/**************************************************************************
 * PokePositionUtilsBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokePosition;



import com.antoinecronier.pokebattle.test.utils.TestUtils;


public abstract class PokePositionUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static PokePosition generateRandom(android.content.Context ctx){
        PokePosition pokePosition = new PokePosition();

        pokePosition.setId(TestUtils.generateRandomInt(0,100) + 1);
        pokePosition.setX(TestUtils.generateRandomInt(0,100));
        pokePosition.setY(TestUtils.generateRandomInt(0,100));

        return pokePosition;
    }

    public static boolean equals(PokePosition pokePosition1,
            PokePosition pokePosition2){
        return equals(pokePosition1, pokePosition2, true);
    }
    
    public static boolean equals(PokePosition pokePosition1,
            PokePosition pokePosition2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(pokePosition1);
        Assert.assertNotNull(pokePosition2);
        if (pokePosition1!=null && pokePosition2 !=null){
            Assert.assertEquals(pokePosition1.getId(), pokePosition2.getId());
            Assert.assertEquals(pokePosition1.getX(), pokePosition2.getX());
            Assert.assertEquals(pokePosition1.getY(), pokePosition2.getY());
        }

        return ret;
    }
}

