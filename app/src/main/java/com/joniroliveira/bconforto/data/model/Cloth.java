package com.joniroliveira.bconforto.data.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Cloth extends RealmObject {
    @PrimaryKey
    public String id = UUID.randomUUID().toString();
    private String name;
    private float price;

    public Cloth() {
    }

    public Cloth(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
