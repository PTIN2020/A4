package terminal1.a4.tarjeta_embarque;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import terminal1.a4.listanegocios.MainActivity;
import terminal1.a4.listanegocios.Perfil;
import terminal1.a4.listanegocios.modificarperfil;
import terminal1.a4.loginui.R;
import terminal1.a4.loginui.mis_vuelos;

public class Tembarque extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarjeta_embarque);
        botndevuelos();
    }
    private void botndevuelos(){
        Button misvuelos = (Button) findViewById(R.id.buttonotrosvuelo);
        misvuelos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Tembarque.this, mis_vuelos.class));
            }
        });
    }
}


