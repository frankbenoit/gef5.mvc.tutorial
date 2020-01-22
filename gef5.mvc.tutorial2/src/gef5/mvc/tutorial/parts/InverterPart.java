package gef5.mvc.tutorial.parts;

import java.util.*;

import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.*;

import com.google.common.collect.*;

import gef5.mvc.tutorial.model.*;
import javafx.scene.*;
import javafx.scene.effect.*;
import javafx.scene.effect.Light.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class InverterPart extends AbstractContentPart<Group> {

	private static final double CIRCLE_RADIUS = 5;
	private static final double PIN_LENGTH = 10;
	private static final double BODY_LENGTH = 40;
	private static final double BODY_STROKE = 4d;
	private static final double NORMAL_STROKE = 2d;

	@Override
	public InverterModel getContent() {
		return (InverterModel) super.getContent();
	}

	@Override
	protected Group doCreateVisual() {
		Group cont = new Group();
		Group res = new Group(cont);

		Circle circle = new Circle(CIRCLE_RADIUS);
		circle.setTranslateX(BODY_LENGTH/2 + CIRCLE_RADIUS / 2);
		circle.setTranslateY(0);
		circle.setFill(Color.WHITE);
		circle.setStroke(Color.BLACK);
		circle.setStrokeWidth(NORMAL_STROKE);

		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(
				BODY_LENGTH / 2, 0.0, 
				-BODY_LENGTH / 2, BODY_LENGTH / 2, 
				-BODY_LENGTH / 2, -BODY_LENGTH / 2);
		polygon.setFill(Color.WHITE);
		polygon.setStrokeWidth(BODY_STROKE);
		polygon.setStroke(Color.BLACK);
		polygon.setStrokeLineJoin(StrokeLineJoin.ROUND);

		double pinDockLeft = -BODY_LENGTH / 2;
		double pinDockRight = BODY_LENGTH / 2 + CIRCLE_RADIUS;
		
		Line pinLeft = new Line(pinDockLeft - PIN_LENGTH, 0, pinDockLeft, 0);
		pinLeft.setStrokeWidth(NORMAL_STROKE);
		pinLeft.setStrokeLineCap(StrokeLineCap.ROUND);
		
		Line pinRight = new Line(pinDockRight + PIN_LENGTH, 0, pinDockRight, 0);
		pinRight.setStrokeWidth(NORMAL_STROKE);
		pinRight.setStrokeLineCap(StrokeLineCap.ROUND);

		cont.getChildren().addAll(pinLeft, pinRight, polygon, circle);

		//res.setEffect(createShadowEffect(0));

		return res;
	}

	private static Effect createShadowEffect(double angle) {
		DropShadow outerShadow = new DropShadow();
		outerShadow.setRadius(3);
		outerShadow.setSpread(0.2);
		outerShadow.setOffsetX(3);
		outerShadow.setOffsetY(3);
		outerShadow.setColor(new Color(0.3, 0.3, 0.3, 0.5));

		Distant light = new Distant();
		light.setAzimuth(-135.0f+angle);

		Lighting l = new Lighting();
		l.setLight(light);
		l.setSurfaceScale(1.0f);
		l.setSpecularConstant(2.0f);
		l.setSpecularExponent(40.0f);

		Blend effects = new Blend(BlendMode.MULTIPLY );
//		effects.setTopInput(l);
		effects.setBottomInput(outerShadow);

		return effects;
	}

	@Override
	protected void doRefreshVisual(Group visual) {
		Point position = getContent().getPosition();
		visual.setTranslateX(position.x);
		visual.setTranslateY(position.y);
		
		double angle = getContent().getOrientation().degree();
		visual.getChildren().get(0).setRotate(-angle);

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
