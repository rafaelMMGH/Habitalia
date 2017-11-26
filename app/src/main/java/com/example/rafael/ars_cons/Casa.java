package com.example.rafael.ars_cons;

/**
 * Created by rafael on 28/10/17.
 */

public class Casa {

    String negocioId;
    String negocio_nombre;
    String negocio_descripcion;
    String negocio_tipo;
    String negocio_tipo_construccion;
    String negocio_latitud;
    String negocio_longuitud;
    String negocio_tPrecio;

    public Casa() {

    }

    public Casa(String negocioId, String negocio_nombre, String negocio_descripcion, String negocio_tipo, String negocio_tipo_construccion, String negocio_latitud, String negocio_longuitud, String negocio_tPrecio) {
        this.negocioId = negocioId;
        this.negocio_nombre = negocio_nombre;
        this.negocio_descripcion = negocio_descripcion;
        this.negocio_tipo = negocio_tipo;
        this.negocio_tipo_construccion = negocio_tipo_construccion;
        this.negocio_latitud = negocio_latitud;
        this.negocio_longuitud = negocio_longuitud;
        this.negocio_tPrecio = negocio_tPrecio;

    }

    public String getNegocioId() {
        return negocioId;
    }

    public String getNegocio_nombre() {
        return negocio_nombre;
    }

    public String getNegocio_descripcion() {
        return negocio_descripcion;
    }

    public String getNegocio_tipo() {
        return negocio_tipo;
    }

    public String getNegocio_tipo_construccion() {
        return negocio_tipo_construccion;
    }

    public String getNegocio_latitud() {
        return negocio_latitud;
    }

    public String getNegocio_longuitud() {
        return negocio_longuitud;
    }

    public String getNegocio_tPrecio() {
        return negocio_tPrecio;
    }
}