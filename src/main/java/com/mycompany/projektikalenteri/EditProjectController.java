package com.mycompany.projektikalenteri;

import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.text.Text;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class EditProjectController {
	@FXML
	private ResourceBundle resources;
	@FXML
	private ListView<String> teamMembers;
	@FXML
	private Text projectName;
	@FXML
	private Text errorText;

	private LoggedInUser user;
	private Project project;
	private CalendarController cController;

	public void setAll(final LoggedInUser user, final Project project, final CalendarController cController) {
		this.user = user;
		this.project = project;
		this.cController = cController;
	}

	public void setItems() {
		projectName.setText((resources.getString("project") + " " + project.getName() + "\n"
				+ resources.getString("boss") + " " + project.getGroupLeader().getDisplayName()));
		final List<String> list3 = project.getTeamMemberNames();
		final ObservableList<String> items = FXCollections.observableArrayList(list3);
		teamMembers.setItems(items);
	}

	@FXML
	void close(final ActionEvent event) {
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	@FXML
	void removeProject(final ActionEvent event) {
		try {
			user.removeProject(project);
		} catch (Exception e) {
			errorText.setText(e.getMessage());
		}
		cController.fillInfo();
		close(event);
	}

}
