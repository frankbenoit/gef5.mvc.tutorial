package gef5.mvc.tutorial.parts;

import java.beans.*;
import java.util.*;

import org.eclipse.core.commands.*;
import org.eclipse.gef.fx.nodes.*;
import org.eclipse.gef.geometry.planar.*;
import org.eclipse.gef.mvc.fx.models.FocusModel;
import org.eclipse.gef.mvc.fx.parts.*;

import com.google.common.collect.*;

import gef5.mvc.tutorial.model.*;
import gef5.mvc.tutorial.policies.*;
import javafx.beans.value.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.transform.*;

public class TextNodePart extends AbstractContentPart<StackPane>
		implements PropertyChangeListener, ITransformableContentPart<StackPane> {

	private Text text;
	private GeometryNode<RoundedRectangle> fxRoundedRectNode;

	private boolean isEditing = false;
	private TextField editText;

	private final ChangeListener<Object> objectObserver = new ChangeListener<Object>() {
		@Override
		public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {

			refreshVisual();
		}
	};

	private class FocusListener implements ChangeListener<IContentPart<? extends Node>> {

		private final TextNodePart nodePart;

		FocusListener(TextNodePart nodePart) {
			this.nodePart = nodePart;
		}

		@Override
		public void changed(ObservableValue<? extends IContentPart<? extends Node>> observable,
				IContentPart<? extends Node> oldValue, IContentPart<? extends Node> newValue) {

			if (nodePart != newValue) {

				editModeEnd(false);

			}
		}
	}

	private ChangeListener<IContentPart<? extends Node>> focusObserver = new FocusListener(this);

	@Override
	protected void doActivate() {
		super.doActivate();
		getContent().addPropertyChangeListener(objectObserver);

		FocusModel focusModel = getRoot().getViewer().getAdapter(FocusModel.class);

		focusModel.focusProperty().addListener(focusObserver);

	}

	@Override
	protected void doDeactivate() {
		getContent().removePropertyChangeListener(objectObserver);

		FocusModel focusModel = getRoot().getViewer().getAdapter(FocusModel.class);
		focusModel.focusProperty().removeListener(focusObserver);

		super.doDeactivate();
	}

	@Override
	public TextNode getContent() {
		return (TextNode) super.getContent();
	}

	@Override
	protected StackPane doCreateVisual() {
		StackPane group = new StackPane();
		text = new Text();
		fxRoundedRectNode = new GeometryNode<>();
		editText = new TextField();

		editText.setManaged(false);
		editText.setVisible(false);

		group.getChildren().add(fxRoundedRectNode);
		group.getChildren().add(text);
		group.getChildren().add(editText);

		return group;
	}

	@Override
	protected void doRefreshVisual(StackPane visual) {
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

		Rectangle bounds = new Rectangle(0, 0, textBounds.getWidth() + textBounds.getHeight(),
				textBounds.getHeight() * 1.5);

		// the rounded rectangle
		RoundedRectangle roundRect = new RoundedRectangle(bounds, 10, 10);
		fxRoundedRectNode.setGeometry(roundRect);
		fxRoundedRectNode.setFill(model.getColor());
		fxRoundedRectNode.setStroke(Color.BLACK);
		fxRoundedRectNode.setStrokeWidth(2);
		fxRoundedRectNode.toBack();

		text.toFront();

		editText.toFront();
		editText.setPrefWidth(bounds.getWidth());

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

	public void setPosition(Point newPos) {
		getContent().setPosition(newPos);
	}

	public void editModeStart() {
		if (isEditing) {
			return;
		}

		isEditing = true;
		setVisualsForEditing();

		editText.setText(text.getText());
		editText.requestFocus();
		refreshVisual();
	}

	public void editModeEnd(boolean commit) {
		if (!isEditing) {
			return;
		}
		if (commit) {
			String newText = editText.getText();
			text.setText(newText);
			ChangeTextNodeTextOperation op = new ChangeTextNodeTextOperation(this, getContent().getText(), newText);

			try {
				getViewer().getDomain().execute(op, null);
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		isEditing = false;
		setVisualsForEditing();
	}

	private void setVisualsForEditing() {
		editText.setManaged(isEditing);
		editText.setVisible(isEditing);
		text.setManaged(!isEditing);
		text.setVisible(!isEditing);

	}

	public boolean isEditing() {
		return isEditing;
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
