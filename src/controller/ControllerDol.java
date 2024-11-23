package controller;


import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lombok.SneakyThrows;
import model.Doleance;
import model.Vehicule;
import service.BDoracle;
import service.DolService;
import service.VehiculeService;


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

public class ControllerDol  implements Initializable {

    @FXML
    private TableView<Doleance> tvDol;

    @FXML
    private TableColumn <Doleance, Integer> colTel;

    @FXML
    private TableColumn <Doleance, String> colMail;

    @FXML
    private TableColumn <Doleance, String> colnomprenom;

    @FXML
    private TableColumn<Doleance, Integer> colID;

    @FXML
    private TableColumn<Doleance, Integer> colCin;

    @FXML
    private TableColumn<Doleance, String> coldateaj;

    @FXML
    private TextArea tfShowTime;

    @FXML
    private TextField tfid;

    @FXML
    private TextField tftel;

    @FXML
    private TextField tfMail;

    @FXML
    private TextField tfcin;

    @FXML
    private TextField tfnom;

    @FXML
    private DatePicker tfdate;

    @FXML
    private TextArea tfdesc;

    @FXML
    private Button btnModif;

    @FXML
    private Button btnAjout;

    @FXML
    private Button btnSuprr;

    @FXML
    private TextField tfSearch;


    DolService ins = new DolService();
    DolService ins1 = new DolService();
    PreparedStatement pst = null;
    ObservableList<Doleance> listM;
    ObservableList<Doleance> dataList;
    int index = -1;
    java.sql.Connection conn = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        updateTable();
        search_user();
        String uniqueID = UUID.randomUUID().toString();
        System.out.println(uniqueID);

    }

    public void handlebtnAdd(MouseEvent event){
        if (event.getSource() == btnAjout) {
            if ((!(tfid.getText().isEmpty())) && (check(String.valueOf(tfdate.getValue() )) )){
                if(isStringInteger(tfid.getText(),10)) {
                    System.out.println("dss");
                    if (verifyLenght(tfcin.getText())) {
                        System.out.println("dss");
                        DolService vs = new DolService();
                        int value1 = Integer.parseInt(tfid.getText());
                        int value2 = Integer.parseInt(tfcin.getText());
                        String value3 = tfnom.getText();
                        String value4 = String.valueOf(tfdate.getValue());
                        String value5 = tfdesc.getText();
                        int value6 = Integer.parseInt(tftel.getText());
                        String value7 = tfMail.getText();

                        Doleance vc = new Doleance(value1, value2, value3, value4, value5, value6, value7);
                        vs.adddol(vc);
                        updateTable();
                        search_user();
                        tfid.setText("");
                        tfcin.setText("");
                        tfnom.setText("");
                        tfid.requestFocus();
                        tftel.setText("");
                        tfMail.setText("");

                    }
                    else {
                        JOptionPane.showMessageDialog(null, "longueur de Cin diff de 8");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Information saisie incorrecte");
                }
            }
        }
    }

    public void handlebtnModif (MouseEvent event) throws IOException {
        if (event.getSource() == btnModif) {
            if (!(tfid.getText().isEmpty())) {
                DolService vs = new DolService();
                int value1 = Integer.parseInt(tfid.getText());
                int value2 = Integer.parseInt(tfcin.getText());
                String value3 = tfnom.getText();
                String value4 = String.valueOf(tfdate.getValue());
                String value5 = tfdesc.getText();
                int value6 = Integer.parseInt(tftel.getText());
                String value7 = tfMail.getText();

                Doleance vc = new Doleance(value1,value2,value3,value4,value5,value6,value7);
                vs.modifierDol(vc);
                updateTable();
                search_user();
            }
        }
    }

    public void handleButtonSupp(MouseEvent event) throws IOException {
        if (event.getSource() == btnSuprr) {
            if (!(tfid.getText().isEmpty())) {
                DolService v = new DolService();
                v.deleteDol(tfid.getText());
                updateTable();
                search_user();
            }
        }
    }
//    public void Delete () {
//        conn = BDoracle.ConnectDb();
//        String sql = "delete from dol where id = ?";
//        try {
//            pst = conn.prepareStatement(sql);
//            pst.setInt(1, Integer.parseInt(tfid.getText()));
//            pst.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Suppresion avec succès");
//            updateTable();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Echec de Suppression");
//        }
//
//    }
    @SneakyThrows
    public void updateTable() {
        colID.setCellValueFactory(new PropertyValueFactory<Doleance, Integer>("id"));
        colCin.setCellValueFactory(new PropertyValueFactory<Doleance, Integer>("cin"));
        colnomprenom.setCellValueFactory(new PropertyValueFactory<Doleance, String>("nomprenom"));
        coldateaj.setCellValueFactory(new PropertyValueFactory<Doleance, String>("dateaj"));
        colTel.setCellValueFactory(new PropertyValueFactory<Doleance, Integer>("tel"));
        colMail.setCellValueFactory(new PropertyValueFactory<Doleance, String>("mail"));

        listM = BDoracle.getDataDol();
        tvDol.setItems(listM);
    }


    public void getSelected() {
        index = tvDol.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        tfid.setText(colID.getCellData(index).toString());
        tfcin.setText(String.valueOf(colCin.getCellData(index)));
        tfnom.setText(colnomprenom.getCellData(index));
        tfdate.setValue(LocalDate.parse((coldateaj.getCellData(index))));
        tfdesc.setText(dataList.get(index).getDescription());
        tftel.setText(String.valueOf(dataList.get(index).getTel()));
        tfMail.setText(String.valueOf(dataList.get(index).getMail()));

    }


    @FXML
    void search_user() {
        colID.setCellValueFactory(new PropertyValueFactory<Doleance,Integer>("id"));
        colCin.setCellValueFactory(new PropertyValueFactory<Doleance,Integer>("cin"));
        coldateaj.setCellValueFactory(new PropertyValueFactory<Doleance,String>("dateaj"));
        colnomprenom.setCellValueFactory((new PropertyValueFactory<Doleance,String>("nomprenom")));
        colTel.setCellValueFactory(new PropertyValueFactory<Doleance, Integer>("tel"));
        colMail.setCellValueFactory(new PropertyValueFactory<Doleance, String>("mail"));
        dataList = BDoracle.getDataDol();
        tvDol.setItems(dataList);

        FilteredList<Doleance> filteredData = new FilteredList<>(dataList, b -> true);
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(person.getId()).toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches matricule
                }

                else if (String.valueOf(person.getCin()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches etat
                }

                else if (person.getNomprenom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches dateaj
                }

                else if (String.valueOf(person.getTel()).indexOf(lowerCaseFilter)!=-1)
                    return true;// Filter matches nb

                else if (String.valueOf(person.getMail()).indexOf(lowerCaseFilter)!=-1)
                    return true;// Filter matches nb

                else if (String.valueOf(person.getDateaj()).indexOf(lowerCaseFilter)!=-1)
                    return true;// Filter matches nb

                else
                    return false; // Does not match.
            });
        });
        SortedList<Doleance> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvDol.comparatorProperty());
        tvDol.setItems(sortedData);
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

    public boolean verifyLenght(String s) {
        int x;
        x = s.length();
        boolean r = false;
        if (x == 8)
            r = true;
        return r;
    }
}



