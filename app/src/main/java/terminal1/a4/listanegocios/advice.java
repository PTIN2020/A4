package terminal1.a4.listanegocios;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import terminal1.a4.loginui.R;

class advice extends nuevoperfil {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advice);
       // configureregistrarse();
    }
    /*
    private void configureregistrarse() {
        Button reg = (Button) findViewById(R.id.accept_policy);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(advice.this, Perfil.class));
            }
        });
    }

     */
}
