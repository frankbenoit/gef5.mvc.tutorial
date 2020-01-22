package gef5.mvc.tutorial.model;

import org.eclipse.gef.geometry.planar.*;

import javafx.scene.paint.*;

public class InverterModel {

	public final Point position = new Point();

	private Orientation orientation = Orientation.NORTH;
	private Color color = Color.LIGHTSKYBLUE;

	public InverterModel(double x, double y, Orientation orientation) {
		this.orientation = orientation;
		position.setLocation(x, y);
	}

	public Point getPosition() {
		return position;
	}

	public Color getColor() {
		return color;
	}

	public Orientation getOrientation() {
		return orientation;
	}
}
