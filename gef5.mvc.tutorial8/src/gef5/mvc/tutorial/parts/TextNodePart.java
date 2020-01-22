package gef5.mvc.tutorial.parts;

import java.beans.*;
import java.util.*;

import org.eclipse.gef.fx.nodes.*;
import org.eclipse.gef.geometry.planar.*;
import org.eclipse.gef.mvc.fx.parts.*;
import org.eclipse.gef.mvc.fx.policies.*;

import com.google.common.collect.*;

import gef5.mvc.tutorial.model.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.transform.*;

public class TextNodePart extends AbstractContentPart<StackPane> implements PropertyChangeListener {

	private Point layoutVisualPosition = new Point();
	private Rectangle layoutBounds = new Rectangle();

	private Text text;
	private GeometryNode<RoundedRectangle> fxRoundedRectNode;
	private TextField editText;
	private static final Color COLOR = Color.LIGHTSKYBLUE;
	private static final int TEXT_STROKE_WIDTH = 1;
	private static final double LAYOUT_VSPACE = 10;
	private static final double LAYOUT_HSPACE = 25;
	private Font font = Font.font("Monospace", FontWeight.BOLD, 25);

	@Override
	protected void doActivate() {
		super.doActivate();
		getContent().addPropertyChangeListener(this);
	}

	@Override
	protected void doDeactivate() {
		getContent().removePropertyChangeListener(this);
		super.doDeactivate();
	}

	@Override
	public TextNode getContent() {
		return (TextNode) super.getContent();
	}

	@Override
	protected StackPane doCreateVisual() {

		StackPane stack = new StackPane();
		text = new Text();
		fxRoundedRectNode = new GeometryNode<>();
		editText = new TextField();

		editText.setManaged(false);
		editText.setVisible(false);

		stack.getChildren().add(fxRoundedRectNode);
		stack.getChildren().add(text);
		stack.getChildren().add(editText);

		return stack;
	}

	@Override
	protected void doRefreshVisual(StackPane visual) {
		TextNode model = getContent();

		Color textColor = Color.BLACK;

		text.setText(model.getText());
		text.setFont(font);
		text.setFill(textColor);
		text.setStrokeWidth(TEXT_STROKE_WIDTH);

		// measure size
		Dimension size = msrVisual();
		Rectangle bounds = new Rectangle(0, 0, size.width, size.height);

		// the rounded rectangle
		RoundedRectangle roundRect = new RoundedRectangle(bounds, 4, 4);
		fxRoundedRectNode.setGeometry(roundRect);
		fxRoundedRectNode.setFill(COLOR);
		fxRoundedRectNode.setStroke(Color.BLACK);
		fxRoundedRectNode.setStrokeWidth(1.2);
		fxRoundedRectNode.toBack();

		text.toFront();

		editText.toFront();
		editText.setPrefWidth(bounds.getWidth());

//		getRoot().getViewer().getContentPartMap().get(

		{
//			Point position = model.getPosition();
			Affine affine = getAdapter(TransformPolicy.TRANSFORM_PROVIDER_KEY).get();
			affine.setTx(layoutVisualPosition.x);
			affine.setTy(layoutVisualPosition.y);
		}

	}

	private Dimension msrVisual() {
		Bounds textBounds = msrText(getContent().getText(), font, TEXT_STROKE_WIDTH);
		return new Dimension(textBounds.getWidth() + textBounds.getHeight(), textBounds.getHeight() * 1.5);
	}

	private Bounds msrText(String string, Font font, int textStrokeWidth) {
		Text msrText = new Text(string);
		msrText.setFont(font);
		msrText.setStrokeWidth(textStrokeWidth);

		new Scene(new Group(msrText));
		Bounds textBounds = msrText.getLayoutBounds();
		return textBounds;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() == getContent()) {
			refreshVisual();
		}
	}

	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
		HashMultimap<TextNode, String> res = HashMultimap.create();
		res.put(getContent(), "START");
		res.put(getContent(), "END");
		return res;
	}

	@Override
	protected List<? extends Object> doGetContentChildren() {
		return Collections.emptyList();
	}

	private void layoutExtends() {
		double childsHeight = 0;
		double childsWidth = 0;
		for (TextNode tn : getContent().childs) {
			TextNodePart part = (TextNodePart) getViewer().getContentPartMap().get(tn);
			part.layoutExtends();
			childsHeight += part.layoutBounds.getHeight();
			childsWidth = Math.max(childsWidth, part.layoutBounds.getWidth());
		}
		if (!getContent().childs.isEmpty()) {
			childsHeight += (getContent().childs.size() - 1) * LAYOUT_VSPACE;
		}

		Dimension size = msrVisual();

		layoutBounds.setHeight(size.height);
		layoutBounds.setWidth(size.width);

		if (!getContent().childs.isEmpty()) {
			layoutBounds.setHeight(Math.max(layoutBounds.getHeight(), childsHeight));
			layoutBounds.setWidth(layoutBounds.getWidth() + LAYOUT_HSPACE + childsWidth);
		}

	}

	private void layoutPosition(Point p) {

		Dimension size = msrVisual();

		layoutBounds.setX(p.x);
		layoutBounds.setY(p.y);
		layoutVisualPosition.x = p.x;
		layoutVisualPosition.y = p.y + layoutBounds.getHeight() / 2 - size.height / 2;

		double x = p.x + size.width + LAYOUT_HSPACE;
		double y = p.y;
		for (TextNode tn : getContent().childs) {
			TextNodePart part = (TextNodePart) getViewer().getContentPartMap().get(tn);

			part.layoutPosition(new Point(x, y));
			y = part.layoutBounds.getY() + part.layoutBounds.getHeight() + LAYOUT_VSPACE;
		}
	}

	public void layout() {
		layoutExtends();
		layoutPosition(new Point(10, 10));
		layoutRefreshUi();
	}

	private void layoutRefreshUi() {
		refreshVisual();
		for (TextNode tn : getContent().childs) {
			TextNodePart part = (TextNodePart) getViewer().getContentPartMap().get(tn);
			part.layoutRefreshUi();
		}
	}

	@Override
	protected void doAttachToAnchorageVisual(IVisualPart<? extends Node> anchorage, String role) {
	}

}
