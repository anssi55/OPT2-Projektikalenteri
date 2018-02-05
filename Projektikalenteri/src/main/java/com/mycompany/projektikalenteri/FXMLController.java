package com.mycompany.projektikalenteri;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class FXMLController implements Initializable {
    DatabaseHandler handler = new DatabaseHandler();
    @FXML
    private Text promptText;
    
    @FXML
    private TextField usernameTextfield;

    @FXML
    private PasswordField passwordField;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("Painoit kirjaudu-painiketta!");
        
        if (usernameTextfield.getText().trim().isEmpty()) {
            promptText.setText("Syötä käyttäjätunnus!");
//        } else if (passwordTextfield.getText().trim().isEmpty()) {
//            promptText.setText("Syötä salasana!!");
        } else {
            System.out.println("Yritetään kirjautua!");
            promptText.setText("Yritetään kirjautua!");
            try{
                String user = usernameTextfield.getText();
                String passwd = passwordField.getText();
                boolean addUser = handler.addUser(user, passwd);
            } catch (SQLException ex) {
                System.out.println("Kirjautuminen epäonnistui");
                
            }
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
