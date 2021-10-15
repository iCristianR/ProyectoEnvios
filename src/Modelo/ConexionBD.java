
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * la clase ConexionBD permitira generar instancias con los datos de 
 * conexion,bd,usuario,clave,mensaje
 * @author Cristian Romero y Santiago Gonzales
 */
public class ConexionBD {
    
    /**
     * Atributo que contiene la conexion de la clase ConexionBD
     */       
    private Connection conexion;
    
    /**
     * Atributo que contiene el nombre de la base
     * de datos de la clase ConexionBD
     */      
    private String bd, 
            
     /**
     * Atributo que contiene el usuario de la base
     * de datos de la clase ConexionBD
     */             
    usuario, 
            
     /**
     * Atributo que contiene la clave de la base
     * de datos de la clase ConexionBD
     */      
    clave,
            
     /**
     * Atributo que contiene el mensaje de la clase ConexionBD
     */              
    mensaje;
    
    /**
     * El constructor parametrico permitira crear una instancia 
     * de tipo ConexionBD a partir de los datos que reciba correspondientes 
     * a conexion,bd,usuario,clave,mensaje
     * @param conexion de tipo Connection
     * @param bd de tipo String
     * @param usuario de tipo String
     * @param clave de tipo String
     * @param mensaje de tipo String
     */
    public ConexionBD(Connection conexion, String bd, String usuario, String clave, String mensaje) {
        this.conexion = conexion;
        this.bd = bd;
        this.usuario = usuario;
        this.clave = clave;
        this.mensaje = mensaje;
    }
    
    /**
     * El constructor basico permitira crear una instancia de tipo
     * ConexionBD
     */
    public ConexionBD() {
        this.conexion = null;
        this.bd = "enviosbd";//Nombre de la base de datos
        this.usuario = "root";
        this.clave = "";
        this.mensaje = "";
    }
    
    /**
     * Retorna el valor de tipo Connection del atributo 
     * Conexion
     * @return String
     */
    public Connection getConexion() {
        return conexion;
    }
    
    /**
     * Establece o mofidica el contenido del atributo 
     * conexion con el valor que recibe en formato de
     * Connection
     * @param conexion de tipo Connection
     */
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    
    /**
     * Retorna el valor de tipo texto del atributo Mensaje
     * @return String
     */
    public String getMensaje() {
        return mensaje;
    }
    
    /**
     * Establece o mofidica el contenido del atributo 
     * mensaje con el valor que recibe en formato de
     * texto.
     * @param mensaje de tipo String
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    } 
    
    /**
     * Metodo que permite generar la conexion con la base de 
     * datos, mediante los atributos de la clase ConexionBD
     */
    public void conectar(){
       try {
            Class.forName("com.mysql.jdbc.Driver");
            String ruta= "jdbc:mysql://localhost:3306/"+bd;
            conexion=DriverManager.getConnection(ruta,usuario,clave);
            mensaje= "Conexión exitosa a Base de datos " + bd;
       } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Driver no encontrado...");
       }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al intentar conectar BD: "+ bd +"\n"+ex);
       }
    }
}
