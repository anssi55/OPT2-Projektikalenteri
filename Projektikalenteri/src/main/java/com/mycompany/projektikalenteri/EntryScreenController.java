package com.mycompany.projektikalenteri;
import java.awt.Color;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.fxml.Initializable;
import javafx.stage.StageStyle;
import javafx.stage.Modality;
import java.awt.Dialog.ModalityType;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;


public class EntryScreenController {
	private DatabaseHandler handler;
	@FXML
	private ChoiceBox startHour;
	@FXML
	private ChoiceBox endMinute;
	@FXML
	private ChoiceBox startMinute;
	@FXML
	private ChoiceBox endHour;
	@FXML
	private DatePicker startDate;
	@FXML
	private DatePicker endDate;
	@FXML
	private ChoiceBox entryChoice;
	@FXML
	private Label startLabel;
	@FXML
	private Label endLabel;
	@FXML
	private Label choiceLabel;
	@FXML
	private TextArea messageField;
	@FXML
	private Text errorText;
	
	@FXML
	public void addEntry(ActionEvent event) {
		if(messageField.getText().isEmpty()) {
			messageField.setStyle("-fx-border-color: red");
			errorText.setText("Merkintä on tyhjä!");
			errorText.setStyle("-fx-text-color: red");
		}
		if (endDate.getValue().compareTo(endDate.getValue()) == 1) {
			endDate.setStyle("-fx-border-color: red");
			errorText.setText("Loppuaika alkuaikaa ennen!");
			errorText.setStyle("-fx-text-color: red");
			
		} else if (endHour.getValue().toString().compareTo(startHour.getValue().toString()) == 1) {
			endHour.setStyle("-fx-border-color: red");
			errorText.setText("Loppuaika alkuaikaa ennen!");
			errorText.setStyle("-fx-text-color: red");
		} else if(endMinute.getValue().toString().compareTo(startMinute.getValue().toString()) == 1 ) {
			endMinute.setStyle("-fx-border-color: red");
			errorText.setText("Loppuaika alkuaikaa ennen!");
			errorText.setStyle("-fx-text-color: red");
		}
	}
	@FXML 
	void close(ActionEvent event) {
		Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
	    stage.close();
		
	}
	
	
	

	
	
}
