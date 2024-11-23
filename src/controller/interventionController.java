package controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import model.Intervention;
import service.BDoracle;
import service.InterService;
import javax.swing.*;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.UUID;

public class interventionController implements Initializable {

    @FXML
    private TableView<Intervention> tvInterv;

    @FXML
    private TextField tfid;

    @FXML
    private DatePicker tfdateaj;

    @FXML
    private DatePicker tfdated;

    @FXML
    private DatePicker tfdatef;

    @FXML
    private TextField tfbug;

    @FXML
    private TextField tfadrs;

    @FXML
    private TextField tfsearch;

    @FXML
    private TableColumn<Intervention, Integer> colid;

    @FXML
    private TableColumn<Intervention, String> coldateaj;

    @FXML
    private TableColumn<Intervention, String> coldated;

    @FXML
    private TableColumn<Intervention, String> coldatef;

    @FXML
    private TableColumn<Intervention, String> colbug;

    @FXML
    private TableColumn<Intervention, String> coladrs;

    @FXML
    private Button btnajt;

    @FXML
    private Button btnmodif;

    @FXML
    private Button btnsuppr;

    @FXML
    private Button btnReturn;

    @FXML
    private Button btnplanif;


    ObservableList<Intervention> listi;
    ObservableList<Intervention> dataListi;
    int index = -1;
    java.sql.Connection conn = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateTable();
        search_user();
        String uniqueID = UUID.randomUUID().toString();
        System.out.println(uniqueID);

    }

    public void handleBtnAdd(MouseEvent event) {
        if (event.getSource() == btnajt) {
            if (!(tfid.getText().isEmpty())) {
                if ((isStringInteger(tfid.getText(), 10)) && (check(String.valueOf(tfdateaj.getValue())) && check(String.valueOf(tfdated.getValue()))&&check(String.valueOf(tfdatef.getValue())) ) ){
                    if(isStringInteger(tfbug.getText(), 10)){
                        System.out.println("uu");
                        InterService vs = new InterService();
                        int value1 = Integer.parseInt(tfid.getText());
                        String value2 = String.valueOf(tfdateaj.getValue());
                        String value3 = String.valueOf(tfdated.getValue());
                        String value4 = String.valueOf(tfdatef.getValue());
                        String value5 = tfbug.getText();
                        String value6 = tfadrs.getText();
                        Intervention o = new Intervention(value1, value2, value3, value4, value5,value6);
                        vs.addInter(o);
                        updateTable();
                        search_user();
                        tfid.setText("");
                        tfadrs.setText("");
                        tfbug.setText("");
                        tfdateaj.setValue(LocalDate.parse(" "));
                        tfdated.setValue(LocalDate.parse(" "));
                        tfdatef.setValue(LocalDate.parse(" "));
                        tfid.requestFocus();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Buget saisie incorrecte");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Information saisie incorrecte");
                }
            }
        }
    }

    public void handlebtnModif(MouseEvent event) throws IOException {
        conn = BDoracle.ConnectDb();
        if (event.getSource() == btnmodif) {
            if (!(tfid.getText().isEmpty())) {
                InterService vs = new InterService();
                int value1 = Integer.parseInt(tfid.getText());
                String value2 = String.valueOf(tfdateaj.getValue());
                String value3 = String.valueOf(tfdated.getValue());
                String value4 = String.valueOf(tfdatef.getValue());
                String value5 = tfbug.getText();
                String value6 = tfadrs.getText();
                Intervention o = new Intervention(value1, value2, value3, value4, value5,value6);
                vs.modifInter(o);
                updateTable();
                search_user();
            }
        }
    }

    public void handleButtonSupp(MouseEvent event) throws IOException {
        if (event.getSource() == btnsuppr) {
            if (!(tfid.getText().isEmpty())) {
                InterService v = new InterService();
                v.delInter(tfid.getText());
                updateTable();
                search_user();
            }
            else {
                JOptionPane.showMessageDialog(null, "Rien est selectionner");
            }
        }
    }
    @SneakyThrows
    public void onReturnClicked(MouseEvent event) {

        Parent root = FXMLLoader.load(getClass().getResource("../templates/interventionXML.fxml"));
        Stage stage = (Stage) btnReturn.getScene().getWindow();
        stage.setScene(new Scene(root, 1366, 768));

    }

    @SneakyThrows
    public void onPlanifClicked(MouseEvent event) {

            Parent root = FXMLLoader.load(getClass().getResource("../templates/plans.fxml"));
            Stage stage = (Stage) btnplanif.getScene().getWindow();
            stage.setScene(new Scene(root, 1366, 768));

    }


    public void updateTable() {
        colid.setCellValueFactory(new PropertyValueFactory<Intervention, Integer>("id"));
        coldateaj.setCellValueFactory(new PropertyValueFactory<Intervention, String>("dateaj"));
        coldated.setCellValueFactory(new PropertyValueFactory<Intervention, String>("dated"));
        coldatef.setCellValueFactory(new PropertyValueFactory<Intervention, String>("datef"));
        colbug.setCellValueFactory(new PropertyValueFactory<Intervention, String>("buget"));
        coladrs.setCellValueFactory(new PropertyValueFactory<Intervention, String>("adresse"));

        listi = BDoracle.getdatainterv();
        tvInterv.setItems(listi);
    }

    public void getSelected() {
        index = tvInterv.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        tfid.setText(colid.getCellData(index).toString());
        tfdateaj.setValue(LocalDate.parse(coldateaj.getCellData(index)));
        tfdated.setValue(LocalDate.parse(dataListi.get(index).getDated()));
        tfdatef.setValue(LocalDate.parse(dataListi.get(index).getDatef()));
        tfbug.setText(dataListi.get(index).getBuget());
        tfadrs.setText(dataListi.get(index).getAdresse());
    }

    @FXML
    void search_user() {
        colid.setCellValueFactory(new PropertyValueFactory<Intervention, Integer>("id"));
        coldateaj.setCellValueFactory(new PropertyValueFactory<Intervention, String>("dateaj"));
        coldated.setCellValueFactory(new PropertyValueFactory<Intervention, String>("dated"));
        coldatef.setCellValueFactory(new PropertyValueFactory<Intervention, String>("datef"));
        colbug.setCellValueFactory(new PropertyValueFactory<Intervention, String>("buget"));
        coladrs.setCellValueFactory(new PropertyValueFactory<Intervention, String>("adresse"));
        dataListi = BDoracle.getdatainterv();
        tvInterv.setItems(dataListi);

        FilteredList<Intervention> filteredData = new FilteredList<>(dataListi, b -> true);
        tfsearch.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredData.setPredicate(o -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(o.getId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches id
                } else if (String.valueOf(o.getDatef()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches quantite
                } else if (String.valueOf(o.getDatef()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches dateaj
                } else if (String.valueOf(o.getDated()).toLowerCase().indexOf(lowerCaseFilter) != -1)
                    return true;// Filter matches etat
                else if (o.getBuget().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true ;
                }
                else if (o.getAdresse().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches type
                }
                else
                    return false; // Does not match.
            });
        });
        SortedList<Intervention> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvInterv.comparatorProperty());
        tvInterv.setItems(sortedData);
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

