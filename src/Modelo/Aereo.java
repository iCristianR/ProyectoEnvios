
package Modelo;

/**
 * la clase Aereo permitira generar instancias con los datos  
 * heredados de la clase Envio
 * @author Cristian Romero y Santiago Gonzales
 */
public class Aereo extends Envio{

    /**
     * El constructor basico permitira crear una instancia
     * de tipo Aereo
     */
    public Aereo(){
        super();
    }

    /**
     * El constructor parametrico permitira crear una instancia 
     * de tipo Aereo a partir de los datos que reciba correspondientes 
     * a nroE,origen,destino, cliente y merc
     * @param nroE de tipo String
     * @param origen de tipo String
     * @param destino de tipo String
     * @param cliente de tipo Object
     * @param merc de tipo Object
     */
    public Aereo(String nroE, String origen, String destino, Cliente cliente, Mercancia merc) {
        super(nroE, origen, destino, cliente, merc);
    }

    /**
     * Retorna el valor del envio a partir de la cantidad de kilos
     * registrados en la mercancia
     * @return double 
     */
    @Override
    public double valorEnvio(){
        double valor = 0;
        valor = 6000 * this.merc.getKilos();
        return valor + (valor*0.2);
    }

}
