package model;

public class InterEmp {
    private int idinter;
    private int idemp ;
    private String dated ;
    private String datef ;

    public InterEmp(int idinter, int idemp, String dated, String datef) {
        this.idinter = idinter;
        this.idemp = idemp;
        this.dated = dated;
        this.datef = datef;
    }

    public int getIdinter() {
        return idinter;
    }

    public void setIdinter(int idinter) {
        this.idinter = idinter;
    }

    public int getIdemp() {
        return idemp;
    }

    public void setIdemp(int idemp) {
        this.idemp = idemp;
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
}
