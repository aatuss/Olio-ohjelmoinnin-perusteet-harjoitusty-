package oope2018ht.viestit;
import java.io.BufferedReader;
import java.io.FileReader;
import oope2018ht.omalista.*;
import oope2018ht.apulaiset.*;
import oope2018ht.viestit.*;
import oope2018ht.tiedostot.*;

   /**
    * Alue-luokka, joka sisältää viestiketjut
    *
    * @author Aleksi Lahtinen (lahtinen.aleksi.e@student.uta.fi)
    * Luonnontieteiden yksikkö, Tampereen yliopisto.
    */

public class Alue {
    /** lista, jossa on alueen viestiketjut */
    private OmaLista viestiketjut;
    /** apumuuttuja, jolla asetetaan viestiketju aktiiviseksi (select). */
    private Viestiketju aktiivinen;
    
    //rakentaja
    public Alue(){
        viestiketjut = new OmaLista();
        setKetjutLista(viestiketjut);
    }
   
    //palauttaa listan ketjuja
    @Getteri
    public OmaLista getKetjutLista(){
        return viestiketjut;  
    }
    //asettaa ketjun aktiiviseksti
    @Setteri
    public void setKetju(Viestiketju k) throws IllegalArgumentException{
        aktiivinen = k;
    }
    //palauttaa aktiivisena olevan viestiketjun
    @Getteri
    public Viestiketju getKetju() throws IllegalArgumentException{
        return aktiivinen;    
    }
    @Setteri
    public void setKetjutLista(OmaLista lista) throws IllegalArgumentException{
        if (lista != null){
            viestiketjut = lista;
        }else{
           throw new IllegalArgumentException(); 
        }
    }

    /** Lisää uuden ketjun alueeseen,
     * jos aikaisempia ketjuja ei ole, lisätään alkuun. Muuten loppuun
     *
     * @param aihe viestiketjulle
     */
    public void lisaaAlueeseenKetju(String aihe) throws IllegalArgumentException{
        //jos alueella ei ole aikaisempia ketjuja, niin luodaan uusi listan alkuun
        if (getKetjutLista().koko() == 0){
            Viestiketju k1 = new Viestiketju(aihe);
            k1.setViestimaara(0);
            setKetju(k1);
            getKetjutLista().lisaaAlkuun(k1);
        //lisätään loput ketjut listan loppuun
        }else{
            Viestiketju k2 = new Viestiketju(aihe);
            k2.setViestimaara(0);
            getKetjutLista().lisaaLoppuun(k2);
        }
    }
    
    /** Catalog-komento,
     * tulostaa alueen ketjut ja nissä olevien viestien määrän
     */
    public void catalog(){
        //jos ketjuja on listassa, niin tulostetaan niiden tiedot
        if (getKetjutLista().koko() > 0){
        for (int i = 0; i < getKetjutLista().koko(); i++){
            //tulostetaan ketjut
            Viestiketju k1 = (Viestiketju)getKetjutLista().alkio(i);
            System.out.println(k1.toString());
            }
        }     
    }
    /** Select-komento,
     * aktivoi etsityn viestiketjun
     *
     * @param tunniste - hakee aktivoitavan ketjun
     * @throws IllegalArgumentException jos parametri virheellinen tai etsittyä viestiketjua ei ole
     */
    public void select(int tunniste) throws IllegalArgumentException{
        int tmp = 0;
        if (getKetjutLista().koko() > 1){
            for (int i = 0; i < getKetjutLista().koko(); i++){
                Viestiketju etsittava = (Viestiketju)getKetjutLista().alkio(i);
                if (etsittava.getTunniste() == tunniste){
                //asetetaan aktiiviseksi ketjuksi
                setKetju(etsittava);
            //jos ketjua ei löytynyt, tulee poikkeus
            }else if (etsittava.getTunniste() != tunniste && tmp < getKetjutLista().koko() - 1){
                //kasvatetaan laskuria, jotta listan koko sisältöä voidaan tarkastella
                tmp++;
            }else{
                if (tmp == getKetjutLista().koko() - 1 && etsittava.getTunniste() != tunniste){
                    throw new IllegalArgumentException(); 
                    }
                }
            }
        }else{
            throw new IllegalArgumentException();
        }
    }
    
