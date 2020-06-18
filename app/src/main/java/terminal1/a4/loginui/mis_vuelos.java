package terminal1.a4.loginui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;

import terminal1.a4.listanegocios.ListaNegocios;
import terminal1.a4.listanegocios.Perfil;
import terminal1.a4.tarjeta_embarque.Tembarque;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class mis_vuelos extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAirAdapter adapter;
    private ArrayList<air_card> data_list;;
    Button reserva_vuelo;
    private RequestQueue mQueue;

    private void load_data_from_server() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("username","");
        System.out.println(user);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
        String url = "http://craaxcloud.epsevg.upc.edu:36301/billetes/user/"+user;
        System.out.println(url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //mTextViewResult.setText("Response: " + response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject vuelo = response.getJSONObject(i);
                                air_card ac = new air_card(vuelo.getString("asiento"),vuelo.getString("id_flight"),vuelo.getString("fecha"));
                                data_list.add(ac);

                            }

                            adapter = new RecyclerViewAirAdapter(data_list, mis_vuelos.this);

                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        mQueue.add(request);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_vuelos);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_vuelos:
                        Intent intent0 = new Intent(mis_vuelos.this, Tembarque.class);
                        startActivity(intent0);
                        break;
                    case R.id.ic_mapa:
                        Intent intent1 = new Intent(mis_vuelos.this, mapa.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_servicios:
                        Intent intent2 = new Intent(mis_vuelos.this, servicios.class);
                        //CheckBox checkBox = findViewById(R.id.checkBoxminus);
                        //intent2.putExtra("grade1", checkBox.isChecked());
                        startActivity(intent2);
                        break;
                    case R.id.ic_tiendas:
                        Intent intent3 = new Intent(mis_vuelos.this, ListaNegocios.class);
                        startActivity(intent3);
                        break;
                    case R.id.ic_perfil:
                        Intent intent4 = new Intent(mis_vuelos.this, Perfil.class);
                        startActivity(intent4);
                        break;
                }
                return true;
            }

        });



        reserva_vuelo=(Button)findViewById(R.id.reserva_vuelo);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_vuelo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        data_list = new ArrayList<>();

        mQueue = Volley.newRequestQueue(this);

        load_data_from_server();


        reserva_vuelo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent res = new Intent(mis_vuelos.this, reserva_vuelos.class);
                startActivity(res);
            }
        });

    }


}
