package application;

import java.util.Collections;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

	public static void displayWarning(String title, App storage, Slider slider) {
		Stage window = new Stage();

		window.setResizable(false);

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);

		Button closeButton = new Button("Ok");

		CheckBox reversed = new CheckBox("Reverse Slider");
		closeButton.setOnAction(e -> {
			if (storage.isAlreadyReversed() && !(storage.isReversedSlider())) {
				Collections.reverse(storage.getFiles());
				storage.setAlreadyReversed(false);
			} else if (!(storage.isAlreadyReversed()) && storage.isReversedSlider()) {
				Collections.reverse(storage.getFiles());
				storage.setAlreadyReversed(true);
			}

			window.close();
		});

		reversed.setSelected(storage.isReversedSlider());
		reversed.setOnAction(e -> {
			storage.setReversedSlider(reversed.isSelected());
		});

		VBox layout = new VBox(10);
		layout.getChildren().addAll(reversed, closeButton);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, 250, 80);
		scene.getStylesheets().add("application.css");

		window.setScene(scene);
		window.showAndWait();
	}

}