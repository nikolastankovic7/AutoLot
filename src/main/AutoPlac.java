package main;

import java.util.ArrayList;

public class AutoPlac {
    private int id;
    private String imeAutoPlaca;
    private String adresa;
    private String drzava;
    private ArrayList<Automobil> automobili;

    public AutoPlac(int id, String imeAutoPlaca, String adresa, String drzava, ArrayList<Automobil> automobili) {
        this.id = id;
        this.imeAutoPlaca = imeAutoPlaca;
        this.adresa = adresa;
        this.drzava = drzava;
        this.automobili = automobili;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImeAutoPlaca() {
        return imeAutoPlaca;
    }

    public void setImeAutoPlaca(String imeAutoPlaca) {
        this.imeAutoPlaca = imeAutoPlaca;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public ArrayList<Automobil> getAutomobili() {
        return automobili;
    }

    public void setAutomobili(ArrayList<Automobil> automobili) {
        this.automobili = automobili;
    }
}
