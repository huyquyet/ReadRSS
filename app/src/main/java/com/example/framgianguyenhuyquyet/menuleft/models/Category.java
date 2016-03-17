package com.example.framgianguyenhuyquyet.menuleft.models;

import java.util.ArrayList;

/**
 * Created by FRAMGIA\nguyen.huy.quyet on 17/03/2016.
 */
public class Category {
    private String name;
    private ArrayList<Data> arrListPost = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Data> getArrListPost() {
        return this.arrListPost;
    }

    public void addPost(Data item) {
        this.arrListPost.add(item);
    }

    public Data getPost(int index) {
        return this.arrListPost.get(index);
    }
}
