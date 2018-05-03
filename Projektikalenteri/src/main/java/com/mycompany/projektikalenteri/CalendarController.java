package com.mycompany.projektikalenteri;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class CalendarController {
	
	private Kayttaja kayttaja;
	private Kalenteri kalenteri;
	@FXML
	private ResourceBundle resources;
	@FXML
    private GridPane monthPane;
    
    @FXML
    private Text monthName;
    @FXML
    private Text infoText;
    @FXML 
    private ListView ownProjects;
    @FXML 
    private ListView otherProjects;
   
	
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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateProjectScene.fxml"), resources); //$NON-NLS-1$
	        GridPane pane = loader.load();
	        AddProjectController controller = loader.getController();
	        
	        Scene scene = new Scene(pane);
	        
	        dialog.setTitle(resources.getString("addProjectTitle")); 
	        
	        dialog.setScene(scene);
	        
	        dialog.show();
	        controller.setKayttaja(kayttaja);
			
		
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    @FXML
	public void initialize() {
    	
    	kalenteri = new Kalenteri(resources);
    	
    	try {
			fillMonthPane();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	
    }
    public Kayttaja getKayttaja() {
		return kayttaja;
    	
    }

	public void setKayttaja(Kayttaja kayttaja) {
		this.kayttaja = kayttaja;
		fillInfo();
		
	}
	public void fillInfo() {
		String text = resources.getString("user")+": "+kayttaja.getKayttajatunnus();
		
		infoText.setText(text);
		List<Projekti> list = kayttaja.getPomona();
		List<Projekti> list2 = kayttaja.getTekijana();
		List<String> list3 = new ArrayList();
		List<String> list4 = new ArrayList();
		for (Projekti p: list) {
			list3.add(p.getNimi());
			System.out.println(p.getNimi());
		}
		for (Projekti p: list2) {
			list4.add(p.getNimi());
		}
		ObservableList<String> items = FXCollections.observableArrayList(list3);
		ObservableList<String> items2 = FXCollections.observableArrayList(list4);
		
		
		if (!items.isEmpty()) {
			ownProjects.setItems(items);
			
		}
		if (!items2.isEmpty()) {
			otherProjects.setItems(items);
			
		}
		
	}
    
  

	
    

}
