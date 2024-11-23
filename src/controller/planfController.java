package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.*;
import service.BDoracle;
import service.PlanService;

import javax.swing.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

public class planfController implements Initializable {

    @FXML
    private TableColumn<Outils, Integer> colIDoutil;

    @FXML
    private TableColumn<Outils, Integer> colQant;

    @FXML
    private TableColumn<Vehicule, Integer> colidVehic;

    @FXML
    private TableColumn<Vehicule, String> colDispoVehi;

    @FXML
    private TableColumn<Employe, Integer> colidemp;

    @FXML
    private TableColumn<Employe, String> colRole;

    @FXML
    private TextField tfidEmploye;

    @FXML
    private TextField tfidinterv;

    @FXML
    private DatePicker tfdated;

    @FXML
    private DatePicker tfdatef;

    @FXML
    private TextField tfidoutils;

    @FXML
    private TextField tfidVehc;

    @FXML
    private TextField tfQuantite;

    @FXML
    private Button btnplannifier;

    @FXML
    private Button btnplannifierEmp;

    @FXML
    private Button btnR;

    @FXML
    private Button btnplannifierOutil;

    @FXML
    private TableView<Outils> tvOutil;

    @FXML
    private TableView<Vehicule> tvVehicule;

    @FXML
    private TableView<Employe> tvEmploye;

    @FXML
    private TableView<Intervention> tvInterv;

    @FXML
    private TableColumn<Intervention, Integer> colidIntervention;

    @FXML
    private TableColumn<Intervention, String> colDated;

    @FXML
    private TableColumn<Intervention, String> colDatefin;


    ObservableList<Intervention> listInterv;
    ObservableList<Intervention> dataListi = BDoracle.getdatainterv();

    ObservableList<Outils> listOutil;
    ObservableList<Outils> dataListo = BDoracle.getDataOutils();
    ;

    ObservableList<Vehicule> listVehicule;
    ObservableList<Vehicule> dataListv = BDoracle.getDataVehicules();

    ObservableList<Employe> listEmp;
    ObservableList<Employe> dataListEmp = BDoracle.getdataEmp();
    ;

    ObservableList<InterVehic> dataInteVeh = BDoracle.getDataInterVehicule();
    ObservableList<InterOutil> dataInterOutil = BDoracle.getDataInterOutil();
    ObservableList<InterEmp> dataInterEmp = BDoracle.getDataInterEmp();

    int indexi = -1;
    int indexe = -1;
    int indexo = -1;
    int indexv = -1;

