
package Modelo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * la clase EnvioDAO permitira generar metodos por
 * los cuales se hace uso de sentencias SQL
 * @author Cristian Romero y Santiago Gonzales
 */
public class EnvioDAO {
    
    /**
     * Este metodo permite realizar el llenado de un DefaultTableModel
     * mediante la tabla envios ya creada en una base de datos
     * @return DefaultTableModel
     */
    public DefaultTableModel consulta(){
        DefaultTableModel plantilla = new DefaultTableModel();
        ConexionBD objCon= new ConexionBD();
        try {
            objCon.conectar();
            Statement consulta = objCon.getConexion().createStatement(); //bloque de contenedor 
            ResultSet datos= consulta.executeQuery("select * from envios"); // ejecuta una consulta
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
     * en una tabla llamada envios, contenida en una base de 
     * datos
     * @param objE de tipo Object
     * @param envio de tipo Object
     * @return String
     */
    public String insertar(Envio objE,String envio){
        String mensaje=""; 
        try {
            ConexionBD conexion=new ConexionBD();
            PreparedStatement consulta = null;
            conexion.conectar();
            String comando= "insert into envios values(?,?,?,?,?,?,?,?,?,?)";
            consulta=conexion.getConexion().prepareStatement(comando);
            consulta.setString(1,objE.getNroE());
            consulta.setString(2,envio);
            consulta.setString(3,objE.getCliente().getId());
            consulta.setString(4,objE.getCliente().getNom());
            consulta.setString(5,objE.getMerc().getCod());
            consulta.setString(6,objE.getMerc().getDescr());
            consulta.setString(7,String.valueOf(objE.getMerc().getKilos()));
            consulta.setString(8,objE.getOrigen());
            consulta.setString(9,objE.getDestino());
            consulta.setString(10,String.valueOf(objE.valorEnvio()));
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
     * contenido en la tabla envios de una base de datos
     * @param Nro de tipo String
     * @return String
     */
    public String buscarDato(String Nro){
        ConexionBD objCon= new ConexionBD();
        objCon.conectar();
        String registros="ENVIO: \n";
               
        String SQL="select * from envios where nro_envio like '%"+Nro+"%'";
        try{
            
            Statement st = (Statement) objCon.getConexion().createStatement();
            ResultSet rs = st.executeQuery(SQL);
            
            while(rs.next()){
                registros ="Numero Envio: "+ rs.getString("nro_envio")+";"
                          +"\nEnvio: "+ rs.getString("envio")+";"
                          +"\nId_Cliente: "+ rs.getString("id_cliente")+"\n"
                          +"\nNombre_Cliente: "+ rs.getString("nombre_cliente")+"\n"
                          +"\nCod_Mercancia: "+ rs.getString("cod_mercancia")+"\n"
                          +"\nDescri_Mercancia: "+ rs.getString("des_mercancia")+"\n"
                          +"\nKilos: "+ rs.getString("kilos")+"\n"
                          +"\nOrigen: "+ rs.getString("origen")+"\n"
                          +"\nDestino: "+ rs.getString("destino")+"\n"
                          +"\n\nVALOR ENVIO: "+ rs.getString("valor_envio");
            }       
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error al mostrar datos"+e.getMessage());
        }
        return registros;
    }
    
    /**
     * Este metodo permite realizar la actualizacion de un campo 
     * en especifico, en la tabla envios de una base de datos
     * @param objE de tipo Object
     * @return int
     */
    public int actualizar(Envio objE){
            ConexionBD objCon=new ConexionBD();
            objCon.conectar();
            int val=0;
        try{
            String SQL = "UPDATE envios SET envio=?,id_cliente=?,nombre_cliente=?,"
                    + "cod_mercancia=?,des_mercancia=?,kilos=?,origen=?,destino=?,valor_envio=? WHERE nro_envio=?";
            PreparedStatement pst = objCon.getConexion().prepareStatement(SQL);   
            if(objE.getClass().getName().equals("Aereo")){
                 pst.setString(1,"Aereo");
            }else{
                pst.setString(1,"Terrestre");
            }             
            pst.setString(2,objE.getCliente().getId());                      
            pst.setString(3,objE.getCliente().getNom());
            pst.setString(4,objE.getMerc().getCod());
            pst.setString(5,objE.getMerc().getDescr());
            pst.setString(6,String.valueOf(objE.getMerc().getKilos()));
            pst.setString(7,objE.getOrigen());
            pst.setString(8,objE.getDestino());
            pst.setString(9,String.valueOf(objE.valorEnvio()));
            pst.setString(10,objE.getNroE());
            
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
     * contenido en la tabla envios de una base de datos
     * @param Nro de tipo String
     */
    public void eliminar(String Nro){
        ConexionBD objCon=new ConexionBD();
        objCon.conectar();
        try{
            String SQL = "delete from envios where nro_envio=?";
            PreparedStatement pst = objCon.getConexion().prepareStatement(SQL);
            pst.setString(1,Nro);
            pst.execute();            
            pst.close();
            objCon.getConexion().close();
        }catch (Exception e){
            
        }
    }
}
