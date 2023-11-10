package com.example.inmobiliariasinapirest.modelo;

import java.io.Serializable;

public class Pago implements Serializable {

    private int id;
    private int numero;
    private Contrato contrato;
    private double monto;
    private String fechaPago;

    private int contratoId;

    public Pago() {}

    public Pago(int id, int numero, Contrato contrato, double monto, String fechaPago, int contratoId) {
        this.id = id;
        this.numero = numero;
        this.contrato = contrato;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.contratoId = contratoId;
    }

    public Pago(int id, int numero, double monto, String fechaPago, int contratoId) {
        this.id = id;
        this.numero = numero;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.contratoId = contratoId;
    }

    public int getContratoId() {
        return contratoId;
    }

    public void setContratoId(int contratoId) {
        this.contratoId = contratoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "id=" + id +
                ", numero=" + numero +
                ", monto=" + monto +
                ", fechaPago='" + fechaPago + '\'' +
                '}';
    }
}
