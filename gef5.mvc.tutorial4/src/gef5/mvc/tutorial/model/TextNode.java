package gef5.mvc.tutorial.model;

import java.io.*;

import org.eclipse.gef.geometry.planar.*;

import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.scene.paint.*;

public class TextNode implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7445815811387721542L;

	public static final String POSITION_PROPERTY = "position";
	public static final String TEXT_PROPERTY = "text";

	private SimpleObjectProperty<Point> position;
	private SimpleObjectProperty<String> text;

	private Color color;

	public TextNode() {
		reset();
	}

	public TextNode(double x, double y, String text) {
		reset();
		position.setValue(new Point(x, y));
		this.text.setValue(text);
	}

	private void reset() {
		position = new SimpleObjectProperty<>(this, POSITION_PROPERTY);
		text = new SimpleObjectProperty<>(this, TEXT_PROPERTY);
		color = Color.LIGHTSKYBLUE;
		position.setValue(new Point(0, 0));
		text.setValue("");
	}

	public String getText() {
		return text.getValue();
	}

	public Point getPosition() {
		return position.getValue().getCopy();
	}

	public void setText(String text) {
		this.text.setValue(text);
	}

	public void setPosition(Point position) {
		this.position.setValue(position);
	}

	public Color getColor() {
		return color;
	}

	public void doChange() {
		setPosition(new Point(getPosition().x + Math.random() * 10 - 5, getPosition().y + Math.random() * 10 - 5));
		setText(String.format("%s %s", getText().split(" ")[0], Math.round(Math.random() * 100)));
	}

	public void addPropertyChangeListener(ChangeListener<Object> observer) {
		position.addListener(observer);
		text.addListener(observer);
	}

	public void removePropertyChangeListener(ChangeListener<Object> observer) {
		position.removeListener(observer);
		text.removeListener(observer);
	}

	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		s.writeDouble(position.get().x);
		s.writeDouble(position.get().y);
		s.writeUTF(text.get());
	}

	private void readObject(java.io.ObjectInputStream s) throws java.lang.ClassNotFoundException, java.io.IOException {
		reset();
		double x = s.readDouble();
		double y = s.readDouble();
		String t = s.readUTF();
		position.setValue(new Point(x, y));
		text.setValue(t);
	}

}
