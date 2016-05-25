/**************************************************************************
 * DataManager.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.fixture;


import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

import com.antoinecronier.pokebattle.data.base.SQLiteAdapterBase;
import com.antoinecronier.pokebattle.data.PokeAreneSQLiteAdapter;
import com.antoinecronier.pokebattle.entity.PokeArene;
import com.antoinecronier.pokebattle.data.PokeBadgeSQLiteAdapter;
import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.data.PokeZoneSQLiteAdapter;
import com.antoinecronier.pokebattle.entity.PokeZone;
import com.antoinecronier.pokebattle.data.PokeTypeSQLiteAdapter;
import com.antoinecronier.pokebattle.entity.PokeType;
import com.antoinecronier.pokebattle.data.PokePositionSQLiteAdapter;
import com.antoinecronier.pokebattle.entity.PokePosition;
import com.antoinecronier.pokebattle.data.PokePokemonSQLiteAdapter;
import com.antoinecronier.pokebattle.entity.PokePokemon;
import com.antoinecronier.pokebattle.data.PokeObjetSQLiteAdapter;
import com.antoinecronier.pokebattle.entity.PokeObjet;
import com.antoinecronier.pokebattle.data.PokeAttaqueSQLiteAdapter;
import com.antoinecronier.pokebattle.entity.PokeAttaque;
import com.antoinecronier.pokebattle.data.PokeDresseurSQLiteAdapter;
import com.antoinecronier.pokebattle.entity.PokeDresseur;
import com.antoinecronier.pokebattle.data.PokeTypePokemonSQLiteAdapter;
import com.antoinecronier.pokebattle.entity.PokeTypePokemon;
import com.antoinecronier.pokebattle.data.PokeTypeObjetSQLiteAdapter;
import com.antoinecronier.pokebattle.entity.PokeTypeObjet;
import com.antoinecronier.pokebattle.data.PokeNpcSQLiteAdapter;
import com.antoinecronier.pokebattle.entity.PokeNpc;

/**
 * DataManager.
 * 
 * This class is an "orm-like" manager which simplifies insertion in database
 * with sqlite adapters.
 */
public class DataManager {
    /** HashMap to join Entity Name and its SQLiteAdapterBase. */
    protected Map<String, SQLiteAdapterBase<?>> adapters =
            new HashMap<String, SQLiteAdapterBase<?>>();
    /** is successfull. */
    protected boolean isSuccessfull = true;
    /** is in internal transaction. */
    protected boolean isInInternalTransaction = false;
    /** database. */
    protected SQLiteDatabase db;
    /** PokeArene name constant. */
    private static final String POKEARENE = "PokeArene";
    /** PokeBadge name constant. */
    private static final String POKEBADGE = "PokeBadge";
    /** PokeZone name constant. */
    private static final String POKEZONE = "PokeZone";
    /** PokeType name constant. */
    private static final String POKETYPE = "PokeType";
    /** PokePosition name constant. */
    private static final String POKEPOSITION = "PokePosition";
    /** PokePokemon name constant. */
    private static final String POKEPOKEMON = "PokePokemon";
    /** PokeObjet name constant. */
    private static final String POKEOBJET = "PokeObjet";
    /** PokeAttaque name constant. */
    private static final String POKEATTAQUE = "PokeAttaque";
    /** PokeDresseur name constant. */
    private static final String POKEDRESSEUR = "PokeDresseur";
    /** PokeTypePokemon name constant. */
    private static final String POKETYPEPOKEMON = "PokeTypePokemon";
    /** PokeTypeObjet name constant. */
    private static final String POKETYPEOBJET = "PokeTypeObjet";
    /** PokeNpc name constant. */
    private static final String POKENPC = "PokeNpc";
    /**
     * Constructor.
     * @param ctx The context
     * @param db The DB to work in
     */
    public DataManager(final android.content.Context ctx, final SQLiteDatabase db) {
        this.db = db;
        this.adapters.put(POKEARENE,
                new PokeAreneSQLiteAdapter(ctx));
        this.adapters.get(POKEARENE).open(this.db);
        this.adapters.put(POKEBADGE,
                new PokeBadgeSQLiteAdapter(ctx));
        this.adapters.get(POKEBADGE).open(this.db);
        this.adapters.put(POKEZONE,
                new PokeZoneSQLiteAdapter(ctx));
        this.adapters.get(POKEZONE).open(this.db);
        this.adapters.put(POKETYPE,
                new PokeTypeSQLiteAdapter(ctx));
        this.adapters.get(POKETYPE).open(this.db);
        this.adapters.put(POKEPOSITION,
                new PokePositionSQLiteAdapter(ctx));
        this.adapters.get(POKEPOSITION).open(this.db);
        this.adapters.put(POKEPOKEMON,
                new PokePokemonSQLiteAdapter(ctx));
        this.adapters.get(POKEPOKEMON).open(this.db);
        this.adapters.put(POKEOBJET,
                new PokeObjetSQLiteAdapter(ctx));
        this.adapters.get(POKEOBJET).open(this.db);
        this.adapters.put(POKEATTAQUE,
                new PokeAttaqueSQLiteAdapter(ctx));
        this.adapters.get(POKEATTAQUE).open(this.db);
        this.adapters.put(POKEDRESSEUR,
                new PokeDresseurSQLiteAdapter(ctx));
        this.adapters.get(POKEDRESSEUR).open(this.db);
        this.adapters.put(POKETYPEPOKEMON,
                new PokeTypePokemonSQLiteAdapter(ctx));
        this.adapters.get(POKETYPEPOKEMON).open(this.db);
        this.adapters.put(POKETYPEOBJET,
                new PokeTypeObjetSQLiteAdapter(ctx));
        this.adapters.get(POKETYPEOBJET).open(this.db);
        this.adapters.put(POKENPC,
                new PokeNpcSQLiteAdapter(ctx));
        this.adapters.get(POKENPC).open(this.db);
    }

