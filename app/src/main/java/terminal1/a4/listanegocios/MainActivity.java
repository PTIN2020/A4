package terminal1.a4.listanegocios;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;

import android.os.Bundle;

import terminal1.a4.loginui.R;

public class MainActivity extends AppCompatActivity {

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
                Intent redb = new Intent(MainActivity.this, redbar.class);
                startActivity(redb);
            }
        });

        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bur = new Intent(MainActivity.this, burger.class);
                startActivity(bur);
            }
        });

        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mc = new Intent(MainActivity.this, mcdonalds.class);
                startActivity(mc);
            }
        });

        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kf = new Intent(MainActivity.this, kfc.class);
                startActivity(kf);
            }
        });

    }
}
