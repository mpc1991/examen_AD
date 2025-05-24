package net.porcel.examen_presencial_ad_plantilla;

import java.util.List;
import net.porcel.examen_presencial_ad_plantilla.Data.U4_DataAccess_ORN;
import net.porcel.examen_presencial_ad_plantilla.Auxiliars.PersException;
import net.porcel.examen_presencial_ad_plantilla.Data.U6_DataAccess_MongoDB;
import net.porcel.examen_presencial_ad_plantilla.dto.dto_MongoDB.Categoria;
import net.porcel.examen_presencial_ad_plantilla.dto.dto_ORN.Aspirant;

public class Examen_presencial_ad_plantilla {
    public static void main(String[] args) throws PersException, Exception {
        MongoDB();
        
    }
    
    public static void ORM() throws PersException, Exception{
        //ORM
        U4_DataAccess_ORN persistencia = new U4_DataAccess_ORN("Biblioteca-PU");
        
        List<Aspirant> aspirants = persistencia.getAspirants();
        for (Aspirant aspirant : aspirants) {
            String nom = aspirant.getNom();
            System.out.println("Aspirant: " + aspirant);
        }
        Aspirant aspirantByNom = persistencia.getAspirantByNom("Bartomeu");
        System.out.println("getAspirantByNom: " + aspirantByNom);
        
        Aspirant aspirantByNIF = persistencia.getAspirantByNIF("mpcNIF");
        System.out.println("aspirantByNIF: " + aspirantByNIF);
        
        List<String> aspirantsNIFs = persistencia.getNifs();
        for (String aspirantsNIF : aspirantsNIFs){
            System.out.println("NamedQuery: " + aspirantsNIF);
        } 
        
        persistencia.addUser();
        persistencia.updateUser("mpcNIF", "MAXI");
        persistencia.deleteUser("mpcNIF");
        persistencia.close();
    }
    
    public static void MongoDB() throws PersException{
        U6_DataAccess_MongoDB dataAccess = new U6_DataAccess_MongoDB();
        List<Categoria> categorias = dataAccess.getCategoriesBSON();
        for (Categoria categoria : categorias) {
            System.out.println(categoria);
        }
        //dataAccess.getActors();
        //dataAccess.getFilmsByActorAndCategory(null,null);
        int id = 1233;
        String valor = "valorMPC";
        Categoria categoria = new Categoria(id, valor);
        //dataAccess.addCategory(categoria);
        //dataAccess.updateCategoryById(categoria, "valorMPCMODIFICAT");
        //dataAccess.deleteCategory(categoria);
    }
}
