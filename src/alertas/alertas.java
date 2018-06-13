
package alertas;

import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import org.controlsfx.control.Notifications;
public class alertas {
    
        public static void arlertaInformacion(String titulo, String contenido) {

        Alert alert = new Alert(AlertType.INFORMATION);

        //alert.getDialogPane().getStylesheets().addAll("/alertas/boton2.css");
        alert.setTitle(titulo);
        //alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();

    }
   
   

    public static boolean alertaConfirmacion(String titulo, String Cabecera, String pregunta) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(Cabecera);
        alert.setContentText(pregunta);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            return true;
        } else {
            // ... user chose CANCEL or closed the dialog
            return false;
        }
    }

    public static void alertaDeExcepcion(String titulo, String cabecera, String contexto, String ErrorExcepcion) {

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecera);
        alert.setContentText(contexto);

        Label label = new Label("La excepción fue:");

        TextArea textArea = new TextArea(ErrorExcepcion);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();

    }

    public static void arlertaInformacion(String titulo, String cabecera, String contenido) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        //alert.getDialogPane().getStylesheets().addAll("/alertas/boton2.css");
        alert.getDialogPane().setStyle("-fx-background-color: #FAFAFA;");
        alert.setTitle(titulo);
        alert.setHeaderText(cabecera);
        alert.setContentText(contenido);

        alert.showAndWait();
    }

    public static void alertaError(String titulo, String cabecera, String contenido) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.initStyle(StageStyle.UNDECORATED);
       // alert.getDialogPane().getStylesheets().addAll("/alertas/boton2.css");
        alert.getDialogPane().setStyle("-fx-background-color: #FAFAFA;");
        alert.setTitle(titulo);
        alert.setHeaderText(cabecera);
        alert.setContentText(contenido);

        alert.showAndWait();
    }

    public static String alertaTextInput(String titulo, String encabezado, String contenido) {
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle(titulo);
        dialog.setHeaderText(encabezado);
        dialog.setContentText(contenido);
        

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
  
        if (result.isPresent()) {

            return result.get();
        } else {
            return null;
        }


    }
    public static String alertaTextInputDefault(String titulo, String encabezado, String contenido, String defau) {
        TextInputDialog dialog = new TextInputDialog(defau);
        dialog.setTitle(titulo);
        dialog.setHeaderText(encabezado);
        dialog.setContentText(contenido);
        

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
  
        if (result.isPresent()) {

            return result.get();
        } else {
            return null;
        }


    }

    public static String[] alertaInputTipoLogin(String titulo, String encabezado) {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(titulo);
        dialog.setHeaderText(encabezado);

// Set the icon (must be included in the project).
//        dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));
// Set the button types.
        ButtonType loginButtonType = new ButtonType("Agregar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Nombre");
        TextField password = new TextField();
        password.setPromptText("Numero");

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("N. Cliente:"), 0, 1);
        grid.add(password, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });
        String[] info = new String[2];
        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
            info[0] = usernamePassword.getKey();
            info[1] = usernamePassword.getValue();
        });

        return info;

    }

    public static String[] alertaInputCambioPass(String titulo, String encabezado) {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(titulo);
        dialog.setHeaderText(encabezado);

// Set the icon (must be included in the project).
//        dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));
// Set the button types.
        ButtonType loginButtonType = new ButtonType("Cambiar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField username = new PasswordField();
        username.setPromptText("Contraseña");
        PasswordField password = new PasswordField();
        password.setPromptText("Re-Contraseña");

        grid.add(new Label("Contraseña"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Repetir Contraseña"), 0, 1);
        grid.add(password, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });
        String[] info = new String[2];
        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
            info[0] = usernamePassword.getKey();
            info[1] = usernamePassword.getValue();
        });

        return info;

    }


    
     
     
     
     
     public static void NotificacionWarning(String titulo, String contenido){
     Notifications.create()
              .title(titulo)
              .text(contenido)
              .showWarning();
     }
     
     public static void NotificacionError(String titulo, String contenido){
     Notifications.create()
              .title(titulo)
              .text(contenido)
              .showError();
     }
     
     public static void NotificacionInformacion(String titulo, String contenido){
     Notifications.create()
              .title(titulo)
              .text(contenido)
              .showInformation();
     }
     
     
     
     
     
     
     
     
     
     
     
     
}