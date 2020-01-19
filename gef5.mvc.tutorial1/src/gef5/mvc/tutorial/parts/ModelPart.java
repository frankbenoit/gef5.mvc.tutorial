package gef5.mvc.tutorial.parts;

import java.util.*;

import org.eclipse.gef.fx.nodes.*;
import org.eclipse.gef.geometry.planar.*;
import org.eclipse.gef.mvc.fx.parts.*;

import com.google.common.collect.*;

import gef5.mvc.tutorial.model.*;
import javafx.scene.paint.*;

public class ModelPart extends AbstractContentPart<GeometryNode<RoundedRectangle>> {

	@Override
	public Model getContent() {
		return (Model) super.getContent();
	}

	@Override
	protected void doRefreshVisual(GeometryNode<RoundedRectangle> visual) {
		Model model = getContent();
		visual.setGeometry(new RoundedRectangle(model.getRect(), 10, 10));
		visual.setFill(model.getColor());
		visual.setStroke(Color.BLACK);
		visual.setStrokeWidth(2);
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
	protected GeometryNode<RoundedRectangle> doCreateVisual() {
		return new GeometryNode<>();
	}

}
