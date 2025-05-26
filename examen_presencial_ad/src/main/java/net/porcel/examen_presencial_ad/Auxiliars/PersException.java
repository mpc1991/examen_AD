package net.porcel.examen_presencial_ad_plantilla.Auxiliars;

public class PersException extends Exception {
    String missatge;
    
    public PersException(String message){
        super(message);
    }
    
    public PersException(){
        super();
    }
}
