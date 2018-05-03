package com.mycompany.projektikalenteri;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.*;
import javafx.scene.control.TextField;

public class AddProjectController {
	private Kayttaja kayttaja;
	@FXML
	private TextField projectnameTextfield;
	@FXML
	private Text promptCreateProjectText;
	
	
	
	public void setKayttaja(Kayttaja kayttaja) {
		this.kayttaja = kayttaja;
	}

	@FXML
    private void createProject(ActionEvent event) {
		String s = projectnameTextfield.getText();
		if (!s.equals("")) {
			kayttaja.addProject(s, promptCreateProjectText);
		}
	}
	@FXML
    private void cancel(ActionEvent event) {
		
	}
	

}
