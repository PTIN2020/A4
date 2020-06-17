package terminal1.a4.loginui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.ResourceBundle;

import terminal1.a4.tarjeta_embarque.Tembarque;

public class StartActivity extends AppCompatActivity {
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mQueue = Volley.newRequestQueue(this);
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String token = preferences.getString("username","");
        ////////////////////////////////////////////////
        if(!token.equals("")) {
                String urlpreferencias = "http://craaxcloud.epsevg.upc.edu:36301/negocios/porinteres/" + token;
                final String[] tipo = {" "};
                final String[] nombrenegocio = {" "};
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlpreferencias, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                //mTextViewResult.setText("Response: " + response.toString());
                                try {
                                    final int min = 0;
                                    final int max = response.length();
                                    final int random = new Random().nextInt((max - min) ) + min;
                                    SharedPreferences.Editor editor = preferences.edit();
                                        editor.commit();
                                        JSONObject jsonObject = response.getJSONObject(random);
                                        tipo[0] = jsonObject.getString("tipo");
                                        nombrenegocio[0] = jsonObject.getString("nombre");
                                        System.out.println("nombrenegocio:");
                                        System.out.println(tipo[0]);
                                        editor.putString("tipo", tipo[0]);
                                        editor.putString("nombrenegocio", nombrenegocio[0]);
                                        editor.commit();

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
                ///////////////////////////////////////////////
                String tipon = preferences.getString("tipo", "");
                String nombrenegocion = preferences.getString("nombrenegocio", "");
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("tipo");
                editor.remove("nombrenegocio");
                if(!tipon.equals("") && !nombrenegocion.equals("")) {
                    createNotificationChannel();
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "gustos")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setWhen(System.currentTimeMillis())
                            .setContentTitle("Segun sus intereses le recomendamos:")
                            .setContentText("Ir a " + tipon + " " + nombrenegocion)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    Intent intent = new Intent(StartActivity.this, mapa.class);
                    PendingIntent pendingintent = PendingIntent.getActivity(StartActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pendingintent);
                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(201, builder.build());
                }
        }
        ///////////////////////////////////////////////



        ////////////////////////////////////////////////////////////////////////////////////////////////
        System.out.println("despues2");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(!token.equals("")){

                    startActivity(new Intent(StartActivity.this, Tembarque.class));
                    finish();
                }
                else{

                    startActivity(new Intent(StartActivity.this, Login.class));
                }
            }
        }, 2000);   //2 seconds
    }
    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "gustotiendas";
            String description = "Avisa tiendas y restaurantes de interes";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("gustos", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
