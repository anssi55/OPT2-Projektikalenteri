package com.mycompany.projektikalenteri;

/**
 * Created by Miika on 7.2.2018.
 */
public class Kayttaja {


    private int id;

    private String Kayttajatunnus;

    private String salasana;

    private String nayttonimi;


    public String getKayttajatunnus() {
        return Kayttajatunnus;
    }

    public void setKayttajatunnus(String name) {
        Kayttajatunnus = Kayttajatunnus;
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


    @Override
    public String toString() {
        return "Kayttaja [id=" + id + ", Kayttajatunnus=" + Kayttajatunnus + ", salasana=" + salasana + ", nayttonimi=" + nayttonimi + "]";
    }
}