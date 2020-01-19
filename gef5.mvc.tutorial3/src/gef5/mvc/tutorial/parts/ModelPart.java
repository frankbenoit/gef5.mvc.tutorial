package gef5.mvc.tutorial.parts;

import java.util.*;

import org.eclipse.gef.mvc.fx.parts.*;

import com.google.common.collect.*;

import gef5.mvc.tutorial.model.*;
import javafx.collections.*;
import javafx.scene.*;

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
	public SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return HashMultimap.create();
	}

}
