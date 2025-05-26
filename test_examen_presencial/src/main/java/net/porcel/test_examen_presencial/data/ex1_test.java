package net.porcel.test_examen_presencial.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.porcel.test_examen_presencial.dto.Modul;

/**
 *
 *  Crea una classe amb un mètode per guardar les dades d'una llista de mòduls dins un fitxer utilitzant 
    DataStreams. La llista i la ruta han d'arribar per paràmetres. 
    Tens la classe Modul al final de l'enunciat.
    Nota: per crear un DataStream pots utilitzar 
    * 
    * DataOutputStream dos = new DataOutputStream(new FileOutputStream(ruta)
 */
public class ex1escrito_test {
    List<Modul> moduls = new ArrayList<>();
    
    public ex1escrito_test(){
        inicialize();
        String path = "c:/examenAD/log2.log";
        addModuls(moduls, path);
        
        List<Modul> modulsRecuperats = recuperaModuls( path);
        for (Modul modul : modulsRecuperats){
            System.out.println(modul.getNom());
            System.out.println(modul.getCodi());
            System.out.println(modul.getHores());
        }
    }
    
    public void inicialize(){
        Modul modul1 = new Modul("1","Macia",10);
        Modul modul2 = new Modul("2", "Lulas", 20);
        Modul modul3 = new Modul("3", "Nuri", 30);
        
        moduls.add(modul1);
        moduls.add(modul2);
        moduls.add(modul3);
    }
    
    public void addModuls(List<Modul> moduls, String path){
        //String path1 = "c:/examenAD/log.log";
        //Path path2 = Path.of("c:/examenAD/log.txt");
        
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
            for (Modul modul : moduls) {
                dos.writeUTF(modul.getNom());
                dos.writeUTF(modul.getCodi());
                dos.writeInt(modul.getHores());
            }
        } catch (Exception e) {
            System.out.println("Err " + e.getMessage());
        }
    }
    
    public List<Modul> recuperaModuls(String path){
        List<Modul> modulsRecuperats = new ArrayList<>();
        try(DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(path)))) {
            while (true) {
                String codi = in.readUTF();
                String nom = in.readUTF();
                int hores = in.readInt();

                Modul modulRecuperat = new Modul(codi, nom, hores);
                modulsRecuperats.add(modulRecuperat);
            }
        } catch (Exception e) {
            System.out.println("Err " + e.getMessage());
        }
        return modulsRecuperats;
    }    
}
