
package Modelo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * la clase MercanciaDAO permitira generar metodos por
 * los cuales se hace uso de sentencias SQL
 * @author Cristian Romero y Santiago Gonzales
 */
public class MercanciaDAO {
    
    /**
     * Este metodo permite realizar el llenado de un DefaultTableModel
     * mediante la tabla mercancia ya creada en una base de datos
     * @return DefaultTableModel
     */
    public DefaultTableModel consulta(){
        DefaultTableModel plantilla = new DefaultTableModel();
        ConexionBD objCon= new ConexionBD();
        try {
            objCon.conectar();
            Statement consulta = objCon.getConexion().createStatement(); //bloque de contenedor 
            ResultSet datos= consulta.executeQuery("select * from mercancia"); // ejecuta una consulta
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
     * en una tabla llamada mercancia, contenida en una base de 
     * datos
     * @param objM de tipo Object
     * @return String
     */
    public String insertar(Mercancia objM){
        String mensaje=""; 
        try {
            ConexionBD conexion=new ConexionBD();
            PreparedStatement consulta = null;
            conexion.conectar();
            String comando= "insert into mercancia values(?,?,?)";
            consulta=conexion.getConexion().prepareStatement(comando);
            consulta.setString(1,objM.getCod());
            consulta.setString(2,objM.getDescr());
            consulta.setString(3,String.valueOf(objM.getKilos()));
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
     * Este metodo permite realizar la busqueda de un campo
     * contenido en la tabla mercancia de una base de datos
     * @param cod de tipo String
     * @return String
     */
    public String buscarDato(String cod){
        ConexionBD objCon= new ConexionBD();
        objCon.conectar();
        String registros="MERCANCIA: \n";
               
        String SQL="select * from mercancia where codigo like '%"+cod+"%'";
        try{
            
            Statement st = (Statement) objCon.getConexion().createStatement();
            ResultSet rs = st.executeQuery(SQL);
            
            while(rs.next()){
                registros ="Codigo: "+ rs.getString("codigo")+";"
                          +"\nDescripcion: "+ rs.getString("descripcion")+";"
                          +"\nKilos: "+ rs.getString("kilos")+"\n";
            }       
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error al mostrar datos"+e.getMessage());
        }
        return registros;
    }

    /**
     * Este metodo permite realizar la actualizacion de un campo 
     * en especifico, en la tabla mercancia de una base de datos
     * @param objM de tipo Object
     * @return int
     */
    public int actualizar(Mercancia objM){
            ConexionBD objCon=new ConexionBD();
            objCon.conectar();
            int val=0;
        try{
            String SQL = "UPDATE mercancia SET descripcion=?,kilos=? WHERE codigo=?";
            PreparedStatement pst = objCon.getConexion().prepareStatement(SQL);        
            pst.setString(1,objM.getDescr());
            pst.setString(2,String.valueOf(objM.getKilos()));
            pst.setString(3,objM.getCod());
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
     * Este metodo permite realizar la eliminacion de un campo en especifico,
     * contenido en la tabla clientes de una base de datos
     * @param cod de tipo String
     */
    public void eliminar(String cod){
        ConexionBD objCon=new ConexionBD();
        objCon.conectar();
        try{
            String SQL = "delete from mercancia where codigo=?";
            PreparedStatement pst = objCon.getConexion().prepareStatement(SQL);
            pst.setString(1,cod);
            pst.execute();            
            pst.close();
            objCon.getConexion().close();
        }catch (Exception e){
            
        }
    }
}
