package org.lasseufpa.circular;

import java.util.ArrayList;

/**
 * Created by alberto on 26/11/2017.
 */

public class RepositorioRotas {

    private ArrayList<OnRepositorioRotasChangeListener> listeners;

    private boolean rota1Ativa;
    private boolean rota2Ativa;

    public RepositorioRotas() {



        listeners = new ArrayList<>();

    }


    public void setRota1Ativa(boolean b) {
        if (rota1Ativa!=b) {
            rota1Ativa = b;
            notifyChange();
        }
    }

    public boolean isrota1Ativa () {
        return rota1Ativa;
    }

    public void setRota2Ativa(boolean b) {
        if (rota2Ativa!=b) {
            rota2Ativa = b;
            notifyChange();
        }
    }

    public boolean isrota2Ativa () {
        return rota2Ativa;
    }

    public void setOnRepositorioParadasChangeListener(OnRepositorioRotasChangeListener listener) {
        listeners.add(listener);
    }

    public void removeOnRepositorioParadasChangeListener(OnRepositorioRotasChangeListener listener) {
        listeners.remove(listener);
    }


    public void notifyChange() {
        for (OnRepositorioRotasChangeListener listener:
                listeners) {
            listener.OnRepositorioRotasChanged();
        }
    }



    public interface OnRepositorioRotasChangeListener {

        public void OnRepositorioRotasChanged();
    }



}
