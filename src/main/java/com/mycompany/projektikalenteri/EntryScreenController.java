package com.mycompany.projektikalenteri;
import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
import javax.swing.JPopupMenu.Separator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Window;
import javafx.scene.layout.AnchorPane;


public class EntryScreenController {
	private Kayttaja kayttaja;
	private List<Projekti> list;
	private List<Projekti> list2;
	private int splitter;
	
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
	private ResourceBundle resources;
	@FXML
	private AnchorPane entryPane;
	
	
	
	@FXML
	public void initialize() {
		DateFormat dateFormat = new SimpleDateFormat("hh:mm"); //$NON-NLS-1$
        Calendar cal = Calendar.getInstance();
        

        
        int minute = cal.get(Calendar.MINUTE);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
		
		
		ObservableList<String> hours = FXCollections.observableArrayList();
		for (int i = 0; i < 24;i++) {
			if (i < 10) {
				hours.add("0" +i); //$NON-NLS-1$
			} else {
				hours.add("" +i); //$NON-NLS-1$
			}
		}
		
		startHour.setItems(hours);
		endHour.setItems(hours);
		startHour.getSelectionModel().select(hour);
		endHour.getSelectionModel().select(hour);
		
		ObservableList<String> minutes = FXCollections.observableArrayList();
		for (int i = 0; i < 60;i++) {
			if (i < 10) {
				minutes.add("0" +i); //$NON-NLS-1$
			} else {
				minutes.add("" +i); //$NON-NLS-1$
			}
		}
		startMinute.setItems(minutes);
		endMinute.setItems(minutes);
		startMinute.getSelectionModel().select(minute);
		endMinute.getSelectionModel().select(minute);

		
		
		
	}
	
	@FXML
	public void addEntry(ActionEvent event) {
		String projectName = entryChoice.getValue().toString();
		setAllBlanc();
		boolean canAccept = true;
		if(messageField.getText().isEmpty()) {
			messageField.setStyle("-fx-border-color: red");
			errorText.setText(resources.getString("EntryScreenController.entryIsEmpty"));
			errorText.setStyle("-fx-text-color: red");
			canAccept = false;
		}
		if(endDate.getValue() == null) {
			endDate.setStyle("-fx-border-color: red");
			canAccept = false;
		}
		if(startDate.getValue() == null) {
			startDate.setStyle("-fx-border-color: red");
			canAccept = false;
		}
		if(canAccept == true) {
			if (endDate.getValue().compareTo(startDate.getValue()) < 0) {
				errorText.setText(resources.getString("EntryScreenController.EndTimeb4StartTime")); 
				errorText.setStyle("-fx-text-color: red"); 
				endDate.setStyle("-fx-border-color: red");
			}else if(endDate.getValue().compareTo(startDate.getValue()) == 0) {
				if (endHour.getValue().toString().compareTo(startHour.getValue().toString()) < 0) {
					errorText.setText(resources.getString("EntryScreenController.EndTimeb4StartTime")); 
					errorText.setStyle("-fx-text-color: red"); 
					endHour.setStyle("-fx-border-color: red");
				}else if(endHour.getValue().toString().compareTo(startHour.getValue().toString()) == 0) {
					if(endMinute.getValue().toString().compareTo(startMinute.getValue().toString()) < 0 ) {
							endMinute.setStyle("-fx-border-color: red"); 
							errorText.setText(resources.getString("EntryScreenController.EndTimeb4StartTime")); 
							errorText.setStyle("-fx-text-color: red"); 
					} else {
						String startTime = startDate.getValue().toString() +"-"+startHour.getValue()+"-"+startMinute.getValue();
						String endTime = endDate.getValue().toString() +"-"+endHour.getValue()+"-"+endMinute.getValue();
						int c = Character.getNumericValue(projectName.charAt(0));
						boolean success;
						if (c == 1) {
							success = kayttaja.addPersonalEntry(startTime, endTime, messageField.getText());
						} else if(c < splitter) {
							success = kayttaja.addProjectEntry(startTime, endTime, messageField.getText(), list.get(c-2));
						} else {
							success = kayttaja.addProjectEntry(startTime, endTime, messageField.getText(), list.get(c- splitter - 2));
						}
						close(event);
					}
				}
			}
		}
				
		
		
		
			
	}
	// sets all fields without red borders.
	private void setAllBlanc() {
		endDate.setStyle("-fx-border-color: transparent");
		startDate.setStyle("-fx-border-color: transparent");
		endMinute.setStyle("-fx-border-color: transparent");
		startMinute.setStyle("-fx-border-color: transparent");
		endHour.setStyle("-fx-border-color: transparent");
		startHour.setStyle("-fx-border-color: transparent");
		messageField.setStyle("-fx-border-color: transparent");
		
	}

	@FXML 
	void close(ActionEvent event) {
		final Node source = (Node) event.getSource();
	    final Stage stage = (Stage) source.getScene().getWindow();
	    stage.close();	
	}
	public void setKayttaja(Kayttaja k) {
		int orderNumber = 1;
		this.kayttaja = k;
		
		list = kayttaja.getPomona();
		list2 = kayttaja.getTekijana();
		List<String> list3 = new ArrayList();
		list3.add(orderNumber + ": " + resources.getString("EntryScreenController.personal"));
		orderNumber++;
		for (Projekti p: list) {
			list3.add(orderNumber + ": " +p.getNimi());
			orderNumber++;
		}
		splitter = orderNumber;
		for (Projekti p: list2) {
			list3.add(orderNumber + ": " + p.getNimi());
			orderNumber++;
		}
		ObservableList<String> items = FXCollections.observableArrayList(list3);
		
		
		
		
		if (!items.isEmpty()) {
			entryChoice.setItems(items);
			
		}
		entryChoice.getSelectionModel().select(0);
		
		
	}
	
	
	
	
	

	
	
}