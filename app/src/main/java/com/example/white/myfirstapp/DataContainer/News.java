package com.example.white.myfirstapp.DataContainer;

import java.util.Date;

/**
 * Created by White on 09.01.2015.
 */
public class News {
    public final int companyID;       // the drawable for the ListView item ImageView
    public final String type;        // the text for the ListView item title
    public final String description;  // the text for the ListView item description
    public final Date start;
    public final Date end;

    public News(int companyID, String type, String description, Date start, Date end) {
        this.companyID = companyID;
        this.type = type;
        this.description = description;
        this.start = start;
        this.end = end;
    }
}
