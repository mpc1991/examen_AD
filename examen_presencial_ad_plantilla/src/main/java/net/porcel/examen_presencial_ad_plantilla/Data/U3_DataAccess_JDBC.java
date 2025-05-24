/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.porcel.examen_presencial_ad_plantilla.Data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import net.porcel.examen_presencial_ad_plantilla.Auxiliars.PersException;
import net.porcel.examen_presencial_ad_plantilla.dto.dto_JDBC.Usuari;

public class U3_DataAccess_JDBC { // BBDD MySQL.
    private List<Usuari> buffer = new ArrayList<>();
    
    // Metodo para realizar la conexion a la BBDD
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://daw.paucasesnovescifp.cat:3306/biblioteca?user=usuari&password=seCret_24";
        Connection con = DriverManager.getConnection(url);
        return con;
    }
    
    public void addUsuari2Buffer(Usuari usuariAVerificar) throws PersException {
        boolean existeix = false;

        for (Usuari usuari : buffer) {
            if (usuari.equals(usuariAVerificar)) {
                existeix = true; // Si existe ponemos la flag en true
            }
        }

        if (existeix == false) { // Si la flag sigue en false al llegar a este punto, añadimos el editor a la lista
            buffer.add(usuariAVerificar);
        } else {
            throw new PersException("l'Editor ja existeix al buffer"); // Si la flag esta en false, lanzamos excepción personalizada
        }
    }
    public void removeUsuariFromBuffer(Usuari usuariAEliminar) throws PersException {
        if (buffer.contains(usuariAEliminar)) {
            buffer.remove(usuariAEliminar);
        } else {
            throw new PersException("l'Editor no existeix al buffer");
        }
    }
    public void clearBuffer() {
        buffer.clear();
    }
    
    public List<Usuari> getAllEditors() throws PersException, SQLException {
        List<Usuari> usuaris = new ArrayList<>();
        try (Connection con = getConnection();) {
            String sql = "SELECT * FROM EDITORS";
            
            //Statement st = con.createStatement();
            //ResultSet rs = st.executeQuery(sql);
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt(1);
                String nom = rs.getString(2);
                String llinatges = rs.getString(3);
                Usuari usuari = new Usuari(id, nom, llinatges, 10);
                usuaris.add(usuari);
            }
            return usuaris;
        } catch (SQLException e) {
            throw new PersException(e.getMessage());
        }
    }
    public void addUsuaris(List<Usuari> usuaris) throws PersException {
        String sql = "INSERT INTO usuaris (ID_USUARI, NOM_USUARI, LLINATGES_USUARI) VALUES (?, ?, ?) ";

        try (Connection con = getConnection()) {
            con.setAutoCommit(false);
            PreparedStatement pst = con.prepareStatement(sql);
            
            CallableStatement cs = con.prepareCall("{Call get_next_id_edit(?)}"); // método de la BBDD para obtener el siguiente ID disponible.
            cs.registerOutParameter(1, Types.INTEGER);

            try {
                for (Usuari usuari : usuaris) {
                    cs.execute();
                    int nextId = cs.getInt(1);  // Obtener el siguiente id disponible
                    usuari.setId(nextId); // Machacamos el id del editor con el primero disponible

                    pst.setInt(1, usuari.getId());
                    pst.setString(2, usuari.getNom());
                    pst.setString(3, usuari.getLlinatges());
                    pst.executeUpdate();
                }
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new PersException("Error al insertar los editores: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new PersException(e.getMessage());
        } finally {
            usuaris.clear(); // vaciamos el buffer
        }
    }
    /* 
    *  Recibiendo el ID, eliminamos el editor de la BBDD.
    *  Para poder eliminarlo, el método primero pondrá la FK de la tabla Llibres en null.
    */
    public void deleteUsuariById(int id) throws PersException {
        String sqlLlibres = "UPDATE LLIBRES SET FK_IDEDIT = null WHERE FK_IDEDIT = ?";
        String sqlEditors = "DELETE FROM EDITORS WHERE ID_EDIT = ?";
        try (Connection con = getConnection();) {
            con.setAutoCommit(false);
            try {
                PreparedStatement pstLlibres = con.prepareStatement(sqlLlibres);
                pstLlibres.setInt(1, id);
                int i = pstLlibres.executeUpdate();

                PreparedStatement pstEditors = con.prepareStatement(sqlEditors);
                pstEditors.setInt(1, id);
                int e = pstEditors.executeUpdate();

                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new PersException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new PersException(e.getMessage());
        }
    }
}
