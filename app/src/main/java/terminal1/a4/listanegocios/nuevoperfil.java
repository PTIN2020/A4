package terminal1.a4.listanegocios;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import terminal1.a4.loginui.R;

public class nuevoperfil extends AppCompatActivity {
    Dialog popup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevoperfil);
        popup = new Dialog(this);

    }


    public void ShowPopUp(View v) {
        TextView desc;
        ImageView exclamation;
        Button accept;
        Button deny;
        popup.setContentView(R.layout.advice);
        accept = (Button) popup.findViewById(R.id.accept_policy);
        deny = (Button) popup.findViewById(R.id.deny_policy);
        desc = (TextView) popup.findViewById(R.id.Desc);
        exclamation = (ImageView) popup.findViewById(R.id.advice);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(nuevoperfil.this, Perfil.class));
            }
        });
        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.show();
    }

}
