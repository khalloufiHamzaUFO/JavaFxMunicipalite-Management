package model;

public class InterVehic {
    private int idInter ;
    private int idVec ;
    private String dated ;
    private String datef ;

    public InterVehic(int idInter, int idVec, String dated, String datef) {
        this.idInter = idInter;
        this.idVec = idVec;
        this.dated = dated;
        this.datef = datef;
    }

    public int getIdInter() {
        return idInter;
    }

    public void setIdInter(int idInter) {
        this.idInter = idInter;
    }

    public int getIdVec() {
        return idVec;
    }

    public void setIdVec(int idVec) {
        this.idVec = idVec;
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
