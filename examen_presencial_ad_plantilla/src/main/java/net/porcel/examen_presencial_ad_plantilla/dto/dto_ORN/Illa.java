/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.porcel.examen_presencial_ad_plantilla.dto.dto_ORN;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import net.porcel.examen_presencial_ad_plantilla.Auxiliars.PersException;
import org.hibernate.JDBCException;

/**
 *
 * @author seek_
 */
@Entity

@Table(name = "Illes")
public class Illa implements Serializable{
    @Id
    @Column(name = "idIlla", nullable = false)
    String illa;
    
    @Column(name = "nomIlla", nullable = false)
    String nomIlla;
    
    //@OneToMany(fetch = FetchType.LAZY)
    //@JoinColumn(name = "idIlla")
    @OneToMany(mappedBy = "idIlla", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Localitat> localitats;

    //private List<Localitat> localitats;
    
    public Illa() {
    }

    public Illa(String illa, String nomIlla) throws PersException {
        setIlla(illa);
        setNomIlla(nomIlla);
    }

    public String getIlla() {
        return illa;
    }

    public void setIlla(String illa) throws PersException {
        if (!illa.trim().isEmpty()) {
            if (illa != null) {
                this.illa = illa;
            } else {
                throw new PersException ("Illa no pot ser null");
            }
        } else {
            throw new PersException("Illa no pot estar buid");
        }
    }

    public String getNomIlla() {
        return nomIlla;
    } 

    public List<Localitat> getLocalitats() {
        return localitats;
    }

    public void setLocalitats(List<Localitat> localitats) {
        this.localitats = localitats;
    }

    public void setNomIlla(String nomIlla) throws PersException {
        if (!nomIlla.trim().isEmpty()) {
            if (nomIlla != null) {
                this.nomIlla = nomIlla;
            } else {
                throw new PersException ("nomIlla no pot ser null");
            }
        } else {
            throw new PersException("nomIlla no pot estar buid");
        }
        
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.illa);
        hash = 67 * hash + Objects.hashCode(this.nomIlla);
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
        final Illa other = (Illa) obj;
        if (!Objects.equals(this.illa, other.illa)) {
            return false;
        }
        return Objects.equals(this.nomIlla, other.nomIlla);
    }

    @Override
    public String toString() {
        return "Illa{" + "illa=" + illa + ", nomIlla=" + nomIlla + '}';
    }
}
