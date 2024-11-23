package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.prism.paint.Color;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Revenu;
import service.BDoracle;
import service.RevenuService;
import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.UUID;

public class RevenusController implements Initializable {

    @FXML
    private TableView<Revenu> tvRv;

    @FXML
    private TableColumn<Revenu, Integer> colID;

    @FXML
    private TableColumn<Revenu, String> colRef;

    @FXML
    private TableColumn<Revenu, String> colMont;

    @FXML
    private TableColumn<Revenu, String> colDate;

    @FXML
    private TableColumn<Revenu, String> colType;

    @FXML
    private Button btnAjout;

    @FXML
    private Button btnCalcule;

    @FXML
    private Button btnModif;

    @FXML
    private Button btnSuppr;

    @FXML
    private TextField tfID;

    @FXML
    private TextField tfRef;

    @FXML
    private TextField tfMnt;

    @FXML
    private DatePicker tfDateaj;

    @FXML
    private DatePicker tfDatech;

    @FXML
    private DatePicker tfDatechf;

    @FXML
    private TextArea tfDesc;

    @FXML
    private TextField tfSearch;

    @FXML
    private ChoiceBox<String> lblchx;

    @FXML
    private ChoiceBox<String> lblchoice;


    ObservableList<Revenu> listR;
    ObservableList<Revenu> dataListR;
    int index = -1;
    java.sql.Connection conn = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateTable();
        search_user();
        String uniqueID = UUID.randomUUID().toString();
        System.out.println(uniqueID);
        lblchoice.getItems().add("Revenus");
        lblchoice.getItems().add("Depence");
        lblchx.getItems().add("Revenus");
        lblchx.getItems().add("Depence");
    }

    public void handleBtnAdd(MouseEvent event) {
        if (event.getSource() == btnAjout) {
            if (!(tfID.getText().isEmpty())) {
                if ((isStringInteger(tfID.getText(), 10))) {
                    if (checkMontant(tfMnt.getText())) {
                        System.out.println("u_u");
                        RevenuService vs = new RevenuService();
                        int value1 = Integer.parseInt(tfID.getText());
                        String value2 = tfRef.getText();
                        String value3 = tfMnt.getText();
                        String value4 = String.valueOf(tfDateaj.getValue());
                        String value5 = tfDesc.getText();
                        String value6 = String.valueOf(lblchoice.getValue());
                        Revenu r = new Revenu(value1, value2, value3, value4, value5, value6);
                        vs.addRev(r);
                        updateTable();
                        search_user();
                        tfID.setText("");
                        tfRef.setText("");
                        tfMnt.setText("");
                        tfID.requestFocus();
                        tfDesc.setText("");
                    }else {
                        JOptionPane.showMessageDialog(null, "Montant saisie incorrecte");
                    }
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
                if ((isStringInteger(tfID.getText(), 10))) {
                    RevenuService vs = new RevenuService();
                    int value1 = Integer.parseInt(tfID.getText());
                    String value2 = tfRef.getText();
                    String value3 = tfMnt.getText();
                    String value4 = String.valueOf(tfDateaj.getValue());
                    String value5 = tfDesc.getText();
                    String value6 = lblchoice.getValue();
                    vs.modifRev(value1, value2, value3, value4, value5, value6);
                    updateTable();
                    search_user();
                } else
                    System.out.println("id incorrect");
            } else {
                System.out.println("id introuvable !");
            }
        }
    }

    public void handleButtonSupp(MouseEvent event) throws IOException {
        if (event.getSource() == btnSuppr) {
            if (!(tfID.getText().isEmpty())) {
                RevenuService v = new RevenuService();
                v.deleteRev(tfID.getText());
                updateTable();
                search_user();
            }
        }
    }

    public void handlebtnPrint(MouseEvent event) throws IOException, DocumentException {
        if (event.getSource() == btnCalcule) {
            if (!(String.valueOf(tfDatech.getValue()).isEmpty()) || !(String.valueOf(tfDatechf.getValue()).isEmpty() )) {
                if (check(String.valueOf(tfDatech.getValue())) && check(String.valueOf(tfDatechf.getValue()))) {
                    LocalDate dated = tfDatech.getValue();
                    LocalDate datef = tfDatechf.getValue();
                    LocalDate dateXx = LocalDate.now();
                    if (datef.compareTo(dated) > 0 ) {

                        if(datef.compareTo(dateXx)<0){
                        float sommer = 0 , sommed = 0;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String DR = lblchx.getValue();
                        String R = "Revenus";

                        Document document = new Document();
                        Font font = new Font();
                        font.setColor(BaseColor.RED);
                        try {
                            PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Hamza\\Desktop\\helloo.pdf"));
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                        document.open();
                        try {
                            document.add(new Phrase("                                                     Rapport financier ",font));
                            document.add(new Paragraph(" "));
                            document.add(new Paragraph("Du date "+tfDatech.getValue()+" Jusqu a "+tfDatechf.getValue()));
                            document.add(new Paragraph(" "));
                            document.add(new Paragraph(" "));

                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }


                        if (DR == null) {
                            PdfPTable table = new PdfPTable(3);
                            table.addCell("Type ");
                            table.addCell("Montant ");
                            table.addCell("Date ");

                            for (int i = 0; i < dataListR.size(); i++) {
                                if (LocalDate.parse(dataListR.get(i).getDateaj()).compareTo(dated) > 0 && LocalDate.parse(dataListR.get(i).getDateaj()).compareTo(datef) < 0) {

                                        table.addCell(dataListR.get(i).getType());
                                        table.addCell((Float.parseFloat(dataListR.get(i).getMontant()))+" DT");
                                        table.addCell(dataListR.get(i).getDateaj());


                                    String p = dataListR.get(i).getType() + " = " + Float.parseFloat(dataListR.get(i).getMontant()) + " dt  date :=> " + dataListR.get(i).getDateaj();
//                                    try {
//                                        document.add(new Paragraph(p));
//                                        document.add(new Paragraph(" "));
//                                    } catch (DocumentException e) {
//                                        e.printStackTrace();
//                                    }
                                    if(stringCompare(dataListR.get(i).getType(),R)==0){
                                        sommer = sommer + Float.parseFloat(dataListR.get(i).getMontant());
                                    }
                                    else {
                                        sommed = sommed + Float.parseFloat(dataListR.get(i).getMontant());
                                    }
                                }
                            }

                            String sd = "Somme des depences = "+ sommed +" dt";
                            String sr = "Somme des revenus = "+ sommer + " dt";
                            try {
                                document.add(table);
                                document.add(new Paragraph(" "));
                                document.add(new Phrase(sd,font));
                                document.add(new Paragraph(" "));
                                document.add(new Phrase(sr,font));
                            } catch (DocumentException e) {
                                e.printStackTrace();
                            }

                            document.close();
                        } else {
                            for (int i = 0; i < dataListR.size(); i++) {
                                if (dataListR.get(i).getType().equals(DR)) {
                                    if (LocalDate.parse(dataListR.get(i).getDateaj()).compareTo(dated) > 0 && LocalDate.parse(dataListR.get(i).getDateaj()).compareTo(datef) < 0) {
                                        String d = dataListR.get(i).getType() + " = " + Float.parseFloat(dataListR.get(i).getMontant()) + " dt  date :=> " + dataListR.get(i).getDateaj();
                                        sommed = sommed + Float.parseFloat(dataListR.get(i).getMontant());
                                        try {
                                            document.add(new Phrase(d));
                                            document.add(new Paragraph(" "));
                                        } catch (DocumentException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }

                            String s ="Somme de "+ DR +" = "+ sommed+ " dt ";
                            try {
                                document.add(new Phrase(s,font));
                            } catch (DocumentException e) {
                                e.printStackTrace();
                            }
                            document.close();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Date est plus Recente aue date d'aujourd'hui !! ");
                    }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Date 2 plus recente que date 1 !! ");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Date choisie est invalide");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Champs obligatoires !!");
            }
        }
    }


//    public void handleButtonCalcule(MouseEvent event) throws IOException {
//        if (event.getSource() == btnCalcule) {
//            if (!(String.valueOf(tfDatech.getValue()).isEmpty() || lblchx.getValue() == null)) {
//                if (check(String.valueOf(tfDatech.getValue()))) {
//                    float somme = 0;
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    String s = String.valueOf(tfDatech.getValue());
//                    LocalDate date = tfDatech.getValue();
//                    String DR = lblchx.getValue();
//                    for (int i = 0; i < dataListR.size(); i++) {
//                        if (dataListR.get(i).getType().equals(DR)) {
//                            if (LocalDate.parse(dataListR.get(i).getDateaj()).compareTo(date) > 0) {
//                                somme += Float.parseFloat(dataListR.get(i).getMontant());
//                            }
//                        }
//                    }
//                    JOptionPane.showMessageDialog(null, "Somme depuis cette date =" + somme + "DT");
//                } else {
//                    JOptionPane.showMessageDialog(null, "Date choisie est invalide");
//                }
//            } else {
//                JOptionPane.showMessageDialog(null, "Champs obligatoires !!");
//            }
//        }
//    }

    public void updateTable() {
        colID.setCellValueFactory(new PropertyValueFactory<Revenu, Integer>("id"));
        colRef.setCellValueFactory(new PropertyValueFactory<Revenu, String>("REF"));
        colMont.setCellValueFactory(new PropertyValueFactory<Revenu, String>("montant"));
        colDate.setCellValueFactory(new PropertyValueFactory<Revenu, String>("dateaj"));
        colType.setCellValueFactory(new PropertyValueFactory<Revenu, String>("type"));
        listR = BDoracle.getDataRevenu();
        tvRv.setItems(listR);
    }

    public void getSelected() {
        index = tvRv.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        tfID.setText(colID.getCellData(index).toString());
        tfRef.setText(colRef.getCellData(index));
        tfMnt.setText(dataListR.get(index).getMontant());
        tfDateaj.setValue(LocalDate.parse(colDate.getCellData(index)));
        tfDesc.setText(dataListR.get(index).getDescription());
    }

    @FXML
    void search_user() {
        colID.setCellValueFactory(new PropertyValueFactory<Revenu, Integer>("id"));
        colRef.setCellValueFactory(new PropertyValueFactory<Revenu, String>("ref"));
        colMont.setCellValueFactory(new PropertyValueFactory<Revenu, String>("montant"));
        colDate.setCellValueFactory((new PropertyValueFactory<Revenu, String>("dateaj")));
        colType.setCellValueFactory((new PropertyValueFactory<Revenu, String>("type")));
        dataListR = BDoracle.getDataRevenu();
        tvRv.setItems(dataListR);

        FilteredList<Revenu> filteredData = new FilteredList<>(dataListR, b -> true);
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredData.setPredicate(revenu -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(revenu.getId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches matricule
                } else if (String.valueOf(revenu.getRef()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches etat
                } else if (revenu.getMontant().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches dateaj
                } else if (String.valueOf(revenu.getDateaj()).indexOf(lowerCaseFilter) != -1)
                    return true;// Filter matches nb

                else if (revenu.getType().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches dateaj
                } else
                    return false; // Does not match.
            });
        });
        SortedList<Revenu> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvRv.comparatorProperty());
        tvRv.setItems(sortedData);
    }

    public static boolean isStringInteger(String stringToCheck, int radix) {
        Scanner sc = new Scanner(stringToCheck.trim());
        if (!sc.hasNextInt(radix)) return false;
        sc.nextInt(radix);
        return !sc.hasNext();
    }

    public static boolean check(String date) {
        // Définir le format date préféré
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        try {
            Date d = format.parse(date);
            System.out.println(date + " est une date valide");
        }
        // Date invalide
        catch (ParseException e) {
            System.out.println(date + " est une date invalide");
            return false;
        }
        return true;
    }

    public boolean checkMontant(String input) {
        // checking valid float using parseInt() method
        try {
            Float.parseFloat(input);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    public static int stringCompare(String str1, String str2)
    {

        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);

        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int)str1.charAt(i);
            int str2_ch = (int)str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }

        if (l1 != l2) {
            return l1 - l2;
        }

        else {
            return 0;
        }
    }
}



