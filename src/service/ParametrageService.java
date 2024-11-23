package service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Parametrage;
import repository.ParametrageRep;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;


public class ParametrageService implements ParametrageRep {

    Statement con = null;
    static Connection conn = null;
    ResultSet rs = null;
    static PreparedStatement pst = null;

    public ParametrageService() {
        try {
            con = BDoracle.ConnectDb().createStatement();
            System.out.println("instance created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addParametre(Parametrage p) {
        conn = BDoracle.ConnectDb();
        String sql = "insert into PARAMETRE (id,adresse,contact,gouvernorat,pays)values(?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1,p.getId());
            pst.setString(2, p.getAdresse());
            pst.setString(3, p.getContact());
            pst.setString(4, p.getGouvernorat());
            pst.setString(5, p.getPays());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Ajout du parametre avec succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur");
        }
    }

    public void modifierParametrage(Parametrage p)throws IOException{
        try {
            conn = BDoracle.ConnectDb();
            String sql = "update parametre set id= '" + p.getId() + "',adresse = '" + p.getAdresse() + "',contact= '" +
                    p.getContact() + "',gouvernorat= '" + p.getGouvernorat() + "',pays= '" + p.getPays() +"' where id='" + p.getId() + "' ";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Modification avec succes");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public ObservableList<Parametrage> getDataParametre() {
        Connection conn = BDoracle.ConnectDb();
        ObservableList<Parametrage> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from parametre");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Parametrage(rs.getInt("id"),rs.getString("adresse"), rs.getString("contact"), rs.getString("gouvernorat"), rs.getString("pays")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void DeleteParametrage(String m){
        conn = BDoracle.ConnectDb();
        String sql = "delete from PARAMETRE where id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(m));
            pst.execute();
            JOptionPane.showMessageDialog(null, "Supprission des parametres avec succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }


}
