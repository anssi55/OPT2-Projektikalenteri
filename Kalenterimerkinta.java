package com.mycompany.projektikalenteri;

/**
 *
 * @author anssi
 */
public class Kalenterimerkinta {
    private int id;
    private String kayttaja;
    private String projekti;
    private String merkinta;
    private String alkuaika;
    private String loppuaika;
    
    public Kalenterimerkinta(String kayttaja) {
        this.kayttaja = kayttaja;
        
}
    public Kalenterimerkinta( String kayttaja, String projekti) {
        this.projekti = projekti;
        this.kayttaja = kayttaja;
    }
    public Kalenterimerkinta(int id, String kayttaja, String merkinta) {
    	this.id= id;
    	this.kayttaja= kayttaja;
    	this.merkinta = merkinta;
    }
    public Kalenterimerkinta(int id, String kayttaja, String projekti, String merkinta) {
    	this.id = id;
    	this.kayttaja = kayttaja;
    	this.projekti= projekti;
    	this.merkinta = merkinta;
    }
    public void setAika(String alkuaika, String loppuaika) {
        this.alkuaika= alkuaika;
        this.loppuaika = loppuaika;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKayttaja() {
        return kayttaja;
    }

    public void setKayttaja(String kayttaja) {
        this.kayttaja = kayttaja;
    }

    public String getProjekti() {
        return projekti;
    }

    public void setProjekti(String projekti) {
        this.projekti = projekti;
    }

    public String getMerkinta() {
        return merkinta;
    }

    public void setMerkinta(String merkinta) {
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
 
}
