/**************************************************************************
 * PokePositionWebServiceClientAdapterBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokePosition;
import com.antoinecronier.pokebattle.data.RestClient.Verb;
import com.antoinecronier.pokebattle.provider.contract.PokePositionContract;



/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokePositionWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class PokePositionWebServiceClientAdapterBase
        extends WebServiceClientAdapter<PokePosition> {
    /** PokePositionWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "PokePositionWSClientAdapter";

    /** JSON Object PokePosition pattern. */
    protected static String JSON_OBJECT_POKEPOSITION = "PokePosition";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_X attributes. */
    protected static String JSON_X = "x";
    /** JSON_Y attributes. */
    protected static String JSON_Y = "y";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** PokePosition REST Columns. */
    public static String[] REST_COLS = new String[]{
            PokePositionContract.COL_ID,
            PokePositionContract.COL_X,
            PokePositionContract.COL_Y
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public PokePositionWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public PokePositionWebServiceClientAdapterBase(Context context,
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
    public PokePositionWebServiceClientAdapterBase(Context context,
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
    public PokePositionWebServiceClientAdapterBase(Context context,
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
    public PokePositionWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the PokePositions in the given list. Uses the route : PokePosition.
     * @param pokePositions : The list in which the PokePositions will be returned
     * @return The number of PokePositions returned
     */
    public int getAll(List<PokePosition> pokePositions) {
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
                result = extractItems(json, "PokePositions", pokePositions);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokePositions = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "pokeposition";
    }

    /**
     * Retrieve one PokePosition. Uses the route : PokePosition/%id%.
     * @param pokePosition : The PokePosition to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(PokePosition pokePosition) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokePosition.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, pokePosition)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokePosition = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one PokePosition. Uses the route : PokePosition/%id%.
     * @param pokePosition : The PokePosition to retrieve (set the  ID)
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
     * Update a PokePosition. Uses the route : PokePosition/%id%.
     * @param pokePosition : The PokePosition to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(PokePosition pokePosition) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokePosition.getId(),
                        REST_FORMAT),
                    itemToJson(pokePosition));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, pokePosition);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a PokePosition. Uses the route : PokePosition/%id%.
     * @param pokePosition : The PokePosition to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(PokePosition pokePosition) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokePosition.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }


    /**
     * Tests if the json is a valid PokePosition Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(PokePositionWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a PokePosition from a JSONObject describing a PokePosition.
     * @param json The JSONObject describing the PokePosition
     * @param pokePosition The returned PokePosition
     * @return true if a PokePosition was found. false if not
     */
    public boolean extract(JSONObject json, PokePosition pokePosition) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(PokePositionWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(PokePositionWebServiceClientAdapter.JSON_ID)) {
                    pokePosition.setId(
                            json.getInt(PokePositionWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(PokePositionWebServiceClientAdapter.JSON_X)
                        && !json.isNull(PokePositionWebServiceClientAdapter.JSON_X)) {
                    pokePosition.setX(
                            json.getInt(PokePositionWebServiceClientAdapter.JSON_X));
                }

                if (json.has(PokePositionWebServiceClientAdapter.JSON_Y)
                        && !json.isNull(PokePositionWebServiceClientAdapter.JSON_Y)) {
                    pokePosition.setY(
                            json.getInt(PokePositionWebServiceClientAdapter.JSON_Y));
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
        String id = json.optString(PokePositionWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[3];
                if (json.has(PokePositionWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(PokePositionWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokePositionWebServiceClientAdapter.JSON_X)) {
                    row[1] = json.getString(PokePositionWebServiceClientAdapter.JSON_X);
                }
                if (json.has(PokePositionWebServiceClientAdapter.JSON_Y)) {
                    row[2] = json.getString(PokePositionWebServiceClientAdapter.JSON_Y);
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
     * Convert a PokePosition to a JSONObject.
     * @param pokePosition The PokePosition to convert
     * @return The converted PokePosition
     */
    public JSONObject itemToJson(PokePosition pokePosition) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokePositionWebServiceClientAdapter.JSON_ID,
                    pokePosition.getId());
            params.put(PokePositionWebServiceClientAdapter.JSON_X,
                    pokePosition.getX());
            params.put(PokePositionWebServiceClientAdapter.JSON_Y,
                    pokePosition.getY());
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
    public JSONObject itemIdToJson(PokePosition item) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokePositionWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a PokePosition to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(PokePositionWebServiceClientAdapter.JSON_ID,
                    values.get(PokePositionContract.COL_ID));
            params.put(PokePositionWebServiceClientAdapter.JSON_X,
                    values.get(PokePositionContract.COL_X));
            params.put(PokePositionWebServiceClientAdapter.JSON_Y,
                    values.get(PokePositionContract.COL_Y));
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
            List<PokePosition> items,
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
                PokePosition item = new PokePosition();
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
            List<PokePosition> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
