package oope2018ht.tiedostot;
import oope2018ht.apulaiset.*;

   /**
    * Video-luokka, joka perii Tiedosto-luokan
    *
    * @author Aleksi Lahtinen (lahtinen.aleksi.e@student.uta.fi)
    * Luonnontieteiden yksikkö, Tampereen yliopisto.
    */
public class Video extends Tiedosto{
    /** viedon pituus sekunteina */
    private double pituus;

    public Video(String n, int koko, double p)throws  IllegalArgumentException{
        super(n, koko);
        setPituus(p);
    }
    //aksessorit
    @Setteri
    public void setPituus(double p)throws  IllegalArgumentException{
        if (p > 0){
            pituus = p;
        }else{
            throw new IllegalArgumentException(); 
        }    
    }
    
    @Getteri
    public double getPituus(){
            return pituus;
    }
    /** toString-metodi, ketjutettu
     */
    @Override
    public String toString(){
        return super.toString() + "B " + getPituus() + " s";
    }    
}