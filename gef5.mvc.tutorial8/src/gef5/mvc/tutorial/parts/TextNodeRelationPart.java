package gef5.mvc.tutorial.parts;

import java.util.*;

import org.eclipse.gef.fx.anchors.*;
import org.eclipse.gef.fx.nodes.*;
import org.eclipse.gef.geometry.planar.*;
import org.eclipse.gef.mvc.fx.parts.*;

import com.google.common.collect.*;
import com.google.common.reflect.*;
import com.google.inject.*;

import gef5.mvc.tutorial.model.*;
import javafx.scene.*;
import javafx.scene.paint.*;

public class TextNodeRelationPart extends AbstractContentPart<Connection> {

	private static final String ROLE_START = "START";
	private static final String ROLE_END = "END";
	protected static final double VSPACE = 10;

	public TextNodeRelationPart() {
	}

	@Override
	public TextNodeRelation getContent() {
		return (TextNodeRelation) super.getContent();
	}

	@Override
	protected Connection doCreateVisual() {

		Connection visual = new Connection();
		visual.setRouter(new IConnectionRouter() {
			@Override
			public ICurve routeConnection(Point[] points) {
				if (points == null || points.length < 2) {
					return new Line(0, 0, 0, 0);
				}
				if (points.length > 2) {
					throw new RuntimeException("len: " + points.length);
				}
				Point start = points[0].x < points[1].x ? points[0] : points[1];
				Point end = points[0].x > points[1].x ? points[0] : points[1];
				Point p1 = new Point(start.x + VSPACE, start.y);
				Point p2 = new Point(start.x + VSPACE, end.y);
				Polyline poly = new Polyline(start, p1, p2, end);
				return poly;
			}

			@Override
			public void route(Connection connection) {
				throw new UnsupportedOperationException();

			}

			@Override
			public boolean wasInserted(IAnchor anchor) {
				throw new UnsupportedOperationException();
				return false;
			}
		});
		visual.getCurveNode().setStroke(Color.BLACK);
		visual.getCurveNode().setStrokeWidth(1.5);
		return visual;
	}

	@Override
	protected void doRefreshVisual(Connection visual) {
	}

	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
		HashMultimap<Object, String> res = HashMultimap.create();
		TextNodeRelation nr = getContent();
		res.put(nr.getParent(), ROLE_START);
		res.put(nr.getChild(), ROLE_END);
		return res;
	}

	@Override
	protected List<? extends Object> doGetContentChildren() {
		return Collections.emptyList();
	}

	@Override
	protected void doAttachToAnchorageVisual(IVisualPart<? extends Node> anchorage, String role) {
		@SuppressWarnings("serial")
		Provider<? extends IAnchor> provider = anchorage.getAdapter(new TypeToken<Provider<? extends IAnchor>>() {
		});

		IAnchor anchor = provider.get();
		if (role.equals(ROLE_START)) {
			getVisual().setStartAnchor(anchor);
		} else if (role.equals(ROLE_END)) {
			getVisual().setEndAnchor(anchor);
		} else {
			throw new IllegalStateException("Cannot attach to anchor with role <" + role + ">.");
		}
	}

}
