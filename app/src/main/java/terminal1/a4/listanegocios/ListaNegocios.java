package terminal1.a4.listanegocios;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import terminal1.a4.loginui.R;
import terminal1.a4.loginui.mapa;
import terminal1.a4.loginui.servicios;
import terminal1.a4.tarjeta_embarque.Tembarque;

public class ListaNegocios extends AppCompatActivity {

    RelativeLayout relativeLayout1;
    RelativeLayout relativeLayout2;
    RelativeLayout relativeLayout3;
    RelativeLayout relativeLayout4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_negocios);

        relativeLayout1=(RelativeLayout)findViewById(R.id.relativeLayout1) ;
        relativeLayout2=(RelativeLayout)findViewById(R.id.relativeLayout2);
        relativeLayout3=(RelativeLayout)findViewById(R.id.relativeLayout3);
        relativeLayout4=(RelativeLayout)findViewById(R.id.relativeLayout4);

        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redb = new Intent(ListaNegocios.this, redbar.class);
                startActivity(redb);
            }
        });

        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bur = new Intent(ListaNegocios.this, burger.class);
                startActivity(bur);
            }
        });

        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mc = new Intent(ListaNegocios.this, mcdonalds.class);
                startActivity(mc);
            }
        });

        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kf = new Intent(ListaNegocios.this, kfc.class);
                startActivity(kf);
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_vuelos:
                        Intent intent3 = new Intent(ListaNegocios.this, Tembarque.class);
                        startActivity(intent3);
                        break;
                    case R.id.ic_mapa:
                        Intent intent1 = new Intent(ListaNegocios.this, mapa.class);
                        startActivity(intent1);
                        break;
                    case R.id.ic_servicios:
                        Intent intent2 = new Intent(ListaNegocios.this, servicios.class);
                        startActivity(intent2);
                        break;
                    case R.id.ic_tiendas:
                        break;
                    case R.id.ic_perfil:
                        Intent intent4 = new Intent(ListaNegocios.this, Perfil.class);
                        startActivity(intent4);
                        break;
                }
                return true;
            }
        });
    }
}
