package com.mycompany.projektikalenteri;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class FXMLController implements Initializable {
    
    @FXML
    private Text promptText;
    
    @FXML
    private TextField usernameTextfield;

    @FXML
    private PasswordField passwordTextfield;
    
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
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
