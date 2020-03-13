package mobe.m2dl.myapplication;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Layout;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This shows how to create a simple activity with a map and a marker on the map.
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,ClusterManager.OnClusterClickListener<MyItem> {

    private ClusterManager<MyItem> mClusterManager;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_demo);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we
     * just add a marker near Africa.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMapReady(GoogleMap map) {
        new FireUtils().getImages(new Consumer<ListResult>() {
            @Override
            public void accept(ListResult l) {
                Log.i("lol", l.getItems().toString());
            }
        });


        mClusterManager = new ClusterManager<>(this, map);
        map.setOnCameraIdleListener(mClusterManager);
        map.setOnMarkerClickListener(mClusterManager);

        class MarkerClusterRenderer<T extends ClusterItem> extends DefaultClusterRenderer<T> {

            public MarkerClusterRenderer(Context context, GoogleMap googleMap, ClusterManager<T> clusterManager){
                super(context, googleMap, clusterManager);
            }

            @Override
            protected boolean shouldRenderAsCluster(Cluster cluster) {
                return cluster.getSize() >= 1;
            }
        }
        mClusterManager.setOnClusterClickListener(this);

        MarkerClusterRenderer<MyItem> clusterRenderer = new MarkerClusterRenderer<>(this, map, mClusterManager);
        mClusterManager.setRenderer(clusterRenderer);
        this.map = map;
        new FireUtils().getImages(new Consumer<ListResult>() {
            @Override
            public void accept(ListResult l) {
                start(l);
            }
        });
    }


     final void start(ListResult listResult) {

        for (final StorageReference s : listResult.getItems() ){
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.604500, 1.444000), listResult.getItems().size()));
            map.animateCamera( CameraUpdateFactory.zoomTo( 12.0f ) );
            s.getMetadata().addOnCompleteListener(new OnCompleteListener<StorageMetadata>() {
                @Override
                public void onComplete(@NonNull Task<StorageMetadata> task) {
                    StorageMetadata storageMetadata = task.getResult();
                    double lat = Double.parseDouble(storageMetadata.getCustomMetadata("lat"));
                    double lng = Double.parseDouble(storageMetadata.getCustomMetadata("long"));
                    mClusterManager.addItem(new MyItem(lat,lng, s));
                }
            });
        }
    }

    @Override
    public boolean onClusterClick(Cluster<MyItem> cluster) {
        List<String> list = new ArrayList<>();
        for ( MyItem item : cluster.getItems()) {
            list.add(item.ref.getPath());
           // Log.i("ref",item.ref.getPath());
        }
        Intent i  = new Intent(this,ImagesActivity.class);
        i.putExtra("list", (Serializable) list);
        MapsActivity.this.startActivity(i);
        return true;
    }
}