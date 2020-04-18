package terminal1.a4.loginui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.clans.fab.FloatingActionMenu;

public class mapa extends AppCompatActivity {

    FloatingActionMenu actionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        actionMenu=(FloatingActionMenu)findViewById(R.id.floatingActionMenu);
        actionMenu.setClosedOnTouchOutside(true);
    }


}
