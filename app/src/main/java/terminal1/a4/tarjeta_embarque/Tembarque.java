package terminal1.a4.tarjeta_embarque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import terminal1.a4.listanegocios.ListaNegocios;
import terminal1.a4.listanegocios.Perfil;
import terminal1.a4.loginui.AlertReceiver;
import terminal1.a4.loginui.R;
import terminal1.a4.loginui.mapa;
import terminal1.a4.loginui.mis_vuelos;
import terminal1.a4.loginui.servicios;

public class Tembarque extends AppCompatActivity {
    private RequestQueue mQueue;
    private TextView mTextViewResult2;
    private TextView mTextViewResult3;
    private TextView mTextViewResult4;
    private TextView mTextViewResult5;
    private TextView mTextViewResult6;
    private TextView mTextViewResult7;
    private TextView mTextViewResult8;
    private TextView mTextViewResult9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarjeta_embarque);


        mTextViewResult2 = findViewById(R.id.hora);
        mTextViewResult3 = findViewById(R.id.numero_emb);
        mTextViewResult4 = findViewById(R.id.numero_as);
        mTextViewResult5 = findViewById(R.id.nombre_com);
        mTextViewResult6 = findViewById(R.id.n_maletas);
        mTextViewResult7 = findViewById(R.id.num_dni);
        CheckBox V = findViewById(R.id.textvip1);
        CheckBox D = findViewById(R.id.textnecesidades1);

        createNotificationChannel();
        botndevuelos();
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        botontransporte();
        mQueue = Volley.newRequestQueue(this);
        jsonParse();
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        String user = preferences.getString("username","");
        String url = "http://craaxcloud.epsevg.upc.edu:36302/pospasajeros/" + user;
        final String[] strDate = {""};
        System.out.println("antesd");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //mTextViewResult.setText("Response: " + response.toString());
                        try {
                            System.out.println("entry");
                            strDate[0] = response.getString("hora");
                            //Cuando se inicia la app mira el proximo vuelo y pone una alarma
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("Horaembarque",strDate[0]);
                            editor.commit();

                            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                            Date dateembarque = null;
                            try {
                                dateembarque = dateFormat.parse(strDate[0]);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Date dateactual = new Date();
                            String dateformatted = dateFormat.format(dateembarque);
                            String dateformatted2 = dateFormat.format(dateactual);

                            long diff = dateembarque.getTime() - dateactual.getTime();
                            String dateformatted3 = dateFormat.format(diff);

                            Date milis = null;
                            try {
                                milis = dateFormat.parse(dateformatted3);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            System.out.println(dateformatted);
                            System.out.println(dateformatted2);
                            System.out.println(dateformatted3);
                            System.out.println(milis.getTime());
                            if(milis.getTime() <= 1800000){
                                Intent intent = new Intent(Tembarque.this, AlertReceiver.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(Tembarque.this,1, intent ,0);
                                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                long tiempoactual = System.currentTimeMillis();
                                long segundosenmillis = milis.getTime() ;
                                alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                                        tiempoactual + 5000,
                                        pendingIntent);
                            }
                            else {
                                Intent intent = new Intent(Tembarque.this, AlertReceiver.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(Tembarque.this, 1, intent, 0);
                                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                long tiempoactual = System.currentTimeMillis();
                                long segundosenmillis = milis.getTime()-1800000;
                                alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                                        tiempoactual + segundosenmillis,
                                        pendingIntent);
                            }

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
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        String token = preferences.getString("cochepedido","");
        Button buttransporte = (Button) findViewById(R.id.buttontransporte);
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
                        break;
                    case R.id.ic_mapa:
                        Intent intent1 = new Intent(Tembarque.this, mapa.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_servicios:
                        Intent intent2 = new Intent(Tembarque.this, servicios.class);
                        //CheckBox checkBox = findViewById(R.id.checkBoxminus);
                        //intent2.putExtra("grade1", checkBox.isChecked());
                        startActivity(intent2);
                        break;
                    case R.id.ic_tiendas:
                        Intent intent3 = new Intent(Tembarque.this, ListaNegocios.class);
                        startActivity(intent3);
                        break;
                    case R.id.ic_perfil:
                        Intent intent4 = new Intent(Tembarque.this, Perfil.class);
                        startActivity(intent4);
                        break;
                }
                return true;
            }

        });
    }
    private void botontransporte(){
            Button buttransporte = (Button) findViewById(R.id.buttontransporte);
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
                                buttransporte.setBackgroundResource(R.drawable.btn_circle);}
                        }, 60000);
                        final AlertDialog.Builder builder = new AlertDialog.Builder(Tembarque.this);
                        builder.setMessage("Su vehiculo ha sido solicitado.");
                        builder.setCancelable(true);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else if (buttransporte.getText().toString().equals("Vehiculo Solicitado")){
                        final AlertDialog.Builder builder = new AlertDialog.Builder(Tembarque.this);
                        builder.setMessage("No puede solicitar otro vehiculo");
                        builder.setCancelable(true);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
            });
    }
    private void botndevuelos(){
        Button misvuelos = (Button) findViewById(R.id.buttonotrosvuelo);
        misvuelos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentvuelo = new Intent(Tembarque.this, mis_vuelos.class);
                startActivity(intentvuelo);
            }
        });
    }
    private void createNotificationChannel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            CharSequence name = "Horaembarquereminder";
            String description = "Avisa cuando queda 1 hora para el embarque";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyembarque", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void jsonParse() {

        final String[] hora={" "};
        final String[] numero={" "};
        final String[] numero2={" "};
        final String[] nombre2={" "};
        final String[] nummaletas={" "};
        final  String[] dnipass={" "};
        final String[] vip={" "};
        final String[] minusvalia={" "};


        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String username = preferences.getString("username","");
        //String username = "catwoman@gothamcitymail.com";
        String url = "http://craaxcloud.epsevg.upc.edu:36301/Billetes/user/"+username;
        System.out.println(url);
        //mTextViewResult.setText("");
        // mTextViewResult2.append(hora[0] + "\n");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //mTextViewResult.setText("Response: " + response.toString());

                        // JSONObject  = new JSONObject();
                        // System.out.println();
                        try {
                            hora[0] = response.getString("hora");
                            numero[0] = response.getString("puerta");
                            numero2[0] = response.getString("asiento");
                            nombre2[0] = response.getString("aerolinea");
                            nummaletas[0] = response.getString("maletas_paid");
                            dnipass[0] = response.getString("id_user");
                            vip[0] = response.getString("vip");
                            minusvalia[0] = response.getString("disable");

                            mTextViewResult2.append(hora[0] );
                            mTextViewResult3.append(numero[0] );
                            mTextViewResult4.append(numero2[0] );
                            mTextViewResult5.append(nombre2[0] );
                            mTextViewResult6.append(nummaletas[0] );
                            mTextViewResult7.append(dnipass[0] );
                            if(vip[0].equals("true")){
                                CheckBox V = findViewById(R.id.textvip1);
                                V.setChecked(true);
                            }
                            if(minusvalia[0].equals("true")){
                                CheckBox D = findViewById(R.id.textnecesidades1);
                                D.setChecked(true);
                            }
                        }  catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //  mTextViewResult3.setText(error.getMessage());
            }
        });
        mQueue.add(request);
    }

}


