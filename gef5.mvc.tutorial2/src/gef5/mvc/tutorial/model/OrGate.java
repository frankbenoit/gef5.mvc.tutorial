package gef5.mvc.tutorial.model;

public class OrGate extends Gate {

	public OrGate(double x, double y, Orientation orientation) {
		this.orientation = orientation;
		position.setLocation(x, y);
	}

}
