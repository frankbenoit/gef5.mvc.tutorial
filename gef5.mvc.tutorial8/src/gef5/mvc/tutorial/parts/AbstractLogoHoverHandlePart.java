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

import java.beans.*;

import org.eclipse.gef.mvc.fx.parts.*;
import org.eclipse.gef.mvc.fx.viewer.*;

import com.google.common.collect.*;

import javafx.scene.*;

public abstract class AbstractLogoHoverHandlePart<T extends Node> extends AbstractHandlePart<T> {

	private boolean registered = false;
	private final PropertyChangeListener parentAnchoragesChangeListener = new PropertyChangeListener() {
		@SuppressWarnings("unchecked")
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (IVisualPart.ANCHORAGES_PROPERTY.equals(evt.getPropertyName())) {
				onParentAnchoragesChanged((SetMultimap<IVisualPart<? extends Node>, String>) evt.getOldValue(),
						(SetMultimap<IVisualPart<? extends Node>, String>) evt.getNewValue());
			}
		}
	};

	@Override
	protected void doRefreshVisual(T visual) {
		// automatically layed out by its parent
	}

	protected void onParentAnchoragesChanged(SetMultimap<IVisualPart<? extends Node>, String> oldAnchorages,
			SetMultimap<IVisualPart<? extends Node>, String> newAnchorages) {
		if (!registered && getViewer() != null) {
			register(getViewer());
		}
	}

	@Override
	protected void register(IViewer viewer) {
		if (registered) {
			return;
		}
		super.register(viewer);
		registered = true;
	}

	@Override
	public void setParent(IVisualPart<? extends Node> newParent) {
		if (getParent() != null) {
			getParent().removePropertyChangeListener(parentAnchoragesChangeListener);
		}
		if (newParent != null) {
			newParent.addPropertyChangeListener(parentAnchoragesChangeListener);
		}
		super.setParent(newParent);
	}

	@Override
	protected void unregister(IViewer viewer) {
		if (!registered) {
			return;
		}
		super.unregister(viewer);
		registered = false;
	}

}
