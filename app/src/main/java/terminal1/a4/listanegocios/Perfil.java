package terminal1.a4.listanegocios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import terminal1.a4.loginui.R;
import terminal1.a4.loginui.mapa;
import terminal1.a4.loginui.servicios;
import terminal1.a4.tarjeta_embarque.Tembarque;

public class Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        configureeditarperfil();

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