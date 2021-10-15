/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 * la clase Terrestre permitira generar instancias con los datos
 * heredados de la clase Envio
 * @author Cristian Romero y Santiago Gonzales
 */
public class Terrestre extends Envio{

    /**
     * El constructor basico permitira crear una instancia de tipo
     * Terrestre
     */
    public Terrestre(){
        super();
    }

    /**
     * El constructor parametrico permitira crear una instancia 
     * de tipo Terrestre a partir de los datos que reciba correspondientes 
     * a nroE,origen,destino,cliente y merc
     * @param nroE de tipo String
     * @param origen de tipo String
     * @param destino de tipo String
     * @param cliente de tipo String
     * @param merc de tipo String
     */
    public Terrestre(String nroE, String origen, String destino, Cliente cliente, Mercancia merc) {
        super(nroE, origen, destino, cliente, merc);
    }

    /** 
     * Este metodo permite retornar el valor del envio
     * a partir de los kilos ingresados
     * @return  double
     */
    @Override
    public double valorEnvio() {
        double valor = 0;
        valor = 6000 * this.merc.getKilos();
        return valor;
    }
    
}
