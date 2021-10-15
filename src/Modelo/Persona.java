
package Modelo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * la clase persona permitira generar instancias con los datos de 
 * identificacion,nombre y telefono 
 * @author Cristian Romero y Santiago Gonzales
 */
public abstract class Persona {
    /**
     * Atributo que contiene el id de una persona
     */    
    protected String id, 
            
    /**
     * Atributo que contiene el nombre de una persona
     */            
    nom, 
            
    /**
     * Atributo que contiene el tel de una persona
     */            
    tel;

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
     * El constructor parametrico permitira crear una instancia 
     * de tipo persona a partir de los datos que reciba correspondientes 
     * a identificacion, nombre y telefono
     * @param id de tipo String
     * @param nom de tipo String
     * @param tel de tipo String
     */
    public Persona(String id, String nom, String tel) {
        this.id = id;
        this.nom = nom;
        this.tel = tel;
    }

    /**
     * El constructor basico permitira crear una instancia de tipo
     * persona
     */
    public Persona() {
        this.id = "";
        this.nom = "";
        this.tel = "";
    }

    /**
     * Retorna el valor de tipo texto del atributo id
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Establece o mofidica el contenido del atributo 
     * id con el valor que recibe en formato de
     * texto. Ademas de esto se validara si el atributo 
     * id es un campo vacio, es inferior a 0 y
     * tiene caracteres distintos de 1 a 9, por medio 
     * de la excepcion EnterFormatException
     * @param id de tipo String
     * @throws EnterFormatException
     */
    public void setId(String id) throws EnterFormatException{
        pat=Pattern.compile("[0-9]");
        mat=pat.matcher(id);        
        if(id.equals("")){    
            throw new EnterFormatException(1);
        }else if(!mat.find()){ 
            throw new EnterFormatException(5);
        }else{
            this.id = id;
        }
    }

    /**
     * Retorna el valor de tipo texto del atributo nombre
     * @return String
     */
    public String getNom() {
        return nom;
    }

    /**
     * Establece o mofidica el contenido del atributo 
     * nom con el valor que recibe en formato de
     * texto. Ademas de esto se validara si el atributo 
     * tiene caracteres entre a y z y no tiene numeros. 
     * Por otro lado se validara si el campo esta vacio, 
     * todo esto por medio de la excepcion EnterFormatException
     * @param nom de tipo String
     * @throws EnterFormatException
     */
    public void setNom(String nom) throws EnterFormatException {
        pat=Pattern.compile("[a-zA-Z]*[^0-9]");
        mat=pat.matcher(nom);        
        if(nom.equals("")){
            throw new EnterFormatException(1);
        }else if(!mat.find()){
            throw new EnterFormatException(4);
        }else{
            this.nom = nom;
        }
    }

    /**
     * Retorna el valor de tipo texto del atributo telefono
     * @return String
     */
    public String getTel() {
        return tel;
    }

    /**
     * Establece o mofidica el contenido del atributo 
     * tel con el valor que recibe en formato de
     * texto. Ademas de esto se validara si el atributo 
     * tel es un campo vacio y tiene caracteres distintos 
     * de 1 a 9, por medio de la excepcion EnterFormatException
     * @param tel de tipo String
     * @throws EnterFormatException
     */
    public void setTel(String tel) throws EnterFormatException {
        pat=Pattern.compile("[0-9]");
        mat=pat.matcher(tel);        
        if(tel.equals("")){
            throw new EnterFormatException(1);
        }else if(!mat.find()){
            throw new EnterFormatException(6);
        }else{        
            this.tel = tel;
        }
    }

    /**
     * Retorna la informacion de la clase Persona persona con los datos de 
     * id, nom y tel
     * @return String
     */
    @Override
    public String toString() {
        return  "\nIdentificación: " + id + "\nNombre: " + nom + "\nTelefono: " + tel ;
    }
    
}
