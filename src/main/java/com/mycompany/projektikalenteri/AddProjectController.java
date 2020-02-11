package com.mycompany.projektikalenteri;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class AddProjectController {
	private User user;
	@FXML
	private TextField projectnameTextfield;
	@FXML
	private Text promptCreateProjectText;
	@FXML
	private ResourceBundle resources;
	private CalendarController controller;

	@FXML
	private void createProject(ActionEvent event) {
		String s = projectnameTextfield.getText();
		if (!s.equals("")) {

			boolean b = user.addProject(s, promptCreateProjectText);
			if (b == true) {
				promptCreateProjectText.setFill(Color.GREEN);
				promptCreateProjectText.setText(s + " " + resources.getString("projectCreationSuccessful"));
				c.fillInfo();
			}

		} else {
			promptCreateProjectText.setFill(Color.RED);
			promptCreateProjectText.setText(resources.getString("setNameforProject"));

		}
	}

	@FXML
	private void cancel(ActionEvent event) {
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	public void setAll(User user, CalendarController controller) {
		this.user = user;
		this.controller = controller;
	}

}
