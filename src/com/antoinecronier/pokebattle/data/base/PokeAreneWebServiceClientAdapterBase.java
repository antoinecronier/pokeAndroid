/**************************************************************************
 * PokeAreneWebServiceClientAdapterBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokeArene;
import com.antoinecronier.pokebattle.data.RestClient.Verb;
import com.antoinecronier.pokebattle.provider.contract.PokeAreneContract;

import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.entity.PokeZone;
import com.antoinecronier.pokebattle.entity.PokePosition;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokeAreneWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class PokeAreneWebServiceClientAdapterBase
        extends WebServiceClientAdapter<PokeArene> {
    /** PokeAreneWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "PokeAreneWSClientAdapter";

    /** JSON Object PokeArene pattern. */
    protected static String JSON_OBJECT_POKEARENE = "PokeArene";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_MAITRE attributes. */
    protected static String JSON_MAITRE = "maitre";
    /** JSON_DRESSEURS attributes. */
    protected static String JSON_DRESSEURS = "dresseurs";
    /** JSON_BADGE attributes. */
    protected static String JSON_BADGE = "badge";
    /** JSON_ZONE attributes. */
    protected static String JSON_ZONE = "zone";
    /** JSON_POSITION attributes. */
    protected static String JSON_POSITION = "position";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** PokeArene REST Columns. */
    public static String[] REST_COLS = new String[]{
            PokeAreneContract.COL_ID,
            PokeAreneContract.COL_NOM,
            PokeAreneContract.COL_MAITRE_ID,
            PokeAreneContract.COL_BADGE_ID,
            PokeAreneContract.COL_ZONE_ID,
            PokeAreneContract.COL_POSITION_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public PokeAreneWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public PokeAreneWebServiceClientAdapterBase(Context context,
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
    public PokeAreneWebServiceClientAdapterBase(Context context,
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
    public PokeAreneWebServiceClientAdapterBase(Context context,
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
    public PokeAreneWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the PokeArenes in the given list. Uses the route : PokeArene.
     * @param pokeArenes : The list in which the PokeArenes will be returned
     * @return The number of PokeArenes returned
     */
    public int getAll(List<PokeArene> pokeArenes) {
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
                result = extractItems(json, "PokeArenes", pokeArenes);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeArenes = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "pokearene";
    }

    /**
     * Retrieve one PokeArene. Uses the route : PokeArene/%id%.
     * @param pokeArene : The PokeArene to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(PokeArene pokeArene) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeArene.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, pokeArene)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeArene = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one PokeArene. Uses the route : PokeArene/%id%.
     * @param pokeArene : The PokeArene to retrieve (set the  ID)
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
     * Update a PokeArene. Uses the route : PokeArene/%id%.
     * @param pokeArene : The PokeArene to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(PokeArene pokeArene) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeArene.getId(),
                        REST_FORMAT),
                    itemToJson(pokeArene));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, pokeArene);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a PokeArene. Uses the route : PokeArene/%id%.
     * @param pokeArene : The PokeArene to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(PokeArene pokeArene) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeArene.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the PokeArene associated with a PokeNpc. Uses the route : pokenpc/%PokeNpc_id%/pokearene.
     * @param pokeArene : The PokeArene that will be returned
     * @param pokenpc : The associated pokenpc
     * @return -1 if an error has occurred. 0 if not.
     */
    public int getByPokeNpc(PokeArene pokeArene, PokeNpc pokeNpc) {
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
                this.extract(json, pokeArene);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeArene = null;
            }
        }

        return result;
    }


    /**
     * Get the PokeArene associated with a PokeBadge. Uses the route : pokebadge/%PokeBadge_id%/pokearene.
     * @param pokeArene : The PokeArene that will be returned
     * @param pokebadge : The associated pokebadge
     * @return -1 if an error has occurred. 0 if not.
     */
    public int getByPokeBadge(PokeArene pokeArene, PokeBadge pokeBadge) {
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
                this.extract(json, pokeArene);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeArene = null;
            }
        }

        return result;
    }

    /**
     * Get the PokeArenes associated with a PokeZone. Uses the route : pokezone/%PokeZone_id%/pokearene.
     * @param pokeArenes : The list in which the PokeArenes will be returned
     * @param pokezone : The associated pokezone
     * @return The number of PokeArenes returned
     */
    public int getByZone(List<PokeArene> pokeArenes, PokeZone pokeZone) {
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
                result = this.extractItems(json, "PokeArenes", pokeArenes);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeArenes = null;
            }
        }

        return result;
    }

    /**
     * Get the PokeArenes associated with a PokePosition. Uses the route : pokeposition/%PokePosition_id%/pokearene.
     * @param pokeArenes : The list in which the PokeArenes will be returned
     * @param pokeposition : The associated pokeposition
     * @return The number of PokeArenes returned
     */
    public int getByPosition(List<PokeArene> pokeArenes, PokePosition pokePosition) {
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
                result = this.extractItems(json, "PokeArenes", pokeArenes);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeArenes = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid PokeArene Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(PokeAreneWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a PokeArene from a JSONObject describing a PokeArene.
     * @param json The JSONObject describing the PokeArene
     * @param pokeArene The returned PokeArene
     * @return true if a PokeArene was found. false if not
     */
    public boolean extract(JSONObject json, PokeArene pokeArene) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(PokeAreneWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(PokeAreneWebServiceClientAdapter.JSON_ID)) {
                    pokeArene.setId(
                            json.getInt(PokeAreneWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(PokeAreneWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(PokeAreneWebServiceClientAdapter.JSON_NOM)) {
                    pokeArene.setNom(
                            json.getString(PokeAreneWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(PokeAreneWebServiceClientAdapter.JSON_MAITRE)
                        && !json.isNull(PokeAreneWebServiceClientAdapter.JSON_MAITRE)) {

                    try {
                        PokeNpcWebServiceClientAdapter maitreAdapter =
                                new PokeNpcWebServiceClientAdapter(this.context);
                        PokeNpc maitre =
                                new PokeNpc();

                        if (maitreAdapter.extract(
                                json.optJSONObject(
                                        PokeAreneWebServiceClientAdapter.JSON_MAITRE),
                                        maitre)) {
                            pokeArene.setMaitre(maitre);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains PokeNpc data");
                    }
                }

                if (json.has(PokeAreneWebServiceClientAdapter.JSON_DRESSEURS)
                        && !json.isNull(PokeAreneWebServiceClientAdapter.JSON_DRESSEURS)) {
                    ArrayList<PokeNpc> dresseurs =
                            new ArrayList<PokeNpc>();
                    PokeNpcWebServiceClientAdapter dresseursAdapter =
                            new PokeNpcWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(PokeAreneWebServiceClientAdapter.JSON_DRESSEURS);
                        dresseursAdapter.extractItems(
                                json, PokeAreneWebServiceClientAdapter.JSON_DRESSEURS,
                                dresseurs);
                        pokeArene.setDresseurs(dresseurs);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(PokeAreneWebServiceClientAdapter.JSON_BADGE)
                        && !json.isNull(PokeAreneWebServiceClientAdapter.JSON_BADGE)) {

                    try {
                        PokeBadgeWebServiceClientAdapter badgeAdapter =
                                new PokeBadgeWebServiceClientAdapter(this.context);
                        PokeBadge badge =
                                new PokeBadge();

                        if (badgeAdapter.extract(
                                json.optJSONObject(
                                        PokeAreneWebServiceClientAdapter.JSON_BADGE),
                                        badge)) {
                            pokeArene.setBadge(badge);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains PokeBadge data");
                    }
                }

                if (json.has(PokeAreneWebServiceClientAdapter.JSON_ZONE)
                        && !json.isNull(PokeAreneWebServiceClientAdapter.JSON_ZONE)) {

                    try {
                        PokeZoneWebServiceClientAdapter zoneAdapter =
                                new PokeZoneWebServiceClientAdapter(this.context);
                        PokeZone zone =
                                new PokeZone();

                        if (zoneAdapter.extract(
                                json.optJSONObject(
                                        PokeAreneWebServiceClientAdapter.JSON_ZONE),
                                        zone)) {
                            pokeArene.setZone(zone);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains PokeZone data");
                    }
                }

                if (json.has(PokeAreneWebServiceClientAdapter.JSON_POSITION)
                        && !json.isNull(PokeAreneWebServiceClientAdapter.JSON_POSITION)) {

                    try {
                        PokePositionWebServiceClientAdapter positionAdapter =
                                new PokePositionWebServiceClientAdapter(this.context);
                        PokePosition position =
                                new PokePosition();

                        if (positionAdapter.extract(
                                json.optJSONObject(
                                        PokeAreneWebServiceClientAdapter.JSON_POSITION),
                                        position)) {
                            pokeArene.setPosition(position);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains PokePosition data");
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
        String id = json.optString(PokeAreneWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[6];
                if (json.has(PokeAreneWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(PokeAreneWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokeAreneWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(PokeAreneWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(PokeAreneWebServiceClientAdapter.JSON_MAITRE)) {
                    JSONObject maitreJson = json.getJSONObject(
                            PokeAreneWebServiceClientAdapter.JSON_MAITRE);
                    row[2] = maitreJson.getString(
                            PokeNpcWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokeAreneWebServiceClientAdapter.JSON_BADGE)) {
                    JSONObject badgeJson = json.getJSONObject(
                            PokeAreneWebServiceClientAdapter.JSON_BADGE);
                    row[3] = badgeJson.getString(
                            PokeBadgeWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokeAreneWebServiceClientAdapter.JSON_ZONE)) {
                    JSONObject zoneJson = json.getJSONObject(
                            PokeAreneWebServiceClientAdapter.JSON_ZONE);
                    row[4] = zoneJson.getString(
                            PokeZoneWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokeAreneWebServiceClientAdapter.JSON_POSITION)) {
                    JSONObject positionJson = json.getJSONObject(
                            PokeAreneWebServiceClientAdapter.JSON_POSITION);
                    row[5] = positionJson.getString(
                            PokePositionWebServiceClientAdapter.JSON_ID);
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
     * Convert a PokeArene to a JSONObject.
     * @param pokeArene The PokeArene to convert
     * @return The converted PokeArene
     */
    public JSONObject itemToJson(PokeArene pokeArene) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokeAreneWebServiceClientAdapter.JSON_ID,
                    pokeArene.getId());
            params.put(PokeAreneWebServiceClientAdapter.JSON_NOM,
                    pokeArene.getNom());

            if (pokeArene.getMaitre() != null) {
                PokeNpcWebServiceClientAdapter maitreAdapter =
                        new PokeNpcWebServiceClientAdapter(this.context);

                params.put(PokeAreneWebServiceClientAdapter.JSON_MAITRE,
                        maitreAdapter.itemIdToJson(pokeArene.getMaitre()));
            }

            if (pokeArene.getDresseurs() != null) {
                PokeNpcWebServiceClientAdapter dresseursAdapter =
                        new PokeNpcWebServiceClientAdapter(this.context);

                params.put(PokeAreneWebServiceClientAdapter.JSON_DRESSEURS,
                        dresseursAdapter.itemsIdToJson(pokeArene.getDresseurs()));
            }

            if (pokeArene.getBadge() != null) {
                PokeBadgeWebServiceClientAdapter badgeAdapter =
                        new PokeBadgeWebServiceClientAdapter(this.context);

                params.put(PokeAreneWebServiceClientAdapter.JSON_BADGE,
                        badgeAdapter.itemIdToJson(pokeArene.getBadge()));
            }

            if (pokeArene.getZone() != null) {
                PokeZoneWebServiceClientAdapter zoneAdapter =
                        new PokeZoneWebServiceClientAdapter(this.context);

                params.put(PokeAreneWebServiceClientAdapter.JSON_ZONE,
                        zoneAdapter.itemIdToJson(pokeArene.getZone()));
            }

            if (pokeArene.getPosition() != null) {
                PokePositionWebServiceClientAdapter positionAdapter =
                        new PokePositionWebServiceClientAdapter(this.context);

                params.put(PokeAreneWebServiceClientAdapter.JSON_POSITION,
                        positionAdapter.itemIdToJson(pokeArene.getPosition()));
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
    public JSONObject itemIdToJson(PokeArene item) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokeAreneWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a PokeArene to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(PokeAreneWebServiceClientAdapter.JSON_ID,
                    values.get(PokeAreneContract.COL_ID));
            params.put(PokeAreneWebServiceClientAdapter.JSON_NOM,
                    values.get(PokeAreneContract.COL_NOM));
            PokeNpcWebServiceClientAdapter maitreAdapter =
                    new PokeNpcWebServiceClientAdapter(this.context);

            params.put(PokeAreneWebServiceClientAdapter.JSON_MAITRE,
                    maitreAdapter.contentValuesToJson(values));
            PokeBadgeWebServiceClientAdapter badgeAdapter =
                    new PokeBadgeWebServiceClientAdapter(this.context);

            params.put(PokeAreneWebServiceClientAdapter.JSON_BADGE,
                    badgeAdapter.contentValuesToJson(values));
            PokeZoneWebServiceClientAdapter zoneAdapter =
                    new PokeZoneWebServiceClientAdapter(this.context);

            params.put(PokeAreneWebServiceClientAdapter.JSON_ZONE,
                    zoneAdapter.contentValuesToJson(values));
            PokePositionWebServiceClientAdapter positionAdapter =
                    new PokePositionWebServiceClientAdapter(this.context);

            params.put(PokeAreneWebServiceClientAdapter.JSON_POSITION,
                    positionAdapter.contentValuesToJson(values));
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
            List<PokeArene> items,
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
                PokeArene item = new PokeArene();
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
            List<PokeArene> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
