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

	private final Text text = new Text();
	private final GeometryNode<RoundedRectangle> rectangleNode = new GeometryNode<>();

	@Override
	public TextModel getContent() {
		return (TextModel) super.getContent();
	}

	@Override
	protected Group doCreateVisual() {

		text.setFont(Font.font("Monospace", FontWeight.BOLD, 50));
		text.setFill(Color.BLACK);
		text.setStrokeWidth(2);
		text.setTextOrigin(VPos.CENTER);
		
		rectangleNode.setGeometry(new RoundedRectangle(new Rectangle(), 10, 10));
		rectangleNode.setStroke(Color.BLACK);
		
		Group group = new Group();
		group.getChildren().addAll( rectangleNode, text );
		
		return group;
	}

	@Override
	protected void doRefreshVisual(Group visual) {

		text.setText(getContent().getText());
		
		Bounds textBounds = text.getLayoutBounds();

		refreshRectangleBounds(textBounds);

		refreshText(visual, textBounds);
		
		visual.setTranslateX(getContent().getPosition().x);
		visual.setTranslateY(getContent().getPosition().y);
	}

	private void refreshRectangleBounds(Bounds textBounds) {
		TextModel model = getContent();
		double textWidth = textBounds.getWidth();
		double textHeight = textBounds.getHeight();
		Rectangle bounds = new Rectangle(new Point(), new Dimension(textWidth + textHeight, textHeight * 1.5));
		rectangleNode.getGeometry().setBounds( bounds );
		rectangleNode.setFill(model.getColor());
	}

	private void refreshText(Group visual, Bounds textBounds) {
		text.setText(getContent().getText());
		Rectangle bounds = rectangleNode.getGeometry().getBounds();
		text.setY( bounds.getHeight() / 2);
		text.setX( bounds.getWidth() / 2 - textBounds.getWidth() / 2);
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
