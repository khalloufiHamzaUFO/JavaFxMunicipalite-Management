package controller;


import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Outils;
import service.BDoracle;
import service.OutilsService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.UUID;

public class outilsController implements Initializable {

    @FXML
    private TableView<Outils> tvOutils;

    @FXML
    private TableColumn<Outils, Integer> colID;

    @FXML
    private TableColumn<Outils, Integer> colQuant;

    @FXML
    private TableColumn<Outils, String> colDate;

    @FXML
    private TableColumn<Outils, String> colRef;

    @FXML
    private TableColumn<Outils, String> colEtat;

    @FXML
    private TableColumn<Outils, String> coltype;

    @FXML
    private Button btnAjout;

    @FXML
    private Button btnModif;

    @FXML
    private Button btnSuppr;

    @FXML
    private TextField tfID;

    @FXML
    private TextField tfQuantite;

    @FXML
    private TextField tfRef;

    @FXML
    private DatePicker tfDateaj;

    @FXML
    private ChoiceBox<String> lblChoice;

    @FXML
    private ChoiceBox<String> lbltype;

    @FXML
    private TextField tfSearch;
    
    ObservableList<Outils> listo;
    ObservableList<Outils> dataListo;
    int index = -1;
    java.sql.Connection conn = null;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateTable();
        search_user();
        String uniqueID = UUID.randomUUID().toString();
        System.out.println(uniqueID);
        lblChoice.getItems().add("disponible");
        lblChoice.getItems().add("indisponible");
        lbltype.getItems().add("Consumable ");
        lbltype.getItems().add("NonConsumable");
    }

    public void handleBtnAddoutil(MouseEvent event) {
        if (event.getSource() == btnAjout) {
            if (!(tfID.getText().isEmpty())) {
                if ((isStringInteger(tfID.getText(), 10)) && (check(String.valueOf(tfDateaj.getValue()))) ){
                    System.out.println("uu");
                    OutilsService vs = new OutilsService();
                    int value1 = Integer.parseInt(tfID.getText());
                    String value2 = tfRef.getText();
                    int value3 = Integer.parseInt(tfQuantite.getText());
                    String value4 = String.valueOf(tfDateaj.getValue());
                    String value5 = lblChoice.getValue();
                    String valuex = lbltype.getValue();
                    Outils o = new Outils(value1, value2, value3, value4, value5,valuex);
                    vs.addOutil(o);
                    updateTable();
                    search_user();
                    tfID.setText("");
                    tfRef.setText("");
                    tfQuantite.setText("");
                    tfID.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Information saisie incorrecte");
                }
            }
        }
    }

    public void handlebtnModif(MouseEvent event) throws IOException {
        conn = BDoracle.ConnectDb();
        if (event.getSource() == btnModif) {
            if (!(tfID.getText().isEmpty())) {
                OutilsService vs = new OutilsService();
                int value1 = Integer.parseInt(tfID.getText());
                String valuex = tfRef.getText();
                int value2 = Integer.parseInt(tfQuantite.getText());
                String value3 = String.valueOf(tfDateaj.getValue());
                String value4 = lblChoice.getValue();
                String valuexx = lbltype.getValue();
                vs.modifierOutil(value1, valuex, value2, value3, value4,valuexx);
                updateTable();
                search_user();
            }
        }
    }

    public void handleButtonSupp(MouseEvent event) throws IOException {
        if (event.getSource() == btnSuppr) {
            if (!(tfID.getText().isEmpty())) {
                OutilsService v = new OutilsService();
                v.deleteOutil(tfID.getText());
                updateTable();
                search_user();
            }
        }
    }

//    public void deleteOutils() {
//        conn = BDoracle.ConnectDb();
//        String sql = "delete from outils where id = ?";
//        try {
//            pst = conn.prepareStatement(sql);
//            pst.setInt(1, Integer.parseInt(tfID.getText()));
//            pst.execute();
//            JOptionPane.showMessageDialog(null, "Suppresion avec succès");
//            updateTable();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Echec de Suppression");
//        }
//    }

    public void updateTable() {
        colID.setCellValueFactory(new PropertyValueFactory<Outils, Integer>("id"));
        colRef.setCellValueFactory(new PropertyValueFactory<Outils, String>("reference"));
        colQuant.setCellValueFactory(new PropertyValueFactory<Outils, Integer>("quantite"));
        colDate.setCellValueFactory(new PropertyValueFactory<Outils, String>("dateaj"));
        colEtat.setCellValueFactory(new PropertyValueFactory<Outils, String>("etat"));
        coltype.setCellValueFactory(new PropertyValueFactory<Outils, String>("type"));

        listo = BDoracle.getDataOutils();
        tvOutils.setItems(listo);
    }

    public void getSelected() {
        index = tvOutils.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        tfID.setText(colID.getCellData(index).toString());
        tfQuantite.setText(colQuant.getCellData(index).toString());
        tfDateaj.setValue(LocalDate.parse(colDate.getCellData(index)));
        lblChoice.setValue(colDate.getCellData(index));
        lbltype.setValue(colDate.getCellData(index));
        tfRef.setText(colRef.getCellData(index));
    }

    @FXML
    void search_user() {
        colID.setCellValueFactory(new PropertyValueFactory<Outils, Integer>("id"));
        colQuant.setCellValueFactory(new PropertyValueFactory<Outils, Integer>("quantite"));
        colDate.setCellValueFactory(new PropertyValueFactory<Outils, String>("dateaj"));
        colEtat.setCellValueFactory(new PropertyValueFactory<Outils, String>("etat"));
        colRef.setCellValueFactory(new PropertyValueFactory<Outils, String>("reference"));
        coltype.setCellValueFactory(new PropertyValueFactory<Outils, String>("type"));
        dataListo = BDoracle.getDataOutils();
        tvOutils.setItems(dataListo);

        FilteredList<Outils> filteredData = new FilteredList<>(dataListo, b -> true);
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredData.setPredicate(o -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(o.getId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches id
                } else if (String.valueOf(o.getQuantite()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches quantite
                } else if (o.getDateaj().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches dateaj
                } else if (o.getEtat().toLowerCase().indexOf(lowerCaseFilter) != -1)
                    return true;// Filter matches etat
                else if (o.getReference().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true ;
                }
                else if (o.getType().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches type
                }
                else
                    return false; // Does not match.
            });
        });
        SortedList<Outils> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvOutils.comparatorProperty());
        tvOutils.setItems(sortedData);
    }

    public static boolean isStringInteger(String stringToCheck, int radix) {
        Scanner sc = new Scanner(stringToCheck.trim());
        if (!sc.hasNextInt(radix)) return false;
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

