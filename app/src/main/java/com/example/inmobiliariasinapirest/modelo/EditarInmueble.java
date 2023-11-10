package com.example.inmobiliariasinapirest.modelo;

import java.io.Serializable;

public class EditarInmueble implements Serializable {

    private int id;
    private Boolean estado;

    public EditarInmueble(int id, Boolean estado) {
        this.id = id;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
