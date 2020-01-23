package gef5.mvc.tutorial.parts;

import java.util.*;

import org.eclipse.gef.geometry.planar.*;
import org.eclipse.gef.mvc.fx.parts.*;

import com.google.common.collect.*;

import gef5.mvc.tutorial.model.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class InverterGatePart extends AbstractContentPart<Group> {

	private static final double CIRCLE_RADIUS = 5;
	private static final double PIN_LENGTH = 10;
	private static final double BODY_LENGTH = 40;
	private static final double BODY_HEIGHT = 40;
	private static final double BODY_STROKE = 4d;
	private static final double NORMAL_STROKE = 2d;

	@Override
	public InverterGate getContent() {
		return (InverterGate) super.getContent();
	}

	@Override
	protected Group doCreateVisual() {
		Group res = new Group();

		Circle circle = new Circle(CIRCLE_RADIUS);
		circle.setTranslateX(BODY_LENGTH / 2 - CIRCLE_RADIUS);
		circle.setTranslateY(0);
		circle.setFill(Color.WHITE);
		circle.setStroke(Color.BLACK);
		circle.setStrokeWidth(NORMAL_STROKE);

		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(
				BODY_LENGTH / 2 - CIRCLE_RADIUS, 0.0, 
				-BODY_LENGTH / 2, BODY_HEIGHT / 2, 
				-BODY_LENGTH / 2, -BODY_HEIGHT / 2);
		polygon.setFill(Color.WHITE);
		polygon.setStrokeWidth(BODY_STROKE);
		polygon.setStroke(Color.BLACK);
		polygon.setStrokeLineJoin(StrokeLineJoin.ROUND);

		double pinDockLeft = -BODY_LENGTH / 2;
		double pinDockRight = BODY_LENGTH / 2;
		
		Line pinLeft = new Line(pinDockLeft - PIN_LENGTH, 0, pinDockLeft, 0);
		pinLeft.setStrokeWidth(NORMAL_STROKE);
		pinLeft.setStrokeLineCap(StrokeLineCap.ROUND);
		
		Line pinRight = new Line(pinDockRight + PIN_LENGTH, 0, pinDockRight, 0);
		pinRight.setStrokeWidth(NORMAL_STROKE);
		pinRight.setStrokeLineCap(StrokeLineCap.ROUND);

		res.getChildren().addAll(pinLeft, pinRight, polygon, circle);

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
}
