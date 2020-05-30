package terminal1.a4.loginui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import terminal1.a4.listanegocios.nuevoperfil;
import terminal1.a4.tarjeta_embarque.Tembarque;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
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

public class Login extends AppCompatActivity {

    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        configureNextButton();
        configureregistrarusuario();
        mQueue = Volley.newRequestQueue(this);
        //estoy hay que pasarla a la splash screen
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String token = preferences.getString("username","");
        if(!token.equals("")){
            startActivity(new Intent(Login.this, Tembarque.class));
            finish();
        }
    }

    private void configureNextButton(){
        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonPost();
            }
        });
    }
    private void jsonPost() {
        final EditText[] campouser = new EditText[1], campopassword = new EditText[1];
        campopassword[0] = (EditText) findViewById(R.id.contrasenya);
        campouser[0] = (EditText) findViewById(R.id.correu);
        String password = campopassword[0].getText().toString();
        String user = campouser[0].getText().toString();

        String url = "http://craaxcloud.epsevg.upc.edu:36301/login";
        //mTextViewResult.setText("Todo Ready!");
        JSONObject pasajero = new JSONObject();
        try {
            pasajero.put("correu", user);
            pasajero.put("contrasenya", password);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, pasajero, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response.toString().contains("ok")){
                    SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("username",user);
                    editor.commit();
                    startActivity(new Intent(Login.this, Tembarque.class));
                    finish();
                }
                else{
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setMessage("Email o Contraseña incorrectos");
                    builder.setCancelable(true);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //si entra aqui esta bien ya que en el post no hay json.
            }
        });
        mQueue.add(jsonRequest);
    }

    private void configureregistrarusuario(){
        Button registButton = (Button) findViewById(R.id.Registralogin);
        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, nuevoperfil.class));
            }
        });
    }
}
