package net.porcel.examen_presencial_ad_plantilla.Data;

// 

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.porcel.examen_presencial_ad_plantilla.Auxiliars.PersException;
import net.porcel.examen_presencial_ad_plantilla.Auxiliars.U5_tractarValors_ORDB;
import net.porcel.examen_presencial_ad_plantilla.dto.dto_ORDB.Conection;
import net.porcel.examen_presencial_ad_plantilla.dto.dto_ORDB.Servidor;
import org.postgresql.util.PGobject;

public class U5_DataAccess_ORDB {
    
    public static Connection getConnection() throws PersException {
        Connection connection = null;
        Properties properties = new Properties();
        try {
            // Cargar las propiedades desde el archivo de configuración
            properties.load(U5_DataAccess_ORDB.class.getClassLoader().getResourceAsStream("properties/propietats.properties"));
            String connectionUrl = properties.getProperty("connectionUrl");

            // Establecer la conexión
            connection = DriverManager.getConnection(connectionUrl);
        } catch (Exception e) {
            throw new PersException("Error al obtener la conexión: " + e.getMessage());
        }
        return connection;
    }
    
    public static List<Servidor> getAllservers() throws PersException {
        List<Servidor> servidors = new ArrayList<>();

        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM \"ut5-practica\".servidors";
            PreparedStatement ps = con.prepareStatement(sql);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Campos de la BBDD
                    String codi = rs.getString("codi");
                    String descripcio = rs.getString("descripcio");
                    boolean servidorActiu = rs.getBoolean("servidor_actiu");

                    // Obtener  array de usuarios 
                    Array usuarisArray = rs.getArray("usuaris"); // retorna una referencia
                    String[] usuaris = new String[0]; // Inicializar vacío por seguridad
                    if (usuarisArray != null) {
                        usuaris = (String[]) usuarisArray.getArray();
                        usuarisArray.free();
                    }
                    
                    // Obtener la cadena del tipo compuesto (conexión)
                    PGobject conectionPGObject = (PGobject) rs.getObject("conection");
                    String conectionValue = conectionPGObject.getValue(); // Obtener el valor como texto

                    // Usar la clase TractarValors para procesar la cadena
                    String[] attributes = U5_tractarValors_ORDB.parseValue(conectionValue);

                    // Asignar los valores a variables
                    String sgdb = attributes[0];  // Primer atributo: sgdb
                    String host = attributes[1];  // Segundo atributo: host
                    int port = Integer.parseInt(attributes[2]);  // Tercer atributo: port  

                    // Crear el objeto Conection
                    Conection conection = new Conection(sgdb, host, port);

                    // Crear el objeto Servidor
                    Servidor servidor = new Servidor(codi, descripcio, usuaris, servidorActiu, conection);
                    servidors.add(servidor);

                    // Imprimir el servidor
                    //System.out.println(servidor.toString());
                }
            }
            return servidors;
        } catch (Exception e) {
            throw new PersException("Error al recuperar todos los servidores: " + e.getMessage());
        }
    }
    public static Servidor getServerById(String codiAObtenir) throws PersException {
        Servidor servidor = null;

        try (Connection con = getConnection()) {
            String sql = """
                         SELECT * 
                         FROM \"ut5-practica\".servidors
                         WHERE codi = ?
                         """;

            PreparedStatement ps = con.prepareStatement(sql); // Prepara la consulta con el interrogante
            ps.setString(1, codiAObtenir); // Cambia el primer interrogante por codiAObtenir

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Campos de la BBDD
                    String codi = rs.getString("codi");
                    String descripcio = rs.getString("descripcio");
                    boolean servidorActiu = rs.getBoolean("servidor_actiu");

                    // Obtener Array de usuarios
                    Array usuarisArray = rs.getArray("usuaris");
                    String[] usuaris = (String[]) usuarisArray.getArray();
                    usuarisArray.free();

                    //Obtener tipo compuesto
                    PGobject pgObject = (PGobject) rs.getObject("conection");
                    String conectionValue = pgObject.getValue();
                    String[] attributes = U5_tractarValors_ORDB.parseValue(conectionValue); // procesar la cadena

                    String sgdb = attributes[0];
                    String host = attributes[1];
                    int port = Integer.parseInt(attributes[2]);
                    
                    // Crear clase Conection
                    Conection conection = new Conection(sgdb, host, port);
                    // Crear clase Servidor
                    servidor = new Servidor(codi, descripcio, usuaris, servidorActiu, conection);
                }
            }
            return servidor;
        } catch (Exception e) {
            throw new PersException("Error al obtener el servidor: " + e.getMessage());
        }
    }

    public static void addServidor(Servidor servidor) throws PersException {
        try (Connection con = getConnection()) {
            String sql = """
                         INSERT INTO \"ut5-practica\".servidors
                         VALUES (?,?,?,?,ROW(?,?,?))
                         """;
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, servidor.getCodi());
            ps.setString(2, servidor.getDescripcio());
            Array usuarisArray = con.createArrayOf("text", servidor.getUsuaris());
            ps.setArray(3, usuarisArray);
            ps.setBoolean(4, servidor.isServidoractiu());
            ps.setString(5, servidor.getConection().getSgdb());
            ps.setString(6, servidor.getConection().getHost());
            ps.setInt(7, servidor.getConection().getPort());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new PersException("Error al insertar nuevo servidor" + e.getMessage());
        }
    }
    public static int deleteUserByNif(String codiServidor, String NIF) throws PersException, SQLException {
        int modificaciones = 0;
        String sql = """ 
                     UPDATE \"ut5-practica\".servidors
                     SET usuaris = array_remove(usuaris,?)
                     WHERE codi = ?
                     """;

        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, NIF);
            ps.setString(2, codiServidor);

            modificaciones = ps.executeUpdate();
        } catch (Exception e) {
            throw new PersException("Error al insertar los datos" + e.getMessage());
        }
        return modificaciones;
    }
    public static int updateConectionData(String sgdb, String host, int port, String codi) throws PersException, SQLException {
        int modificaciones = 0;
        String sql = """
                     UPDATE \"ut5-practica\".servidors
                     SET conection = ROW(?,?,?)
                     WHERE codi = ?
                     """;
        try (Connection con = getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sgdb);
            ps.setString(2, host);
            ps.setInt(3, port);
            ps.setString(4, codi);

            modificaciones = ps.executeUpdate();
        } catch (Exception e) {
            throw new PersException("Error al modificar los datos: " + e.getMessage());
        }
        return modificaciones;
    }
}
