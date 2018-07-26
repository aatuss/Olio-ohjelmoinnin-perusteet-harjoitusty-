package oope2018ht.kayttoliittyma;
import oope2018ht.apulaiset.*;
import oope2018ht.viestit.*;

   /**
    * Käyttöliittymä-luokka, joka toteuttaa ohjelman graafisen ulkoasun
    *
    * @author Aleksi Lahtinen (lahtinen.aleksi.e@student.uta.fi)
    * Luonnontieteiden yksikkö, Tampereen yliopisto.
    */

public class Kayttoliittyma {
    /** add-komennon vakio. */
    private final String ADD = "add";
    /** catalog-komennon vakio. */
    private final String CATALOG = "catalog";
    /** select-komennon vakio. */
    private final String SELECT = "select";
    /** new-komennon vakio. */
    private final String NEW = "new";
    /** reply-komennon vakio. */
    private final String REPLY = "reply";
    /** list-komennon vakio. */
    private final String LIST = "list";
    /** tree-komennon vakio. */
    private final String TREE = "tree";
    /** head-komennon vakio. */
    private final String HEAD = "head";
    /** tail-komennon vakio. */
    private final String TAIL = "tail";
    /** find-komennon vakio. */
    private final String FIND = "find";
    /** exit-komennon vakio. */
    private final String EXIT = "exit";
    /** empty-komennon vakio. */
    private final String EMPTY = "empty";
    /** error-komennon vakio. */
    private final String ERROR = "Error!";
    /** bye-komennon vakio. */
    private final String GOODBYE = "Bye! See you soon.";
    /** Alueen attribuutti, jolla alustetaan uusi keskustelualue rakentajassa. */
    private Alue alue;
    
    //rakentaja
    public Kayttoliittyma(){
        alue = new Alue();
    }
    
