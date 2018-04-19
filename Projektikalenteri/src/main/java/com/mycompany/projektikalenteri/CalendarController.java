package com.mycompany.projektikalenteri;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CalendarController {
	private Kalenteri kalenteri;
	@FXML
	private ResourceBundle resources;
	@FXML
    private GridPane monthPane;
    
    @FXML
    private Text monthName;
	
	private void fillMonthPane() throws IOException {
    	monthName.setText(kalenteri.getMonthName()+"   "+kalenteri.getYear());
    	monthPane.getChildren().clear();
    	monthPane.setGridLinesVisible(true);
    	int day = 1;
    	int firstDay = kalenteri.getFirstDayOfMonth();
    	for (int i = 0; i <= 5; i++) {
    		for (int j = 0; j < 7; j++){
    			if (i == 0 && j==0) j = (j+firstDay);
	    		if (day ==kalenteri.getMaxDaysInMonth()) return;
		    	Text text = new Text(Integer.toString(day));
		    	day++;
		    	
		    	monthPane.setColumnIndex(text, j);
		    	monthPane.setRowIndex(text, i);
		    	monthPane.getChildren().addAll(text);
	    		}
    	}

    }
    
    @FXML
    private void handlePreviousMonthButton(ActionEvent event) throws IOException {
    	kalenteri.setMonthToPrevious();
    	fillMonthPane();
    }
    @FXML
    private void handleNextMonthButton(ActionEvent event) throws IOException {
    	kalenteri.setMonthToNext();
    	fillMonthPane();
    }
	
	
	
	
	
	
	
	@FXML
    private void addEntry(ActionEvent event) {
    	EntryScreenController c = new EntryScreenController();
    	final Stage dialog = new Stage();
        
        
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/CreateEntryScene.fxml"), resources); //$NON-NLS-1$
			
			Scene scene = new Scene(root);  
			dialog.setScene(scene);
			dialog.setTitle(resources.getString("addEntryTitle"));
			dialog.show();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    @FXML
    private void addProject(ActionEvent event) {
    	final Stage dialog = new Stage();
        Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/CreateProjectScene.fxml")); //$NON-NLS-1$
			Scene scene = new Scene(root);
			dialog.setScene(scene);
			dialog.show();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    @FXML
	public void initialize() {
    	FXMLLoader loader = new FXMLLoader();
    	
    	kalenteri = new Kalenteri(resources);
    	try {
			fillMonthPane();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    

}
