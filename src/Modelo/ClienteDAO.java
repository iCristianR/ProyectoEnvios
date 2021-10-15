
package Modelo;

import java.awt.Event;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * la clase ClienteDAO permitira generar metodos por
 * los cuales se hace uso de sentencias SQL
 * @author Cristian Romero y Santiago Gonzales
 */
public class ClienteDAO {
    
    /**
     * Este metodo permite realizar el llenado de un DefaultTableModel
     * mediante la tabla clientes ya creada en una base de datos
     * @return DefaultTableModel
     */
    public DefaultTableModel consulta(){
        DefaultTableModel plantilla = new DefaultTableModel();
        ConexionBD objCon= new ConexionBD();
        try {
            objCon.conectar();
            Statement consulta = objCon.getConexion().createStatement(); //bloque de contenedor 
            ResultSet datos= consulta.executeQuery("select * from clientes"); // ejecuta una consulta
            ResultSetMetaData campos= datos.getMetaData(); // transformacion de datos
            for (int i = 1; i <= campos.getColumnCount(); i++){
                plantilla.addColumn(campos.getColumnName(i));           
            }
            while (datos.next()){
                Object[] fila = new Object[campos.getColumnCount()];
                for (int i = 0; i < campos.getColumnCount(); i++){
                    fila[i]=datos.getObject(i+1);
                }
                plantilla.addRow(fila);
            }
            datos.close();
            objCon.getConexion().close();
            } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: "+ex);
        }
      return plantilla;
    }
    
    /**
     * Este metodo permite realizar la insercion de un campo
     * en una tabla llamada cliente, contenida en una base de 
     * datos
     * @param objC de tipo Object
     * @return String
     */
    public String insertar(Cliente objC){
        String mensaje=""; 
        try {
            ConexionBD conexion=new ConexionBD();
            PreparedStatement consulta = null;
            conexion.conectar();
            String comando= "insert into clientes values(?,?,?)";
            consulta=conexion.getConexion().prepareStatement(comando);
            consulta.setString(1,objC.getId());
            consulta.setString(2,objC.getNom());
            consulta.setString(3,objC.getTel());
            consulta.execute();
            mensaje="Registro BD exitoso...";
            consulta.close();
            conexion.getConexion().close();
        } catch (SQLException ex) {
           mensaje="Error al intentar insertar en BD...\n"+ex;
        }
      return mensaje;  
    }
    
    /**
     * Este metodo permite realizar la actualizacion de un campo 
     * en especifico, en la tabla clientes de una base de datos
     * @param objC de tipo Object
     * @return int
     */
    public int actualizar(Cliente objC){
            ConexionBD objCon=new ConexionBD();
            objCon.conectar();
            int val=0;
        try{
            String SQL = "UPDATE clientes SET nombre=?,telefono=? WHERE identificacion=?";
            PreparedStatement pst = objCon.getConexion().prepareStatement(SQL);        
            pst.setString(1,objC.getNom());
            pst.setString(2,objC.getTel());
            pst.setString(3,objC.getId());
            pst.execute();
            val=pst.executeUpdate();
            pst.close();
            objCon.getConexion().close();
            
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error: "+e.getMessage());
        }
        return val;
    }
    
    /**
     * Este metodo permite realizar la busqueda de un campo
     * contenido en la tabla clientes de una base de datos
     * @param id de tipo String
     * @return String
     */
    public String buscarDato(String id){
        ConexionBD objCon= new ConexionBD();
        objCon.conectar();
        String registros="CLIENTE: \n";
               
        String SQL="select * from clientes where identificacion like '%"+id+"%'";
        try{
            
            Statement st = (Statement) objCon.getConexion().createStatement();
            ResultSet rs = st.executeQuery(SQL);
            
            while(rs.next()){
                registros ="Identificacion: "+ rs.getString("identificacion")+";"
                          +"\nNombre: "+ rs.getString("nombre")+";"
                          +"\nTelefono: "+ rs.getString("telefono")+"\n";
            }       
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error al mostrar datos"+e.getMessage());
        }
        return registros;
    }

    /**
     * Este metodo permite realizar la eliminacion de un campo en especifico,
     * contenido en la tabla clientes de una base de datos
     * @param id de tipo String
     */
    public void eliminar(String id){
        ConexionBD objCon=new ConexionBD();
        objCon.conectar();
        try{
            String SQL = "delete from clientes where identificacion=?";
            PreparedStatement pst = objCon.getConexion().prepareStatement(SQL);
            pst.setString(1,id);
            pst.execute();            
            pst.close();
            objCon.getConexion().close();
        }catch (Exception e){
            
        }
    }
}
