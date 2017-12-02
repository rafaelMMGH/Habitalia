package com.ambarrojostudios.rafael.habitalias;

/**
 * Created by rafael on 28/10/17.
 */

public class Casa {

    String fld_acceso;
    String fld_habitacion;
    String fld_informacion;
    String fld_lat;
    String fld_lng;
    String fld_plantas;
    String fld_precio;
    String fld_tipo;
    String fld_titulo;
    String p_empresafk02;

    public Casa() {

    }

    public Casa(String fld_acceso, String fld_habitacion, String fld_informacion, String fld_lat, String fld_lng, String fld_plantas, String fld_precio, String fld_tipo, String fld_titulo, String p_empresafk02) {
        this.fld_acceso = fld_acceso;
        this.fld_habitacion = fld_habitacion;
        this.fld_informacion = fld_informacion;
        this.fld_lat = fld_lat;
        this.fld_lng = fld_lng;
        this.fld_plantas = fld_plantas;
        this.fld_precio = fld_precio;
        this.fld_tipo = fld_tipo;
        this.fld_titulo = fld_titulo;
        this.p_empresafk02 = p_empresafk02;
    }



    public String getFld_acceso() {
        return fld_acceso;
    }

    public String getFld_habitacion() {
        return fld_habitacion;
    }

    public String getFld_informacion() {
        return fld_informacion;
    }

    public String getFld_lat() {
        return fld_lat;
    }

    public String getFld_lng() {
        return fld_lng;
    }

    public String getFld_plantas() {
        return fld_plantas;
    }

    public String getFld_precio() {
        return fld_precio;
    }

    public String getFld_tipo() {
        return fld_tipo;
    }

    public String getFld_titulo() {
        return fld_titulo;
    }

    public String getP_empresafk02() {
        return p_empresafk02;
    }
}