    /** Tree-komento,
     * tulostaa alueessa olevan ketjun viestit puumallina
     */
    public void tree(){
        getKetju().tree();  
    }
    
    /** List-komento.
     * tulostaa alueessa olevan ketjun viestit järjestettynä listana
     */
    public void list(){
        getKetju().list();
    }
    
    /** Erottaa viestiin liitetyn tiedoston viestin aiheesta ja
     * katsoo, onko merkkijonossa tiedostoa ja tekee erotuksen sen mukaisesti
     *
     * @param merkkijono sisältää viestin aiheen ja mahdollisesti tiedoston
     * @return viestistä erotetut osat tai koko merkkijono
     */
    public String erotaViesti(String merkkijono){
        String kommentti = "";
        //jos merkkijonossa esiintyy tyhjä ja & merkki, tiedetään että viestiin yritetään lisätä tiedostoa.
        if (merkkijono.contains(" &")){
            String[]osat = merkkijono.split(" &");
            //lisää String apumuuttujaan vain viestin merkkijonon sisällön
            for (int i = 0; i < osat.length; i++){
                kommentti = osat[0];  
            }
            //palautetaan viestin kommentti, josta erotettu tiedosto
            return kommentti;
        }else{
            //jos tiedostoa ei yritetty lisätä, palautetaan suoraan merkkijono
            return merkkijono;
        }
    }
    
    /** Katsoo, että yritetäänkö viestiin lisätä tiedosto ja
     * havaitsee, että mitä tiedostoa ollaan lisäämässä. 
     *
     * Pilkkoo merkkijonon osiksi ja tarkastaa osien pituuden, 
     * tekee päätelmän tiedoston tyypistä ja "rakentaa" tiedoston palasista
     *
     * @param merkkijono - joka sisältää tiedoston
     * @return Palauttaa tiedoston, joka löydettiin merkkijonon osista
     * @return Palautuu null, jos ei ollut tiedostoa
     * @throws IllegalArgumentException jos parametri virheellinen tai tiedostoa ei löydy
     */
    public Tiedosto onkoTiedostoa(String merkkijono) throws IllegalArgumentException{
        String tiedostonSisalto = "";
        //puolitetaan merkkijono tähän listaan, jos siinä on tiedosto
        String [] erotaTiedosto;
        String tmp = "";
        String erotaPiste[];
         //tiedostoa yritetään liittää, jos merkkijonossa esiintyy " &"
        if (merkkijono.contains(" &")){
            //erotetaan merkkijono ensiksi "&" merkkiin
            erotaTiedosto = merkkijono.split("&");
            //lisätään apumuuttujaan tiedoston nimi
            tmp = erotaTiedosto[1];
            //yritetään avata tiedostoa
            try{
                //avataan ja luetaan tiedosto
                FileReader tiedosto = new FileReader(tmp);
                BufferedReader lukija = new BufferedReader(tiedosto);
                //luetaan tiedostoa, kunnes sen sisältö == null
                while((tiedostonSisalto = lukija.readLine()) != null){
                    //paloitellaan tiedoston merkkijono osiksi
                    String[] osat = tiedostonSisalto.split(" ");
                    //jos osien pituus on 4, tarkoittaa se sitä
                    //että yritetään lisätä kuvaa
                    if (osat.length ==  4 && osat[0].equals("Kuva")){
                        //luodaan paloista kuvatiedosto ja palautetaan
                        Kuva kuva = new Kuva(erotaTiedosto[1], Integer.parseInt(osat[1]),
                                    Integer.parseInt(osat[2]), Integer.parseInt(osat[3]));
                        return kuva;
                    //jos osia on 3, yritetään lisätä videota    
                    }else if (osat.length ==  3 && osat[0].equals("Video")){
                        //luodaan paloista videotiedosto ja palautetaan
                        Video video = new Video(erotaTiedosto[1], 
                                Integer.parseInt(osat[1]),Double.parseDouble(osat[2]));
                        return video;
                    }else{
                        throw new IllegalArgumentException();    
                    }
                    
                }
                lukija.close();
            }catch(Exception e){
                throw new IllegalArgumentException();
            }
        }
        //palutuu null, jos tiedostoa ei yritetty liittää viestiin
        return null;
    }
    
