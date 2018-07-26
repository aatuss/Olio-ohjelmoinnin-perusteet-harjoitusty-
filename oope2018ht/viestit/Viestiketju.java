package oope2018ht.viestit;
import oope2018ht.omalista.*;
import oope2018ht.apulaiset.*;
import oope2018ht.tiedostot.Tiedosto;

   /**
    * Viestiketju-luokka, joka toteuttaa ohjelman viestiketjun
    *
    * @author Aleksi Lahtinen (lahtinen.aleksi.e@student.uta.fi)
    * Luonnontieteiden yksikkö, Tampereen yliopisto.
    */

public class Viestiketju{
    /** Lista, johon lisätään uudet viestit */
    private OmaLista puuLista;
    /** viestiketjun tunniste. */
    private int tunniste;
    /** viestiketjun aihe. */
    private String aihe;
    /** apumuuttuja, jolla pidetään lukua viestiketjujen määrästä. */
    private static int laskuriTunniste = 0;
    /** apumuuttuja, jolla pidetään lukua viestien määrästä ketjussa. */
    private int viestimaara = 0;
    /** apumuuttuja, jota käytetään tree-metodin tukena. */
    private static int TASOSYVYYS = 0;
    /** lista, jonka avulla lisätään viesteihin vastaukset ja tulostetaan "tree". */
    private OmaLista jarjestettyLista;

    public Viestiketju(String a) throws IllegalArgumentException{
        setAihe(a);
        //alustetaan viestiketjun säilövä lista
        puuLista = new OmaLista();
        laskuriTunniste++;
        setTunniste(laskuriTunniste);
        //alustetaan viestiketjun listamaiseen tulostukseen tarkoitettu lista
        jarjestettyLista = new OmaLista();
        setJarjestetty(jarjestettyLista);
    }

    //viestiketjun aksessorit
    
    @Setteri
    //aiheen on oltava vähintään 1 merkin mittainen eikä saa olla tyhjä
    public void setAihe(String a) throws IllegalArgumentException{
        if (a.length() > 0 && !(a.equals(" "))){
            aihe = a;
        }else{
            throw new IllegalArgumentException();
        }
    }
    //palauttaa ketjun aiheen
    @Getteri
    public String getAihe(){
        return aihe;
    }
    //asettaa ketjulle tunnisteen
    @Setteri
    public void setTunniste(int t) throws IllegalArgumentException{
        if (t > 0){
            tunniste = t;
        }else{
            throw new IllegalArgumentException();
       }
    }
    //palauttaa ketjun yksilöivän tunnisteen
    @Getteri
    public int getTunniste(){
        return tunniste;
    }
    //palauttaa ketjussa olevat viestit puumaisena rakenteena
    @Getteri
    public OmaLista getViestitLista(){
        return puuLista;
    }
    
  //palauttaa kaikki viestit (vastaukset ja viestit). Käytetään "list"-komennon ja "tree"-komennon yhteydessä.
    @Getteri
    public OmaLista getJarjestetty(){
        return jarjestettyLista;
    }
    
    @Setteri
    public void setJarjestetty(OmaLista lista) throws IllegalArgumentException{
        if (lista != null){
            jarjestettyLista = lista;
        }else{
           throw new IllegalArgumentException();
        }
    }
    
     //palauttaa ketjussa olevien viestien määrän
    @Getteri
    public int getViestimaara(){ 
        return viestimaara;
    }
    //asettaa ketjun viestien määrän
    @Setteri
    public void setViestimaara(int maara){ 
        viestimaara = maara;
    }
    
     /** Lisää uuden viestin ketjuun
      *
      * @param viestiAihe - viestin aihe
      * @param tiedosto joka lisätään viestiin
      * @throws IllegalArgumentException mikäli viestin tiedot virheelliset
      */
    public void lisaaKetjuunUusiViesti(String viestiAihe, Tiedosto tiedosto) throws IllegalArgumentException{
        //jos viestiaihe alkaa pelkällä tiedostolla, tulostetaan error. Tarkastetaan se apumetodilla
        if (alkaakoTiedostolla(viestiAihe) == true){
            System.out.println("Error!");
        //muussa tapauksessa lisätään viestiin aihe ja tiedosto(jos sellainen on tullut, muussa tapauksessa null)    
        }else{     
            Viesti uusi = new Viesti(viestiAihe, tiedosto);
            //lisätään listoihin viesti, jossa tiedosto
            puuLista.lisaa(uusi);
            jarjestettyLista.lisaa(uusi);
            viestimaara++;
        }
    }
  
    /** toString-metodi
     */
    @Override
    public String toString(){
        //palauttaa ketjun tiedot viestien määrällä
        return "#" + getTunniste() + " " + getAihe() + " (" + getViestimaara()+ " "
             + "messages" + ")";
    }
     /** Tarkastaa, että viestin aihe on oikeellinen ja 
      * katsoo, että alkaako viestiaihe tiedostolla
      *
      * @param merkkijono joka on viestin sisältö
      * @return true, jos alkoi
      * @return false, jos ei alkanut
      */
    public boolean alkaakoTiedostolla(String merkkijono){
        if (merkkijono.startsWith("&")){
            return true;
        }else{
            return false;
        }
    }
  
