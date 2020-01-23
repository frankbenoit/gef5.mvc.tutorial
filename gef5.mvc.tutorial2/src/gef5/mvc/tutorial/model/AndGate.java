package gef5.mvc.tutorial.model;

public class AndGate extends Gate {


	public AndGate(double x, double y, Orientation orientation) {
		this.orientation = orientation;
		position.setLocation(x, y);
	}

}
