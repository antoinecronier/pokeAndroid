/**************************************************************************
 * PokeZoneWebServiceClientAdapterBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokeZone;
import com.antoinecronier.pokebattle.data.RestClient.Verb;
import com.antoinecronier.pokebattle.provider.contract.PokeZoneContract;

import com.antoinecronier.pokebattle.entity.PokeTypePokemon;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokeZoneWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class PokeZoneWebServiceClientAdapterBase
        extends WebServiceClientAdapter<PokeZone> {
    /** PokeZoneWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "PokeZoneWSClientAdapter";

    /** JSON Object PokeZone pattern. */
    protected static String JSON_OBJECT_POKEZONE = "PokeZone";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** PokeZone REST Columns. */
    public static String[] REST_COLS = new String[]{
            PokeZoneContract.COL_ID,
            PokeZoneContract.COL_NOM
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public PokeZoneWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public PokeZoneWebServiceClientAdapterBase(Context context,
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
    public PokeZoneWebServiceClientAdapterBase(Context context,
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
    public PokeZoneWebServiceClientAdapterBase(Context context,
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
    public PokeZoneWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the PokeZones in the given list. Uses the route : PokeZone.
     * @param pokeZones : The list in which the PokeZones will be returned
     * @return The number of PokeZones returned
     */
    public int getAll(List<PokeZone> pokeZones) {
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
                result = extractItems(json, "PokeZones", pokeZones);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeZones = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "pokezone";
    }

    /**
     * Retrieve one PokeZone. Uses the route : PokeZone/%id%.
     * @param pokeZone : The PokeZone to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(PokeZone pokeZone) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeZone.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, pokeZone)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeZone = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one PokeZone. Uses the route : PokeZone/%id%.
     * @param pokeZone : The PokeZone to retrieve (set the  ID)
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
     * Update a PokeZone. Uses the route : PokeZone/%id%.
     * @param pokeZone : The PokeZone to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(PokeZone pokeZone) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeZone.getId(),
                        REST_FORMAT),
                    itemToJson(pokeZone));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, pokeZone);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a PokeZone. Uses the route : PokeZone/%id%.
     * @param pokeZone : The PokeZone to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(PokeZone pokeZone) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeZone.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the PokeZones associated with a PokeTypePokemon. Uses the route : poketypepokemon/%PokeTypePokemon_id%/pokezone.
     * @param pokeZones : The list in which the PokeZones will be returned
     * @param poketypepokemon : The associated poketypepokemon
     * @return The number of PokeZones returned
     */
    public int getByPokeTypePokemonzonesInternal(List<PokeZone> pokeZones, PokeTypePokemon pokeTypePokemon) {
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
                result = this.extractItems(json, "PokeZones", pokeZones);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeZones = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid PokeZone Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(PokeZoneWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a PokeZone from a JSONObject describing a PokeZone.
     * @param json The JSONObject describing the PokeZone
     * @param pokeZone The returned PokeZone
     * @return true if a PokeZone was found. false if not
     */
    public boolean extract(JSONObject json, PokeZone pokeZone) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(PokeZoneWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(PokeZoneWebServiceClientAdapter.JSON_ID)) {
                    pokeZone.setId(
                            json.getInt(PokeZoneWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(PokeZoneWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(PokeZoneWebServiceClientAdapter.JSON_NOM)) {
                    pokeZone.setNom(
                            json.getString(PokeZoneWebServiceClientAdapter.JSON_NOM));
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
        String id = json.optString(PokeZoneWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[2];
                if (json.has(PokeZoneWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(PokeZoneWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokeZoneWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(PokeZoneWebServiceClientAdapter.JSON_NOM);
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
     * Convert a PokeZone to a JSONObject.
     * @param pokeZone The PokeZone to convert
     * @return The converted PokeZone
     */
    public JSONObject itemToJson(PokeZone pokeZone) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokeZoneWebServiceClientAdapter.JSON_ID,
                    pokeZone.getId());
            params.put(PokeZoneWebServiceClientAdapter.JSON_NOM,
                    pokeZone.getNom());
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
    public JSONObject itemIdToJson(PokeZone item) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokeZoneWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a PokeZone to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(PokeZoneWebServiceClientAdapter.JSON_ID,
                    values.get(PokeZoneContract.COL_ID));
            params.put(PokeZoneWebServiceClientAdapter.JSON_NOM,
                    values.get(PokeZoneContract.COL_NOM));
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
            List<PokeZone> items,
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
                PokeZone item = new PokeZone();
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
            List<PokeZone> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
