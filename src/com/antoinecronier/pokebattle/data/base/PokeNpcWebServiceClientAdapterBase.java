/**************************************************************************
 * PokeNpcWebServiceClientAdapterBase.java, pokebattle Android
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
import com.antoinecronier.pokebattle.entity.PokeNpc;
import com.antoinecronier.pokebattle.data.RestClient.Verb;
import com.antoinecronier.pokebattle.provider.contract.PokeNpcContract;

import com.antoinecronier.pokebattle.entity.PokeArene;
import com.antoinecronier.pokebattle.entity.PokeDresseur;
import com.antoinecronier.pokebattle.entity.PokeObjet;
import com.antoinecronier.pokebattle.entity.PokeBadge;
import com.antoinecronier.pokebattle.entity.PokePokemon;
import com.antoinecronier.pokebattle.entity.PokePosition;
import com.antoinecronier.pokebattle.entity.PokeZone;
import com.antoinecronier.pokebattle.entity.PokeProfession;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokeNpcWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class PokeNpcWebServiceClientAdapterBase
        extends WebServiceClientAdapter<PokeNpc> {
    /** PokeNpcWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "PokeNpcWSClientAdapter";

    /** JSON Object PokeNpc pattern. */
    protected static String JSON_OBJECT_POKENPC = "PokeNpc";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_PROFESSION attributes. */
    protected static String JSON_PROFESSION = "profession";
    /** JSON_DESCRIPTION attributes. */
    protected static String JSON_DESCRIPTION = "description";
    /** JSON_OBJETS attributes. */
    protected static String JSON_OBJETS = "objets";
    /** JSON_BADGE attributes. */
    protected static String JSON_BADGE = "badge";
    /** JSON_POKEMONS attributes. */
    protected static String JSON_POKEMONS = "pokemons";
    /** JSON_TEAM attributes. */
    protected static String JSON_TEAM = "team";
    /** JSON_POSITION attributes. */
    protected static String JSON_POSITION = "position";
    /** JSON_ZONE attributes. */
    protected static String JSON_ZONE = "zone";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** PokeNpc REST Columns. */
    public static String[] REST_COLS = new String[]{
            PokeNpcContract.COL_ID,
            PokeNpcContract.COL_NOM,
            PokeNpcContract.COL_PROFESSION,
            PokeNpcContract.COL_DESCRIPTION,
            PokeNpcContract.COL_POSITION_ID,
            PokeNpcContract.COL_ZONE_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public PokeNpcWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public PokeNpcWebServiceClientAdapterBase(Context context,
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
    public PokeNpcWebServiceClientAdapterBase(Context context,
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
    public PokeNpcWebServiceClientAdapterBase(Context context,
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
    public PokeNpcWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the PokeNpcs in the given list. Uses the route : PokeNpc.
     * @param pokeNpcs : The list in which the PokeNpcs will be returned
     * @return The number of PokeNpcs returned
     */
    public int getAll(List<PokeNpc> pokeNpcs) {
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
                result = extractItems(json, "PokeNpcs", pokeNpcs);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeNpcs = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "pokenpc";
    }

    /**
     * Retrieve one PokeNpc. Uses the route : PokeNpc/%id%.
     * @param pokeNpc : The PokeNpc to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(PokeNpc pokeNpc) {
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
                if (extract(json, pokeNpc)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeNpc = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one PokeNpc. Uses the route : PokeNpc/%id%.
     * @param pokeNpc : The PokeNpc to retrieve (set the  ID)
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
     * Update a PokeNpc. Uses the route : PokeNpc/%id%.
     * @param pokeNpc : The PokeNpc to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(PokeNpc pokeNpc) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeNpc.getId(),
                        REST_FORMAT),
                    itemToJson(pokeNpc));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, pokeNpc);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a PokeNpc. Uses the route : PokeNpc/%id%.
     * @param pokeNpc : The PokeNpc to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(PokeNpc pokeNpc) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokeNpc.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the PokeNpcs associated with a PokeArene. Uses the route : pokearene/%PokeArene_id%/pokenpc.
     * @param pokeNpcs : The list in which the PokeNpcs will be returned
     * @param pokearene : The associated pokearene
     * @return The number of PokeNpcs returned
     */
    public int getByPokeArenedresseursInternal(List<PokeNpc> pokeNpcs, PokeArene pokeArene) {
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
                result = this.extractItems(json, "PokeNpcs", pokeNpcs);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeNpcs = null;
            }
        }

        return result;
    }

    /**
     * Get the PokeNpcs associated with a PokeDresseur. Uses the route : pokedresseur/%PokeDresseur_id%/pokenpc.
     * @param pokeNpcs : The list in which the PokeNpcs will be returned
     * @param pokedresseur : The associated pokedresseur
     * @return The number of PokeNpcs returned
     */
    public int getByPokeDresseurnpcsInternal(List<PokeNpc> pokeNpcs, PokeDresseur pokeDresseur) {
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
                result = this.extractItems(json, "PokeNpcs", pokeNpcs);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeNpcs = null;
            }
        }

        return result;
    }





    /**
     * Get the PokeNpcs associated with a PokePosition. Uses the route : pokeposition/%PokePosition_id%/pokenpc.
     * @param pokeNpcs : The list in which the PokeNpcs will be returned
     * @param pokeposition : The associated pokeposition
     * @return The number of PokeNpcs returned
     */
    public int getByPosition(List<PokeNpc> pokeNpcs, PokePosition pokePosition) {
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
                result = this.extractItems(json, "PokeNpcs", pokeNpcs);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeNpcs = null;
            }
        }

        return result;
    }

    /**
     * Get the PokeNpcs associated with a PokeZone. Uses the route : pokezone/%PokeZone_id%/pokenpc.
     * @param pokeNpcs : The list in which the PokeNpcs will be returned
     * @param pokezone : The associated pokezone
     * @return The number of PokeNpcs returned
     */
    public int getByZone(List<PokeNpc> pokeNpcs, PokeZone pokeZone) {
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
                result = this.extractItems(json, "PokeNpcs", pokeNpcs);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokeNpcs = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid PokeNpc Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(PokeNpcWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a PokeNpc from a JSONObject describing a PokeNpc.
     * @param json The JSONObject describing the PokeNpc
     * @param pokeNpc The returned PokeNpc
     * @return true if a PokeNpc was found. false if not
     */
    public boolean extract(JSONObject json, PokeNpc pokeNpc) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(PokeNpcWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(PokeNpcWebServiceClientAdapter.JSON_ID)) {
                    pokeNpc.setId(
                            json.getInt(PokeNpcWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(PokeNpcWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(PokeNpcWebServiceClientAdapter.JSON_NOM)) {
                    pokeNpc.setNom(
                            json.getString(PokeNpcWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(PokeNpcWebServiceClientAdapter.JSON_PROFESSION)
                        && !json.isNull(PokeNpcWebServiceClientAdapter.JSON_PROFESSION)) {
                    pokeNpc.setProfession(PokeProfession.valueOf(json.getString(
                                    PokeNpcWebServiceClientAdapter.JSON_PROFESSION)));
                }

                if (json.has(PokeNpcWebServiceClientAdapter.JSON_DESCRIPTION)
                        && !json.isNull(PokeNpcWebServiceClientAdapter.JSON_DESCRIPTION)) {
                    pokeNpc.setDescription(
                            json.getString(PokeNpcWebServiceClientAdapter.JSON_DESCRIPTION));
                }

                if (json.has(PokeNpcWebServiceClientAdapter.JSON_OBJETS)
                        && !json.isNull(PokeNpcWebServiceClientAdapter.JSON_OBJETS)) {
                    ArrayList<PokeObjet> objets =
                            new ArrayList<PokeObjet>();
                    PokeObjetWebServiceClientAdapter objetsAdapter =
                            new PokeObjetWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(PokeNpcWebServiceClientAdapter.JSON_OBJETS);
                        objetsAdapter.extractItems(
                                json, PokeNpcWebServiceClientAdapter.JSON_OBJETS,
                                objets);
                        pokeNpc.setObjets(objets);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(PokeNpcWebServiceClientAdapter.JSON_BADGE)
                        && !json.isNull(PokeNpcWebServiceClientAdapter.JSON_BADGE)) {
                    ArrayList<PokeBadge> badge =
                            new ArrayList<PokeBadge>();
                    PokeBadgeWebServiceClientAdapter badgeAdapter =
                            new PokeBadgeWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(PokeNpcWebServiceClientAdapter.JSON_BADGE);
                        badgeAdapter.extractItems(
                                json, PokeNpcWebServiceClientAdapter.JSON_BADGE,
                                badge);
                        pokeNpc.setBadge(badge);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(PokeNpcWebServiceClientAdapter.JSON_POKEMONS)
                        && !json.isNull(PokeNpcWebServiceClientAdapter.JSON_POKEMONS)) {
                    ArrayList<PokePokemon> pokemons =
                            new ArrayList<PokePokemon>();
                    PokePokemonWebServiceClientAdapter pokemonsAdapter =
                            new PokePokemonWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(PokeNpcWebServiceClientAdapter.JSON_POKEMONS);
                        pokemonsAdapter.extractItems(
                                json, PokeNpcWebServiceClientAdapter.JSON_POKEMONS,
                                pokemons);
                        pokeNpc.setPokemons(pokemons);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(PokeNpcWebServiceClientAdapter.JSON_TEAM)
                        && !json.isNull(PokeNpcWebServiceClientAdapter.JSON_TEAM)) {
                    ArrayList<PokePokemon> team =
                            new ArrayList<PokePokemon>();
                    PokePokemonWebServiceClientAdapter teamAdapter =
                            new PokePokemonWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(PokeNpcWebServiceClientAdapter.JSON_TEAM);
                        teamAdapter.extractItems(
                                json, PokeNpcWebServiceClientAdapter.JSON_TEAM,
                                team);
                        pokeNpc.setTeam(team);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(PokeNpcWebServiceClientAdapter.JSON_POSITION)
                        && !json.isNull(PokeNpcWebServiceClientAdapter.JSON_POSITION)) {

                    try {
                        PokePositionWebServiceClientAdapter positionAdapter =
                                new PokePositionWebServiceClientAdapter(this.context);
                        PokePosition position =
                                new PokePosition();

                        if (positionAdapter.extract(
                                json.optJSONObject(
                                        PokeNpcWebServiceClientAdapter.JSON_POSITION),
                                        position)) {
                            pokeNpc.setPosition(position);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains PokePosition data");
                    }
                }

                if (json.has(PokeNpcWebServiceClientAdapter.JSON_ZONE)
                        && !json.isNull(PokeNpcWebServiceClientAdapter.JSON_ZONE)) {

                    try {
                        PokeZoneWebServiceClientAdapter zoneAdapter =
                                new PokeZoneWebServiceClientAdapter(this.context);
                        PokeZone zone =
                                new PokeZone();

                        if (zoneAdapter.extract(
                                json.optJSONObject(
                                        PokeNpcWebServiceClientAdapter.JSON_ZONE),
                                        zone)) {
                            pokeNpc.setZone(zone);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains PokeZone data");
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
        String id = json.optString(PokeNpcWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[6];
                if (json.has(PokeNpcWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(PokeNpcWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokeNpcWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(PokeNpcWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(PokeNpcWebServiceClientAdapter.JSON_PROFESSION)) {
                    row[2] = json.getString(PokeNpcWebServiceClientAdapter.JSON_PROFESSION);
                }
                if (json.has(PokeNpcWebServiceClientAdapter.JSON_DESCRIPTION)) {
                    row[3] = json.getString(PokeNpcWebServiceClientAdapter.JSON_DESCRIPTION);
                }
                if (json.has(PokeNpcWebServiceClientAdapter.JSON_POSITION)) {
                    JSONObject positionJson = json.getJSONObject(
                            PokeNpcWebServiceClientAdapter.JSON_POSITION);
                    row[4] = positionJson.getString(
                            PokePositionWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokeNpcWebServiceClientAdapter.JSON_ZONE)) {
                    JSONObject zoneJson = json.getJSONObject(
                            PokeNpcWebServiceClientAdapter.JSON_ZONE);
                    row[5] = zoneJson.getString(
                            PokeZoneWebServiceClientAdapter.JSON_ID);
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
     * Convert a PokeNpc to a JSONObject.
     * @param pokeNpc The PokeNpc to convert
     * @return The converted PokeNpc
     */
    public JSONObject itemToJson(PokeNpc pokeNpc) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokeNpcWebServiceClientAdapter.JSON_ID,
                    pokeNpc.getId());
            params.put(PokeNpcWebServiceClientAdapter.JSON_NOM,
                    pokeNpc.getNom());

            if (pokeNpc.getProfession() != null) {
                params.put(PokeNpcWebServiceClientAdapter.JSON_PROFESSION,
                        pokeNpc.getProfession().name());
            }
            params.put(PokeNpcWebServiceClientAdapter.JSON_DESCRIPTION,
                    pokeNpc.getDescription());

            if (pokeNpc.getObjets() != null) {
                PokeObjetWebServiceClientAdapter objetsAdapter =
                        new PokeObjetWebServiceClientAdapter(this.context);

                params.put(PokeNpcWebServiceClientAdapter.JSON_OBJETS,
                        objetsAdapter.itemsIdToJson(pokeNpc.getObjets()));
            }

            if (pokeNpc.getBadge() != null) {
                PokeBadgeWebServiceClientAdapter badgeAdapter =
                        new PokeBadgeWebServiceClientAdapter(this.context);

                params.put(PokeNpcWebServiceClientAdapter.JSON_BADGE,
                        badgeAdapter.itemsIdToJson(pokeNpc.getBadge()));
            }

            if (pokeNpc.getPokemons() != null) {
                PokePokemonWebServiceClientAdapter pokemonsAdapter =
                        new PokePokemonWebServiceClientAdapter(this.context);

                params.put(PokeNpcWebServiceClientAdapter.JSON_POKEMONS,
                        pokemonsAdapter.itemsIdToJson(pokeNpc.getPokemons()));
            }

            if (pokeNpc.getTeam() != null) {
                PokePokemonWebServiceClientAdapter teamAdapter =
                        new PokePokemonWebServiceClientAdapter(this.context);

                params.put(PokeNpcWebServiceClientAdapter.JSON_TEAM,
                        teamAdapter.itemsIdToJson(pokeNpc.getTeam()));
            }

            if (pokeNpc.getPosition() != null) {
                PokePositionWebServiceClientAdapter positionAdapter =
                        new PokePositionWebServiceClientAdapter(this.context);

                params.put(PokeNpcWebServiceClientAdapter.JSON_POSITION,
                        positionAdapter.itemIdToJson(pokeNpc.getPosition()));
            }

            if (pokeNpc.getZone() != null) {
                PokeZoneWebServiceClientAdapter zoneAdapter =
                        new PokeZoneWebServiceClientAdapter(this.context);

                params.put(PokeNpcWebServiceClientAdapter.JSON_ZONE,
                        zoneAdapter.itemIdToJson(pokeNpc.getZone()));
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
    public JSONObject itemIdToJson(PokeNpc item) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokeNpcWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a PokeNpc to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(PokeNpcWebServiceClientAdapter.JSON_ID,
                    values.get(PokeNpcContract.COL_ID));
            params.put(PokeNpcWebServiceClientAdapter.JSON_NOM,
                    values.get(PokeNpcContract.COL_NOM));
            params.put(PokeNpcWebServiceClientAdapter.JSON_PROFESSION,
                    values.get(PokeNpcContract.COL_PROFESSION));
            params.put(PokeNpcWebServiceClientAdapter.JSON_DESCRIPTION,
                    values.get(PokeNpcContract.COL_DESCRIPTION));
            PokePositionWebServiceClientAdapter positionAdapter =
                    new PokePositionWebServiceClientAdapter(this.context);

            params.put(PokeNpcWebServiceClientAdapter.JSON_POSITION,
                    positionAdapter.contentValuesToJson(values));
            PokeZoneWebServiceClientAdapter zoneAdapter =
                    new PokeZoneWebServiceClientAdapter(this.context);

            params.put(PokeNpcWebServiceClientAdapter.JSON_ZONE,
                    zoneAdapter.contentValuesToJson(values));
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
            List<PokeNpc> items,
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
                PokeNpc item = new PokeNpc();
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
            List<PokeNpc> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
