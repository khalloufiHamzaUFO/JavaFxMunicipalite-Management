package model;

public class Outils {
    private int id;
    private String reference;
    private int quantite;
    private String dateaj;
    private String etat;
    private String type ;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Outils(int id, String reference, int quantite, String dateaj, String etat,String type) {
        this.id = id;
        this.reference = reference;
        this.quantite = quantite;
        this.dateaj = dateaj;
        this.etat = etat;
        this.type = type ;
    }

    public int getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
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
}
