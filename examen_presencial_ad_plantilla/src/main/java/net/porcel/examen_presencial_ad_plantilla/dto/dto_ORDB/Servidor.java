package net.porcel.examen_presencial_ad_plantilla.dto.dto_ORDB;

import java.util.Arrays;
import java.util.List;
import net.porcel.examen_presencial_ad_plantilla.Auxiliars.PersException;

public class Servidor {
    private String codi;
    private String descripcio;
    private String[] usuaris;
    private boolean servidoractiu = false;
    private Conection conection;

    public Servidor(String codi, String descripcio, String[] usuaris, boolean servidoractiu, Conection conection) throws PersException {
        setCodi(codi);
        setDescripcio(descripcio);
        setUsuaris(usuaris);
        setServidoractiu(servidoractiu);
        setConection(conection);
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) throws PersException {
        if (codi != null && !codi.isEmpty()) {
            this.codi = codi;
        } else {
            throw new PersException("El código está vacío");
        }
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) throws PersException {
        if (descripcio != null && !descripcio.isEmpty()) {
            this.descripcio = descripcio;
        } else {
            throw new PersException("La descripción está vacío");
        }
    }

    public String[] getUsuaris() {
        return usuaris;
    }

    public void setUsuaris(String[] usuaris) throws PersException {
        if (usuaris.length > 0) {
            this.usuaris = usuaris;
        } else {
            throw new PersException("La lista de usuarios está vacía");
        }
    }

    public boolean isServidoractiu() {
        return servidoractiu;
    }

    public void setServidoractiu(boolean servidoractiu) {
        this.servidoractiu = servidoractiu;
    }

    public Conection getConection() {
        return conection;
    }

    public void setConection(Conection conection) throws PersException {
        if (conection != null) {
            this.conection = conection;
        } else {
            throw new PersException("La conexión no puede ser null");
        }
    }

    @Override
    public String toString() {
        return "Servidor{" + 
                "codi=" + codi + 
                ", descripcio=" + descripcio + 
                ", usuaris=" + Arrays.toString(usuaris) + 
                ", servidoractiu=" + servidoractiu + 
                ", conection=" + conection + '}';
    }
}
