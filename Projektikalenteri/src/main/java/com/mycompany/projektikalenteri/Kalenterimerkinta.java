package com.mycompany.projektikalenteri;

/**
 *
 * @author anssi
 */
public class Kalenterimerkinta {
    private String id;
    private String kayttaja;
    private String projekti;
    private String merkinta;
    private String alkuaika;
    private String loppuaika;
    
    public Kalenterimerkinta(Kayttaja kayttaja) {
        this.kayttaja = kayttaja.getNayttonimi();
        
}
    public Kalenterimerkinta( Kayttaja kayttaja, Projekti projekti) {
        this.projekti = projekti.getNimi();
        
    }
    public Kalenterimerkinta(String id, String kayttaja, String merkinta) {
    	this.id= id;
    	this.kayttaja= kayttaja;
    	this.merkinta = merkinta;
    }
    public Kalenterimerkinta(String id, String kayttaja, String projekti, String merkinta) {
    	this.id = id;
    	this.kayttaja = kayttaja;
    	this.projekti= projekti;
    	this.merkinta = merkinta;
    }
    public void setAika(String alkuaika, String loppuaika) {
        this.alkuaika= alkuaika;
        this.loppuaika = loppuaika;
    }

    public String getNimi() {
        return id;
    }

    public void setNimi(String nimi) {
        this.id = nimi;
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

    public String getKayttaja() {
        return kayttaja;
    }

    public String getProjekti() {
        return projekti;
    }
    
    
    
}
