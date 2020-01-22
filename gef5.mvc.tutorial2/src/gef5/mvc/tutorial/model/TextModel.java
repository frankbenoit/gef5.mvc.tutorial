package gef5.mvc.tutorial.model;

import org.eclipse.gef.geometry.planar.*;

import javafx.scene.paint.*;

public class TextModel {

	public final Point position = new Point();

	private String text = "Text ...";
	private Color color = Color.LIGHTSKYBLUE;

	public TextModel() {
		position.setLocation(50, 50);
	}

	public String getText() {
		return text;
	}

	public Point getPosition() {
		return position;
	}

	public Color getColor() {
		return color;
	}
}