    /** Suorittaa ohjelman käyttöliittymän, ottaa vastaan komennot
     *  ja pilkkoo käyttäjän syötteet
     *
     */
    public void simpleOopeBoard(){
        //apumuuttujat pääsilmukan käyttöön
        String syote = "";
        String ketjuNimi = "";
        String viestiNimi = "";
        String merkkijono = "";
        int osat = 0;
        boolean lopetus = true;
        
        //aloitetaan ohjelma tervehdyksellä
        System.out.println("Welcome to S.O.B.");
        //luuppi pyörii niin kauan, kunnes annetaan lopettava komento == false
        while(lopetus){  
            try{
                System.out.print(">");
                syote = In.readString();
                //pilkotaan syöte osiksi
                String[] syotePalat = syote.split(" ");
                //otetaan syötteen pituus muuttujaan
                osat = syotePalat.length;  
                //silmukan pysäytys
                if (syote.equals(EXIT)){
                   lopetus = false;
                   System.out.println(GOODBYE);
                   
                }else if (syotePalat[0].equals(ADD)){
                   //komennossa on oltava vähintään 2 osaa
                   if (osat >= 2){
                        for (int i = 1; i < syotePalat.length; i++){
                            //rakennetaan paloista merkkijono
                            ketjuNimi = ketjuNimi +syotePalat[i] + " ";
                        }
                       //lisätään alueeseen uusi ketju 
                       alue.lisaaAlueeseenKetju(ketjuNimi.trim());
                       //nollataan nimi uusia ketjuja varten
                       ketjuNimi = "";
                    }else{
                        System.out.println(ERROR);
                    }
                //kutsutaan viestialueen catalog-metodia
                }else if (syotePalat[0].equals(CATALOG)){
                    alue.catalog();
                //kutsutaan viestialueen list-metodia 
                }else if (syotePalat[0].equals(LIST)){
                    alue.list();
                //kutsutaan viestialueen tree-metodia 
                }else if (syotePalat[0].equals(TREE)){
                    alue.tree();
                //kutsutaan viestialueen metodia, joka lisää uuden viestin ketjuun
                }else if (syotePalat[0].equals(NEW)){
                    //jos syötteessä on osia vähintään 2
                    if (osat >= 2){
                        for (int i = 1; i < syotePalat.length; i++){
                            //rakennetaan paloista merkkijono
                            viestiNimi = viestiNimi + " " + syotePalat[i];
                       //lisätään alueen ketjuun uusi viesti     
                       }if (viestiNimi.contains("  ")){
                            alue.lisaaViesti(viestiNimi.substring(1));   
                       }else{
                           alue.lisaaViesti(viestiNimi.trim());    
                       }
                       
                       //nollataan nimi uusia ketjuja varten
                       viestiNimi = "";
                    }else{
                        System.out.println(ERROR);
                    }
                //kutsutaan viestialueen metodia, joka lisää vastauksen ketjun aikaisempaan viestiin     
                }else if (syotePalat[0].equals(REPLY)){
               //osien koon on oltava vähintään 2 koska: komento->viestin_tunniste->merkkijono->tiedosto
                    if (osat > 2){
                        for (int i = 2; i < syotePalat.length; i++){
                            //rakennetaan paloista merkkijono
                            viestiNimi = viestiNimi + " " + syotePalat[i];
                        //lisätään alueen ketjuun uusi vastaus
                        //jos viestissä merkit ovat "tyhjiä", niin erotetaan komento ja viesti substringin avulla
                        }if (viestiNimi.contains("  ")){
                            alue.lisaaVastaus(Integer.parseInt(syotePalat[1]), viestiNimi.substring(1));
                        }else{
                            alue.lisaaVastaus(Integer.parseInt(syotePalat[1]), viestiNimi.trim());
                        }
                        viestiNimi = "";
                    }else{
                        System.out.println(ERROR);
                    }
                //kutsutaan alueen metodia, joka tyhjentää etsityn viestin    
                }else if (syotePalat[0].equals(EMPTY)){
                    //osia on oltava tasan 2 koska: empty->viestin_tunniste
                    if (osat == 2){   
                        alue.tyhjenna(Integer.parseInt(syotePalat[1])); 
                    }else{
                        System.out.println(ERROR);
                    }
                //kutsutaan metodia, joka valitsee ketjun aktiiviseksi    
                }else if (syotePalat[0].equals(SELECT)){
                    //osia oltava vähintään 2 koska: select->ketjun_tunniste
                    if (osat == 2){
                        alue.select(Integer.parseInt(syotePalat[1]));
                    }else{
                        System.out.println(ERROR);      
                    }
                //kutsutaan viestin hakuun tarkoitettua metodia    
                }else if(syotePalat[0].equals(FIND)){
                    //osia oltava vähintään 2 tai enemmän: find-->viestin_merkkijono/tiedosto
                    if (osat >= 2){
                        for (int i = 1; i < syotePalat.length; i++){
                            //rakennetaan paloista merkkijono
                            merkkijono =  merkkijono + syotePalat[i] + " ";
                        }
                        alue.haeViesteista(merkkijono.trim());
                        merkkijono = "";
                    }else{
                        System.out.println(ERROR);
                    }
                 //kutsutaan metodia, joka tulostaa listan päästä alkioita * n  
                }else if (syotePalat[0].equals(HEAD)){
                    //osien oltava tasan 2 koska: head-->n
                    if (osat == 2){
                        alue.annaAlku(Integer.parseInt(syotePalat[1]));     
                    }else{
                        System.out.println(ERROR);    
                    }
                //kutsutaan metodia, joka tulostaa listan hänniltä alkioita * n      
                }else if (syotePalat[0].equals(TAIL)){
                    //osien oltava tasan 2 koska: tail-->n
                    if (osat == 2){
                        alue.annaLoppu(Integer.parseInt(syotePalat[1]));     
                    }else{
                        System.out.println(ERROR);    
                    }
                //jos jokin tuntematon syöte, tulostetaan virheilmoitus    
                }else{
                    System.out.println(ERROR);
                }
            //siepetaan virheet ja tulostetaan niiden sattuessa virheiloitus  
            }catch (Exception e){
                System.out.println(ERROR);
                //nollataan ketjun ja viestin nimeen suunnatut muuttujat, jos virhe tapahtuu
                viestiNimi = "";
                ketjuNimi = "";
            }
        }
    }
}