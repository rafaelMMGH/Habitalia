package com.ambarrojostudios.rafael.habitalias;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mMap;
    double lat = 0.0, lng = 0.0;
    private Marker marker,marcador1, marcador2,marcador3;


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v = inflater.inflate(R.layout.fragment_map, null, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       mMap = googleMap;
       //miUbicacion();

        LatLngBounds Tux = new LatLngBounds(new LatLng(16.657857, -93.297801), new LatLng(16.747417, -92.639064));
        mMap.setLatLngBoundsForCameraTarget(Tux);

        mMap.setMinZoomPreference(10);
        mMap.setMaxZoomPreference(20);

        LatLng latLng = new LatLng(16.754909, -93.133057);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 13);
        mMap.animateCamera(cameraUpdate);

        marcador1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.74858235215322,-93.15033596009015))
                .title("Residencial Colina Universidad")
                .draggable(false));

        marcador2 = mMap.addMarker(new MarkerOptions() // central de abasto
                .position(new LatLng(16.752877, -93.069009))
                .title("Residencial Posada")
                .draggable(false));

        marcador3 = mMap.addMarker(new MarkerOptions() // san cristobal
                .position(new LatLng(16.747296,-92.650865))
                .title("Residencial Villa Real")
                .draggable(false));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.equals(marcador1))
                {
                    Intent intent = new Intent(getContext(), Pop.class);
                    startActivity(intent);

                }else if(marker.equals(marcador2))
                {
                    Intent intent = new Intent(getContext(), Pop.class);
                    startActivity(intent);
                }else if (marker.equals(marcador3))
                {
                    Intent intent = new Intent(getContext(), Pop.class);
                    startActivity(intent);
                }

            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FavoritesFragment.OnFragmentInteractionListener) {
            //  mListener = (FavoritesFragment.OnFragmentInteractionListener) context;
        } else {
            //Acción al iniciar el Fragment
        }
    }

     public void addMarker(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marker != null) marker.remove();
        marker = mMap.addMarker(new MarkerOptions().position(coordenadas).title("Posición Actual"));
        mMap.animateCamera(miUbicacion);
    }

    public void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            addMarker(lat, lng);
        }

    }


    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

}
