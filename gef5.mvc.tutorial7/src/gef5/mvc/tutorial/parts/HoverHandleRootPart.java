/*******************************************************************************
 * Copyright (c) 2015 itemis AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Matthias Wienand (itemis AG) - initial API and implementation
 *
 *******************************************************************************/
package gef5.mvc.tutorial.parts;

import java.util.Map.*;

import org.eclipse.gef.mvc.fx.handlers.*;
import org.eclipse.gef.mvc.fx.parts.*;

import com.google.common.collect.*;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

public class HoverHandleRootPart extends AbstractHandlePart<VBox> {

	public HoverHandleRootPart() {
		setAdapter(new HoverOnHoverHandler() {
			@Override
			public void hover(MouseEvent e) {
				// XXX: deactivate hover for this part
			}
		});
	}

	@Override
	protected void doAddChildVisual(IVisualPart<? extends Node> child, int index) {
		getVisual().getChildren().add(index, child.getVisual());
		for (Entry<IVisualPart<? extends Node>, String> anchorage : getAnchoragesUnmodifiable().entries()) {
			child.attachToAnchorage(anchorage.getKey(), anchorage.getValue());
		}
	}

	@Override
	protected void doAttachToAnchorageVisual(IVisualPart<? extends Node> anchorage, String role) {
		super.doAttachToAnchorageVisual(anchorage, role);
		for (IVisualPart<? extends Node> child : getChildrenUnmodifiable()) {
			child.attachToAnchorage(anchorage, role);
		}
	}

	@Override
	protected VBox doCreateVisual() {
		VBox vBox = new VBox();
		vBox.setPickOnBounds(true);
		return vBox;
	}

	@Override
	protected void doDetachFromAnchorageVisual(IVisualPart<? extends Node> anchorage, String role) {
		super.doDetachFromAnchorageVisual(anchorage, role);
		for (IVisualPart<? extends Node> child : getChildrenUnmodifiable()) {
			child.detachFromAnchorage(anchorage, role);
		}
	}

	@Override
	protected void doRefreshVisual(VBox visual) {
		// check if we have a host
		SetMultimap<IVisualPart<? extends Node>, String> anchorages = getAnchoragesUnmodifiable();
		if (anchorages.isEmpty()) {
			return;
		}

		// determine center location of host visual
		IVisualPart<? extends Node> anchorage = anchorages.keys().iterator().next();
		refreshHandleLocation(anchorage.getVisual());
	}

	protected void refreshHandleLocation(Node hostVisual) {
		// position vbox top-right next to the host
		Bounds hostBounds = hostVisual.getBoundsInParent();
		Parent parent = hostVisual.getParent();
		if (parent != null) {
			hostBounds = parent.localToScene(hostBounds);
		}
		Point2D location = getVisual().getParent().sceneToLocal(hostBounds.getMaxX(), hostBounds.getMinY());
		getVisual().setLayoutX(location.getX());
		getVisual().setLayoutY(location.getY());
	}

	@Override
	protected void doRemoveChildVisual(IVisualPart<? extends Node> child, int index) {
		getVisual().getChildren().remove(index);
	}

}
