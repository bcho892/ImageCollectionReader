package application;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class App {
	private ImageView current = new ImageView();
	private ArrayList<String> files = new ArrayList<String>();
	private Image startUp = new Image("hanma.jpg");
	private boolean reversedSlider = false; // normal = false, reversed = true
	private boolean alreadyReversed = false;

	public App() {
		this.current = new ImageView(startUp);
	}

	public ArrayList<String> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<String> files) {
		this.files = files;
	}

	public ImageView getCurrent() {
		return current;
	}

	public void setCurrent(Image image) {

		this.current = new ImageView(image);

	}

	public int getlength(ArrayList<String> files) {
		return files.size();
	}

	public boolean isReversedSlider() {
		return reversedSlider;
	}

	public void setReversedSlider(boolean reversedSlider) {
		this.reversedSlider = reversedSlider;
	}

	public boolean isAlreadyReversed() {
		return alreadyReversed;
	}

	public void setAlreadyReversed(boolean alreadyReversed) {
		this.alreadyReversed = alreadyReversed;
	}

}
