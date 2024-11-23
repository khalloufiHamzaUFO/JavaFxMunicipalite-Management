package service;


import model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class BDoracle {

    Connection conn = null;

    public static Connection ConnectDb() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "TEST1", "123456");
            System.out.println("Driver Loaded");// JOptionPane.showMessageDialog(null, "Connection Established");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }

    }

    public static ObservableList<Vehicule> getDataVehicules() {
        Connection conn = ConnectDb();
        ObservableList<Vehicule> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from VEHICULES");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Vehicule(rs.getInt("MATRICULE"), rs.getString("MARQUE") ,rs.getInt("NB"), rs.getString("DATEAJ"), rs.getString("ETAT")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static ObservableList<Outils> getDataOutils() {
        Connection conn = BDoracle.ConnectDb();
        ObservableList<Outils> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from outils");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Outils(rs.getInt("id"), rs.getString("reference"),rs.getInt("quantite"), rs.getString("dateaj"), rs.getString("etat"),rs.getString("type")));
            }

        } catch (Exception e) {
        }
        return list;
    }

    public static ObservableList<Revenu> getDataRevenu() {
        Connection conn = BDoracle.ConnectDb();
        ObservableList<Revenu> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from REVEVUS");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Revenu(rs.getInt("id"),rs.getString("reference"),rs.getString("montant"),rs.getString("dateaj"),rs.getString("descr"),rs.getString("type")));
            }

        } catch (Exception e) {
        }
        return list;
    }

    public static ObservableList<Doleance> getDataDol() {
        Connection conn = BDoracle.ConnectDb();
        ObservableList<Doleance> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from DOL");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Doleance(rs.getInt("id"), rs.getInt("CiN"), rs.getString("NOMPRENOM"), rs.getString("DATEAJ"),rs.getString("DESCRIPTION"), rs.getInt("tel"), rs.getString("mail")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static ObservableList<Intervention> getdatainterv() {
        Connection conn = BDoracle.ConnectDb();
        ObservableList<Intervention> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from INTERV");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Intervention(rs.getInt("id"), rs.getString("dateaj"), rs.getString("DD"), rs.getString("DF"),rs.getString("buget"), rs.getString("adresse")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static ObservableList<Employe> getdataEmp() {
        Connection conn = BDoracle.ConnectDb();
        ObservableList<Employe> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from personnel");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Employe(rs.getInt("cin"), rs.getString("nom"), rs.getString("prenom"), rs.getString("dateaj"),rs.getString("adresse"), rs.getString("role")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static ObservableList<InterVehic> getDataInterVehicule() {
        Connection conn = BDoracle.ConnectDb();
        ObservableList<InterVehic> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from INTERVEHICULE");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new InterVehic(rs.getInt("IDINTER"), rs.getInt("IDVEHICULE"), rs.getString("DATED"), rs.getString("DATEF")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static ObservableList<InterOutil> getDataInterOutil() {
        Connection conn = BDoracle.ConnectDb();
        ObservableList<InterOutil> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from INTEROUTIL");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new InterOutil(rs.getInt("IDINTER"), rs.getInt("IDOUTIL"), rs.getString("DATED"), rs.getString("DATEF") ,rs.getInt("qunt")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static ObservableList<InterEmp> getDataInterEmp() {
        Connection conn = BDoracle.ConnectDb();
        ObservableList<InterEmp> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from INTEREMP");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new InterEmp(rs.getInt("IDINTER"), rs.getInt("IDEMP"), rs.getString("DATED"), rs.getString("DATEF")));
            }
        } catch (Exception e) {
        }
        return list;
    }

}