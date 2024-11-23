package controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Vehicule;
import service.BDoracle;
import service.VehiculeService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;



public class Consultervehicule implements Initializable {

    @FXML
    private TableView<Vehicule> tvVehicule;

    @FXML
    private TableColumn<Vehicule, Integer> colMatricule;

    @FXML
    private TableColumn<Vehicule, String> colMrq;

    @FXML
    private TableColumn<Vehicule, Integer> colnb;

    @FXML
    private TableColumn<Vehicule, String> coldateaj;

    @FXML
    private TableColumn<Vehicule, String> coletat;

    @FXML
    private DatePicker tfdateaj2;

    @FXML
    private ComboBox<String> lblchoice;

    @FXML
    private Button btnmodifier;

    @FXML
    private Button btnsupprimer;

    @FXML
    private TextField tfsearch;

    @FXML
    private Button btnajout;

    @FXML
    private TextField tfmatricule;

    @FXML
    private TextField tfmarque;

    @FXML
    private TextField tfnb;

    @FXML
    private Button btnRetour;

    ObservableList<Vehicule> listM;
    ObservableList<Vehicule> dataList;
    int index = -1;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateTable();
        search_user();
        lblchoice.getItems().add("disponible");
        lblchoice.getItems().add("indisponible");
    }

    public void handleButtonModifier(MouseEvent event) throws IOException {
        if (event.getSource() == btnmodifier) {
            if (!(tfmatricule.getText().isEmpty())) {
                VehiculeService vs = new VehiculeService();
                int value1 = Integer.parseInt(tfmatricule.getText());
                String value2 = tfmarque.getText();
                int value3 = Integer.parseInt(tfnb.getText());
                String value4 = String.valueOf(tfdateaj2.getValue());
                String value5 = lblchoice.getValue();

                Vehicule vc = new Vehicule(value1,value2,value3,value4,value5);
                vs.modifiermVehiculeDAO(vc);
                updateTable();
                search_user();
            }
        }
    }

    public void handleButtonAjout(MouseEvent event) throws IOException{
        if (event.getSource() == btnajout) {
            if ((!(tfmatricule.getText().isEmpty())) && (check(String.valueOf(tfdateaj2.getValue() )) )){
                if(isStringInteger(tfmatricule.getText(),10)) {
                    VehiculeService vs = new VehiculeService();
                    int value1 = Integer.parseInt(tfmatricule.getText());
                    String value2 = tfmarque.getText();
                    int value3 = Integer.parseInt(tfnb.getText());
                    String value4 = String.valueOf(tfdateaj2.getValue());
                    String value5 = lblchoice.getValue();

                    Vehicule vc = new Vehicule(value1, value2, value3, value4, value5);
                    vs.addVehicule(vc);
                    updateTable();
                    search_user();
                    tfmatricule.setText("");
                    tfmarque.setText("");
                    tfnb.setText("");
                    tfmatricule.requestFocus();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Information saisie incorrecte");
                }
            }
        }
    }

    public void handleButtonSupp(MouseEvent event) throws IOException {
        if (event.getSource() == btnsupprimer) {
            if (!(tfmatricule.getText().isEmpty())) {
                VehiculeService v = new VehiculeService();
                v.DeleteVehicule(tfmatricule.getText());
                updateTable();
                search_user();
            }
        }
    }

    public void updateTable() {
        colMatricule.setCellValueFactory(new PropertyValueFactory<Vehicule, Integer>("matricule"));
        colMrq.setCellValueFactory(new PropertyValueFactory<Vehicule, String>("marque"));
        colnb.setCellValueFactory(new PropertyValueFactory<Vehicule, Integer>("nb"));
        coldateaj.setCellValueFactory(new PropertyValueFactory<Vehicule, String>("dateaj"));
        coletat.setCellValueFactory(new PropertyValueFactory<Vehicule, String>("etat"));
        listM = BDoracle.getDataVehicules();
        tvVehicule.setItems(listM);
    }

    public void getSelected() {
        index = tvVehicule.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        tfmatricule.setText(colMatricule.getCellData(index).toString());
        tfmarque.setText(colMrq.getCellData(index));
        tfnb.setText(colnb.getCellData(index).toString());
        tfdateaj2.setValue(LocalDate.parse((coldateaj.getCellData(index))));
        lblchoice.setValue(coletat.getCellData(index));

        updateTable();
        search_user();
    }

    @FXML
    void search_user() {
        colMatricule.setCellValueFactory(new PropertyValueFactory<Vehicule,Integer>("Matricule"));
        colMrq.setCellValueFactory(new PropertyValueFactory<Vehicule,String>("marque"));
        colnb.setCellValueFactory(new PropertyValueFactory<Vehicule,Integer>("nb"));
        coldateaj.setCellValueFactory(new PropertyValueFactory<Vehicule,String>("dateaj"));
        coletat.setCellValueFactory(new PropertyValueFactory<Vehicule,String>("etat"));

        dataList = BDoracle.getDataVehicules();
        tvVehicule.setItems(dataList);

        FilteredList<Vehicule> filteredData = new FilteredList<>(dataList, b -> true);
        tfsearch.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(person.getMatricule()).toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches matricule
                }

                else if (person.getEtat().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches etat
                }

                else if (person.getDateaj().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches dateaj
                }

                else if (String.valueOf(person.getNb()).indexOf(lowerCaseFilter)!=-1)
                    return true;// Filter matches nb

                else
                    return false; // Does not match.
            });
        });
        SortedList<Vehicule> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvVehicule.comparatorProperty());
        tvVehicule.setItems(sortedData);
    }

    public static boolean isStringInteger(String stringToCheck, int radix) {
        Scanner sc = new Scanner(stringToCheck.trim());
        if(!sc.hasNextInt(radix)) return false;
        sc.nextInt(radix);
        return !sc.hasNext();
    }

    public static boolean check(String date)
    {
        // Définir le format date préféré
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        try
        {
            Date d = format.parse(date);
            System.out.println(date+" est une date valide");
        }
        // Date invalide
        catch (ParseException e)
        {
            System.out.println(date+" est une date invalide");
            return false;
        }
        return true;
    }
}



