package terminal1.a4.loginui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import terminal1.a4.listanegocios.ListaNegocios;
import terminal1.a4.listanegocios.Perfil;
import terminal1.a4.tarjeta_embarque.Tembarque;

public class servicios extends AppCompatActivity {
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);
        mQueue = Volley.newRequestQueue(this);
        botontransporte();
        Button buttransporte = (Button) findViewById(R.id.button3);
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String token = preferences.getString("cochepedido","");
        if(!token.equals("")){
            buttransporte.setText("Vehiculo Solicitado");
            buttransporte.setBackgroundResource(R.drawable.btn_circle2);
        }
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_vuelos:
                        Intent intent2 = new Intent(servicios.this, Tembarque.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_mapa:
                        Intent intent1 = new Intent(servicios.this, mapa.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_servicios:

                        break;
                    case R.id.ic_tiendas:
                        Intent intent3 = new Intent(servicios.this, ListaNegocios.class);
                        startActivity(intent3);
                        break;
                    case R.id.ic_perfil:
                        Intent intent4 = new Intent(servicios.this, Perfil.class);
                        startActivity(intent4);
                        break;
                }
                return true;
            }
        });
    }

    private void botontransporte(){
        Button buttransporte = (Button) findViewById(R.id.button3);
        buttransporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttransporte.getText().toString().equals("Solicitar Vehiculo")){
                    SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                    String user = preferences.getString("username","");
                    String VIP = preferences.getString("VIP","");
                    String DISABLE = preferences.getString("DISABLE","");
                    String url = "http://craaxcloud.epsevg.upc.edu:36302/pospasajeros/" + user;
                    final String[] posicionpasjaero= {""};
                    final String[] destinopasjaero= {""};


                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    //mTextViewResult.setText("Response: " + response.toString());
                                    try {
                                        posicionpasjaero[0] = response.getString("nodo");
                                        destinopasjaero[0] = response.getString("puerta");

                                        JSONObject pasajero = new JSONObject();
                                        String url1 = "http://craaxcloud.epsevg.upc.edu:36302/listaespera/";
                                        try {
                                            pasajero.put("id_pasajero", user);
                                            pasajero.put("vip", VIP);
                                            pasajero.put("disable", DISABLE);
                                            pasajero.put("nodoactual", posicionpasjaero[0]);
                                            pasajero.put("nododestino", destinopasjaero[0]);
                                            SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor=preferences.edit();
                                            editor.putString("cochepedido","true");
                                            editor.commit();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        //mTextViewResult.setText("Response: " + pasajero.toString());

                                        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url1, pasajero, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                //nada
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                //si entra aqui esta bien ya que en el post no hay json.
                                            }
                                        });
                                        mQueue.add(jsonRequest);
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
                    buttransporte.setText("Vehiculo Solicitado");
                    buttransporte.setBackgroundResource(R.drawable.btn_circle2);
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                            preferences.edit().remove("cochepedido").commit();
                            buttransporte.setText("Solicitar Vehiculo");
                            buttransporte.setBackgroundResource(R.drawable.btn_circle);
                        }
                    }, 60000);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(servicios.this);
                    builder.setMessage("Su vehiculo ha sido solicitado.");
                    builder.setCancelable(true);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else if (buttransporte.getText().toString().equals("Vehiculo Solicitado")){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(servicios.this);
                    builder.setMessage("No puede solicitar otro vehiculo");
                    builder.setCancelable(true);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
    }

}
