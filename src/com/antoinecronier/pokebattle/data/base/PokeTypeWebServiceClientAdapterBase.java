/**************************************************************************
 * PokeTypeWebServiceClientAdapterBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokeType;
import com.antoinecronier.pokebattle.data.RestClient.Verb;
import com.antoinecronier.pokebattle.provider.contract.PokeTypeContract;

import com.antoinecronier.pokebattle.entity.PokeTypePokemon;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokeTypeWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class PokeTypeWebServiceClientAdapterBase
        extends WebServiceClientAdapter<PokeType> {
    /** PokeTypeWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "PokeTypeWSClientAdapter";

    /** JSON Object PokeType pattern. */
    protected static String JSON_OBJECT_POKETYPE = "PokeType";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_MODIFICATEUR attributes. */
    protected static String JSON_MODIFICATEUR = "modificateur";
    /** JSON_TYPEFORT attributes. */
    protected static String JSON_TYPEFORT = "typeFort";
    /** JSON_TYPEFAIBLE attributes. */
    protected static String JSON_TYPEFAIBLE = "typeFaible";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** PokeType REST Columns. */
    public static String[] REST_COLS = new String[]{
            PokeTypeContract.COL_ID,
            PokeTypeContract.COL_NOM,
            PokeTypeContract.COL_MODIFICATEUR
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public PokeTypeWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public PokeTypeWebServiceClientAdapterBase(Context context,
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
    public PokeTypeWebServiceClientAdapterBase(Context context,
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
    public PokeTypeWebServiceClientAdapterBase(Context context,
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
    public PokeTypeWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the PokeTypes in the given list. Uses the route : PokeType.
     * @param pokeTypes : The list in which the PokeTypes will be returned
     * @return The number of PokeTypes returned
     */
    public int getAll(List<PokeType> pokeTypes) {
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
                result = extractItems(json, "PokeTypes", pokeTypes);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeTypes = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "poketype";
    }

    /**
     * Retrieve one PokeType. Uses the route : PokeType/%id%.
     * @param pokeType : The PokeType to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(PokeType pokeType) {
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
                if (extract(json, pokeType)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeType = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one PokeType. Uses the route : PokeType/%id%.
     * @param pokeType : The PokeType to retrieve (set the  ID)
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
     * Update a PokeType. Uses the route : PokeType/%id%.
     * @param pokeType : The PokeType to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(PokeType pokeType) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeType.getId(),
                        REST_FORMAT),
                    itemToJson(pokeType));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, pokeType);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a PokeType. Uses the route : PokeType/%id%.
     * @param pokeType : The PokeType to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(PokeType pokeType) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeType.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the PokeTypes associated with a PokeType. Uses the route : poketype/%PokeType_id%/poketype.
     * @param pokeTypes : The list in which the PokeTypes will be returned
     * @param poketype : The associated poketype
     * @return The number of PokeTypes returned
     */
    public int getByPokeTypetypeFortInternal(List<PokeType> pokeTypes, PokeType pokeType) {
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
                result = this.extractItems(json, "PokeTypes", pokeTypes);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeTypes = null;
            }
        }

        return result;
    }


    /**
     * Get the PokeTypes associated with a PokeType. Uses the route : poketype/%PokeType_id%/poketype.
     * @param pokeTypes : The list in which the PokeTypes will be returned
     * @param poketype : The associated poketype
     * @return The number of PokeTypes returned
     */
    public int getByPokeTypetypeFaibleInternal(List<PokeType> pokeTypes, PokeType pokeType) {
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
                result = this.extractItems(json, "PokeTypes", pokeTypes);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeTypes = null;
            }
        }

        return result;
    }


    /**
     * Get the PokeTypes associated with a PokeTypePokemon. Uses the route : poketypepokemon/%PokeTypePokemon_id%/poketype.
     * @param pokeTypes : The list in which the PokeTypes will be returned
     * @param poketypepokemon : The associated poketypepokemon
     * @return The number of PokeTypes returned
     */
    public int getByPokeTypePokemontypesInternal(List<PokeType> pokeTypes, PokeTypePokemon pokeTypePokemon) {
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
                result = this.extractItems(json, "PokeTypes", pokeTypes);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeTypes = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid PokeType Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(PokeTypeWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a PokeType from a JSONObject describing a PokeType.
     * @param json The JSONObject describing the PokeType
     * @param pokeType The returned PokeType
     * @return true if a PokeType was found. false if not
     */
    public boolean extract(JSONObject json, PokeType pokeType) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(PokeTypeWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(PokeTypeWebServiceClientAdapter.JSON_ID)) {
                    pokeType.setId(
                            json.getInt(PokeTypeWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(PokeTypeWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(PokeTypeWebServiceClientAdapter.JSON_NOM)) {
                    pokeType.setNom(
                            json.getString(PokeTypeWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(PokeTypeWebServiceClientAdapter.JSON_MODIFICATEUR)
                        && !json.isNull(PokeTypeWebServiceClientAdapter.JSON_MODIFICATEUR)) {
                    pokeType.setModificateur(
                            json.getInt(PokeTypeWebServiceClientAdapter.JSON_MODIFICATEUR));
                }

                if (json.has(PokeTypeWebServiceClientAdapter.JSON_TYPEFORT)
                        && !json.isNull(PokeTypeWebServiceClientAdapter.JSON_TYPEFORT)) {
                    ArrayList<PokeType> typeFort =
                            new ArrayList<PokeType>();
                    PokeTypeWebServiceClientAdapter typeFortAdapter =
                            new PokeTypeWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(PokeTypeWebServiceClientAdapter.JSON_TYPEFORT);
                        typeFortAdapter.extractItems(
                                json, PokeTypeWebServiceClientAdapter.JSON_TYPEFORT,
                                typeFort);
                        pokeType.setTypeFort(typeFort);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(PokeTypeWebServiceClientAdapter.JSON_TYPEFAIBLE)
                        && !json.isNull(PokeTypeWebServiceClientAdapter.JSON_TYPEFAIBLE)) {
                    ArrayList<PokeType> typeFaible =
                            new ArrayList<PokeType>();
                    PokeTypeWebServiceClientAdapter typeFaibleAdapter =
                            new PokeTypeWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(PokeTypeWebServiceClientAdapter.JSON_TYPEFAIBLE);
                        typeFaibleAdapter.extractItems(
                                json, PokeTypeWebServiceClientAdapter.JSON_TYPEFAIBLE,
                                typeFaible);
                        pokeType.setTypeFaible(typeFaible);
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
        String id = json.optString(PokeTypeWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[3];
                if (json.has(PokeTypeWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(PokeTypeWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokeTypeWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(PokeTypeWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(PokeTypeWebServiceClientAdapter.JSON_MODIFICATEUR)) {
                    row[2] = json.getString(PokeTypeWebServiceClientAdapter.JSON_MODIFICATEUR);
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
     * Convert a PokeType to a JSONObject.
     * @param pokeType The PokeType to convert
     * @return The converted PokeType
     */
    public JSONObject itemToJson(PokeType pokeType) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokeTypeWebServiceClientAdapter.JSON_ID,
                    pokeType.getId());
            params.put(PokeTypeWebServiceClientAdapter.JSON_NOM,
                    pokeType.getNom());
            params.put(PokeTypeWebServiceClientAdapter.JSON_MODIFICATEUR,
                    pokeType.getModificateur());

            if (pokeType.getTypeFort() != null) {
                PokeTypeWebServiceClientAdapter typeFortAdapter =
                        new PokeTypeWebServiceClientAdapter(this.context);

                params.put(PokeTypeWebServiceClientAdapter.JSON_TYPEFORT,
                        typeFortAdapter.itemsIdToJson(pokeType.getTypeFort()));
            }

            if (pokeType.getTypeFaible() != null) {
                PokeTypeWebServiceClientAdapter typeFaibleAdapter =
                        new PokeTypeWebServiceClientAdapter(this.context);

                params.put(PokeTypeWebServiceClientAdapter.JSON_TYPEFAIBLE,
                        typeFaibleAdapter.itemsIdToJson(pokeType.getTypeFaible()));
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
    public JSONObject itemIdToJson(PokeType item) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokeTypeWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a PokeType to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(PokeTypeWebServiceClientAdapter.JSON_ID,
                    values.get(PokeTypeContract.COL_ID));
            params.put(PokeTypeWebServiceClientAdapter.JSON_NOM,
                    values.get(PokeTypeContract.COL_NOM));
            params.put(PokeTypeWebServiceClientAdapter.JSON_MODIFICATEUR,
                    values.get(PokeTypeContract.COL_MODIFICATEUR));
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
            List<PokeType> items,
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
                PokeType item = new PokeType();
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
            List<PokeType> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
