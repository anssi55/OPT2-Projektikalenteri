package com.mycompany.projektikalenteri;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent login = FXMLLoader.load(getClass().getResource("/fxml/LoginScene.fxml"));
        
        Scene loginScene = new Scene(login);
        loginScene.getStylesheets().addAll("/styles/Styles.css", "/styles/LoginStyles.css");
        
        stage.setTitle("Projektikalenteri - Kirjautuminen");
        stage.setScene(loginScene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
