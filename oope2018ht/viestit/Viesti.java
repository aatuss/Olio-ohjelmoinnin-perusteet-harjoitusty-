package oope2018ht.viestit;
import oope2018ht.omalista.*;
import oope2018ht.apulaiset.*;
import oope2018ht.tiedostot.*;

   /**
    * Viestik-luokka, joka toteuttaa ohjelman viestit
    *
    * @author Aleksi Lahtinen (lahtinen.aleksi.e@student.uta.fi)
    * Luonnontieteiden yksikkö, Tampereen yliopisto.
    */

public class Viesti implements Komennettava <Viesti>, Comparable<Viesti>{
    /** viestin tunniste. */
    private int tunniste;
    /** viestin sisältämä merkkijono. */
    private String merkkijono;
    /** tiedosto, joka voidaan lisätä viestiin. */
    private Tiedosto tiedosto;
    /** vastaus, joka voidaan lisätä viestille vastaukseksi. */
    private Viesti vastaus;
    /** OmaLista tyyppinen linkitettylista, joka sisältää viestiin liitetyt vastaukset. */
    private OmaLista vastaukset;
    /** apumuuttuja, jolla pidetään lukua viestien määrästä. */
    private static int laskuriTunniste = 0;
    
    //rakentajat
    public Viesti(int tunst, String m, Viesti v, Tiedosto t)throws IllegalArgumentException{
        laskuriTunniste++;
        laskuriTunniste = tunst;
        setTunniste(laskuriTunniste);
        setMerkkijono(m);
        setTiedosto(t);
        setVastaus(v);
        vastaukset = new OmaLista();
        setVastausLista(vastaukset);
    }
    public Viesti(String m)throws IllegalArgumentException{
        laskuriTunniste++;
        setTunniste(laskuriTunniste);
        setMerkkijono(m);
        vastaukset = new OmaLista();
        setVastausLista(vastaukset);
        
    }
    
    public Viesti(String m, Tiedosto t)throws IllegalArgumentException{
        laskuriTunniste++;
        setTunniste(laskuriTunniste);
        setMerkkijono(m);
        setTiedosto(t);
        vastaukset = new OmaLista();
        setVastausLista(vastaukset);
    
    }
    
    //aksessorit
    
    @Setteri
    public void setTunniste(int tunst) throws IllegalArgumentException{
        if (tunst > 0){
            tunniste = tunst;
        }else{
            throw new IllegalArgumentException();
        }    
    }
    //palauttaa tiedoston tunnisteen    
    @Getteri
    public int getTunniste(){
        return tunniste;
    }
    
    //asettaa viestin merkkijonon
    @Setteri
    public void setMerkkijono(String m)throws IllegalArgumentException{
        if (m.length() >= 1 || m != null){
            merkkijono = m;
        }else {
            throw new IllegalArgumentException();
            
        }    
    }
    //palauttaa viestin merkkijonon
    @Getteri
    public String getMerkkijono(){
        return merkkijono;
    }
    
    //tiedoston aksessorit
    @Setteri
    public void setTiedosto(Tiedosto t){
        //jos tiedosto ei ole null, asetetaan tiedosto
        if (t != null){
            tiedosto = t;
        }else {
            tiedosto = null;
        }    
    }
    
    @Getteri
    public Tiedosto getTiedosto(){
        return tiedosto;
    }
    
    //viestin aksessorit
    @Setteri
    public void setVastaus(Viesti v){
        //jos ei ole vastausta, vastaus on null
        if (v == null){
            vastaus = null;
        //jos tunniste yksi, niin viesti on itsenäinen (Viestiketjun ensimmäinen viesti)    
        }else{
            vastaus = v;
       }        
    }
    
    @Setteri
    public void setVastausLista(OmaLista lista) throws IllegalArgumentException{
        if (lista != null){
            vastaukset = lista;
        }else{
           throw new IllegalArgumentException(); 
        }
    }
    //listan aksessorit
    @Getteri
    public OmaLista getVastausLista(){
        return vastaukset;            
    }
    
    
    @Getteri
    public Viesti getViesti(){
        return vastaus;
    }
    
