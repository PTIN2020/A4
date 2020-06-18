package terminal1.a4.loginui;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import terminal1.a4.listanegocios.ListaNegocios;
import terminal1.a4.listanegocios.Perfil;
import terminal1.a4.tarjeta_embarque.Tembarque;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.Timer;
import java.util.TimerTask;


public class mapa extends AppCompatActivity {
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //handle permissions first, before map is created. not depicted here

        //load/initialize the osmdroid configuration, this can be done
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's
        //tile servers will get you banned based on this string

        //inflate and create the map

        setContentView(R.layout.activity_mapa);

        map = (MapView) findViewById(R.id.map);
        map.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        map.setTileSource(TileSourceFactory.MAPNIK);
        requestPermissionsIfNecessary(new String[] {
                // if you need to show the current location, uncomment the line below
                // Manifest.permission.ACCESS_FINE_LOCATION,
                // WRITE_EXTERNAL_STORAGE is required in order to show the map
                Manifest.permission.WRITE_EXTERNAL_STORAGE

        });
        map.setMultiTouchControls(true);

        //pasajero
        mQueue = Volley.newRequestQueue(this);


        //periodic call
        class UpdatePos extends TimerTask {
            public void run() {
                //jsonGet();
                map.getOverlayManager().clear();
                map.invalidate();
                Points_map();
                jsonGet();
                jsonGetCoche();
                // Change the overlay
            }
        }

        Timer timer = new Timer();
        timer.schedule(new UpdatePos(), 0, 4000);

        // geoloc palma
        GeoPoint StartPoint = new GeoPoint(39.54827, 2.73418);

        IMapController mapController = map.getController();
        //zoom aprox cerca
        mapController.setZoom(18.00);
        mapController.setCenter(StartPoint);

        Points_map ();
        /*Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(3000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                map.getOverlayManager().clear();
                                map.invalidate();
                                Points_map();
                                jsonGet();
                                //SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                                //String ubi = preferences.getString("last_pos","");
                                //String[] s = ubi.split(",");
                                //GeoPoint ubiPoint = new GeoPoint(Double.parseDouble(s[0]), Double.parseDouble(s[1]));
                                //add_marker_pospasajero(ubiPoint);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();*/
        map.invalidate();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_vuelos:
                        Intent intent1 = new Intent(mapa.this, Tembarque.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_mapa:
                        break;
                    case R.id.ic_servicios:
                        Intent intent2 = new Intent(mapa.this, servicios.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_tiendas:
                        Intent intent3 = new Intent(mapa.this, ListaNegocios.class);
                        startActivity(intent3);
                        break;
                    case R.id.ic_perfil:
                        Intent intent4 = new Intent(mapa.this, Perfil.class);
                        startActivity(intent4);
                        break;
                }
                return true;
            }
        });
    }

    private void Points_map (){

        //supermercados
        GeoPoint cartPoint = new GeoPoint(39.55032, 2.73625);
        GeoPoint cart2Point = new GeoPoint(39.546894,2.733270);
        GeoPoint cart3Point = new GeoPoint(39.54994, 2.72821);

        //restaurantes
        GeoPoint restaurantPoint= new GeoPoint(39.55069, 2.73);
        GeoPoint restaurant2Point = new GeoPoint(39.5487, 2.73388);
        GeoPoint restaurant3Point = new GeoPoint(39.5498, 2.7356);

        //info
        GeoPoint infoPoint= new GeoPoint(39.54859, 2.73179);
        GeoPoint info2Point = new GeoPoint(39.54949, 2.73463);
        GeoPoint info3Point = new GeoPoint(39.55023, 2.72913);

        //vip
        GeoPoint vipPoint= new GeoPoint(39.54508, 2.73123);
        GeoPoint vip2Point = new GeoPoint(39.54528, 2.73174);
        GeoPoint vip3Point = new GeoPoint(39.54578, 2.73274);
        GeoPoint vip4Point = new GeoPoint(39.54606, 2.73327);

        add_marker_cart(cartPoint, "Duty Free");
        add_marker_cart(cart2Point, "Vilanova Souvenirs");
        add_marker_cart(cart3Point, "Adidas");

        //restuarants
        add_marker_restaruant(restaurantPoint, "Tagliatella");
        add_marker_restaruant(restaurant2Point, "Restaurant Pepin");
        add_marker_restaruant(restaurant3Point, "Mc Donald's");

        //info
        add_marker_info(infoPoint, "Punt d'informació P");
        add_marker_info(info2Point, "Punt d'informació C");
        add_marker_info(info3Point, "Punt d'informació A");

        //vip
        add_marker_vip(vipPoint, "Sala VIP Clàssica");
        add_marker_vip(vip2Point, "Sala VIP Tropical");
        add_marker_vip(vip3Point, "Sala VIP Mediterrània");
        add_marker_vip(vip4Point, "Sala VIP Clàssica");

    }

    private void jsonGet(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("username","");
        String url = "http://craaxcloud.epsevg.upc.edu:36302/nodos/random/position/";
        url += user;
        //System.out.println(url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            double longitud = Double.parseDouble(response.getString("longitud"));
                            double latitud =  Double.parseDouble(response.getString("latitud"));
                            GeoPoint ubi = new GeoPoint(latitud, longitud);
                            add_marker_pospasajero(ubi, "Mi posición actual");
                            //SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                            //SharedPreferences.Editor editor = preferences.edit();
                            //editor.putString("last_pos", response.getString("longitud")+","+response.getString("latitud"));
                            //editor.apply();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //mTextViewResult.setText(error.getMessage());
            }
        });
        mQueue.add(request);
    }


    private void jsonGetCoche(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("username","");
        String url = "http://craaxcloud.epsevg.upc.edu:36302/cochesuser/";
        url += user;
        //System.out.println(url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String node = null;
                            node = response.getString("puntActual");
                            if (!node.equals("")) jsonGetResolvNode(node);
                            //jsonGetResolvNode(node);
                            //GeoPoint ubi_coche = new GeoPoint(latitud, longitud);
                            //add_marker_pospasajero(ubi_coche);
                            //jsonGet();
                            //add_marker_poscoche(ubi_coche);
                            //SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                            //SharedPreferences.Editor editor = preferences.edit();
                            //editor.putString("last_pos", response.getString("longitud")+","+response.getString("latitud"));
                            //editor.apply();

                        } catch (JSONException e) {
                            //System.out.println("No hay coche");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //mTextViewResult.setText(error.getMessage());
            }
        });
        mQueue.add(request);
    }

    private void jsonGetResolvNode(String node){
        //SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        //String user = preferences.getString("username","");
        String url = "http://craaxcloud.epsevg.upc.edu:36302/nodos/";
        url += node;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //jsonGet();
                            double latitud = Double.parseDouble(response.getString("longitud"));
                            double longitud =  Double.parseDouble(response.getString("latitud"));
                            //String longitud = response.getString("longitud");
                            //String latitud =  response.getString("latitud");
                            //add_marker_poscoche(point_test, latitud+","+longitud);
                            GeoPoint ubi_coche = new GeoPoint(latitud, longitud);
                            //add_marker_pospasajero(ubi_coche);
                            add_marker_poscoche(ubi_coche,"Mi coche");
                            //SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                            //SharedPreferences.Editor editor = preferences.edit();
                            //editor.putString("last_pos", response.getString("longitud")+","+response.getString("latitud"));
                            //editor.apply();

                        } catch (JSONException e) {
                            System.out.println("No hay coche");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //mTextViewResult.setText(error.getMessage());
            }
        });
        mQueue.add(request);
    }
