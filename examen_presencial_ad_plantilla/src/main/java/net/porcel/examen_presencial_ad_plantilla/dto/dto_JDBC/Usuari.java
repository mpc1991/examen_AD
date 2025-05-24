
package net.porcel.examen_presencial_ad_plantilla.dto.dto_JDBC;

import java.util.Objects;

public class Usuari {
    int id;
    String nom;
    String llinatges;
    int edad;

    public Usuari(int id, String nom, String llinatges, int edad) {
        this.id = id;
        this.nom = nom;
        this.llinatges = llinatges;
        this.edad = edad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLlinatges() {
        return llinatges;
    }

    public void setLlinatges(String llinatges) {
        this.llinatges = llinatges;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    
   
}
