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
		} else if (content instanceof InverterGate) {
			return injector.getInstance(InverterGatePart.class);
		} else if (content instanceof AndGate) {
			return injector.getInstance(AndGatePart.class);
		} else if (content instanceof NandGate) {
			AndGatePart res = injector.getInstance(AndGatePart.class);
			res.setNand(true);
			return res;
		} else if (content instanceof NorGate) {
			OrGatePart res = injector.getInstance(OrGatePart.class);
			res.setNor(true);
			return res;
		} else if (content instanceof OrGate) {
			return injector.getInstance(OrGatePart.class);
		} else {
			throw new IllegalArgumentException(content.getClass().toString());
		}
	}

}
