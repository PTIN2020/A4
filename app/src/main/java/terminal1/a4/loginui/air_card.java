package terminal1.a4.loginui;

import android.widget.ImageView;

import org.json.JSONArray;

import java.util.List;

public class air_card {
    private String id;
    private String ori;
    private String dst;
    private String da;
    private String hora;
    private String ac;
    private String pu;
    private int aa;
    private int at;
    private JSONArray as;
    private int qr;


    public air_card(String id, String dst, String ori, String da, String hora, String ac, String pu, int aa, int at, JSONArray as, int qr) {
        this.id = id;
        this.ori = ori;
        this.dst = dst;
        this.da = da;
        this.hora = hora;
        this.ac = ac;
        this.pu = pu;
        this.aa = aa;
        this.at = at;
        this.as = as;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getOri() {
        return ori;
    }

    public void setOri(String ori) {
        this.ori = ori;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getDa() {
        return da;
    }

    public void setDa(String da) {
        this.da = da;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }
    public String getPu() {
        return pu;
    }

    public void setPu(String pu) {
        this.pu = pu;
    }

    public int getAa() {
        return aa;
    }

    public void setAa(int aa) {
        this.aa = aa;
    }

    public int getAt() {
        return at;
    }

    public void setAt(int at) {
        this.at = at;
    }
    public JSONArray getAs() {
        return as;
    }

    public void setAs(JSONArray as) {
        this.as = as;
    }
    public int getQr() {
        return qr;
    }

    public void setAs(int qr) {
        this.qr = qr;
    }
}
