package model;

public class Vehicule {
    private int matricule;
    private String marque;
    private int nb;
    private String dateaj;
    private String etat;

    public Vehicule(int matricule ,String marque,int nb,String dateaj, String etat ) {
        this.matricule = matricule;
        this.nb = nb;
        this.dateaj = dateaj;
        this.etat = etat;
        this.marque = marque;
    }

    public Vehicule(int matricule, String text, int parseInt, String text1) {
    }

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }

    public String getDateaj() {
        return dateaj;
    }

    public void setDateaj(String dateaj) {
        this.dateaj = dateaj;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

}
