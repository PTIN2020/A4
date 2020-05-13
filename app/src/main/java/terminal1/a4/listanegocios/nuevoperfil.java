package terminal1.a4.listanegocios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import terminal1.a4.loginui.R;

public class nuevoperfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevoperfil);

        configureregistrarse();
    }
    private void configureregistrarse(){
        Button reg = (Button) findViewById(R.id.registrarse);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(nuevoperfil.this, Perfil.class));
            }
        });
    }
}
