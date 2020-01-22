/*******************************************************************************
 * Copyright (c) 2014, 2015 itemis AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Nyßen (itemis AG) - initial API and implementation
 *
 *******************************************************************************/
package gef5.mvc.tutorial.parts;

import java.util.*;

import org.eclipse.gef.mvc.fx.parts.*;

import com.google.inject.*;

import javafx.scene.*;

public class HandlePartFactory extends DefaultHoverIntentHandlePartFactory {

	@Inject
	private Injector injector;

	@Override
	public List<IHandlePart<? extends Node>> createHandleParts(List<? extends IVisualPart<? extends Node>> targets,
			Map<Object, Object> contextMap) {

		List<IHandlePart<? extends Node>> handles = new ArrayList<>();

		final IVisualPart<? extends Node> target = targets.get(0);
		if (target instanceof TextNodePart) {
			// create root handle part
			HoverHandleRootPart parentHp = new HoverHandleRootPart();
			injector.injectMembers(parentHp);
			handles.add(parentHp);

			DeleteHoverHandlePart deleteHp = new DeleteHoverHandlePart();
			injector.injectMembers(deleteHp);
			parentHp.addChild(deleteHp);

			return handles;
		}
		return super.createHandleParts(targets, contextMap);
	}

}
