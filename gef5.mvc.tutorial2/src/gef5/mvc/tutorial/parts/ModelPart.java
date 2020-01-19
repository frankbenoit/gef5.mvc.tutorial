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

public class ModelPart extends AbstractContentPart<Group> {

	@Override
	public Model getContent() {
		return (Model) super.getContent();
	}

	@Override
	protected Group doCreateVisual() {
		return new Group();
	}

	@Override
	protected void doRefreshVisual(Group visual) {
		Model model = getContent();

		Text text = new Text(model.getText());
		text.setFont(Font.font("Monospace", FontWeight.BOLD, 50));
		text.setFill(Color.BLACK);
		text.setStrokeWidth(2);

		// measure size
		new Scene(new Group(text));
		Bounds textBounds = text.getLayoutBounds();

		Rectangle bounds = new Rectangle(model.getPosition(),
				new Dimension(textBounds.getWidth() + textBounds.getHeight(), textBounds.getHeight() * 1.5));

		// the rounded rectangle
		{
			RoundedRectangle roundRect = new RoundedRectangle(bounds, 10, 10);
			GeometryNode<RoundedRectangle> GeometryNode = new GeometryNode<>(roundRect);
			GeometryNode.setFill(model.getColor());
			GeometryNode.setStroke(Color.BLACK);
			GeometryNode.setStrokeWidth(2);

			visual.getChildren().add(GeometryNode);
		}
		// the text
		{
			text.setTextOrigin(VPos.CENTER);
			text.setY(bounds.getY() + bounds.getHeight() / 2);
			text.setX(bounds.getX() + bounds.getWidth() / 2 - textBounds.getWidth() / 2);
			visual.getChildren().add(text);
		}
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
