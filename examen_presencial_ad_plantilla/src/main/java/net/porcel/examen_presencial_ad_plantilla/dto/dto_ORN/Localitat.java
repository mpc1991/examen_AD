package net.porcel.examen_presencial_ad_plantilla.dto.dto_ORN;

import jakarta.persistence.*;

import java.util.Objects;
import net.porcel.examen_presencial_ad_plantilla.Auxiliars.PersException;

@Entity
@Table(name = "Localitats")
public class Localitat {
    @Id
    @Column(name = "idLocalitat", nullable = false, length = 9)
    private String idLocalitat;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idIlla", nullable = false)
    private Illa idIlla;

    @Column(name = "nomLocalitat", nullable = false, length = 100)
    private String nomLocalitat;

    public Localitat() {
    }

    public Localitat(String idLocalitat, Illa idIlla, String nomLocalitat) throws PersException {
        this.setIdLocalitat(idLocalitat);
        this.setIdIlla(idIlla);
        this.setNomLocalitat(nomLocalitat);
    }

    public String getIdLocalitat() {
        return idLocalitat;
    }

    public void setIdLocalitat(String idLocalitat) throws PersException {
        if (idLocalitat == null || idLocalitat.isBlank()) {
            throw new PersException("L'identificador no pot ser una cadena nul·la, buida o formada només per espais.");
        }
        this.idLocalitat = idLocalitat;
    }

    public Illa getIdIlla() {
        return idIlla;
    }

    public void setIdIlla(Illa idIlla) throws PersException {
        if (idIlla == null) {
            throw new PersException("La illa no pot ser nul·la.");
        }
        this.idIlla = idIlla;
    }

    public String getNomLocalitat() {
        return nomLocalitat;
    }

    public void setNomLocalitat(String nomLocalitat) throws PersException {
        if (nomLocalitat == null || nomLocalitat.isBlank()) {
            throw new PersException("El nom no pot ser una cadena nul·la, buida o formada només per espais.");
        }
        this.nomLocalitat = nomLocalitat;
    }

    @Override
    public String toString() {
        return "Localitat{" +
                "idLocalitat='" + getIdLocalitat() + '\'' +
                ", idIlla=" + getIdIlla() +
                ", nomLocalitat='" + getNomLocalitat() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Localitat localitat = (Localitat) o;
        return Objects.equals(getIdLocalitat(), localitat.getIdLocalitat()) && Objects.equals(getIdIlla(), localitat.getIdIlla()) && Objects.equals(getNomLocalitat(), localitat.getNomLocalitat());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdLocalitat(), getIdIlla(), getNomLocalitat());
    }
}