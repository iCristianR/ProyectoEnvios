
package Modelo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * la clase persona permitira generar instancias con los datos de 
 * identificacion,nombre y telefono 
 * @author Cristian Romero y Santiago Gonzales
 */
public class Mercancia {
    
    /**
     * Atributo que contiene el cod de una Mercancia
     */      
    private String cod, 
            
    /**
     * Atributo que contiene la descr de una Mercancia
     */              
    descr;
    
    /**
     * Atributo que contiene los kilos de una Mercancia
     */      
    private double kilos;
    
    /**
     * El objeto pat permitira generar un patron
     * de busqueda para nuestra cadena de caracteres.
     * Se crea globalmente para ser utilizado en toda
     * la clase 
     */
    Pattern pat = null;
    
    /**
     * El objeto mat permitira realizar una busqueda
     * por medio de una cadena de caracteres, pasada 
     * previamente por Pattern. Se crea globalmente 
     * para ser utilizado en toda la clase  
     */
    Matcher mat = null;     
    
    /**
     * El constructor basico permitira crear una instancia de tipo
     * Mercancia
     */
    public Mercancia(){
        this.cod = "";
        this.descr = "";
        this.kilos = 0;
    }
    
    /**
     * El constructor parametrico permitira crear una instancia 
     * de tipo Mercancia a partir de los datos que reciba correspondientes 
     * a cod,descr y kilos
     * @param cod de tipo String
     * @param descr de tipo String
     * @param kilos de tipo String
     */
    public Mercancia(String cod, String descr, double kilos) {
        this.cod = cod;
        this.descr = descr;
        this.kilos = kilos;
    }

    /**
     * Retorna el valor de tipo texto del atributo cod
     * @return String
     */
    public String getCod() {
        return cod;
    }

    /**
     * Establece o mofidica el contenido del atributo 
     * cod con el valor que recibe en formato de
     * texto. Ademas de estos no permite que el campo 
     * este vacio y no permite el ingreso de letras
     * @param cod de tipo String
     * @throws EnterFormatException
     */
    public void setCod(String cod) throws EnterFormatException {
        pat=Pattern.compile("[0-9]");
        mat=pat.matcher(cod);            
        if(cod.equals("")){
            throw new EnterFormatException(1);
        }else if(!mat.find()){
            throw new EnterFormatException(7);
        }else{        
            this.cod = cod;
        }
    }

    /**
     * Retorna el valor de tipo texto del atributo descr
     * @return String
     */
    public String getDescr() {
        return descr;
    }

    /**
     * Establece o mofidica el contenido del atributo 
     * descr con el valor que recibe en formato de
     * texto. Ademas de esto no permite que el campo
     * este vacio
     * @param descr de tipo String
     * @throws EnterFormatException
     */
    public void setDescr(String descr) throws EnterFormatException {
        if(descr.equals("")){
            throw new EnterFormatException(1);
        }else{        
            this.descr = descr;
        }
    }

    /**
     * Retorna el valor de tipo double del atributo kilos
     * @return double
     */
    public double getKilos() {
        return kilos;
    }

    /**
     * Establece o mofidica el contenido del atributo 
     * id con el valor que recibe en formato de
     * texto. Ademas de esto no permite que se ingresen
     * valores inferiores a 0
     * @param kilos de tipo double
     * @throws EnterFormatException
     */
    public void setKilos(double kilos) throws EnterFormatException {
        if(kilos<0){
            throw new EnterFormatException(2);
        }else{
            this.kilos = kilos;
        }
    }

    /**
     * Retorna la informacion de la clase Mercancia con los datos de 
     * cod,descr y kilos
     * @return String
     */
    @Override
    public String toString() {
        return "\nCodigo: " + cod + "\nDescripción: " + descr + "\nKilos: " + kilos;
    }

    /**
     * Retorna un vector de Objectos con los gets de la clase Mercancia
     * @return Object[]
     */
    public Object[] datos(){
        return new Object[]{getCod(),getDescr(),getKilos()};
    }
}
