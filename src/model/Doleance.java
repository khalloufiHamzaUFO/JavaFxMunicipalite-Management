package model;

public class Doleance {
    private int id ;
    private int cin ;
    private String nomprenom ;
    private String dateaj ;
    private String description ;
    private int tel ;
    private String mail;



    public void setNomprenom(String nomprenom) {
        this.nomprenom = nomprenom;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Doleance(int id, int cin , String nomprenom, String dateaj, String description , int tel, String mail) {
        this.id = id;
        this.cin = cin ;
        this.nomprenom = nomprenom;
        this.dateaj = dateaj;
        this.description = description;
        this.tel = tel ;
        this.mail = mail ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getNomprenom() {
        return nomprenom;
    }

    public void setNomPrenom(String nomprenom) {
        this.nomprenom = nomprenom;
    }

    public String getDateaj() {
        return dateaj;
    }

    public void setDateaj(String dateaj) {
        this.dateaj = dateaj;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
