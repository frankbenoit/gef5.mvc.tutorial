package gef5.mvc.tutorial.parts;

import java.util.*;

import org.eclipse.gef.mvc.fx.parts.*;

import com.google.inject.*;

import gef5.mvc.tutorial.model.*;
import javafx.scene.*;

public class ModelPartFactory implements IContentPartFactory {

	@Inject
	private Injector injector;

	@Override
	public IContentPart<? extends Node> createContentPart(Object content, Map<Object, Object> contextMap) {
		System.out.println("ModelPartFactory.createContentPart() " + content);
		contextMap.forEach((k, v) -> System.out.printf("%s -> %s%n"));

		if (content instanceof Model) {
			return injector.getInstance(ModelPart.class);
		} else if (content instanceof TextNode) {
			return injector.getInstance(TextNodePart.class);
		} else {
			throw new IllegalArgumentException(content.getClass().toString());
		}
	}

}
