package com.example.melek.getitfree;

import android.widget.ImageView;
import java.io.Serializable;
import android.widget.ImageView;

public class item_actuality implements Serializable {

    String label;
    String description;


    public item_actuality(String label, String description) {
        this.label = label;
        this.description = description;

    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    }






