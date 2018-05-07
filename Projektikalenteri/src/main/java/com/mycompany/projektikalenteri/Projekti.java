/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projektikalenteri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 *
 * @author anssi
 */
public class Projekti {
	private int id;
    private String nimi;
    private String pomo;
    private HashMap<String, Integer> tiimilaiset;
    private ArrayList<Kalenterimerkinta> merkinnat;
    
    
    
    public Projekti(String nimi, Kayttaja pomo) {
        this.nimi = nimi;
        this.pomo = pomo.getNayttonimi();
        tiimilaiset = new HashMap();
        merkinnat = new ArrayList();
        
    }
    public Projekti(int id, String nimi, String boss) {
    	this.id = id;
    	this.nimi = nimi;
    	this.pomo = boss;
    	tiimilaiset = new HashMap();
    	merkinnat = new ArrayList<Kalenterimerkinta>();
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setPomo(String pomo) {
		this.pomo = pomo;
	}
	public String getPomo() {
        return pomo;
    }

    public void setPomo(Kayttaja pomo) {
        this.pomo = pomo.getNayttonimi();
    }

    public ArrayList<String> getTiimilaiset() {
        return new ArrayList<String>(tiimilaiset.keySet());
    }

    public void setTiimilaiset(Kayttaja kayttaja) {
        tiimilaiset.put(kayttaja.getNayttonimi(), kayttaja.getId());
    }
    public void setTiimilaiset(String k, int i) {
    	tiimilaiset.put(k, i);
    }
    public void lisaaMerkinnat(Kalenterimerkinta ka) {
    	this.merkinnat.add(ka);
    }

    @Override
    public String toString() {
        return "Nimi: " + nimi + "\nJohtaja: " + pomo + "\nTiimiläiset: " + tiimilaiset ;
    }
    
    
}
