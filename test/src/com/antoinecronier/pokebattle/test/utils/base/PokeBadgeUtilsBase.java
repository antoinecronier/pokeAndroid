/**************************************************************************
 * PokeBadgeUtilsBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokeBadge;



import com.antoinecronier.pokebattle.test.utils.TestUtils;

import com.antoinecronier.pokebattle.entity.PokeBadgeBonus;

public abstract class PokeBadgeUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static PokeBadge generateRandom(android.content.Context ctx){
        PokeBadge pokeBadge = new PokeBadge();

        pokeBadge.setId(TestUtils.generateRandomInt(0,100) + 1);
        pokeBadge.setNom("nom_"+TestUtils.generateRandomString(10));
        pokeBadge.setBonus(PokeBadgeBonus.values()[TestUtils.generateRandomInt(0,PokeBadgeBonus.values().length)]);

        return pokeBadge;
    }

    public static boolean equals(PokeBadge pokeBadge1,
            PokeBadge pokeBadge2){
        return equals(pokeBadge1, pokeBadge2, true);
    }
    
    public static boolean equals(PokeBadge pokeBadge1,
            PokeBadge pokeBadge2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(pokeBadge1);
        Assert.assertNotNull(pokeBadge2);
        if (pokeBadge1!=null && pokeBadge2 !=null){
            Assert.assertEquals(pokeBadge1.getId(), pokeBadge2.getId());
            Assert.assertEquals(pokeBadge1.getNom(), pokeBadge2.getNom());
            //TODO : Manage field type : enum / enum
        }

        return ret;
    }
}

