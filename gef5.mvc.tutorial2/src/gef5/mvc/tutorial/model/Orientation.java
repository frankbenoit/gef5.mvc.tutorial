package gef5.mvc.tutorial.model;

public enum Orientation {
	NORTH(90), EAST(0), SOUTH(270), WEST(180);
	
	private double angle;

	Orientation(double angle) {
		this.angle = angle;
	}

	public double degree() {
		return angle;
	}
}
