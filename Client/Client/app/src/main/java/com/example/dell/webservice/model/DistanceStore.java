package com.example.dell.webservice.model;

import android.support.annotation.NonNull;

public class DistanceStore implements Comparable {
    private Store store;
    private double distance;

    public DistanceStore() {
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


    @Override
    public int compareTo(@NonNull Object o) {
        if (o instanceof DistanceStore)
        {
            DistanceStore d= (DistanceStore) o;
            if ( d.distance-this.distance>0) return -1;
            if ( d.distance-this.distance<0) return 1;
            return 0;
        }
        return 0;
    }

    @Override
    public String toString() {
        return
                ", distance=" + distance +
                '}';
    }
}
