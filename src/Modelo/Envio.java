
package Modelo;

/**
 * la clase Envio permitira generar instancias con los datos de 
 * nroE,origen,destino,cliente y merc 
 * @author Cristian Romero y Santiago Gonzales
 */
public abstract class Envio 
{

    /**
     * Atributo que contiene el numero de envio
     */
    protected String nroE,

    /**
     * Atributo que contiene el origen de envio
     */
    origen,

    /**
     * Atributo que contiene el destino de envio
     */
    destino;

    /**
     * Atributo que contiene el cliente de envio
     */
    protected Cliente cliente;

    /**
     * Atributo que contiene la mercancia de envio
     */
    protected Mercancia merc;

    /**
     * El constructor vacio nos permitira instanciar atributos
     * de la clase envio
     */
    public Envio(){
        this.nroE = "";
        this.origen = "";
        this.destino = "";
        this.cliente = new Cliente();
        this.merc = new Mercancia();
    }

    /**
     * El constructor parametrico permitira crear una instancia 
     * de tipo Envio a partir de los datos que reciba correspondientes 
     * a nroE,origen,destino,cliente y merc 
     * @param nroE de tipo String
     * @param origen de tipo String
     * @param destino de tipo String
     * @param cliente de tipo Cliente
     * @param merc de tipo Mercancia
     */
    public Envio(String nroE, String origen, String destino, Cliente cliente, Mercancia merc) {
        this.nroE = nroE;
        this.origen = origen;
        this.destino = destino;
        this.cliente = cliente;
        this.merc = merc;
    }

    /**
     * Retorna el numero de envio
     * @return String
     */ 
    public String getNroE() {
        return nroE;
    }

    /**
     * Establece o modifica el valor del numero que recibe en formato String,
     * Ademas de esto validara si el campo se encientra vacio
     * @param nroE de tipo String
     * @throws EnterFormatException
     */
    public void setNroE(String nroE) throws EnterFormatException {
        if(nroE.equals("")){
            throw new EnterFormatException(1);
        }else{
            this.nroE = nroE;
        }
    }

    /**
     * Retorna el origen de envio
     * @return String
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * Establece o modifica el valor del origen que recibe en formato String,
     * ademas de esto validara si el campo se encuentra vacio
     * @param origen de tipo String
     * @throws EnterFormatException
     */
    public void setOrigen(String origen) throws EnterFormatException {
        if(origen.equals("")){
            throw new EnterFormatException(1);
        }else{
            this.origen = origen;
        }
    }

    /**
     * Retorna el destino de envio
     * @return String
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Establece o modifica el valor del destino que recibe en formato String,
     * ademas de esto validara si el campo se encuentra vacio
     * @param destino de tipo String
     * @throws EnterFormatException
     */
    public void setDestino(String destino) throws EnterFormatException {
        if(destino.equals("")){
            throw new EnterFormatException(1);
        }else{
            this.destino = destino;
        }
    }

    /**
     * Retorna el cliente de envio
     * @return Persona
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece o modifica el valor del cliente que recibe en formato Persona
     * @param cliente de tipo Persona
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Retorna la mercancia de envio
     * @return Mercancia
     */
    public Mercancia getMerc() {
        return merc;
    }

    /**
     * Establece o modifica el valor de la mercancia que recibe en formato Mercancia
     * @param merc de tipo Mercancia
     */
    public void setMerc(Mercancia merc) {
        this.merc = merc;
    }

    /**
     * Retorna la cadena de datos de la clase Envio
     * @return  String
     */
    @Override
    public String toString() {
        return "\nNumero Envio:" + nroE + "\nOrigen: " + origen + "\nDestino: " + destino + "\n\nCLIENTE: " + cliente.toString() + "\n\nMERCANCIA: " + merc.toString()+"\n\nVALOR ENVIO: "+valorEnvio();
    }
    
    /**
     * Metodo que permite calcular el valor del envio
     * @return double
     */
    public abstract double valorEnvio();
        
    /**
     * Retorna un vector de objetos con la informacion contenida
     * en los gets de la clase Envio
     * @param Envio de tipo String
     * @return Object[]
     */
    public Object[] datos(String Envio){
        return new Object[]{getNroE(),Envio,cliente.getId(),cliente.getNom(),merc.getCod(),merc.getDescr(),merc.getKilos(),getOrigen(),getDestino(),valorEnvio()};
    }
}
