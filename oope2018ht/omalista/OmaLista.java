package oope2018ht.omalista;
import fi.uta.csjola.oope.lista.*;
import oope2018ht.apulaiset.*;

   /**
    * OmaLista-luokka, joka perii LinkitettyLista-luokan ja toteuttaa rajapinnan
    *
    * @author Aleksi Lahtinen (lahtinen.aleksi.e@student.uta.fi)
    * Luonnontieteiden yksikkö, Tampereen yliopisto.
    */
public class OmaLista extends LinkitettyLista implements Ooperoiva<OmaLista>{
    
    /** Hakee alkiota listasta
     *
     * @param haettava alkio, jota haetaan listasta
     * @return haettava alkio, jos haku onnistui
     * @return null, jos haku ei tuottanut tulosta tai löytynyt alkio
     */
    @Override
    public Object hae(Object haettava){
    //apumuuttuja, jonka avulla vertaillaan alkioita.
    Object tmp = new Object();
    //palautetaan null, jos haettava tai listan koko ovat "null"
    if (haettava == null || koko() == 0){
        return null;
    // tarkastetaan listaa muussa tapauksessa.    
    }else{
        for (int i = 0; i < koko(); i ++){
            tmp = alkio(i);
            if (tmp.equals(haettava)){
                return tmp;
                }
            }
        }
        //täällä palautuu null, jos haku ei löytänyt tuloksia
        return null;
        }
    
    /** Lisää listaan uuden alkion.
     *  Alkiot lisätään siten, että lista on nousevassa järjestyksessä.
     *
     * @param uusi alkio, joka lisätään listaan
     * @return true tai false, joka kertoo lisäyksen onnisumisesta
     */
    
    @SuppressWarnings({"unchecked"})
    public boolean lisaa(Object uusi){
        //apumuuttuja alkoille
        Object obj = new Object();
        //apumuuttuja (laskuri) alkioiden järjestelyä varten
        int tmp = 0;
        //jos lisättävä alkio ei ole null ja listassa ei ole alkioita
        if(uusi != null && koko() == 0 && uusi instanceof Comparable){
            //lisätään uusi alkio listan alkuun
            lisaaAlkuun(uusi);
            return true;
            //jos listassa on jo alkioita, siirrytään asettelmaan niitä
        }else{
            //katsotaan, että toteuttaako lisättävä comparable-rajapinnan
            if (uusi instanceof Comparable){
                for(int i = 0; i < koko(); i++){
                //sijoitetaan listan alkiot apumuuttujaan vertailua varten
                    obj = alkio(i);
                //luodaan Comparable objekti, jolla vertaillaan listan alkoita lisättävään
                    Comparable comp = (Comparable)obj;
                    if(comp.compareTo(uusi) > 0){
                        lisaa(i, uusi);
                        //lisäys onnistui
                        return true;
                    //jos vertailtava alkio pienempi     
                    }else if (comp.compareTo(uusi) < 0){
                        //laskuria kasvatetaan, jotta järjestely onnistuu
                        tmp++;
                    //tarkastetaan, että onko vertailtava yhtäsuuri lisättävän kanssa    
                    }else if (comp.compareTo(uusi) == 0){
                        lisaa(i, uusi);
                        return true;
                    }else{
                        return false;
                    }
                }
                //jos apumuuttuja on kasvanut listan kokoiseksi
                if(tmp == koko()){
                    //alkio lisätään listan loppuun
                    lisaaLoppuun(uusi);
                        //lisäys onnistui
                        return true;
                }else if(tmp > 0){
                    lisaa(tmp - 1, uusi);
                    return true;
                }else{
                    return false;
                }
                    
            }
         
        }
        //jos vertailu ei onnistunut tai alkiota ei onnistuttu lisäämään
        return false;
    }
    
    /** Antaa listan alusta n * alkioita.
     * Asettaa uuteen listaan nousevassa järjestyksessä omaListan alusta alkioita.
     *
     * @param i - määrittää, että montako alkiota tulostetaan listan alusta
     * @return uusi lista, jossa alun alkiot. null, jos parametrissä virhe tai lista on tyhjä
     */
    @Override
    public OmaLista annaAlku(int i) throws  IllegalArgumentException{
        OmaLista lista = new OmaLista();
        //apumuuttuja alkioita varten
        Object tmp = new Object();
        //jos on virheellinen parametri tai lista tyhjä
        if (i < 1 || i > koko() || koko() == 0){
            return null;
            //muuten ajetaan metodi        
        }else{         
            //iteroidaan lista ja asetetaan kooksi int j < int i
            for (int j = 0; j < i; j++){
            //sijoitetaan muuttujat apumuuttujaan
                tmp = alkio(j);
                //jos ensimmäinen alkio, lisätään alkuun
                if (j == 0){
                    lista.lisaaAlkuun(tmp);
                //lisätään muut alkiot alusta ensimmäisen perään
                }else{    
                    lista.lisaaLoppuun(tmp);
                }
            }
        }
        return lista;
    }
    
    /** Antaa listan lopusta n * alkioita.
     * Asettaa uuteen listaan laskevassa järjestyksessä omaListan lopun alkioita.
     *
     * @param i - määrittää, että montako alkiota tulostetaan listan lopusta
     * @return uusi lista, jossa lopun alkiot. null, jos parametrissä virhe tai lista on tyhjä
     */
    @Override
    public OmaLista annaLoppu(int i) throws  IllegalArgumentException{
        OmaLista lista = new OmaLista();
        //apumuuttuja alkioita varten
        Object tmp = new Object();
        boolean check = true;
        //jos on virheellinen parametri tai lista tyhjä
        if (i < 1 || i > koko() || koko() == 0){
            return null;
        //muuten ajetaan metodi
       }else{
       //pyöritetään silmukkaa siihen asti, jos check == false
       while (check){
               //iteroidaan lista lopusta alkuun
               for (int j = koko() - 1; j >= 0; j--){
                   //sijoitetaan alkiot apumuuttujaan
                   tmp = alkio(j);
                    //jos listan koko == i, katkaistaan silmukka
                    if (lista.koko() == i){
                        check = false;
                    //jos lista.koko() < i, lisätään sinne alkioita
                    } else{
                        //jos viimeinen alkio, lisää uuden listan loppuun
                        if (j == koko() - 1){
                            lista.lisaaLoppuun(tmp);
                        //muussa tapauksessa lisää uuden listan alkuun
                        }else{
                            lista.lisaaAlkuun(tmp);
                        }
                    }
                }
            }
        }
        return lista;
    }
}