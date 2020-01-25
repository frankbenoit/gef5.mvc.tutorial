package gef5.mvc.tutorial.model;

import org.eclipse.gef.geometry.planar.*;

import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.scene.paint.*;

public class TextNode {

	public static final String POSITION_PROPERTY = "position";
	public static final String TEXT_PROPERTY = "text";

	public ObjectProperty<Point> position;
	private ObjectProperty<String> text;

	private Color color = Color.LIGHTSKYBLUE;

	public TextNode(double x, double y, String text) {
		Point point = new Point(x, y);

		position = new SimpleObjectProperty<>(this, POSITION_PROPERTY);
		this.text = new SimpleObjectProperty<>(this, TEXT_PROPERTY);

		position.setValue(point);
		this.text.setValue(text);
	}

	public String getText() {
		return text.getValue();
	}

	public Point getPosition() {
		return position.getValue();
	}

	public Color getColor() {
		return color;
	}

	public void setText(String text) {
		this.text.setValue(text);
	}

	public void setPosition(Point position) {
		this.position.setValue(position);
	}

	public void doChange() {
		setPosition(new Point(getPosition().x + Math.random() * 10 - 5, getPosition().y + Math.random() * 10 - 5));

		if (Math.random() > 0.5) {
			setText(String.format("%s %s", getText().split(" ")[0], Math.round(Math.random() * 100)));
		} else {
			setText(getText().split(" ")[0]);
		}
	}

	public void addPropertyChangeListener(ChangeListener<Object> pointObserver) {
		position.addListener(pointObserver);
		text.addListener(pointObserver);
	}

	public void removePropertyChangeListener(ChangeListener<Object> pointObserver) {
		position.removeListener(pointObserver);
		text.removeListener(pointObserver);
	}
}