    /** Lisää alueen viestiketjun viestiin uuden vastauksen
     *
     * @param tunniste jolle lisätään vastaus
     * @param merkkijono - vastauksen sisältämä merkkijono
     * @throws IllegalArgumentException jos parametrissä virhe tai listassa ei ole ketjua
     */
    public void lisaaVastaus(int tunniste, String merkkijono) throws IllegalArgumentException{
        //katsotaan, että ketjussa on viestejä joihin voisi vastata
        if (getKetjutLista().koko() == 0){
            throw new IllegalArgumentException();
        }else{
            //kutsutaan ketjuluokan metodia, jolla että löytyykö vastattava viesti
            //luodaan tiedosto merkkijonosta. On null, jos sitä ei löydy
            Tiedosto tiedosto = onkoTiedostoa(merkkijono);
            //jos tiedosto on null tai tai merkkijono sisältää tiedoston, hyväksytään se
            if (tiedosto == null || tiedosto.getNimi().length() >= 1){
                //erottaa viestin merkkijonosta tiedoston luomiseen käyretyt komennot
                String kommentti = erotaViesti(merkkijono);
                getKetju().lisaaVastausViestiin(tunniste, kommentti, tiedosto);  
            }else{
                System.out.println("Error!");
            }
        }
    }
    /** Lisää viestialueessa olevaan ketjuun uuden viestin
     *
     * @param merkkijono - viestin sisältö
     * @throws IllegalArgumentException jos parametrissä virhe tai listassa ei ole ketjua
     */
    public void lisaaViesti(String merkkijono) throws IllegalArgumentException{
        if (getKetjutLista().koko() == 0){
            throw new IllegalArgumentException();
        }else{
            //luodaan tiedosto merkkijonosta. On null, jos sitä ei löydy
            Tiedosto tiedosto = onkoTiedostoa(merkkijono);
            //jos tiedosto on null tai tai merkkijono sisältää tiedoston, hyväksytään se
            if (tiedosto == null || tiedosto.getNimi().length() >= 1){
                //erottaa viestin merkkijonosta tiedoston luomiseen käyretyt komennot
                String kommentti = erotaViesti(merkkijono);
                getKetju().lisaaKetjuunUusiViesti(kommentti, tiedosto);
                //jos virheellinen tiedosto, niin tulostetaan virhe
                }else{
                    System.out.println("Error!"); 
            }
        } 
    }
    
    /** Tyhjentää alueen ketjussa olevan viestin
     *
     * @param tunniste - viesti joka tyhjennetään
     */
    public void tyhjenna(int tunniste) throws IllegalArgumentException{
        getKetju().tyhjennaViesti(tunniste);
    }
    
    /** Tulostaa viestialueen ketjun alkupään viestejä parametrin määrittämän määrän
     *
     * @param lukumaara määrittää lopusta palautettujen viestin määrän
     */
    public void annaLoppu(int lukumaara)throws IllegalArgumentException{
        OmaLista loppu = new OmaLista();
        //lisätään apumuuttujaan ketjun metodista saatu paluuarvo
        loppu = getKetju().annaLoppu(lukumaara);
        for (int i = 0; i < loppu.koko(); i++){
            Viesti lopusta = (Viesti)loppu.alkio(i);
            System.out.println(lopusta.toString()); 
        }
    }
    
    /** Tulostaa viestialueen ketjun loppupään viestejä parametrin määrittämän määrän
     *
     * @param lukumaara määrittää alusta palautettujen viestin määrän
     */
    public void annaAlku(int lukumaara) throws  IllegalArgumentException{
        OmaLista alku = new OmaLista();
        //lisätään apumuuttujaan ketjun metodista saatu paluuarvo
        alku = getKetju().annaAlku(lukumaara);
        for (int i = 0; i < alku.koko(); i++){
            Viesti alusta = (Viesti)alku.alkio(i);
            System.out.println(alusta.toString());
        }
    }
    
    /** Hakee viestiä,
     * haku voi kohdistua viestin aiheeseen tai tiedostoon
     *
     * @param merkkijono - jota haetaan viestistä
     * @throws IllegalArgumentException jos parametrissä virhe tai lista tyhjä
     */
    public void haeViesteista(String merkkijono) throws IllegalArgumentException{
        if (getKetjutLista().koko() == 0){
            throw new IllegalArgumentException();
        }else{
            getKetju().haeViesteista(merkkijono);
        }
    }
}
