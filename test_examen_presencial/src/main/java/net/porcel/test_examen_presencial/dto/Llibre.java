/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.porcel.test_examen_presencial.dto;

import java.io.Serializable;

public class Llibre implements Serializable{
    private Integer id;
    private String titol;
    private Integer anyEdicio;
    private String colleccio;
    private String departament;

    public Llibre(Integer id, String titol, Integer anyEdicio, String colleccio, String departament){
        this.setId(id);
        this.setTitol(titol);
        this.setAnyEdicio(anyEdicio);
        this.setColleccio(colleccio);
        this.setDepartament(departament);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id)  {
        this.id = id;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol){
         this.titol = titol;
    }

    public Integer getAnyEdicio() {
        return anyEdicio;
    }

    public void setAnyEdicio(Integer anyEdicio)  {
        this.anyEdicio = anyEdicio;
    }

    public String getColleccio() {
        return colleccio;
    }

    public void setColleccio(String colleccio){
       this.colleccio = colleccio;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament)   {
       this.departament = departament;
    }

    @Override
    public String toString() {
        return "Llibre{" +
                "id=" + id +
                ", titol='" + titol + '\'' +
                ", anyEdicio=" + anyEdicio +
                ", colleccio='" + colleccio + '\'' +
                ", departament='" + departament + '\'' +
                '}';
    }
}
