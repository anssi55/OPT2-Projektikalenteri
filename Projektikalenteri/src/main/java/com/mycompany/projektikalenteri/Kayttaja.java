package com.mycompany.projektikalenteri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miika on 7.2.2018.
 */
public class Kayttaja {


    private int id;

    private String Kayttajatunnus;

    private String salasana;

    private String nayttonimi;
    
    private ArrayList<Projekti> pomona;
    
    private ArrayList<Projekti> tekijana;
    
    private ArrayList<Kalenterimerkinta> merkinnat;
    
    public Kayttaja(int id, String email, String nayttonimi) {
    	this.id = id;
    	this.Kayttajatunnus = email;
    	this.nayttonimi = nayttonimi;
    	pomona = new ArrayList<Projekti>();
    	tekijana = new ArrayList<Projekti>();
    	this.merkinnat = new ArrayList<Kalenterimerkinta>();
    	
    }


    public String getKayttajatunnus() {
        return Kayttajatunnus;
    }

    public void setKayttajatunnus(String name) {
        Kayttajatunnus = name;
    }


    public String getSalasana() {
        return salasana;
    }

    public void setSalasana(String salasana) {
        this.salasana = salasana;
    }


    public String getNayttonimi() {
        return nayttonimi;
    }

    public void setNayttonimi(String nayttonimi) {
        this.nayttonimi = nayttonimi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Projekti luoProjekti(String nimi) {
        Projekti projekti = new Projekti(nimi, this);
        pomona.add(projekti);
        return projekti;   
    }
    public void lisaaPomona(Projekti p) {
    	pomona.add(p);
    }
    public void lisaaTekijana(Projekti p) {
    	tekijana.add(p);
    }
    public void lisaaMerkinnat(Kalenterimerkinta lista) {
    	this.merkinnat.add(lista);
    }
    
    public List<Projekti> getPomona() {
        return this.pomona;
    }
            
    public List<Projekti> getTekijana() {
        return this.tekijana;
    }

    public List<Kalenterimerkinta> getMerkinnat() {
        return this.merkinnat;
    }  
            
    


    @Override
    public String toString() {
        return "Kayttaja [id=" + id + ", Kayttajatunnus=" + Kayttajatunnus + ", salasana=" + salasana + ", nayttonimi=" + nayttonimi + "]";
    }
}
