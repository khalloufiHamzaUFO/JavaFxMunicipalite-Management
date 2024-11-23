package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.SneakyThrows;
import model.Vehicule;
import repository.VehiculeRepository;
import javax.swing.*;
import java.io.IOException;
import java.sql.*;



public class VehiculeService implements VehiculeRepository {

    Statement con = null;
    Connection conn ;
    static PreparedStatement pst = null;

    public VehiculeService() {
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

            String s = "select * from VEHICULES where matricule='c'";
            String q = String.valueOf(c);

            PreparedStatement ps = conn.prepareStatement("select * from VEHICULES where matricule= '" + c + "'");
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


    public ObservableList<Vehicule> getDataVehicules() {
        Connection conn = BDoracle.ConnectDb();
        ObservableList<Vehicule> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from VEHICULES");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Vehicule(rs.getInt("MATRICULE"), rs.getString("MARQUE"), rs.getInt("NB"), rs.getString("DATEAJ"), rs.getString("ETAT")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    @SneakyThrows
    public void addVehicule(Vehicule vc) {
        conn = BDoracle.ConnectDb();
        String sql = "insert into vehicules (matricule ,marque,nb,dateaj,etat)values(?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, vc.getMatricule());
            pst.setString(2, vc.getMarque());
            pst.setInt(3, vc.getNb());
            pst.setString(4, vc.getDateaj());
            pst.setString(5, vc.getEtat());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Ajout avec succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur");
        }
    }

    public void modifiermVehiculeDAO(Vehicule vc) throws IOException {

        try {
            conn = BDoracle.ConnectDb();
            int value1 = vc.getMatricule();
            String value2 = vc.getMarque();
            int value3 = vc.getNb();
            String value4 = vc.getDateaj();
            String value5 = vc.getEtat();

            String sql = "update VEHICULES set matricule='" + value1 + "',marque = '" + value2 + "',nb= '" +
                    value3 + "',dateaj= '" + value4 + "',etat='" + value5 + "'  where matricule='" + value1 + "' ";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "modification avec succes");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void DeleteVehicule(String m) {
        conn = BDoracle.ConnectDb();
        String sql = "delete from VEHICULES where MATRICULE = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(m));
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprission avec succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Echec de Suppression");
        }
    }
}

