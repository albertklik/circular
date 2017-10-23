package org.lasseufpa.circular.Domain;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by alberto on 03/04/2017.
 */

public class Parada {

    private LatLng location;

    private String title;
    private String description;
    private final int nParada;
    private boolean IsCircularHere;

    public LatLng getLocation() { return location;    }

    public void setLocation(LatLng location) {  this.location = location;    }

    public Parada (int nParada) {
        this.nParada = nParada;
    }

    public boolean isCircularHere() {
        return IsCircularHere;
    }

    public void setCircularHere(boolean circularHere) {
        IsCircularHere = circularHere;
    }

    public int getnParada() {
        return nParada;
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
