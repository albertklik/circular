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
    private boolean mostrarMapa;
    private int sentido_rota;
    private int tipo;

    public boolean isMostrarMapa() {
        return mostrarMapa;
    }

    public void setMostrarMapa(boolean mostrarMapa) {
        this.mostrarMapa = mostrarMapa;
    }

    public int getSentido_rota() {
        return sentido_rota;
    }

    public void setSentido_rota(int sentido_rota) {
        this.sentido_rota = sentido_rota;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

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
