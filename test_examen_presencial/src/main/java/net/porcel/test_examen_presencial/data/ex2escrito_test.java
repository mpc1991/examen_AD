package net.porcel.test_examen_presencial.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.porcel.test_examen_presencial.aux.TractarValors;
import net.porcel.test_examen_presencial.dto.TServidor;
import org.postgresql.util.PGobject;

/*
 En una aplicació Java que utilitza una base de dades PostgreSql, escriu el codi que utilitzaries per 
recuperar un camp d'un tipus definit per l'usuari. Suposem que el tipus de base de dades es diu 
TServidor i tenim una classe Java anomenada Servidor amb tot el necessari per utilitzar-se amb 
aquest tipus de dades de la base de dades.
*/
public class ex2escrito_test {
    
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://daw.paucasesnovescifp.cat:3306/biblioteca?user=usuari&password=seCret_24";
        Connection con = DriverManager.getConnection(url);
        return con;
    }
    
    public TServidor getServidor() throws SQLException{
        Connection con = getConnection();
        TServidor tservidor = null;
        String sql = "SELECT * from \"ut5-practica\".Servidor";
        
        PreparedStatement pst = con.prepareStatement(sql);
        try (ResultSet rs = pst.executeQuery()){
            while (rs.next()) {
                String nombreServidor = rs.getString("nombre_Servidor");
                
                //Tipo compuesto
                PGobject pgo = (PGobject) rs.getObject("TServidor");
                String tServidorValue = pgo.getValue();
                
                String[] att = TractarValors.parseValue(tServidorValue);
                int host = Integer.parseInt(att[0]);
                
                tservidor = new TServidor(host);
            }
        } catch (Exception e) {
        }
        return tservidor;
    }
}
