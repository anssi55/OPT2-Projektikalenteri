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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Locale;

public class FXMLController implements Initializable {
	ResourceBundle messages;
    private DatabaseHandler handler = new DatabaseHandler();
    private Kayttaja kayttaja;
    private Kalenteri kalenteri;
    @FXML
    private Text promptRegistrationText;
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
    @FXML
    private GridPane monthPane;
    @FXML
    private Text tekstikentta;
    @FXML
    private Text promptLoginText;
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
    
    // Kirjautumispainikkeen toiminnot
    
    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
        System.out.println("Painoit kirjaudu-painiketta!"); //$NON-NLS-1$
        
        if (usernameTextfield.getText().trim().isEmpty()) {
            promptLoginText.setText(messages.getString("FXMLController.insertUsername")); //$NON-NLS-1$
//        } else if (passwordTextfield.getText().trim().isEmpty()) {
//            promptText.setText("Syötä salasana!!");
        } else {
            System.out.println("Yritetään kirjautua!"); //$NON-NLS-1$
            
            try{
                String user = usernameTextfield.getText();
                String passwd = passwordField.getText();
                kayttaja = handler.loadUser(user, passwd, promptLoginText);
                if (kayttaja != null) {
                	
//                    Node node = (Node) event.getSource();
//                    Stage stage = (Stage) node.getScene().getWindow();
//                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/CalendarScene.fxml"));
//                    Scene scene = new Scene(root);
//                    //fillMonthPane();
//                    //scene.getStylesheets().addAll("/styles/Styles.css", "/styles/LoginStyles.css");
//                    stage.setTitle("Projektikalenteri - Kalenteri");
//                    stage.setScene(scene);
//                    stage.show();
//                    
                    moveToCalendar(event);
                    
                }

            } catch (SQLException ex) {
                System.out.println("Kirjautuminen epäonnistui"); //$NON-NLS-1$
                ex.printStackTrace();
                
            }
           
        }
       
    }
    
    // Rekisteröimispainikkeen toiminnot
    @FXML
    private void handleRegistrationButtonAction(ActionEvent event) {
        System.out.println("Painoit rekisteröitymispainiketta!"); //$NON-NLS-1$
        if (usernameTextfield.getText().trim().isEmpty()) {
            promptLoginText.setText(messages.getString("FXMLController.insertUsername")); //$NON-NLS-1$
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
	            	System.out.println("Rekisteröityminen onnistui!"); //$NON-NLS-1$
	            }
	
	        } catch (SQLException ex) {
	            System.out.println("Rekisteröityminen epäonnistui"); //$NON-NLS-1$
	            ex.printStackTrace();
	        }
        } else {
        	System.out.println("Salasanat ei täsmää"); //$NON-NLS-1$
        }
        
        
    }
    
    // Siirtyminen kirjautumisnäkymään
    @FXML
    private void handleRegistrationSceneButtonAction(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginScene.fxml")); //$NON-NLS-1$
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll("/styles/Styles.css", "/styles/LoginStyles.css"); //$NON-NLS-1$ //$NON-NLS-2$
        stage.setTitle(messages.getString("FXMLController.projectCalendar_login")); //$NON-NLS-1$
        stage.setScene(scene);
        stage.show();
    }
    
    // Siirtyminen rekisteröintinäkymään
    @FXML
    private void handleLoginSceneButtonAction(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/RegistrationScene.fxml")); //$NON-NLS-1$
        Scene scene = new Scene(root);        
        scene.getStylesheets().addAll("/styles/Styles.css", "/styles/Registration.css");         //$NON-NLS-1$ //$NON-NLS-2$
        stage.setTitle(messages.getString("FXMLController.projectCalendar_register")); //$NON-NLS-1$
        stage.setScene(scene);
        stage.show();        
        
    }
    
    //Siirtyminen kalenterinäkymään
    private void moveToCalendar(ActionEvent event) throws IOException {
    	
    	Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CalendarScene.fxml"), messages); //$NON-NLS-1$
        Scene scene = new Scene(root);
        //fillMonthPane();
        //scene.getStylesheets().addAll("/styles/Styles.css", "/styles/LoginStyles.css");
        stage.setTitle("Projektikalenteri - Kalenteri"); //$NON-NLS-1$
        
        stage.setScene(scene);
        
        stage.show();
        

        
    }
    @FXML
    private void addEntry(ActionEvent event) {
    	EntryScreenController c = new EntryScreenController();
    	final Stage dialog = new Stage();
        
        
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/CreateEntryScene.fxml"), messages); //$NON-NLS-1$
			
			Scene scene = new Scene(root);  
			dialog.setScene(scene);
			dialog.setTitle(messages.getString("addEntryTitle"));
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    	
    	Locale currentLocale = new Locale("ar","AE");
    	messages = ResourceBundle.getBundle("com.mycompany.projektikalenteri.MessagesBundle",currentLocale);
    	kalenteri = new Kalenteri(messages);
    	Locale.setDefault(currentLocale);
    	
    	
    }
    
}
