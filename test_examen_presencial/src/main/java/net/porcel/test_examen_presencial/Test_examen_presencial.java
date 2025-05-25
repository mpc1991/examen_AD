/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package net.porcel.test_examen_presencial;

import java.io.IOException;
import java.nio.file.Path;
import net.porcel.test_examen_presencial.data.ex1escrito_test;
import net.porcel.test_examen_presencial.data.ex3pc;
import net.porcel.test_examen_presencial.data.ex4pc;
import net.porcel.test_examen_presencial.dto.Llibre;
import net.porcel.test_examen_presencial.dto.Servidor;
import net.porcel.test_examen_presencial.dto.TServidor;

public class Test_examen_presencial {

    public static void main(String[] args) throws IOException {
        //TServidor tsrv = new TServidor(100);
        //Servidor srv = new Servidor("Srv01", tsrv);   
        
        //ex3pc ex3 = new ex3pc();
        //ex3.getCategoria();
        
        ex4pc ex4 = new ex4pc();
        
        Llibre llibre = new Llibre(1, "titulazo", 2000, "premium", "yes");
        String path = "c:/examenad/log.json";
        Path p = Path.of(path);
        //ex4.escriurePathToFile(llibre, p);
        ex4.llegirPathWithBuffer(path, Llibre.class);
    }
}
