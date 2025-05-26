package net.porcel.test_examen_presencial.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import net.porcel.test_examen_presencial.dto.Llibre;

/**
 * Ha de llegir i tornar un objecte json guardat dins un fitxer. El mètode
 * hauria de rebre per paràmetre la ruta del fitxer i la classe de l'objecte que
 * conté
 *
 * Ha d'escriure el json de l'objecte que rep per paràmetre dins el fitxer que
 * digui la ruta que rep per paràmetre.
 */
public class ex4pc {

    public void llegirPathToFile(Path p, Class<Llibre> llibre) throws IOException {
        ObjectMapper om = new ObjectMapper();
        Llibre recuperat = om.readValue(p.toFile(), llibre);
        System.out.println(recuperat);
    }
    public void llegirPathWithBuffer(String p, Class<Llibre> llibre) throws FileNotFoundException, IOException{
        ObjectMapper om = new ObjectMapper();
        BufferedReader in = new BufferedReader(new FileReader(p));
        Llibre llibreRecuperat = om.readValue(in, llibre); // específico
        //Object json = om.readValue(p, llibre); //generico
    }
    public void escriurePathToFile(Llibre llibre, Path p) throws IOException {
        ObjectMapper om = new ObjectMapper();
        om.writeValue(p.toFile(), llibre);
    }
}
