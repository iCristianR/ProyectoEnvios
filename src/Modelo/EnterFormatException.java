
package Modelo;

/**
 * la clase EnterFormatException permitira generar instancias con los datos de 
 * nroErr y msj 
 * @author Cristian Romero y Santiago Gonzales
 */
public class EnterFormatException extends Exception{
    
    /**
     * Atributo que contiene el nroErr de EnterFormatException
     */       
    private int nroErr;
    
    /**
     * Atributo que contiene el msj de EnterFormatException
     */       
    private String msj;

    /**
     * El constructor parametrico permitira crear una instancia 
     * de tipo EnterFormatException a partir de los datos que 
     * reciba correspondientes a nroErr
     * @param nroErr de tipo int
     */
    public EnterFormatException(int nroErr) {
        this.nroErr = nroErr;
    }
    
    /**
     * El constructor basico permitira crear una instancia de tipo
     * EnterFormatException
     */
    public EnterFormatException() {
        this.nroErr = 0;
        this.msj = "";
    }

    /**
     * Retorna el valor de tipo entero del atributo nroErr
     * @return int
     */
    public int getNroErr() {
        return nroErr;
    }

    /**
     * Establece o mofidica el contenido del atributo 
     * nroErr con el valor que recibe en formato de
     * entero
     * @param nroErr de tipo int
     */
    public void setNroErr(int nroErr) {
        this.nroErr = nroErr;
    }

    /**
     * Retorna el valor de tipo texto del atributo msj
     * @return String
     */
    public String getMsj() {
        return msj;
    }

    /**
     * Establece o mofidica el contenido del atributo 
     * msj con el valor que recibe en formato de
     * texto
     * @param msj de tipo String
     */
    public void setMsj(String msj) {
        this.msj = msj;
    }
    
    /**
     * Permite sobreescribir el metodo getMessage(), heredado
     * del objeto Exception. Envia un mensaje por el numero
     * de error que reciba la clase
     * @return String
     */
    @Override
    public String getMessage(){
        switch(nroErr){
            case 1:
                msj="Valores Nulos";
                break;
            case 2:
                msj="No se admiten numeros negativos";
                break;
            case 3: 
                msj="Identificacion repetida";
                break;
        }
        return msj;
    }
    
}
