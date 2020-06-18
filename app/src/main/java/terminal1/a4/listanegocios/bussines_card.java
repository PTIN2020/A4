package terminal1.a4.listanegocios;

import org.json.JSONArray;

public class bussines_card {
    private JSONArray intereses;
    private String nombre;
    private String tipo;
    private String local;
    private String desc;
    private String estado;
    private String logo;
    private String foto;
    private JSONArray valoracion;

    public bussines_card (JSONArray intereses, String nombre, String tipo, String local, String desc, String estado, String logo, String foto, JSONArray valoracion){
        this.intereses=intereses;
        this.nombre=nombre;
        this.tipo=tipo;
        this.local=local;
        this.desc=desc;
        this.estado=estado;
        this.logo=logo;
        this.foto=foto;
        this.valoracion=valoracion;
    }

    public JSONArray getIntereses() {
        return intereses;
    }

    public void setIntereses(JSONArray intereses) {
        this.intereses = intereses;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public JSONArray getValoracion() {
        return valoracion;
    }

    public void setValoracion(JSONArray valoracion) {
        this.valoracion = valoracion;
    }
}