     /** Käytetään "list" komentoa varten.
      * Tulostaa ketjun viestit järjestyksessä
      */
    public void list(){
        if (jarjestettyLista.koko() > 0){   
            System.out.println("=");
            System.out.println("== " + this.toString());
            System.out.println("===");
            for (int i = 0; i < jarjestettyLista.koko(); i++){
                System.out.println(jarjestettyLista.alkio(i).toString());
            } 
        //jos ei ole viestejä, tulostetaan pelkkä viestiketju    
        }else{
            System.out.println("=");
            System.out.println("== " + this.toString());
            System.out.println("===");
        }
    }
    
     /** Tulostaa viestit puuna "tree",
      * käyttää rekursiota puumaisen rakenteen saavuttamiseksi
      *
      * @param viesti - tulostettava
      * @param syvyys tulostettavalle puurakenteelle
      * @throws IllegalArgumentException jos parametrinä oleva viesti on virheellinen
      */
    public void tree(Viesti viesti, int syvyys) throws IllegalArgumentException{
        OmaLista vastaukset = new OmaLista();
        if (viesti.getTunniste() == 0 || viesti.getMerkkijono().length() < 1){
            throw new IllegalArgumentException();
        }else{
            //tulostetaan viestin merkkijo
            System.out.println(viesti.toString());
            //sijoitetaan apumuuttujaan viestin lista vastausten viitteistä
            vastaukset = viesti.getVastausLista();
            for (int j = 0; j < vastaukset.koko(); j++){
                //tulostellaan välilyöntejä
                
                tulostaTyhjaa(TASOSYVYYS + syvyys);
                //tehdään tyypinmuunnos ja kutsutaan "this.metodi"
                Viesti vastaus = (Viesti)vastaukset.alkio(j);
                tree(vastaus, TASOSYVYYS + syvyys);
            } 
        }   
    }
    /** Käytetään rekursiota varten void tyyppiselle tree metodin kanssa
     */
    public void tree() throws IllegalArgumentException{
        //tulostetaan ketjun tiedot
        if (jarjestettyLista.koko() > 0){
            System.out.println("=");
            System.out.println("== " + this.toString());
            System.out.println("===");
            TASOSYVYYS++; 
            for (int i = 0; i < getViestitLista().koko(); i++){
                //tehdään tyypinmuunnos viestiksi, ja kutsutaan tulostusmetodia
                Viesti v = (Viesti)getViestitLista().alkio(i);
                //kasvatetaan syvyyttä aina kutsuttaessa
                tree(v, 0);
            }
        }else{
            System.out.println("=");
            System.out.println("== " + this.toString());
            System.out.println("===");
        }
        //nollataan tasosyvyys ennen seuraavaa metodin kutsua
        TASOSYVYYS = 0;  
    }
    /** Lisää vastauksen viestille
     *
     * @param tunniste viestille
     * @param viestiAihe - viestin aihe
     * @param tiedosto joka lisätään viestiin
     * @throws IllegalArgumentException jos parametri virheellinen tai lista tyhjä
     * @return true, jos vastaus lisättiin viestille
     */
    public boolean lisaaVastausViestiin(int tunniste, String viestiAihe, Tiedosto tiedosto) 
                                        throws IllegalArgumentException{
        //laskurin avulla tarkastellaan, että tarkastellaan alkioita listan kooon sallimissa puitteissa
        int laskuri = 0;
        //vastauksia lisäillään vain, jos listassa on viestejä ennestään
        if (getJarjestetty().koko() > 0){
            for (int i = 0; i < getJarjestetty().koko(); i++){
                //luodaan tyypinmuunnos, johon sijoitetaan etsittävä viesti
                Viesti etsittava = (Viesti)getJarjestetty().alkio(i);
                if (etsittava.getTunniste() == tunniste){
                    //tarkastetaan, ettei virheellisesti lisätä pelkkää tiedostoa viestiksi
                    //jos paluuarvo on false, ei viestiAihe sisällä pelkästään esimerkiksi: & 
                    if (alkaakoTiedostolla(viestiAihe) == false){
                        //muuten lisätään viestiin kommentti ja tiedosto (jos sellainen on)    
                        Viesti vastaus = new Viesti(viestiAihe, tiedosto);
                        //lisätään etsittävän viestin vastauslistaan vastaus
                        etsittava.lisaaVastaus(vastaus);
                        //lisätään järjestettyyn listaan vastaus, jotta saadaan tulostuksen ym. oikein
                        jarjestettyLista.lisaa(vastaus);
                        //viestimäärä kasvaa ketjussa uusien viestien myötä
                        viestimaara++;
                        //katsotaan, että oliko viesti ketjun listassa, jossa viestit luotuna "new" komennolla.
                        return true;
                    }
                 //jos viestiä ei heti löytynyt, kasvatetaan laskuria listan koon puitteissa   
                }else if (laskuri < getJarjestetty().koko() - 1 && etsittava.getTunniste() != tunniste){
                    laskuri++;
                    //liikutaan seuraavaan alkioon, jota tarkastellaan
                    etsittava = (Viesti)getJarjestetty().alkio(i + 1);
                }else{ 
                    //jos laskuri on listan pituus ja etsittävää ei ole, tulee virheilmoitus
                    if (laskuri == getJarjestetty().koko() - 1 && etsittava.getTunniste() != tunniste){
                        throw new IllegalArgumentException();
                    }
                }
            }
        }
        throw new IllegalArgumentException();   
    }
    
