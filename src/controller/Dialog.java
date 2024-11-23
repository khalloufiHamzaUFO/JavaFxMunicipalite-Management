package controller;

import javafx.scene.control.Alert;

public class Dialog {

    public static void showConfirmationDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public static void showWarningDialog(String title, String header, String content) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();

    }

    public static void showErrorDialog(String title, String header, String content) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();

    }
}
