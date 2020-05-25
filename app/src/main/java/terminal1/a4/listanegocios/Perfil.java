package terminal1.a4.listanegocios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import terminal1.a4.loginui.R;
import terminal1.a4.loginui.mapa;
import terminal1.a4.loginui.servicios;
import terminal1.a4.tarjeta_embarque.Tembarque;

public class Perfil extends AppCompatActivity {
    private TextView mTopicSelected;
    private String[] listTopics;
    private boolean[] checkedTopic;
    private ArrayList<Integer> mUserTopics = new ArrayList<>();
    private RequestQueue mQueue;
    private String[] listIntereses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        configureeditarperfil();

        //API
        mQueue = Volley.newRequestQueue(this);
        jsonGet();


        // Dialog
        Button mTopic = (Button) findViewById(R.id.btntopic);
        mTopicSelected = (TextView) findViewById(R.id.tvtopic);

        listTopics = getResources().getStringArray(R.array.preferences);
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
                        for (int i = 0;  i < mUserTopics.size(); i++){
                            // aqui es donde ser recogen los datos para crear el json.
                            topic = topic + listTopics[mUserTopics.get(i)];
                            if (i != mUserTopics.size()-1){
                                topic = topic + ", ";
                            }
                        }
                        mTopicSelected.setText(topic);
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
                            mTopicSelected.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

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

    private void jsonGet() {
        String url = "http://192.168.0.29:3000/intereses";
        listIntereses = null;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            listIntereses = response.getJSONObject(0).getString("interes").split(",");
                            //for (int i = 0; i < listIntereses.length; i++) {
                            //    mTopicSelected.append("pos "+ i + " " + listIntereses[i] + " ");
                            //}
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

    private void configureeditarperfil(){
        Button edit = (Button) findViewById(R.id.editar);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Perfil.this, modificarperfil.class));
            }
        });
    }
}