package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	private Desktop desktop = Desktop.getDesktop();
	public App storage = new App();

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.getStyle();
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 900, 1200);
			Menu file = new Menu("File");
			MenuItem open = new MenuItem("Open");
			Menu save = new Menu("Save");
			MenuItem saveas = new Menu("Save As");
			save.getItems().addAll(saveas);
			file.getItems().addAll(open, save);
			FileChooser getFiles = new FileChooser();

			Text errorMessage = new Text("Invalid Value!");

			Menu options = new Menu("Options");
			MenuItem preferences = new MenuItem("Preferences");

			options.getItems().addAll(preferences);

			Slider slider = new Slider(0, 100, 50);
			slider.setValue(0);
			slider.setBlockIncrement(1);
			slider.setMajorTickUnit(1);
			slider.setMinorTickCount(0);
			slider.setSnapToTicks(true);
			slider.prefWidthProperty().bind(root.widthProperty().multiply(0.75));
			slider.minHeightProperty().bind(root.heightProperty().multiply(0.05));
			preferences.setOnAction(e -> {
				AlertBox.displayWarning("Preferences", storage, slider);
			});
			// textwindow
			TextField goTo = new TextField(Integer.toString((int) slider.getValue()));

			goTo.setPrefWidth(30);
			HBox bottom = new HBox();
			bottom.getChildren().addAll(slider, goTo);
			bottom.setAlignment(Pos.CENTER);
			root.setBottom(bottom);

			open.setOnAction(e -> {
				try {
					int start = 0;
					List<File> images = getFiles.showOpenMultipleDialog(primaryStage);
					ArrayList<String> currentFiles = new ArrayList<String>();
					if (file != null) {
						for (File files : images) {
							currentFiles.add(files.toURI().toString());
						}
					}
					storage.setFiles(currentFiles);
					slider.setMax(storage.getlength(currentFiles));
					if (storage.isReversedSlider()) {
						Collections.reverse(storage.getFiles());
						start = storage.getlength(currentFiles) - 1;
					}

					Image image = new Image(storage.getFiles().get(start));
					ImageView current = new ImageView(image);
					root.setCenter(current);
					current.setPreserveRatio(true);
					if (storage.isReversedSlider()) {
						slider.setValue(start);
					}

					if (image.getWidth() > primaryStage.getWidth()) {
						try {
							current.setFitWidth(1500);
							primaryStage.setWidth(1500);
							primaryStage.setHeight((1500 * image.getHeight()) / image.getWidth() + 100);
						} catch (java.lang.RuntimeException e3) {
							System.out.println("No image present");
						}
						return;
					}
					if (image.getHeight() > current.getFitHeight()) {

						current.setFitHeight(1100);
						primaryStage.setWidth((1100 * image.getWidth()) / image.getHeight());
						return;
					}
				}

				catch (java.lang.NullPointerException noFiles) {
					System.err.println("No valid files opened");
				}

			});

			configureFileChooser(getFiles, "Select the folder");

			slider.valueProperty().addListener((observable, oldValue, newValue) -> {
				try {
					Image image = new Image(storage.getFiles().get((int) slider.getValue()));
					ImageView current = new ImageView(image);
					root.setCenter(current);
					goTo.setText(Integer.toString((int) slider.getValue()));
					current.setPreserveRatio(true);
					current.fitHeightProperty().bind(primaryStage.heightProperty());
					if (image.getWidth() > primaryStage.getWidth()) {
						try {
							current.setFitWidth(1500);
							primaryStage.setWidth(1500);
							primaryStage.setHeight((1500 * image.getHeight()) / image.getWidth() + 100);
						} catch (java.lang.RuntimeException e3) {
							System.out.println("No image present");
						}
						return;
					}
					if (image.getHeight() > current.getFitHeight()) {

						current.setFitHeight(1100);
						primaryStage.setWidth((1100 * image.getWidth()) / image.getHeight());
						return;
					}
				}

				catch (java.lang.NullPointerException noFiles) {
					System.err.println("No valid files opened");
				}
			});

			goTo.textProperty().addListener((observable, currentValue, newValue) -> {
				try {
					slider.setValue(Integer.parseInt(newValue));
				} catch (java.lang.IndexOutOfBoundsException e) {
					bottom.getChildren().add(errorMessage);
				}
			});
			scene.getStylesheets().add("application.css");
			MenuBar navbar = new MenuBar();
			navbar.getMenus().addAll(file, options);
			root.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

			root.setTop(navbar);

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private static void configureFileChooser( // method from javafx documentation
			final FileChooser fileChooser, String title) {
		fileChooser.setTitle(title);
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("PNG", "*.png"));
	}

	private static void changeWidth(Stage stage, int width) {

	}

	private void openFile(File file) {
		try {
			desktop.open(file);
		} catch (IOException ex) {
		}
	}
}
