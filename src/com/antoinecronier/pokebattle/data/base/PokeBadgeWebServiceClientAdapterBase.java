/**************************************************************************
 * PokeBadgeWebServiceClientAdapterBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.data.RestClient.Verb;
import com.antoinecronier.pokebattle.provider.contract.PokeBadgeContract;

import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.entity.PokeBadgeBonus;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokeBadgeWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class PokeBadgeWebServiceClientAdapterBase
        extends WebServiceClientAdapter<PokeBadge> {
    /** PokeBadgeWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "PokeBadgeWSClientAdapter";

    /** JSON Object PokeBadge pattern. */
    protected static String JSON_OBJECT_POKEBADGE = "PokeBadge";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_BONUS attributes. */
    protected static String JSON_BONUS = "bonus";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** PokeBadge REST Columns. */
    public static String[] REST_COLS = new String[]{
            PokeBadgeContract.COL_ID,
            PokeBadgeContract.COL_NOM,
            PokeBadgeContract.COL_BONUS
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public PokeBadgeWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public PokeBadgeWebServiceClientAdapterBase(Context context,
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
    public PokeBadgeWebServiceClientAdapterBase(Context context,
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
    public PokeBadgeWebServiceClientAdapterBase(Context context,
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
    public PokeBadgeWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the PokeBadges in the given list. Uses the route : PokeBadge.
     * @param pokeBadges : The list in which the PokeBadges will be returned
     * @return The number of PokeBadges returned
     */
    public int getAll(List<PokeBadge> pokeBadges) {
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
                result = extractItems(json, "PokeBadges", pokeBadges);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeBadges = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "pokebadge";
    }

    /**
     * Retrieve one PokeBadge. Uses the route : PokeBadge/%id%.
     * @param pokeBadge : The PokeBadge to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(PokeBadge pokeBadge) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeBadge.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, pokeBadge)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeBadge = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one PokeBadge. Uses the route : PokeBadge/%id%.
     * @param pokeBadge : The PokeBadge to retrieve (set the  ID)
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
     * Update a PokeBadge. Uses the route : PokeBadge/%id%.
     * @param pokeBadge : The PokeBadge to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(PokeBadge pokeBadge) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeBadge.getId(),
                        REST_FORMAT),
                    itemToJson(pokeBadge));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, pokeBadge);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a PokeBadge. Uses the route : PokeBadge/%id%.
     * @param pokeBadge : The PokeBadge to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(PokeBadge pokeBadge) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeBadge.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the PokeBadges associated with a PokeNpc. Uses the route : pokenpc/%PokeNpc_id%/pokebadge.
     * @param pokeBadges : The list in which the PokeBadges will be returned
     * @param pokenpc : The associated pokenpc
     * @return The number of PokeBadges returned
     */
    public int getByPokeNpcbadgeInternal(List<PokeBadge> pokeBadges, PokeNpc pokeNpc) {
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
                result = this.extractItems(json, "PokeBadges", pokeBadges);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeBadges = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid PokeBadge Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(PokeBadgeWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a PokeBadge from a JSONObject describing a PokeBadge.
     * @param json The JSONObject describing the PokeBadge
     * @param pokeBadge The returned PokeBadge
     * @return true if a PokeBadge was found. false if not
     */
    public boolean extract(JSONObject json, PokeBadge pokeBadge) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(PokeBadgeWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(PokeBadgeWebServiceClientAdapter.JSON_ID)) {
                    pokeBadge.setId(
                            json.getInt(PokeBadgeWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(PokeBadgeWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(PokeBadgeWebServiceClientAdapter.JSON_NOM)) {
                    pokeBadge.setNom(
                            json.getString(PokeBadgeWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(PokeBadgeWebServiceClientAdapter.JSON_BONUS)
                        && !json.isNull(PokeBadgeWebServiceClientAdapter.JSON_BONUS)) {
                    pokeBadge.setBonus(PokeBadgeBonus.valueOf(json.getString(
                                    PokeBadgeWebServiceClientAdapter.JSON_BONUS)));
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
        String id = json.optString(PokeBadgeWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[3];
                if (json.has(PokeBadgeWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(PokeBadgeWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokeBadgeWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(PokeBadgeWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(PokeBadgeWebServiceClientAdapter.JSON_BONUS)) {
                    row[2] = json.getString(PokeBadgeWebServiceClientAdapter.JSON_BONUS);
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
     * Convert a PokeBadge to a JSONObject.
     * @param pokeBadge The PokeBadge to convert
     * @return The converted PokeBadge
     */
    public JSONObject itemToJson(PokeBadge pokeBadge) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokeBadgeWebServiceClientAdapter.JSON_ID,
                    pokeBadge.getId());
            params.put(PokeBadgeWebServiceClientAdapter.JSON_NOM,
                    pokeBadge.getNom());

            if (pokeBadge.getBonus() != null) {
                params.put(PokeBadgeWebServiceClientAdapter.JSON_BONUS,
                        pokeBadge.getBonus().name());
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
    public JSONObject itemIdToJson(PokeBadge item) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokeBadgeWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a PokeBadge to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(PokeBadgeWebServiceClientAdapter.JSON_ID,
                    values.get(PokeBadgeContract.COL_ID));
            params.put(PokeBadgeWebServiceClientAdapter.JSON_NOM,
                    values.get(PokeBadgeContract.COL_NOM));
            params.put(PokeBadgeWebServiceClientAdapter.JSON_BONUS,
                    values.get(PokeBadgeContract.COL_BONUS));
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
            List<PokeBadge> items,
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
                PokeBadge item = new PokeBadge();
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
            List<PokeBadge> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
