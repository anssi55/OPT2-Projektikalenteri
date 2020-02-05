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
	private ListView<String> members;
	@FXML
	private Text projectName;
	@FXML
	private Text errorText;

	private Kayttaja kayttaja;
	private Projekti projekti;
	private CalendarController c;

	public void setAll(final Kayttaja k, final Projekti p, final CalendarController c) {
		this.kayttaja = k;
		this.projekti = p;
		this.c = c;
	}

	public void setItems() {
		projectName.setText((resources.getString("project") + " " + projekti.getNimi() + "\n"
				+ resources.getString("boss") + " " + projekti.getPomo()));
		final List<String> list3 = projekti.getTiimilaiset();
		final ObservableList<String> items = FXCollections.observableArrayList(list3);
		members.setItems(items);
	}

	@FXML
	void close(final ActionEvent event) {
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	@FXML
	void removeProject(final ActionEvent event) {
		kayttaja.removeProject(projekti, errorText);
		c.fillInfo();
		close(event);
	}

}