    /** Vertailee viestejä (compareTo)
     *
     *  @param vertailtava viesti
     *  @return Vertailun tulos
     */
    @Override
    public int compareTo(Viesti vertailtava){
        //nykyisen olion tunniste < kuin vertailtavan tunniste
        if (tunniste < vertailtava.getTunniste()){
            return -1;
        //nykyisen olion tunniste == kuin vertailtavan tunniste    
        } else if (tunniste == vertailtava.getTunniste()){
           return 0;
        //nykyisen olion tunniste > kuin vertailtavan tunniste        
        }else{
            return 1;
        }        
    }
    
    /** Equals-metodi.
     * Viestit ovat samat, jos tunniste on sama
     *
     * @param objekti vertailuun
     * @return tieto vertailun onnistumisesta
     */
    @Override
    public boolean equals(Object objekti){
        //yritetään muuttaa vertailtava objekti viestiksi
        try{
            //palautetaan vertailun tuottama totuusarvo
            Viesti vv = (Viesti)objekti;
            return tunniste == vv.getTunniste();
        //poikkeuksen tapahtuessa palautetaan false
        }catch(Exception e){
            return false;
            
        }  
    }
    /** toString-metodi
     */

    @Override
    public String toString(){
        //tulostetaan ilman tiedostoa, jos sitä ei ole
        if (getTiedosto() == null){
            return "#" + getTunniste() + " " + getMerkkijono();        
        //jos on, tulostetaan tiedosto mukaan    
        }else{
            return "#" + getTunniste() + " " + getMerkkijono() + " (" + getTiedosto().toString() + ")";
        }        
    }
    
    /** Hakee viestejä viestin vastauksien listasta.
     * Viestit ovat samat, jos tunniste on sama
     *
     * @param haettava viesti, jota haetaan vastausten listasta
     * @return haettava viesti, tai null, jos haeuttua viestiä ei löytynyt listasta
     * @throws IllegalArgumentException jos lisättävä parametri on null
     */
    @Override
    public Viesti hae(Viesti haettava) throws IllegalArgumentException {
        //heitetään poikkeus parametrin ollessa null arvoinen
        if (haettava == null){
            throw new IllegalArgumentException();
        }else{
            //jos OmaListan metodi löytää haettavan listasta (paluuarvo ei ole null), palautetaan viite
            if (getVastausLista().hae(haettava) != null){
                return haettava;
            //jos haettavaa ei löytynyt, null palautuu    
            }else{
                return null;
            }        
        }        
    }
    
    /** Lisää vastauksen viestille.
     * Vastaus lisätään viestin listaan, joka sisältää viitteet vastauskiin
     *
     * @param lisattava vastaus viestiin
     * @throws IllegalArgumentException jos lisättävä viesti löytyy jo listasta tai se on null
     */
    
    @Override
    public void lisaaVastaus(Viesti lisattava) throws IllegalArgumentException{
        //jos lisattava löytyy jo listasta tai se on null, heitetään poikkeus
        if (hae(lisattava) != null || lisattava == null){
            throw new IllegalArgumentException();
        }else{
            //asetetaan lisattava vastaukseksi viestille
            setVastaus(lisattava);
            //lisataan loppuun
            getVastausLista().lisaaLoppuun(lisattava);
        }        
    }
    /** Tyhjentää viestin ja poistaa siinä olevan tiedoston
     */
    @Override
    public void tyhjenna(){
        //jos viestillä ei ole tiedostoa, poistetaan pelkkä tekstisisältö
        if (getTiedosto() == null){
            setMerkkijono(POISTETTUTEKSTI);
        //jos on, niin poistetaan teksti sekä tiedosto    
        }else{
            setMerkkijono(POISTETTUTEKSTI);
            //tiedoston poistamiseen käytetään apuna sille tarkoitettua metodia
            poistaTiedosto();
        }    
    }
    
    /** Apumetodi viestin tyhjennykseen,
     * poistaa tiedoston viestistä
     */     
    public void poistaTiedosto(){
        setTiedosto(null);
    }
}