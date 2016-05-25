/**************************************************************************
 * ResourceWebServiceClientAdapter.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.data;

import android.content.Context;

import com.antoinecronier.pokebattle.data.base.ResourceWebServiceClientAdapterBase;

/**
 * Class for all your ResourceWebServiceClient adapters.
 * You can add your own/customize your methods here.
 */
public class ResourceWebServiceClientAdapter
        extends ResourceWebServiceClientAdapterBase {

    public ResourceWebServiceClientAdapter(Context context, String host,
            Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);
    }
}

