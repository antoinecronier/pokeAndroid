/**************************************************************************
 * EntityResourceBase.java, pokebattle Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : May 25, 2016
 *
 **************************************************************************/

package com.antoinecronier.pokebattle.entity.base;

import com.antoinecronier.pokebattle.entity.base.RestResource;

public class EntityResourceBase implements  RestResource {

    protected String path;

    private int id;

    private String localPath;

    @Override
    public int getId() {
         return this.id;
    }

    @Override
    public void setId(final int value) {
         this.id = value;
    }

    @Override
    public String getLocalPath() {
         return this.localPath;
    }

    @Override
    public void setLocalPath(final String value) {
         this.localPath = value;
    }

    @Override
    public String getPath() {
         return this.path;
    }

    @Override
    public void setPath(final String value) {
         this.path = value;
    }

}