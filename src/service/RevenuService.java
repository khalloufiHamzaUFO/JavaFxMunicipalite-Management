package service;

import lombok.SneakyThrows;
import model.Revenu;
import repository.RevenusRep;
import javax.swing.*;
import java.io.IOException;
import java.sql.*;

public class RevenuService implements RevenusRep {

    Statement con = null;
    static Connection conn = null;
    ResultSet rs = null;
    static PreparedStatement pst = null;

    public RevenuService() {
        try {
            con = BDoracle.ConnectDb().createStatement();
            System.out.println("instance created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SneakyThrows
    public void addRev(Revenu o) {
        conn = BDoracle.ConnectDb();
        String sql = "insert into REVEVUS (id,reference,montant,dateaj,descr,type)values(?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, o.getId());
            pst.setString(2, o.getRef());
            pst.setString(3, o.getMontant());
            pst.setString(4, o.getDateaj());
            pst.setString(5, o.getDescription());
            pst.setString(6, o.getType());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Ajout avec succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void modifRev(int value1, String value2, String value3, String value4,String value5,String value6) throws IOException {

        try {
            conn = BDoracle.ConnectDb();
            String sql = "update REVEVUS set id= '" + value1 + "',reference = '" + value2 + "',montant= '" +
                    value3 + "',dateaj= '" + value4 + "',descr= '" + value5 + "',type= '" + value6 + "' where id='" + value1 + "' ";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Modification avec succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void deleteRev(String m) {
        conn = BDoracle.ConnectDb();
        String sql = "delete from REVEVUS where id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(m));
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprission avec succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}