/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.porcel.examen_presencial_ad_plantilla.dto.dto_MongoDB;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author seek_
 */
public class Film {
    private int id;
    private String titol;
    private String descripcio;
    private LocalDate fecha;
    private int durada;

    public Film() {
    }

    public Film(int id, String titol, String descripcio, LocalDate fecha, int durada) {
        this.id = id;
        this.titol = titol;
        this.descripcio = descripcio;
        this.fecha = fecha;
        this.durada = durada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getDurada() {
        return durada;
    }

    public void setDurada(int durada) {
        this.durada = durada;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.titol);
        hash = 59 * hash + Objects.hashCode(this.descripcio);
        hash = 59 * hash + Objects.hashCode(this.fecha);
        hash = 59 * hash + Objects.hashCode(this.durada);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Film other = (Film) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.titol, other.titol)) {
            return false;
        }
        if (!Objects.equals(this.descripcio, other.descripcio)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return Objects.equals(this.durada, other.durada);
    }

    @Override
    public String toString() {
        return "Titol: "+ titol + "(" + id + ") - Descripció: " + descripcio;
    }
}
