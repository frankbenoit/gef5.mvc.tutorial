package gef5.mvc.tutorial.parts;

import java.beans.*;
import java.util.*;

import org.eclipse.gef.mvc.fx.parts.*;

import com.google.common.collect.*;

import gef5.mvc.tutorial.model.*;
import javafx.collections.*;
import javafx.scene.*;

public class ModelPart extends AbstractContentPart<Group> implements PropertyChangeListener {

	@Override
	protected void doActivate() {
		super.doActivate();
		// TODO: fix this
		// getContent().addPropertyChangeListener(this);

	}

	@Override
	protected void doDeactivate() {
		// TODO: fix this
		// getContent().removePropertyChangeListener(this);

		super.doDeactivate();
	}

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
	}

	@Override
	public List<? extends TextNode> doGetContentChildren() {
		Model model = getContent();
		return model.getNodes();
	}

	@Override
	protected void doAddChildVisual(IVisualPart<? extends Node> child, int index) {
		ObservableList<Node> children = getVisual().getChildren();
		Node visual = child.getVisual();
		children.add(visual);
	}

	@Override
	protected void doAddContentChild(Object contentChild, int index) {
		getContent().addNode((TextNode) contentChild, index);
	}

	@Override
	public void doRemoveContentChild(Object contentChild) {
		getContent().getNodes().remove(contentChild);
	}

	@Override
	protected void doRemoveChildVisual(IVisualPart<? extends Node> child, int index) {
		ObservableList<Node> children = getVisual().getChildren();
		children.remove(index);
	}

	@Override
	public SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return HashMultimap.create();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() == getContent()) {
			refreshVisual();
		}
	}

}
