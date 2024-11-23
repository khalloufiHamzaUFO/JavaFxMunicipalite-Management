package model;

public class Employe {
    private int cin;
    private String nom;
    private String prenom;
    private String dateaj;
    private String adresse;
    private String role ;

    public Employe(int cin, String nom, String prenom, String dateaj, String adresse, String role) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.dateaj = dateaj;
        this.adresse = adresse;
        this.role = role;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateaj() {
        return dateaj;
    }

    public void setDateaj(String dateaj) {
        this.dateaj = dateaj;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
