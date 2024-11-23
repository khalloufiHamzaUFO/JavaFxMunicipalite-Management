package service;

import lombok.SneakyThrows;
import model.Intervention;
import repository.InterRep;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class InterService implements InterRep {

    Statement con = null;
    static Connection conn = null;
    ResultSet rs = null;
    static PreparedStatement pst = null;

    public InterService() {
        try {
            con = BDoracle.ConnectDb().createStatement();
            System.out.println("instance created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public void addInter(Intervention o) {
        conn = BDoracle.ConnectDb();
        String sql = "insert into INTERV (id,dateaj,dd,df,buget,adresse)values(?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, o.getId());
            pst.setString(2, o.getDateaj());
            pst.setString(3, o.getDated());
            pst.setString(4, o.getDatef());
            pst.setString(5, o.getBuget());
            pst.setString(6, o.getAdresse());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Ajout avec succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur");
        }
    }

    public void modifInter(Intervention o) throws IOException {
        int value1 = o.getId() ;
        String value2 = o.getDateaj();
        String value3 = o.getDated();
        String value4 = o.getDatef();
        String value5 = o.getBuget();
        String valuex = o.getAdresse();
        try {
            conn = BDoracle.ConnectDb();
            String sql = "update INTERV set id= '" + value1 + "',dateaj = '" + value2 + "',dd= '" +
                    value3 + "',df= '" + value4 + "',buget= '" + valuex + "',adresse= '" + value5 + "' where id='" + value1 + "' ";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Modification avec succes");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void delInter(String m) {
        conn = BDoracle.ConnectDb();
        String sql = "delete from INTERV where id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(m));
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprission avec succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur !!");
        }
    }
}