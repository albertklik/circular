package org.lasseufpa.circular;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.lasseufpa.circular.Domain.Parada;
import org.lasseufpa.circular.Domain.ParadasList;

import java.util.ArrayList;

/**
 * Created by alberto on 03/09/2017.
 */

public class RepositorioParadas {

    private ArrayList<Parada> paradas;
    private boolean active = true;
    private ArrayList<OnRepositorioParadasChangeListener> listeners;


    public void setParadasActive(boolean b) {
        if (active!=b) {
            active = b;
            notifyChange();
        }
    }

    public boolean isActive () {
        return active;
    }


    public RepositorioParadas() {


        paradas = new ArrayList<>();
        listeners = new ArrayList<>();
        seupParadasList();
    }

    public void salvaParada(Parada p) {

        for (Parada currentP : paradas)
            if (currentP.getnParada()==p.getnParada()) {
                currentP.setDescription(p.getDescription());
                currentP.setCircularHere(p.isCircularHere());
                currentP.setTitle(p.getTitle());
                currentP.setLocation(p.getLocation());
                return;
            }

        paradas.add(p.getnParada(),p);

        }


    public ArrayList<Parada> getParadasList() {
        return paradas; }


    public Parada getParada(int nParada) {
        return paradas.get(nParada);
    }


    public void seupParadasList() {

        new SetupParadasListTask().execute();


    }

    public void setOnRepositorioParadasChangeListener(OnRepositorioParadasChangeListener listener) {
        listeners.add(listener);
    }

    public void removeOnRepositorioParadasChangeListener(OnRepositorioParadasChangeListener listener) {
        listeners.remove(listener);
    }


    public void notifyChange() {
        for (OnRepositorioParadasChangeListener listener:
             listeners) {
            listener.OnRepositorioParadasChanged();
        }
    }


    public interface OnRepositorioParadasChangeListener {

         void OnRepositorioParadasChanged();
    }

    private class SetupParadasListTask extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... voids) {
         try {
             Parada currentP;
             for (int i = 0; i < ParadasList.POINTS.length; i++) {
                 currentP = new Parada(i);
                 currentP.setTitle(ParadasList.NAME_DESCRIPTION[i][0]);
                 currentP.setDescription(ParadasList.NAME_DESCRIPTION[i][1]);
                 currentP.setLocation(new LatLng(ParadasList.POINTS[i][ParadasList.INDEX_POINTS_LATITUDE], ParadasList.POINTS[i][ParadasList.INDEX_POINTS_LONGITUDE]));
                 currentP.setMostrarMapa(((int) ParadasList.POINTS[i][ParadasList.INDEX_POINTS_INSERIR_MAPA]) == 1);
                 currentP.setSentido_rota((int) ParadasList.POINTS[i][ParadasList.INDEX_SENTIDO_ROTA]);
                 currentP.setTipo((int) ParadasList.POINTS[i][ParadasList.INDEX_POINTS_TIPO]);

                 currentP.setCircularHere(false);
                 paradas.add(i, currentP);
                 Log.i("log", "adicionado" + currentP.getDescription());
             }
         }catch (Exception e ) {
             e.printStackTrace();
         }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            notifyChange();
        }
    }


}
