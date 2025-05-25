package net.porcel.examen_presencial_ad_plantilla.dto.dto_Files;

public class Llibre {
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

