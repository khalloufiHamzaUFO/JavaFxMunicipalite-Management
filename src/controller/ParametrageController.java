package controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Parametrage;
import model.Vehicule;
import service.BDoracle;
import service.ParametrageService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ParametrageController implements Initializable {
    @FXML
    private TextField tfid;

    @FXML
    private TextField tfadr;

    @FXML
    private TextField tfCont;

    @FXML
    private TextField tfGouv;

    @FXML
    private TextField tfPays;

    @FXML
    private TableView<Parametrage> tvParametre;

    @FXML
    private TableColumn<Parametrage,Integer > colId;

    @FXML
    private TableColumn<Parametrage, String> colAdresse;

    @FXML
    private TableColumn<Parametrage, String> colContact;

    @FXML
    private TableColumn<Parametrage, String> colGouv;

    @FXML
    private TableColumn<Parametrage, String> colpays;

    @FXML
    private Button btnAjout;

    @FXML
    private Button btnModif;

    @FXML
    private Button btnSuprim;

    ParametrageService vs = new ParametrageService();
    ObservableList<Parametrage> listP;
    ObservableList<Parametrage> dataList;
    int index = -1;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateTable();
    }

    public void handleButtonModifier(MouseEvent event) throws IOException {
        if (event.getSource() == btnModif) {
            if (!(tfid.getText().isEmpty())) {
                int value1 = Integer.parseInt(tfid.getText());
                String value2 = tfadr.getText();
                String value3 = tfCont.getText();
                String value4 = tfGouv.getText();
                String value5 = tfPays.getText();

                Parametrage vc = new Parametrage(value1,value2,value3,value4,value5);
                vs.modifierParametrage(vc);
                updateTable();
            }
        }
    }

    public void handleButtonAjout(MouseEvent event) throws IOException{
        if (event.getSource() == btnAjout) {
            if ((!(tfid.getText().isEmpty()))){
                if(isStringInteger(tfid.getText(),10)) {
                    if(Integer.valueOf(tfCont.getText())/10000000<1){
                    int value1 = Integer.parseInt(tfid.getText());
                    String value2 = tfadr.getText();
                    String value3 = tfCont.getText();
                    String value4 = tfGouv.getText();
                    String value5 = tfPays.getText();

                    Parametrage vc = new Parametrage(value1, value2, value3, value4, value5);
                    vs.addParametre(vc);
                    updateTable();
                    tfid.setText("");
                    tfadr.setText("");
                    tfCont.setText("");
                    tfid.requestFocus();
                    tfPays.setText("");
                    tfGouv.setText("");
                }else {
                        JOptionPane.showMessageDialog(null, "Numero du contact est incorrecte ");
                }

                }else {
                    JOptionPane.showMessageDialog(null, "Id saisie incorrecte");
                }
            }
        }
    }

    public void handleButtonSupp(MouseEvent event) throws IOException {
        if (event.getSource() == btnSuprim) {
            if (!(tfid.getText().isEmpty())) {
                vs.DeleteParametrage(tfid.getText());
                updateTable();
                tfid.setText("");
                tfadr.setText("");
                tfCont.setText("");
                tfid.requestFocus();
                tfPays.setText("");
                tfGouv.setText("");
            }
        }
    }

    public void updateTable() {
        colId.setCellValueFactory(new PropertyValueFactory<Parametrage, Integer>("id"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<Parametrage, String>("adresse"));
        colContact.setCellValueFactory(new PropertyValueFactory<Parametrage, String>("contact"));
        colGouv.setCellValueFactory(new PropertyValueFactory<Parametrage, String>("gouvernorat"));
        colpays.setCellValueFactory(new PropertyValueFactory<Parametrage, String>("pays"));
        listP = vs.getDataParametre();
        tvParametre.setItems(listP);
    }

    public void getSelected() {
        index = tvParametre.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        tfid.setText(colId.getCellData(index).toString());
        tfadr.setText(colAdresse.getCellData(index));
        tfCont.setText(colContact.getCellData(index));
        tfGouv.setText(colGouv.getCellData(index));
        tfPays.setText(colpays.getCellData(index));

        updateTable();

    }

    public static boolean isStringInteger(String stringToCheck, int radix) {
        Scanner sc = new Scanner(stringToCheck.trim());
        if(!sc.hasNextInt(radix)) return false;
        sc.nextInt(radix);
        return !sc.hasNext();
    }


}