//    public void Add_vehicule (){
//        conn = BDoracle.ConnectDb();
//        String sql = "insert into vehicules (matricule ,marque,nb,dateaj,etat)values(?,?,?,?,?)";
//        try {
//            pst = conn.prepareStatement(sql);
//            pst.setInt(1, Integer.parseInt(tfmatricule.getText()));
//            pst.setString(2, tfmarque.getText());
//            pst.setInt(3, Integer.parseInt(tfnb.getText()));
//            pst.setString(4, String.valueOf(tfdateaj2.getValue()));
//            pst.setString(5, String.valueOf(lblchoice.getValue()));
//            pst.executeUpdate();
//            tfmatricule.setText("");
//            tfmarque.setText("");
//            tfnb.setText("");
//            tfmatricule.requestFocus();
//
//            JOptionPane.showMessageDialog(null, "Ajout avec succes");
//            updateTable();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Erreur");
//        }
//    }

//    public void DeleteVehicule () {
//        conn = BDoracle.ConnectDb();
//        String sql = "delete from VEHICULES where MATRICULE = ?";
//        try {
//            pst = conn.prepareStatement(sql);
//            pst.setInt(1, Integer.parseInt(tfmatricule.getText()));
//            pst.execute();
//            JOptionPane.showMessageDialog(null, "Suppresion avec succès");
//            updateTable();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Echec de Suppression");
//        }
//    }

//   public void Edit (){
//        try {
//            conn = BDoracle.ConnectDb();
//            int value1 = Integer.parseInt(tfmatricule.getText());
//            String value2 = tfmarque.getText();
//            int value3 = Integer.parseInt(tfnb.getText());
//            String value4 = String.valueOf(tfdateaj2.getValue());
//            String value5 = lblchoice.getValue();
//
//            String sql = "update vehicules set matricule= '"+value1+"',marque= '"+value2+"',nb= '"+
//                    value3+"',dateaj= '"+value4+"',etat= '"+value5+"' where matricule='"+value1+"' ";
//            pst= conn.prepareStatement(sql);
//            pst.execute();
//            JOptionPane.showMessageDialog(null, "Modification avec suces");
//            updateTable();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
//
//    }