    /** Tulostaa "tyhjät" tree-komentoa varten
     *
     * @param koko - määrittää, montako "tyhjää" tulostetaan
     */
    public void tulostaTyhjaa(int koko){
        for (int i = 0; i < koko; i ++){
            System.out.print("   ");
        }
    }
    
    /** Tyhjentää viestin
     *
     * @param tunniste joka on viestin tunniste
     * @throws IllegalArgumentException jos tyhjennettävää viestiä ei löydy tai lista tyhjä
     */
    public void tyhjennaViesti(int tunniste) throws IllegalArgumentException{
        int tmp = 0;
        //jos järjestetyssä listassa on viestejä
        //käydään se läpi ja tehdään tyypinmuunnokset
        if (jarjestettyLista.koko() > 0){
            for (int i = 0; i < jarjestettyLista.koko(); i++){
                Viesti viesti = (Viesti)jarjestettyLista.alkio(i);
                //jos viestiä ei löydy, mutta listaa ei ole käyty kokoaan läpi
                if (viesti.getTunniste() != tunniste && tmp < jarjestettyLista.koko() - 1){
                    //siirrtytään alkio eteenpäin
                    jarjestettyLista.alkio(i + 1);
                    //laskuria kasvatetaan pitämään lukua listan koosta
                    tmp++;
                 //jos viesti löytyy, tyhjennetään se kutsuttaen viestin metodia
                }else if (viesti.getTunniste() == tunniste){
                    viesti.tyhjenna();        
                }else{
                    //jos lista on käyty loppuun, eikä etsittyä viestiä löytynyt
                    if (tmp == jarjestettyLista.koko() - 1 && viesti.getTunniste() != tunniste ){
                        throw new IllegalArgumentException();     
                        } 
                    }           
                } 
            }else{
                throw new IllegalArgumentException();    
        }
    }
    
    /** Tulostaa listan alusta viestejä
     *
     * @param maara jonka veran tulostetaan listan alusta viestejä
     * @return lista, jossa alusta lisätyt viestit
     * @throws IllegalArgumentException jos parametri virheellinen
     */
    public OmaLista annaAlku(int maara) throws IllegalArgumentException{
        return jarjestettyLista.annaAlku(maara);
    }
    
    /** Tulostaa listan lopusta viestejä
     *
     * @param maara jonka veran tulostetaan listan lopusta viestejä
     * @return lista, jossa lopusta lisätyt viestit
     * @throws IllegalArgumentException jos parametri virheellinen
     */
    public OmaLista annaLoppu(int maara) throws IllegalArgumentException{
        return jarjestettyLista.annaLoppu(maara);
    }
    
    /** Hakee viestiä ja tulostaa löydetyn viestin
     *
     * @param merkkijono jolla haetaan viestiä. Voi kohdistua sen aiheeseen tai tiedostoon
     */
    public void haeViesteista (String merkkijono){
        //apumuuttuja, johon sijoitetaan järjestetyn listan sisältö
        //for eachia varten
        Viesti[] etsitytViestit;
        if (getJarjestetty().koko() > 0){
            etsitytViestit = new Viesti[getJarjestetty().koko()];
            //käydään vectori, johon lisätty viestit hakumetodia varten
            for (int i = 0; i < getJarjestetty().koko(); i++){
                Viesti jarjestetyssa = (Viesti)getJarjestetty().alkio(i);
                etsitytViestit[i] = jarjestetyssa;
            }
            for (Viesti etsittava : etsitytViestit){
                //jos etsittavassa esiintyy tiedosto, katsotaan tiedoston ja viestin merkkijonot
                if (etsittava.getTiedosto() != null){
                    if (etsittava.getMerkkijono().contains(merkkijono) ||
                        etsittava.getTiedosto().getNimi().contains(merkkijono)){
                        System.out.println(etsittava.toString()); 
                    }
                //jos tiedostoa ei ole, katsotaan vain viestin merkkijonoa    
                }else{
                    if (etsittava.getMerkkijono().contains(merkkijono)){
                        System.out.println(etsittava.toString()); 
                    }
                }
            }
        }  
    }
}    



