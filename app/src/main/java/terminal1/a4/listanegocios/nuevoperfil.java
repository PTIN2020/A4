package terminal1.a4.listanegocios;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import terminal1.a4.loginui.Login;
import terminal1.a4.loginui.R;
import terminal1.a4.loginui.servicios;
import terminal1.a4.tarjeta_embarque.Tembarque;


public class nuevoperfil extends AppCompatActivity {
    Dialog popup;
    private RequestQueue mQueue;

//Abandone toda esperanza aquel que ose leer mi código espagueti

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevoperfil);
        popup = new Dialog(this);
        mQueue = Volley.newRequestQueue(this);

    }

    public void regis(View v) {
        final EditText[] camporepetirpassword = new EditText[1], camponombre = new EditText[1], campoapellidos = new EditText[1], campoanacio = new EditText[1], campopassword = new EditText[1], campousuario = new EditText[1], campoid_user = new EditText[1], campotelefono = new EditText[1];
        ;
        final Boolean[] disabled = new Boolean[1];
        campopassword[0] = (EditText) findViewById(R.id.editpassword);
        String password = campopassword[0].getText().toString();
        camporepetirpassword[0] = (EditText) findViewById(R.id.edittpasword2);
        String password2 = camporepetirpassword[0].getText().toString();
        camponombre[0] = (EditText) findViewById(R.id.editnombre);
        String nombre = camponombre[0].getText().toString();
        campoapellidos[0] = (EditText) findViewById(R.id.editapellidos);
        String apellidos = campoapellidos[0].getText().toString();
        campoanacio[0] = (EditText) findViewById(R.id.editnacio);
        String nacio = campoanacio[0].getText().toString();
        campoid_user[0] = (EditText) findViewById(R.id.editid_user);
        String iduser = campoid_user[0].getText().toString();
        campotelefono[0] = (EditText) findViewById(R.id.edittelefono);
        String telefono = campotelefono[0].getText().toString();
        campousuario[0] = (EditText) findViewById(R.id.editusuario);
        String usuario = campousuario[0].getText().toString();
        CheckBox checkBox = findViewById(R.id.checkBox);
        RadioButton Masc = findViewById(R.id.GMasculino);
        RadioButton Fem = findViewById(R.id.GFemenino);
        RadioButton Otr = findViewById(R.id.GOtro);
        String genero = "";
        if (checkBox.isChecked()) {
            disabled[0] = true;
        } else {
            disabled[0] = false;
        }
        if (Masc.isChecked()) {
            genero = "Masculino";
        }
        if (Fem.isChecked()) {
            genero = "Femenino";
        }
        if (Otr.isChecked()) {
            genero = "Otro";
        }

        if (iduser.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || nacio.isEmpty() || genero.isEmpty() || telefono.isEmpty() || usuario.isEmpty() || password.isEmpty() || password2.isEmpty()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(nuevoperfil.this);
            builder.setMessage("Debes rellenar todos los campos");
            builder.setCancelable(true);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (password.equals(password2)) {
            TextView desc;
            ImageView exclamation;
            Button accept;
            Button deny;
            popup.setContentView(R.layout.advice);
            accept = (Button) popup.findViewById(R.id.accept_policy);
            deny = (Button) popup.findViewById(R.id.deny_policy);
            desc = (TextView) popup.findViewById(R.id.Desc);
            exclamation = (ImageView) popup.findViewById(R.id.advice);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    camponombre[0] = (EditText) findViewById(R.id.editnombre);
                    String nombre = camponombre[0].getText().toString();
                    campoapellidos[0] = (EditText) findViewById(R.id.editapellidos);
                    String apellidos = campoapellidos[0].getText().toString();
                    campoanacio[0] = (EditText) findViewById(R.id.editnacio);
                    String nacio = campoanacio[0].getText().toString();
                    campoid_user[0] = (EditText) findViewById(R.id.editid_user);
                    String iduser = campoid_user[0].getText().toString();
                    campotelefono[0] = (EditText) findViewById(R.id.edittelefono);
                    String telefono = campotelefono[0].getText().toString();
                    campousuario[0] = (EditText) findViewById(R.id.editusuario);
                    String usuario = campousuario[0].getText().toString();
                    campopassword[0] = (EditText) findViewById(R.id.editpassword);
                    String password = campopassword[0].getText().toString();
                    CheckBox checkBox = findViewById(R.id.checkBox);
                    RadioButton Masc = findViewById(R.id.GMasculino);
                    RadioButton Fem = findViewById(R.id.GFemenino);
                    RadioButton Otr = findViewById(R.id.GOtro);
                    String genero = "";
                    if (checkBox.isChecked()) {
                        disabled[0] = true;
                    } else {
                        disabled[0] = false;
                    }
                    if (Masc.isChecked()) {
                        genero = "Masculino";
                    }
                    if (Fem.isChecked()) {
                        genero = "Femenino";
                    }
                    if (Otr.isChecked()) {
                        genero = "Otro";
                    }
                    String url = "http://192.168.1.57:3000/pasajero";
                    //mTextViewResult.setText("Todo Ready!");
                    JSONObject pasajero = new JSONObject();
                    try {
                        pasajero.put("id_user", iduser);
                        pasajero.put("nombre", nombre);
                        pasajero.put("apellidos", apellidos);
                        pasajero.put("nacio", nacio);
                        pasajero.put("genero", genero);
                        pasajero.put("telefono", telefono);
                        pasajero.put("disable", disabled[0]);
                        pasajero.put("username", usuario);
                        pasajero.put("password", password);
                        pasajero.put("vip", false);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    //mTextViewResult.setText("Response: " + pasajero.toString());

                    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, pasajero, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (response.toString().contains("ok")) {
                                startActivity(new Intent(nuevoperfil.this, Login.class));
                                finish();
                            } else {
                                popup.dismiss();
                                final AlertDialog.Builder builder = new AlertDialog.Builder(nuevoperfil.this);
                                builder.setMessage("La dirección de e-mail introducida ya está en uso");
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
            });
            deny.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.dismiss();
                }
            });
            popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popup.show();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(nuevoperfil.this);
            builder.setMessage("Las contraseñas introducidas no coinciden");
            builder.setCancelable(true);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
