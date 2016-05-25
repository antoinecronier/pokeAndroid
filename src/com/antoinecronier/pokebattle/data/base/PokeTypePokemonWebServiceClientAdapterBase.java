/**************************************************************************
 * PokeTypePokemonWebServiceClientAdapterBase.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/

package com.antoinecronier.pokebattle.data.base;

import java.util.List;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;

import com.antoinecronier.pokebattle.data.*;
import com.antoinecronier.pokebattle.entity.PokeTypePokemon;
import com.antoinecronier.pokebattle.data.RestClient.Verb;
import com.antoinecronier.pokebattle.provider.contract.PokeTypePokemonContract;

import com.antoinecronier.pokebattle.entity.PokeType;
import com.antoinecronier.pokebattle.entity.PokeZone;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokeTypePokemonWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class PokeTypePokemonWebServiceClientAdapterBase
        extends WebServiceClientAdapter<PokeTypePokemon> {
    /** PokeTypePokemonWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "PokeTypePokemonWSClientAdapter";

    /** JSON Object PokeTypePokemon pattern. */
    protected static String JSON_OBJECT_POKETYPEPOKEMON = "PokeTypePokemon";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_ATTAQUE attributes. */
    protected static String JSON_ATTAQUE = "attaque";
    /** JSON_ATTAQUE_SPE attributes. */
    protected static String JSON_ATTAQUE_SPE = "attaque_spe";
    /** JSON_DEFENCE attributes. */
    protected static String JSON_DEFENCE = "defence";
    /** JSON_DEFENCE_SPE attributes. */
    protected static String JSON_DEFENCE_SPE = "defence_spe";
    /** JSON_VITESSE attributes. */
    protected static String JSON_VITESSE = "vitesse";
    /** JSON_PV attributes. */
    protected static String JSON_PV = "pv";
    /** JSON_POKEDEX attributes. */
    protected static String JSON_POKEDEX = "pokedex";
    /** JSON_EVOLUE attributes. */
    protected static String JSON_EVOLUE = "evolue";
    /** JSON_TYPES attributes. */
    protected static String JSON_TYPES = "types";
    /** JSON_ZONES attributes. */
    protected static String JSON_ZONES = "zones";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** PokeTypePokemon REST Columns. */
    public static String[] REST_COLS = new String[]{
            PokeTypePokemonContract.COL_ID,
            PokeTypePokemonContract.COL_NOM,
            PokeTypePokemonContract.COL_ATTAQUE,
            PokeTypePokemonContract.COL_ATTAQUE_SPE,
            PokeTypePokemonContract.COL_DEFENCE,
            PokeTypePokemonContract.COL_DEFENCE_SPE,
            PokeTypePokemonContract.COL_VITESSE,
            PokeTypePokemonContract.COL_PV,
            PokeTypePokemonContract.COL_POKEDEX,
            PokeTypePokemonContract.COL_EVOLUE_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public PokeTypePokemonWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public PokeTypePokemonWebServiceClientAdapterBase(Context context,
        Integer port) {
        this(context, null, port);
    }

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     * @param host The overriden host
     * @param port The overriden port
     */
    public PokeTypePokemonWebServiceClientAdapterBase(Context context,
            String host, Integer port) {
        this(context, host, port, null);
    }

    /**
     * Constructor with overriden port, host and scheme.
     *
     * @param context The context
     * @param host The overriden host
     * @param port The overriden port
     * @param scheme The overriden scheme
     */
    public PokeTypePokemonWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme) {
        this(context, host, port, scheme, null);
    }

    /**
     * Constructor with overriden port, host, scheme and prefix.
     *
     * @param context The context
     * @param host The overriden host
     * @param port The overriden port
     * @param scheme The overriden scheme
     * @param prefix The overriden prefix
     */
    public PokeTypePokemonWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the PokeTypePokemons in the given list. Uses the route : PokeTypePokemon.
     * @param pokeTypePokemons : The list in which the PokeTypePokemons will be returned
     * @return The number of PokeTypePokemons returned
     */
    public int getAll(List<PokeTypePokemon> pokeTypePokemons) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "%s",
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = extractItems(json, "PokeTypePokemons", pokeTypePokemons);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeTypePokemons = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "poketypepokemon";
    }

    /**
     * Retrieve one PokeTypePokemon. Uses the route : PokeTypePokemon/%id%.
     * @param pokeTypePokemon : The PokeTypePokemon to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(PokeTypePokemon pokeTypePokemon) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeTypePokemon.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, pokeTypePokemon)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeTypePokemon = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one PokeTypePokemon. Uses the route : PokeTypePokemon/%id%.
     * @param pokeTypePokemon : The PokeTypePokemon to retrieve (set the  ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public Cursor query(final int id) {
        MatrixCursor result = new MatrixCursor(REST_COLS);
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        id,
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extractCursor(json, result);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                result = null;
            }
        }

        return result;
    }

    /**
     * Update a PokeTypePokemon. Uses the route : PokeTypePokemon/%id%.
     * @param pokeTypePokemon : The PokeTypePokemon to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(PokeTypePokemon pokeTypePokemon) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeTypePokemon.getId(),
                        REST_FORMAT),
                    itemToJson(pokeTypePokemon));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, pokeTypePokemon);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a PokeTypePokemon. Uses the route : PokeTypePokemon/%id%.
     * @param pokeTypePokemon : The PokeTypePokemon to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(PokeTypePokemon pokeTypePokemon) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeTypePokemon.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the PokeTypePokemons associated with a PokeTypePokemon. Uses the route : poketypepokemon/%PokeTypePokemon_id%/poketypepokemon.
     * @param pokeTypePokemons : The list in which the PokeTypePokemons will be returned
     * @param poketypepokemon : The associated poketypepokemon
     * @return The number of PokeTypePokemons returned
     */
    public int getByEvolue(List<PokeTypePokemon> pokeTypePokemons, PokeTypePokemon pokeTypePokemon) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeTypePokemon.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "PokeTypePokemons", pokeTypePokemons);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeTypePokemons = null;
            }
        }

        return result;
    }




    /**
     * Tests if the json is a valid PokeTypePokemon Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(PokeTypePokemonWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a PokeTypePokemon from a JSONObject describing a PokeTypePokemon.
     * @param json The JSONObject describing the PokeTypePokemon
     * @param pokeTypePokemon The returned PokeTypePokemon
     * @return true if a PokeTypePokemon was found. false if not
     */
    public boolean extract(JSONObject json, PokeTypePokemon pokeTypePokemon) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(PokeTypePokemonWebServiceClientAdapter.JSON_ID)) {
                    pokeTypePokemon.setId(
                            json.getInt(PokeTypePokemonWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(PokeTypePokemonWebServiceClientAdapter.JSON_NOM)) {
                    pokeTypePokemon.setNom(
                            json.getString(PokeTypePokemonWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_ATTAQUE)
                        && !json.isNull(PokeTypePokemonWebServiceClientAdapter.JSON_ATTAQUE)) {
                    pokeTypePokemon.setAttaque(
                            json.getInt(PokeTypePokemonWebServiceClientAdapter.JSON_ATTAQUE));
                }

                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_ATTAQUE_SPE)
                        && !json.isNull(PokeTypePokemonWebServiceClientAdapter.JSON_ATTAQUE_SPE)) {
                    pokeTypePokemon.setAttaque_spe(
                            json.getInt(PokeTypePokemonWebServiceClientAdapter.JSON_ATTAQUE_SPE));
                }

                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_DEFENCE)
                        && !json.isNull(PokeTypePokemonWebServiceClientAdapter.JSON_DEFENCE)) {
                    pokeTypePokemon.setDefence(
                            json.getInt(PokeTypePokemonWebServiceClientAdapter.JSON_DEFENCE));
                }

                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_DEFENCE_SPE)
                        && !json.isNull(PokeTypePokemonWebServiceClientAdapter.JSON_DEFENCE_SPE)) {
                    pokeTypePokemon.setDefence_spe(
                            json.getInt(PokeTypePokemonWebServiceClientAdapter.JSON_DEFENCE_SPE));
                }

                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_VITESSE)
                        && !json.isNull(PokeTypePokemonWebServiceClientAdapter.JSON_VITESSE)) {
                    pokeTypePokemon.setVitesse(
                            json.getInt(PokeTypePokemonWebServiceClientAdapter.JSON_VITESSE));
                }

                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_PV)
                        && !json.isNull(PokeTypePokemonWebServiceClientAdapter.JSON_PV)) {
                    pokeTypePokemon.setPv(
                            json.getInt(PokeTypePokemonWebServiceClientAdapter.JSON_PV));
                }

                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_POKEDEX)
                        && !json.isNull(PokeTypePokemonWebServiceClientAdapter.JSON_POKEDEX)) {
                    pokeTypePokemon.setPokedex(
                            json.getInt(PokeTypePokemonWebServiceClientAdapter.JSON_POKEDEX));
                }

                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_EVOLUE)
                        && !json.isNull(PokeTypePokemonWebServiceClientAdapter.JSON_EVOLUE)) {

                    try {
                        PokeTypePokemonWebServiceClientAdapter evolueAdapter =
                                new PokeTypePokemonWebServiceClientAdapter(this.context);
                        PokeTypePokemon evolue =
                                new PokeTypePokemon();

                        if (evolueAdapter.extract(
                                json.optJSONObject(
                                        PokeTypePokemonWebServiceClientAdapter.JSON_EVOLUE),
                                        evolue)) {
                            pokeTypePokemon.setEvolue(evolue);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains PokeTypePokemon data");
                    }
                }

                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_TYPES)
                        && !json.isNull(PokeTypePokemonWebServiceClientAdapter.JSON_TYPES)) {
                    ArrayList<PokeType> types =
                            new ArrayList<PokeType>();
                    PokeTypeWebServiceClientAdapter typesAdapter =
                            new PokeTypeWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(PokeTypePokemonWebServiceClientAdapter.JSON_TYPES);
                        typesAdapter.extractItems(
                                json, PokeTypePokemonWebServiceClientAdapter.JSON_TYPES,
                                types);
                        pokeTypePokemon.setTypes(types);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_ZONES)
                        && !json.isNull(PokeTypePokemonWebServiceClientAdapter.JSON_ZONES)) {
                    ArrayList<PokeZone> zones =
                            new ArrayList<PokeZone>();
                    PokeZoneWebServiceClientAdapter zonesAdapter =
                            new PokeZoneWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(PokeTypePokemonWebServiceClientAdapter.JSON_ZONES);
                        zonesAdapter.extractItems(
                                json, PokeTypePokemonWebServiceClientAdapter.JSON_ZONES,
                                zones);
                        pokeTypePokemon.setZones(zones);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    @Override
    public boolean extractCursor(JSONObject json, MatrixCursor cursor) {
        boolean result = false;
        String id = json.optString(PokeTypePokemonWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[10];
                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(PokeTypePokemonWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(PokeTypePokemonWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_ATTAQUE)) {
                    row[2] = json.getString(PokeTypePokemonWebServiceClientAdapter.JSON_ATTAQUE);
                }
                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_ATTAQUE_SPE)) {
                    row[3] = json.getString(PokeTypePokemonWebServiceClientAdapter.JSON_ATTAQUE_SPE);
                }
                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_DEFENCE)) {
                    row[4] = json.getString(PokeTypePokemonWebServiceClientAdapter.JSON_DEFENCE);
                }
                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_DEFENCE_SPE)) {
                    row[5] = json.getString(PokeTypePokemonWebServiceClientAdapter.JSON_DEFENCE_SPE);
                }
                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_VITESSE)) {
                    row[6] = json.getString(PokeTypePokemonWebServiceClientAdapter.JSON_VITESSE);
                }
                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_PV)) {
                    row[7] = json.getString(PokeTypePokemonWebServiceClientAdapter.JSON_PV);
                }
                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_POKEDEX)) {
                    row[8] = json.getString(PokeTypePokemonWebServiceClientAdapter.JSON_POKEDEX);
                }
                if (json.has(PokeTypePokemonWebServiceClientAdapter.JSON_EVOLUE)) {
                    JSONObject evolueJson = json.getJSONObject(
                            PokeTypePokemonWebServiceClientAdapter.JSON_EVOLUE);
                    row[9] = evolueJson.getString(
                            PokeTypePokemonWebServiceClientAdapter.JSON_ID);
                }

                cursor.addRow(row);
                result = true;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Convert a PokeTypePokemon to a JSONObject.
     * @param pokeTypePokemon The PokeTypePokemon to convert
     * @return The converted PokeTypePokemon
     */
    public JSONObject itemToJson(PokeTypePokemon pokeTypePokemon) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_ID,
                    pokeTypePokemon.getId());
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_NOM,
                    pokeTypePokemon.getNom());
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_ATTAQUE,
                    pokeTypePokemon.getAttaque());
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_ATTAQUE_SPE,
                    pokeTypePokemon.getAttaque_spe());
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_DEFENCE,
                    pokeTypePokemon.getDefence());
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_DEFENCE_SPE,
                    pokeTypePokemon.getDefence_spe());
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_VITESSE,
                    pokeTypePokemon.getVitesse());
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_PV,
                    pokeTypePokemon.getPv());
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_POKEDEX,
                    pokeTypePokemon.getPokedex());

            if (pokeTypePokemon.getEvolue() != null) {
                PokeTypePokemonWebServiceClientAdapter evolueAdapter =
                        new PokeTypePokemonWebServiceClientAdapter(this.context);

                params.put(PokeTypePokemonWebServiceClientAdapter.JSON_EVOLUE,
                        evolueAdapter.itemIdToJson(pokeTypePokemon.getEvolue()));
            }

            if (pokeTypePokemon.getTypes() != null) {
                PokeTypeWebServiceClientAdapter typesAdapter =
                        new PokeTypeWebServiceClientAdapter(this.context);

                params.put(PokeTypePokemonWebServiceClientAdapter.JSON_TYPES,
                        typesAdapter.itemsIdToJson(pokeTypePokemon.getTypes()));
            }

            if (pokeTypePokemon.getZones() != null) {
                PokeZoneWebServiceClientAdapter zonesAdapter =
                        new PokeZoneWebServiceClientAdapter(this.context);

                params.put(PokeTypePokemonWebServiceClientAdapter.JSON_ZONES,
                        zonesAdapter.itemsIdToJson(pokeTypePokemon.getZones()));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Convert a <T> to a JSONObject.
     * @param item The <T> to convert
     * @return The converted <T>
     */
    public JSONObject itemIdToJson(PokeTypePokemon item) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a PokeTypePokemon to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_ID,
                    values.get(PokeTypePokemonContract.COL_ID));
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_NOM,
                    values.get(PokeTypePokemonContract.COL_NOM));
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_ATTAQUE,
                    values.get(PokeTypePokemonContract.COL_ATTAQUE));
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_ATTAQUE_SPE,
                    values.get(PokeTypePokemonContract.COL_ATTAQUE_SPE));
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_DEFENCE,
                    values.get(PokeTypePokemonContract.COL_DEFENCE));
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_DEFENCE_SPE,
                    values.get(PokeTypePokemonContract.COL_DEFENCE_SPE));
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_VITESSE,
                    values.get(PokeTypePokemonContract.COL_VITESSE));
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_PV,
                    values.get(PokeTypePokemonContract.COL_PV));
            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_POKEDEX,
                    values.get(PokeTypePokemonContract.COL_POKEDEX));
            PokeTypePokemonWebServiceClientAdapter evolueAdapter =
                    new PokeTypePokemonWebServiceClientAdapter(this.context);

            params.put(PokeTypePokemonWebServiceClientAdapter.JSON_EVOLUE,
                    evolueAdapter.contentValuesToJson(values));
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Extract a list of <T> from a JSONObject describing an array of <T> given the array name.
     * @param json The JSONObject describing the array of <T>
     * @param items The returned list of <T>
     * @param paramName The name of the array
     * @param limit Limit the number of items to parse
     * @return The number of <T> found in the JSON
     */
    public int extractItems(JSONObject json,
            String paramName,
            List<PokeTypePokemon> items,
            int limit) throws JSONException {

        JSONArray itemArray = json.optJSONArray(paramName);

        int result = -1;

        if (itemArray != null) {
            int count = itemArray.length();

            if (limit > 0 && count > limit) {
                count = limit;
            }

            for (int i = 0; i < count; i++) {
                JSONObject jsonItem = itemArray.getJSONObject(i);
                PokeTypePokemon item = new PokeTypePokemon();
                this.extract(jsonItem, item);
                if (item!=null) {
                    synchronized (items) {
                        items.add(item);
                    }
                }
            }
        }

        if (!json.isNull("Meta")) {
            JSONObject meta = json.optJSONObject("Meta");
            result = meta.optInt("nbt",0);
        }

        return result;
    }

    /**
     * Extract a list of <T> from a JSONObject describing an array of <T> given the array name.
     * @param json The JSONObject describing the array of <T>
     * @param items The returned list of <T>
     * @param paramName The name of the array
     * @return The number of <T> found in the JSON
     */
    public int extractItems(JSONObject json,
            String paramName,
            List<PokeTypePokemon> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
