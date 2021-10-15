
package Modelo;

import java.util.ArrayList;

/**
 * la clase Reporte permitira generar una instancia con 
 * un arraylist de la clase Envio
 * @author Cristian Romero y Santiago Gonzales
 */
public class Reporte {
    
    /**
     * Atributo que contiene el arraylist de la clase
     * reporte
     */      
    private ArrayList<Envio> listaE;

    /**
     * El constructor basico permitira crear una instancia de tipo
     * Reporte
     */
    public Reporte(){
        this.listaE = new ArrayList<>();
    }

    /**
     * El constructor parametrico permitira crear una instancia 
     * de tipo Reporte a partir del ArrayList que recibe
     * @param listaE de tipo ArrayList
     */
    public Reporte(ArrayList<Envio> listaE) {
        this.listaE = listaE;
    }

    /**
     * Retorna el valor de tipo ArrayList del atributo listaE
     * @return ArrayList
     */
    public ArrayList<Envio> getListaE() {
        return listaE;
    }

    /**
     * Establece o mofidica el contenido del atributo 
     * listaE con el valor que recibe en formato de
     * ArrayList
     * @param listaE de tipo ArrayList
     */
    public void setListaE(ArrayList<Envio> listaE) {
        this.listaE = listaE;
    }

    /**
     * Retorna la informacion del atributo listaE
     * perteneciente a la clase Reporte
     * @return String
     */
    @Override
    public String toString() {
        return "listaE= " + listaE;
    }
    
    /**
     * Este metodo permite retornar la sumatoria de todos
     * los envios realizados en la ejecucion del programa
     * @return double
     */
    public double totalEnvios()
    {
        double total = 0;
        for(int i = 0 ; i < this.listaE.size() ; i++)
        {
            total += listaE.get(i).valorEnvio();
        }
        return total;
    }
    
}
