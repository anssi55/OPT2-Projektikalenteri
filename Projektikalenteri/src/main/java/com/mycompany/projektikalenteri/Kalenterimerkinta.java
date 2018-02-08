package com.mycompany.projektikalenteri;

/**
 *
 * @author anssi
 */
public class Kalenterimerkinta {
    private String nimi;
    private Kayttaja kayttaja;
    private Projekti projekti;
    private String merkinta;
    private String alkuaika;
    private String loppuaika;
    
    public Kalenterimerkinta(String nimi, Kayttaja kayttaja) {
        this.kayttaja = kayttaja;
        this.nimi = nimi;
}
    public Kalenterimerkinta(String nimi, Kayttaja kayttaja, Projekti projekti) {
        this.projekti = projekti;
        this.nimi = nimi;
    }
    public void setAika(String alkuaika, String loppuaika) {
        this.alkuaika= alkuaika;
        this.loppuaika = loppuaika;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getMerkinta() {
        return merkinta;
    }

    public void setMerkintä(String merkinta) {
        this.merkinta = merkinta;
    }

    public String getAlkuaika() {
        return alkuaika;
    }

    public void setAlkuaika(String alkuaika) {
        this.alkuaika = alkuaika;
    }

    public String getLoppuaika() {
        return loppuaika;
    }

    public void setLoppuaika(String loppuaika) {
        this.loppuaika = loppuaika;
    }

    public Kayttaja getKayttaja() {
        return kayttaja;
    }

    public Projekti getProjekti() {
        return projekti;
    }
    
    
    
}
