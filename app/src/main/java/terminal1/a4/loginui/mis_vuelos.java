package terminal1.a4.loginui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mis_vuelos extends AppCompatActivity {

    Button reserva_vuelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_vuelos);

        reserva_vuelo=(Button)findViewById(R.id.reserva_vuelo);

        reserva_vuelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent res = new Intent(mis_vuelos.this, reserva_vuelos.class);
                startActivity(res);

            }
        });


    }
}
