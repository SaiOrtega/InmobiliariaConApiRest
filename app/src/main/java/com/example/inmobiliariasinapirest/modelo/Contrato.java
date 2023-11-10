package com.example.inmobiliariasinapirest.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Contrato implements Serializable {

    private int id;
    private String fechaInicio;
    private String fechaFinal;
    private double montoMensual;
    private Inquilino inquilino;
    private Inmueble inmueble;

    public Contrato() {}
    public Contrato(int idContrato, String fechaInicio, String fechaFinal, double montoAlquiler, Inquilino inquilino, Inmueble inmueble) {
        this.id = idContrato;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.montoMensual = montoAlquiler;
        this.inquilino = inquilino;
        this.inmueble = inmueble;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public double getMontoMensual() {
        return montoMensual;
    }

    public void setMontoMensual(double montoMensual) {
        this.montoMensual = montoMensual;
    }


    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrato contrato = (Contrato) o;
        return id == contrato.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
