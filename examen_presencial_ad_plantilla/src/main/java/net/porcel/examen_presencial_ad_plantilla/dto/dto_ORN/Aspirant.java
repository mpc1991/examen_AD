package net.porcel.examen_presencial_ad_plantilla.dto.dto_ORN;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Objects;
import net.porcel.examen_presencial_ad_plantilla.Auxiliars.PersException;

@Entity
@Table(name = "Aspirants", schema = "interins")
@NamedQueries({@NamedQuery(name="Aspirant.findAll", query="SELECT a FROM Aspirant a"),
    @NamedQuery(name="Aspirant.getNifs",query = "SELECT a.nif FROM Aspirant a"),
    @NamedQuery(name = "Aspirant.getNomComplet",query = "SELECT a.nom, a.llinatges FROM Aspirant a ORDER BY a.llinatges, a.nom")})
public class Aspirant {
    private String nif;
    private String nom;
    private String llinatges;
    private String adreca;
    private String codiPostal;
    private Localitat localitat;
    private Collection<Preferencia> preferencies;

    public Aspirant(String nif, String nom, String llinatges, String adreca, String codiPostal, Localitat localitat) throws PersException {
        this.nif = nif;
        setNom(nom);
        setLlinatges(llinatges);
        this.adreca = adreca;
        this.codiPostal = codiPostal;
        this.localitat = localitat;
    }

    public Aspirant() throws PersException {
    }

    @Id
    @Column(name = "Nif", nullable = false, length = 9)
    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    @Basic
    @Column(name = "nom", nullable = false, length = 45)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) throws PersException {
        if(nom==null || nom.isBlank()){
            throw new PersException("El nom no potser null ni buid.");
        }
        this.nom = nom;
    }

    @Basic
    @Column(name = "llinatges", nullable = false, length = 45)
    public String getLlinatges() {
        return llinatges;
    }

    public void setLlinatges(String llinatges) throws PersException {
        if(llinatges==null || llinatges.isBlank()){
            throw new PersException("Els llinatges no poden ser null ni buids.");
        }
        this.llinatges = llinatges;
    }

    @Basic
    @Column(name = "adreca", nullable = true, length = 100)
    public String getAdreca() {
        return adreca;
    }

    public void setAdreca(String adreca) {
        this.adreca = adreca;
    }

    @Basic
    @Column(name = "codiPostal", nullable = true, length = 5)
    public String getCodiPostal() {
        return codiPostal;
    }

    public void setCodiPostal(String codiPostal) {
        this.codiPostal = codiPostal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aspirant aspirant = (Aspirant) o;
        return Objects.equals(nif, aspirant.nif) &&
                Objects.equals(nom, aspirant.nom) &&
                Objects.equals(llinatges, aspirant.llinatges) &&
                Objects.equals(adreca, aspirant.adreca) &&
                Objects.equals(codiPostal, aspirant.codiPostal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nif, nom, llinatges, adreca, codiPostal);
    }

    @ManyToOne
    @JoinColumn(name = "idLocalitat", referencedColumnName = "idLocalitat")
    public Localitat getLocalitat() {
        return localitat;
    }

    public void setLocalitat(Localitat localitat) {
        this.localitat = localitat;
    }

    @OneToMany(mappedBy = "aspirant")
    public Collection<Preferencia> getPreferencies() {
        return preferencies;
    }

    public void setPreferencies(Collection<Preferencia> preferencies) {
        this.preferencies = preferencies;
    }

    @Override
    public String toString() {
        return "Aspirant{" +
                "nif='" + nif + '\'' +
                ", nom='" + nom + '\'' +
                ", llinatges='" + llinatges + '\'' +
                ", adreca='" + adreca + '\'' +
                ", codiPostal='" + codiPostal + '\'' +
                ", localitat=" + localitat +
                '}';
    }
}
