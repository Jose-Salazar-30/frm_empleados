package modelo;

import java.awt.HeadlessException;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
/**
 *
 * @author josej
 */
public class Empleado extends Persona{
    private int id, id_puesto;
    private String codigo;
    
    Conexion cn;

    public Empleado() {
    }

    public Empleado(String codigo, int id, int id_puesto, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento) {
        super(nombres, apellidos, direccion, telefono, fecha_nacimiento);
        this.id = id;
        this.codigo = codigo;
        this.id_puesto = id_puesto;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_puesto() {
        return id_puesto;
    }

    public void setId_puesto(int id_puesto) {
        this.id_puesto = id_puesto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    
    public DefaultTableModel leer(){
        DefaultTableModel tabla = new DefaultTableModel();
        
        try {
            cn = new Conexion();
            cn.abrirConexion();
            String query;
            query = "Select e.id_empleado as id, e.codigo, e.nombres, e.apellidos ,e.direccion ,e.telefono ,e.fecha_nacimiento ,concat(p.id_puesto,') ',p.puesto) as puesto FROM empleados as e inner join puestos as p on e.id_puesto = p.id_puesto;";
            ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
            
            String encabezado[] = {"id","Codigo","Nombres","Apellidos","Direccion","Telefono","Nacimiento","Puesto"};
            tabla.setColumnIdentifiers(encabezado);
            
            String datos[] = new String[8];
            
            while(consulta.next()) {
                datos[0] = consulta.getString("id");
                datos[1] = consulta.getString("codigo");
                datos[2] = consulta.getString("nombres");
                datos[3] = consulta.getString("apellidos");
                datos[4] = consulta.getString("direccion");
                datos[5] = consulta.getString("telefono");
                datos[6] = consulta.getString("fecha_nacimiento");
                datos[7] = consulta.getString("puesto");
                tabla.addRow(datos);
            }
            cn.cerrarConexion();
        
        } catch(SQLException ex) {
            cn.cerrarConexion();
            System.out.println("Error" + ex);
        }
        return tabla;
    }
    
    public DefaultComboBoxModel puesto(){
        DefaultComboBoxModel cmb = new DefaultComboBoxModel();
        try {
            cn = new Conexion();
            cn.abrirConexion();
            String query;
            query = "SELECT id_puesto as id, puesto from puestos";
            ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
            cmb.addElement("0. Seleccione Puesto");
            while (consulta.next()) {
                cmb.addElement(consulta.getString("id")+") "+consulta.getString("puesto"));
            }
            
            cn.cerrarConexion();
            
        } catch(SQLException ex) {
            System.out.println("Ha ocurrido un Error: " + ex.getMessage());
        }
        return cmb;
    }
    
    @Override
    public void agregar(){
        try {
            PreparedStatement parametro;
            cn = new Conexion();
            cn.abrirConexion();
            String query;
            query = "insert into empleados(codigo,nombres,apellidos,direccion,telefono,fecha_nacimiento,id_puesto) values (?,?,?,?,?,?,?);";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getCodigo());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            parametro.setInt(7, getId_puesto());
            
            int executar = parametro.executeUpdate();
            cn.cerrarConexion();
            
            JOptionPane.showMessageDialog(null, Integer.toString(executar) + " Registro Ingresado",
                  "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } catch(HeadlessException | SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    @Override
    public void actualizar() {
        try {
            PreparedStatement parametro;
            cn = new Conexion();
            cn.abrirConexion();
            String query;
            
            query = "update empleados set codigo =?, nombres = ?, apellidos = ?, direccion = ?, telefono = ?, fecha_nacimiento = ?, id_puesto = ? where id_empleado = ?"; 
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getCodigo());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            parametro.setInt(7, getId_puesto());
            parametro.setInt(8, getId());
            
            int executar = parametro.executeUpdate();
            cn.cerrarConexion();
            
            JOptionPane.showMessageDialog(null, Integer.toString(executar) + " Registro Actualizado",
                  "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } catch(HeadlessException | SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    @Override
    public void eliminar(){
        try {
            PreparedStatement parametro;
            cn = new Conexion();
            cn.abrirConexion();
            String query;
            query = "delete from empleados where id_empleado = ?";
            
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setInt(1, getId());
            
            int executar =  parametro.executeUpdate();
            cn.cerrarConexion();
            
            JOptionPane.showMessageDialog(null,Integer.toString(executar) + " Registro Eliminado",
                "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } catch(HeadlessException | SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
