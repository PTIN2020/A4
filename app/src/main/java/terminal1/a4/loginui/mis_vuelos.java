package terminal1.a4.loginui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;

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
        String url = "http://craaxcloud.epsevg.upc.edu:36301/billetes/user/"+user;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //mTextViewResult.setText("Response: " + response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject vuelo = response.getJSONObject(i);
                                air_card ac = new air_card(vuelo.getString("id"),vuelo.getString("destino"),vuelo.getString("origen"), vuelo.getString("fecha"), vuelo.getString("hora"), vuelo.getString("aerolinea"), vuelo.getString("puerta"), vuelo.getInt("asientos_a"),vuelo.getInt("asientos_t"), vuelo.getJSONArray("asientos"),R.drawable.qraircard);
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
        ;
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
