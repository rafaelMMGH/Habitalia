package com.example.rafael.ars_cons;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

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
    private Marker marker,marcador1, marcador2, marcador3,marcador4,marcador5;


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

        LatLngBounds Tux = new LatLngBounds(new LatLng(16.657857, -93.297801), new LatLng(16.820600, -92.965465));
        mMap.setLatLngBoundsForCameraTarget(Tux);

        mMap.setMinZoomPreference(12.3f);
        mMap.setMaxZoomPreference(16);

        LatLng latLng = new LatLng(16.754909, -93.133057);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 13);
        mMap.animateCamera(cameraUpdate);

        marcador1 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.746493, -93.129060))
                .title("Casa en Renta")
                .snippet("Renta\n2 planta(s) \n5 habitaciones \n2 baños \n$3000")
                .draggable(false));




        marcador2 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.750295, -93.158452))
                .title("Departamento en Venta")
                .snippet("Renta\n1 planta(s) \n3 habitacion(es) \n1 baño(s) \n$2000")
                .draggable(false));


        marcador3 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.764813, -93.153995))
                .title("Casa en Venta")
                .snippet("Venta\n2 plantas \n8 habitaciones \n4 baños \n$500000")
                .draggable(false));


        marcador4 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.765307, -93.129024))
                .title("Departamento en Renta")
                .snippet("Renta\n3 plantas \n10 habitaciones \n5 baños \n$15000")
                .draggable(false));


        marcador5 = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(16.776280, -93.131438))
                .title("Casa en Venta")
                .snippet("Venta\n2 plantas \n2 habitaciones \n2 baños \n$6000")
                .draggable(false));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.equals(marcador1))
                {
                    Intent intent = new Intent(getContext(), Pop.class);
                    startActivity(intent);

                }
            }
        });

    }




   /* public boolean onMarkerClick(final Marker marker) {

        if (marker.equals(marcador1))
        {
            Intent intent = new Intent(getContext(), Pop.class);
            startActivity(intent);

        }
        return  true;
    } */

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

    /*private void miUbicacion() {


        LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000,5,locationListener);
    } */
}
