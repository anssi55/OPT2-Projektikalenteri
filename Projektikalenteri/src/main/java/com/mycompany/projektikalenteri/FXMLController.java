package com.mycompany.projektikalenteri;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FXMLController implements Initializable {
    DatabaseHandler handler = new DatabaseHandler();
    @FXML
    private Text promptText;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordConfirmField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField displayNameField;
    
    // Kirjautumispainikkeen toiminnot
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        System.out.println("Painoit kirjaudu-painiketta!");
        
        if (usernameTextfield.getText().trim().isEmpty()) {
            promptText.setText("Syötä käyttäjätunnus!");
//        } else if (passwordTextfield.getText().trim().isEmpty()) {
//            promptText.setText("Syötä salasana!!");
        } else {
            System.out.println("Yritetään kirjautua!");
            
            try{
                String user = usernameTextfield.getText();
                String passwd = passwordField.getText();
                Kayttaja addUser = handler.loadUser(user, passwd);

            } catch (SQLException ex) {
                System.out.println("Kirjautuminen epäonnistui");
                ex.printStackTrace();
                
            }
        }
        
    }
    
    // Rekisteröimispainikkeen toiminnot
    @FXML
    private void handleRegistrationButtonAction(ActionEvent event) {
        System.out.println("Painoit rekisteröitymispainiketta!");
        if (usernameTextfield.getText().trim().isEmpty()) {
            promptText.setText("Syötä käyttäjätunnus!");
//        } else if (passwordTextfield.getText().trim().isEmpty()) {
//            promptText.setText("Syötä salasana!!");
        } 
        if (passwordField.getText().equals(passwordConfirmField.getText())){            
	        try{
	            String user = usernameTextfield.getText();
	            String passwd = passwordField.getText();
	            String email = emailField.getText();
	            String displayName = displayNameField.getText();
	            boolean userAdded = handler.addUser(user, passwd, email, displayName);
	            
	            if (userAdded) {
	            	System.out.println("Rekisteröityminen onnistui!");
	            }
	
	        } catch (SQLException ex) {
	            System.out.println("Rekisteröityminen epäonnistui");
	            ex.printStackTrace();
	        }
        } else {
        	System.out.println("Salasanat ei täsmää");
        }
        
        
    }
    
    // Siirtyminen kirjautumisnäkymään
    @FXML
    private void handleRegistrationSceneButtonAction(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginScene.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll("/styles/Styles.css", "/styles/LoginStyles.css");
        stage.setTitle("Projektikalenteri - Kirjautuminen");
        stage.setScene(scene);
        stage.show();
    }
    
    // Siirtyminen rekisteröintinäkymään
    @FXML
    private void handleLoginSceneButtonAction(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/RegistrationScene.fxml"));
        Scene scene = new Scene(root);        
        scene.getStylesheets().addAll("/styles/Styles.css", "/styles/Registration.css");        
        stage.setTitle("Projektikalenteri - Rekisteröityminen");
        stage.setScene(scene);
        stage.show();        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
