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

		if (content instanceof TextModel) {
			return injector.getInstance(TextPart.class);
		} else if (content instanceof InverterModel) {
			return injector.getInstance(InverterPart.class);
		} else {
			throw new IllegalArgumentException(content.getClass().toString());
		}
	}

}
