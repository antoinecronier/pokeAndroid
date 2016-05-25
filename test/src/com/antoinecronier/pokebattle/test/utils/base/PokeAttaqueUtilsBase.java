/**************************************************************************
 * PokeAttaqueUtilsBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokeAttaque;



import com.antoinecronier.pokebattle.test.utils.TestUtils;
import com.antoinecronier.pokebattle.entity.PokeType;
import com.antoinecronier.pokebattle.fixture.PokeTypeDataLoader;


import java.util.ArrayList;

public abstract class PokeAttaqueUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static PokeAttaque generateRandom(android.content.Context ctx){
        PokeAttaque pokeAttaque = new PokeAttaque();

        pokeAttaque.setId(TestUtils.generateRandomInt(0,100) + 1);
        pokeAttaque.setNom("nom_"+TestUtils.generateRandomString(10));
        pokeAttaque.setPuissance(TestUtils.generateRandomInt(0,100));
        pokeAttaque.setPrecision(TestUtils.generateRandomInt(0,100));
        ArrayList<PokeType> types =
            new ArrayList<PokeType>();
        types.addAll(PokeTypeDataLoader.getInstance(ctx).getMap().values());
        if (!types.isEmpty()) {
            pokeAttaque.setType(types.get(TestUtils.generateRandomInt(0, types.size())));
        }

        return pokeAttaque;
    }

    public static boolean equals(PokeAttaque pokeAttaque1,
            PokeAttaque pokeAttaque2){
        return equals(pokeAttaque1, pokeAttaque2, true);
    }
    
    public static boolean equals(PokeAttaque pokeAttaque1,
            PokeAttaque pokeAttaque2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(pokeAttaque1);
        Assert.assertNotNull(pokeAttaque2);
        if (pokeAttaque1!=null && pokeAttaque2 !=null){
            Assert.assertEquals(pokeAttaque1.getId(), pokeAttaque2.getId());
            Assert.assertEquals(pokeAttaque1.getNom(), pokeAttaque2.getNom());
            Assert.assertEquals(pokeAttaque1.getPuissance(), pokeAttaque2.getPuissance());
            Assert.assertEquals(pokeAttaque1.getPrecision(), pokeAttaque2.getPrecision());
            if (pokeAttaque1.getType() != null
                    && pokeAttaque2.getType() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokeAttaque1.getType().getId(),
                            pokeAttaque2.getType().getId());
                }
            }
        }

        return ret;
    }
}

