package terminal1.a4.listanegocios;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import terminal1.a4.loginui.R;
import terminal1.a4.loginui.mapa;
import terminal1.a4.loginui.servicios;
import terminal1.a4.tarjeta_embarque.Tembarque;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class ListaNegocios extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewBussinesAdapter adapter;
    private ArrayList<bussines_card> data_list;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_negocios);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_negocios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        data_list = new ArrayList<>();

        mQueue = Volley.newRequestQueue(this);

        load_data_from_server();






        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_vuelos:
                        Intent intent3 = new Intent(ListaNegocios.this, Tembarque.class);
                        startActivity(intent3);
                        break;
                    case R.id.ic_mapa:
                        Intent intent1 = new Intent(ListaNegocios.this, mapa.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_servicios:
                        Intent intent2 = new Intent(ListaNegocios.this, servicios.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_tiendas:
                        break;
                    case R.id.ic_perfil:
                        Intent intent4 = new Intent(ListaNegocios.this, Perfil.class);
                        startActivity(intent4);
                        break;
                }
                return true;
            }
        });
    }
    private void load_data_from_server() {
        String url = "http://craaxcloud.epsevg.upc.edu:36301/negocios";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //mTextViewResult.setText("Response: " + response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject bussines = response.getJSONObject(i);
                                bussines_card b = new bussines_card(bussines.getJSONArray("intereses"), bussines.getString("nombre"), bussines.getString("tipo"), bussines.getString("local"), bussines.getString("descripcion"), bussines.getString("estado"), bussines.getString("logo"), bussines.getString("foto"), bussines.getJSONArray("valoracion"));
                                data_list.add(b);
                            }
                            adapter = new RecyclerViewBussinesAdapter(data_list, ListaNegocios.this);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

}
