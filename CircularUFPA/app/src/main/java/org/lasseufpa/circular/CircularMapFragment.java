package org.lasseufpa.circular;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.lasseufpa.circular.Domain.Circular;
import org.lasseufpa.circular.Domain.Parada;
import org.lasseufpa.circular.Domain.RouteCircular1;

import java.util.ArrayList;

public class CircularMapFragment extends Fragment implements OnMapReadyCallback,
        RepositorioCircular.RepositorioCircularChangeListener,
        RepositorioParadas.OnRepositorioParadasChangeListener, RepositorioRotas.OnRepositorioRotasChangeListener {

    //TASKS
    // - atualizar pontos de parada
    // - atualizar circulares
    // - traçar rota

    //Objeto Google Map
    private GoogleMap mMap;

    //mapview
    private MapView map;

    //repositorio circulares
    private RepositorioCircular repositorioCirculares = MainActivity.repositorioCirculares;

    //repositorio paradas
    private RepositorioParadas repositorioParadas = MainActivity.repositorioParadas;

    //repositoriorotas
    private RepositorioRotas repositorioRotas = MainActivity.repositorioRotas;

    //lista de circulares
    private ArrayList<Circular> circulares;

    //lista de paradas
    private ArrayList<Parada> paradas;

    //lista de marcadores Circulares
    private ArrayList<Marker> circularesMarkers;

    //lista de marcadores Paradas
    ArrayList<Marker> paradasMarkers;

    //rota do circular
    Polyline rotacircularIda;
    Polyline rotacircularVolta;

    //contexto
    Context contexto;
    private boolean saved;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        contexto = context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        circulares = repositorioCirculares.getCircularList();
        paradas = repositorioParadas.getParadasList();
        circularesMarkers = new ArrayList<>();
        paradasMarkers = new ArrayList<>();

        if (savedInstanceState!=null) {
            saved = savedInstanceState.getBoolean("saved");
        }




    }

    @Override
    public void onStart() {
        super.onStart();
        repositorioCirculares.setRepositorioCircularChangeListener(this);
        repositorioParadas.setOnRepositorioParadasChangeListener(this);
        repositorioRotas.setOnRepositorioParadasChangeListener(this);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();

        Log.i("circularMapFragment","Onresume chamado");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        map.onDestroy();
    }

    @Override
    public void onPause() {

        super.onPause();
        map.onPause();
        Log.i("circularMapFragment","Onspause chamado");

        //salav estado do mapa
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map.onLowMemory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps,null);

        // obtém o SupportMapFragment e recebe uma notificação caso o mapa esteja pronto paras ser utilizado
        map = (MapView) view.findViewById(R.id.map);
        map.onCreate(savedInstanceState);
        map.onResume();


        map.getMapAsync(this);

        Log.i("circularMapFragment","OncreateView chamado");


        return view;
    }


    private void cleanCircularMarkers() {

        //retirar todos os marcadores circular do mapa
        for (Marker m : circularesMarkers) {
            m.remove();
        }
        circularesMarkers.clear();
        //limpar lista de marcadores circulares


    }

    private void cleanParadaMarkers() {

        //retirar todos os marcadores circular do mapa
        for (Marker m : paradasMarkers) {
            m.remove();
        }
        paradasMarkers.clear();
        //limpar lista de marcadores circulares
    }

    public void setupRouteCircular () {
        new SetupRouteVolta().execute();
        new SetupRouteIda().execute();

    }



    @Override
    public void onStop() {
        super.onStop();
        repositorioCirculares.removeRepositorioCircularChangeListener(this);
        repositorioParadas.removeOnRepositorioParadasChangeListener(this);
        repositorioRotas.removeOnRepositorioParadasChangeListener(this);
        map.onStop();
        cleanCircularMarkers();
        Log.i("circularMapFragment","Onstop chamado");
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {



        mMap = googleMap;


        if (saved) {

        } else {
            LatLng place = new LatLng(-1.472465599146933,-48.45721595321921);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
            // Zoom in, animating the camera.
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 1000, null);
        }



        //salvar estado do mapa

        //atualiza posição dos circulares no mapa
        updateCircularPosition();

        //coloca as paradas no mapa
        updateParadas();

        //desenha rota
        setupRouteCircular();

        Log.i("circularMapFragment","OnmapReady chamado");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }






    /**
     * updateCircularPosition()
     * Atualiza os pontos do circular no mapa
     *
     */

    private void updateCircularPosition(){
        Log.i("circularMapFragment","UpdateCircularPosition chamado");

        ArrayList<Marker> toRemoveList = new ArrayList<>();
        boolean found = false;

        //atualizando marcadores existentes (atualiza existentes e remove inexistentes)
        for (Marker m : circularesMarkers) {

            for (Circular currentC : circulares) {

                if (m.getTitle().equals(currentC.getNome())) {
                    m.setPosition(currentC.getPosition());
                    //verifica se ele não esta antigo
                    if (currentC.isObsolet()) {
                        m.setIcon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("circpind", 100, 100)));
                    }
                    found = true;
                    break;
                }
            }

            if (!found) {
                toRemoveList.add(m);
            } else {
                found = false;
            }
        }

        //para cada marcador em toremovelist remover do mapa e da lista
        for (Marker m : toRemoveList) {
            m.remove();
            circularesMarkers.remove(m);
        }

        //adicionando novos
        for (Circular currentC : circulares) {

            for (Marker m : circularesMarkers) {

                if (m.getTitle().equals(currentC.getNome())) {
                    found = true;
                    break;
                }

            }

            if (!found) {
                addCircular(currentC);
            } else {
                found = false;
            }

        }

    }

    /* old
    private void updateCircularPosition(){

        Log.i("circularMapFragment","UpdateCircularPosition chamado");

        for (Marker m : circularesMarkers) {
            m.remove();
        }
        circularesMarkers.clear();
        //repete para cada circular na lista
        for (Circular currentC : circulares) {
            addCircular(currentC);
        }
    } */


    //redimenciona os icones do mapa para tamanhos personalizados
    public Bitmap resizeMapIcons(String iconName,int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getActivity().getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }


    private void addCircular(Circular C) {

    Log.i("circularMapFragment","addCircular chamado");
    //cria um novo marcador circular e adiciona no mapa
    Marker circular = mMap.addMarker(new MarkerOptions()
                    .position(C.getPosition())
                    .title(C.getNome())
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("circpin", 100, 100)))
            );

     circularesMarkers.add(circulares.indexOf(C),circular);

    }

    private void traceRoute(ArrayList<LatLng> pontos) {
        Log.i("rota","a rota foi adicionada ao mapa");
        mMap.addPolyline(new PolylineOptions().addAll(pontos).width(5).color(Color.rgb(255,153,153)));
    }

    private void updateParadas() {

        //remove todas as paradas
        removeParadasMarkers();
        //repete para cada parada na lista
        new UpdateParadas().execute();

    }

    private void removeParadasMarkers() {
        for (Marker m : paradasMarkers) {
            m.remove();
        }
        paradasMarkers.clear();
    }




    @Override
    public void onRepositorioCircularChanged() {
        Log.i("circularMapFragment","OrepositorioCircularChanged chamado");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

               updateCircularPosition();

            }
        });
    }

    public void circularMArkerFocus(final String CircularName, Activity context) {

    }

    @Override
    public void OnRepositorioParadasChanged() {
        Log.i("circularMapFragment","OrepositorioCircularChanged chamado");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                updateParadas();

            }
        });
    }

    @Override
    public void OnRepositorioRotasChanged() {
        if (repositorioRotas.isrota1Ativa()) {
            rotacircularIda.setVisible(true);
        } else {
            rotacircularIda.setVisible(false);
        }

        if (repositorioRotas.isrota2Ativa()) {
            rotacircularVolta.setVisible(true);
        } else {
            rotacircularVolta.setVisible(false);
        }
    }


    private class UpdateParadas extends AsyncTask<Void,MarkerOptions,Void> {


        @Override
        protected Void doInBackground(Void... voids) {


            if (repositorioParadas.isActive()) {
                MarkerOptions markerOption;
                BitmapDescriptor icone = BitmapDescriptorFactory.fromBitmap(resizeMapIcons("stoppin", 70, 70));
                for (Parada p : paradas) {
                    markerOption = (new MarkerOptions()
                            .position(p.getLocation())
                            .title(p.getTitle())
                            .icon(icone))
                            .snippet(p.getDescription());

                    publishProgress(markerOption);
                }
            } else {
                cleanParadaMarkers();
            }

            return null;
        }


        @Override
        protected void onProgressUpdate(MarkerOptions... values) {
            super.onProgressUpdate(values);
            paradasMarkers.add(mMap.addMarker(values[0]));
        }
    }



    private class SetupRouteIda extends AsyncTask<Void,Void,PolylineOptions> {


        @Override
        protected PolylineOptions doInBackground(Void... voids) {

            ArrayList<LatLng> pontos= new ArrayList<>();

            for (int i = 0; i< RouteCircular1.NROUTERETURN; i++) {
                pontos.add(new LatLng(RouteCircular1.POINTS[i][1], RouteCircular1.POINTS[i][0]));
            }
            //pontos.add(new LatLng(RouteCircular1.POINTS[0][1], RouteCircular1.POINTS[0][0]));

            return new PolylineOptions().addAll(pontos).width(5).color(Color.rgb(255,153,153));
        }

        @Override
        protected void onPostExecute(PolylineOptions rota) {
            super.onPostExecute(rota);
            rotacircularIda = mMap.addPolyline(rota);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (map.isShown()) {
            map.onSaveInstanceState(outState);
        }
        outState.putBoolean("saved",true);
    }

    private class SetupRouteVolta extends AsyncTask<Void,Void,PolylineOptions> {


        @Override
        protected PolylineOptions doInBackground(Void... voids) {

            ArrayList<LatLng> pontos= new ArrayList<>();

            for (int i = RouteCircular1.NROUTERETURN-1; i< RouteCircular1.POINTS.length; i++) {
                pontos.add(new LatLng(RouteCircular1.POINTS[i][1], RouteCircular1.POINTS[i][0]));
            }

            pontos.add(new LatLng(RouteCircular1.POINTS[0][1], RouteCircular1.POINTS[0][0]));
            return new PolylineOptions().addAll(pontos).width(10).color(Color.rgb(38,121,167));
        }

        @Override
        protected void onPostExecute(PolylineOptions rota) {
            super.onPostExecute(rota);
            rotacircularVolta = mMap.addPolyline(rota);


        }


    }






}