    /**
     * Tells the ObjectManager to make an instance managed and persistent.
     *
     * The object will be entered into the database as a result of the <br />
     * flush operation.
     *
     * NOTE: The persist operation always considers objects that are not<br />
     * yet known to this ObjectManager as NEW. Do not pass detached <br />
     * objects to the persist operation.
     *
     * @param object $object The instance to make managed and persistent.
     * @return Count of objects entered into the DB
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public int persist(final Object object) {
        int result;

        this.beginTransaction();
        try {
            final SQLiteAdapterBase adapter = this.getRepository(object);

            result = (int) adapter.insert(object);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.isSuccessfull = false;
            result = 0;
        }

        return result;
    }

    /**
     * Removes an object instance.
     *
     * A removed object will be removed from the database as a result of <br />
     * the flush operation.
     *
     * @param object $object The object instance to remove.
     */
    public void remove(final Object object) {
        this.beginTransaction();
        try {
            if (object instanceof PokeArene) {
                ((PokeAreneSQLiteAdapter)
                        this.adapters.get(POKEARENE))
                            .remove(((PokeArene) object).getId());
            }
            if (object instanceof PokeBadge) {
                ((PokeBadgeSQLiteAdapter)
                        this.adapters.get(POKEBADGE))
                            .remove(((PokeBadge) object).getId());
            }
            if (object instanceof PokeZone) {
                ((PokeZoneSQLiteAdapter)
                        this.adapters.get(POKEZONE))
                            .remove(((PokeZone) object).getId());
            }
            if (object instanceof PokeType) {
                ((PokeTypeSQLiteAdapter)
                        this.adapters.get(POKETYPE))
                            .remove(((PokeType) object).getId());
            }
            if (object instanceof PokePosition) {
                ((PokePositionSQLiteAdapter)
                        this.adapters.get(POKEPOSITION))
                            .remove(((PokePosition) object).getId());
            }
            if (object instanceof PokePokemon) {
                ((PokePokemonSQLiteAdapter)
                        this.adapters.get(POKEPOKEMON))
                            .remove(((PokePokemon) object).getId());
            }
            if (object instanceof PokeObjet) {
                ((PokeObjetSQLiteAdapter)
                        this.adapters.get(POKEOBJET))
                            .remove(((PokeObjet) object).getId());
            }
            if (object instanceof PokeAttaque) {
                ((PokeAttaqueSQLiteAdapter)
                        this.adapters.get(POKEATTAQUE))
                            .remove(((PokeAttaque) object).getId());
            }
            if (object instanceof PokeDresseur) {
                ((PokeDresseurSQLiteAdapter)
                        this.adapters.get(POKEDRESSEUR))
                            .remove(((PokeDresseur) object).getId());
            }
            if (object instanceof PokeTypePokemon) {
                ((PokeTypePokemonSQLiteAdapter)
                        this.adapters.get(POKETYPEPOKEMON))
                            .remove(((PokeTypePokemon) object).getId());
            }
            if (object instanceof PokeTypeObjet) {
                ((PokeTypeObjetSQLiteAdapter)
                        this.adapters.get(POKETYPEOBJET))
                            .remove(((PokeTypeObjet) object).getId());
            }
            if (object instanceof PokeNpc) {
                ((PokeNpcSQLiteAdapter)
                        this.adapters.get(POKENPC))
                            .remove(((PokeNpc) object).getId());
            }
        } catch (Exception ex) {
            this.isSuccessfull = false;
        }
    }

//    /**
//     * Merges the state of a detached object into the persistence context
//     * of this ObjectManager and returns the managed copy of the object.
//     * The object passed to merge will not become associated/managed with
//       * this ObjectManager.
//     *
//     * @param object $object
//     */
//    public void merge(Object object) {
//
//    }
//
//    /**
//     * Clears the ObjectManager. All objects that are currently managed
//     * by this ObjectManager become detached.
//     *
//     * @param objectName $objectName if given, only objects of this type will
//     * get detached
//     */
//    public void clear(String objectName) {
//
//    }
//
//    /**
//     * Detaches an object from the ObjectManager, causing a managed object to
//     * become detached. Unflushed changes made to the object if any
//     * (including removal of the object), will not be synchronized to the
//     * database.
//     * Objects which previously referenced the detached object will continue
//     * to reference it.
//     *
//     * @param object $object The object to detach.
//     */
//    public void detach(Object object) {
//
//    }
//
//    /**
//     * Refreshes the persistent state of an object from the database,
//     * overriding any local changes that have not yet been persisted.
//     *
//     * @param object $object The object to refresh.
//     */
//    public void refresh(Object object) {
//
//    }

