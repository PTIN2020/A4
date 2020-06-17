package terminal1.a4.listanegocios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import terminal1.a4.loginui.Login;
import terminal1.a4.loginui.R;
import terminal1.a4.loginui.mapa;
import terminal1.a4.loginui.servicios;
import terminal1.a4.tarjeta_embarque.Tembarque;

public class Perfil extends AppCompatActivity {
    private String[] listTopics;
    //private ArrayList<String> mCheckedTopic = new ArrayList<String>(); //para el put
    private boolean[] checkedTopic;
    private String test ="";
    private ArrayList<String> listIntereses;
    private ArrayList<Integer> mUserTopics = new ArrayList<>();
    private RequestQueue mQueue;
    private EditText usuario, nombreperfil, apellidosperfil, docuperfil, nacimientoperfil, generoperfil, telefonoperfil, vipperfil, disabledperfil;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        configureeditarperfil();
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        boolean fogBool = preferences.getBoolean("fog",false);
        String fogString = new String();
        if (fogBool) fogString = "true";
        else fogString = "false";
        //Controldelfog
        @SuppressLint("WrongViewCast") Switch fogControl = (Switch) findViewById(R.id.switch1);
        fogControl.setChecked(fogBool);
        System.out.println(fogString);
        fogControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    activarfog();
                } else {
                    desactivarfog();
                }
            }
        });

        //API
        mQueue = Volley.newRequestQueue(this);
        //Dentro del Get llamamos al menu para tratar las preferencias
        jsonGet();
        jsonGetid();
        //Cargar datos perfil
        cargarpreferencias();
        //Botón log out
        controllogout();
        //Menu inferior
        Menuinferior();

    }


    private void SeleccionPreferencias(ArrayList<String> listIntereses){
        // Dialog
        TextView mTopicSelected = findViewById(R.id.tvtopic); // print testing
        //listTopics = getResources().getStringArray(R.array.preferences);
        Button mTopic = findViewById(R.id.btntopic);

        listTopics = new String[listIntereses.size()];
        listTopics = listIntereses.toArray(listTopics);
        checkedTopic = new boolean[listTopics.length];

        mTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Perfil.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listTopics, checkedTopic, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if (! mUserTopics.contains(position)) {
                                mUserTopics.add(position);
                            }
                        } else if (mUserTopics.contains(position)){
                            mUserTopics.remove((Integer) position);
                        }
                    }
                });

                mBuilder.setCancelable(false);

                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String topic = "";
                        ArrayList<String> mCheckedTopic = new ArrayList<String>();
                        for (int i = 0;  i < mUserTopics.size(); i++){
                            // aqui es donde ser recogen los datos para crear el json.
                            topic = topic + listTopics[mUserTopics.get(i)];
                            mCheckedTopic.add(listTopics[mUserTopics.get(i)]);
                            if (i != mUserTopics.size()-1){
                                topic = topic + ", ";
                            }
                        }
                        //mCheckedTopic = topic.split(",");
                        //mTopicSelected.setText(topic);
                        jsonPut(mCheckedTopic);
                    }
                });

                mBuilder.setNegativeButton(R.string.Cancel_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.Clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i=0; i < checkedTopic.length; i++){
                            checkedTopic[i] = false;
                            mUserTopics.clear();
                            //mTopicSelected.setText("");
                        }
                        jsonPut(null);
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }

        });
    }

    private void logout() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        String user = "";
        editor.putString("username", user);
        editor.putBoolean("fog", false);
        editor.apply();
        startActivity(new Intent(Perfil.this, Login.class));
        finish();
    }

    private void activarfog() {

        String url = "http://craaxcloud.epsevg.upc.edu:36302/nodos/random/position/";
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("username","");
        boolean fogState = true;
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("fog",fogState);
        url+=user;
        Log.d("Entramos en activarfog",url);
        editor.commit();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        //mTextViewResult.setText(error.getMessage());
                    }
                });
        mQueue.add(request);
    }

    private void desactivarfog(){
        String url = "http://craaxcloud.epsevg.upc.edu:36302/pospasajeros/";
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("username","");
        boolean fogState = false;
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("fog",fogState);
        editor.commit();
        url+=user;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        //mTextViewResult.setText(error.getMessage());
                    }
                });
        mQueue.add(request);


    }

    private void jsonGet() {
        String url = "http://craaxcloud.epsevg.upc.edu:36301/intereses";
        listIntereses = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String s ="";
                            for (int i = 0; i < response.length(); i++) {
                                listIntereses.add(response.getJSONObject(i).getString("interes"));
                                //Menu preferencias
                                SeleccionPreferencias(listIntereses);
                            }
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

    private void jsonGetid(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("username","");
        String url = "http://craaxcloud.epsevg.upc.edu:36301/pasajero/";
        url += user;
        System.out.println(url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String id;
                        try {
                            id = response.getString("_id");
                            SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("_id", id);
                            editor.apply();
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


    private void jsonPut(ArrayList<String> s) {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String id = preferences.getString("_id","");
        String url = "http://craaxcloud.epsevg.upc.edu:36301/pasajero/" + id;
        JSONObject new_intereses = new JSONObject();
        try {
            new_intereses.put("intereses", new JSONArray(s));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //mTopicSelected.setText("Response: " + new_intereses);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, url, new_intereses, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //nada
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //si entra aqui esta bien ya que en el put no hay json.
            }
        });

        mQueue.add(jsonRequest);
    }

    private void configureeditarperfil(){
        Button edit = findViewById(R.id.editar);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Perfil.this, modificarperfil.class));
            }
        });
    }

    private void controllogout(){
        ImageButton loff = findViewById(R.id.logoff);
        loff.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                String user = "";
                editor.putString("username", user);
                editor.putBoolean("fog", false);
                editor.apply();
                startActivity(new Intent(Perfil.this, Login.class));
                finish();
            }
        });
    }

    private void cargarpreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("username","");
        String url1 = "http://craaxcloud.epsevg.upc.edu:36301/pasajero/";
        String url = url1 + user ;
        final String[] iduserp = {"1"};
        final String[] nombrep = {" "};
        final String[] apellidosp = { " " };
        final String[] naciop = { " " };
        final String[] generop = { " " };
        final String[] vipp = { " " };
        final String[] disablep = { " " };
        final String[] telefonop = { " " };
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //mTextViewResult.setText("Response: " + response.toString());
                        try {
                            iduserp[0] = response.getString("id_user");
                            nombrep[0] = response.getString("nombre");
                            apellidosp[0] = response.getString("apellidos");
                            naciop[0] = response.getString("nacio");
                            generop[0] = response.getString("genero");
                            vipp[0] = response.getString("vip");
                            disablep[0] = response.getString("disable");
                            telefonop[0] = response.getString("telefono");
                            docuperfil = findViewById(R.id.textiduser);
                            docuperfil.setText(iduserp[0]);
                            nombreperfil = findViewById(R.id.textnombre);
                            nombreperfil.setText(nombrep[0]);
                            apellidosperfil = findViewById(R.id.textapellidos);
                            apellidosperfil.setText(apellidosp[0]);
                            nacimientoperfil = findViewById(R.id.textnacio);
                            nacimientoperfil.setText(naciop[0]);
                            telefonoperfil = findViewById(R.id.texttelefono);
                            telefonoperfil.setText(telefonop[0]);
                            if(vipp[0].equals("true")){
                                CheckBox V = findViewById(R.id.textvip);
                                V.setChecked(true);
                            }
                            if(disablep[0].equals("true")){
                                CheckBox D = findViewById(R.id.textnecesidades);
                                D.setChecked(true);
                            }
                            if(generop[0].equals("Masculino")){
                                RadioButton M = findViewById(R.id.textm);
                                M.setChecked(true);
                            }
                            if(generop[0].equals("Femenino")){
                                RadioButton F = findViewById(R.id.textf);
                                F.setChecked(true);
                            }
                            if(generop[0].equals("Otro")){
                                RadioButton O = findViewById(R.id.texto);
                                O.setChecked(true);
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
        usuario = findViewById(R.id.Emaild);
        usuario.setText(user);
    }

    private void Menuinferior(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_vuelos:
                        Intent intent4 = new Intent(Perfil.this, Tembarque.class);
                        startActivity(intent4);
                        break;
                    case R.id.ic_mapa:
                        Intent intent1 = new Intent(Perfil.this, mapa.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_servicios:
                        Intent intent2 = new Intent(Perfil.this, servicios.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_tiendas:
                        Intent intent3 = new Intent(Perfil.this, ListaNegocios.class);
                        startActivity(intent3);
                        break;
                    case R.id.ic_perfil:
                        break;
                }
                return true;
            }
        });
    }
}