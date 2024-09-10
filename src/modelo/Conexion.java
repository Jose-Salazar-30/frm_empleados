package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public final String puerto = "3306";
    public final String bd = "db_empleados";
    public final String urlConexion = String.format("jdbc:mysql://localhost:%s/%s?serverTimezone=UTC", puerto, bd);
    public final String usuario = "root";
    public final String contra = "123456";
    public final String jdbc = "com.mysql.cj.jdbc.Driver";
    public Connection conexionBD;
    
    public void abrirConexion() {
        try {
            Class.forName(jdbc);
            conexionBD = DriverManager.getConnection(urlConexion, usuario, contra);
            System.out.println("Conexion Exitosa");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Algo salio mal" + ex.getMessage());
        }
    }
    
    public void cerrarConexion(){
        try {
            conexionBD.close();
        } catch (SQLException ex) {
            System.out.println("Algo salio mal" + ex.getMessage());
        }
    }
    
}
