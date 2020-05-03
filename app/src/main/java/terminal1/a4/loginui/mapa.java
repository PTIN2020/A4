package terminal1.a4.loginui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import terminal1.a4.listanegocios.ListaNegocios;
import terminal1.a4.listanegocios.Perfil;
import terminal1.a4.tarjeta_embarque.Tembarque;

public class mapa extends AppCompatActivity {

    FloatingActionMenu actionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        actionMenu=(FloatingActionMenu)findViewById(R.id.floatingActionMenu);
        actionMenu.setClosedOnTouchOutside(true);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_vuelos:
                        Intent intent1 = new Intent(mapa.this, Tembarque.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_mapa:
                        break;
                    case R.id.ic_servicios:
                        Intent intent2 = new Intent(mapa.this, servicios.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_tiendas:
                        Intent intent3 = new Intent(mapa.this, ListaNegocios.class);
                        startActivity(intent3);
                        break;
                    case R.id.ic_perfil:
                        Intent intent4 = new Intent(mapa.this, Perfil.class);
                        startActivity(intent4);
                        break;
                }
                return true;
            }
        });
    }


}
