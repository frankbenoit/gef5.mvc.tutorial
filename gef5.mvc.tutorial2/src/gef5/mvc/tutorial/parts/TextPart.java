package gef5.mvc.tutorial.parts;

import java.util.*;

import org.eclipse.gef.fx.nodes.*;
import org.eclipse.gef.geometry.planar.*;
import org.eclipse.gef.mvc.fx.parts.*;

import com.google.common.collect.*;

import gef5.mvc.tutorial.model.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;

public class TextPart extends AbstractContentPart<Group> {

	@Override
	public TextModel getContent() {
		return (TextModel) super.getContent();
	}

	@Override
	protected Group doCreateVisual() {
		return new Group();
	}

	@Override
	protected void doRefreshVisual(Group visual) {
		TextModel model = getContent();

		Text text = createText(model);

		Bounds textBounds = measureText(text);

		Rectangle rectBounds = getRectangleBounds(model, textBounds);

		createRectangle(visual, model, rectBounds);
		addText(visual, text, textBounds, rectBounds);
	}

	private Rectangle getRectangleBounds(TextModel model, Bounds textBounds) {
		double textWidth = textBounds.getWidth();
		double textHeight = textBounds.getHeight();

		return new Rectangle(model.getPosition(), new Dimension(textWidth + textHeight, textHeight * 1.5));
	}

	private Text createText(TextModel model) {
		Text text = new Text(model.getText());
		text.setFont(Font.font("Monospace", FontWeight.BOLD, 50));
		text.setFill(Color.BLACK);
		text.setStrokeWidth(2);
		return text;
	}

	private Bounds measureText(Text text) {
		new Scene(new Group(text));
		text.applyCss();
		return text.getLayoutBounds();
	}

	private void createRectangle(Group visual, TextModel model, Rectangle bounds) {
		RoundedRectangle roundRect = new RoundedRectangle(bounds, 10, 10);
		GeometryNode<RoundedRectangle> node = new GeometryNode<>(roundRect);
		node.setFill(model.getColor());
		node.setStroke(Color.BLACK);
		node.setStrokeWidth(2);

		visual.getChildren().add(node);
	}

	private void addText(Group visual, Text text, Bounds textBounds, Rectangle bounds) {
		text.setTextOrigin(VPos.CENTER);
		text.setY(bounds.getY() + bounds.getHeight() / 2);
		text.setX(bounds.getX() + bounds.getWidth() / 2 - textBounds.getWidth() / 2);
		visual.getChildren().add(text);
	}

	@Override
	public SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return HashMultimap.create();
	}

	@Override
	public List<? extends Object> doGetContentChildren() {
		return Collections.emptyList();
	}
}
