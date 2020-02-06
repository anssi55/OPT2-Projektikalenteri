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

	private Kayttaja kayttaja;
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
	private List<Projekti> list;

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
		EntryScreenController c = new EntryScreenController();
		final Stage dialog = new Stage();

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateEntryScene.fxml"), resources); //$NON-NLS-1$
			AnchorPane pane = loader.load();
			EntryScreenController controller = loader.getController();

			Scene scene = new Scene(pane);

			dialog.setTitle(resources.getString("addEntryTitle"));

			dialog.setScene(scene);

			dialog.show();
			controller.setKayttaja(kayttaja);

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
			controller.setAll(kayttaja, this);

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

	public Kayttaja getKayttaja() {
		return kayttaja;

	}

	public void setKayttaja(Kayttaja kayttaja) {
		this.kayttaja = kayttaja;
		kayttaja.changeHandlersResourceBundle(resources);
		fillInfo();

	}

	public void fillInfo() {
		String text = resources.getString("user") + ": " + kayttaja.getKayttajatunnus();

		infoText.setText(text);
		list = kayttaja.getPomona();
		List<Projekti> list2 = kayttaja.getTekijana();
		List<String> list3 = new ArrayList();
		List<String> list4 = new ArrayList();
		for (Projekti p : list) {
			list3.add(p.getNimi());

		}
		for (Projekti p : list2) {
			list4.add(p.getNimi());
		}
		ObservableList<String> items = FXCollections.observableArrayList(list3);
		ObservableList<String> items2 = FXCollections.observableArrayList(list4);

		ownProjects.setItems(items);

		if (!items2.isEmpty()) {
			otherProjects.setItems(items);

		}

	}

	@FXML
	public void projectSettings(MouseEvent click) {
		if (click.getClickCount() == 2 && !list.isEmpty()) {
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
				Projekti p = null;
				String s = ownProjects.getSelectionModel().getSelectedItem().toString();
				for (Projekti pr : list) {
					if (pr.getNimi().equals(s)) {
						p = pr;
						break;
					}
				}
				controller.setAll(kayttaja, p, this);

				controller.setItems();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