    /**
     * Flushes all changes to objects that have been queued up to now to <br />
     * the database. This effectively synchronizes the in-memory state of<br />
     * managed objects with the database.
     */
    public void flush() {
        if (this.isInInternalTransaction) {
            if (this.isSuccessfull) {
                this.db.setTransactionSuccessful();
            }
            this.db.endTransaction();
            this.isInInternalTransaction = false;
        }
    }

    /**
     * Gets the repository for a class.
     *
     * @param className $className
     * @return \Doctrine\Common\Persistence\ObjectRepository
     */
    public SQLiteAdapterBase<?> getRepository(final String className) {
        return this.adapters.get(className);
    }


    /**
     * Gets the repository for a given object.
     *
     * @param o object
     * @return \Doctrine\Common\Persistence\ObjectRepository
     */
    private SQLiteAdapterBase<?> getRepository(final Object o) {
        final String className = o.getClass().getSimpleName();

        return this.getRepository(className);
    }

//    /**
//     * Returns the ClassMetadata descriptor for a class.
//     *
//     * The class name must be the fully-qualified class name without a <br />
//     * leading backslash (as it is returned by get_class($obj)).
//     *
//     * @param className $className
//     * @return \Doctrine\Common\Persistence\Mapping\ClassMetadata
//     */
//    public ClassMetadata getClassMetadata(final String className) {
//        return null;
//    }

    /**
     * Check if the object is part of the current UnitOfWork and therefore
     * managed.
     *
     * @param object $object
     * @return bool
     */
    public boolean contains(final Object object) {
        return false;
    }

    /**
     * Called before any transaction to open the DB.
     */
    private void beginTransaction() {
        // If we are not already in a transaction, begin it
        if (!this.isInInternalTransaction) {
            this.db.beginTransaction();
            this.isSuccessfull = true;
            this.isInInternalTransaction = true;
        }
    }

}
