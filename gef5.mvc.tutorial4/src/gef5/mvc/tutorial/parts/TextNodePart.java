package gef5.mvc.tutorial.parts;

import java.util.*;

import org.eclipse.gef.fx.nodes.*;
import org.eclipse.gef.geometry.planar.*;
import org.eclipse.gef.mvc.fx.parts.*;

import com.google.common.collect.*;

import gef5.mvc.tutorial.model.*;
import javafx.beans.value.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.transform.*;

public class TextNodePart extends AbstractContentPart<Group> implements ITransformableContentPart<Group> {

	private Text text;
	private GeometryNode<RoundedRectangle> fxRoundedRectNode;

	private final ChangeListener<Object> objectObserver = new ChangeListener<Object>() {
		@Override
		public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {

			refreshVisual();
		}
	};

	@Override
	protected void doActivate() {
		super.doActivate();
		getContent().addPropertyChangeListener(objectObserver);
	}

	@Override
	protected void doDeactivate() {
		getContent().removePropertyChangeListener(objectObserver);
		super.doDeactivate();
	}

	@Override
	public TextNode getContent() {
		return (TextNode) super.getContent();
	}

	@Override
	protected Group doCreateVisual() {
		Group group = new Group();
		text = new Text();
		fxRoundedRectNode = new GeometryNode<>();

		group.getChildren().add(fxRoundedRectNode);
		group.getChildren().add(text);

		return group;
	}

	@Override
	protected void doRefreshVisual(Group visual) {
		TextNode model = getContent();

		Font font = Font.font("Monospace", FontWeight.BOLD, 50);
		Color textColor = Color.BLACK;
		int textStrokeWidth = 2;

		text.setText(model.getText());
		text.setFont(font);
		text.setFill(textColor);
		text.setStrokeWidth(textStrokeWidth);

		// measure size
		Bounds textBounds = msrText(model.getText(), font, textStrokeWidth);

		Rectangle bounds = new Rectangle(new Point(0, 0),
				new Dimension(textBounds.getWidth() + textBounds.getHeight(), textBounds.getHeight() * 1.5));

		// the rounded rectangle
		{
			RoundedRectangle roundRect = new RoundedRectangle(bounds, 10, 10);
			fxRoundedRectNode.setGeometry(roundRect);
			fxRoundedRectNode.setFill(model.getColor());
			fxRoundedRectNode.setStroke(Color.BLACK);
			fxRoundedRectNode.setStrokeWidth(2);
			fxRoundedRectNode.toBack();
		}
		// the text
		{
			text.setTextOrigin(VPos.CENTER);
			text.setY(bounds.getY() + bounds.getHeight() / 2);
			text.setX(bounds.getX() + bounds.getWidth() / 2 - textBounds.getWidth() / 2);
			text.toFront();
		}
		{
		}
	}

	private Bounds msrText(String string, Font font, int textStrokeWidth) {
		Text msrText = new Text(string);
		msrText.setFont(font);
		msrText.setStrokeWidth(textStrokeWidth);

		new Scene(new Group(msrText));
		Bounds textBounds = msrText.getLayoutBounds();
		return textBounds;
	}

	public void setPosition(Point newPos) {
		getContent().setPosition(newPos);
	}

	@Override
	public SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return HashMultimap.create();
	}

	@Override
	public List<? extends Object> doGetContentChildren() {
		return Collections.emptyList();
	}

	@Override
	public Affine getContentTransform() {
		TextNode model = getContent();
		Point position = model.getPosition();
		Affine res = new Affine();
		res.setToTransform(Transform.translate(position.x, position.y));
		return res;
	}

	@Override
	public void setContentTransform(Affine totalTransform) {
		TextNode model = getContent();
		model.setPosition(new Point(totalTransform.getTx(), totalTransform.getTy()));
	}

}
