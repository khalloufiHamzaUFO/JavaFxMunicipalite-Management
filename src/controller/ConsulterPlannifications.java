package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.*;
import service.BDoracle;
import service.PlanService;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConsulterPlannifications  implements Initializable {
    @FXML
    private TableView<InterOutil> tvinteroutil;

    @FXML
    private TableView<InterVehic> tvintervehic;

    @FXML
    private TableView<InterEmp> tvinteremp;

    @FXML
    private TableColumn<InterOutil,Integer> colInterOutl;

    @FXML
    private TableColumn<InterOutil, Integer> colidOutil;

    @FXML
    private TableColumn<InterOutil,String> coldated;

    @FXML
    private TableColumn<InterOutil,String> coldatef;

    @FXML
    private TableColumn<InterOutil, Integer> colqunt;

    @FXML
    private TableColumn<InterVehic,Integer> colinterVehic;

    @FXML
    private TableColumn<InterVehic,Integer> colidVehic;

    @FXML
    private TableColumn<InterVehic,String> coldatedV;

    @FXML
    private TableColumn<InterVehic,String> coldatefV;

    @FXML
    private TableColumn<InterEmp,Integer> colinterEmp;

    @FXML
    private TableColumn<InterEmp, Integer> colEmp;

    @FXML
    private TableColumn<InterEmp, String> coldatedEmp;

    @FXML
    private TableColumn<InterEmp, String> colDatefEmp;

    @FXML
    private Button btnAnuulerOutil;

    @FXML
    private Button btnAnuulerEmp;

    @FXML
    private Button btnAnnulerVehic;

    @FXML
    private TextField tfidintervO;

    @FXML
    private TextField tfidInterVeh;

    @FXML
    private TextField tfidInterEmp;

    @FXML
    private TextField tfidvehic;

    @FXML
    private TextField tfidoutil;

    @FXML
    private TextField tfidEmp;


    ObservableList<InterVehic> listeintervechi = BDoracle.getDataInterVehicule();
    ObservableList<InterOutil> listeinterOutil = BDoracle.getDataInterOutil();
    ObservableList<InterEmp> listeInterEMP = BDoracle.getDataInterEmp();


    int indexv = -1;
    int indexe = -1;
    int indexo = -1;

    java.sql.Connection conn = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTable();
    }

    public void updateTable() {
        colInterOutl.setCellValueFactory(new PropertyValueFactory<InterOutil, Integer>("idinter"));
        colidOutil.setCellValueFactory(new PropertyValueFactory<InterOutil, Integer>("idoutil"));
        coldated.setCellValueFactory(new PropertyValueFactory<InterOutil, String>("dated"));
        coldatef.setCellValueFactory(new PropertyValueFactory<InterOutil, String>("datef"));
        colqunt.setCellValueFactory(new PropertyValueFactory<InterOutil, Integer>("qunt"));

        colidVehic.setCellValueFactory(new PropertyValueFactory<InterVehic, Integer>("idVec"));
        colinterVehic.setCellValueFactory(new PropertyValueFactory<InterVehic, Integer>("idInter"));
        coldatedV.setCellValueFactory(new PropertyValueFactory<InterVehic, String>("dated"));
        coldatefV.setCellValueFactory(new PropertyValueFactory<InterVehic, String>("datef"));

        colEmp.setCellValueFactory(new PropertyValueFactory<InterEmp, Integer>("idemp"));
        colDatefEmp.setCellValueFactory(new PropertyValueFactory<InterEmp, String>("datef"));
        colinterEmp.setCellValueFactory(new PropertyValueFactory<InterEmp, Integer>("idinter"));
        coldatedEmp.setCellValueFactory(new PropertyValueFactory<InterEmp, String>("dated"));

        listeintervechi = BDoracle.getDataInterVehicule();
        tvintervehic.setItems(listeintervechi);

        listeInterEMP = BDoracle.getDataInterEmp();
        tvinteremp.setItems(listeInterEMP);

        listeinterOutil = BDoracle.getDataInterOutil();
        tvinteroutil.setItems(listeinterOutil);

    }

    public void getSelectedInterVec() {
        indexv = tvintervehic.getSelectionModel().getSelectedIndex();
        if (indexv <= -1) {
            return;
        }
        tfidvehic.setText(colidVehic.getCellData(indexv).toString());
        tfidInterVeh.setText(colinterVehic.getCellData(indexv).toString());
    }

    public void getSelectedInterEMP() {
        indexe = tvinteremp.getSelectionModel().getSelectedIndex();
        if (indexe <= -1) {
            return;
        }
        tfidEmp.setText(colEmp.getCellData(indexe).toString());
        tfidInterEmp.setText(colinterEmp.getCellData(indexe).toString());
    }

    public void getSelectedInterOutil() {
        indexo = tvinteroutil.getSelectionModel().getSelectedIndex();
        if (indexo <= -1) {
            return;
        }
        tfidoutil.setText(colidOutil.getCellData(indexo).toString());
        tfidintervO.setText(colInterOutl.getCellData(indexo).toString());
    }

    public void handleButtonAnnulerEmp(MouseEvent event) throws IOException {
        if (event.getSource() == btnAnuulerEmp) {
            if (!(tfidInterEmp.getText().isEmpty()) && !(tfidEmp.getText().isEmpty())) {
                PlanService v = new PlanService();
                v.deleteInterEmp(tfidInterEmp.getText(),tfidEmp.getText());
                updateTable();
            }
            else {
                JOptionPane.showMessageDialog(null, "Pas de selection de table intervention-Employe ");
            }
        }
    }

    public void handleButtonAnnulerVehic(MouseEvent event) throws IOException {
        if (event.getSource() == btnAnnulerVehic) {
            if (!(tfidInterVeh.getText().isEmpty()) && !(tfidvehic.getText().isEmpty())) {
                PlanService v = new PlanService();
                v.deleteInterVec(tfidInterVeh.getText(),tfidvehic.getText());
                updateTable();
            }
            else {
                JOptionPane.showMessageDialog(null, "Pas de selection de table intervention-Vehicule ");
            }
        }
    }

    public void handleButtonAnnulerOutil(MouseEvent event) throws IOException {
        if (event.getSource() == btnAnuulerOutil) {
            if (!(tfidintervO.getText().isEmpty()) && !(tfidoutil.getText().isEmpty())) {
                PlanService v = new PlanService();
                v.deleteInterOutil(tfidintervO.getText(),tfidoutil.getText());
                updateTable();
            }
            else {
                JOptionPane.showMessageDialog(null, "Pas de selection de table intervention-Outil ");
            }
        }
    }
}
