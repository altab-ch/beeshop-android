package com.example.white.myfirstapp.DataContainer;

import org.json.JSONObject;

/**
 * Created by White on 15.01.2015.
 */
public class Company {
    public final int companyID;
    public final String name;
    public final String type;
    public final String description;
    public final int latitude;
    public final int longitude;
    public final JSONObject meta;

    public Company(int companyID, String name, String type, String description, int latitude, int longitude, JSONObject meta) {
        this.companyID = companyID;
        this.name = name;
        this.type = type;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.meta = meta;
    }
}
