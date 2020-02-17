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

import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.input.MouseEvent;

public class CalendarController {

	private LoggedInUser user;
	private Calendar calendar;
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
	private List<Project> listOfOwnProjects;

	private void fillMonthPane() throws IOException {
		monthName.setText(this.calendar.getMonthName() + "   " + this.calendar.getYear());
		monthPane.getChildren().clear();
		monthPane.setGridLinesVisible(true);
		int day = 1;
		int firstDay = this.calendar.getFirstDayOfMonth();
		for (int i = 0; i <= 5; i++) {
			for (int j = 0; j < 7; j++) {
				if (i == 0 && j == 0)
					j = (j + firstDay);
				if (day > this.calendar.getMaxDaysInMonth())
					return;
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
		this.calendar.setMonthToPrevious();
		fillMonthPane();
	}

	@FXML
	private void handleNextMonthButton(ActionEvent event) throws IOException {
		this.calendar.setMonthToNext();
		fillMonthPane();
	}

	@FXML
	private void addEntry(ActionEvent event) {
		final Stage dialog = new Stage();

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateEntryScene.fxml"), resources); //$NON-NLS-1$
			AnchorPane pane = loader.load();
			EntryScreenController controller = loader.getController();

			Scene scene = new Scene(pane);

			dialog.setTitle(resources.getString("addEntryTitle"));

			dialog.setScene(scene);

			dialog.show();
			controller.setUser(user);

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
			controller.setAll(user, this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void initialize() {

		this.calendar = new Calendar(resources);

		try {
			fillMonthPane();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public LoggedInUser getUser() {
		return this.user;

	}

	public void setUser(LoggedInUser user) {
		this.user = user;
		fillInfo();

	}

	public void fillInfo() {
		String text = resources.getString("user") + ": " + user.getUserName();

		infoText.setText(text);
		listOfOwnProjects = user.getProjectsWhereLeader();
		List<Project> listOfOtherProjects = user.getProjectsWhereMember();
		List<String> ownProjectNames = new ArrayList();
		List<String> otherProjectNames = new ArrayList();
		for (Project p : listOfOwnProjects) {
			ownProjectNames.add(p.getName());

		}
		for (Project p : listOfOtherProjects) {
			otherProjectNames.add(p.getName());

		}
		ObservableList<String> ownProjectsOList = FXCollections.observableArrayList(ownProjectNames);
		ObservableList<String> otherProjectsOList = FXCollections.observableArrayList(otherProjectNames);

		if (!ownProjectsOList.isEmpty()) {
			ownProjects.setItems(ownProjectsOList);
		}
		if (!otherProjectsOList.isEmpty()) {
			otherProjects.setItems(otherProjectsOList);
		}

	}

	@FXML
	public void projectSettings(MouseEvent click) {
		if (click.getClickCount() == 2 && !listOfOwnProjects.isEmpty()) {
			final Stage dialog = new Stage();
			Parent root;
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditProjectScene.fxml"), resources);
				AnchorPane pane = loader.load();

				EditProjectController controller = loader.getController();
				Scene scene = new Scene(pane);

				dialog.setTitle(resources.getString("addProjectTitle"));

				dialog.setScene(scene);

				dialog.show();
				Project selectedProject = null;
				String projectName = ownProjects.getSelectionModel().getSelectedItem().toString();
				for (Project project : listOfOwnProjects) {
					if (project.getName().equals(projectName)) {
						selectedProject = project;
						break;
					}
				}
				controller.setAll(user, selectedProject, this);

				controller.setItems();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
