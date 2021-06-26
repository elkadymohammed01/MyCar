package com.great.mycar;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.great.mycar.adapter.myDbAdapter;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(10f);
        mMap.stopAnimation();

        LatLng sydney = new LatLng(31, 30);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        getPoint();
        onClickMap();
    }
    void getPoint(){

        FirebaseDatabase.getInstance().getReference()
                .child("Maps").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(dataSnapshot
                                    .child("latitude").getValue().toString()),
                                    Double.parseDouble(dataSnapshot
                                    .child("longitude").getValue().toString())))
                            .title("Marker in Sydney"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void onClickMap(){
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {
                onMapClick(latLng);
            }

            public void onMapClick(@NonNull LatLng latLng) {
                myDbAdapter Db = new myDbAdapter(getApplicationContext());

                if(Db.getData_inf()[1].equals("Car@MyCar.com")){
                FirebaseDatabase.getInstance().getReference()
                        .child("Maps").push().setValue(latLng);
                    Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
