/**************************************************************************
 * PokePokemonWebServiceClientAdapterBase.java, pokebattle Android
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


import org.joda.time.format.DateTimeFormatter;
import com.antoinecronier.pokebattle.harmony.util.DateUtils;
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
import com.antoinecronier.pokebattle.entity.PokePokemon;
import com.antoinecronier.pokebattle.data.RestClient.Verb;
import com.antoinecronier.pokebattle.provider.contract.PokePokemonContract;

import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.entity.PokeTypePokemon;
import com.antoinecronier.pokebattle.entity.PokeAttaque;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokePokemonWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class PokePokemonWebServiceClientAdapterBase
        extends WebServiceClientAdapter<PokePokemon> {
    /** PokePokemonWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "PokePokemonWSClientAdapter";

    /** JSON Object PokePokemon pattern. */
    protected static String JSON_OBJECT_POKEPOKEMON = "PokePokemon";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_SURNOM attributes. */
    protected static String JSON_SURNOM = "surnom";
    /** JSON_NIVEAU attributes. */
    protected static String JSON_NIVEAU = "niveau";
    /** JSON_CAPTURE attributes. */
    protected static String JSON_CAPTURE = "capture";
    /** JSON_TYPE attributes. */
    protected static String JSON_TYPE = "type";
    /** JSON_ATTAQUE1 attributes. */
    protected static String JSON_ATTAQUE1 = "attaque1";
    /** JSON_ATTAQUE2 attributes. */
    protected static String JSON_ATTAQUE2 = "attaque2";
    /** JSON_ATTAQUE3 attributes. */
    protected static String JSON_ATTAQUE3 = "attaque3";
    /** JSON_ATTAQUE4 attributes. */
    protected static String JSON_ATTAQUE4 = "attaque4";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** PokePokemon REST Columns. */
    public static String[] REST_COLS = new String[]{
            PokePokemonContract.COL_ID,
            PokePokemonContract.COL_SURNOM,
            PokePokemonContract.COL_NIVEAU,
            PokePokemonContract.COL_CAPTURE,
            PokePokemonContract.COL_TYPE_ID,
            PokePokemonContract.COL_ATTAQUE1_ID,
            PokePokemonContract.COL_ATTAQUE2_ID,
            PokePokemonContract.COL_ATTAQUE3_ID,
            PokePokemonContract.COL_ATTAQUE4_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public PokePokemonWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public PokePokemonWebServiceClientAdapterBase(Context context,
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
    public PokePokemonWebServiceClientAdapterBase(Context context,
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
    public PokePokemonWebServiceClientAdapterBase(Context context,
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
    public PokePokemonWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the PokePokemons in the given list. Uses the route : PokePokemon.
     * @param pokePokemons : The list in which the PokePokemons will be returned
     * @return The number of PokePokemons returned
     */
    public int getAll(List<PokePokemon> pokePokemons) {
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
                result = extractItems(json, "PokePokemons", pokePokemons);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokePokemons = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "pokepokemon";
    }

    /**
     * Retrieve one PokePokemon. Uses the route : PokePokemon/%id%.
     * @param pokePokemon : The PokePokemon to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(PokePokemon pokePokemon) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokePokemon.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, pokePokemon)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokePokemon = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one PokePokemon. Uses the route : PokePokemon/%id%.
     * @param pokePokemon : The PokePokemon to retrieve (set the  ID)
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
     * Update a PokePokemon. Uses the route : PokePokemon/%id%.
     * @param pokePokemon : The PokePokemon to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(PokePokemon pokePokemon) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokePokemon.getId(),
                        REST_FORMAT),
                    itemToJson(pokePokemon));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, pokePokemon);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a PokePokemon. Uses the route : PokePokemon/%id%.
     * @param pokePokemon : The PokePokemon to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(PokePokemon pokePokemon) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokePokemon.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the PokePokemons associated with a PokeNpc. Uses the route : pokenpc/%PokeNpc_id%/pokepokemon.
     * @param pokePokemons : The list in which the PokePokemons will be returned
     * @param pokenpc : The associated pokenpc
     * @return The number of PokePokemons returned
     */
    public int getByPokeNpcpokemonsInternal(List<PokePokemon> pokePokemons, PokeNpc pokeNpc) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeNpc.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "PokePokemons", pokePokemons);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokePokemons = null;
            }
        }

        return result;
    }

    /**
     * Get the PokePokemons associated with a PokeNpc. Uses the route : pokenpc/%PokeNpc_id%/pokepokemon.
     * @param pokePokemons : The list in which the PokePokemons will be returned
     * @param pokenpc : The associated pokenpc
     * @return The number of PokePokemons returned
     */
    public int getByPokeNpcteamInternal(List<PokePokemon> pokePokemons, PokeNpc pokeNpc) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeNpc.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "PokePokemons", pokePokemons);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokePokemons = null;
            }
        }

        return result;
    }

    /**
     * Get the PokePokemons associated with a PokeTypePokemon. Uses the route : poketypepokemon/%PokeTypePokemon_id%/pokepokemon.
     * @param pokePokemons : The list in which the PokePokemons will be returned
     * @param poketypepokemon : The associated poketypepokemon
     * @return The number of PokePokemons returned
     */
    public int getByType(List<PokePokemon> pokePokemons, PokeTypePokemon pokeTypePokemon) {
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
                result = this.extractItems(json, "PokePokemons", pokePokemons);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokePokemons = null;
            }
        }

        return result;
    }

    /**
     * Get the PokePokemons associated with a PokeAttaque. Uses the route : pokeattaque/%PokeAttaque_id%/pokepokemon.
     * @param pokePokemons : The list in which the PokePokemons will be returned
     * @param pokeattaque : The associated pokeattaque
     * @return The number of PokePokemons returned
     */
    public int getByAttaque1(List<PokePokemon> pokePokemons, PokeAttaque pokeAttaque) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeAttaque.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "PokePokemons", pokePokemons);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokePokemons = null;
            }
        }

        return result;
    }

    /**
     * Get the PokePokemons associated with a PokeAttaque. Uses the route : pokeattaque/%PokeAttaque_id%/pokepokemon.
     * @param pokePokemons : The list in which the PokePokemons will be returned
     * @param pokeattaque : The associated pokeattaque
     * @return The number of PokePokemons returned
     */
    public int getByAttaque2(List<PokePokemon> pokePokemons, PokeAttaque pokeAttaque) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeAttaque.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "PokePokemons", pokePokemons);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokePokemons = null;
            }
        }

        return result;
    }

    /**
     * Get the PokePokemons associated with a PokeAttaque. Uses the route : pokeattaque/%PokeAttaque_id%/pokepokemon.
     * @param pokePokemons : The list in which the PokePokemons will be returned
     * @param pokeattaque : The associated pokeattaque
     * @return The number of PokePokemons returned
     */
    public int getByAttaque3(List<PokePokemon> pokePokemons, PokeAttaque pokeAttaque) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeAttaque.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "PokePokemons", pokePokemons);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokePokemons = null;
            }
        }

        return result;
    }

    /**
     * Get the PokePokemons associated with a PokeAttaque. Uses the route : pokeattaque/%PokeAttaque_id%/pokepokemon.
     * @param pokePokemons : The list in which the PokePokemons will be returned
     * @param pokeattaque : The associated pokeattaque
     * @return The number of PokePokemons returned
     */
    public int getByAttaque4(List<PokePokemon> pokePokemons, PokeAttaque pokeAttaque) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeAttaque.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "PokePokemons", pokePokemons);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokePokemons = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid PokePokemon Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(PokePokemonWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a PokePokemon from a JSONObject describing a PokePokemon.
     * @param json The JSONObject describing the PokePokemon
     * @param pokePokemon The returned PokePokemon
     * @return true if a PokePokemon was found. false if not
     */
    public boolean extract(JSONObject json, PokePokemon pokePokemon) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(PokePokemonWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(PokePokemonWebServiceClientAdapter.JSON_ID)) {
                    pokePokemon.setId(
                            json.getInt(PokePokemonWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(PokePokemonWebServiceClientAdapter.JSON_SURNOM)
                        && !json.isNull(PokePokemonWebServiceClientAdapter.JSON_SURNOM)) {
                    pokePokemon.setSurnom(
                            json.getString(PokePokemonWebServiceClientAdapter.JSON_SURNOM));
                }

                if (json.has(PokePokemonWebServiceClientAdapter.JSON_NIVEAU)
                        && !json.isNull(PokePokemonWebServiceClientAdapter.JSON_NIVEAU)) {
                    pokePokemon.setNiveau(
                            json.getInt(PokePokemonWebServiceClientAdapter.JSON_NIVEAU));
                }

                if (json.has(PokePokemonWebServiceClientAdapter.JSON_CAPTURE)
                        && !json.isNull(PokePokemonWebServiceClientAdapter.JSON_CAPTURE)) {
                    DateTimeFormatter captureFormatter = DateTimeFormat.forPattern(
                            PokePokemonWebServiceClientAdapter.REST_UPDATE_DATE_FORMAT);
                    try {
                        pokePokemon.setCapture(
                                captureFormatter.withOffsetParsed().parseDateTime(
                                        json.getString(
                                        PokePokemonWebServiceClientAdapter.JSON_CAPTURE)));
                    } catch (IllegalArgumentException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(PokePokemonWebServiceClientAdapter.JSON_TYPE)
                        && !json.isNull(PokePokemonWebServiceClientAdapter.JSON_TYPE)) {

                    try {
                        PokeTypePokemonWebServiceClientAdapter typeAdapter =
                                new PokeTypePokemonWebServiceClientAdapter(this.context);
                        PokeTypePokemon type =
                                new PokeTypePokemon();

                        if (typeAdapter.extract(
                                json.optJSONObject(
                                        PokePokemonWebServiceClientAdapter.JSON_TYPE),
                                        type)) {
                            pokePokemon.setType(type);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains PokeTypePokemon data");
                    }
                }

                if (json.has(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE1)
                        && !json.isNull(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE1)) {

                    try {
                        PokeAttaqueWebServiceClientAdapter attaque1Adapter =
                                new PokeAttaqueWebServiceClientAdapter(this.context);
                        PokeAttaque attaque1 =
                                new PokeAttaque();

                        if (attaque1Adapter.extract(
                                json.optJSONObject(
                                        PokePokemonWebServiceClientAdapter.JSON_ATTAQUE1),
                                        attaque1)) {
                            pokePokemon.setAttaque1(attaque1);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains PokeAttaque data");
                    }
                }

                if (json.has(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE2)
                        && !json.isNull(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE2)) {

                    try {
                        PokeAttaqueWebServiceClientAdapter attaque2Adapter =
                                new PokeAttaqueWebServiceClientAdapter(this.context);
                        PokeAttaque attaque2 =
                                new PokeAttaque();

                        if (attaque2Adapter.extract(
                                json.optJSONObject(
                                        PokePokemonWebServiceClientAdapter.JSON_ATTAQUE2),
                                        attaque2)) {
                            pokePokemon.setAttaque2(attaque2);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains PokeAttaque data");
                    }
                }

                if (json.has(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE3)
                        && !json.isNull(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE3)) {

                    try {
                        PokeAttaqueWebServiceClientAdapter attaque3Adapter =
                                new PokeAttaqueWebServiceClientAdapter(this.context);
                        PokeAttaque attaque3 =
                                new PokeAttaque();

                        if (attaque3Adapter.extract(
                                json.optJSONObject(
                                        PokePokemonWebServiceClientAdapter.JSON_ATTAQUE3),
                                        attaque3)) {
                            pokePokemon.setAttaque3(attaque3);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains PokeAttaque data");
                    }
                }

                if (json.has(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE4)
                        && !json.isNull(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE4)) {

                    try {
                        PokeAttaqueWebServiceClientAdapter attaque4Adapter =
                                new PokeAttaqueWebServiceClientAdapter(this.context);
                        PokeAttaque attaque4 =
                                new PokeAttaque();

                        if (attaque4Adapter.extract(
                                json.optJSONObject(
                                        PokePokemonWebServiceClientAdapter.JSON_ATTAQUE4),
                                        attaque4)) {
                            pokePokemon.setAttaque4(attaque4);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains PokeAttaque data");
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
        String id = json.optString(PokePokemonWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[9];
                if (json.has(PokePokemonWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(PokePokemonWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokePokemonWebServiceClientAdapter.JSON_SURNOM)) {
                    row[1] = json.getString(PokePokemonWebServiceClientAdapter.JSON_SURNOM);
                }
                if (json.has(PokePokemonWebServiceClientAdapter.JSON_NIVEAU)) {
                    row[2] = json.getString(PokePokemonWebServiceClientAdapter.JSON_NIVEAU);
                }
                if (json.has(PokePokemonWebServiceClientAdapter.JSON_CAPTURE)) {
                    row[3] = json.getString(PokePokemonWebServiceClientAdapter.JSON_CAPTURE);
                }
                if (json.has(PokePokemonWebServiceClientAdapter.JSON_TYPE)) {
                    JSONObject typeJson = json.getJSONObject(
                            PokePokemonWebServiceClientAdapter.JSON_TYPE);
                    row[4] = typeJson.getString(
                            PokeTypePokemonWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE1)) {
                    JSONObject attaque1Json = json.getJSONObject(
                            PokePokemonWebServiceClientAdapter.JSON_ATTAQUE1);
                    row[5] = attaque1Json.getString(
                            PokeAttaqueWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE2)) {
                    JSONObject attaque2Json = json.getJSONObject(
                            PokePokemonWebServiceClientAdapter.JSON_ATTAQUE2);
                    row[6] = attaque2Json.getString(
                            PokeAttaqueWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE3)) {
                    JSONObject attaque3Json = json.getJSONObject(
                            PokePokemonWebServiceClientAdapter.JSON_ATTAQUE3);
                    row[7] = attaque3Json.getString(
                            PokeAttaqueWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE4)) {
                    JSONObject attaque4Json = json.getJSONObject(
                            PokePokemonWebServiceClientAdapter.JSON_ATTAQUE4);
                    row[8] = attaque4Json.getString(
                            PokeAttaqueWebServiceClientAdapter.JSON_ID);
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
     * Convert a PokePokemon to a JSONObject.
     * @param pokePokemon The PokePokemon to convert
     * @return The converted PokePokemon
     */
    public JSONObject itemToJson(PokePokemon pokePokemon) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokePokemonWebServiceClientAdapter.JSON_ID,
                    pokePokemon.getId());
            params.put(PokePokemonWebServiceClientAdapter.JSON_SURNOM,
                    pokePokemon.getSurnom());
            params.put(PokePokemonWebServiceClientAdapter.JSON_NIVEAU,
                    pokePokemon.getNiveau());

            if (pokePokemon.getCapture() != null) {
                params.put(PokePokemonWebServiceClientAdapter.JSON_CAPTURE,
                        pokePokemon.getCapture().toString(REST_UPDATE_DATE_FORMAT));
            }

            if (pokePokemon.getType() != null) {
                PokeTypePokemonWebServiceClientAdapter typeAdapter =
                        new PokeTypePokemonWebServiceClientAdapter(this.context);

                params.put(PokePokemonWebServiceClientAdapter.JSON_TYPE,
                        typeAdapter.itemIdToJson(pokePokemon.getType()));
            }

            if (pokePokemon.getAttaque1() != null) {
                PokeAttaqueWebServiceClientAdapter attaque1Adapter =
                        new PokeAttaqueWebServiceClientAdapter(this.context);

                params.put(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE1,
                        attaque1Adapter.itemIdToJson(pokePokemon.getAttaque1()));
            }

            if (pokePokemon.getAttaque2() != null) {
                PokeAttaqueWebServiceClientAdapter attaque2Adapter =
                        new PokeAttaqueWebServiceClientAdapter(this.context);

                params.put(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE2,
                        attaque2Adapter.itemIdToJson(pokePokemon.getAttaque2()));
            }

            if (pokePokemon.getAttaque3() != null) {
                PokeAttaqueWebServiceClientAdapter attaque3Adapter =
                        new PokeAttaqueWebServiceClientAdapter(this.context);

                params.put(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE3,
                        attaque3Adapter.itemIdToJson(pokePokemon.getAttaque3()));
            }

            if (pokePokemon.getAttaque4() != null) {
                PokeAttaqueWebServiceClientAdapter attaque4Adapter =
                        new PokeAttaqueWebServiceClientAdapter(this.context);

                params.put(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE4,
                        attaque4Adapter.itemIdToJson(pokePokemon.getAttaque4()));
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
    public JSONObject itemIdToJson(PokePokemon item) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokePokemonWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a PokePokemon to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(PokePokemonWebServiceClientAdapter.JSON_ID,
                    values.get(PokePokemonContract.COL_ID));
            params.put(PokePokemonWebServiceClientAdapter.JSON_SURNOM,
                    values.get(PokePokemonContract.COL_SURNOM));
            params.put(PokePokemonWebServiceClientAdapter.JSON_NIVEAU,
                    values.get(PokePokemonContract.COL_NIVEAU));
            params.put(PokePokemonWebServiceClientAdapter.JSON_CAPTURE,
                    new DateTime(values.get(
                            PokePokemonContract.COL_CAPTURE)).toString(REST_UPDATE_DATE_FORMAT));
            PokeTypePokemonWebServiceClientAdapter typeAdapter =
                    new PokeTypePokemonWebServiceClientAdapter(this.context);

            params.put(PokePokemonWebServiceClientAdapter.JSON_TYPE,
                    typeAdapter.contentValuesToJson(values));
            PokeAttaqueWebServiceClientAdapter attaque1Adapter =
                    new PokeAttaqueWebServiceClientAdapter(this.context);

            params.put(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE1,
                    attaque1Adapter.contentValuesToJson(values));
            PokeAttaqueWebServiceClientAdapter attaque2Adapter =
                    new PokeAttaqueWebServiceClientAdapter(this.context);

            params.put(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE2,
                    attaque2Adapter.contentValuesToJson(values));
            PokeAttaqueWebServiceClientAdapter attaque3Adapter =
                    new PokeAttaqueWebServiceClientAdapter(this.context);

            params.put(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE3,
                    attaque3Adapter.contentValuesToJson(values));
            PokeAttaqueWebServiceClientAdapter attaque4Adapter =
                    new PokeAttaqueWebServiceClientAdapter(this.context);

            params.put(PokePokemonWebServiceClientAdapter.JSON_ATTAQUE4,
                    attaque4Adapter.contentValuesToJson(values));
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
            List<PokePokemon> items,
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
                PokePokemon item = new PokePokemon();
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
            List<PokePokemon> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
