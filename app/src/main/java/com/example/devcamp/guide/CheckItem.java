package com.example.devcamp.guide;

/**
 * Created by ridickle on 2017. 3. 28..
 */

public class CheckItem {
    String title;
    String description;

    public CheckItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
