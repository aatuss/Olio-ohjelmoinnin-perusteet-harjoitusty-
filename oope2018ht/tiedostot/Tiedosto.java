package oope2018ht.tiedostot;
import oope2018ht.apulaiset.*;

   /**
    * Abstrakti Tiedosto-luokka
    *
    * @author Aleksi Lahtinen (lahtinen.aleksi.e@student.uta.fi)
    * Luonnontieteiden yksikkö, Tampereen yliopisto.
    */
     
public abstract class Tiedosto{
    /** tiedoston nimi */
    private String nimi;
    /** tiedoston koko */
    private int koko;

    public Tiedosto(String n, int k)throws  IllegalArgumentException{
        setNimi(n);
        setKoko(k);
    }
    
    //aksessorit
    @Setteri
    public void setNimi(String n) throws  IllegalArgumentException{
        if (n == null){
            throw new IllegalArgumentException();
        }else{    
           //jos parametri >= 1, asetaan nimi
            if (n.length() >= 1){
                nimi = n;
            }else{
                throw new IllegalArgumentException();
            }
        }
    }
    @Getteri
    public String getNimi(){
        return nimi;
    }

    @Setteri
    public void setKoko(int k) throws  IllegalArgumentException{
    //asetetaan koko, jos se on suurempi kuin 1
        if (k >= 1){
            koko = k;
        }else{
            throw new IllegalArgumentException();
        }
    }
    @Getteri
    public int getKoko(){
        return koko;
    }
    /** toString-metodi
     */
    @Override
    public String toString(){
        return getNimi() + " " + getKoko() + " ";
    }    
}    