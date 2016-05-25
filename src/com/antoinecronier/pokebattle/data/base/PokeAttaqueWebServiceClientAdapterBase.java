/**************************************************************************
 * PokeAttaqueWebServiceClientAdapterBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokeAttaque;
import com.antoinecronier.pokebattle.data.RestClient.Verb;
import com.antoinecronier.pokebattle.provider.contract.PokeAttaqueContract;

import com.antoinecronier.pokebattle.entity.PokeType;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokeAttaqueWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class PokeAttaqueWebServiceClientAdapterBase
        extends WebServiceClientAdapter<PokeAttaque> {
    /** PokeAttaqueWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "PokeAttaqueWSClientAdapter";

    /** JSON Object PokeAttaque pattern. */
    protected static String JSON_OBJECT_POKEATTAQUE = "PokeAttaque";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_PUISSANCE attributes. */
    protected static String JSON_PUISSANCE = "puissance";
    /** JSON_PRECISION attributes. */
    protected static String JSON_PRECISION = "precision";
    /** JSON_TYPE attributes. */
    protected static String JSON_TYPE = "type";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** PokeAttaque REST Columns. */
    public static String[] REST_COLS = new String[]{
            PokeAttaqueContract.COL_ID,
            PokeAttaqueContract.COL_NOM,
            PokeAttaqueContract.COL_PUISSANCE,
            PokeAttaqueContract.COL_PRECISION,
            PokeAttaqueContract.COL_TYPE_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public PokeAttaqueWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public PokeAttaqueWebServiceClientAdapterBase(Context context,
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
    public PokeAttaqueWebServiceClientAdapterBase(Context context,
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
    public PokeAttaqueWebServiceClientAdapterBase(Context context,
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
    public PokeAttaqueWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the PokeAttaques in the given list. Uses the route : PokeAttaque.
     * @param pokeAttaques : The list in which the PokeAttaques will be returned
     * @return The number of PokeAttaques returned
     */
    public int getAll(List<PokeAttaque> pokeAttaques) {
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
                result = extractItems(json, "PokeAttaques", pokeAttaques);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeAttaques = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "pokeattaque";
    }

    /**
     * Retrieve one PokeAttaque. Uses the route : PokeAttaque/%id%.
     * @param pokeAttaque : The PokeAttaque to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(PokeAttaque pokeAttaque) {
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
                if (extract(json, pokeAttaque)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeAttaque = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one PokeAttaque. Uses the route : PokeAttaque/%id%.
     * @param pokeAttaque : The PokeAttaque to retrieve (set the  ID)
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
     * Update a PokeAttaque. Uses the route : PokeAttaque/%id%.
     * @param pokeAttaque : The PokeAttaque to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(PokeAttaque pokeAttaque) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeAttaque.getId(),
                        REST_FORMAT),
                    itemToJson(pokeAttaque));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, pokeAttaque);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a PokeAttaque. Uses the route : PokeAttaque/%id%.
     * @param pokeAttaque : The PokeAttaque to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(PokeAttaque pokeAttaque) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeAttaque.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the PokeAttaques associated with a PokeType. Uses the route : poketype/%PokeType_id%/pokeattaque.
     * @param pokeAttaques : The list in which the PokeAttaques will be returned
     * @param poketype : The associated poketype
     * @return The number of PokeAttaques returned
     */
    public int getByType(List<PokeAttaque> pokeAttaques, PokeType pokeType) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeType.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "PokeAttaques", pokeAttaques);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeAttaques = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid PokeAttaque Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(PokeAttaqueWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a PokeAttaque from a JSONObject describing a PokeAttaque.
     * @param json The JSONObject describing the PokeAttaque
     * @param pokeAttaque The returned PokeAttaque
     * @return true if a PokeAttaque was found. false if not
     */
    public boolean extract(JSONObject json, PokeAttaque pokeAttaque) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(PokeAttaqueWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(PokeAttaqueWebServiceClientAdapter.JSON_ID)) {
                    pokeAttaque.setId(
                            json.getInt(PokeAttaqueWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(PokeAttaqueWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(PokeAttaqueWebServiceClientAdapter.JSON_NOM)) {
                    pokeAttaque.setNom(
                            json.getString(PokeAttaqueWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(PokeAttaqueWebServiceClientAdapter.JSON_PUISSANCE)
                        && !json.isNull(PokeAttaqueWebServiceClientAdapter.JSON_PUISSANCE)) {
                    pokeAttaque.setPuissance(
                            json.getInt(PokeAttaqueWebServiceClientAdapter.JSON_PUISSANCE));
                }

                if (json.has(PokeAttaqueWebServiceClientAdapter.JSON_PRECISION)
                        && !json.isNull(PokeAttaqueWebServiceClientAdapter.JSON_PRECISION)) {
                    pokeAttaque.setPrecision(
                            json.getInt(PokeAttaqueWebServiceClientAdapter.JSON_PRECISION));
                }

                if (json.has(PokeAttaqueWebServiceClientAdapter.JSON_TYPE)
                        && !json.isNull(PokeAttaqueWebServiceClientAdapter.JSON_TYPE)) {

                    try {
                        PokeTypeWebServiceClientAdapter typeAdapter =
                                new PokeTypeWebServiceClientAdapter(this.context);
                        PokeType type =
                                new PokeType();

                        if (typeAdapter.extract(
                                json.optJSONObject(
                                        PokeAttaqueWebServiceClientAdapter.JSON_TYPE),
                                        type)) {
                            pokeAttaque.setType(type);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains PokeType data");
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
        String id = json.optString(PokeAttaqueWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[5];
                if (json.has(PokeAttaqueWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(PokeAttaqueWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokeAttaqueWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(PokeAttaqueWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(PokeAttaqueWebServiceClientAdapter.JSON_PUISSANCE)) {
                    row[2] = json.getString(PokeAttaqueWebServiceClientAdapter.JSON_PUISSANCE);
                }
                if (json.has(PokeAttaqueWebServiceClientAdapter.JSON_PRECISION)) {
                    row[3] = json.getString(PokeAttaqueWebServiceClientAdapter.JSON_PRECISION);
                }
                if (json.has(PokeAttaqueWebServiceClientAdapter.JSON_TYPE)) {
                    JSONObject typeJson = json.getJSONObject(
                            PokeAttaqueWebServiceClientAdapter.JSON_TYPE);
                    row[4] = typeJson.getString(
                            PokeTypeWebServiceClientAdapter.JSON_ID);
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
     * Convert a PokeAttaque to a JSONObject.
     * @param pokeAttaque The PokeAttaque to convert
     * @return The converted PokeAttaque
     */
    public JSONObject itemToJson(PokeAttaque pokeAttaque) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokeAttaqueWebServiceClientAdapter.JSON_ID,
                    pokeAttaque.getId());
            params.put(PokeAttaqueWebServiceClientAdapter.JSON_NOM,
                    pokeAttaque.getNom());
            params.put(PokeAttaqueWebServiceClientAdapter.JSON_PUISSANCE,
                    pokeAttaque.getPuissance());
            params.put(PokeAttaqueWebServiceClientAdapter.JSON_PRECISION,
                    pokeAttaque.getPrecision());

            if (pokeAttaque.getType() != null) {
                PokeTypeWebServiceClientAdapter typeAdapter =
                        new PokeTypeWebServiceClientAdapter(this.context);

                params.put(PokeAttaqueWebServiceClientAdapter.JSON_TYPE,
                        typeAdapter.itemIdToJson(pokeAttaque.getType()));
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
    public JSONObject itemIdToJson(PokeAttaque item) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokeAttaqueWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a PokeAttaque to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(PokeAttaqueWebServiceClientAdapter.JSON_ID,
                    values.get(PokeAttaqueContract.COL_ID));
            params.put(PokeAttaqueWebServiceClientAdapter.JSON_NOM,
                    values.get(PokeAttaqueContract.COL_NOM));
            params.put(PokeAttaqueWebServiceClientAdapter.JSON_PUISSANCE,
                    values.get(PokeAttaqueContract.COL_PUISSANCE));
            params.put(PokeAttaqueWebServiceClientAdapter.JSON_PRECISION,
                    values.get(PokeAttaqueContract.COL_PRECISION));
            PokeTypeWebServiceClientAdapter typeAdapter =
                    new PokeTypeWebServiceClientAdapter(this.context);

            params.put(PokeAttaqueWebServiceClientAdapter.JSON_TYPE,
                    typeAdapter.contentValuesToJson(values));
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
            List<PokeAttaque> items,
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
                PokeAttaque item = new PokeAttaque();
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
            List<PokeAttaque> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
