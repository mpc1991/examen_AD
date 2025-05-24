package net.porcel.examen_presencial_ad_plantilla.Data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import net.porcel.examen_presencial_ad_plantilla.Auxiliars.PersException;
import net.porcel.examen_presencial_ad_plantilla.dto.dto_ORN.Aspirant;
import net.porcel.examen_presencial_ad_plantilla.dto.dto_ORN.Illa;
import net.porcel.examen_presencial_ad_plantilla.dto.dto_ORN.Localitat;

// Persistencia en:  examen_presencial_ad_plantilla\src\main\resources\META-INF\persistence.xml
public class U4_DataAccess_ORN { // Persistencia.java // BBDD MySQL // JPA/Hibernate
    private final EntityManagerFactory emf;
    
    public U4_DataAccess_ORN(String unitatPersistencia) throws PersException {
        if (unitatPersistencia != null && !unitatPersistencia.trim().isEmpty()) {
            this.emf = Persistence.createEntityManagerFactory(unitatPersistencia);
        } else {
            throw new PersException("La unidad de persistencia no pot ser nulla o buida");
        }
    }
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    // SELECT
    public List<Aspirant> getAspirants() throws Exception{
        EntityManager em = getEntityManager();
        List<Aspirant> aspirants = new ArrayList<>();
        try {
            TypedQuery<Aspirant> query = em.createQuery("SELECT a FROM Aspirant a", Aspirant.class);
            aspirants = query.getResultList();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            em.close();
        }
        return aspirants;
    }
    public Aspirant getAspirantByNom(String nom) throws Exception{
        EntityManager em = getEntityManager();
        Aspirant aspirant = null;
        try {
            TypedQuery<Aspirant> query = em.createQuery("SELECT a FROM Aspirant a WHERE a.nom = :nom", Aspirant.class);
            query.setParameter("nom", nom);
            query.setMaxResults(1);
            aspirant = query.getSingleResult();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            em.close();
        }
        return aspirant;
    }
    public Aspirant getAspirantByNIF(String nif) throws Exception{
        EntityManager em = getEntityManager();
        Aspirant aspirant = null;
        try {
            aspirant = em.find(Aspirant.class, nif);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            em.close();
        }
        return aspirant;
    }
    
    // NAMED QUERY
    public List<String> getNifs(){
        EntityManager em = getEntityManager();
        List<String> aspirantsNIFs = new ArrayList<>();
        aspirantsNIFs = em.createNamedQuery("Aspirant.getNifs", String.class).getResultList();
        
        em.close();
        return aspirantsNIFs;
    }
    // PERSIST/INSERT
    public void addUser() throws Exception{
        EntityManager em = getEntityManager();
        String nif = "mpcNIF";
        String nom = "mpc";
        String llinatges = "pc";
        String adreca = "aqui";
        String codiPostal = "123";
        Illa illa = new Illa("50", "Malloraca");
        Localitat localitat = new Localitat("Llucmajor", illa, codiPostal);
        Aspirant aspirant = new Aspirant(nif, nom, llinatges, adreca, codiPostal, localitat);
        
        em.getTransaction().begin(); //iniciar
        em.persist(illa);
        em.persist(localitat);
        em.persist(aspirant); // Persist/insert
        em.getTransaction().commit(); // Confirmar   
        em.close();
    }
    // MERGE/UPDATE
    public void updateUser(String nif, String nouNom) throws PersException{
        EntityManager em = getEntityManager();
        Aspirant aspirant = em.find(Aspirant.class, nif);
        
        em.getTransaction().begin();
        aspirant.setNom(nouNom);
        em.merge(aspirant);
        em.getTransaction().commit();
        
        em.close();
    }
    
    // REMOVE/DELETE
    public void deleteUser(String nif){
        EntityManager em = getEntityManager();
        Aspirant aspirant = em.find(Aspirant.class, nif);
        if (aspirant != null) {
            em.getTransaction().begin();
            em.remove(aspirant);
            em.getTransaction().commit();
        }
        em.close();
    }
    public void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
