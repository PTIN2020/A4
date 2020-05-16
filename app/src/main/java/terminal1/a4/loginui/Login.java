package terminal1.a4.loginui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import terminal1.a4.listanegocios.nuevoperfil;
import terminal1.a4.tarjeta_embarque.Tembarque;


public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        configureNextButton();
        configureregistrarusuario();
    }

    private void configureNextButton(){
        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Tembarque.class));
            }
        });
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
