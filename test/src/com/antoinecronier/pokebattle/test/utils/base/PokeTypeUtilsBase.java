/**************************************************************************
 * PokeTypeUtilsBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokeType;



import com.antoinecronier.pokebattle.test.utils.TestUtils;
import com.antoinecronier.pokebattle.entity.PokeType;
import com.antoinecronier.pokebattle.fixture.PokeTypeDataLoader;


import java.util.ArrayList;

public abstract class PokeTypeUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static PokeType generateRandom(android.content.Context ctx){
        PokeType pokeType = new PokeType();

        pokeType.setId(TestUtils.generateRandomInt(0,100) + 1);
        pokeType.setNom("nom_"+TestUtils.generateRandomString(10));
        pokeType.setModificateur(TestUtils.generateRandomInt(0,100));
        ArrayList<PokeType> typeForts =
            new ArrayList<PokeType>();
        typeForts.addAll(PokeTypeDataLoader.getInstance(ctx).getMap().values());
        ArrayList<PokeType> relatedTypeForts = new ArrayList<PokeType>();
        if (!typeForts.isEmpty()) {
            relatedTypeForts.add(typeForts.get(TestUtils.generateRandomInt(0, typeForts.size())));
            pokeType.setTypeFort(relatedTypeForts);
        }
        ArrayList<PokeType> typeFaibles =
            new ArrayList<PokeType>();
        typeFaibles.addAll(PokeTypeDataLoader.getInstance(ctx).getMap().values());
        ArrayList<PokeType> relatedTypeFaibles = new ArrayList<PokeType>();
        if (!typeFaibles.isEmpty()) {
            relatedTypeFaibles.add(typeFaibles.get(TestUtils.generateRandomInt(0, typeFaibles.size())));
            pokeType.setTypeFaible(relatedTypeFaibles);
        }

        return pokeType;
    }

    public static boolean equals(PokeType pokeType1,
            PokeType pokeType2){
        return equals(pokeType1, pokeType2, true);
    }
    
    public static boolean equals(PokeType pokeType1,
            PokeType pokeType2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(pokeType1);
        Assert.assertNotNull(pokeType2);
        if (pokeType1!=null && pokeType2 !=null){
            Assert.assertEquals(pokeType1.getId(), pokeType2.getId());
            Assert.assertEquals(pokeType1.getNom(), pokeType2.getNom());
            Assert.assertEquals(pokeType1.getModificateur(), pokeType2.getModificateur());
            if (pokeType1.getTypeFort() != null
                    && pokeType2.getTypeFort() != null) {
                Assert.assertEquals(pokeType1.getTypeFort().size(),
                    pokeType2.getTypeFort().size());
                if (checkRecursiveId) {
                    for (PokeType typeFort1 : pokeType1.getTypeFort()) {
                        boolean found = false;
                        for (PokeType typeFort2 : pokeType2.getTypeFort()) {
                            if (typeFort1.getId() == typeFort2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated typeFort (id = %s) in PokeType (id = %s)",
                                        typeFort1.getId(),
                                        pokeType1.getId()),
                                found);
                    }
                }
            }
            if (pokeType1.getTypeFaible() != null
                    && pokeType2.getTypeFaible() != null) {
                Assert.assertEquals(pokeType1.getTypeFaible().size(),
                    pokeType2.getTypeFaible().size());
                if (checkRecursiveId) {
                    for (PokeType typeFaible1 : pokeType1.getTypeFaible()) {
                        boolean found = false;
                        for (PokeType typeFaible2 : pokeType2.getTypeFaible()) {
                            if (typeFaible1.getId() == typeFaible2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated typeFaible (id = %s) in PokeType (id = %s)",
                                        typeFaible1.getId(),
                                        pokeType1.getId()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}

