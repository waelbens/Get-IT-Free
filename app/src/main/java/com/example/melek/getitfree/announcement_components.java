package com.example.melek.getitfree;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties

public class announcement_components {
    public String label;
    public String description;
    public String tel;
    public String name;
    public String url;
    public String user;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public announcement_components() {
    }

    public announcement_components(String label, String description, String tel, String name, String url, String user) {
        this.label = label;
        this.description = description;
        this.tel = tel;
        this.name=name;
        this.url=url;
        this.user= user;
    }

    public announcement_components(String url) {
        this.url=url;
    }

}

