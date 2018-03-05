/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projektikalenteri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author anssi
 */
public class Projekti {
	private int id;
    private String nimi;
    private String pomo;
    private ArrayList<String> tiimilaiset;
    private ArrayList<Kalenterimerkinta> merkinnat;
    
    
    
    public Projekti(String nimi, Kayttaja pomo) {
        this.nimi = nimi;
        this.pomo = pomo.getNayttonimi();
        tiimilaiset = new ArrayList();
        merkinnat = new ArrayList();
        
    }
    public Projekti(int id, String nimi, String pomo) {
    	this.id = id;
    	this.nimi = nimi;
    	this.pomo = pomo;
    	tiimilaiset = new ArrayList();
    	merkinnat = new ArrayList<Kalenterimerkinta>();
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getPomo() {
        return pomo;
    }

    public void setPomo(Kayttaja pomo) {
        this.pomo = pomo.getNayttonimi();
    }

    public ArrayList<String> getTiimilaiset() {
        return tiimilaiset;
    }

    public void setTiimilaiset(Kayttaja kayttaja) {
        tiimilaiset.add(kayttaja.getNayttonimi());
    }
    public void setTiimilaiset(String k) {
    	tiimilaiset.add(k);
    }
    public void lisaaMerkinnat(Kalenterimerkinta ka) {
    	this.merkinnat.add(ka);
    }

    @Override
    public String toString() {
        return "Nimi: " + nimi + "\nJohtaja: " + pomo + "\nTiimiläiset: " + tiimilaiset ;
    }
    
    
}
