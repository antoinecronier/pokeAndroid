/**************************************************************************
 * RestResource.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/
package com.antoinecronier.pokebattle.entity.base;

import com.antoinecronier.pokebattle.entity.base.Resource;

import java.io.Serializable;

import org.joda.time.DateTime;

public interface RestResource extends Resource {
    /**
     * @return the local path
     */
    String getLocalPath();

    /**
     * @param value the local path to set
     */
    void setLocalPath(final String value);
}