/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 * la clase Cliente permitira generar instancias con los datos  
 * heredados de la clase Persona
 * @author Cristian Romero y Santiago Gonzales
 */
public class Cliente extends Persona{

    /**
     * El constructor parametrico permitira crear una instancia 
     * de tipo Cliente a partir de los datos que reciba correspondientes 
     * a id, nom y tel
     * @param id de tipo String
     * @param nom de tipo String
     * @param tel de tipo String
     */
    public Cliente(String id, String nom, String tel) {
        super(id, nom, tel);
    }

    /**
     * El constructor basico permitira crear una instancia de tipo
     * Cliente
     */
    public Cliente() {
        super();
    }
    
    /**
     * Retorna un arreglo de objetos con la informacion
     * contenida en los gets de la clase cliente
     * @return Object[]
     */
    public Object[] datos(){
        return new Object[]{getId(),getNom(),getTel()};
    }
}
