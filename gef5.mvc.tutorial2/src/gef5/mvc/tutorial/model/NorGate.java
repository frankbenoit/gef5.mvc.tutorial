package gef5.mvc.tutorial.model;

public class NorGate extends Gate {

	public NorGate(double x, double y, Orientation orientation) {
		this.orientation = orientation;
		position.setLocation(x, y);
	}

}
