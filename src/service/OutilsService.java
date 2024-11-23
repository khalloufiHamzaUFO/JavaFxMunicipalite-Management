package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.SneakyThrows;
import model.Outils;
import repository.OutilsRepository;
import javax.swing.*;
import java.io.IOException;
import java.sql.*;


public class OutilsService implements OutilsRepository {

    Statement con = null;
    static Connection conn = null;
    ResultSet rs = null;
    static PreparedStatement pst = null;

    public OutilsService() {
        try {
            con = BDoracle.ConnectDb().createStatement();
            System.out.println("instance created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean find(int c) {

        boolean test = false;
        BDoracle bd = null;


        ResultSet r;
        try {
            conn = BDoracle.ConnectDb();

            String s = "select * from outils where id='c'";
            String q = String.valueOf(c);

            PreparedStatement ps = conn.prepareStatement("select * from outils where id= '" + c + "'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                test = true;
            }


        } catch (SQLException throwables) {
            System.out.println("erreur");
            throwables.printStackTrace();
        }
        return test;
    }


    @SneakyThrows
    public void addOutil(Outils o) {
        conn = BDoracle.ConnectDb();
        String sql = "insert into outils (id , quantite,dateaj,etat,reference,type)values(?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, o.getId());
            pst.setInt(2, o.getQuantite());
            pst.setString(3, o.getDateaj());
            pst.setString(4, o.getEtat());
            pst.setString(5, o.getReference());
            pst.setString(6,o.getType());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Ajout avec succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur");
        }
    }

    public void modifierOutil(int value1,String valuex, int value2, String value3, String value4, String value5 ) throws IOException {

        try {
            conn = BDoracle.ConnectDb();
            String sql = "update outils set id= '" + value1 + "',quantite = '" + value2 + "',dateaj= '" +
                    value3 + "',etat= '" + value4 + "',reference= '" + valuex + "',type= '" + value5 + "' where id='" + value1 + "' ";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Modification avec succes");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public ObservableList<Outils> getDataOutils() {
        Connection conn = BDoracle.ConnectDb();
        ObservableList<Outils> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from outils");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Outils(rs.getInt("id"),rs.getString("reference"), rs.getInt("quantite"), rs.getString("dateaj"), rs.getString("etat") ,rs.getString("type")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void deleteOutil(String m) {
        conn = BDoracle.ConnectDb();
        String sql = "delete from outils where id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(m));
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprission avec succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}