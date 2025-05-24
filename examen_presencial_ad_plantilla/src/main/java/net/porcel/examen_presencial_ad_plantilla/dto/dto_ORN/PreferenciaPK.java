package net.porcel.examen_presencial_ad_plantilla.dto.dto_ORN;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class PreferenciaPK implements Serializable {
    private String nif;
    private int ordre;

    public PreferenciaPK(String nif, int ordre) {
        this.nif = nif;
        this.ordre = ordre;
    }

    public PreferenciaPK() {
        this(null, -1);
    }

    @Column(name = "nif", nullable = false, length = 9)
    @Id
    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    @Column(name = "ordre", nullable = false)
    @Id
    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreferenciaPK that = (PreferenciaPK) o;
        return ordre == that.ordre &&
                Objects.equals(nif, that.nif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nif, ordre);
    }
}
