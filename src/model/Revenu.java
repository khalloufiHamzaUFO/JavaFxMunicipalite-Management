package model;

public class Revenu {
    private int id ;
    private String ref ;
    private String montant ;
    private String dateaj ;
    private String description ;
    private String Type ;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Revenu(int id, String ref, String montant, String dateaj, String description, String type) {
        this.id = id;
        this.ref = ref;
        this.montant = montant;
        this.dateaj = dateaj;
        this.description = description;
        this.Type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
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
