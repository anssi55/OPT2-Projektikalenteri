package com.mycompany.projektikalenteri;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Locale currentLocale = new Locale("fi", "FI");
        Locale.setDefault(currentLocale);
        ResourceBundle rb = ResourceBundle.getBundle("MessagesBundle", currentLocale);
        Parent login = FXMLLoader.load(getClass().getResource("/fxml/LoginScene.fxml"), rb);

        Scene loginScene = new Scene(login);
        loginScene.getStylesheets().addAll("/styles/Styles.css", "/styles/LoginStyles.css");

        stage.setTitle("Projektikalenteri - Kirjautuminen");
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
