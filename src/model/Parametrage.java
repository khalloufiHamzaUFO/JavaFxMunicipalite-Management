package model;

public class Parametrage {
    private int id ;
    private String adresse ;
    private String contact ;
    private String gouvernorat ;
    private String pays ;

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Parametrage(int id, String adresse, String contact, String gouvernorat,String pays) {
        this.id = id;
        this.adresse = adresse;
        this.contact = contact;
        this.gouvernorat = gouvernorat;
        this.pays = pays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGouvernorat() {
        return gouvernorat;
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
    }
}
