package com.example.inmobiliariasinapirest.modelo;

import java.io.Serializable;
import java.util.Objects;

public class InmuebleEnviar implements Serializable {

    private int id;
    private String direccion;
    private Integer uso;
    private Integer tipo;
    private int cantidadDeAmbientes;
    private double precio;
    private Propietario propietario;
    //En falso significa que el innmueble no está disponible por alguna falla en el mismo.
    private boolean estado=true;
    private String image;

    public InmuebleEnviar(String direccion, Integer uso, Integer tipo, int cantidadDeAmbientes, double precio, String image) {
        this.direccion = direccion;
        this.uso = uso;
        this.tipo = tipo;
        this.cantidadDeAmbientes = cantidadDeAmbientes;
        this.precio = precio;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getUso() {
        return uso;
    }

    public void setUso(Integer uso) {
        this.uso = uso;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public int getCantidadDeAmbientes() {
        return cantidadDeAmbientes;
    }

    public void setCantidadDeAmbientes(int cantidadDeAmbientes) {
        this.cantidadDeAmbientes = cantidadDeAmbientes;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Inmueble{" +
                "idInmueble=" + id +
                ", direccion='" + direccion +
                "inumeble=" + image +'\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InmuebleEnviar inmueble = (InmuebleEnviar) o;
        return id == inmueble.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
