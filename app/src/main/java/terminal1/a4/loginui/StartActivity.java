package terminal1.a4.loginui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import terminal1.a4.tarjeta_embarque.Tembarque;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                String token = preferences.getString("username","");
                if(!token.equals("")){
                    startActivity(new Intent(StartActivity.this, Tembarque.class));
                    finish();
                }
                else{
                    startActivity(new Intent(StartActivity.this, Login.class));
                }
            }
        }, 1000);   //5 seconds
    }
}
