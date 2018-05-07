package com.mycompany.projektikalenteri;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.text.Text;

/**
 * Created by Miika on 7.2.2018.
 */
public class Kayttaja {
    private DatabaseHandler handler;

    private int id;

    private String Kayttajatunnus;

    private String salasana;

    private String nayttonimi;

    private List<Projekti> pomona;

    private List<Projekti> tekijana;

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
    public void setHandler(DatabaseHandler handler) {
        this.handler = handler;

    }
    public boolean addProject(String s, Text t) {
        try {
            return handler.addProject(s, this, t);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public boolean addPersonalEntry(String start, String end, String message) {
        try {
            return handler.addUserEntry(this, start, end, message);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public boolean addProjectEntry(String start, String end, String message, Projekti project) {
        try {
            return handler.addProjectEntry(this, project, start, end, message);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean removeProject(Projekti p, Text t){
    	try {
    		return handler.removeProject(p.getNimi(), this, t);

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }
    public void removeProjectFromList(String s) {
    	Iterator<Projekti> i = pomona.iterator();
    	while (i.hasNext()) {
    	   Projekti o = i.next();
    	   if(o.getNimi().equals(s)) {
    		   i.remove();
    	   }
    	}
    }
    public void changeHandlersResourceBundle(ResourceBundle b) {
    	handler.changeResourceBundle(b);
    }



    @Override
    public String toString() {
        return "Kayttaja [id=" + id + ", Kayttajatunnus=" + Kayttajatunnus + ", salasana=" + salasana + ", nayttonimi=" + nayttonimi + "]";
    }









}
