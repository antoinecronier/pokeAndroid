/**************************************************************************
 * PokeDresseurWebServiceClientAdapterBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokeDresseur;
import com.antoinecronier.pokebattle.data.RestClient.Verb;
import com.antoinecronier.pokebattle.provider.contract.PokeDresseurContract;

import com.antoinecronier.pokebattle.entity.PokeNpc;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokeDresseurWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class PokeDresseurWebServiceClientAdapterBase
        extends WebServiceClientAdapter<PokeDresseur> {
    /** PokeDresseurWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "PokeDresseurWSClientAdapter";

    /** JSON Object PokeDresseur pattern. */
    protected static String JSON_OBJECT_POKEDRESSEUR = "PokeDresseur";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_PSEUDO attributes. */
    protected static String JSON_PSEUDO = "pseudo";
    /** JSON_LOGIN attributes. */
    protected static String JSON_LOGIN = "login";
    /** JSON_PASSWORD attributes. */
    protected static String JSON_PASSWORD = "password";
    /** JSON_NPCS attributes. */
    protected static String JSON_NPCS = "npcs";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** PokeDresseur REST Columns. */
    public static String[] REST_COLS = new String[]{
            PokeDresseurContract.COL_ID,
            PokeDresseurContract.COL_PSEUDO,
            PokeDresseurContract.COL_LOGIN,
            PokeDresseurContract.COL_PASSWORD
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public PokeDresseurWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public PokeDresseurWebServiceClientAdapterBase(Context context,
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
    public PokeDresseurWebServiceClientAdapterBase(Context context,
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
    public PokeDresseurWebServiceClientAdapterBase(Context context,
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
    public PokeDresseurWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the PokeDresseurs in the given list. Uses the route : PokeDresseur.
     * @param pokeDresseurs : The list in which the PokeDresseurs will be returned
     * @return The number of PokeDresseurs returned
     */
    public int getAll(List<PokeDresseur> pokeDresseurs) {
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
                result = extractItems(json, "PokeDresseurs", pokeDresseurs);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeDresseurs = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "pokedresseur";
    }

    /**
     * Retrieve one PokeDresseur. Uses the route : PokeDresseur/%id%.
     * @param pokeDresseur : The PokeDresseur to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(PokeDresseur pokeDresseur) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeDresseur.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, pokeDresseur)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeDresseur = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one PokeDresseur. Uses the route : PokeDresseur/%id%.
     * @param pokeDresseur : The PokeDresseur to retrieve (set the  ID)
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
     * Update a PokeDresseur. Uses the route : PokeDresseur/%id%.
     * @param pokeDresseur : The PokeDresseur to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(PokeDresseur pokeDresseur) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeDresseur.getId(),
                        REST_FORMAT),
                    itemToJson(pokeDresseur));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, pokeDresseur);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a PokeDresseur. Uses the route : PokeDresseur/%id%.
     * @param pokeDresseur : The PokeDresseur to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(PokeDresseur pokeDresseur) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeDresseur.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }



    /**
     * Tests if the json is a valid PokeDresseur Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(PokeDresseurWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a PokeDresseur from a JSONObject describing a PokeDresseur.
     * @param json The JSONObject describing the PokeDresseur
     * @param pokeDresseur The returned PokeDresseur
     * @return true if a PokeDresseur was found. false if not
     */
    public boolean extract(JSONObject json, PokeDresseur pokeDresseur) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(PokeDresseurWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(PokeDresseurWebServiceClientAdapter.JSON_ID)) {
                    pokeDresseur.setId(
                            json.getInt(PokeDresseurWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(PokeDresseurWebServiceClientAdapter.JSON_PSEUDO)
                        && !json.isNull(PokeDresseurWebServiceClientAdapter.JSON_PSEUDO)) {
                    pokeDresseur.setPseudo(
                            json.getString(PokeDresseurWebServiceClientAdapter.JSON_PSEUDO));
                }

                if (json.has(PokeDresseurWebServiceClientAdapter.JSON_LOGIN)
                        && !json.isNull(PokeDresseurWebServiceClientAdapter.JSON_LOGIN)) {
                    pokeDresseur.setLogin(
                            json.getString(PokeDresseurWebServiceClientAdapter.JSON_LOGIN));
                }

                if (json.has(PokeDresseurWebServiceClientAdapter.JSON_PASSWORD)
                        && !json.isNull(PokeDresseurWebServiceClientAdapter.JSON_PASSWORD)) {
                    pokeDresseur.setPassword(
                            json.getString(PokeDresseurWebServiceClientAdapter.JSON_PASSWORD));
                }

                if (json.has(PokeDresseurWebServiceClientAdapter.JSON_NPCS)
                        && !json.isNull(PokeDresseurWebServiceClientAdapter.JSON_NPCS)) {
                    ArrayList<PokeNpc> npcs =
                            new ArrayList<PokeNpc>();
                    PokeNpcWebServiceClientAdapter npcsAdapter =
                            new PokeNpcWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(PokeDresseurWebServiceClientAdapter.JSON_NPCS);
                        npcsAdapter.extractItems(
                                json, PokeDresseurWebServiceClientAdapter.JSON_NPCS,
                                npcs);
                        pokeDresseur.setNpcs(npcs);
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
        String id = json.optString(PokeDresseurWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[4];
                if (json.has(PokeDresseurWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(PokeDresseurWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokeDresseurWebServiceClientAdapter.JSON_PSEUDO)) {
                    row[1] = json.getString(PokeDresseurWebServiceClientAdapter.JSON_PSEUDO);
                }
                if (json.has(PokeDresseurWebServiceClientAdapter.JSON_LOGIN)) {
                    row[2] = json.getString(PokeDresseurWebServiceClientAdapter.JSON_LOGIN);
                }
                if (json.has(PokeDresseurWebServiceClientAdapter.JSON_PASSWORD)) {
                    row[3] = json.getString(PokeDresseurWebServiceClientAdapter.JSON_PASSWORD);
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
     * Convert a PokeDresseur to a JSONObject.
     * @param pokeDresseur The PokeDresseur to convert
     * @return The converted PokeDresseur
     */
    public JSONObject itemToJson(PokeDresseur pokeDresseur) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokeDresseurWebServiceClientAdapter.JSON_ID,
                    pokeDresseur.getId());
            params.put(PokeDresseurWebServiceClientAdapter.JSON_PSEUDO,
                    pokeDresseur.getPseudo());
            params.put(PokeDresseurWebServiceClientAdapter.JSON_LOGIN,
                    pokeDresseur.getLogin());
            params.put(PokeDresseurWebServiceClientAdapter.JSON_PASSWORD,
                    pokeDresseur.getPassword());

            if (pokeDresseur.getNpcs() != null) {
                PokeNpcWebServiceClientAdapter npcsAdapter =
                        new PokeNpcWebServiceClientAdapter(this.context);

                params.put(PokeDresseurWebServiceClientAdapter.JSON_NPCS,
                        npcsAdapter.itemsIdToJson(pokeDresseur.getNpcs()));
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
    public JSONObject itemIdToJson(PokeDresseur item) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokeDresseurWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a PokeDresseur to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(PokeDresseurWebServiceClientAdapter.JSON_ID,
                    values.get(PokeDresseurContract.COL_ID));
            params.put(PokeDresseurWebServiceClientAdapter.JSON_PSEUDO,
                    values.get(PokeDresseurContract.COL_PSEUDO));
            params.put(PokeDresseurWebServiceClientAdapter.JSON_LOGIN,
                    values.get(PokeDresseurContract.COL_LOGIN));
            params.put(PokeDresseurWebServiceClientAdapter.JSON_PASSWORD,
                    values.get(PokeDresseurContract.COL_PASSWORD));
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
            List<PokeDresseur> items,
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
                PokeDresseur item = new PokeDresseur();
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
            List<PokeDresseur> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
