package com.example.melek.getitfree;

import android.os.Bundle;

import java.io.Serializable;

public class post implements Serializable {


    String label;
    String description;
    String tel;
    String period_name;
    String url;
    String key;
    String user;



    public post(String label, String description, String tel, String period_name, String url, String key, String user) {
        this.label = label;
        this.description = description;
        this.tel = tel;
        this.period_name= period_name;
        this.url=url;
        this.key= key;
        this.user=user;
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

    public String getTel() {return tel;}
    public void setTel(String tel) {this.tel = tel; }

    public String getPeriod_name() {return period_name;}
    public void setPeriod_name(String period_name) {this.period_name = period_name; }

    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url; }

    public String getKeyy() {return key;}
    public void setKeyy(String key) {this.key = key; }

    public String getUser() {return user;}
    public void setUser(String user) {this.user = user; }
}
