package terminal1.a4.listanegocios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import terminal1.a4.loginui.R;

public class modificarperfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarperfil);
        configureguardar();
    }
    private void configureguardar(){
        Button guardaredit = (Button) findViewById(R.id.guardar);
       guardaredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(modificarperfil.this, Perfil.class));
            }
        });
    }
}