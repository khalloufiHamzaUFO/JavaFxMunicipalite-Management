package model;

public class Intervention {
    private int id;
    private String dateaj;
    private String dated;
    private String datef;
    private String buget;
    private String adresse;

    public Intervention(int id, String dateaj, String dated, String datef, String buget, String adresse) {
        this.id = id;
        this.dateaj = dateaj;
        this.dated = dated;
        this.datef = datef;
        this.buget = buget;
        this.adresse = adresse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateaj() {
        return dateaj;
    }

    public void setDateaj(String dateaj) {
        this.dateaj = dateaj;
    }

    public String getDated() {
        return dated;
    }

    public void setDated(String dated) {
        this.dated = dated;
    }

    public String getDatef() {
        return datef;
    }

    public void setDatef(String datef) {
        this.datef = datef;
    }

    public String getBuget() {
        return buget;
    }

    public void setBuget(String buget) {
        this.buget = buget;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}