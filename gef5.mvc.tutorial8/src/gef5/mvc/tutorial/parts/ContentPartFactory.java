package gef5.mvc.tutorial.parts;

import java.util.*;

import org.eclipse.gef.mvc.fx.parts.*;

import com.google.inject.*;

import gef5.mvc.tutorial.model.*;
import javafx.scene.*;

public class ContentPartFactory implements IContentPartFactory {

	@Inject
	private Injector injector;

	@Override
	public IContentPart<? extends Node> createContentPart(Object content, Map<Object, Object> contextMap) {

		if (content instanceof TextNode) {
			return injector.getInstance(TextNodePart.class);
		} else if (content instanceof TextNodeRelation) {
			return injector.getInstance(TextNodeRelationPart.class);
		} else {
			throw new IllegalArgumentException(content.getClass().toString());
		}
	}

}
