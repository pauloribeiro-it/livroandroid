package hellomaterial.paulo.ribeiro.it.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.google.android.gms.maps.model.MarkerOptions;

import hellomaterial.paulo.ribeiro.it.R;
import hellomaterial.paulo.ribeiro.it.domain.Carro;

public class MapaFragment extends BaseFragment implements OnMapReadyCallback {

    private GoogleMap map;
    private Carro carro;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa,container,false);
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        this.carro = getArguments().getParcelable("carro");
        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        if(carro!=null){
            map.setMyLocationEnabled(true);
            LatLng location = new LatLng(Double.parseDouble(carro.latitude),Double.parseDouble(carro.longitude));
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(location,13);
            map.moveCamera(update);
            map.addMarker(new MarkerOptions().title(carro.nome).snippet(carro.desc).position(location));
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }
}
