//package service;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import lombok.SneakyThrows;
//import model.Doleance;
//import model.Outils;
//import repository.DoleanceRep;
//import repository.OutilsRepository;
//
//import javax.swing.*;
//import java.io.IOException;
//import java.sql.*;
//
//public class DolService implements DoleanceRep {
//
//    Statement con = null;
//    static Connection conn = null;
//    ResultSet rs = null;
//    static PreparedStatement pst = null;
//
//    public DolService() {
//        try {
//            con = BDoracle.ConnectDb().createStatement();
//            System.out.println("instance created");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    @SneakyThrows
//    public void addDol(Doleance d) {
//            String sqlQuery = "INSERT INTO pfa22 (idd,cinn,nomprenomm,dateajj,dscc) VALUES (?,?,?,?,?);";
//            PreparedStatement preStm = conn.prepareStatement(sqlQuery);
//            preStm.setString(1, String.valueOf(d.getId()));
//            preStm.setString(2, String.valueOf(d.getCin()));
//            preStm.setString(3, d.getNomPrenom());
//            preStm.setString(4, d.getDateaj());
//            preStm.setString(5, d.getDescription());
//            preStm.executeUpdate();
//        }
//
//
//    @Override
//    public void deleteDol(String m) {
//        conn = BDoracle.ConnectDb();
//        String sql = "delete from pfa22 where idd = ?";
//        try {
//            pst = conn.prepareStatement(sql);
//            pst.setInt(1, Integer.parseInt(m));
//            pst.execute();
//            JOptionPane.showMessageDialog(null, "Supprission avec succes");
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }
//
//    @Override
//    public void modifierDol(int ID, int cin, String nomPrenom, String dateaj, String desc) throws IOException {
//        try {
//            conn = BDoracle.ConnectDb();
//
//
//            String sql = "update pfa22 set IDd ='" + ID + "',cinn = '" + cin + "',nomPrenomm= '" + nomPrenom + "',dateajj= '" + dateaj + "',dscc '" + desc + "' where IDd='" + ID + "' ";
//            pst = conn.prepareStatement(sql);
//            pst.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Modification avec succes");
//
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }
//
////    public ObservableList<Doleance> getDataDOl() {
////
////        Connection conn = BDoracle.ConnectDb();
////        ObservableList<Doleance> list = FXCollections.observableArrayList();
////        try {
////            PreparedStatement ps = conn.prepareStatement("select * from dol");
////            ResultSet rs = ps.executeQuery();
////
////            while (rs.next()) {
////                list.add(new Doleance(rs.getInt("id"), rs.getInt("cin"), rs.getString("nomPrenom"), rs.getString("dateaj"),rs.getString("description")));
////            }
////        } catch (Exception e) {
////        }
////        return list;
////    }
//}
package service;

        import model.Doleance;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import lombok.SneakyThrows;
        import model.Outils;
        import repository.DoleanceRep;

        import javax.swing.*;
        import java.io.IOException;
        import java.sql.*;


public class DolService implements DoleanceRep {

    Statement con = null;
    static Connection conn = null;
    ResultSet rs = null;
    static PreparedStatement pst = null;


    public DolService() {
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

            String s = "select * from dol where id='c'";
            String q = String.valueOf(c);

            PreparedStatement ps = conn.prepareStatement("select * from dol where id= '" + c + "'");
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


    @Override
    public void adddol(Doleance o) {
        conn = BDoracle.ConnectDb();
        String sqlQuery = "insert into dol (id,cin,nomprenom,dateaj,description,tel,mail) values (?,?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sqlQuery);
            pst.setInt(1, o.getId());
            pst.setInt(2, o.getCin());
            pst.setString(3, o.getNomprenom());
            pst.setString(4, o.getDateaj());
            pst.setString(5, o.getDescription());
            pst.setInt(6, o.getTel());
            pst.setString(7, o.getMail());
            pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Ajout avec succes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur");
        }
    }

    @Override
    public void modifierDol(Doleance vc) throws IOException {

        try {
            conn = BDoracle.ConnectDb();
            int value1 = vc.getId();
            int value2 = vc.getCin();
            String value3 = vc.getNomprenom();
            String value4 = vc.getDateaj();
            String value5 = vc.getDescription();
            int value6 = vc.getTel();
            String value7 = vc.getMail();

            String sql = "update dol set id='" + value1 + "',cin = '" + value2 + "',nomprenom= '" +
                    value3 + "',dateaj= '" + value4 + "',description='" + value5 + "',tel='" + value6 + "',mail='" + value7 + "'  where id='" + value1 + "' ";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "modification avec succes");


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
                list.add(new Outils(rs.getInt("id"),rs.getString("reference"), rs.getInt("quantite"), rs.getString("dateaj"), rs.getString("etat"), rs.getString("type")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void deleteDol(String m) {
        conn = BDoracle.ConnectDb();
        String sql = "delete from dol where id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(m));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Suppresion avec succès");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Echec de Suppression");
        }

    }
}