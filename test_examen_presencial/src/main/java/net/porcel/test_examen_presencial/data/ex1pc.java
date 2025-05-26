package net.porcel.test_examen_presencial.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.porcel.test_examen_presencial.dto.Llibre;

/**
 * Escriu un mètode que rebi una cadena amb el nom d’un departament i torni tots
 * els llibres d’aquest departament. De cada llibre es interessa tenir
 * l’identificador, el títol, el departament i la col·lecció. Utilitza JDBC per
 * fer-ho.
 */
public class ex1pc {

    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://daw.paucasesnovescifp.cat:3306/biblioteca?user=usuari&password=seCret_24";
        Connection con = DriverManager.getConnection(url);
        return con;
    }

    public List<Llibre> getDepartment(String departament) {
            
        List<Llibre> llibres = new ArrayList<>();
        try (Connection con = getConnection();) {
            String sql = "SELECT * FROM llibres WHERE departament = ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1,departament);
            
            ResultSet rs = pst.executeQuery();
            // l’identificador, el títol, el departament i la col·lecció. Utilitza JDBC per
            while (rs.next()) {
                int id = rs.getInt(1);
                String titol = rs.getString(2);
                int anyEdifici = rs.getInt(3);
                String dep = rs.getString(4);
                String coleccio = rs.getString(5);
                
                Llibre llibre = new Llibre(id, titol,anyEdifici, coleccio, dep);
                llibres.add(llibre);
            }
            
        } catch (SQLException e) {
        }
        return llibres;
    }
}
