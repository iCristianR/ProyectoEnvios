
package Modelo;

import java.util.Calendar;

/**
 * la clase FechayHora permitira generar instancias con los datos de 
 * hh,mn,ss,dd,mm y aa
 * @author Cristian Romero y Santiago Gonzales
 */
public class FechayHora {
    
    /**
     * Atributo que contiene la hora de FechayHora
     */        
    private int hh,
            
    /**
     * Atributo que contiene los minutos de FechayHora
     */                
    mn,

    /**
     * Atributo que contiene los segundos de FechayHora
     */                          
    ss,
            
    /**
     * Atributo que contiene el dia de FechayHora
     */              
    dd,
        
    /**
     * Atributo que contiene el mes de FechayHora
     */           
    mm,

    /**
     * Atributo que contiene el año de FechayHora
     */              
    aa;
 
    /**
     * El constructor parametrico permitira crear una instancia 
     * de tipo FechayHora a partir de los datos que reciba correspondientes 
     * a hh,mn,ss,dd,mm y aa
     * @param hh de tipo int
     * @param mn de tipo int
     * @param ss de tipo int
     * @param dd de tipo int
     * @param mm de tipo int
     * @param aa de tipo int
     */
    public FechayHora(int hh, int mn, int ss, int dd, int mm, int aa) {
       this.hh = hh;
       this.mn = mn;
       this.ss = ss;
       this.dd = dd;
       this.mm = mm;
       this.aa = aa;
    }
    
    /**
     * El constructor basico permitira crear una instancia de tipo
     * FechayHora, apartir de la clase Calendar que toma la fecha y 
     * hora del sistema
     */
    public FechayHora() {
    Calendar actual= Calendar.getInstance();
       this.hh = actual.get(Calendar.HOUR);
       this.mn = actual.get(Calendar.MINUTE);
       this.ss = actual.get(Calendar.SECOND);
       this.dd = actual.get(Calendar.DAY_OF_MONTH);
       this.mm = actual.get(Calendar.MONTH)+1;
       this.aa = actual.get(Calendar.YEAR);
    }

    /**
     * Retorna el valor de tipo entero del atributo hh
     * @return int
     */
    public int getHh() {
        return hh;
    }

    /**
     * Establece o mofidica el contenido del atributo 
     * hh con el valor que recibe en formato de
     * entero
     * @param hh de tipo int
     */
    public void setHh(int hh) {
        this.hh = hh;
    }

    /**
     * Retorna el valor de tipo entero del atributo mn
     * @return int
     */
    public int getMn() {
        return mn;
    }

    /**
     * Establece o mofidica el contenido del atributo 
     * mn con el valor que recibe en formato de
     * entero
     * @param mn de tipo int
     */
    public void setMn(int mn) {
        this.mn = mn;
    }

    /**
     * Retorna el valor de tipo entero del atributo ss
     * @return int
     */
    public int getSs() {
        return ss;
    }

    /**
     * Establece o mofidica el contenido del atributo 
     * ss con el valor que recibe en formato de
     * entero
     * @param ss de tipo int
     */
    public void setSs(int ss) {
        this.ss = ss;
    }

    /**
     * Retorna el valor de tipo entero del atributo dd
     * @return int
     */
    public int getDd() {
        return dd;
    }

    /**
     * Establece o mofidica el contenido del atributo 
     * dd con el valor que recibe en formato de
     * entero
     * @param dd de tipo int
     */
    public void setDd(int dd) {
        this.dd = dd;
    }

    /**
     * Retorna el valor de tipo entero del atributo mm
     * @return int
     */
    public int getMm() {
        return mm;
    }

    /**
     * Establece o mofidica el contenido del atributo 
     * mm con el valor que recibe en formato de
     * entero
     * @param mm de tipo int
     */
    public void setMm(int mm) {
        this.mm = mm;
    }

    /**
     * Retorna el valor de tipo entero del atributo aa
     * @return int
     */
    public int getAa() {
        return aa;
    }

    /**
     * Establece o mofidica el contenido del atributo 
     * aa con el valor que recibe en formato de
     * entero
     * @param aa de tipo int
     */
    public void setAa(int aa) {
        this.aa = aa;
    }

    /**
     * El metodo incSS permite generar un conteo del
     * atributo ss, con la condicion que sea menor a 
     * 60
     */
    public void incSS(){
       if(ss<60){
          ss++;
          if(ss==60){
              mn++;
          }
          if(mn==60){
              hh++;
          }
       }else{
           ss=0;
       }
    }    

    /**
     * Retorna la informacion de la clase FechaYhora con los datos de 
     * hh,mn,ss,dd,mm y aa
     * @return String
     */
    @Override
    public String toString() {
         return dd + "/" + mm + "/" + aa + " " +hh + ":" + mn + ":" + ss;
    }



}
