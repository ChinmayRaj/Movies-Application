package com.example.movieapplication.Domains;

import java.io.Serializable;
import java.util.ArrayList;

public class Cast implements Serializable {
   private String PicUrl,Actor;

    public Cast() {
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getActor() {
        return Actor;
    }

    public void setActor(String actor) {
        Actor = actor;
    }
}
