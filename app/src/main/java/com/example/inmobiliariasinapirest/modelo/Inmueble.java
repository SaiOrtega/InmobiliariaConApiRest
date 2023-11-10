package com.example.inmobiliariasinapirest.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Inmueble implements Serializable {

    private int id;
    private String direccion;
    private String uso;
    private String tipo;
    private int cantidadDeAmbientes;
    private double precio;
    private Propietario propietario;
    //En falso significa que el innmueble no está disponible por alguna falla en el mismo.
    private boolean estado=true;
    private String image;

    public Inmueble(int idInmueble, String direccion, String uso, String tipo, int ambientes, double precio, Propietario propietario, boolean estado, String image) {
        this.id= idInmueble;
        this.direccion = direccion;
        this.uso = uso;
        this.tipo = tipo;
        this.cantidadDeAmbientes = ambientes;
        this.precio = precio;
        this.propietario = propietario;
        this.estado = estado;
        this.image = image;
    }
    public Inmueble() {

    }

    public Inmueble(String direccion, String uso, String tipo, int cantidadDeAmbientes, double precio, String image) {
        this.direccion = direccion;
        this.uso = uso;
        this.tipo = tipo;
        this.cantidadDeAmbientes = cantidadDeAmbientes;
        this.precio = precio;
        this.image = image;
    }

    public int getIdInmueble() {
        return id;
    }

    public void setIdInmueble(int idInmueble) {
        this.id = idInmueble;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAmbientes() {
        return cantidadDeAmbientes;
    }

    public void setAmbientes(int ambientes) {
        this.cantidadDeAmbientes = ambientes;
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
        Inmueble inmueble = (Inmueble) o;
        return id == inmueble.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}