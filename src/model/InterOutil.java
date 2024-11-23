package model;

public class InterOutil {
    private int idinter;
    private int idoutil ;
    private String dated ;
    private String datef ;
    private int qunt ;

    public InterOutil(int idinter, int idoutil, String dated, String datef, int qunt) {
        this.idinter = idinter;
        this.idoutil = idoutil;
        this.dated = dated;
        this.datef = datef;
        this.qunt = qunt;
    }

    public int getIdinter() {
        return idinter;
    }

    public void setIdinter(int idinter) {
        this.idinter = idinter;
    }

    public int getIdoutil() {
        return idoutil;
    }

    public void setIdoutil(int idoutil) {
        this.idoutil = idoutil;
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

    public int getQunt() {
        return qunt;
    }

    public void setQunt(int qunt) {
        this.qunt = qunt;
    }
}
