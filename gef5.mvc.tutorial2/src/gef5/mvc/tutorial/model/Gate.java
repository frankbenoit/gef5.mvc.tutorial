package gef5.mvc.tutorial.model;

import org.eclipse.gef.geometry.planar.*;

import javafx.scene.paint.*;

public abstract class Gate {

	protected Color color = Color.LIGHTSKYBLUE;
	protected Point position = new Point();
	protected Orientation orientation = Orientation.NORTH;
	
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