/*    void add_points_map(){
        //your items
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        items.add(new OverlayItem("Point", "Aqui estoy yo", new GeoPoint(39.549627,2.734484))); // Lat/Lon decimal degrees
//the overlay
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        //do something
                        return true;
                    }
                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                },Context);
        mOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(mOverlay);
    }*/


    void add_marker_pospasajero(GeoPoint Point, String info){
        Marker Marker = new Marker(map);
        Marker.setPosition(Point);
        //startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        Marker.setIcon(getResources().getDrawable(R.drawable.user));
        Marker.setTitle(info);
        map.getOverlays().add(Marker);
    }

    void add_marker_poscoche(GeoPoint Point, String info){
        Marker Marker = new Marker(map);
        Marker.setPosition(Point);
        //startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        Marker.setIcon(getResources().getDrawable(R.drawable.car1));
        Marker.setTitle(info);
        map.getOverlays().add(Marker);
    }

    void add_marker_cart(GeoPoint Point, String info){
        Marker Marker = new Marker(map);
        Marker.setPosition(Point);
        //startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        Marker.setIcon(getResources().getDrawable(R.drawable.cart));
        Marker.setTitle(info);

        map.getOverlays().add(Marker);
    }

    void add_marker_restaruant(GeoPoint Point, String info){
        Marker Marker = new Marker(map);
        Marker.setPosition(Point);
        //startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        Marker.setIcon(getResources().getDrawable(R.drawable.restaurant));
        Marker.setTitle(info);

        map.getOverlays().add(Marker);
    }

    void add_marker_info(GeoPoint Point, String info){
        Marker Marker = new Marker(map);
        Marker.setPosition(Point);
        //startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        Marker.setIcon(getResources().getDrawable(R.drawable.info));
        Marker.setTitle(info);

        map.getOverlays().add(Marker);
    }

    void add_marker_vip(GeoPoint Point, String info){
        Marker Marker = new Marker(map);
        Marker.setPosition(Point);
        //startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        Marker.setIcon(getResources().getDrawable(R.drawable.vip));
        Marker.setTitle(info);

        map.getOverlays().add(Marker);
    }

    @Override
    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
}