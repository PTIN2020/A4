package terminal1.a4.loginui;

import android.widget.ImageView;

import org.json.JSONArray;

import java.util.List;

public class air_card {
    private String id;
    private String asiento;
    private String fecha;


    public air_card(String asiento, String id, String fecha) {
        this.id = id;
        this.asiento = asiento;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