    java.sql.Connection conn = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTable();
    }

    public void updateTable() {
        colidemp.setCellValueFactory(new PropertyValueFactory<Employe, Integer>("cin"));
        colRole.setCellValueFactory(new PropertyValueFactory<Employe, String>("role"));

        colIDoutil.setCellValueFactory(new PropertyValueFactory<Outils, Integer>("id"));
        colQant.setCellValueFactory(new PropertyValueFactory<Outils, Integer>("quantite"));

        colidVehic.setCellValueFactory(new PropertyValueFactory<Vehicule, Integer>("matricule"));
        colDispoVehi.setCellValueFactory(new PropertyValueFactory<Vehicule, String>("etat"));

        colidIntervention.setCellValueFactory(new PropertyValueFactory<Intervention, Integer>("id"));
        colDated.setCellValueFactory(new PropertyValueFactory<Intervention, String>("dated"));
        colDatefin.setCellValueFactory(new PropertyValueFactory<Intervention, String>("datef"));

        listInterv = BDoracle.getdatainterv();
        tvInterv.setItems(listInterv);

        listEmp = BDoracle.getdataEmp();
        tvEmploye.setItems(listEmp);

        listOutil = BDoracle.getDataOutils();
        tvOutil.setItems(listOutil);

        listVehicule = BDoracle.getDataVehicules();
        tvVehicule.setItems(listVehicule);
    }

    public void getSelectedVec() {
        indexv = tvVehicule.getSelectionModel().getSelectedIndex();
        if (indexv <= -1) {
            return;
        }
        tfidVehc.setText(colidVehic.getCellData(indexv).toString());
    }

    public void getSelectedEMP() {
        indexe = tvEmploye.getSelectionModel().getSelectedIndex();
        if (indexe <= -1) {
            return;
        }
        tfidEmploye.setText(colidemp.getCellData(indexe).toString());
    }

    public void getSelectedOutil() {
        indexo = tvOutil.getSelectionModel().getSelectedIndex();
        if (indexo <= -1) {
            return;
        }
        tfidoutils.setText(colIDoutil.getCellData(indexo).toString());
        //tfQuantite.setText(colQant.getCellData(indexo).toString());
    }

    public void getSelectedInter() {
        indexi = tvInterv.getSelectionModel().getSelectedIndex();
        if (indexi <= -1) {
            return;
        }
        tfidinterv.setText(colidIntervention.getCellData(indexi).toString());
    }

    public void handlebtnReserver(MouseEvent event) {
        if (event.getSource() == btnplannifier) {
            if (!(tfidinterv.getText().isEmpty()) && !(tfidVehc.getText().isEmpty()) && !(String.valueOf(tfdated.getValue()).isEmpty()) && !(String.valueOf(tfdatef.getValue()).isEmpty())) {
                if (dataListv.get(indexv).getEtat().equals("disponible")) {
                    LocalDate dated = tfdated.getValue();
                    LocalDate datef = tfdatef.getValue();
                    if (datef.compareTo(dated) > 0) {
                        if (dated.compareTo(LocalDate.parse(dataListi.get(indexi).getDated()))>0 && datef.compareTo(LocalDate.parse(dataListi.get(indexi).getDatef()))<0){
                            boolean o = false;
                        int p = 0;
                        while (p < dataInteVeh.size() && o == false) {
                            if (tfidVehc.getText().equals(String.valueOf(dataInteVeh.get(p).getIdVec()))) {
                                o = true;
                                System.out.println(dataInteVeh.get(p).getIdVec());
                            } else {
                                p++;
                            }
                        }
                        boolean x = true;
                        int i = 0;
                        if (o) {
                            while (i < dataInteVeh.size() && x == true) {

                                SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

                                Date sdd = null;
                                Date sdf = null;
                                Date dd = null;
                                Date df = null;
                                try {
                                    sdd = sdformat.parse(String.valueOf(tfdated.getValue()));
                                    sdf = sdformat.parse(String.valueOf(tfdatef.getValue()));
                                    dd = sdformat.parse(String.valueOf(dataInteVeh.get(i).getDated()));
                                    df = sdformat.parse(String.valueOf(dataInteVeh.get(i).getDatef()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                System.out.println(sdd);
                                System.out.println(sdf);
                                System.out.println(dd);
                                System.out.println(df);

                                System.out.println(sdf.compareTo(dd));
                                System.out.println(sdd.compareTo(df));
                                if (tfidVehc.getText().equals(String.valueOf(dataInteVeh.get(i).getIdVec()))) {
                                    if (sdf.compareTo(dd) < 0) {
                                        x = false;
                                        System.out.println("wp ");
                                    } else if (sdd.compareTo(df) > 0) {
                                        x = false;
                                        System.out.println("df");
                                    } else {
                                        System.out.println("continue ");
                                    }
                                } else {
                                    x = false;
                                }
                                i++;
                            }
                        } else {
                            x = false;
                        }
                        System.out.println(x);

                        if (!x) {

                            PlanService vs = new PlanService();

                            int value1 = Integer.parseInt(tfidinterv.getText());
                            int value2 = Integer.parseInt(tfidVehc.getText());
                            String value3 = String.valueOf(tfdated.getValue());
                            String value4 = String.valueOf(tfdatef.getValue());

                            vs.addInterVec(value1, value2, value3, value4);
                            updateTable();
                        } else {
                            JOptionPane.showMessageDialog(null, "Date occupe pour autre intervention !!");
                        }
                    }
                    else {
                            JOptionPane.showMessageDialog(null, "Date choisie est invalide  ");
                    }
                }else {
                        JOptionPane.showMessageDialog(null, "Date fin reservation est plus recent que date reservation !! ");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La vehicule est indisoponible pour le moment ");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Informations manquantes!! ");
            }
        }
    }

    public void handleReserverOutil(MouseEvent event) {
        if (event.getSource() == btnplannifierOutil) {
            System.out.println("begin process");
            if (!(tfidinterv.getText().isEmpty()) && !(tfidoutils.getText().isEmpty())) {
                if (!(tfQuantite.getText().isEmpty())) {
                    LocalDate dated = tfdated.getValue();
                    LocalDate datef = tfdatef.getValue();
                    if (datef.compareTo(dated) > 0) {
                        if (dated.compareTo(LocalDate.parse(dataListi.get(indexi).getDated()))>0 && datef.compareTo(LocalDate.parse(dataListi.get(indexi).getDatef()))<0) {
                            boolean o = false;
                            int p = 0;
                            while (p < dataInterOutil.size() && o == false) {
                                if (tfidoutils.getText().equals(String.valueOf(dataInterOutil.get(p).getIdoutil()))) {
                                    o = true;
                                    System.out.println(dataInterOutil.get(p).getIdoutil());
                                } else {
                                    p++;
                                }
                            }
                            System.out.println("trouve = " + o);
                            int q = dataListo.get(indexo).getQuantite();
                            if (isStringInteger(tfQuantite.getText(), 10)) {
                                if (Integer.parseInt(tfQuantite.getText()) <= q) {
                                    boolean x = true;
                                    int i = 0;
                                    if (o) {
                                        System.out.println("getting in while");
                                        while (i < dataInterOutil.size() && x == true) {
                                            if (tfidoutils.getText().equals(String.valueOf(dataInterOutil.get(i).getIdoutil()))) {
                                                SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
                                                Date sdd = null;
                                                Date sdf = null;
                                                Date dd = null;
                                                Date df = null;
                                                try {
                                                    sdd = sdformat.parse(String.valueOf(tfdated.getValue()));
                                                    sdf = sdformat.parse(String.valueOf(tfdatef.getValue()));
                                                    dd = sdformat.parse(String.valueOf(dataInterOutil.get(i).getDated()));
                                                    df = sdformat.parse(String.valueOf(dataInterOutil.get(i).getDatef()));
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                if (o) {
                                                    if (sdf.compareTo(dd) < 0) {
                                                        x = false;
                                                        System.out.println("wp ");
                                                    } else if (sdd.compareTo(df) > 0) {
                                                        x = false;
                                                        System.out.println("df");
                                                    } else {
                                                        System.out.println("continue ");
                                                    }
                                                } else {
                                                    x = false;
                                                }
                                            }
                                            i++;
                                        }
                                    } else {
                                        System.out.println("getting beside while");
                                        x = false;
                                    }
                                    System.out.println(x);
                                    if (!x) {

                                        System.out.println("u_u");
                                        PlanService vs = new PlanService();
                                        int value1 = Integer.parseInt(tfidinterv.getText());
                                        int value2 = Integer.parseInt(tfidoutils.getText());
                                        String value3 = String.valueOf(tfdated.getValue());
                                        String value4 = String.valueOf(tfdatef.getValue());
                                        int value5 = Integer.parseInt(tfQuantite.getText());

                                        vs.addInterOutil(value1, value2, value3, value4, value5);
                                        updateTable();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Date occupe pour autre intervention !!");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, " Quantite choisie est incorrect  !! ");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, " Quantite choisie est incorrect  !! ");
                            }
                        }else {  JOptionPane.showMessageDialog(null, "Date choisie invalide  ");}
                    } else {
                        JOptionPane.showMessageDialog(null, "Date fin reservation est plus recent que date reservation !!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Quntite manquantes !! ");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Informations manquantes !! ");
            }
        }
    }

    public void handlebtnReserverEmp(MouseEvent event) {
        if (event.getSource() == btnplannifierEmp) {
            if (!(tfidinterv.getText().isEmpty()) && !(tfidEmploye.getText().isEmpty()) && !(String.valueOf(tfdated.getValue()).isEmpty()) && !(String.valueOf(tfdatef.getValue()).isEmpty())) {
                LocalDate dated = tfdated.getValue();
                LocalDate datef = tfdatef.getValue();
                if (datef.compareTo(dated) > 0) {
                    if (dated.compareTo(LocalDate.parse(dataListi.get(indexi).getDated())) > 0 && datef.compareTo(LocalDate.parse(dataListi.get(indexi).getDatef())) < 0) {

                        boolean o = false;
                        int p = 0;

                        while (p < dataInterEmp.size() && o == false) {
                            if (tfidEmploye.getText().equals(String.valueOf(dataInterEmp.get(p).getIdemp()))) {
                                o = true;
                            } else {
                                p++;
                            }
                        }

                        boolean x = true;
                        int i = 0;
                        if (o) {
                            while (i < dataInterEmp.size() && x == true) {
                                System.out.println("yo");
                                if (tfidEmploye.getText().equals(String.valueOf(dataInterEmp.get(i).getIdemp()))) {
                                    SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

                                    Date sdd = null;
                                    Date sdf = null;
                                    Date dd = null;
                                    Date df = null;
                                    try {
                                        sdd = sdformat.parse(String.valueOf(tfdated.getValue()));
                                        sdf = sdformat.parse(String.valueOf(tfdatef.getValue()));
                                        dd = sdformat.parse(String.valueOf(dataInterEmp.get(i).getDated()));
                                        df = sdformat.parse(String.valueOf(dataInterEmp.get(i).getDatef()));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    if (sdf.compareTo(dd) < 0) {
                                        x = false;
                                        System.out.println("wp ");
                                    } else if (sdd.compareTo(df) > 0) {
                                        x = false;
                                        System.out.println("df");
                                    } else {
                                        System.out.println("continue ");
                                        x = true;
                                    }
                                    i++;
                                } else {
                                    i++;
                                }
                            }
                        } else {
                            System.out.println("getting beside while");
                            x = false;
                        }
                        System.out.println(x);
                        if (!x) {
                            PlanService vs = new PlanService();

                            int value1 = Integer.parseInt(tfidinterv.getText());
                            int value2 = Integer.parseInt(tfidEmploye.getText());
                            String value3 = String.valueOf(tfdated.getValue());
                            String value4 = String.valueOf(tfdatef.getValue());

                            vs.addInterEmp(value1, value2, value3, value4);
                            updateTable();
                        } else {
                            JOptionPane.showMessageDialog(null, "Date occupe pour autre intervention !!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Date fin reservation est plus recent que date reservation !! ");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Date choisie invalide  ");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Informations manquantes!! ");
            }
        }
    }

    public static boolean isStringInteger(String stringToCheck, int radix) {
        Scanner sc = new Scanner(stringToCheck.trim());
        if (!sc.hasNextInt(radix)) return false;
        sc.nextInt(radix);
        return !sc.hasNext();
    }

    public static boolean isStringEmpty(String str) {
        if (str.isEmpty())
            return true;
        else
            return false;
    }

    public static int stringCompare(String str1, String str2) {

        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);

        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int) str1.charAt(i);
            int str2_ch = (int) str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }

        if (l1 != l2) {
            return l1 - l2;
        } else {
            return 0;
        }
    }



// if ((datef.compareTo(LocalDate.parse(dataInteVeh.get(i).getDated())) > 0 || (dated.compareTo(LocalDate.parse(dataInteVeh.get(i).getDatef())) < 0))) {
//        for (int i = 0; i < dataInteVeh.size(); i++) {
//        if (dated.compareTo(LocalDate.parse(dataInteVeh.get(i).getDatef())) > 0) {
//            x = true;
//            System.out.println("worng1");
//        } else if (datef.compareTo(LocalDate.parse(dataInteVeh.get(i).getDated())) < 0) {
//            x = true;
//            System.out.println("worng2");
//        } else {
//            x = false;
//            System.out.println("acceptable");
//        }

}


