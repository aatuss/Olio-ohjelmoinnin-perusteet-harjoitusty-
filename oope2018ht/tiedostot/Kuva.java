package oope2018ht.tiedostot;
import oope2018ht.apulaiset.*;

   /**
    * Kuva-luokka, joka perii Tiedosto-luokan
    *
    * @author Aleksi Lahtinen (lahtinen.aleksi.e@student.uta.fi)
    * Luonnontieteiden yksikkö, Tampereen yliopisto.
    */
public class Kuva extends Tiedosto{
    /** kuvatiedoston korkeus */
    private int korkeus;
    /** kuvatiedoston leveys */
    private int leveys;

    public Kuva(String n, int koko, int kork, int lev)throws  IllegalArgumentException{
        super(n, koko);
        setKorkeus(kork);
        setLeveys(lev);
    }

    //aksessorit
    @Setteri
    public void setKorkeus(int k)throws  IllegalArgumentException{
        if (k >= 1){
            korkeus = k;
        }else{
            throw new IllegalArgumentException();
        }
    }
    @Getteri
    public int getKorkeus(){
        return korkeus;
    }

    @Setteri
    public void setLeveys(int l)throws  IllegalArgumentException{
        if (l >= 1){
            leveys = l;
        }else{
            throw new IllegalArgumentException();
        }
    }
    @Getteri
    public int getLeveys(){
        return leveys;
    }
    
    /** toString-metodi, ketjutettu
     */
    @Override
    public String toString(){
        return super.toString() + "B " + getKorkeus() + "x" + getLeveys();
    }    
}