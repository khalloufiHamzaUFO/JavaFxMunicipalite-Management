package service;

import lombok.SneakyThrows;
import model.Revenu;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PlanService {
    Statement con = null;
    static Connection conn = null;
    ResultSet rs = null;
    static PreparedStatement pst = null;

    public PlanService() {
        try {
            con = BDoracle.ConnectDb().createStatement();
            System.out.println("instance created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public void addInterVec(int v1 ,int v2 ,String v3,String v4) {
        conn = BDoracle.ConnectDb();
        String sql = "insert into INTERVEHICULE (IDINTER,IDVEHICULE,DATED,DATEF)values(?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, v1);
            pst.setInt(2, v2);
            pst.setString(3,v3);
            pst.setString(4,v4);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Ajout avec succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @SneakyThrows
    public void addInterOutil(int v1 ,int v2 ,String v3,String v4,int v5 ) {
        conn = BDoracle.ConnectDb();
        String sql = "insert into INTEROUTIL (IDINTER,IDOUTIL,DATED,DATEF,QUNT)values(?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, v1);
            pst.setInt(2, v2);
            pst.setString(3,v3);
            pst.setString(4,v4);
            pst.setInt(5, v5);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Ajout avec succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void addInterEmp(int v1 ,int v2 ,String v3,String v4) {
        conn = BDoracle.ConnectDb();
        String sql = "insert into INTEREMP (IDINTER,IDEMP,DATED,DATEF)values(?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, v1);
            pst.setInt(2, v2);
            pst.setString(3,v3);
            pst.setString(4,v4);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Ajout avec succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void deleteInterVec(String m,String m2) {
        conn = BDoracle.ConnectDb();
        String sql = "delete from INTERVEHICULE where IDINTER = ? and IDVEHICULE = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(m));
            pst.setInt(2, Integer.parseInt(m2));
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprission avec succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void deleteInterEmp(String m,String m2) {
        conn = BDoracle.ConnectDb();
        String sql = "delete from INTEREMP where IDINTER = ? and IDEMP = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(m));
            pst.setInt(2, Integer.parseInt(m2));
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprission avec succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void deleteInterOutil(String m,String m2) {
        conn = BDoracle.ConnectDb();
        String sql = "delete from INTEROUTIL where IDINTER = ? and IDOUTIL = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(m));
            pst.setInt(2, Integer.parseInt(m2));
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprission avec succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


}
