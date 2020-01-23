package gef5.mvc.tutorial.parts;

import java.util.*;

import org.eclipse.gef.geometry.planar.*;
import org.eclipse.gef.mvc.fx.parts.*;

import com.google.common.collect.*;

import gef5.mvc.tutorial.model.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;

public class AndGatePart extends AbstractContentPart<Group> {

	private static final double CIRCLE_RADIUS = 5;
	private static final double PIN_LENGTH = 10;
	private static final double BODY_LENGTH = 40;
	private static final double BODY_HEIGHT = 40;
	private static final double BODY_STROKE = 4d;
	private static final double NORMAL_STROKE = 2d;
	private boolean isNand = false;

	@Override
	public Gate getContent() {
		return (Gate) super.getContent();
	}

	@Override
	protected Group doCreateVisual() {
		Group res = new Group();

		Path body = new Path();
		body.setFill(Color.WHITE);
		body.setStrokeWidth(BODY_STROKE);
		body.setStrokeLineCap(StrokeLineCap.ROUND);

		double circleAdjust = isNand ? CIRCLE_RADIUS : 0;
		ObservableList<PathElement> bodyElements = body.getElements();
		bodyElements.add(new MoveTo(-circleAdjust, -BODY_HEIGHT/2));
		bodyElements.add(new LineTo(-BODY_LENGTH/2, -BODY_HEIGHT/2));
		bodyElements.add(new LineTo(-BODY_LENGTH/2, BODY_HEIGHT/2));
		bodyElements.add(new LineTo(0, BODY_HEIGHT/2));
		bodyElements.add(new ArcTo(BODY_LENGTH/2, BODY_HEIGHT/2, 0, -circleAdjust, -BODY_HEIGHT/2, false, false ));
		
		double pinDockLeft = -BODY_LENGTH / 2;
		double pinDockRight = BODY_LENGTH / 2;
		
		Line pinLeft1 = new Line(pinDockLeft - PIN_LENGTH, -BODY_HEIGHT/4, pinDockLeft, -BODY_HEIGHT/4);
		pinLeft1.setStrokeWidth(NORMAL_STROKE);
		pinLeft1.setStrokeLineCap(StrokeLineCap.ROUND);
		
		Line pinLeft2 = new Line(pinDockLeft - PIN_LENGTH, BODY_HEIGHT/4, pinDockLeft, BODY_HEIGHT/4);
		pinLeft2.setStrokeWidth(NORMAL_STROKE);
		pinLeft2.setStrokeLineCap(StrokeLineCap.ROUND);
		
		Line pinRight = new Line(pinDockRight + PIN_LENGTH, 0, pinDockRight, 0);
		pinRight.setStrokeWidth(NORMAL_STROKE);
		pinRight.setStrokeLineCap(StrokeLineCap.ROUND);

		res.getChildren().addAll(pinLeft1, pinLeft2, pinRight, body);

		if( isNand ) {
			Circle circle = new Circle(CIRCLE_RADIUS);
			circle.setTranslateX(BODY_LENGTH / 2);
			circle.setTranslateY(0);
			circle.setFill(Color.WHITE);
			circle.setStroke(Color.BLACK);
			circle.setStrokeWidth(NORMAL_STROKE);
			res.getChildren().add(circle);
		}
		
		return res;
	}

	@Override
	protected void doRefreshVisual(Group visual) {
		Point position = getContent().getPosition();
		visual.setTranslateX(position.x);
		visual.setTranslateY(position.y);
		
		double angle = getContent().getOrientation().degree();
		visual.setRotate(-angle);

	}

	@Override
	public SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return HashMultimap.create();
	}

	@Override
	public List<? extends Object> doGetContentChildren() {
		return Collections.emptyList();
	}

	public void setNand(boolean value) {
		isNand  = true;
	}
}
