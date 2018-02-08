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
    private String nimi;
    private Kayttaja pomo;
    private final ArrayList<Kayttaja> tiimilaiset;
    private final String luontipvm;
    
    
    public Projekti(String nimi, Kayttaja pomo) {
        this.nimi = nimi;
        this.pomo = pomo;
        tiimilaiset = new ArrayList();
        luontipvm = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public Kayttaja getPomo() {
        return pomo;
    }

    public void setPomo(Kayttaja pomo) {
        this.pomo = pomo;
    }

    public ArrayList getTiimilaiset() {
        return tiimilaiset;
    }

    public void setTiimilaiset(Kayttaja kayttaja) {
        tiimilaiset.add(kayttaja);
    }

    @Override
    public String toString() {
        return "Nimi: " + nimi + "\nJohtaja: " + pomo + "\nTiimiläiset: " + tiimilaiset + "\nLuontipäivämäärä: " + luontipvm;
    }
    
    
}
