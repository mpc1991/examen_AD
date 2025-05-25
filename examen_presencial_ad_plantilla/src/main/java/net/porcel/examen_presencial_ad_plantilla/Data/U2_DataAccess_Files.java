package net.porcel.examen_presencial_ad_plantilla.Data;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path; // <-- Este es el correcto!
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.List;
import net.porcel.examen_presencial_ad_plantilla.Auxiliars.PersException;
import net.porcel.examen_presencial_ad_plantilla.dto.dto_Files.Llibre;
import net.porcel.examen_presencial_ad_plantilla.dto.dto_JDBC.Usuari;

public class U2_DataAccess_Files {
    // Directorio
    public static List<String> getContentDirectory(Path p) throws PersException {
        List<String> fitxers = new ArrayList<>();

        // Verificar si la ruta es un directorio.
        if (!Files.isDirectory(p)) {
            throw new PersException("La ruta debe ser un directorio");
        }

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(p)) {
            for (Path path : ds) {
                // Obtener todos los elementos en una ruta

                if (Files.isDirectory(path)) {
                    fitxers.add("\nDirectori");
                    fitxers.add("nom" + path.getFileName().toString());
                } else {
                    fitxers.add("\nFitxer");
                    fitxers.add("Nom: " + path.getFileName().toString());

                    // verificamos los permisos del archivo.
                    if (Files.isReadable(path)) {
                        fitxers.add("És de lectura");
                    }
                    if (Files.isWritable(path)) {
                        fitxers.add("És de escritura");
                    }
                    if (Files.isExecutable(path)) {
                        fitxers.add("És executable");
                    }

                    // Es oculto?
                    if (Files.isHidden(path)) {
                        fitxers.add("És ocult.");
                    } else {
                        fitxers.add("No és ocult");
                    }

                    // Propietario
                    UserPrincipal fileOwner = Files.getOwner(path);
                    fitxers.add("Owner: " + fileOwner.getName());

                    // Dada darrera modificació
                    fitxers.add("Darrera modificació: " + Files.getLastModifiedTime(path).toString());
                }
            }
        } catch (IOException e) {
            throw new PersException("Error: error al obtener datos de los elementos del directorio");
        }
        return fitxers;
    }
    
    // DataStreams
    public static void setLlistaUsuarisDataStreams(List<Usuari> usuaris, Path p) throws PersException {
        
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(p.toFile())))){
            for (Usuari usuari : usuaris) {
                dos.writeInt(usuari.getId());
                dos.writeUTF(usuari.getNom());
                dos.writeUTF(usuari.getLlinatges());
                dos.writeInt(usuari.getEdad());
            }
        } catch (Exception e) {
            throw new PersException("Error: escritura en el archivo fallida");
        }
    }
    public static ArrayList<Usuari> getLlistaUsuarisDataStreams(Path p) throws PersException, FileNotFoundException{
        ArrayList<Usuari> usuaris = new ArrayList<>();
        
        FileInputStream fi = new FileInputStream(p.toFile());
        BufferedInputStream bis = new BufferedInputStream(fi);
        
        try (DataInputStream di = new DataInputStream(bis)) {
            while(true) {
                int id = di.readInt();
                String nom = di.readUTF();
                String llinatges = di.readUTF();
                int edad = di.readInt();
                
                Usuari usuari = new Usuari(id, nom, llinatges, edad);
                usuaris.add(usuari);
            }
        } catch (EOFException e) {
        } catch (Exception e) {
            throw new PersException("Error al recuperar les dades de l'archiu");
        }    
        return usuaris;
    }
    
    // ObjectStreams
    public static void setLlistaModulsObjectStreams(List<Usuari> usuaris, Path p) throws PersException, FileNotFoundException{
        
        FileOutputStream fos = new FileOutputStream(p.toFile());
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            // Escribimos la lista entera
            oos.writeObject(usuaris);
        } catch (IOException e) {
            throw new PersException("Error: exritura errónea");
        }
    }
    public static List<Usuari> getLlistaModulsObjectStreams(Path p) throws FileNotFoundException, PersException, IOException{
        List<Usuari> usuaris = new ArrayList<>();
        
        FileInputStream fis = new FileInputStream(p.toFile());
        BufferedInputStream bis = new BufferedInputStream(fis);
        
        try (ObjectInputStream ois = new ObjectInputStream(bis)){
                usuaris = (List<Usuari>)ois.readObject();
        } catch (Exception e) { 
            throw new PersException ("Error: lectura del archivo incorrecta");
        }
        return usuaris;
    }
    
    // JSON
    public static <T> T getJson(Path p, Class<T> classe) throws IOException, PersException{
        ObjectMapper objectMapper = new ObjectMapper();
        T t = null;
        
        try {
            t = objectMapper.readValue(p.toFile(), classe);
        } catch (IOException e) {
            throw new PersException("Error: al leer el json");
        }
        return t;
    } 
    public static <T> void copyJson(Path origen, Path desti, Class<T> classe)throws PersException{
        ObjectMapper objectMapper = new ObjectMapper();
        
        try {
            Object jsonOrigen = objectMapper.readValue(origen.toFile(),classe);
            objectMapper.writeValue(desti.toFile(), jsonOrigen);
        } catch (Exception e) {
            throw new PersException("Error al copiar el JSON.");
        }
    }
    public static <T> void writeJson(Path p, Llibre llibre) throws IOException{
        ObjectMapper om = new ObjectMapper();
        om.writeValue(p.toFile(), llibre);
    }
}
